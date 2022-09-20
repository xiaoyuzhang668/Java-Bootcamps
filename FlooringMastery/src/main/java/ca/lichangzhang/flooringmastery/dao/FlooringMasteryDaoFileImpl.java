package ca.lichangzhang.flooringmastery.dao;

import ca.lichangzhang.flooringmastery.dto.Backup;
import ca.lichangzhang.flooringmastery.dto.Order;
import ca.lichangzhang.flooringmastery.dto.Product;
import ca.lichangzhang.flooringmastery.dto.Tax;
import ca.lichangzhang.flooringmastery.service.FlooringMasteryFutureDateException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
 */
public class FlooringMasteryDaoFileImpl implements FlooringMasteryDao {

    public FlooringMasteryDaoFileImpl() {
        ORDERFILEPATH = "Orders";
        BACKUP_FILE = "Backup/DataExport.txt";
    }

    //test file folder path
    public FlooringMasteryDaoFileImpl(String orderFilePath, String backupFilePath) {
        ORDERFILEPATH = orderFilePath;
        BACKUP_FILE = backupFilePath;
    }

    // list initialization;
    private Map<Integer, Order> orders = new HashMap<>();
    private List<Product> products;
    private List<Tax> taxes;

    private String fileName;
    private List<Order> itemList;

    // define the folder path of the order file and product, tax files
    private final String ORDERFILEPATH;
    private final String BACKUP_FILE;

    //used to load file into memory for product and state
    private final String PRODUCT_FILE = "Data/Products.txt";
    private final String TAX_FILE = "Data/Taxes.txt";

    private static final String DELIMITER = ","; // file saved delimiter
    private static final String COMMA = "%$%$";

    List<String> fileList = new ArrayList<>(); //order file list inside folder

     

    //=================1.  DISPLAY ORDER =========================================  
    @Override
    // display the order file list inside the folder 
    public void getFolder() throws FlooringMasteryPersistenceException {
        fileList = refreshFolder(); //get most current and updated file list
        showFile(fileList); //display order file list
    }

    @Override
    //check if the order file user search exist or not
    //based on user date entry, retrieve the file with the associated order date, null if no such order date
    public String getFile(String fileName) throws FlooringMasteryPersistenceException {
        if (fileList.contains(fileName)) {
            return fileName;
        } else {
            return null;
        }
    }

    @Override
    //open the file and list them on the console if the file exist with the name user enters, 
    //print out all orders and return with the orders
    public Map<Integer, Order> openFile(String fileName) throws
            FlooringMasteryPersistenceException {
        orders = loadOrder(fileName);//call loadOrder method to get specific order file list and refreshed order map
        //remove non - numeric character from fileName in order to parse date if file name is not consistent
        LocalDate fileDate = LocalDate.parse(fileName.replaceAll("[^\\d]", ""), DateTimeFormatter.ofPattern("MMddyyyy"));

        //print out order data together with the formatted order date field
        orders.forEach((key, value)
                -> System.out.println(fileDate + "  " + value));
        return orders;
    }

    //=================2.  ADD ORDER =========================================  
    @Override
    public Integer getAutoOrderNumber() throws FlooringMasteryPersistenceException {
        fileList = refreshFolder(); //get most current and updated order file list            
        //get the orderNumber into list
        List<Integer> numberList = new ArrayList<>();
        for (String file : fileList) {
            Scanner scanner;
            try {
                scanner = new Scanner(new BufferedReader(new FileReader(ORDERFILEPATH + "/" + file)));
            } catch (FileNotFoundException e) {
                throw new FlooringMasteryPersistenceException("-_- "
                        + "Could not load order data into memory, order file " + file + " could not be displayed.", e);
            }
            scanner.nextLine();  //skip first line
            //currentLine holds the most recent line read from file
            String currentLine;
            //currentItem holds the most recent order unmarshalled
            String orderNum;
            while (scanner.hasNextLine()) {
                currentLine = scanner.nextLine();
                orderNum = currentLine.split(",")[0];//get first word before comma
                numberList.add(Integer.parseInt(orderNum));
            }
            //close scanner
            scanner.close();
        }
        //orderNumber will be maximum + 1
        int orderNumber
                = Collections.max(numberList) + 1;
        return orderNumber;
    }

