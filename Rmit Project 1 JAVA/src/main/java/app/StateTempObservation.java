package app;

import java.nio.channels.FileLock;

public class StateTempObservation {
    public int year;
    public float avgtempstate;
    public float mintempstate;
    public float maxtempstate;
    public String Statename;
    public String Countrynamestate;

    public StateTempObservation(){}

    public StateTempObservation(int year,float avgtempstate,float mintempstate,float maxtempstate,String Statename,String Countrynamestate){
        this.year = year;
        this.Countrynamestate = Countrynamestate;
        this.Statename = Statename;
        this.avgtempstate = avgtempstate;
        this.maxtempstate = maxtempstate;
        this.mintempstate = mintempstate;
    }

}
