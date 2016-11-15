package Database;

import android.provider.BaseColumns;

import java.sql.Blob;

/**
 * Created by Mads on 08-11-2016.
 */
public final class Contract {
    //Private constructor to prevent initialisation
    private Contract(){}

    private static final String BLOB_TYPE = " BLOB";
    private static final String TEXT_TYPE = " TEXT";
    private static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_TABLES =
            "CREATE TABLE " + ReportEntry.TABLE_NAME + " (" +
                    ReportEntry._ID + "INTEGER PRIMARY KEY, " +
                    ReportEntry.COLUMN_NAME_EMNE + TEXT_TYPE + COMMA_SEP +
                    ReportEntry.COLUMN_NAME_ELEMENT + TEXT_TYPE + COMMA_SEP +
                    ReportEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    ReportEntry.COLUMN_NAME_IMAGE + BLOB_TYPE + COMMA_SEP +
                    ReportEntry.COLUMN_NAME_LONGITUDE + REAL_TYPE + COMMA_SEP +
                    ReportEntry.COLUMN_NAME_LATITUDE + REAL_TYPE + COMMA_SEP +
                    ReportEntry.COLUMN_NAME_TIMESTAMP + TEXT_TYPE + COMMA_SEP +
                    ReportEntry.COLUMN_NAME_OPRINDELSE + TEXT_TYPE + COMMA_SEP +
                    ReportEntry.COLUMN_NAME_NEAR_ADDRESS + TEXT_TYPE + COMMA_SEP +
                    ReportEntry.COLUMN_NAME_USERTYPE + TEXT_TYPE +
                    " )";

    /* Inner class that defines the table contents */
    public static class ReportEntry implements BaseColumns {
        public static final String TABLE_NAME = "report";
        public static final String COLUMN_NAME_EMNE = "emne";
        public static final String COLUMN_NAME_ELEMENT = "element";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_IMAGE = "image";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME_OPRINDELSE = "oprindelse";
        public static final String COLUMN_NAME_NEAR_ADDRESS = "near_address";
        public static final String COLUMN_NAME_USERTYPE = "usertype";

    }
}
