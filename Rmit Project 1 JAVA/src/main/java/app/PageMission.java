package app;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import io.javalin.http.Context;
import io.javalin.http.Handler;


public class PageMission implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/mission.html";


    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Our Mission</title>";

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
                Our Mission </h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        html = html + """
            <p>Find out about Our Mission</p>
            """;

        // This example uses JDBC to lookup the LGAs
        JDBCConnection jdbc = new JDBCConnection();

        // Next we will ask this *class* for the LGAs
        //ArrayList<LGA> lgas = jdbc.getLGAs2016();

        // Add HTML for the LGA list
        html = html + "<h1>Our Mission</h1>" + "<ul>";

        // Finally we can print out all of the LGAs
        // for (LGA lga : lgas) {
        //     html = html + "<li>" + lga.getCode()
        //                 + " - " + lga.getName() + "</li>";
        // }

        // Finish the List HTML
        html = html + "</ul>";
        
        html = html + """
                <p>Welcome to our website! Our mission is to develop a user-friendly web application that helps governments, scientists, and the general public analyze temperature patterns in the ocean and on land over a 260-year period. By providing comprehensive data and advanced analytical tools, we aim to enhance our understanding of climate change and its impact on our planet.
                </p>
                <p>Using our website, users can gain valuable insights and benefits in understanding climate change and its impact on our planet. By utilizing our advanced analytical tools, users can analyze temperature patterns, identify trends, and make informed decisions based on the data. Whether it's monitoring climate shifts, studying long-term temperature trends, or conducting research, our website empowers users to enhance their understanding of climate change and contribute to global efforts in mitigating its effects.
                </p>
                <p>The benefits of using our website are immense. Governments can leverage our platform to inform evidence-based policy decisions, relying on accurate and comprehensive temperature data to shape climate strategies. Scientists can dive into the dataset to conduct in-depth research, investigating the impacts of climate change on ecosystems and exploring potential mitigation measures. Additionally, the general public can gain a deeper understanding of climate change, fostering environmental awareness and inspiring individual action.
                </p>
                <p><b>Our Target Audiences</b></p>
                <p>Our target is to help people such as Elaina, Alex and Emma who all have common goals, to gather accurate data. 
                </p>
                """;
        
               
        // // get persona img
        // ArrayList<String> imageFilePaths = jdbc.getImageFilePaths(); 
        //     for (String imagePath : imageFilePaths) {
        //         html = html + "<div class='persona-container'><img src='" + imagePath + "'class='persona-image' alt='Image'></div>";
        //     }
        
        // // get persona attribute
        // ArrayList<String> attributeRows = jdbc.getPersonaAttributes();
        // for (String attributeRow : attributeRows) {
        // html = html + attributeRow + "<div class='description'><br><br></div>";
        // }
        // html = html + "</p>";

        //new loop to print both img and attribute
        // get persona img
        ArrayList<String> imageFilePaths = jdbc.getImageFilePaths(); 
        // get persona attribute
        ArrayList<String> attributeRows = jdbc.getPersonaAttributes();

        // Make sure the imageFilePaths and attributeRows have the same size
        int size = Math.min(imageFilePaths.size(), attributeRows.size());
        // Help from chatgpt to get size for both 

        for (int i = 0; i < size; i++) {
        String imagePath = imageFilePaths.get(i);
        String attributeRow = attributeRows.get(i);

        html = html + "<div class='persona-container'>";
        html = html + "<br><img src='" + imagePath + "' class='persona-image' alt='Image'>";
        html = html + "<div class='description'>" + attributeRow + "</div><br>";
        html = html + "</div>";
}

        //get student ID
        // html = html + "<p><b>Improvised by: </p></b>";
        // ArrayList<String> studentinfo = jdbc.getStudentInfo();
        // for (String studentinfos : studentinfo) {
        // html = html + studentinfos + "<br><br>";
        // }
        // html = html + "</p>";

        html = html + "<p><b>Improvised by: </b></p>";
        ArrayList<String> studentinfo = jdbc.getStudentInfo();
        int tableCount = 2; // Number of tables

        
        for (int i = 0; i < studentinfo.size(); i += tableCount) {
        html = html + "<table class='table-compact'>";
        html = html + "<tr><th>SID</th><th>Name</th><th>Email</th></tr>";
    
        for (int j = i; j < i + tableCount && j < studentinfo.size(); j++) {
        html = html + studentinfo.get(j);
        }
    
        html = html + "</table><br>";
        }
        html = html + "</p>";

        // Print the generated HTML using System.out
        System.out.print(html);



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