    @Override
    //add order 
    public Order addOrder(String fileName, Order order) throws 
            FlooringMasteryFutureDateException,
            FlooringMasteryPersistenceException {
        // get auto generated order number
        int orderNumber = order.getOrderNumber();
        //create a new order file if there is none, add the order from view to hashmap 
        createNewFile(fileName);//create a file try and catch   
        orders.put(orderNumber, order);
        itemList = new ArrayList<>(orders.values());//refresh itemList to be persisted } 
        writeOrder(fileName);
        return order;
    }

    //load text file of tax into tax hashmap - 
    @Override
    public List<Tax> loadTax() throws FlooringMasteryPersistenceException {
        Pattern pattern = Pattern.compile(",");
        List<Tax> results;
        try ( Stream<String> lines = Files.lines(Path.of(TAX_FILE))) {
            results = lines.skip(1).map(line -> { //skip first line
                String[] arr = pattern.split(line);
                return new Tax(
                        arr[0],
                        arr[1],
                        new BigDecimal(String.valueOf(arr[2])));
            }).collect(Collectors.toList());
        } catch (IOException dtpe) {
            throw new FlooringMasteryPersistenceException(
                    "ERROR: \tCould not load taxes.txt successfully, please try again later.");
        }

        //get the taxes hashmap into memory
//        taxes
//                = results.stream().collect(Collectors.toMap(Tax::getStateAbbreviation,
//                        Function.identity()));
        return results;
    }

    //load text file of product  into product hashmap
    @Override
    public List<Product> loadProduct() throws FlooringMasteryPersistenceException {
        Pattern pattern = Pattern.compile(",");
        List<Product> results;
        try ( Stream<String> lines = Files.lines(Path.of(PRODUCT_FILE))) {
            results = lines.skip(1).map(line -> {
                String[] arr = pattern.split(line);
                return new Product(
                        arr[0],
                        new BigDecimal(String.valueOf(arr[1])),
                        new BigDecimal(String.valueOf(arr[2])));
            }).collect(Collectors.toList());
        } catch (IOException dtpe) {
            throw new FlooringMasteryPersistenceException(
                    "ERROR: \tCould not load products.txt successfully, please try again later.");
        }

//        products
//                = results.stream().collect(Collectors.toMap(Product::getProductType,
//                        Function.identity()));
        return results;
    }

    //=================3.  EDIT ORDER =========================================  
    @Override
    public Order editOrder(String fileName, Order order) throws
            FlooringMasteryPersistenceException {
        orders = loadOrder(fileName);
        Order changedOrder = orders.put(order.getOrderNumber(), order);
        itemList = new ArrayList<>(orders.values());
        writeOrder(fileName);
        return changedOrder;
    }

    //=================4.  REMOVE ORDER =========================================  
    @Override
    public Order removeOrder(String fileName, int orderNumber) throws FlooringMasteryPersistenceException {
        orders = loadOrder(fileName);
        Order removedOrder = orders.remove(orderNumber);
        itemList = new ArrayList<>(orders.values());
        writeOrder(fileName);
        return removedOrder;
    }

