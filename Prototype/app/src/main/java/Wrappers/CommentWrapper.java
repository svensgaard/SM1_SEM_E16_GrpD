package Wrappers;

import android.graphics.Bitmap;

/**
 * Created by Mads on 22-11-2016.
 */
public class CommentWrapper {

    private int id;
    private String text;
    private Bitmap image;
    private int points;
    private int report_fk;

    public CommentWrapper(int id, String text, Bitmap image, int points, int report_fk) {
        this.id = id;
        this.text = text;
        this.image = image;
        this.points = points;
        this.report_fk = report_fk;
    }

    public CommentWrapper(int id, String text, int points, int report_fk) {
        this.id = id;
        this.text = text;
        this.points = points;
        this.report_fk = report_fk;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getReport_fk() {
        return report_fk;
    }

    public void setReport_fk(int report_fk) {
        this.report_fk = report_fk;
    }
}
