import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class DbConnection {

    private Connection conn;

    private Statement statement;

    private String sqlStatement;

    public DbConnection() {

        try {
            String dbUrl = "jdbc:sqlite:bee_project.db";
            conn = DriverManager.getConnection(dbUrl);
            if (conn != null) {
                DatabaseMetaData databaseMetaData = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Connection to" + databaseMetaData.getDatabaseProductName() + " " + databaseMetaData.getDatabaseProductVersion());
                statement = conn.createStatement();
                //          Create table colony
                statement = conn.createStatement();
                sqlStatement = "CREATE TABLE IF NOT EXISTS colonies" +
                        " (id INTEGER PRIMARY KEY NOT NULL, " +
                        "hive_id INTEGER NOT NULL, " +
                        "colony_origin TEXT NOT NULL, " +
                        "queen_breed TEXT NOT NULL, " +
                        "queen_year INTEGER NOT NULL, " +
                        "supers INTEGER NOT NULL, " +
                        "frames INTEGER NOT NULL, " +
                        "bees INTEGER NOT NULL, " +
                        "brood INTEGER NOT NULL, " +
                        "honey INTEGER NOT NULL, " +
                        "pollen INTEGER NOT NULL, " +
                        "varroa_treatment TEXT NOT NULL, " +
                        "food_added INTEGER NOT NULL, " +
                        "next_visit TEXT NOT NULL" +
                        ")";
                statement.execute(sqlStatement);
//                CREATE TABLE LOCATIONS
                sqlStatement = "CREATE TABlE IF NOT EXISTS locations" +
                        "(location_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "location_name TEXT," +
                        "location_address TEXT," +
                        "location_notes TEXT )";
                statement.execute(sqlStatement);
//                CREATE TABLE HIVES
                sqlStatement =
                        "CREATE TABLE IF NOT EXISTS hives" +
                                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "hive_id INTEGER UNIQUE," +
                                "hive_status TEXT," +
                                "hive_type TEXT," +
                                "hive_notes TEXT," +
                                "location_id INTEGER )";
//                "hive_notes TEXT )";
//                                "location_id_for_hive INTEGER FOREIGN KEY REFERENCES locations(location_id))";
//                                "FOREIGN KEY (location_id_for_hive) REFERENCES locations(location_id))";

                statement.execute(sqlStatement);
            }
            //        CREATE TABLE HISTORY
            sqlStatement = "CREATE TABLE IF NOT EXISTS histories" +
                    " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " action_done TEXT, " +
                    " date_and_time TEXT, " +
                    " unit TEXT )";
            statement.execute(sqlStatement);
        } catch (SQLException exception) {
            System.out.println("Database error" + exception);
        }
    }

    //    CREATE METHOD CREATE LOCATION
//
    public void createLocation(Location location) {
        try {
            sqlStatement = "INSERT INTO locations (location_name,location_address, location_notes) " +
                    "VALUES (" +
                    "'" + location.getLocationName() + "'," +
                    "'" + location.getLocationAddress() + "'," +
                    "'" + location.getLocationNote() + "')";
            statement.execute(sqlStatement);

        } catch (SQLException exception) {
            System.out.println("Error inserting into  locations table " + exception);
        }
    }

    //    CREATE METHOD CREATE HIVE
    public void createHive(Hive hive) {
        try {
            sqlStatement = "INSERT INTO hives" +
                    " (hive_id, hive_status, hive_type, hive_notes, Location_id)" +
                    " VALUES (" +
                    "'" + hive.getHiveId() + "'," +
                    "'" + hive.getHiveStatus() + "'," +
                    "'" + hive.getHiveType() + "'," +
                    "'" + hive.getHiveNotes() + "'," +
                    "'" + hive.getLocationId() + "')";

            statement.execute(sqlStatement);

        } catch (SQLException exception) {
            System.out.println("Error inserting into hives table " + exception);
        }
    }

    //    FIND OUT IF THERE IS HIVE WITH SUCH NUMBER ALREADY
    public int checkHivesNr(Hive hive) {
        int dublicateHiveId = 0;
        try {
            sqlStatement = "SELECT hive_id FROM hives WHERE hive_id = " + hive.getHiveId() + "";
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            while (resultSet.next()) {
                dublicateHiveId = resultSet.getInt("hive_id");

            }

        } catch (SQLException exception) {
            System.out.println("Error checking hives nr " + exception);
        }
        return dublicateHiveId;

    }

//    FIND LOCATION FOR ADDED HIVE FOR RECORD IN HISTORY TABLE

    public String findLocationForAddedHive(Location location, Hive hive) {
        String returnLocationForAddedHive = "";
        try {
            sqlStatement = "SELECT location_name, hive_id" +
                    " From locations " +
                    "JOIN hives " +
                    "ON hives.location_id = locations.location_id";
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            while (resultSet.next()) {
                String locationName = resultSet.getString("location_name");
                int hiveID = resultSet.getInt("hive_id");
                if (hiveID == hive.getHiveId()) {
                    returnLocationForAddedHive = locationName;
                }
            }

        } catch (SQLException exception) {
            System.out.println("Error when finding location for added hive" + exception);
        }
        return returnLocationForAddedHive;
    }


    //    CREATE METHOD DELETE LOCATION
    public void deleteLocation(Location location) {

        try {
            sqlStatement = "DELETE FROM locations WHERE location_name = '" + location.getLocationName() + "'";
            statement.execute(sqlStatement);
        } catch (SQLException exception) {
            System.out.println("Error inserting into  locations table " + exception);
        }
    }

    //    CREATE METHOD DELETE HIVE
    public void deleteHive(Hive hive) {
        try {
            sqlStatement = "DELETE FROM hives WHERE hive_id = '" + hive.getHiveId() + "'";
            statement.execute(sqlStatement);
        } catch (SQLException exception) {
            System.out.println("Error inserting into  locations table " + exception);
        }
    }

    //    CREATE METHOD DELETE COLONY
    public void deleteColony(Colony colony) {
        try {
            sqlStatement = "DELETE FROM colonies WHERE hive_id = '" + colony.getHiveId() + "'";
            statement.execute(sqlStatement);

        } catch (SQLException exception) {
            System.out.println("Error deleting Colony " + exception);
        }

    }
    // PRINT OUT LOCATIONS

    public void printLocation(Location location) {
        try {
            sqlStatement = "SELECT location_name FROM locations";
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            while (resultSet.next()) {
                String locationName = resultSet.getString("location_name");
                System.out.println(" Locations: " + locationName);
            }
        } catch (SQLException exception) {
            System.out.println("Error when printing locations " + exception);
        }
    }

    //    HOW MANY HIVES THERE ARE
    public void countHives(Hive hive) {
        try {

            sqlStatement = "SELECT COUNT (hive_id) FROM hives";
            ResultSet resultSet = statement.executeQuery(sqlStatement);

            int hiveCount = resultSet.getInt("COUNT (hive_id)");

            System.out.println("There are " + hiveCount + " bee hives in total.");

        } catch (SQLException exception) {
            System.out.println("Error counting hives" + exception);
        }
    }

    //    TOTAL HONEY AND POLLEN (KG)
    public void sumOfHoneyAndPollen(Colony colony) {
        try {
            sqlStatement = "SELECT SUM (honey), SUM (pollen) FROM colonies";
            ResultSet resultSet = statement.executeQuery(sqlStatement);

            int honeySum = resultSet.getInt("SUM (honey)");
            int pollenSum = resultSet.getInt("SUM (pollen)");
            System.out.println("There are " + honeySum + " kg of honey and there are " + pollenSum + " kg pollen in hives in total.");

        } catch (Exception exception) {
            System.out.println("Error in sum of honey " + exception);
        }

    }

    //THREE STRONGEST COLONIES BY HONEY
    public void strongestColoniesByHoney(Colony colony, Hive hive, Location location) {
        try {
            sqlStatement = "SELECT*FROM colonies " +
                    " JOIN hives" +
                    " ON colonies.hive_id=hives.hive_id" +
                    " JOIN locations" +
                    " ON hives.location_id = locations.location_id " +
                    " ORDER BY honey DESC LIMIT 3";
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            System.out.println("The strongest 3 hives by honey are: ");
            while (resultSet.next()) {
                int hiveID = resultSet.getInt("hive_id");
                int kgHoney = resultSet.getInt("honey");
                String locationName = resultSet.getString("location_name");

                System.out.println("Hive nr. " + hiveID + ", with " + kgHoney + " kg of honey (location: " + locationName + ")");

            }

        } catch (Exception exception) {
            System.out.println("Error in strongest colonies by honey " + exception);
        }
    }

    //HOW MANY COLONIES AR TREATED WITH VARROA TREATMENT,SORT BY COUNT
    public void varroaTreatmentCount(Colony colony) {

        try {
            sqlStatement = "SELECT COUNT (hive_id), varroa_treatment FROM colonies" +
                    " GROUP BY varroa_treatment  " +
                    "ORDER BY COUNT(hive_id) DESC";

            ResultSet resultSet = statement.executeQuery(sqlStatement);
            while (resultSet.next()) {
                int varCount = resultSet.getInt("COUNT (hive_id)");
                String varroaTreatment = resultSet.getString("varroa_treatment");
                System.out.println("There are " + varCount + " hives treated with treatment " + varroaTreatment);
            }

        } catch (Exception exception) {
            System.out.println("There is error in varroa treatment count: " + exception);

        }
    }

    //    ARRAYLIST FROM VARROA TREATMENT
    public ArrayList<String> getvarroaTreatment() {
        ArrayList<String> varroaTreatementList = new ArrayList<String>();
        try {
            sqlStatement = "SELECT varroa_treatment FROM colonies";
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            while (resultSet.next()) {
                varroaTreatementList.add(resultSet.getString("varroa_treatment"));
            }
        } catch (SQLException exception) {
            System.out.println("There is error in varroa treatment list " + exception);
        }
        return varroaTreatementList;
    }

    //    COUNT HOW MANY HIVES ARE IN EACH LOCATION
    public void hivesInLocations(Hive hive, Location location) {

        try {
            sqlStatement = "SELECT locations.location_name, COUNT(hives.hive_id) AS numberOfHives FROM locations" +
                    " LEFT JOIN hives ON locations.location_id= hives.location_id " +
                    "GROUP BY location_name " +
                    "ORDER BY numberOfHives DESC";
            ResultSet resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
                String locationName = resultSet.getString("location_name");
                int numberOfHives = resultSet.getInt("numberOfHives");
                System.out.println("There are " + numberOfHives + " hives in location " + locationName);
            }

        } catch (SQLException exception) {
            System.out.println("There is error in hives in Locations: " + exception);
        }
    }

    // COUNT HOW MUCH HONEY IS IN EACH LOCATION
    public void countHoneyInLocations(Location location, Hive hive, Colony colony) {
        try {
            sqlStatement = "SELECT  SUM( colonies.honey),locations.location_name FROM colonies " +
                    "JOIN hives " +
                    "ON colonies.hive_id = hives.hive_id " +
                    "JOIN locations " +
                    "ON hives.location_id = locations.location_id " +
                    "GROUP BY locations.location_name " +
                    "ORDER BY SUM(colonies.honey)DESC";
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            while (resultSet.next()) {
                String locationName = resultSet.getString("location_name");
                int sumOfHoney = resultSet.getInt("SUM( colonies.honey)");
                System.out.println("There are " + sumOfHoney + " kg of honey in location " + locationName);
            }

        } catch (SQLException exception) {
            System.out.println("Error in counting honey in location: " + exception);
        }

    }

    // PRINT OUT INFORMATION ABOUT COLONIES
    public void coloniesInfo(Colony colony) {
        try {
            sqlStatement = "SELECT*FROM colonies WHERE hive_id = '" + colony.getHiveId() + "'";
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            System.out.println("Information about selected colony:");
            while (resultSet.next()) {
                int hiveId = resultSet.getInt("hive_id");
                String colonyOrigin = resultSet.getString("colony_origin");
                String queenBreed = resultSet.getString("queen_breed");
                int queenYear = resultSet.getInt("queen_year");
                int supers = resultSet.getInt("supers");
                int frames = resultSet.getInt("frames");
                int bees = resultSet.getInt("bees");
                int brood = resultSet.getInt("brood");
                int honey = resultSet.getInt("honey");
                int pollen = resultSet.getInt("pollen");
                String varroaTreatment = resultSet.getString("varroa_treatment");
                String foodAdded = resultSet.getString("food_added");
                String nextVisist = resultSet.getString("next_visit");
                System.out.println("Hive id is " + hiveId);
                System.out.println("Colony origin is " + colonyOrigin);
                System.out.println("Queen breed is " + queenBreed);
                System.out.println("Queen year is " + queenYear);
                System.out.println("There are " + supers + " supers");
                System.out.println("There are " + frames + " frames");
                System.out.println("There are " + bees + " bees");
                System.out.println("There are " + brood + " brood");
                System.out.println("There is " + honey + " kg honey");
                System.out.println("There are " + pollen + " frames of pollen");
                System.out.println("The colony was treated with " + varroaTreatment + " varroa treatement");
                System.out.println("The food was added on: " + foodAdded);
                System.out.println("The next visit is on: " + nextVisist);
            }

        } catch (SQLException exception) {
            System.out.println("There is error in printing info about colonies" + exception);
        }
    }

    //  ADD RECORD TO HISTORY TABLE
    public void addRecordToHistory(History history) {
        try {
            sqlStatement = "INSERT INTO histories (action_done, date_and_time) VALUES " +
                    "('" + history.getActionDone() + "'," +
                    "'" + history.getDataAndTime() + "')";
            statement.execute(sqlStatement);
        } catch (SQLException exception) {
            System.out.println("Error when adding record to history table " + exception);
        }
    }

    public ArrayList<Integer> findHivesToDelete(Location location) {
        ArrayList<Integer> hivesList = new ArrayList<Integer>();
        try {
            sqlStatement = "SELECT* FROM hives" +
                    " JOIN locations" +
                    " ON locations.location_id = hives.location_id " +
                    " WHERE locations.location_name='" + location.getLocationName() + "'";
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            while (resultSet.next()) {
                hivesList.add(resultSet.getInt("hive_id"));
            }
        } catch (SQLException exception) {
            System.out.println("Error finding hives to delete" + exception);
        }
        return hivesList;
    }

    //    FIND LOCATION FOR DELETED TABLE
    public String findLocationForHive(Location location, Hive hive) {
        String returnloaction = "";
        try {
//        SELECT  hives.hive_id, location_name FROM hives
//JOIN locations
//ON hives.location_id= locations.location_id

            sqlStatement = ("SELECT  hives.hive_id, location_name FROM hives" +
                    " JOIN locations " +
                    "ON hives.location_id= locations.location_id");
            ResultSet resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {

                int hiveID = resultSet.getInt("hive_id");
                String locationName = resultSet.getString("location_name");
//                System.out.println(hiveID);
//                System.out.println(locationName);

                if (hiveID == hive.getHiveId()) {
//                    System.out.println("hive id" + hiveID);
//                    System.out.println("Location name " + locationName);
                    returnloaction = locationName;
//                    System.out.println("Return location is " + returnloaction);
                }


            }
//            System.out.println("Return location out of while" + returnloaction);

        } catch (SQLException exception) {
            System.out.println("Error finding locations for deleted hives" + exception);
        }
        return returnloaction;

    }

    //    GET INFORMATION FROM HISTORY TABLE ABOUT SPECIFIC DATA
    public void getInformationFromHistoryTable(History history) {
        try {
            sqlStatement = "Select * FROM histories" +
                    " WHERE date_and_time LIKE '%" + history.getDataAndTime() + "%'";
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String actionDone = resultSet.getString("action_done");
                String dataAndTime = resultSet.getString("date_and_time");
                String units = resultSet.getString("unit");
                System.out.println("Id: " + id + ", Action done: " + actionDone + ", Date and time: " + dataAndTime + ", Units: " + units);
            }

        } catch (SQLException exception) {
            System.out.println("Error in getting information from history table about specific data " + exception);
        }
    }

    // GET INFORMATION FROM HISTORY TABLE ABOUT LAST VISIT
    public String getInfoAboutLastVisit(History history) {
        String lastVisit2 = "";
//    Select*FROM histories
//    ORDER BY date_and_time DESC
//    LIMIT 1

        try {
            sqlStatement = " Select*FROM histories ORDER BY date_and_time DESC LIMIT 1";

            ResultSet resultSet = statement.executeQuery(sqlStatement);

            String lastVisit = resultSet.getString("date_and_time");
            char[] lastVisitArray = new char[10];
            for (int i = 0; i < lastVisitArray.length; i++) {
                lastVisitArray[i] = lastVisit.charAt(i);
            }
            lastVisit2 = new String(lastVisitArray);


        } catch (
                SQLException exception) {
            System.out.println("Error with getting information about last visit" + exception);

        }
        return lastVisit2;

    }

