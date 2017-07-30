package dtrix.quakeit;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Soumya on 09-07-2017.
 */

public class EarthQuake {
    private double magnitude=0.0;
    private String location=null;
    private long time =0;

    public EarthQuake(double magnitude, String location, long time) {
        this.magnitude = magnitude;
        this.location = location;
        this.time = time;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}