    //=================5.  EXPORT ORDER  =========================================  
    @Override
    public void exportData() throws
            FlooringMasteryPersistenceException {
        FileWriter writer = null;
        try {
            Pattern pattern = Pattern.compile(DELIMITER);
            File directory = new File(ORDERFILEPATH);
            // list of all records from all files
            List<Backup> newList = null;
            // Get list of all the files in form of String Array
            String[] allFiles = directory.list();
            // loop for reading the contents of all the files
            // in the directory directory
            for (String aFile : allFiles) {
                List<Backup> aResult;
                try ( Stream<String> lines = Files.lines(Path.of(ORDERFILEPATH + "/" + aFile))) {
                    aResult = lines.skip(1).map(line -> {
                        String[] arr = pattern.split(line);
                        return new Backup(
                                Integer.parseInt(arr[0]),
                                arr[1] + "," + arr[2] + "," + arr[3] + "," + arr[4] + "," + arr[5] + "," + arr[6] + "," + arr[7] + "," + arr[8] + "," + arr[9] + "," + arr[10]
                                + "," + arr[11] + "," + LocalDate.parse(aFile.replaceAll("[^\\d]", ""), DateTimeFormatter.ofPattern("MMddyyyy"))
                        );
                    }).collect(Collectors.toList());
                } catch (IOException e) {
                    throw new FlooringMasteryPersistenceException(
                            "ERROR: \tCould not load " + aFile + ORDERFILEPATH + " successfully, please try again later.");
                }
                if (newList != null) {
//                    newList = Stream.concat(newList.stream(), aResult.stream()).toList();
//                 newList = newList.addAll( aResult );
                    newList.addAll(aResult);
                } else {
                    newList = aResult;
                }
                System.out.println("Reading from " + aFile);
            }   //sort list by order

            List<Backup> sortedList = newList.stream()
                    .sorted(Comparator.comparingLong(Backup::getOrderNumber))
                    .collect(Collectors.toList());

//        forEach(Backup -> System.out.println(Backup.getOrderNumber()+","+Backup.getOther()));
            writer = new FileWriter(BACKUP_FILE);
            writer.write("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total,OrderDate\n");
            for (Backup str : sortedList) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();
            System.out.println("\nReading from all files"
                    + " in directory ---" + directory.getName() + "--- Completed");
        } catch (IOException ex) {
            throw new FlooringMasteryPersistenceException(
                    "ERROR: \tCould not load order file successfully, please try again later.");
        }
    }

//        newList.sort((left, right) -> left.getOrderNumber() - right.getOrderNumber());  //sort by string
//        Map<Integer, Backup> backups
//                = results.stream().collect(Collectors.toMap(Backup::getOrderNumber,
//                        Function.identity()));
//=================PRIVATE METHOD========================================  
//PRIVATE METHOD
//get refreshed file list in the folder - return fileList;
    private List<String> refreshFolder() throws FlooringMasteryPersistenceException {
        //get File[] with filepath - filepath is the folder
        File[] orderFiles = new File(ORDERFILEPATH).listFiles(); // get the folder of order files
        //if orderFiles is not null and also file name begin with Orders and ends with .txt, 
        //add each file to fileList list
        if (orderFiles != null) {
            for (File file : orderFiles) {
                String fileBegin = file.getName().substring(0, 6);
                String fileEnd = file.getName().substring(file.getName().length() - 4);
                //list to hold the file list insie the filepath folder
                //only display file begin with orer and end with .txt and only once
                if (fileBegin.equals("Orders") && fileEnd.equals(".txt") && !fileList.contains(file.getName())) {
                    fileList.add(file.getName());
                }
            }
        }
        return fileList;
    }

    //print out the name of file with filter on - file name begins with order, ends with .txt
    //assignment requirement - use lambda
    private void showFile(List<String> fileList) throws FlooringMasteryPersistenceException {
        fileList.stream()
                .filter(file -> file.endsWith(".txt"))
                .filter(file -> file.startsWith("Orders"))
                .collect(Collectors.toList())
                .forEach((file)
                        -> System.out.println(file));
    }

//check to see if order file exist.  If there is no such order file, create a new order file
    private void createNewFile(String fileName) throws FlooringMasteryPersistenceException {
        //create new file if the order date does not exist, try and catch
        File file = new File(ORDERFILEPATH + "/" + fileName); //initialize File object and passing path as argument  
        boolean result;
        try {
            result = file.createNewFile();  //creates a new file  
            if (result) // test if successfully created a new file  
            {
                orders.clear();//blank new file
            } else {
                orders = loadOrder(fileName);//retrieve current orders from file - refresh orders
            }
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
                    "ERROR: \tCould not create order file. \n\tPlease go back and try again.");
        }
    }