// GET ALL ACTIONS YOU HAVE DONE IN LAST VISIT

    public void allActionsInLastVisit(History history) {

        try {
            sqlStatement = "Select*FROM histories WHERE date_and_time LIKE '%" + getInfoAboutLastVisit(history) + "%'";

            ResultSet resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String actionDone = resultSet.getString("action_done");
                String dataAndTime = resultSet.getString("date_and_time");
                String units = resultSet.getString("unit");
                System.out.println("Id: " + id + ", Action done: " + actionDone + ", Date and time: " + dataAndTime + ", Units: " + units);

            }

            //Select*FROM histories
            //WHERE date_and_time LIKE '%06-09-2021%'

        } catch (SQLException exception) {
            System.out.println("Error in all actions in last visit " + exception);

        }
    }


    //    METHOD TO CREATE COLONY
    public void createColony(Colony colony) {
        try {
            sqlStatement = "INSERT INTO colonies (hive_id,colony_origin,queen_breed,queen_year," +
                    "supers,frames, bees,brood, honey,pollen,varroa_treatment, food_added,next_visit )" +
                    "VALUES ( '" + colony.getHiveId() + "','" + colony.getColonyOrigin() + "', " +
                    "'" + colony.getQueenBreed() + "', '" + colony.getQueenYear() + "'," +
                    "'" + colony.getNumberOfSupers() + "', '" + colony.getNumberOfFrames() + "'," +
                    "'" + colony.getNumberOfBees() + "', '" + colony.getNumberOfBrood() + "'," +
                    "'" + colony.getKgHoney() + "', '" + colony.getPollen() + "'," +
                    "'" + colony.getVarroaTreatment() + "', '" + colony.getFoodAdded() + "' )";
            statement.execute(sqlStatement);

        } catch (SQLException exception) {
            System.out.println("Error when creating colony" + exception);
        }
    }

    //FIND OUT IF THERE IS COLONY WITH SUCH HIVE ID ALREADY
    public int thereIsColonyWithThisHiveNr(Colony colony) {
        int hiveIDForColony = Integer.MAX_VALUE;
        try {
            sqlStatement = "SELECT hive_id from colonies Where hive_id= '" + colony.getHiveId() + "'";
            ResultSet resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
                int hiveID = resultSet.getInt("hive_id");
                hiveIDForColony = hiveID;
            }

        } catch (SQLException exception) {
            System.out.println("Error finding out if there is colony with this hive id: " + exception);
        }
        return hiveIDForColony;
    }

    // FIND OUT IF THERE IS HIVE WITH THIS NUMBER
    public int isThereHiveWithThisNr(Hive hive, Colony colony) {

        int thereIsHive = Integer.MAX_VALUE;
        int hiveID = colony.getHiveId();

        try {
            sqlStatement = "SELECT hive_id FROM hives Where hive_id= '" + hiveID + "'";
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            while (resultSet.next()) {
                int hiveID2 = resultSet.getInt("hive_id");
                thereIsHive = hiveID2;
            }

        } catch (SQLException exception) {
            System.out.println("Error in if there is hive with this number: " + exception);
        }
        return thereIsHive;
    }

    // COUNT HOW MUCH POLLEN IS IN EACH LOCATION
    public void countPollenInLocations(Location location, Hive hive, Colony colony) {
        try {
            sqlStatement = "SELECT  SUM( colonies.pollen),locations.location_name FROM colonies " +
                    "JOIN hives " +
                    "ON colonies.hive_id = hives.hive_id " +
                    "JOIN locations " +
                    "ON hives.location_id = locations.location_id " +
                    "GROUP BY locations.location_name " +
                    "ORDER BY SUM(colonies.honey)DESC";
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            while (resultSet.next()) {
                String locationName = resultSet.getString("location_name");
                int sumOfPollen = resultSet.getInt("SUM( colonies.pollen)");
                System.out.println("There are " + sumOfPollen + " kg of " +
                        "pollen in location " + locationName);
            }

        } catch (SQLException exception) {
            System.out.println("Error in counting pollen in location: " + exception);
        }
    }
    //    Print information about location names and location_id
    public void printLocationNameAndLocationId(Location location) {
        try {
            sqlStatement = "SELECT location_name, location_id FROM locations";
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            System.out.println("Locations: ");
            while (resultSet.next()) {
                String locationName = resultSet.getString("location_name");
                Integer locationId = resultSet.getInt("location_id");
                System.out.println("Location name: " + locationName + ", location_ID: " + locationId);
            }
        } catch (SQLException exception) {
            System.out.println("Error when printing locations " + exception);
        }
    }

    //add colony origin
    public void attachColonyOrigin(Colony colony){
        try {
            sqlStatement = "UPDATE colonies SET colony_origin = " +
                    "'" + colony.getColonyOrigin() + "' " +
                    "WHERE hive_id = " +
                    "'" + colony.getHiveId() + "'";
            statement.execute(sqlStatement);
        }catch (SQLException exception){
            System.out.println("Error adding colony origin " + exception);
        }
    }
    //add queen breed
    public void attachQueenBreed(Colony colony){
        try {
            sqlStatement = "UPDATE colonies SET queen_breed = '" +
                    "" + colony.getQueenBreed() + "' WHERE hive_id = '" +
                    "" + colony.getHiveId() + "'";
            statement.execute(sqlStatement);

        }catch (SQLException exception){
            System.out.println("Error adding queen's breed " + exception);
        }
    }
    //add queen year
    public void attachQueenYear(Colony colony){
        try {
            sqlStatement = "UPDATE colonies SET queen_year = " +
                    "'" + colony.getQueenYear() + "'" +
                    "WHERE hive_id = " +
                    "'" + colony.getHiveId() + "'";
            statement.execute(sqlStatement);

        }catch (SQLException exception){
            System.out.println("Error adding queen's year " + exception);
        }
    }
    //add num of supers
    public void attachNumOfSupers(Colony colony){
        try {
            sqlStatement = "UPDATE colonies SET supers = " +
                    "'" + colony.getNumberOfSupers() + "' " +
                    "WHERE hive_id = " +
                    "'" + colony.getHiveId() + "'";
            statement.execute(sqlStatement);

        }catch (SQLException exception){
            System.out.println("Error adding number of supers " + exception);
        }
    }
    //add num of frames
    public void attachNumOfFrames(Colony colony){
        try {
            sqlStatement = "UPDATE colonies SET frames = " +
                    "'" + colony.getNumberOfFrames() + "' " +
                    "WHERE hive_id = " +
                    "'" + colony.getHiveId() + "'";
            statement.execute(sqlStatement);

        }catch (SQLException exception){
            System.out.println("Error adding number of frames " + exception);
        }
    }
    //add num of bees
    public void attachNumOfBees(Colony colony){
        try {
            sqlStatement = "UPDATE colonies SET bees = " +
                    "'" + colony.getNumberOfBees() + "' " +
                    "WHERE hive_id = " +
                    "'" + colony.getHiveId() + "'";
            statement.execute(sqlStatement);

        }catch (SQLException exception){
            System.out.println("Error adding number of bees " + exception);
        }
    }
    //add num og brood
    public void attachNumOfBrood(Colony colony){
        try {
            sqlStatement = "UPDATE colonies SET brood = " +
                    "'" + colony.getNumberOfBrood() + "' " +
                    "WHERE hive_id = " +
                    "'" + colony.getHiveId() + "'";
            statement.execute(sqlStatement);

        }catch (SQLException exception){
            System.out.println("Error adding number of brood " + exception);
        }
    }
    //add kg honey
    public void attachKgHoney(Colony colony){
        try {
            sqlStatement = "UPDATE colonies SET honey = " +
                    "'" + colony.getKgHoney() + "' " +
                    "WHERE hive_id = " +
                    "'" + colony.getHiveId() + "'";
            statement.execute(sqlStatement);

        }catch (SQLException exception){
            System.out.println("Error adding kg honey " + exception);
        }
    }
    //add pollen
    public void attachPollen(Colony colony){
        try {
            sqlStatement = "UPDATE colonies SET pollen = " +
                    "'" + colony.getPollen() + "' " +
                    "WHERE hive_id = " +
                    "'" + colony.getHiveId() + "'";
            statement.execute(sqlStatement);

        }catch (SQLException exception){
            System.out.println("Error adding pollen " + exception);
        }
    }
    //add varroa treatment
    public void attachVarroaTreatment(Colony colony){
        try {
            sqlStatement = "UPDATE colonies SET varroa_treatment = " +
                    "'" + colony.getVarroaTreatment() + "' " +
                    "WHERE hive_id = " +
                    "'" + colony.getHiveId() + "'";
            statement.execute(sqlStatement);

        }catch (SQLException exception){
            System.out.println("Error adding varroa treatment " + exception);
        }
    }
    //add food added
    public void attachFoodAdded(Colony colony){
        try {
            sqlStatement = "UPDATE colonies SET food_added = " +
                    "'" + colony.getFoodAdded() + "' " +
                    "WHERE hive_id = " +
                    "'" + colony.getHiveId() + "'";
            statement.execute(sqlStatement);

        }catch (SQLException exception){
            System.out.println("Error adding food " + exception);
        }
    }



}






