
//JBDC
package app;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;


public class JDBCConnection {

    // Name of database file (contained in database folder)
    public static final String DATABASE = "jdbc:sqlite:database/database1.db";
    // public static final String DATABASE = "jdbc:sqlite:database/climate.db";

    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }

    // LANDING PAGE

    public ArrayList<GlobalTempObservation> getGlobalTempObservation() {
        // Create the ArrayList to return - this time of Movie objects
        ArrayList<GlobalTempObservation> GTO1 = new ArrayList<GlobalTempObservation>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query1 = "Select max(Year) as year, avgtemp from GlobalTempObservation ;";

            // Get Result
            ResultSet results1 = statement.executeQuery(query1);

            while (results1.next()) {

                // Create a Movie Object
                GlobalTempObservation GTO = new GlobalTempObservation();

                GTO.year = results1.getInt("year");
                GTO.avgTemp = results1.getFloat("avgTemp");
                GTO.year = results1.getInt("year");
                GTO.avgTemp = results1.getFloat("avgTemp");

                // Add the movie object to the array
                GTO1.add(GTO);
            }

            String query2 = "Select min(Year) as year, avgtemp from GlobalTempObservation ;";

            // Get Result
            ResultSet results2 = statement.executeQuery(query2);

            while (results2.next()) {

                // Create a Movie Object
                GlobalTempObservation GTO2 = new GlobalTempObservation();

                GTO2.year = results2.getInt("year");
                GTO2.avgTemp = results2.getFloat("avgTemp");
                GTO2.year = results2.getInt("year");
                GTO2.avgTemp = results2.getFloat("avgTemp");

                // Add the movie object to the array
                GTO1.add(GTO2);
            }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the movies
        return GTO1;
    }

    public ArrayList<Population> getPopulation() {
        // Create the ArrayList to return - this time of Movie objects
        ArrayList<Population> pop1 = new ArrayList<Population>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query3 = "select min(year) as year , sum(population) as population  from population2 group by year having year = 1960;;";
            // Get Result
            ResultSet results3 = statement.executeQuery(query3);

            while (results3.next()) {

                // Create a Movie Object
                Population p1 = new Population();

                // p1.countryname = results3.getString("countryname");
                // p1.countrycode = results3.getString("countrycode");
                p1.year = results3.getInt("year");
                p1.population = results3.getFloat("population");

                // Add the movie object to the array
                pop1.add(p1);

                String query4 = "select max(year) as year , sum(population) as population  from population2 group by year having year = 2013;";
                // Get Result
                ResultSet results4 = statement.executeQuery(query4);

                while (results4.next()) {

                    // Create a Movie Object
                    Population p2 = new Population();

                    // p1.countryname = results3.getString("countryname");
                    // p1.countrycode = results3.getString("countrycode");
                    p2.year = results4.getInt("year");
                    p2.population = results4.getFloat("population");

                    // Add the movie object to the array
                    pop1.add(p2);
                }
            }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the movies
        return pop1;
    }

    // LEVEL 2A country

    public ArrayList<countrypop2a> getCountrydata(String y1, String y2, String s1) {
        // Create the ArrayList to return - this time of Movie objects
        ArrayList<countrypop2a> pop1 = new ArrayList<countrypop2a>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query3 = "SELECT t1.CountryName,AVG(t2.AvgTemp) - AVG(t1.AvgTemp) AS avgtemp,p1.CountryCode,p2.Population - p1.Population AS population FROM countrytempobservation t1 INNER JOIN countrytempobservation t2 ON t1.CountryName = t2.CountryName INNER JOIN population2 p1 ON t1.CountryName = p1.CountryName INNER JOIN population2 p2 ON t1.CountryName = p2.CountryName WHERE t1.year = "
                    + y1 + " AND t2.year = " + y2 + " AND p1.year = " + y1 + " AND p2.year = " + y2
                    + " GROUP BY t1.CountryName, p1.CountryCode " + s1 + ";";
            // Get Result
            ResultSet results3 = statement.executeQuery(query3);

            while (results3.next()) {

                // Create a Movie Object
                // CountryTempObservation p2 = new CountryTempObservation();
                // //Population p3 = new Population();

                // p2.countryname = results3.getString("countryname");
                // p2.avgtemp = results3.getFloat("avgtemp");
                // // p2.mintemp = results3.getFloat("mintemp");
                // //p2.maxtemp = results3.getFloat("maxtemp");
                // p2.countrycode = results3.getString("countrycode");
                // p2.population = results3.getInt("population");

                countrypop2a p2 = new countrypop2a();
                // Population p3 = new Population();

                p2.CountryName = results3.getString("countryname");
                p2.avgtemp = results3.getFloat("avgtemp");
                // p2.mintemp = results3.getFloat("mintemp");
                // p2.maxtemp = results3.getFloat("maxtemp");
                p2.countrycode = results3.getString("countrycode");
                p2.population = results3.getInt("population");

                // Add the movie object to the array

                pop1.add(p2);
                // pop1.add(p3);
            }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the movies
        return pop1;
    }

    // LEVEL 2A world

    public ArrayList<Population> getpopulationdifference(String y1, String y2) {
        // Create the ArrayList to return - this time of Movie objects
        ArrayList<Population> pop1 = new ArrayList<Population>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query3 = "select (select sum(population) from population2 where year = " + y2
                    + ") - (select sum(population) from population2 where year = " + y1 + ") as PopulationDifference;";
            // Get Result
            ResultSet results3 = statement.executeQuery(query3);

            while (results3.next()) {

                // Create a Movie Object
                Population p1 = new Population();

                // p1.countryname = results3.getString("countryname");
                // p1.countrycode = results3.getString("countrycode");
                // p1.year= results3.getInt("year");
                p1.population = results3.getFloat("PopulationDifference");

                // Add the movie object to the array
                pop1.add(p1);
            }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the movies
        return pop1;
    }

    public ArrayList<GlobalTempObservation> getGlobalTempDifference(String y1, String y2) {
        // Create the ArrayList to return - this time of Movie objects
        ArrayList<GlobalTempObservation> pop1 = new ArrayList<GlobalTempObservation>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query3 = "SELECT (select avgtemp FROM globaltempobservation WHERE year = " + y2
                    + ") - (select avgtemp FROM globaltempobservation WHERE year = " + y1 + ") as TempDifference ;";
            // Get Result
            ResultSet results3 = statement.executeQuery(query3);

            while (results3.next()) {

                // Create a Movie Object
                GlobalTempObservation p2 = new GlobalTempObservation();

                // p2.countryname = results3.getString("countryname");
                p2.avgTemp = results3.getFloat("TempDifference");
                // p2.mintemp = results3.getFloat("mintemp");
                // p2.maxtemp = results3.getFloat("maxtemp");

                // Add the movie object to the array
                pop1.add(p2);
            }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the movies
        return pop1;
    }

