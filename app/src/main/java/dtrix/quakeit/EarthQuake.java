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
    private int tsunami_response=0;
    private double lattitude =0;
    private double longitude =0;

    public EarthQuake(double magnitude, String location, long time, int tsunami_response, double lattitude, double longitude) {
        this.magnitude = magnitude;
        this.location = location;
        this.time = time;
        this.tsunami_response = tsunami_response;
        this.lattitude = lattitude;
        this.longitude = longitude;
    }

    public int getTsunami_response() {
        return tsunami_response;
    }

    public void setTsunami_response(int tsunami_response) {
        this.tsunami_response = tsunami_response;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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
