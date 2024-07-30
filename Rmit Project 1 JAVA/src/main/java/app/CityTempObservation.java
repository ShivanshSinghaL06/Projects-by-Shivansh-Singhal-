package app;

public class CityTempObservation {
    public int year;
    public float avgtempc;
    public float mintempc;
    public float maxtempc;
    public String cityname;
    public String countryname;
    public String Latitude;
    public String Longitude;
    
    public CityTempObservation(){}

    public CityTempObservation(int year,float avgtempc,float mintempc,float maxtempc,String cityname,String countryname, String Latitude, String Longitude){
        this.year = year;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.avgtempc = avgtempc;
        this.maxtempc = maxtempc;
        this.mintempc = mintempc;
        this.cityname = cityname;
        this.countryname = countryname;
    }
}
