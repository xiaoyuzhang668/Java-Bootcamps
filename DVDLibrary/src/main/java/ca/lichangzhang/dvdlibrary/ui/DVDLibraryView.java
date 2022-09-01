package ca.lichangzhang.dvdlibrary.ui;

import ca.lichangzhang.dvdlibrary.dto.DVDRecord;
import java.io.IOException;
import java.util.HashMap;
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
public class DVDLibraryView {

    private UserIO io;
    private Map<String, DVDRecord> dvdRecords = new HashMap<>();

    public DVDLibraryView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("============================================================");
        io.print("Main Menu");
        io.print("Please select the opeation you wish to perform: ");
        io.print("============================================================");
        io.print("1. List All DVD");
        io.print("2. Add DVD to Collection");
        io.print("3. Remove DVD from Collection");
        io.print("4. Edit DVD information");
        io.print("5. Find a Specific DVD");
        io.print("6. Find all movies released in the last N years");
        io.print("7. Find all the movies with a given MPAA rating");
        io.print("8. Find all the movies by a given director");
        io.print("9. Find all the movies released by a particular studio");
        io.print("10. Find the average age of the movies in the collection");
        io.print("11. Find the newest movie in your collection");
        io.print("12. Find the oldest movie in your collection");
        io.print("13. Find the average number of notes associated with movies in your collection");

        io.print("14. Exit");
        io.print("============================================================");
        io.print("");

