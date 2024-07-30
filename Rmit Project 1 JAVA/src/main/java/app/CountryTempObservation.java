package app;
//DONT MAKE CHANGES OVER HERE WHOLE FILE ****

public class CountryTempObservation {
    public int year;
    public float avgtemp;
    public float mintemp;
    public float maxtemp;
    public String countryname;
    public String countrycode;
    public int population;

    public CountryTempObservation(int year, float avgtemp, float mintemp, float maxtemp, String countryname,int population,String countrycode){
        this.year = year;
        this.avgtemp= avgtemp;
        this.mintemp =mintemp;
        this.maxtemp =maxtemp;
        this.countryname =countryname;
        this.population = population;
        this.countrycode= countrycode;
    }
    public CountryTempObservation(){}

    public int getYear() {
        return year;
    }


    public float getAvgtemp() {
        return avgtemp;
    }

    public float getMintemp() {
        return mintemp;
    }

    public float getMaxtemp() {
        return maxtemp;
    }

    public String getCountryname() {
        return countryname;
    }

    
    
}
