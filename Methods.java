import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Methods {
    private static DbConnection dbConnection;
    Hive hive = new Hive();

    //    VALIDATOR FOR VARIABLES
    public static int validatorForIntegers(Scanner scanner) {
        int userInput = 0, notInt = 0, newInput = 0;
        while (notInt == 0) {
            try {
                newInput = Integer.parseInt(scanner.nextLine());
                userInput = newInput;
                notInt = 1;
                return userInput;

            } catch (NumberFormatException e) {
                System.out.println("That is not an integer, please try again.");
            }
        }
        return 0;
    }
    //    GET INFORMATION FROM HISTORY TABLE ABOUT SPECIFIC DATA
    public static void getInfoFromHistoryTableAboutData(History history, Scanner scanner, DbConnection dbConnection) {
        System.out.println("Please enter data you would like see information about in format: dd-mm-yyyy");
        history.setDataAndTime(scanner.nextLine());
        dbConnection.getInformationFromHistoryTable(history);
    }

    public static void getPercentsOfVarroaTreatement(Colony colony, DbConnection dbConnection) {
        int counterForL = 0;
        int counterForM = 0;
        int counterForA = 0;

        for (int i = 0; i < dbConnection.getvarroaTreatment().size(); i++) {
            if (dbConnection.getvarroaTreatment().get(i).equals("skudrskābe")) {
                counterForL++;
            } else if (dbConnection.getvarroaTreatment().get(i).equals("skābeņskābe")) {
                counterForM++;
            } else if (dbConnection.getvarroaTreatment().get(i).equals("HiveClean")) {
                counterForA++;
            }
        }
        System.out.println((float) counterForL * 100 / dbConnection.getvarroaTreatment().size() + " % of colonies treated with treatment skudrskābe");
        System.out.println((float) counterForM * 100 / dbConnection.getvarroaTreatment().size() + " % of colonies treated with treatment skābeņskābe");
        System.out.println((float) counterForA * 100 / dbConnection.getvarroaTreatment().size() + " % of colonies treated with treatment HIveClean");
    }

    // PRINT OUT INFORMATION ABOUT COLONIES
    public static void printInfoAboutColony(Scanner scanner, Colony colony, Methods methods, DbConnection dbConnection) {
       do {
           System.out.println("Select hive id whose colony you want information about");
           colony.setHiveId(methods.validatorForIntegers(scanner));
           if (colony.getHiveId()!=dbConnection.isThereHiveWithThisNr(methods.hive, colony)) {
               System.out.println("The hive does not exist! Please enter valid hive-id");
           }
       } while (colony.getHiveId()!= dbConnection.isThereHiveWithThisNr(methods.hive, colony));


        dbConnection.coloniesInfo(colony);
    }

    // METHOD PRINT OUT LOCATIONS
    public static void printLocation1(Location location, DbConnection dbConnection) {
        dbConnection.printLocation(location);
    }

}