//     public Connection getConnection() {
//         Connection connection = null;
//         // Code to establish the database connection
//         // ...
//         return connection;
//     }

//     public ArrayList<String> getImageFilePaths() {
//         ArrayList<String> imageFilePaths = new ArrayList<>();

//         try (Connection connection = DriverManager.getConnection(DATABASE);
//                 Statement statement = connection.createStatement()) {
//             String queryimg = "SELECT ImageFilePath FROM persona";
//             ResultSet resultSet = statement.executeQuery(queryimg);
//             while (resultSet.next()) {
//                 String imageFilePath = resultSet.getString("ImageFilePath");
//                 imageFilePaths.add(imageFilePath);
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }

//         return imageFilePaths;
//     }

//     public ArrayList<String> personaattribute() {
//         // Create the ArrayList to return - this time of Movie objects
//         ArrayList<String> personas = new ArrayList<String>();

//         // Setup the variable for the JDBC connection
//         Connection connection = null;
//         try {
//             // Connect to JDBC data base
//             connection = DriverManager.getConnection(DATABASE);

//             // Prepare a new SQL Query & Set a timeout
//             Statement statement = connection.createStatement();
//             statement.setQueryTimeout(30);

//             // The Query
//             String query4 = "SELECT AttributeType, Description FROM PersonaAttribute";
//             // Get Result
//             ResultSet results4 = statement.executeQuery(query4);

//             while (results4.next()) {

//                 String attributeType = results4.getString("AttributeType");
//                 String description = results4.getString("Description");
//                 // System.out.println("AttributeType: " + attributeType + ", Description: " +
//                 // description);
//             }
//             // Close the statement because we are done with it
//             statement.close();
//         } catch (SQLException e) {
//             // If there is an error, lets just pring the error
//             System.err.println(e.getMessage());
//         } finally {
//             // Safety code to cleanup
//             try {
//                 if (connection != null) {
//                     connection.close();
//                 }
//             } catch (SQLException e) {
//                 // connection close failed.
//                 System.err.println(e.getMessage());
//             }
//         }

//         // Finally we return all of the movies
//         return personas;
//     }

