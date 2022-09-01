package ca.lichangzhang.dvdlibrary.dao;

import ca.lichangzhang.dvdlibrary.dto.DVDRecord;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
 */
public class DVDLibraryDaoFileImpl implements DVDLibraryDao {

    private Map<String, DVDRecord> dvdRecords = new HashMap<>();
    private List<DVDRecord> list = new ArrayList<DVDRecord>(dvdRecords.values());

    public  final String DVD_FILE;
    public static final String DELIMITER = "::";
    
    public DVDLibraryDaoFileImpl() {
        DVD_FILE = "dvdlibrary.txt";
    }
    
    public DVDLibraryDaoFileImpl(String DVDTextFile) {
        DVD_FILE = DVDTextFile;
    }

    private DVDRecord unmarshallDVD(String dvdAsText) {
        //text file one line to change into string text array
        String[] dvdTokens = dvdAsText.split(DELIMITER);
        // title is in index 0 of the array
        String title = dvdTokens[0];
        //create a new dvd object
        DVDRecord dvdFromFile = new DVDRecord(title);
        //add the remaining tokens into the dvd object
        dvdFromFile.setReleaseDate(dvdTokens[1]);
        dvdFromFile.setMpaaRating(dvdTokens[2]);
        dvdFromFile.setDirectorName(dvdTokens[3]);
        dvdFromFile.setStudio(dvdTokens[4]);
        dvdFromFile.setUserNote(dvdTokens[5]);
        //create dvd object and return object
        return dvdFromFile;
    }

    //read from file line into map object in memory
    public void loadDVD() throws DVDLibraryDaoException {

        Scanner scanner;

        try {
            //create scanner for reading file
            scanner = new Scanner(new BufferedReader(new FileReader(DVD_FILE)));
        } catch (FileNotFoundException e) {
            throw new DVDLibraryDaoException("-_- Could not load DVD data into memory.", e);
        }
        //currentLine holds the most recent line read from the file 
        String currentLine;
        //currentRecord holds the most recent DVD unmarshalled
        DVDRecord currentRecord;
        //go through DVD_FILE line by line, decoding each line into a 
        //DVD object by calling the unmarshallDVD method
        //process while have more line
        while (scanner.hasNextLine()) {
            //get the next ine in the file
            currentLine = scanner.nextLine();
            //unmarshall the line into a DVD object
            currentRecord = unmarshallDVD(currentLine);

            //we are going to use the title as the map keyfor DVD object
            //put currentRecord into map using title as key
            dvdRecords.put(currentRecord.getTitle(), currentRecord);
        }
        //close scanner
        scanner.close();
    }

    //translate record from object memory into file and save it
    private String marshallDVD(DVDRecord dvdRecord) {
        // We need to turn a DVD object into a line of text for our file.
        String dvdAsText = dvdRecord.getTitle() + DELIMITER;

        // add the rest of the properties in the correct order:
        // ReleaseDate
        dvdAsText += dvdRecord.getReleaseDate() + DELIMITER;

        // mpaa rating
        dvdAsText += dvdRecord.getMpaaRating() + DELIMITER;

        // Director name
        dvdAsText += dvdRecord.getDirectorName() + DELIMITER;

        // Studio
        dvdAsText += dvdRecord.getStudio() + DELIMITER;

        // User note
        dvdAsText += dvdRecord.getUserNote();

        // We have now turned a student to text! Return it!
        return dvdAsText;
    }

    /**
     * Writes all DVD in the map object memory out to a DVD_FILE.
     *
     * @throws DVDLibraryDaoException if an error occurs writing to the file
     */
    private void writeDVD() throws DVDLibraryDaoException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(DVD_FILE));
        } catch (IOException e) {
            throw new DVDLibraryDaoException("Could not save DVD data.", e);
        }

        String dvdAsText;
        List<DVDRecord> dvdList = this.getAllDVD();
        for (DVDRecord currentRecord : dvdList) {
            // turn a DVD object into a String
            dvdAsText = marshallDVD(currentRecord);
            // write the DVD object to the file
            out.println(dvdAsText);
            // force PrintWriter to write line to the file
            out.flush();
        }
        // Clean up
        out.close();
    }

    private String checkTitle(String title) {
        String duplicateTitle = null;

        if (dvdRecords.containsKey(title)){
             duplicateTitle = title;
        }
        //return existing title or null if there is no matching
        return duplicateTitle;    
    }

    @Override
    public DVDRecord addRecord(String title, DVDRecord dvdRecord) throws DVDLibraryDaoException {
       //load data from file into memory
        loadDVD();
        DVDRecord currentDVD = null;
        //if dvd record already exist, then do not save; if dvd record does not exist, then save the record into file.
        if (checkTitle(title) == null) {
            currentDVD = dvdRecord;
            dvdRecords.put(title, dvdRecord);
            writeDVD();   
        }   
        return currentDVD;
    }
    
    @Override
    public DVDRecord removeRecord(String title) throws DVDLibraryDaoException {
        loadDVD();
        DVDRecord removedRecord = dvdRecords.remove(title);
        writeDVD();
        return removedRecord;
    }

    @Override
    public DVDRecord editRecord(String title, DVDRecord dvdRecord) throws DVDLibraryDaoException {
        loadDVD();
        DVDRecord changeDVD = dvdRecords.put(title, dvdRecord);
        writeDVD();
        return changeDVD;
    }

    @Override
    public List<DVDRecord> getAllDVD() throws DVDLibraryDaoException {
        loadDVD();
        return new ArrayList<DVDRecord>(dvdRecords.values());
    }

    @Override
    public DVDRecord getRecord(String title) throws DVDLibraryDaoException {
        loadDVD();
        return dvdRecords.get(title);
    }

    @Override
    public List<DVDRecord> getByYears(int years) throws DVDLibraryDaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void getByRating() throws DVDLibraryDaoException {
        loadDVD();
        new ArrayList<DVDRecord>(dvdRecords.values()).stream()
            .forEach((dvd) -> System.out.println(dvd.getTitle() + " : " + dvd.getMpaaRating()));
        
    }    

    @Override
    public List<DVDRecord> getByDirector(String director) throws DVDLibraryDaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<DVDRecord> getByStudio(String studio) throws DVDLibraryDaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getAverage() throws DVDLibraryDaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Map<String, DVDRecord> getNewest() throws DVDLibraryDaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Map<String, DVDRecord> getOldest() throws DVDLibraryDaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getAverageNote() throws DVDLibraryDaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
