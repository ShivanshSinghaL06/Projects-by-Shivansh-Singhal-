package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PageST2A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page2A.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Subtask 2.1</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add the topnav
        // This uses a Java v15+ Text Block
        html = html + """
            <div class='topnav'>
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
                <img src='logo1.png' class='top-image' alt='Climate CC Photo' height='75'>Subtask 2.A</h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

         /* Add HTML for the web form
         * We are giving two ways here
         *  - one for a text box
         *  - one for a drop down
         * 
         * Whitespace is used to help us understand the HTML!
         * 
         * IMPORTANT! the action speicifes the URL for POST!
         */
        html = html + "<form action='/page2A.html' method='post'>";
        
        html = html + "   <div class='form-group'>";
        html = html + "      <label for='CountryWorld_drop'>Select the Country/World (Dropdown):</label>";
        html = html + "      <select id='CountryWorld_drop' name='CountryWorld_drop'>";  //DROPDOWN
        html = html + "         <option>Countries</option>";
        html = html + "         <option>World</option>";
        html = html + "      </select>";
        html = html + "   </div>";

        html = html + "   <div class='form-group'>";
        html = html + "      <label for='year1_textbox'>Select first Year (Textbox)</label>";
        html = html + "      <input class='form-control' id='year1_textbox' name='year1_textbox'>"; //TESTBOX
        html = html + "   </div>";

        html = html + "   <div class='form-group'>";
        html = html + "      <label for='year2_textbox'>Select Second Year (Textbox)</label>";
        html = html + "      <input class='form-control' id='year2_textbox' name='year2_textbox'>"; //TESTBOX
        html = html + "   </div>";

        html = html + "   <div class='form-group'>";
        html = html + "      <label for='Sort_drop'>Sort data by : (Dropdown):</label>";
        html = html + "      <select id='Sort_drop' name='Sort_drop'>";  //DROPDOWN
        html = html + "         <option>Temperature ASCENDING</option>";
        html = html + "         <option>Temperature DESCENDING</option>";
        html = html + "         <option>Population ASCENDING</option>";
        html = html + "         <option>Population DESCENDING</option>";
        html = html + "      </select>";
        html = html + "   </div>";

        html = html + "   <button type='submit' class='btn btn-primary'>Get all the data!!!</button>"; //BUTTON TO SUBMIT THE FORM

        html = html + "</form>";

        /* Get the Form Data
         *  from the drop down list
         * Need to be Careful!!
         *  If the form is not filled in, then the form will return null!
        */
        
        
        String CountryWorld_drop = context.formParam("CountryWorld_drop"); // Dropdown String returned to java
        // String movietype_drop = context.queryParam("movietype_drop");  (NOT NEEDED)
        if (CountryWorld_drop == null) {
            // If NULL, nothing to show, therefore we make some "no results" HTML
            html = html + "<h2><i>No Results to show for dropbox</i></h2>";
        } 
        else {
            //If NOT NULL, then lookup the movie by type!
            //if(CountryWorld_drop == "Countries")
            // html = html + outputdata1(CountryWorld_drop);
            //html = html + "<h1>"+CountryWorld_drop+"</h1>";
            if(CountryWorld_drop.equals("Countries")){

                String year1_textbox = context.formParam("year1_textbox");
                if (year1_textbox == null || year1_textbox == "") {
                 // If NULL, nothing to show, therefore we make some "no results" HTML
                html = html + "<h2><i>No Results to show for textbox</i></h2>";
                } 
                else {
                    // If NOT NULL, then lookup the movie by type!
                        //html = html + outputdata1(year1_textbox);
                        String year2_textbox = context.formParam("year2_textbox");
                        if (year2_textbox == null || year2_textbox == "") {
                            // If NULL, nothing to show, therefore we make some "no results" HTML
                            html = html + "<h2><i>No Results to show for textbox</i></h2>";
                        } 
                        else {
                            // If NOT NULL, then lookup the movie by type!
                            String Sort_drop = context.formParam("Sort_drop");
                                if( Sort_drop == null || Sort_drop ==""){
                                    String Sd = "";
                                    html = html + outputdata1(year1_textbox,year2_textbox,Sd);}
                                else if(Sort_drop.equals("Temperature ASCENDING")){
                                    String Sd= "order by  avgtemp asc";
                                    html = html + outputdata1(year1_textbox,year2_textbox,Sd);}
                                else if(Sort_drop.equals("Temperature DESCENDING")){
                                    String Sd= "order by  avgtemp desc";
                                    html = html + outputdata1(year1_textbox,year2_textbox,Sd);}
                                else if(Sort_drop.equals("Population ASCENDING")){
                                    String Sd= "order by  population asc";
                                    html = html + outputdata1(year1_textbox,year2_textbox,Sd);}
                                else if(Sort_drop.equals("Population DESCENDING")){
                                    String Sd= "order by  population desc";
                                    html = html + outputdata1(year1_textbox,year2_textbox,Sd);}

    
                 }}
    
                
            }
            else{
                
                String year1_textbox = context.formParam("year1_textbox");
                if (year1_textbox == null || year1_textbox == "") {
                 // If NULL, nothing to show, therefore we make some "no results" HTML
                html = html + "<h2><i>No Results to show for textbox</i></h2>";
                } else {
            // If NOT NULL, then lookup the movie by type!
            //html = html + outputdata1(year1_textbox);
            String year2_textbox = context.formParam("year2_textbox");
            if (year1_textbox == null || year1_textbox == "") {
                // If NULL, nothing to show, therefore we make some "no results" HTML
                html = html + "<h2><i>No Results to show for textbox</i></h2>";
            } else {
                // If NOT NULL, then lookup the movie by type!
                html = html + outputdata2(year1_textbox,year2_textbox);
                 }}

            }
           
            }
        // String year1_textbox = context.formParam("year1_textbox");
        // if (year1_textbox == null || year1_textbox == "") {
        //     // If NULL, nothing to show, therefore we make some "no results" HTML
        //     html = html + "<h2><i>No Results to show for textbox</i></h2>";
        // } else {
        //     // If NOT NULL, then lookup the movie by type!
        //     //html = html + outputdata1(year1_textbox);
        //     String year2_textbox = context.formParam("year2_textbox");
        //     if (year1_textbox == null || year1_textbox == "") {
        //         // If NULL, nothing to show, therefore we make some "no results" HTML
        //         html = html + "<h2><i>No Results to show for textbox</i></h2>";
        //     } else {
        //         // If NOT NULL, then lookup the movie by type!
        //         html = html + outputdata1(year1_textbox,year2_textbox);
        //          }}
    
    
    
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
    
    // public String outputdata1(String year1,String year2) {
    //     String html = "";
    //     html = html + "<h2>" + "Country Data between  " +year1+"  to  "+year2+"</h2>";

    //     // Look up movies from JDBC

    //     JDBCConnection jdbc = new JDBCConnection();
    //     ArrayList<Population> var1 = jdbc.getpopulationdata(year1,year2);

    //      html = html + "<div class='row'><div class='column'><table class = 'table'><tr><th>Country Name</th><th>CountryCode</th><th>Population Difference</th></tr>";
    //         for (Population p1 : var1) {
    //         html = html + "<tr><td>" + p1.countryname+ "</td><td>" + p1.countrycode +"</td><td>"+p1.population+"</td></tr>";
    //     }
    //     html = html + "</table>  </div>";

    //      ArrayList<CountryTempObservation> var2 = jdbc.getCountrydata(year1,year2);
    //         // + "<h2>" +" Temperature data is as follows</h2>"
    //      html = html  +"<div class='column'><table class = 'table'><tr><th>Temperature Difference</th></tr>"; //<th>Country Name</th>
    //         for (CountryTempObservation p2 : var2) {
    //         html = html + "<tr><td>" + p2.avgtemp + "</td></tr>"; //<td>" + p2.countryname+ "</td>
    //     }
    //     html = html + "</table>   </div></div>";

    //     return html;}


    
        public String outputdata2(String year1,String year2) {
        String html = "";
        html = html + "<h2>" + "World Data between  " +year1+"  to  "+year2+"</h2>";

        // Look up movies from JDBC

        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<Population> var1 = jdbc.getpopulationdifference(year1,year2);

         html = html + "<div class='row'><div class='column'><table class = 'table'><tr><th>Population Difference</th></tr>";
            for (Population p1 : var1) {
            html = html + "<tr><td>" + p1.population+ "</td></tr>";
        }
        html = html + "</table>  </div>";

         ArrayList<GlobalTempObservation> var2 = jdbc.getGlobalTempDifference(year1,year2);
            // + "<h2>" +" Temperature data is as follows</h2>"
         html = html  +"<div class='column'><table class = 'table'><tr><th>Temperature Difference</th></tr>"; //<th>Country Name</th>
            for (GlobalTempObservation p2 : var2) {
            html = html + "<tr><td>" + p2.avgTemp + "</td></tr>"; //<td>" + p2.countryname+ "</td>
        }
        html = html + "</table>   </div></div>";

        return html;}



        public String outputdata1(String year1,String year2,String Sd) {
        String html = "";
        html = html + "<h2>" + "Country Data between  " +year1+"  to  "+year2+"</h2>";

        // Look up movies from JDBC

        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<countrypop2a> var1 = jdbc.getCountrydata(year1,year2,Sd);

         html = html + "<div class='row'><div class='column'><table class = 'table'><tr><th>Country Name</th><th>CountryCode</th><th>Population Difference</th><th>Temperature Difference</th></tr>";
            for (countrypop2a p1 : var1) {
            html = html + "<tr><td>" + p1.CountryName+ "</td><td>" + p1.countrycode +"</td><td>"+p1.population+"</td>"+"</td><td>"+p1.avgtemp+"</td></tr>";
            // html = html + "<tr><td>" + p1+"</td></tr>";
        }
        html = html + "</table>  </div>";

        return html;}


    }