// public ArrayList<String> getStudentInfo() {
    // ArrayList<String> studentinfo = new ArrayList<>();

    //     try (Connection connection = DriverManager.getConnection(DATABASE);
    //             Statement statement = connection.createStatement()) {
    //         String query = "select SID, Name, Email from Student;";
    //         ResultSet resultSet = statement.executeQuery(query);
    //         while (resultSet.next()) {
    //             String SID = resultSet.getString("SID");
    //             String Name = resultSet.getString("Name");
    //             String Email = resultSet.getString("Email");
    //             String studentinfos = "<tr><td>" + SID + "</td><td>" + Name + "</td><td>" + Email + "</td></tr>";
    //             ;
    //             studentinfo.add(studentinfos);
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // try (Connection connection = DriverManager.getConnection(DATABASE);
    //      Statement statement = connection.createStatement()) {
    //     String query = "SELECT SID, Name, Email FROM Student;";
    //     ResultSet resultSet = statement.executeQuery(query);
    //     while (resultSet.next()) {
    //         String SID = resultSet.getString("SID");
    //         String Name = resultSet.getString("Name");
    //         String Email = resultSet.getString("Email");
    //         String studentinfos = "<tr><td>" + SID + "</td><td>" + Name + "</td><td>" + Email + "</td></tr>";
    //         studentinfo.add(studentinfos);
    //     }
    // } catch (SQLException e) {
    //     e.printStackTrace();
    // }

    //     return studentinfo;
    // }

    // LEVEL 3A

    // PART 1

    public ArrayList<CountryTempObservation> level31function(int y1, int y2, String cname) {
        // Create the ArrayList to return - this time of Movie objects
        ArrayList<CountryTempObservation> pop1 = new ArrayList<CountryTempObservation>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query3 = "select  avg(avgtemp) AS avgtemp1 from countrytempobservation where year >=" + y1+ " and year <= " + (y1 + y2) + "  and countryname = \"" + cname + "\" ;";
            // Get Result
            ResultSet results3 = statement.executeQuery(query3);

            while (results3.next()) {

                // Create a Movie Object
                CountryTempObservation p2 = new CountryTempObservation();

                // p2.countryname = results3.getString("countryname");
                p2.avgtemp = results3.getFloat("avgtemp1");
                // p2.mintemp = results3.getFloat("mintemp");
                // p2.maxtemp = results3.getFloat("maxtemp");

                // Add the movie object to the array
                pop1.add(p2);
            }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the movies
        return pop1;
    }

    // PART 2 3 YEARS

    public ArrayList<level3p2> level32function(int y1,int y2,int y3,int y4,int y5,String cname) {
        // Create the ArrayList to return - this time of Movie objects
        ArrayList<level3p2> pop1 = new ArrayList<level3p2>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            
            
            String query3 = "select  "+y3+" as range,avg(avgtemp) - (select avg(avgtemp) AS avgtemp from countrytempobservation where year >= "+y1+" and year <= "+y1+y2+" and countryname =\""+ cname+"\") AS avgtemp2  from countrytempobservation where year >= "+y3+" and year <= "+y3+y2+" and countryname = \""+ cname + "\" union select "+y4+" as range,avg(avgtemp) -(select avg(avgtemp) AS avgtemp from countrytempobservation where year >= "+y1+" and year <= "+y1+y2+" and countryname = \""+ cname+"\") AS avgtemp2 from countrytempobservation where year >= "+y4+" and year <= "+y4+y2+" and countryname = \""+ cname+"\" union select "+y5+" as range,avg(avgtemp) -(select avg(avgtemp) AS avgtemp from countrytempobservation where year >= "+y1+" and year <= "+y1+y2+" and countryname = \""+ cname+"\") AS avgtemp2 from countrytempobservation where year >= "+y5+" and year <= "+y5+y2+" and countryname = \""+ cname+"\" ;";
            System.out.println(y1);
            System.out.println(y2);
            System.out.println(y1+y2);
            System.out.println(y3);
            System.out.println(y3+y2);
            System.out.println(y4);
            System.out.println(y4+y2);
            System.out.println(y5);
            System.out.println(y5+y2);
            


            //select "+y3+"-"+y3+y2+" as range,avg(avgtemp) -(select avg(avgtemp) AS avgtemp from countrytempobservation where year >= "+y1+" and year <= "+y1+y2+" and countryname = \""+ cname+"\") AS avgtemp5 from countrytempobservation where year >= 2002 and year <= 2012 and countryname = \""+ cname+"\";"
            // Get Result
            ResultSet results3 = statement.executeQuery(query3);

            while (results3.next()) {
                
                // Create a Movie Object
               level3p2 p2 = new  level3p2();

                p2.range = results3.getString("range");
                p2.avgtemp2 = Math.abs(results3.getFloat("avgtemp2"));
                // p2.mintemp  = results3.getFloat("mintemp");
                  //p2.maxtemp  = results3.getFloat("maxtemp");


                // Add the movie object to the array
                pop1.add(p2);
            }
        // Close the statement because we are done with it
        statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        
        // Finally we return all of the movies
        return pop1;
    }

     public ArrayList<level3p3> level33function(int y1,int y2,int y3,int y4,int y5,String cname,String cname2) {
        // Create the ArrayList to return - this time of Movie objects
        ArrayList<level3p3> pop1 = new ArrayList<level3p3>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            
            
    
            String query3 = " select t1.year,t1.avgtemp as avgtemp_Aruba,t2.avgtemp as avgtemp_India,t1.avgtemp - t2.avgtemp as tempDiff  from countrytempobservation t1 inner join countrytempobservation t2 on  t1.year = t2.year where  t1.year >= "+y1+" and t1.year <= "+y1+y2+"  and t2.year >= "+y1+" and t2.year <= "+y1+y2+" and t1.countryname = \""+ cname+"\" and t2.countryname = \""+ cname2+"\" union select t1.year,t1.avgtemp as avgtemp_Aruba,t2.avgtemp as avgtemp_India,t1.avgtemp - t2.avgtemp as tempDiff  from countrytempobservation t1 inner join countrytempobservation t2 on  t1.year = t2.year where  t1.year >= "+y3+" and t1.year <= "+y3+y2+" and  t1.year >= "+y3+" and t1.year <= "+y3+y2+" and t2.year >= "+y3+" and t2.year <= "+y3+y2+" and t1.countryname = \""+ cname+"\" and t2.countryname = \""+ cname2+"\" union select t1.year,t1.avgtemp as avgtemp_Aruba,t2.avgtemp as avgtemp_India,t1.avgtemp - t2.avgtemp as tempDiff   from countrytempobservation t1 inner join countrytempobservation t2 on  t1.year = t2.year where  t1.year >= "+y4+" and t1.year <= "+y4+y2+" and t2.year >= "+y4+" and t2.year <= "+y4+y2+" and t1.countryname = \""+ cname+"\" and t2.countryname = \""+ cname2+"\" union select t1.year,t1.avgtemp as avgtemp_Aruba,t2.avgtemp as avgtemp_India,t1.avgtemp - t2.avgtemp as tempDiff  from countrytempobservation t1 inner join countrytempobservation t2 on  t1.year = t2.year where  t1.year >= "+y5+" and t1.year <= "+y5+y2+" and t2.year >= "+y5+" and t2.year <= "+y5+y2+" and t1.countryname = \""+ cname+"\" and t2.countryname = \""+ cname2+"\"  order by t1.year;";
            System.out.println(y1);
            System.out.println(y2);
            System.out.println(y1+y2);
            System.out.println(y3);
            System.out.println(y3+y2);
            System.out.println(y4);
            System.out.println(y4+y2);
            System.out.println(y5);
            System.out.println(y5+y2);
            System.out.println(cname);
            System.out.println(cname2);

            //select "+y3+"-"+y3+y2+" as range,avg(avgtemp) -(select avg(avgtemp) AS avgtemp from countrytempobservation where year >= "+y1+" and year <= "+y1+y2+" and countryname = \""+ cname+"\") AS avgtemp5 from countrytempobservation where year >= 2002 and year <= 2012 and countryname = \""+ cname+"\";"
            // Get Result
            ResultSet results3 = statement.executeQuery(query3);

            while (results3.next()) {
                
                // Create a Movie Object
               level3p3 p2 = new  level3p3();

                p2.year = results3.getInt("year");
                p2.avgtempl31 = Math.abs(results3.getFloat("avgtemp_Aruba"));
                p2.avgtempl32 = Math.abs(results3.getFloat("avgtemp_India"));
                p2.avgdiffl31 = Math.abs(results3.getFloat("tempDiff"));
                // p2.mintemp  = results3.getFloat("mintemp");
                  //p2.maxtemp  = results3.getFloat("maxtemp");


                // Add the movie object to the array
                pop1.add(p2);
            }
        // Close the statement because we are done with it
        statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        
        // Finally we return all of the movies
        return pop1;
    }

    //Level 3 

    public ArrayList<StateTempObservation> level31function1(int y1, int y2, String cname) {
        // Create the ArrayList to return - this time of Movie objects
        ArrayList<StateTempObservation> pop1 = new ArrayList<StateTempObservation>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query3 = "select "+y1+" as range,avg(avgtemp) as avgtemp1 from statetempobservation where year >= "+y1+" and year <="+(y1+y2)+" and statename = \"" + cname + "\" ;";
            // Get Result
            ResultSet results3 = statement.executeQuery(query3);

            while (results3.next()) {

                // Create a Movie Object
                StateTempObservation p2 = new StateTempObservation();

                // p2.countryname = results3.getString("countryname");
                // p2.avgtemp = results3.getFloat("avgtemp1");
                // p2.mintemp = results3.getFloat("mintemp");
                // p2.maxtemp = results3.getFloat("maxtemp");
                p2.avgtempstate = results3.getFloat("avgtemp1");

                // Add the movie object to the array
                pop1.add(p2);
            }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the movies
        return pop1;
    }

    public ArrayList<StateTempObservation> level32function2(int y1,int y2,int y3,int y4,int y5,String cname) {
        // Create the ArrayList to return - this time of Movie objects
        ArrayList<StateTempObservation> pop1 = new ArrayList<StateTempObservation>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            
            
            String query3 = "select  "+y3+" as range,avg(avgtemp) - (select avg(avgtemp) AS avgtemp from statetempobservation where year >= "+y1+" and year <= "+y1+y2+" and statename =\""+ cname+"\") AS avgtemp  from statetempobservation where year >= "+y3+" and year <= "+y3+y2+" and statename = \""+ cname + "\" union select "+y4+" as range,avg(avgtemp) -(select avg(avgtemp) AS avgtemp from statetempobservation where year >= "+y1+" and year <= "+y1+y2+" and statename = \""+ cname+"\") AS avgtemp from statetempobservation where year >= "+y4+" and year <= "+y4+y2+" and statename = \""+ cname+"\" union select "+y5+" as range,avg(avgtemp) -(select avg(avgtemp) AS avgtemp from statetempobservation where year >= "+y1+" and year <= "+y1+y2+" and statename = \""+ cname+"\") AS avgtemp from statetempobservation where year >= "+y5+" and year <= "+y5+y2+" and statename = \""+ cname+"\" ;";
            System.out.println(y1);
            System.out.println(y2);
            System.out.println(y1+y2);
            System.out.println(y3);
            System.out.println(y3+y2);
            System.out.println(y4);
            System.out.println(y4+y2);
            System.out.println(y5);
            System.out.println(y5+y2);
            


            //select "+y3+"-"+y3+y2+" as range,avg(avgtemp) -(select avg(avgtemp) AS avgtemp from countrytempobservation where year >= "+y1+" and year <= "+y1+y2+" and countryname = \""+ cname+"\") AS avgtemp5 from countrytempobservation where year >= 2002 and year <= 2012 and countryname = \""+ cname+"\";"
            // Get Result
            ResultSet results3 = statement.executeQuery(query3);

            while (results3.next()) {
                
                // Create a Movie Object
               StateTempObservation p2 = new  StateTempObservation();

                p2.Countrynamestate = results3.getString("range");
                p2.avgtempstate = Math.abs(results3.getFloat("avgtemp"));
                // p2.mintemp  = results3.getFloat("mintemp");
                  //p2.maxtemp  = results3.getFloat("maxtemp");


                // Add the movie object to the array
                pop1.add(p2);
            }
        // Close the statement because we are done with it
        statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        
        // Finally we return all of the movies
        return pop1;
    }

    public ArrayList<StateTempObservation> level33function3(int y1,int y2,int y3,int y4,int y5,String cname,String cname2) {


        // Create the ArrayList to return - this time of Movie objects
        ArrayList<StateTempObservation> pop1 = new ArrayList<StateTempObservation>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            
            
    
            String query3 = "select t1.year,t1.avgtemp as avgtemp_Aruba,t2.avgtemp as avgtemp_India,t1.avgtemp - t2.avgtemp as tempDiff  from statetempobservation t1 inner join statetempobservation t2 on  t1.year = t2.year where  t1.year >= "+y1+" and t1.year <= "+y1+y2+"  and t1.statename = \""+ cname+"\" and t2.statename = \""+ cname2+"\" union select t1.year,t1.avgtemp as avgtemp_Aruba,t2.avgtemp as avgtemp_India,t1.avgtemp - t2.avgtemp as tempDiff  from statetempobservation t1 inner join statetempobservation t2 on t1.year = t2.year where  t1.year >= "+y3+" and t1.year <= "+y3+y2+"  and t1.statename = \""+ cname+"\" and t2.statename = \""+ cname2+"\" union select t1.year,t1.avgtemp as avgtemp_Aruba,t2.avgtemp as avgtemp_India,t1.avgtemp - t2.avgtemp as tempDiff   from statetempobservation t1 inner join statetempobservation t2 on t1.year = t2.year where  t1.year >= "+y4+" and t1.year <= "+y4+y2+"  and t1.statename = \""+ cname+"\" and t2.statename = \""+ cname2+"\" union select t1.year,t1.avgtemp as avgtemp_Aruba,t2.avgtemp as avgtemp_India,t1.avgtemp - t2.avgtemp as tempDiff  from statetempobservation t1 inner join statetempobservation t2 on  t1.year = t2.year where t1.year >= "+y5+" and t1.year <= "+y5+y2+"  and t1.statename = \""+ cname+"\" and t2.statename = \""+ cname2+"\"  order by t1.year;";
            System.out.println(y1);
            System.out.println(y2);
            System.out.println(y1+y2);
            System.out.println(y3);
            System.out.println(y3+y2);
            System.out.println(y4);
            System.out.println(y4+y2);
            System.out.println(y5);
            System.out.println(y5+y2);
            System.out.println(cname);
            System.out.println(cname2);

            //select "+y3+"-"+y3+y2+" as range,avg(avgtemp) -(select avg(avgtemp) AS avgtemp from countrytempobservation where year >= "+y1+" and year <= "+y1+y2+" and countryname = \""+ cname+"\") AS avgtemp5 from countrytempobservation where year >= 2002 and year <= 2012 and countryname = \""+ cname+"\";"
            // Get Result
            ResultSet results3 = statement.executeQuery(query3);

            while (results3.next()) {
                
                // Create a Movie Object
               StateTempObservation p2 = new  StateTempObservation();

                p2.year = results3.getInt("year");
                p2.avgtempstate = Math.abs(results3.getFloat("avgtemp_Aruba"));
                p2.mintempstate = Math.abs(results3.getFloat("avgtemp_India"));
                p2.maxtempstate = Math.abs(results3.getFloat("tempDiff"));
                // p2.mintemp  = results3.getFloat("mintemp");
                  //p2.maxtemp  = results3.getFloat("maxtemp");


                // Add the movie object to the array
                pop1.add(p2);
            }
        // Close the statement because we are done with it
        statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        
        // Finally we return all of the movies
        return pop1;
    }



    //CITY
    public ArrayList<CityTempObservation> level31function11(int y1, int y2, String cname) {
        // Create the ArrayList to return - this time of Movie objects
        ArrayList<CityTempObservation> pop1 = new ArrayList<CityTempObservation>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query3 = "select "+y1+" as range,avg(avgtemp) as avgtemp1 from CityTempObservation where year >= "+y1+" and year <="+(y1+y2)+" and cityname = \"" + cname + "\" ;";
            // Get Result
            ResultSet results3 = statement.executeQuery(query3);

            while (results3.next()) {

                // Create a Movie Object
                CityTempObservation p2 = new CityTempObservation();

                // p2.countryname = results3.getString("countryname");
                // p2.avgtemp = results3.getFloat("avgtemp1");
                // p2.mintemp = results3.getFloat("mintemp");
                // p2.maxtemp = results3.getFloat("maxtemp");
                p2.avgtempc = results3.getFloat("avgtemp1");

                // Add the movie object to the array
                pop1.add(p2);
            }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the movies
        return pop1;
    }

    public ArrayList<CityTempObservation> level32function22(int y1,int y2,int y3,int y4,int y5,String cname) {
        // Create the ArrayList to return - this time of Movie objects
        ArrayList<CityTempObservation> pop1 = new ArrayList<CityTempObservation>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            
            
            String query3 = "select  "+y3+" as range,avg(avgtemp) - (select avg(avgtemp) AS avgtemp from CityTempObservation where year >= "+y1+" and year <= "+y1+y2+" and cityname =\""+ cname+"\") AS avgtemp  from citytempobservation where year >= "+y3+" and year <= "+y3+y2+" and cityname = \""+ cname + "\" union select "+y4+" as range,avg(avgtemp) -(select avg(avgtemp) AS avgtemp from citytempobservation where year >= "+y1+" and year <= "+y1+y2+" and cityname = \""+ cname+"\") AS avgtemp from citytempobservation where year >= "+y4+" and year <= "+y4+y2+" and cityname = \""+ cname+"\" union select "+y5+" as range,avg(avgtemp) -(select avg(avgtemp) AS avgtemp from citytempobservation where year >= "+y1+" and year <= "+y1+y2+" and cityname = \""+ cname+"\") AS avgtemp from citytempobservation where year >= "+y5+" and year <= "+y5+y2+" and cityname = \""+ cname+"\" ;";
            System.out.println(y1);
            System.out.println(y2);
            System.out.println(y1+y2);
            System.out.println(y3);
            System.out.println(y3+y2);
            System.out.println(y4);
            System.out.println(y4+y2);
            System.out.println(y5);
            System.out.println(y5+y2);
            


            //select "+y3+"-"+y3+y2+" as range,avg(avgtemp) -(select avg(avgtemp) AS avgtemp from countrytempobservation where year >= "+y1+" and year <= "+y1+y2+" and countryname = \""+ cname+"\") AS avgtemp5 from countrytempobservation where year >= 2002 and year <= 2012 and countryname = \""+ cname+"\";"
            // Get Result
            ResultSet results3 = statement.executeQuery(query3);

            while (results3.next()) {
                
                // Create a Movie Object
               CityTempObservation p2 = new  CityTempObservation();

                p2.year = results3.getInt("range");
                p2.avgtempc = Math.abs(results3.getFloat("avgtemp"));
                // p2.mintemp  = results3.getFloat("mintemp");
                  //p2.maxtemp  = results3.getFloat("maxtemp");


                // Add the movie object to the array
                pop1.add(p2);
            }
        // Close the statement because we are done with it
        statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        
        // Finally we return all of the movies
        return pop1;
    }

    public ArrayList<CityTempObservation> level33function33(int y1,int y2,int y3,int y4,int y5,String cname,String cname2) {


        // Create the ArrayList to return - this time of Movie objects
        ArrayList<CityTempObservation> pop1 = new ArrayList<CityTempObservation>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            
            
    
            String query3 = "select t1.year,t1.avgtemp as avgtemp_Aruba,t2.avgtemp as avgtemp_India,t1.avgtemp - t2.avgtemp as tempDiff  from CityTempObservation t1 inner join CityTempObservation t2 on  t1.year = t2.year where  t1.year >= "+y1+" and t1.year <= "+y1+y2+"  and t1.cityname = \""+ cname+"\" and t2.cityname = \""+ cname2+"\" union select t1.year,t1.avgtemp as avgtemp_Aruba,t2.avgtemp as avgtemp_India,t1.avgtemp - t2.avgtemp as tempDiff  from CityTempObservation t1 inner join CityTempObservation t2 on t1.year = t2.year where  t1.year >= "+y3+" and t1.year <= "+y3+y2+"  and t1.cityname = \""+ cname+"\" and t2.cityname = \""+ cname2+"\" union select t1.year,t1.avgtemp as avgtemp_Aruba,t2.avgtemp as avgtemp_India,t1.avgtemp - t2.avgtemp as tempDiff   from CityTempObservation t1 inner join CityTempObservation t2 on t1.year = t2.year where  t1.year >= "+y4+" and t1.year <= "+y4+y2+"  and t1.cityname = \""+ cname+"\" and t2.cityname = \""+ cname2+"\" union select t1.year,t1.avgtemp as avgtemp_Aruba,t2.avgtemp as avgtemp_India,t1.avgtemp - t2.avgtemp as tempDiff  from CityTempObservation t1 inner join CityTempObservation t2 on  t1.year = t2.year where t1.year >= "+y5+" and t1.year <= "+y5+y2+"  and t1.cityname = \""+ cname+"\" and t2.cityname = \""+ cname2+"\"  order by t1.year;";
            System.out.println(y1);
            System.out.println(y2);
            System.out.println(y1+y2);
            System.out.println(y3);
            System.out.println(y3+y2);
            System.out.println(y4);
            System.out.println(y4+y2);
            System.out.println(y5);
            System.out.println(y5+y2);
            System.out.println(cname);
            System.out.println(cname2);

            //select "+y3+"-"+y3+y2+" as range,avg(avgtemp) -(select avg(avgtemp) AS avgtemp from countrytempobservation where year >= "+y1+" and year <= "+y1+y2+" and countryname = \""+ cname+"\") AS avgtemp5 from countrytempobservation where year >= 2002 and year <= 2012 and countryname = \""+ cname+"\";"
            // Get Result
            ResultSet results3 = statement.executeQuery(query3);

            while (results3.next()) {
                
                // Create a Movie Object
               CityTempObservation p2 = new  CityTempObservation();

                p2.year = results3.getInt("year");
                p2.avgtempc = Math.abs(results3.getFloat("avgtemp_Aruba"));
                p2.mintempc = Math.abs(results3.getFloat("avgtemp_India"));
                p2.maxtempc = Math.abs(results3.getFloat("tempDiff"));
                // p2.mintemp  = results3.getFloat("mintemp");
                  //p2.maxtemp  = results3.getFloat("maxtemp");


                // Add the movie object to the array
                pop1.add(p2);
            }
        // Close the statement because we are done with it
        statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        
        // Finally we return all of the movies
        return pop1;
    }

    public ArrayList<worldtempobservation> level33function34(int y1, int y2) {
        // Create the ArrayList to return - this time of Movie objects
        ArrayList<worldtempobservation> pop1 = new ArrayList<worldtempobservation>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query3 = "select  avg(avgtemp) AS avgtemp1 from globaltempobservation where year >=" + y1+ " and year <= " + (y1 + y2) + " ;";
            // Get Result
            ResultSet results3 = statement.executeQuery(query3);

            while (results3.next()) {

                // Create a Movie Object
                worldtempobservation p2 = new worldtempobservation();

                // p2.countryname = results3.getString("countryname");
                p2.avgtemp = results3.getFloat("avgtemp1");
                // p2.mintemp = results3.getFloat("mintemp");
                // p2.maxtemp = results3.getFloat("maxtemp");

                // Add the movie object to the array
                pop1.add(p2);
            }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the movies
        return pop1;
    }


