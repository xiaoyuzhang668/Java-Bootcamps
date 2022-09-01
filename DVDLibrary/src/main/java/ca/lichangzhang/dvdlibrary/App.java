package ca.lichangzhang.dvdlibrary;

import ca.lichangzhang.dvdlibrary.controller.DVDLibraryController;
import ca.lichangzhang.dvdlibrary.dao.DVDLibraryDao;
import ca.lichangzhang.dvdlibrary.dao.DVDLibraryDaoFileImpl;
import ca.lichangzhang.dvdlibrary.ui.DVDLibraryView;
import ca.lichangzhang.dvdlibrary.ui.UserIO;
import ca.lichangzhang.dvdlibrary.ui.UserIOImpl;

/*
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
*/
public class App {
    
    public static void main(String[] args) {
        
        UserIO myIo = new UserIOImpl();
        DVDLibraryView myView = new DVDLibraryView(myIo);
        DVDLibraryDao myDao = new DVDLibraryDaoFileImpl();             
        
      DVDLibraryController controller = new DVDLibraryController(myDao, myView);
      controller.run();
    }

}
