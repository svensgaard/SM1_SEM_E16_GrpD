package Models;

/**
 * Created by Dan on 22-11-2016.
 */

public class GeoReport {
    private String ID;
    private long latitude;
    private long longitude;

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

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
