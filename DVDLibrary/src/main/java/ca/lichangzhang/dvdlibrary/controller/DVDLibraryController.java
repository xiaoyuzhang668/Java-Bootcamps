package ca.lichangzhang.dvdlibrary.controller;

import ca.lichangzhang.dvdlibrary.dao.DVDLibraryDao;
import ca.lichangzhang.dvdlibrary.dao.DVDLibraryDaoException;
import ca.lichangzhang.dvdlibrary.dto.DVDRecord;
import ca.lichangzhang.dvdlibrary.ui.DVDLibraryView;
import java.util.List;

/*
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
*/
public class DVDLibraryController {
    
    private DVDLibraryView view; 
    private DVDLibraryDao dao;
    
    public DVDLibraryController(DVDLibraryDao dao, DVDLibraryView view){
        this.dao = dao;
        this.view = view;
    }
    
    public void run(){
        boolean keepGoing = true;
        int menuSelection = 0;
              
        try{
        while (keepGoing){    
            
            menuSelection = getMenuSelection();
            
            switch(menuSelection){
                case 1: 
                    getAll();
                    break;
                case 2: 
                    do  {
                    addRecord(); 
                    } while (view.needMore().equals("yes"));
                    break;
                case 3: 
                    do  {
                    removeRecord();
                     } while (view.needMore().equals("yes"));
                    break;
                case 4: 
                    do  {
                    editRecord();
                     } while (view.needMore().equals("yes"));
                    break;
                case 5: 
                    getRecord();
                    break; 
                case 7:
                    getByRating();
                    promptEnterKey();
                    break;
                case 14:
                    keepGoing = false;
                    break;
                default: 
                   displayUnknown();
            }   
        }
        displayExit();
    } catch (DVDLibraryDaoException e) {
            view.displayError(e.getMessage());
    }
  }  
    
    // get menu selection from daoview class, called from controller
    private int getMenuSelection(){
        return view.printMenuAndGetSelection();
    }
    
    //add DVD record
    private void addRecord() throws DVDLibraryDaoException{
        view.displayAddRecord();
        DVDRecord newDVD = view.addRecordInfo();   
        // if object does not exist in the system, then add new record.
        if ((dao.addRecord(newDVD.getTitle(), newDVD)) != null){
             view.displayAddSuccess();
        } else {
             view.displayExisting();
        }    
    }          
           
    //display all dvd
    private void getAll() throws DVDLibraryDaoException{
        view.displayAll();
        List<DVDRecord> dvdList = dao.getAllDVD();
        view.getAll(dvdList);
    }
    
    //get specific DVD record
    private void getRecord() throws DVDLibraryDaoException{
        view.displayGetRecord();
        String title = view.getTitle();
        DVDRecord dvdRecord = dao.getRecord(title);
        view.getRecordInfo(dvdRecord);
    }
    
    //remove specific DVD record
    private void removeRecord() throws DVDLibraryDaoException{
        view.displayRemoveRecord();
        String title = view.getTitle();
        DVDRecord removedDVD = dao.removeRecord(title);
        view.removeRecordInfo(removedDVD);        
    }   
    
    //edit DVD record
    private void editRecord() throws DVDLibraryDaoException{
        view.displayEditRecord();
        String title = view.getTitle();
        DVDRecord changeDVD = dao.getRecord(title);
        view.editRecordInfo(changeDVD); 
        dao.editRecord(title, changeDVD);        
    }
    
    //private getByRating
    private void getByRating() throws DVDLibraryDaoException {
     dao.getByRating();
    }
    
    //display   exit message
    private void displayExit() {
        view.displayExit();
    }
    
    //display unknown command
    private void displayUnknown(){
        view.displayUnknown();
    }
    
    //press enter to continue
    private void promptEnterKey() {
        view.promptEnterKey();
    }
}
