import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

// CREATE CLASS LOCATION
public class Location {
    private int locationId;
    private String locationName;
    private String locationAddress;
    private String locationNote;
    DbConnection dbConnection = new DbConnection();
    // CREATE NON ARGUMENT CONSTRUCTOR
    public Location() {
    }

    // CREATE CONSTRUCTOR WITH ARGUMENTS
    public Location(int locationId, String locationName, String locationAddress, String locationNote) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.locationAddress = locationAddress;
        this.locationName = locationName;
    }
    // CREATE METHOD ADD NEW LOCATION
    public static void addNewLocation(Scanner scanner, Location location, History history, LocalDateTime
            localDateTime, DateTimeFormatter dateTimeFormatter, DbConnection dbConnection) {
        System.out.println("Please enter location name");
        location.setLocationName(scanner.nextLine());

        System.out.println("Please enter location address");
        location.setLocationAddress(scanner.nextLine());

        System.out.println("Please enter location note");
        location.setLocationNote(scanner.nextLine());

//        CREATE LOCATION
        dbConnection.createLocation(location);
//        ADD RECORD TO HISTORY TABLE
        history.setActionDone("Added new location " + location.getLocationName());
        history.setDataAndTime(localDateTime.format(dateTimeFormatter));
        dbConnection.addRecordToHistory(history);

    }
    //    COUNT POLLEN IN LOCATIONS
    public static void countPollenInLocation(Location location, Hive hive, Colony colony, DbConnection dbConnection) {
        dbConnection.countPollenInLocations(location, hive, colony);
    }
    //    PRINT LOCATION NAME AND LOCATION ID FOR ADDING HIVE
    public static void printLocationNameAndLocationId(Location location, DbConnection dbConnection) {
        dbConnection.printLocationNameAndLocationId(location);
    }
    //    COUNT HONEY IN LOCATIONS
    public static void countHoneyInLocation(Location location, Hive hive, Colony colony, DbConnection dbConnection) {
        dbConnection.countHoneyInLocations(location, hive, colony);
    }
    //HOW MANY HIVES IN LOCATIONS
    public static void hivesInLocations(Hive hive, Location location, DbConnection dbConnection) {
        dbConnection.hivesInLocations(hive, location);
    }
    // CREATE METHOD DELETE LOCATION
    public static void deleteLocation(Scanner scanner, Location location, Hive hive, Colony colony, History
            history, LocalDateTime localDateTime, DateTimeFormatter dateTimeFormatter, DbConnection dbConnection) {
        System.out.println("Please enter location you want to delete");
        location.setLocationName(scanner.nextLine());

        //        DELETE HIVES TOGETHER WITH LOCATION WITH FOREACH LOOP
        for (int eatchvariable : dbConnection.findHivesToDelete(location)
        ) {
            hive.setHiveId(eatchvariable);
            colony.setHiveId(eatchvariable);
            dbConnection.deleteHive(hive);
            dbConnection.deleteColony(colony);
//      ADD INFORMATION ABOUT DELETED HIVES IN HISTORY TABLE IN LOOP
            history.setActionDone("Hive and colony deleted together with location " + location.getLocationName() + " is: " + eatchvariable);
            history.setDataAndTime(localDateTime.format(dateTimeFormatter));
            dbConnection.addRecordToHistory(history);
        }
// ADD INFORMATION ABOUT DELETED LOCATION IN HISTORY TABLE
        history.setActionDone("Location deleted " + location.getLocationName());
        history.setDataAndTime(localDateTime.format(dateTimeFormatter));
        dbConnection.addRecordToHistory(history);
//        DELETE LOCATION
        dbConnection.deleteLocation(location);
    }

    //CREATE TO STRING METHOD
    @Override
    public String toString() {
        return "Location{" +
                "locationId=" + locationId +
                ", locationName='" + locationName + '\'' +
                ", locationAddress='" + locationAddress + '\'' +
                ", locationNote='" + locationNote + '\'' +
                '}';
    }

    // CREATE SETTERS AND GETTERS
    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getLocationNote() {
        return locationNote;
    }

    public void setLocationNote(String locationNote) {
        this.locationNote = locationNote;
    }
}


