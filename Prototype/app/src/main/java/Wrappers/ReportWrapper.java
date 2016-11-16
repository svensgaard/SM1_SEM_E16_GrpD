package Wrappers;

import android.graphics.Bitmap;

/**
 * Created by Mads on 08-11-2016.
 */
public class ReportWrapper {

    private int id;
    private String emne;
    private String element;
    private String description;
    private Bitmap image;
    private Long longitude;
    private Long latitude;
    private String timestamp;
    private String oprindelse;
    private String near_address;
    private String usertype;
    private Integer points;

    /*Constructor wihtout image*/
    public ReportWrapper(int id, String emne, String element, String description, Long longitude, Long latitude, String timestamp, String oprindelse, String near_address, String usertype, int points) {
        this.id = id;
        this.emne = emne;
        this.element = element;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.timestamp = timestamp;
        this.oprindelse = oprindelse;
        this.near_address = near_address;
        this.usertype = usertype;
        this.points = points;
    }
    /*Constructor with image */
    public ReportWrapper(int id, String emne, String element, String description, Long longitude, Long latitude, String timestamp, String oprindelse, String near_address, String usertype, Bitmap image, int points) {
        this.id = id;
        this.emne = emne;
        this.element = element;
        this.description = description;
        this.image = image;
        this.longitude = longitude;
        this.latitude = latitude;
        this.timestamp = timestamp;
        this.oprindelse = oprindelse;
        this.near_address = near_address;
        this.usertype = usertype;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmne() {
        return emne;
    }

    public void setEmne(String emne) {
        this.emne = emne;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getOprindelse() {
        return oprindelse;
    }

    public void setOprindelse(String oprindelse) {
        this.oprindelse = oprindelse;
    }

    public String getNear_address() {
        return near_address;
    }

    public void setNear_address(String near_address) {
        this.near_address = near_address;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
