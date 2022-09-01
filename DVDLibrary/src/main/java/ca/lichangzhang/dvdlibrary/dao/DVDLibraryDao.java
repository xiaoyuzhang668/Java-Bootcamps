package ca.lichangzhang.dvdlibrary.dao;

import ca.lichangzhang.dvdlibrary.dto.DVDRecord;
import java.util.List;
import java.util.Map;

/*
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
*/
public interface DVDLibraryDao {
    
    /**
     * Adds the given DVD to the collection and associates it with the given
     * title.If there is already a DVD record associated with the given  DVD title, 
     * it will return that DVD object, otherwise it will return null.
     *
     * @param title  with which DVD record is to be associated
     * @param dvdRecord DVD record to be added to the collection
     * @return the DVD object previously associated with the given  
     * title if it exists, null otherwise
     * @throws ca.lichangzhang.dvdlibrary.dao.DVDLibraryDaoException
     */
    DVDRecord addRecord(String title, DVDRecord dvdRecord) throws DVDLibraryDaoException;
    
    /**
     * Removes from the collection the DVD associated with the given title.Returns the DVD object that is being removed or 
     * null if there is no DVD associated with the given title
     *
     * @param title title of DVD to be removed
     * @return DVD object that was removed or null if no DVD
     * was associated with the given title
     * @throws ca.lichangzhang.dvdlibrary.dao.DVDLibraryDaoException
     */
    DVDRecord removeRecord(String title) throws DVDLibraryDaoException ;
    
    /**
     * Returns the DVD object associated with the given title.Returns null if no such DVD exists
     *
     * @param title title of the DVD to retrieve
     * @param dvdRecord DVD record to be edited and saved into the collection
     * @return the DVD object associated with the given title that is being edited
     * null if no such student exists
     * @throws ca.lichangzhang.dvdlibrary.dao.DVDLibraryDaoException
     */
    DVDRecord editRecord(String title, DVDRecord dvdRecord) throws DVDLibraryDaoException;
    
    /**
     * Returns a List of all DVD in the collection.
     *
     * @return List containing all DVD in the collection.
     * @throws ca.lichangzhang.dvdlibrary.dao.DVDLibraryDaoException
     */
    List<DVDRecord> getAllDVD() throws DVDLibraryDaoException;

    /**
     * Returns the DVD object associated with the given title.Returns null if no such DVD exists
     *
     * @param title title of the DVD to retrieve
     * @return the DVD object associated with the given title,  
     * null if no such DVD exists
     * @throws ca.lichangzhang.dvdlibrary.dao.DVDLibraryDaoException
     */
    DVDRecord getRecord(String title) throws DVDLibraryDaoException;
    
    /**
     * Returns a List of all DVD with specific released in the last N years in the collection.
     * @param years in the last N years of the DVD to retrieve
     * @return List containing all DVD with specific released in the last N year in the collection.
     * @throws ca.lichangzhang.dvdlibrary.dao.DVDLibraryDaoException
     */
    List<DVDRecord> getByYears(int years) throws DVDLibraryDaoException;
    
      /**
     * Returns a List of all DVD with specific rating in the collection.
     * @param rating of the DVD to retrieve
     *
     * @throws ca.lichangzhang.dvdlibrary.dao.DVDLibraryDaoException
     */
    void getByRating() throws DVDLibraryDaoException;
    
     /**
     * Returns a List of all DVD with specific director in the collection.
     * @param director of the DVD to retrieve
     * @return List containing all DVD with specific director in the collection.
     * @throws ca.lichangzhang.dvdlibrary.dao.DVDLibraryDaoException
     */
    List<DVDRecord> getByDirector(String director) throws DVDLibraryDaoException;
    
     /**
     * Returns a List of all DVD with specific director in the collection.
     * @param studio of the DVD to retrieve
     * @return List containing all DVD with specific director in the collection.
     * @throws ca.lichangzhang.dvdlibrary.dao.DVDLibraryDaoException
     */
    List<DVDRecord> getByStudio(String studio) throws DVDLibraryDaoException;
    
    /**
     * Returns average age of movies in the collection.
     * 
     * @return integer of average age of all DVD in the collection.
     * @throws ca.lichangzhang.dvdlibrary.dao.DVDLibraryDaoException
     */
    int getAverage() throws DVDLibraryDaoException;
    
     /**
     * Returns the newest movie object in the collection.
     * 
     * @return the newest movie object in all DVD in the collection.
     * @throws ca.lichangzhang.dvdlibrary.dao.DVDLibraryDaoException
     */
    Map<String, DVDRecord> getNewest() throws DVDLibraryDaoException;
    
     /**
     * Returns the oldest movie object in the collection.
     * 
     * @return the oldest movie object in all DVD in the collection.
     * @throws ca.lichangzhang.dvdlibrary.dao.DVDLibraryDaoException
     */
    Map<String, DVDRecord> getOldest() throws DVDLibraryDaoException;
    
        /**
     * Returns the average number of notes in the collection.
     * 
     * @return the average number of notes in all DVD in the collection.
     * @throws ca.lichangzhang.dvdlibrary.dao.DVDLibraryDaoException
     */
    int getAverageNote() throws DVDLibraryDaoException; 
}


