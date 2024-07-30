package app;

public class Population {
    public String countryname;
    public String countrycode;
    public int year;
    public float population;

    public Population(){}


    public Population(String countryname,String countrycode,int year,float population){
        this.countryname = countryname;
        this.countrycode = countrycode;
        this.year = year;
        this.population = population;
    }

}
