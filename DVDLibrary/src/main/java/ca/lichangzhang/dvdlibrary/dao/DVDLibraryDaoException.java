package ca.lichangzhang.dvdlibrary.dao;

/*
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
*/
public class DVDLibraryDaoException extends Exception {
    
    public DVDLibraryDaoException (String message){
        super(message);
    }
    
    public DVDLibraryDaoException (String message, Throwable cause){
        super(message, cause);
    }

}
