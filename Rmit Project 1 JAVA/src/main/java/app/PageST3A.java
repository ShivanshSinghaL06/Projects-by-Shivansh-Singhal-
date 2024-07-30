package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class PageST3A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page3A.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Subtask 3.1</title>";

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
                <h1><img src='logo1.png' class='top-image' alt='Climate CC Photo' height='75'>Subtask 3.A</h1>
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
        html = html + "<form action='/page3A.html' method='post'>";
        
        html = html + "   <div class='form-group'>";
        html = html + "      <label for='CountryWorld_drop'>Select the Region (Dropdown):</label>";
        html = html + "      <select id='CountryWorld_drop' name='CountryWorld_drop'>";  //DROPDOWN  Region
        html = html + "         <option>Countries</option>";
        html = html + "         <option>State</option>";
        html = html + "         <option>City</option>";
        html = html + "         <option>World</option>";
        html = html + "      </select>";
        html = html + "   </div>";


        html = html + "   <div class='form-group'>";
        html = html + "      <label for='CCS_textbox'>Select Country/City/State</label>";
        html = html + "      <input class='form-control' id='CCS_textbox' name='CCS_textbox'>"; //TESTBOX  Country/City/State
        html = html + "   </div>";

        html = html + "   <div class='form-group'>";
        html = html + "      <label for='year1_textbox'>Select Starting Year </label>";
        html = html + "      <input class='form-control' id='year1_textbox' name='year1_textbox'>"; //TESTBOX  Starting Year 1
        html = html + "   </div>";

        html = html + "   <div class='form-group'>";
        html = html + "      <label for='year2_textbox'>Select Time Period </label>";
        html = html + "      <input class='form-control' id='year2_textbox' name='year2_textbox'>"; //TESTBOX  TimePeriod
        html = html + "   </div>";

        // html = html + "   <div class='form-group'>";
        // html = html + "      <input type= 'radio' id='html' name='fav_language' value='YES'>"; // RADIO BOX 1 
        // html = html + "      <label for='html'>YES</label><br>"; 
        // html = html + "   </div>";

        // html = html + "   <div class='form-group'>";
        // html = html + "      <input type= 'radio' id='html2' name='fav_language' value='NO'>";  // RADIO BOX 2
        // html = html + "      <label for='html2'>NO</label><br>"; 
        // html = html + "   </div>";

        html = html + "   <div class='form-group'>";
        html = html + "      <label for='year3_textbox'>Select Starting Year (1)</label>";
        html = html + "      <input class='form-control' id='year3_textbox' name='year3_textbox'>"; //TESTBOX  Starting Year 2
        html = html + "   </div>";

        html = html + "   <div class='form-group'>";
        html = html + "      <label for='year4_textbox'>Select Starting Year (2)</label>";
        html = html + "      <input class='form-control' id='year4_textbox' name='year4_textbox'>"; //TESTBOX  Starting Year 3
        html = html + "   </div>";

        html = html + "   <div class='form-group'>";
        html = html + "      <label for='year5_textbox'>Select Starting Year (3)</label>";
        html = html + "      <input class='form-control' id='year5_textbox' name='year5_textbox'>"; //TESTBOX  Starting Year 4
        html = html + "   </div>";


        html = html + "   <div class='form-group'>";
        html = html + "      <label for='CCS_textbox1'>Select Country/City/State to compae with</label>";
        html = html + "      <input class='form-control' id='CCS_textbox1' name='CCS_textbox1'>"; //TESTBOX  Country/City/State to conpare with
        html = html + "   </div>";


        

        // html = html + "   <div class='form-group'>";
        // html = html + "      <label for='Sort_drop'>Sort data by : (Dropdown):</label>";
        // html = html + "      <select id='Sort_drop' name='Sort_drop'>";  //DROPDOWN
        // html = html + "         <option>Temperature ASCENDING</option>";
        // html = html + "         <option>Temperature DESCENDING</option>";
        // html = html + "         <option>Population ASCENDING</option>";
        // html = html + "         <option>Population DESCENDING</option>";
        // html = html + "      </select>";
        // html = html + "   </div>";


        html = html + "   <button type='submit' class='btn btn-primary'>Get all the data!!!</button>"; //BUTTON TO SUBMIT THE FORM

        html = html + "</form>";
        // Add HTML for the page content
        html = html + """
            <p>Subtask 3.A page content</p>
            """;
        
        String CountryWorld_drop = context.formParam("CountryWorld_drop"); // Dropdown String returned to java
        if(CountryWorld_drop == null){
            html = html + "<h2><i>No Results to show for dropbox</i></h2>";
        }
        else{
            if(CountryWorld_drop.equals("Countries")){
                String countrynametxt = context.formParam("CCS_textbox");
                if(countrynametxt == null){
                     html = html + "<h2><i>No Results to show for textbox</i></h2>";
                }
                else{
                    int year1_textbox = Integer.valueOf(context.formParam("year1_textbox"));
                    if (year1_textbox == 0 ) {
                        html = html + "<h2><i>No Results to show for textbox</i></h2>";
                    } 
                    else {
                        int year2_textbox =  Integer.valueOf(context.formParam("year2_textbox"));
                        if (year2_textbox == 0) {
                        html = html + "<h2><i>No Results to show for textbox</i></h2>";}
                        else{
                            html = html + outputdata31(year1_textbox,year2_textbox,countrynametxt);



                            int year3_textbox = Integer.valueOf(context.formParam("year3_textbox"));
                            if(year3_textbox == 0){
                                html = html + "<h2><i>No Results to show for textbox</i></h2>";
                                }
                            else{
                                //System.out.println(year3_textbox);
                                int year4_textbox = Integer.valueOf(context.formParam("year4_textbox"));
                                if(year4_textbox == 0 ){
                                    html = html + "<h2><i>No Results to show for textbox</i></h2>";
                                }
                                else{
                                    //System.out.println(year4_textbox);
                                    int year5_textbox = Integer.valueOf(context.formParam("year5_textbox"));
                                    if(year5_textbox == 0){
                                    html = html + "<h2><i>No Results to show for textbox</i></h2>";
                                    }
                                    else{
                                        //System.out.println(year5_textbox);
                                        html = html + outputdata32(year1_textbox,year2_textbox,year3_textbox,year4_textbox,year5_textbox,countrynametxt);

                                        String countrynametxt1 = context.formParam("CCS_textbox1");
                                        if(countrynametxt1 == null){
                                             html = html + "<h2><i>No Results to show for textbox</i></h2>";
                                        }
                                        else{
                                            html = html + outputdata33(year1_textbox, year2_textbox, year3_textbox, year4_textbox, year5_textbox, countrynametxt,countrynametxt1);
                                        }
                                        
                                    }}}}}}}
            

            else if(CountryWorld_drop.equals("State")){


                String statenametxt = context.formParam("CCS_textbox");
                if(statenametxt == null){
                     html = html + "<h2><i>No Results to show for textbox</i></h2>";
                    }
                else{
                    int year1_textbox = Integer.valueOf(context.formParam("year1_textbox"));
                    if (year1_textbox == 0 ) {
                        html = html + "<h2><i>No Results to show for textbox</i></h2>";
                    } 
                    else {
                        int year2_textbox =  Integer.valueOf(context.formParam("year2_textbox"));
                        if (year2_textbox == 0) {
                        html = html + "<h2><i>No Results to show for textbox</i></h2>";}
                        else{
                            html = html + outputdata311(year1_textbox,year2_textbox,statenametxt);


                            int year3_textbox = Integer.valueOf(context.formParam("year3_textbox"));
                            if(year3_textbox == 0){
                                html = html + "<h2><i>No Results to show for textbox</i></h2>";
                                }
                            else{
                                //System.out.println(year3_textbox);
                                int year4_textbox = Integer.valueOf(context.formParam("year4_textbox"));
                                if(year4_textbox == 0 ){
                                    html = html + "<h2><i>No Results to show for textbox</i></h2>";
                                }
                                else{
                                    //System.out.println(year4_textbox);
                                    int year5_textbox = Integer.valueOf(context.formParam("year5_textbox"));
                                    if(year5_textbox == 0){
                                    html = html + "<h2><i>No Results to show for textbox</i></h2>";
                                    }
                                    else{
                                        //System.out.println(year5_textbox);
                                        html = html + outputdata322(year1_textbox,year2_textbox,year3_textbox,year4_textbox,year5_textbox,statenametxt);

                                        String statenametxt1 = context.formParam("CCS_textbox1");
                                        if(statenametxt == ""){
                                             html = html + "<h2><i>No Results to show for textbox</i></h2>";
                                        }
                                        else{
                                            html = html + outputdata323(year1_textbox, year2_textbox, year3_textbox, year4_textbox, year5_textbox, statenametxt,statenametxt1);
                                        }
                                        
                            
                        } }}}
                    }


             }
                    
                }

            else if(CountryWorld_drop.equals("City")){


                 String statenametxt = context.formParam("CCS_textbox");
                if(statenametxt == null){
                     html = html + "<h2><i>No Results to show for textbox</i></h2>";
                    }
                else{
                    int year1_textbox = Integer.valueOf(context.formParam("year1_textbox"));
                    if (year1_textbox == 0 ) {
                        html = html + "<h2><i>No Results to show for textbox</i></h2>";
                    } 
                    else {
                        int year2_textbox =  Integer.valueOf(context.formParam("year2_textbox"));
                        if (year2_textbox == 0) {
                        html = html + "<h2><i>No Results to show for textbox</i></h2>";}
                        else{
                            html = html + outputdata3111(year1_textbox,year2_textbox,statenametxt);


                            int year3_textbox = Integer.valueOf(context.formParam("year3_textbox"));
                            if(year3_textbox == 0){
                                html = html + "<h2><i>No Results to show for textbox</i></h2>";
                                }
                            else{
                                //System.out.println(year3_textbox);
                                int year4_textbox = Integer.valueOf(context.formParam("year4_textbox"));
                                if(year4_textbox == 0 ){
                                    html = html + "<h2><i>No Results to show for textbox</i></h2>";
                                }
                                else{
                                    //System.out.println(year4_textbox);
                                    int year5_textbox = Integer.valueOf(context.formParam("year5_textbox"));
                                    if(year5_textbox == 0){
                                    html = html + "<h2><i>No Results to show for textbox</i></h2>";
                                    }
                                    else{
                                        //System.out.println(year5_textbox);
                                        html = html + outputdata3222(year1_textbox,year2_textbox,year3_textbox,year4_textbox,year5_textbox,statenametxt);

                                        String statenametxt1 = context.formParam("CCS_textbox1");
                                        if(statenametxt == ""){
                                             html = html + "<h2><i>No Results to show for textbox</i></h2>";
                                        }
                                        else{
                                            html = html + outputdata3233(year1_textbox, year2_textbox, year3_textbox, year4_textbox, year5_textbox, statenametxt,statenametxt1);
                                        }
                                        
                            
                        } }}}
                    }


             }
            }


            else if(CountryWorld_drop.equals("World")){
                int year1_textbox = Integer.valueOf(context.formParam("year1_textbox"));
                    if (year1_textbox == 0 ) {
                        html = html + "<h2><i>No Results to show for textbox</i></h2>";
                    } 
                    else {
                        int year2_textbox =  Integer.valueOf(context.formParam("year2_textbox"));
                        if (year2_textbox == 0) {
                        html = html + "<h2><i>No Results to show for textbox</i></h2>";}
                        else{
                            html = html + outputdata31112(year1_textbox,year2_textbox);

            }}}
    
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



        public String outputdata31(int year1,int year2,String cname) {
        String html = "";
        html = html + "<h2>" + "Avgtemp Data"+"</h2>";

        // Look up movies from JDBC
        System.out.println(year1);
        System.out.println(year2);

        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<CountryTempObservation> var1 = jdbc.level31function(year1,year2,cname);

         html = html + "<div class='row'><div class='column'><table class = 'table'><tr><th>Average Temperature Difference</th></tr>";
            for (CountryTempObservation p1 : var1) {
            html = html + "<tr><td>" + p1.avgtemp+ "</td></tr>";
        }
        html = html + "</table>  </div>";

        return html;}


        public String outputdata32(int year1,int year2,int year3,int year4,int year5,String cname) {
        String html = "";
        html = html + "<h2>" + "Average Temp Data having multiple years"+"</h2>";

        // Look up movies from JDBC

        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<level3p2> var1 = jdbc.level32function(year1,year2,year3,year4,year5,cname);

         html = html + "<div class='row'><div class='column'><table class = 'table'><tr><th>Range</th><th>Average Temperature</th></tr>";
            for (level3p2 p1 : var1) {
            html = html + "<tr><td>" + p1.range +"</td><td>"+p1.avgtemp2+"</td></tr>";
        }
        html = html + "</table>  </div>";

        return html;}


         public String outputdata33(int year1,int year2,int year3,int year4,int year5,String cname,String cname2) {
        String html = "";
        html = html + "<h2>" + "Average Temp Data having multiple years and regions"+"</h2>";

        // Look up movies from JDBC

        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<level3p3> var1 = jdbc.level33function(year1,year2,year3,year4,year5,cname,cname2);

         html = html + "<div class='row'><div class='column'><table class = 'table'><tr><th>Year</th><th>Average Temperature1</th><th>Average Temperature2</th><th>Temperature Difference</th></tr>";
            for (level3p3 p1 : var1) {
                System.out.println(p1.year);
                System.out.println(p1.avgtempl31);
                System.out.println(p1.avgtempl32);
            html = html + "<tr><td>" + p1.year +"</td><td>"+p1.avgtempl31+"</td><td>"+p1.avgtempl32+"</td><td>"+p1.avgdiffl31+"</td></tr>";
        }
        html = html + "</table>  </div>";

        return html;}
    

        public String outputdata311(int year1,int year2,String cname) {

        String html = "";
        html = html + "<h2>" + "Avgtemp Data"+"</h2>";

        // Look up movies from JDBC
        System.out.println(year1);
        System.out.println(year2);

        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<StateTempObservation> var1 = jdbc.level31function1(year1,year2,cname);

         html = html + "<div class='row'><div class='column'><table class = 'table'><tr><th>Average Temperature</th></tr>";
            for (StateTempObservation p1 : var1) {
            System.out.println(p1.avgtempstate);
            html = html + "<tr><td>" + p1.avgtempstate+ "</td></tr>";
        }
        html = html + "</table>  </div>";

        return html;}

         public String outputdata322(int year1,int year2,int year3,int year4,int year5,String cname) {
        String html = "";
        html = html + "<h2>" + "Average Temp Data for multiple years"+"</h2>";

        // Look up movies from JDBC

        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<StateTempObservation> var1 = jdbc.level32function2(year1,year2,year3,year4,year5,cname);

         html = html + "<div class='row'><div class='column'><table class = 'table'><tr><th>Range</th><th>Average Temperature</th></tr>";
            for (StateTempObservation p1 : var1) {
            html = html + "<tr><td>" + p1.Countrynamestate +"</td><td>"+p1.avgtempstate+"</td></tr>";
        }
        html = html + "</table>  </div>";

        return html;}
    
        public String outputdata323(int year1,int year2,int year3,int year4,int year5,String cname,String cname2) {
        String html = "";
        html = html + "<h2>" + "Average Temp Data for multiple years and multiple regions"+"</h2>";

        // Look up movies from JDBC

        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<StateTempObservation> var1 = jdbc.level33function3(year1,year2,year3,year4,year5,cname,cname2);

         html = html + "<div class='row'><div class='column'><table class = 'table'><tr><th>Year</th><th>Average Temperature1</th><th>Average Temperature2</th><th>Temperature Difference</th></tr>";
            for (StateTempObservation p1 : var1) {
                System.out.println(p1.year);
                System.out.println(p1.avgtempstate);
                System.out.println(p1.maxtempstate);
                System.out.println(p1.mintempstate);
            html = html + "<tr><td>" + p1.year +"</td><td>"+p1.avgtempstate+"</td><td>"+p1.mintempstate+"</td><td>"+p1.maxtempstate+"</td></tr>";
        }
        html = html + "</table>  </div>";

        return html;}
    

        //CITY


      public String outputdata3111(int year1,int year2,String cname) {

        String html = "";
        html = html + "<h2>" + "Avgtemp Data"+"</h2>";

        // Look up movies from JDBC
        System.out.println(year1);
        System.out.println(year2);

        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<CityTempObservation> var1 = jdbc.level31function11(year1,year2,cname);

         html = html + "<div class='row'><div class='column'><table class = 'table'><tr><th>Average Temperature</th></tr>";
            for (CityTempObservation p1 : var1) {
            System.out.println(p1.avgtempc);
            html = html + "<tr><td>" + p1.avgtempc+ "</td></tr>";
        }
        html = html + "</table>  </div>";

        return html;}

         public String outputdata3222(int year1,int year2,int year3,int year4,int year5,String cname) {
        String html = "";
        html = html + "<h2>" +  "Average Temp Data for multiple years"+"</h2>";

        // Look up movies from JDBC

        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<CityTempObservation> var1 = jdbc.level32function22(year1,year2,year3,year4,year5,cname);

         html = html + "<div class='row'><div class='column'><table class = 'table'><tr><th>Range</th><th>Average Temperature</th></tr>";
            for (CityTempObservation p1 : var1) {
            html = html + "<tr><td>" + p1.year +"</td><td>"+p1.avgtempc+"</td></tr>";
        }
        html = html + "</table>  </div>";

        return html;}
    
        public String outputdata3233(int year1,int year2,int year3,int year4,int year5,String cname,String cname2) {
        String html = "";
        html = html + "<h2>" + "Average Temp Data for multiple years and multiple regions"+"</h2>";

        // Look up movies from JDBC

        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<CityTempObservation> var1 = jdbc.level33function33(year1,year2,year3,year4,year5,cname,cname2);

         html = html + "<div class='row'><div class='column'><table class = 'table'><tr><th>Year</th><th>Average Temperature1</th><th>Average Temperature2</th><th>Temperature Difference</th></tr>";
            for (CityTempObservation p1 : var1) {
                System.out.println(p1.year);
                System.out.println(p1.avgtempc);
                System.out.println(p1.maxtempc);
                System.out.println(p1.mintempc);
            html = html + "<tr><td>" + p1.year +"</td><td>"+p1.avgtempc+"</td><td>"+p1.mintempc+"</td><td>"+p1.maxtempc+"</td></tr>";
        }
        html = html + "</table>  </div>";

        return html;}
        
         public String outputdata31112(int year1,int year2) {

        String html = "";
        html = html + "<h2>" + "Avgtemp Data"+"</h2>";

        // Look up movies from JDBC
        System.out.println(year1);
        System.out.println(year2);

        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<worldtempobservation> var1 = jdbc.level33function34(year1,year2);

         html = html + "<div class='row'><div class='column'><table class = 'table'><tr><th>Average Temperature</th></tr>";
            for (worldtempobservation p1 : var1) {
            System.out.println(p1.avgtemp);
            html = html + "<tr><td>" + p1.avgtemp+ "</td></tr>";
        }
        html = html + "</table>  </div>";

        return html;}
    
    
    }
