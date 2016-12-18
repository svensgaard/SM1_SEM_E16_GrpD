package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Models.GeoReport;
import Utitlities.ImageUtils;
import Wrappers.CommentWrapper;
import Wrappers.ReportWrapper;

/**
 * Created by Mads on 08-11-2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "reports";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDatabase(db, 0, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDatabase(db, oldVersion, newVersion);
    }

    public long insertReport(SQLiteDatabase db, ReportWrapper reportWrapper) {
        ContentValues values = new ContentValues();
        values.put(Contract.ReportEntry.COLUMN_NAME_EMNE, reportWrapper.getEmne());
        values.put(Contract.ReportEntry.COLUMN_NAME_ELEMENT, reportWrapper.getElement());
        values.put(Contract.ReportEntry.COLUMN_NAME_DESCRIPTION, reportWrapper.getDescription());
        values.put(Contract.ReportEntry.COLUMN_NAME_IMAGE, codec(reportWrapper.getImage(), Bitmap.CompressFormat.JPEG, 30));
        values.put(Contract.ReportEntry.COLUMN_NAME_LONGITUDE, reportWrapper.getLongitude().toString());
        values.put(Contract.ReportEntry.COLUMN_NAME_LATITUDE, reportWrapper.getLatitude().toString());
        values.put(Contract.ReportEntry.COLUMN_NAME_TIMESTAMP, reportWrapper.getTimestamp());
        values.put(Contract.ReportEntry.COLUMN_NAME_OPRINDELSE, reportWrapper.getOprindelse());
        values.put(Contract.ReportEntry.COLUMN_NAME_NEAR_ADDRESS, reportWrapper.getNear_address());
        values.put(Contract.ReportEntry.COLUMN_NAME_USERTYPE, reportWrapper.getUsertype());
        values.put(Contract.ReportEntry.COLUMN_NAME_POINTS, reportWrapper.getPoints());
        values.put(Contract.ReportEntry.COLUMN_NAME_UPVOTED, reportWrapper.getIsUpvoted());
        values.put(Contract.ReportEntry.COLUMN_NAME_DOWNVOTED, reportWrapper.getIsDownvoted());
        long id = db.insert(Contract.ReportEntry.TABLE_NAME, null, values);
        return id;
    }

    public List<GeoReport> getAllGeoReports() {
        List<GeoReport> geoReportList = new LinkedList<GeoReport>();

        // 1. build the query
        String query = "SELECT * FROM " + Contract.ReportEntry.TABLE_NAME;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        GeoReport geoReport = null;
        if (cursor.moveToFirst()) {
            do {
                geoReport = new GeoReport();
                geoReport.setID(cursor.getString(1));
                geoReport.setLatitude(Double.parseDouble(cursor.getString(cursor.getColumnIndex(Contract.ReportEntry.COLUMN_NAME_LATITUDE))));
                geoReport.setLongitude(Double.parseDouble(cursor.getString(cursor.getColumnIndex(Contract.ReportEntry.COLUMN_NAME_LONGITUDE))));

                geoReportList.add(geoReport);
            } while (cursor.moveToNext());
        }

        Log.d("getAllBooks()", geoReportList.toString());

        return geoReportList;
    }

    public long insertComment(SQLiteDatabase db, CommentWrapper commentWrapper) {
        ContentValues values = new ContentValues();
        values.put(Contract.CommentEntry.COLUMN_NAME_TEXT, commentWrapper.getText());
        values.put(Contract.CommentEntry.COLUMN_NAME_POINTS, commentWrapper.getPoints());

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        if(commentWrapper.getImage() != null) {
            values.put(Contract.CommentEntry.COLUMN_NAME_IMAGE, codec(commentWrapper.getImage(), Bitmap.CompressFormat.JPEG, 30));
        }
        values.put(Contract.CommentEntry.COLUMN_NAME_REPORT_FK, commentWrapper.getReport_fk());
        long id = db.insert(Contract.CommentEntry.TABLE_NAME, null, values);
        return id;
    }

    private static byte[] codec(Bitmap src, Bitmap.CompressFormat format, int quality) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        src.compress(format, quality, os);

        return os.toByteArray();
    }

    public ArrayList<Integer> getAllIds() {
        String selectQuery = "SELECT " + Contract.ReportEntry._ID + " FROM " + Contract.ReportEntry.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<Integer> result = new ArrayList<>();

        if(cursor.moveToFirst()) {
            do {
                result.add(cursor.getInt(cursor.getColumnIndex(Contract.ReportEntry._ID)));
            } while (cursor.moveToNext());
        }
        return result;
    }

    public ReportWrapper getReport(int id) {
        String selectQuery = "SELECT * FROM " + Contract.ReportEntry.TABLE_NAME + " WHERE "+ Contract.ReportEntry._ID +" = '" + id + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ReportWrapper report = null;
        if(cursor.moveToFirst()) {
            report = new ReportWrapper(
                    cursor.getInt(0),
                    cursor.getString(cursor.getColumnIndex(Contract.ReportEntry.COLUMN_NAME_EMNE)),
                    cursor.getString(cursor.getColumnIndex(Contract.ReportEntry.COLUMN_NAME_ELEMENT)),
                    cursor.getString(cursor.getColumnIndex(Contract.ReportEntry.COLUMN_NAME_DESCRIPTION)),
                    Double.parseDouble(cursor.getString(cursor.getColumnIndex(Contract.ReportEntry.COLUMN_NAME_LONGITUDE))),
                    Double.parseDouble(cursor.getString(cursor.getColumnIndex(Contract.ReportEntry.COLUMN_NAME_LATITUDE))),
                    cursor.getString(cursor.getColumnIndex(Contract.ReportEntry.COLUMN_NAME_TIMESTAMP)),
                    cursor.getString(cursor.getColumnIndex(Contract.ReportEntry.COLUMN_NAME_OPRINDELSE)),
                    cursor.getString(cursor.getColumnIndex(Contract.ReportEntry.COLUMN_NAME_NEAR_ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(Contract.ReportEntry.COLUMN_NAME_USERTYPE)),
                    ImageUtils.getByteArrayAsBitmap(cursor.getBlob(cursor.getColumnIndex(Contract.ReportEntry.COLUMN_NAME_IMAGE))),
                    cursor.getInt(cursor.getColumnIndex(Contract.ReportEntry.COLUMN_NAME_POINTS))),
                    cursor.getInt(cursor.getColumnIndex(Contract.ReportEntry.COLUMN_NAME_UPVOTED)),
                    cursor.getInt(cursor.getColumnIndex(Contract.ReportEntry.COLUMN_NAME_DOWNVOTED));
            )
        }
        if(report != null) {
            return report;
        } else {
            return ReportWrapper.getDummyReport();
        }
    }

    public int upvoteReport(int id, int oldpoints) {
        int newPoints = oldpoints + 1;
        ContentValues values = new ContentValues();
        values.put(Contract.ReportEntry.COLUMN_NAME_POINTS, String.valueOf(newPoints));
        String where = Contract.ReportEntry._ID + "=?";
        String[] value = {String.valueOf(id)};
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(Contract.ReportEntry.TABLE_NAME, values, where, value);
        return newPoints;
    }

    public int downvoteReport(int id, int oldpoints) {
        int newPoints = oldpoints - 1;
        ContentValues values = new ContentValues();
        values.put(Contract.ReportEntry.COLUMN_NAME_POINTS, String.valueOf(newPoints));
        String where = Contract.ReportEntry._ID + "=?";
        String[] value = {String.valueOf(id)};
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(Contract.ReportEntry.TABLE_NAME, values, where, value);
        return newPoints;
    }

    public int upvoteComment(int id, int oldpoints) {
        int newPoints = oldpoints + 1;
        ContentValues values = new ContentValues();
        values.put(Contract.CommentEntry.COLUMN_NAME_POINTS, String.valueOf(newPoints));
        String where = Contract.CommentEntry._ID + "=?";
        String[] value = {String.valueOf(id)};
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(Contract.CommentEntry.TABLE_NAME, values, where, value);
        return newPoints;
    }

    public int downvoteComment(int id, int oldpoints) {
        int newPoints = oldpoints - 1;
        ContentValues values = new ContentValues();
        values.put(Contract.CommentEntry.COLUMN_NAME_POINTS, String.valueOf(newPoints));
        String where = Contract.CommentEntry._ID + "=?";
        String[] value = {String.valueOf(id)};
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(Contract.CommentEntry.TABLE_NAME, values, where, value);
        return newPoints;
    }

    public ArrayList<CommentWrapper> getComments(int reportID) {
        String selectQuery = "SELECT * FROM " + Contract.CommentEntry.TABLE_NAME + " WHERE "+ Contract.CommentEntry.COLUMN_NAME_REPORT_FK +" = '" + reportID + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<CommentWrapper> comments = new ArrayList<>();

        if(cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(Contract.CommentEntry._ID));
                String text = cursor.getString(cursor.getColumnIndex(Contract.CommentEntry.COLUMN_NAME_TEXT));
                Bitmap image = null;
                if(!cursor.isNull(cursor.getColumnIndex(Contract.CommentEntry.COLUMN_NAME_IMAGE))) {
                    image = ImageUtils.getByteArrayAsBitmap(cursor.getBlob(cursor.getColumnIndex(Contract.CommentEntry.COLUMN_NAME_IMAGE)));
                }
                int points = cursor.getInt(cursor.getColumnIndex(Contract.CommentEntry.COLUMN_NAME_POINTS));

                CommentWrapper comment = null;
                if(image != null) {
                    comment = new CommentWrapper(id, text, image, points, reportID);
                } else {
                    comment = new CommentWrapper(id, text, points, reportID);
                }
                comments.add(comment);
            } while (cursor.moveToNext());
        }
        return comments;
    }

    public CommentWrapper getComment(int commentId) {
        String selectQuery = "SELECT * FROM " + Contract.CommentEntry.TABLE_NAME + " WHERE "+ Contract.CommentEntry._ID +" = '" + commentId + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        CommentWrapper comment = null;

        if(cursor.moveToFirst()) {
                int id = commentId;
                String text = cursor.getString(cursor.getColumnIndex(Contract.CommentEntry.COLUMN_NAME_TEXT));
                Bitmap image = null;
                if(!cursor.isNull(cursor.getColumnIndex(Contract.CommentEntry.COLUMN_NAME_IMAGE))) {
                    image = ImageUtils.getByteArrayAsBitmap(cursor.getBlob(cursor.getColumnIndex(Contract.CommentEntry.COLUMN_NAME_IMAGE)));
                }
                int points = cursor.getInt(cursor.getColumnIndex(Contract.CommentEntry.COLUMN_NAME_POINTS));
                int reportID = cursor.getInt(cursor.getColumnIndex(Contract.CommentEntry.COLUMN_NAME_REPORT_FK));;
                if(image != null) {
                    comment = new CommentWrapper(id, text, image, points, reportID);
                } else {
                    comment = new CommentWrapper(id, text, points, reportID);
                }

        }
        return comment;
    }
    /* This method will update the database */
    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 1) {
            //Create database
            db.execSQL(Contract.SQL_CREATE_REPORT_TABLE);
            db.execSQL(Contract.SQL_CREATE_COMMENT_TABLE);
        }
        if(oldVersion < 2) {
            //Update 1 to database IE add a new column or table
            //Continue oldVersion < 3 when updating the database
        }
        if(oldVersion < 3) {

        }
    }
}