        return io.readInt("Please select from the above choices, from 1 to 14 inclusive: ", 1, 14);
    }

    //Allow the user to add, edit, or delete many DVDs in one session
    public String needMore() {
        return io.readString("Continue with same action/session?  Answer yes or no: ").toLowerCase();
    }

    // add DVD menu 
    public DVDRecord addRecordInfo() {
        String title = io.readString("Please enter the DVD title: ");
        String releaseDate = io.readString("Please enter the DVD release date: ");
        String mpaaRating = io.readString("Please enter the DVD MPAA rating: ");
        String directorName = io.readString("Please enter the DVD director's name: ");
        String studio = io.readString("Please enter the DVD studio: ");
        String userNote = io.readString("Please enter the DVD user note if there is any: ");
        io.print("");
        DVDRecord currentDVD = new DVDRecord(title);

        currentDVD.setReleaseDate(releaseDate);
        currentDVD.setMpaaRating(mpaaRating);
        currentDVD.setDirectorName(directorName);
        currentDVD.setStudio(studio);
        currentDVD.setUserNote(userNote);
        //create and return a DVDRecord object
        return currentDVD;
    }

    //add DVD banner
    public void displayAddRecord() {
        io.print("=== Add DVD Menu ===");
    }

    public void displayExisting() {
        io.print("DVD with same title exists in the system already.");
        io.print("DVD record was not re-created.");
        io.print("============================================================");
        io.readString("Please hit enter to continue.");
    }

    public void displayAddSuccess() {
        io.print("DVD has been successfully added. ");
        io.print("============================================================");
        io.readString("Please hit enter to continue.");
    }

    //display all DVD
    public void displayAll() {
        io.print("=== Display All DVD ===");
    }

    public void getAll(List<DVDRecord> dvdList) {
        for (DVDRecord currentDVD : dvdList) {
            String dvdInfo = String.format("#%s : %s, %s,  %s, %s, %s",
                    currentDVD.getTitle(),
                    currentDVD.getReleaseDate(),
                    currentDVD.getMpaaRating(),
                    currentDVD.getDirectorName(),
                    currentDVD.getStudio(),
                    currentDVD.getUserNote());
            io.print(dvdInfo);
        }
        io.print("");
        io.readString("Please hit enter to continue.");
    }

    //display specific DVD
    public void displayGetRecord() {
        io.print("=== Find DVD Menu ===");
    }

    public String getTitle() {
        return io.readString("Please enter the DVD title you want to find and perform operation:");
    }

    public void getRecordInfo(DVDRecord dvdRecord) {

        if (dvdRecord != null) {
            String dvdInfo = String.format("#%s : %s, %s,  %s, %s, %s",
                    dvdRecord.getTitle(),
                    dvdRecord.getReleaseDate(),
                    dvdRecord.getMpaaRating(),
                    dvdRecord.getDirectorName(),
                    dvdRecord.getStudio(),
                    dvdRecord.getUserNote());
            io.print(dvdInfo);
            io.print("");
        } else {
            io.print("The DVD you want to find does not exist. No such DVD with this title.");
            io.print("============================================================");
            io.print("");
        }
        io.readString("Please hit enter to continue.");
    }

    //remove DVD
    public void displayRemoveRecord() {
        io.print("=== Remove DVD Menu ===");
    }

    public void removeRecordInfo(DVDRecord dvdRecord) {
        if (dvdRecord != null) {
            io.print("DVD record has been successfully removed.");
            io.print("============================================================");
            io.print("");
        } else {
            io.print("The DVD you want to remove does not exist.  No such DVD with this title.");
            io.print("============================================================");
            io.print("");
        }
        io.readString("Please hit enter to continue.");
    }

    //edit record
    public void displayEditRecord() {
        io.print("=== Edit DVD Menu ===");
    }

    public DVDRecord editRecordInfo(DVDRecord dvdRecord) {
        if (dvdRecord != null) {
            //prompt user if they want to update release date field
            if ((io.readString("Would you like to edit release date? Answer Yes or No.")).toLowerCase().equals("yes")) {
                String releaseDate = io.readString("Update Release Date from \"" + dvdRecord.getReleaseDate() + "\" to:");
                dvdRecord.setReleaseDate(releaseDate);
            }
            //prompt user if they want to update rating field
            if ((io.readString("Would you like to edit MPAA rating? Answer Yes or No.")).toLowerCase().equals("yes")) {
                String mpaaRating = io.readString("Update MPAA Rating from \"" + dvdRecord.getMpaaRating() + "\" to:");
                dvdRecord.setMpaaRating(mpaaRating);
            }
            //prompt user if they want to update director name field
            if ((io.readString("Would you like to edit director name? Answer Yes or No.")).toLowerCase().equals("yes")) {
                String directorName = io.readString("Update Director Name from \"" + dvdRecord.getDirectorName() + "\" to");
                dvdRecord.setDirectorName(directorName);
            }
            //prompt user if they want to update studio field
            if ((io.readString("Would you like to edit studio? Answer Yes or No.")).toLowerCase().equals("yes")) {
                String studio = io.readString("Update Studio from \"" + dvdRecord.getStudio() + "\" to:");
                dvdRecord.setStudio(studio);
            }
            //prompt user if they want to update user note field
            if ((io.readString("Would you like to edit user note? Answer Yes or No.")).toLowerCase().equals("yes")) {
                String userNote = io.readString("Update User Note from \"" + dvdRecord.getUserNote().trim() + "\" to:");
                dvdRecord.setUserNote(userNote);
            }

            io.print("DVD record has been successfully updated.");
            io.print("============================================================");
            io.print("");
        } else {
            io.print("The DVD you want to edit does not exist.  No such DVD with this title.");
            io.print("============================================================");
            io.print("");
        }
        io.readString("Please hit enter to continue.");
        return dvdRecord;
    }

    //if blank, leave default
    //display exit message
    public void displayExit() {
        io.print("Good Bye!!");
    }

    //display unknown command
    public void displayUnknown() {
        io.print("Unknown Command!!");
    }

    public void displayError(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
    
    //method to   prompt user enter to continue
    public void promptEnterKey() {
        System.out.println("========================================================================================");
        System.out.println("Please hit enter to continue");
        try {
            System.in.read();
        } catch (IOException e) {
        }
    }
}