// MARSHALL AND UNMARSHALL 
//method to persisten object and retrieve file into object memory
    private Order unmarshallItem(String orderAsText) throws FlooringMasteryPersistenceException {
        taxes = loadTax();
        /*
        read text file orderAsText, one line to one line and
        finally change into object orderFromFile 
         */
        String[] indexArray = orderAsText.split(DELIMITER);
        //order is in index of 0 of the array
        int orderNumber = Integer.parseInt(indexArray[0]);
        //itemName is in the index of 1 of the array
        String customerName = indexArray[1].replace(COMMA, DELIMITER);
        //parse last string state
        String stateAbbreviation = indexArray[2];
        Tax taxFromFile = new Tax(stateAbbreviation);
        for (int i = 0; i < taxes.size(); i++) {
            if (taxes.get(i).getStateAbbreviation().equals(stateAbbreviation)) {
                taxFromFile.setStateName(taxes.get(i).getStateName());
            }
        }
        taxFromFile.setTaxRate(new BigDecimal(indexArray[3]));
        //set area entry
        //create new product
        String productType = indexArray[4];
        Product productFromfile = new Product(productType);
        productFromfile.setCostPerSquareFoot(new BigDecimal(indexArray[6]));
        productFromfile.setLaborCostPerSquareFoot(new BigDecimal(indexArray[7]));
        //crate a new order object with orderNumber and itemFromFile parameter
        Order orderFromFile = new Order(orderNumber, productFromfile, taxFromFile);
        //add the remaining index array into the order object    
        //form an item object inside order object
        orderFromFile.setCustomerName(customerName);
        orderFromFile.setTax(taxFromFile);
        orderFromFile.setArea(new BigDecimal(indexArray[5]));
        orderFromFile.setMaterialCost(new BigDecimal(indexArray[8]));
        orderFromFile.setLaborCost(new BigDecimal(indexArray[9]));
        orderFromFile.setTaxSub(new BigDecimal(indexArray[10]));
        orderFromFile.setTotal(new BigDecimal(indexArray[11]));

        orders.put(orderNumber, orderFromFile);
        //create items object and return object to form map
        return orderFromFile;
    }

    /*
    read from file line by line, read order file first, then 
    read item file in order to get item object related to this order
    unmarshallItem, then form map object in memory
    @throws FlooringMasteryPersistenceException if error occurs reading from the file
     */
    private Map<Integer, Order> loadOrder(String fileName) throws FlooringMasteryPersistenceException {
        orders.clear(); //clear and reload a file
        Scanner scanner;
        //complete the file name from dateOfOrder parameter, which is user input
        //create scanner for reading file
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(ORDERFILEPATH + "/" + fileName)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException("-_- Could not load order data into memory, order file " + fileName + " could not be displayed.", e);
        }
        scanner.nextLine();  //skip first line
        //currentLine holds the most recent line read from file
        String currentLine;
        //currentItem holds the most recent order unmarshalled
        Order currentOrder;
        //go through line by line, decoding each line into a
        //order object by calling the unmarshallingItem method
        //process while having more lines
        while (scanner.hasNextLine()) {
            //get the next line in the file
            currentLine = scanner.nextLine();
            //unmarshall the line into a order object with item object inside
            currentOrder = unmarshallItem(currentLine);

            //we are going to use the orderNumber as the map key for the order object
            //put currentItem into map using itemName as key         
            orders.put(currentOrder.getOrderNumber(), currentOrder);
        }
        //close scanner
        scanner.close();
        return orders;
    }

    /*
    translate record from map object memory into file and save it, save string text array 
     */
    private String marshallItem(Order currentOrder) {
        //we need to trun current order object into a line of text for our file
        String orderAsText = currentOrder.getOrderNumber() + DELIMITER;
        //add the rest of the properties in the correct order;
        orderAsText += currentOrder.getCustomerName().replace(DELIMITER, COMMA) + DELIMITER;
        //get tax object
        orderAsText += currentOrder.getTax().getStateAbbreviation() + DELIMITER;
        orderAsText += currentOrder.getTax().getTaxRate() + DELIMITER;
        //product object
        orderAsText += currentOrder.getProduct().getProductType() + DELIMITER;
        orderAsText += currentOrder.getArea() + DELIMITER;
        orderAsText += currentOrder.getProduct().getCostPerSquareFoot() + DELIMITER;
        orderAsText += currentOrder.getProduct().getLaborCostPerSquareFoot() + DELIMITER;
        //remaining order object
        orderAsText += currentOrder.getMaterialCost() + DELIMITER;
        orderAsText += currentOrder.getLaborCost() + DELIMITER;
        orderAsText += currentOrder.getTaxSub() + DELIMITER;
        orderAsText += currentOrder.getTotal();
        //we have now turned an item object into text, retur it
        return orderAsText;
    }

    /*
     write all order in the map object memory out a order file
     @throws FlooringMasteryPersistenceException if an error occurs written to the file
     */
    private void writeOrder(String fileName) throws FlooringMasteryPersistenceException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(ORDERFILEPATH + "/" + fileName));
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("Could not save order data into the order file.", e);
        }
        out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");
        String orderAsText;

        //convert list into hashMap object. lambdas function
//        Map<String, Item> map = new HashMap<>();
//        for (Item i : itemList) {
//            map.put(i.getItemName(), i);
//        }
        for (Order currentOrder : itemList) {
            //turn an order object into a String
            orderAsText = marshallItem(currentOrder);
            //write the item object to the file
            out.println(orderAsText);
            //force PrintWriter to write line to the file
            out.flush();
        }
        //clear up
        out.close();
    }
}
