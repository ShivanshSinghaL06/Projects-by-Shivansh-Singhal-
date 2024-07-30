package app;

public class GlobalTempObservation {
    
    public int year;
    public float avgTemp;
    public float minTemp;
    public float maxTemp;
    public float LOavgTemp;
    public float LOminTemp;
    public float LOmaxTemp;

    public GlobalTempObservation(){

    }

    public GlobalTempObservation(int  year,float avgTemp ,float minTemp,float maxTemp, float LOavgTemp ,float LOminTemp,float LOmaxTemp){
        this.year = year;
        this.avgTemp = avgTemp;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.LOavgTemp = LOavgTemp;
        this.LOminTemp = LOminTemp;
        this.LOmaxTemp = LOmaxTemp;
    }


}

