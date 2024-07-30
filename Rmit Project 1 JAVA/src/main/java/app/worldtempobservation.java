package app;


public class worldtempobservation {
    public int year;
    public float avgtemp;
    public float mintemp;
    public float maxtemp;
    public float loavgtemp;
    public float lomintemp;
    public float lomaxtemp;

    public worldtempobservation(){
    }

    public worldtempobservation(int year,float avgtemp,float mintemp,float maxtemp,float loavgtemp,float lomintemp,float lomaxtemp){
        this.year = year;
        this.avgtemp = avgtemp;
        this.loavgtemp = loavgtemp;
        this.lomaxtemp = lomaxtemp;
        this.lomintemp = lomintemp;
        this.mintemp = mintemp;
        this.maxtemp = maxtemp;
    }
}
