package Models;

/**
 * Created by Dan on 22-11-2016.
 */

public class GeoReport {
    private String ID;
    private double latitude;
    private double longitude;

    public GeoReport() {
    }

    public GeoReport(String ID, long latitude, long longitude) {
        this.ID = ID;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getID() {
        return ID;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
