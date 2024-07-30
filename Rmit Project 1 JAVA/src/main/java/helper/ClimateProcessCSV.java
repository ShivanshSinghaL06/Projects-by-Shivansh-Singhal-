package helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ClimateProcessCSV {

   // MODIFY these to load/store to/from the correct locations
   private static final String DATABASE = "jdbc:sqlite:database/database1.db";
   private static final String CSV_FILE = "database/Population.csv";


   public static void main (String[] args) {
      // Load up the Date table
      // This only needs to be done once - uncomment this to reload the Date table
      // loadYears();

      // Load the Country Temperature Observations
      loadCountryTemperatures();

      return;
   }

   public static void loadYears() {
      // JDBC Database Object
      Connection connection = null;

      // Like JDBCConnection, we need some error handling.
      try {
         connection = DriverManager.getConnection(DATABASE);

         for (int i = 1750; i != 2024; ++i) {
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();

            // Create Insert Statement
            String query = "INSERT into Population2 VALUES ("
                           + i
                           + ")";

            // Execute the INSERT
            System.out.println("Executing: " + query);
            statement.execute(query);
         }

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public static void loadCountryTemperatures() {
      // JDBC Database Object
      Connection connection = null;

      // We need some error handling.
      try {
         // Open A CSV File to process, one line at a time
         // CHANGE THIS to process a different file
         Scanner lineScanner = new Scanner(new File(CSV_FILE));

         // Read the first line of "headings"
         String header = lineScanner.nextLine();
         System.out.println("Heading row" + header + "\n");

         // Setup JDBC
         // Connect to JDBC database
         connection = DriverManager.getConnection(DATABASE);

         // Read each line of the CSV
         int row = 1;
         while (lineScanner.hasNext()) {
            // Always get scan by line
            String line = lineScanner.nextLine();
            
            // Create a new scanner for this line to delimit by commas (,)
            Scanner rowScanner = new Scanner(line);
            rowScanner.useDelimiter(",");

            // Get all of the columns in order
            
            String rawCountryName = rowScanner.next();
            String CountryCode = rowScanner.next();

            Integer Year = 1960; //rowScanner.nextInt();
            while(rowScanner.hasNext()){
               Float Population = rowScanner.nextFloat();

               // if (CountryCode.equals("")) {
               //    CountryCode = "0";
               // }
               // if (rawCountryName.equals("")) {
               //    rawCountryName = "0";
               // }
               // if (Year.equals("")) {
               //    Year = 0;
               // }
               // if (Population.equals("")) {
               //    Population = "0.0";
               // }


               String query = "INSERT into \"Population2\" VALUES ("
               + "'" + rawCountryName.replace("'", " ") + "',"+
               "'" + CountryCode +"'" + ","
               + Year + ","
               + Population +")";

               Statement statement = connection.createStatement();


               // Execute the INSERT
               System.out.println("Executing: " + query);
               statement.execute(query);

               Year= Year + 1;
            }
            //Float Population = rowScanner.nextFloat();


            // In this example, we don't have the population, so we'll leave that as zero for now
            //int population = 0;
            
            // Set a default country code
            //CountryCode = "ZZZZ";

            // Convert any Latin1 encoded country names to UTF-8
            String Countryname = new String(rawCountryName.getBytes("ISO-8859-1"), "UTF-8");
            // We now need to look-up the country code from the name
            Statement statement = connection.createStatement();
            String query = "SELECT * from Population2";
            System.out.println("Looking up: " + query);
            ResultSet results = statement.executeQuery(query);
            if(results.next()) {
               CountryCode = results.getString("CountryCode");
            }

            // Now we can insert the entry into the CountryTempObservation tabe
            // Prepare a new SQL Query & Set a timeout
            statement = connection.createStatement();

            // Create Insert Statement
          
         }

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}
