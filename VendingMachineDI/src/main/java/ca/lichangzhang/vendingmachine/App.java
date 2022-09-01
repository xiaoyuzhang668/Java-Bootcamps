package ca.lichangzhang.vendingmachine;

import ca.lichangzhang.vendingmachine.controller.VendingMachineController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author catzh Name: Li Chang Zhang Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 */
public class App {

    public static void main(String[] args)  {
//        Instantiate the UserIO implementation
//        UserIO myIo = new UserIOImpl();
//        //instantiate the view and wire the UserIO implementation into view instantiation
//        VendingMachineView myView = new VendingMachineView(myIo);
//
//        //instantiate dao
//        VendingMachineDao myDao = new VendingMachineDaoFileImpl();
//        //instantiate audit dao
//        VendingMachineAuditDao myAuditDao = new VendingMachineAuditDaoFileImpl();
//        
//        //instantiate service layer and wire the dao an audit dao into service instantiation
//        VendingMachineService myService = new VendingMachineServiceImpl(myDao, myAuditDao);
//
//        //instantiate controller and wire the service layer into controller instantiation
//        VendingMachineController controller = new VendingMachineController(myService, myView);
//        //kick off controller
//        controller.run();

        ApplicationContext appContext 
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        VendingMachineController controller
                = appContext.getBean("controller", VendingMachineController.class);

        controller.run();
    }
}
