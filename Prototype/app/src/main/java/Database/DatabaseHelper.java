package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

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
        values.put(Contract.ReportEntry.COLUMN_NAME_IMAGE, getBitmapAsByteArray(reportWrapper.getImage()));
        values.put(Contract.ReportEntry.COLUMN_NAME_LONGITUDE, reportWrapper.getLongitude());
        values.put(Contract.ReportEntry.COLUMN_NAME_LATITUDE, reportWrapper.getLatitude());
        values.put(Contract.ReportEntry.COLUMN_NAME_TIMESTAMP, reportWrapper.getTimestamp());
        values.put(Contract.ReportEntry.COLUMN_NAME_OPRINDELSE, reportWrapper.getOprindelse());
        values.put(Contract.ReportEntry.COLUMN_NAME_NEAR_ADDRESS, reportWrapper.getNear_address());
        values.put(Contract.ReportEntry.COLUMN_NAME_USERTYPE, reportWrapper.getUsertype());

        return db.insert(Contract.ReportEntry.TABLE_NAME, null, values);
    }
    //Method used to make the bitmap into a byte array.
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
    /* This method will update the database */
    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 1) {
            //Create database
            db.execSQL(Contract.SQL_CREATE_TABLES);
        }
        if(oldVersion < 2) {
            //Update 1 to database IE add a new column or table
            //Continue oldVersion < 3 when updating the database
        }
    }
}
