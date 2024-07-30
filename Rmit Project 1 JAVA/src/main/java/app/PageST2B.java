package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import app.JDBCConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class PageST2B implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page2B.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Subtask 2.2</title>";

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
                <h1><img src='logo1.png' class='top-image' alt='Climate CC Photo' height='75'>Subtask 2.B</h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        html = html + """
            <form action='/page2B.html' method='post'>
            
            <div class='formcs'>
                <label for='selectcountry'>Name of the Country (Textbox): </label>
                <input class='selectcountry' id='selectcountry' name='selectcountry'>
            </div>
            
            <div class='formcs'>
            <label for='CityState_drop'>Select the City/State (DropDown): </label>
                <select id='CityState_drop' name='CityState_drop'>
                    <option value='City'>City</option>
                    <option value='State'>State</option>
                </select>
            </div>

            <div class='formcs'>
                <label for='year1'>Select starting year (Textbox)</label>
                <input class='form-control' id='year1' name='year1'>
            </div>

            <div class='formcs'>
                <label for='year2'>Select ending year (Textbox)</label>
                <input class='form-control' id='year2' name='year2'>
            </div>

            <button type='submit' class='btn btn-primary'>Get all the data!!</button>

            </form>
            """;
        ArrayList<String> topCities = new ArrayList<>();
        ArrayList<String> topStates = new ArrayList<>();
        String selectcountry = context.formParam("selectcountry");
        String CityState_drop = context.formParam("CityState_drop");
        if (selectcountry == null || selectcountry == "") {
            html = html + "<h2><i>Please enter the name of the country</i></h2>";
        } else if (CityState_drop == null) {
            html = html + "<h2><i>No Results to show for textbox</i></h2>";
        } else {
            if (CityState_drop.equals("City")) {
            JDBCConnection jdbc = new JDBCConnection();
            String year1 = context.formParam("year1");
            if (year1 == null || year1 == "") {
                html = html + "<h2><i>No Results to show for textbox</i></h2>";
            } else {
                String year2 = context.formParam("year2");
                if (year2 == null || year2 == "") {
                    html = html + "<h2><i>No Results to show for textbox</i></h2>";
                } else {
                // If NOT NULL, then lookup the movie by type!
                //Top 3 Cities
                topCities = jdbc.getTopCitiesWithMaxTempDifference(selectcountry, Integer.parseInt(year1), Integer.parseInt(year2));
                html += "<h2>Top 3 Cities with Biggest Temperature Difference</h2>";
                    for (int i = 0; i < Math.min(3, topCities.size()); i++) {
                        html += "<p>" + topCities.get(i) + "</p>";
                    }
                
                html += outputCity(selectcountry, year1, year2);
                }
            }
            } else if (CityState_drop.equals("State")) {
                String year1 = context.formParam("year1");
                JDBCConnection jdbc = new JDBCConnection();
                if (year1 == null || year1 == "") {
                    html = html + "<h2><i>No Results to show for textbox</i></h2>";
                } else {
                String year2 = context.formParam("year2");
                if (year2 == null || year2 == "") {
                    html = html + "<h2><i>No Results to show for textbox</i></h2>";
                } else {
                // If NOT NULL, then lookup the movie by type!
                //Top 3 states
                topStates = jdbc.getTopStatesWithMaxTempDifference(selectcountry, Integer.parseInt(year1), Integer.parseInt(year2));
                html += "<h2>Top 3 States with Biggest Temperature Difference</h2>";
                for (int i = 0; i < Math.min(3, topStates.size()); i++) {
                    html += "<p>" + topStates.get(i) + "</p>";
                }
                

                html += outputState(selectcountry, year1, year2);
                }
                }
            }
        }

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

    public String outputCity(String country, String year1, String year2) {
    JDBCConnection jdbc = new JDBCConnection();
    ArrayList<String> temperatureData = jdbc.cityTempDiff(country, Integer.parseInt(year1), Integer.parseInt(year2));

    String tableHtml = "<table>";
    tableHtml = tableHtml + "<tr><th>City</th><th>Temperature Difference</th></tr>";

    for (String data : temperatureData) {
        // Split the data string into individual values
        String[] values = data.split(", ");

        String cityName = values[0].split(": ")[1];
        String tempDifference = values[1].split(": ")[1];

        tableHtml = tableHtml + "<tr><td>" + cityName + "</td><td>" + tempDifference + "</td></tr>";
    }

    tableHtml = tableHtml + "</table>";

    return tableHtml;
}
public String outputState(String country, String year1, String year2) {
    JDBCConnection jdbc = new JDBCConnection();
    ArrayList<String> temperatureData = jdbc.stateTempDiff(country, Integer.parseInt(year1), Integer.parseInt(year2));

    String tableHtml = "<table>";
    tableHtml = tableHtml + "<tr><th>State</th><th>Temperature Difference</th></tr>";

    for (String data : temperatureData) {
        // Split the data string into individual values
        String[] values = data.split(", ");

        String stateName = values[0].split(": ")[1];
        String tempDifference = values[1].split(": ")[1];

        tableHtml = tableHtml + "<tr><td>" + stateName + "</td><td>" + tempDifference + "</td></tr>";
    }

    tableHtml = tableHtml + "</table>";

    return tableHtml;
}

}
