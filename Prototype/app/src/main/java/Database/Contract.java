package Database;

import android.provider.BaseColumns;

/**
 * Created by Mads on 08-11-2016.
 */
public final class Contract {
    //Private constructor to prevent initialisation
    private Contract(){}

    private static final String BLOB_TYPE = " BLOB";
    private static final String TEXT_TYPE = " TEXT";
    private static final String REAL_TYPE = " REAL";
    private static final String INTEGER_TYPE = " INTEGER";
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
                    ReportEntry.COLUMN_NAME_POINTS + INTEGER_TYPE +
                    " )" +

                    "CREATE TABLE " + CommentEntry.TABLE_NAME + " ("+
                    CommentEntry._ID + "INTEGER PRIMARY KEY, " +
                    CommentEntry.COLUMN_NAME_IMAGE + BLOB_TYPE + COMMA_SEP +
                    CommentEntry.COLUMN_NAME_TEXT + TEXT_TYPE + COMMA_SEP +
                    CommentEntry.COLUMN_NAME_points + INTEGER_TYPE + COMMA_SEP +
                    " )"
            ;

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
        public static final String COLUMN_NAME_POINTS = "points";

    }
    public static class CommentEntry implements BaseColumns{
        public static final String TABLE_NAME = "comment";
        public static final String COLUMN_NAME_TEXT = "text";
        public static final String COLUMN_NAME_IMAGE = "image";
        public static final String COLUMN_NAME_points = "points";


    }
}