public ArrayList<String> getStudentInfo() {
    ArrayList<String> studentinfo = new ArrayList<>();

    try (Connection connection = DriverManager.getConnection(DATABASE);
         Statement statement = connection.createStatement()) {
        String query = "SELECT SID, Name, Email FROM Student;";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            String SID = resultSet.getString("SID");
            String Name = resultSet.getString("Name");
            String Email = resultSet.getString("Email");
            String studentinfos = "<tr><td>" + SID + "</td><td>" + Name + "</td><td>" + Email + "</td></tr>";
            studentinfo.add(studentinfos);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return studentinfo;
}
public ArrayList<String> cityTempDiff(String country, int startYear, int endYear) {
    ArrayList<String> tempDiff = new ArrayList<>();

    try (Connection connection = DriverManager.getConnection(DATABASE);
         Statement statement = connection.createStatement()) {

        String queryCity = "SELECT CityName, " +
                "(SELECT avgtemp FROM CityTempObservation WHERE year = " + startYear + " AND CountryName = '" + country + "' AND CityName = C.CityName) - " +
                "(SELECT avgtemp FROM CityTempObservation WHERE year = " + endYear + " AND CountryName = '" + country + "' AND CityName = C.CityName) AS TempDifference " +
                "FROM (SELECT DISTINCT CityName FROM CityTempObservation WHERE CountryName = '" + country + "') AS C";

        ResultSet resultSet = statement.executeQuery(queryCity);
        while (resultSet.next()) {
            String cityName = resultSet.getString("CityName");
            double tempDifference = resultSet.getDouble("TempDifference");

            // Chatgpt help me with how to get a two decimal places
            DecimalFormat decimalFormat = new DecimalFormat("#0.00");
            String formattedTempDifference = decimalFormat.format(tempDifference);

            String result = "City: " + cityName + ", Temp Difference: " + formattedTempDifference;
            tempDiff.add(result);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return tempDiff;
}

public ArrayList<String> stateTempDiff(String country, int startYear, int endYear) {
    ArrayList<String> tempDiff = new ArrayList<>();

    try (Connection connection = DriverManager.getConnection(DATABASE);
         Statement statement = connection.createStatement()) {

        String queryState = "SELECT StateName, " +
                "(SELECT avgtemp FROM StateTempObservation2 WHERE year = " + startYear + " AND CountryName = '" + country + "' AND StateName = S.StateName) - " +
                "(SELECT avgtemp FROM StateTempObservation2 WHERE year = " + endYear + " AND CountryName = '" + country + "' AND StateName = S.StateName) AS TempDifference " +
                "FROM (SELECT DISTINCT StateName FROM StateTempObservation2 WHERE CountryName = '" + country + "') AS S";

        ResultSet resultSet = statement.executeQuery(queryState);
        while (resultSet.next()) {
            String stateName = resultSet.getString("StateName");
            double tempDifference = resultSet.getDouble("TempDifference");

            // Chatgpt help me with how to get a two decimal places
            DecimalFormat decimalFormat = new DecimalFormat("#0.000");
            String formattedTempDifference = decimalFormat.format(tempDifference);

            String result = "State: " + stateName + ", Temp Difference: " + formattedTempDifference;
            tempDiff.add(result);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return tempDiff;
}
public ArrayList<String> getTopCitiesWithMaxTempDifference(String country, int startYear, int endYear) {
    ArrayList<String> topCities = new ArrayList<>();

    try (Connection connection = DriverManager.getConnection(DATABASE);
         Statement statement = connection.createStatement()) {
        
        //Got help from ChatGPT to generate the query in the right format
        String query = "SELECT CityName, " +
                "(SELECT avgtemp FROM CityTempObservation WHERE year = " + startYear + " AND CountryName = '" + country + "' AND CityName = C.CityName) - " +
                "(SELECT avgtemp FROM CityTempObservation WHERE year = " + endYear + " AND CountryName = '" + country + "' AND CityName = C.CityName) AS TempDifference " +
                "FROM (SELECT DISTINCT CityName FROM CityTempObservation WHERE CountryName = '" + country + "') AS C " +
                "ORDER BY TempDifference DESC " +
                "LIMIT 3";

        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            String cityName = resultSet.getString("CityName");
            double tempDifference = resultSet.getDouble("TempDifference");

            DecimalFormat decimalFormat = new DecimalFormat("#0.00");
            String formattedTempDifference = decimalFormat.format(tempDifference);

            String result = "City: " + cityName + ", Temp Difference: " + formattedTempDifference;
            topCities.add(result);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return topCities;
}

public ArrayList<String> getTopStatesWithMaxTempDifference(String country, int startYear, int endYear) {
    ArrayList<String> topStates = new ArrayList<>();

    try (Connection connection = DriverManager.getConnection(DATABASE);
         Statement statement = connection.createStatement()) {
        
        //Got help from ChatGPT to generate the query in the right format
        String query = "SELECT StateName, " +
                "(SELECT avgtemp FROM StateTempObservation2 WHERE year = " + startYear + " AND CountryName = '" + country + "' AND StateName = S.StateName) - " +
                "(SELECT avgtemp FROM StateTempObservation2 WHERE year = " + endYear + " AND CountryName = '" + country + "' AND StateName = S.StateName) AS TempDifference " +
                "FROM (SELECT DISTINCT StateName FROM StateTempObservation2 WHERE CountryName = '" + country + "') AS S " +
                "ORDER BY TempDifference DESC " +
                "LIMIT 3";

        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            String stateName = resultSet.getString("StateName");
            double tempDifference = resultSet.getDouble("TempDifference");

            DecimalFormat decimalFormat = new DecimalFormat("#0.00");
            String formattedTempDifference = decimalFormat.format(tempDifference);

            String result = "State: " + stateName + ", Temp Difference: " + formattedTempDifference;
            topStates.add(result);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return topStates;
}



    public ArrayList<String> getImageFilePaths() {
    ArrayList<String> imageFilePaths = new ArrayList<>();

    try(Connection connection = DriverManager.getConnection(DATABASE);
        Statement statement = connection.createStatement()) 
    {
        String queryimg = "SELECT ImageFilePath FROM persona";
        ResultSet resultSet = statement.executeQuery(queryimg);
        while (resultSet.next()) {
            String imageFilePath = resultSet.getString("ImageFilePath");
            imageFilePaths.add(imageFilePath);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return imageFilePaths;
}
public ArrayList<String> getPersonaAttributes() {
    ArrayList<String> attributeRows = new ArrayList<>();

    try (Connection connection = DriverManager.getConnection(DATABASE);
         Statement statement = connection.createStatement()) {
        String query = "SELECT AttributeType, Description FROM PersonaAttribute";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            String attributeType = resultSet.getString("AttributeType");
            String description = resultSet.getString("Description");
            String attributeRow = "<tr><td>" + attributeType + "</td><td>" + description + "</td></tr>";
            attributeRows.add(attributeRow);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return attributeRows;
}

}
