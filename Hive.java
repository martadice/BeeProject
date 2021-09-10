import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

// CREATE CLASS LOCATION
public class Hive {
    private int id;
    private int hiveId;
    private String hiveStatus;
    private String hiveType;
    private String hiveNotes;
    private int locationId;
    DbConnection dbConnection = new DbConnection();

    // CREATE NON ARGUMENT CONSTRUCTOR

    public Hive() {
    }


    // CREATE CONSTRUCTOR WITH ARGUMENTS

    public Hive(int id, int hiveId, String hiveStatus, String hiveType, String hiveNotes, int locationId) {
        this.id = id;
        this.hiveId = hiveId;
        this.hiveStatus = hiveStatus;
        this.hiveType = hiveType;
        this.hiveNotes = hiveNotes;
        this.locationId = locationId;
    }
    // CREATE METHOD ADD NEW HIVE
    public static void addNewHive(Scanner scanner, Hive hive, History history, Location location, LocalDateTime
            localDateTime, DateTimeFormatter dateTimeFormatter, Methods methods, DbConnection dbConnection) {
        do {
            System.out.println("Please enter hive id");
            hive.setHiveId(methods.validatorForIntegers(scanner));

            if (hive.getHiveId() == dbConnection.checkHivesNr(hive)) {
                System.out.println("There is hive with nr." + hive.getHiveId() + " already, please enter another one!");
            }
        } while (hive.getHiveId() == dbConnection.checkHivesNr(hive));

        System.out.println("Please enter hive status");
        hive.setHiveStatus(scanner.nextLine());

        System.out.println("Please enter hive type");
        hive.setHiveType(scanner.nextLine());

        System.out.println("Please enter  hive notes");
        hive.setHiveNotes(scanner.nextLine());
// PRINT LOCATION NAME AND LOCATION ID
        location.printLocationNameAndLocationId(location, dbConnection);
        System.out.println("Please enter location Id");
        hive.setLocationId(methods.validatorForIntegers(scanner));
        dbConnection.createHive(hive);
//   ADD INFORMATION TO HISTORY TABLE
        history.setActionDone("Added new hive: nr: " + hive.getHiveId() + ", in location: " + dbConnection.findLocationForAddedHive(location, hive));
        history.setDataAndTime(localDateTime.format(dateTimeFormatter));
        dbConnection.addRecordToHistory(history);
        System.out.println("Added new hive: nr: " + hive.getHiveId() + " ,in location: " + dbConnection.findLocationForAddedHive(location, hive));
    }
    // COUNTING HIVES
    public static void countHive(Hive hive, DbConnection dbConnection) {
        dbConnection.countHives(hive);
    }
    // CREATE METHOD DELETE HIVE
    public static void deleteHive(Scanner scanner, Hive hive, Location location, Colony colony, History
            history, LocalDateTime localDateTime, DateTimeFormatter dateTimeFormatter, Methods methods, DbConnection dbConnection) {
        System.out.println("Please enter hive id you want to delete");
        hive.setHiveId(methods.validatorForIntegers(scanner));
        colony.setHiveId(hive.getHiveId());
//        ADD INFORMATION IN HISTORY DATA BASE
        history.setActionDone("Deleted hive and colony nr:" + hive.getHiveId() + " from location " + dbConnection.findLocationForHive(location, hive));
        history.setDataAndTime(localDateTime.format(dateTimeFormatter));
        dbConnection.addRecordToHistory(history);
//        DELETING HIVE
        dbConnection.deleteHive(hive);
        dbConnection.deleteColony(colony);
        System.out.println("Deleted hive and colony nr:" + hive.getHiveId() + " from location " + dbConnection.findLocationForHive(location, hive));
    }

    // CREATE TO STRING METHOD

    @Override
    public String toString() {
        return "Hive{" +
                "id=" + id +
                ", hiveId=" + hiveId +
                ", hiveStatus='" + hiveStatus + '\'' +
                ", hiveType='" + hiveType + '\'' +
                ", hiveNotes='" + hiveNotes + '\'' +
                ", locationId=" + locationId +
                '}';
    }


//        CREATE GETTERS AND SETTERS


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHiveId() {
        return hiveId;
    }

    public void setHiveId(int hiveId) {
        this.hiveId = hiveId;
    }

    public String getHiveStatus() {
        return hiveStatus;
    }

    public void setHiveStatus(String hiveStatus) {
        this.hiveStatus = hiveStatus;
    }

    public String getHiveType() {
        return hiveType;
    }

    public void setHiveType(String hiveType) {
        this.hiveType = hiveType;
    }

    public String getHiveNotes() {
        return hiveNotes;
    }

    public void setHiveNotes(String hiveNotes) {
        this.hiveNotes = hiveNotes;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
}

