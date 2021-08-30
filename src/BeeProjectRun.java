import java.sql.*;
import java.util.Scanner;

public class BeeProjectRun {

    public static void main(String[] args) {

//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Input colony  ID");
//        String colony_id = scanner.next();
//        System.out.println("Input origin");
//        String origin = scanner.next();
//        System.out.println("Input queen year");
//        String queen_year = scanner.next();

        try{
            String urlDataBase = "jdbc:sqLite:beeproject.db";
            Connection connection = DriverManager.getConnection(urlDataBase);

            if (connection != null) {
                DatabaseMetaData databaseMetaData = (DatabaseMetaData) connection.getMetaData();
                System.out.println("Connected to " + databaseMetaData.getDatabaseProductName() + " " + databaseMetaData.getDatabaseProductVersion() );

        //  Creating colonies table
                Statement statement = connection.createStatement();
                String sqlStatement =
                        "CREATE TABLE IF NOT EXISTS colonies" +
                                " (colony_id INTEGER PRIMARY KEY NOT NULL," +
                                "hive_id INTEGER NOT NULL," +
                                "colony_origin TEXT NOT NULL," +
                                "queen_breed TEXT NOT NULL," +
                                "queen_year INTEGER NOT NULL," +
                                "supers INTEGER NOT NULL," +
                                "frames INTEGER NOT NULL," +
                                "bees INTEGER NOT NULL," +
                                "brood INTEGER NOT NULL," +
                                "honey INTEGER NUMBER NOT NULL," +
                                "varroa_treatment TEXT NOT NULL," +
                                "food_added TEXT NOT NULL )";
                statement.execute(sqlStatement);
            }
        }catch(SQLException exception){
            System.out.println("Error!" + exception.toString());
        }


    }
}
