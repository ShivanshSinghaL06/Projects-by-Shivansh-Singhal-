//PAGEINDEX
package app;

import java.util.ArrayList;

import org.sqlite.JDBC;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.time.Year;


public class PageIndex implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Header information
        html = html + "<head>" + 
               "<title>Homepage</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add the topnav
        // This uses a Java v15+ Text Block
        html = html + """
            <div class='topnav'>
                <a href='/'>Homepage</a>
                <a href='mission.html'>Our Mission</a>
                <a href='page2A.html'>Global Temperature and Population</a>
                <a href='page2B.html'>State/ City Temperature Change</a>
                <a href='page3A.html'>Temperature Extended Periods</a>
                <a href='page3B.html'>Temperature Trends</a>
            </div>
        """;

        // Add header content block
        html = html + """
            <div class='header'>
                <h1>
                    <img src='logo1.png' class='top-image' alt='Climate CC Photo' height='75'>
                    Homepage
                </h1>
            </div>
        """;
        

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        html = html + """
            <h1>Welcome to ClimateCC</h1>
            <p>ClimateCC is a web application developed to help users such as general people, governments, and 
            scientists to analyze patterns of change in ocean and land temperatures over past 260 years. The 
            interface has different webpages to cater to the needs of different users having Links for scientific data 
            and data for governments on other web pages to avoid inconvenience for the common user. Webpages 
            for government and scientists have data in an informative manner and high-level concepts. Application 
            also provides data for high and lows of temperature of a course of 50 years and an average of the period 
            which helps the user to compare in a convenient way. The Distinctive feature for the website is the data 
            visualization for the general user including charts, graphs and maps, to represent trends over time. Users
            can select different time scales, regions, and variables to data in simplest way possible and export data
            for further analysis. The website also provides the basic definitions for the people who are beginners and 
            trying to get an understanding of climate change from scratch.<br>
            PRODUCT AND SERVICE
            1) It\'s a product where users can learn about climate change around the world including the 
            oceans.
            2) It helps its consumers to analyze the data in a simple and fast manner, as well as providing
            complex data for the more advanced user group.

            </p>
            """;
        html = html + """
            <div class='row'>
            <div class='img1'>
                    <img src='cc1.jpg' class='top-image1' alt='Climate CC Photo' style='width:100%;height:500'>
            </div>
            <div class='img1'>
                    <img src='cc2.jpg' class='top-image1' alt='Climate CC Photo' style='width:100%;height:500'>
            </div></div>
        """;

        // Get the ArrayList of Strings of all LGAs
        // ArrayList<String> lgaNames = getLGAs2016();
        
        
        
        // Add HTML for the LGA list
        //html = html + "<h1>All 2016 LGAs in the CTG database</h1>" + "<ul>";
        html = html + "<h2>The Data represented on our website is as follows:  </h2>" ;
         
        
        // Finally we can print out all of the LGAs
        // for (int var :gto2) {
            //     html = html + "The last year of the Globaldata is --->      " + var + "</li>";
        // }
        
        // Finish the List HTML
        JDBCConnection jdbc = new JDBCConnection();
        // ArrayList<GlobalTempObservation> GTO = jdbc.getGlobalTempObservation();
        // for(GlobalTempObservation gto: GTO){
        //     html = html + "Year:  " + gto.year + " AverageTemp--> "+gto.avgTemp;
        // }
        ArrayList<GlobalTempObservation> GTO2 = jdbc.getGlobalTempObservation();
        for(GlobalTempObservation gto: GTO2){
            html = html + "Year:  " + gto.year + " AverageTemp: "+gto.avgTemp +"<br>";
        }

        ArrayList<Population> pop2 = jdbc.getPopulation();
        for(Population pop3: pop2){
            html = html +"  Year:  "+pop3.year+"  Population:  "+pop3.population+"<br>";
            //+"    Population-->   "+pop3.population
        }
        html = html + "</ul>";
        
         html = html + "<h2>How can i use ClimateCC?  </h2>" + "<ul>";
       html = html + "<p> The Global Temperature and Population page gives all the Information of Temperature and Population of Countries and Entire world as a whole where user can retrive data based of various filters and can sort the data as well </p>" + "<ul>";
       html = html + "<p> The State/ City Temperature Change page gives all the Information of Temperature and Population of State and Cities of Countrie user can retrive data based of various filters and can sort the data as well </p>" + "<ul>";
       html = html + "<p> TheTemperature Extended Periods page gives all the Information of Temperature and Population Changes of Countries/State/Cities and  Entire world as a whole where user can retrive data based of various filters and can sort the data as well </p>" + "<ul>";
       html = html + "<p> The Temperature Trends page gives all the Information of Temperature and Population Temperature of Extended Periods where user can compare between them  </p>" + "<ul>";
        // Close Content div
        html = html + "</div>";
        
        // Footer
        html = html + """
            <div class='footer'>
            <p>COSC2803 - Studio Project Starter Code (Apr23)</p>
            </div>
            """;
            
            // Finish the HTML webpage
            html = html + "</body>" + "</html>";
            

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }




}

