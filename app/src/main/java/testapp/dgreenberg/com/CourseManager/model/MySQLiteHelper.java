package testapp.dgreenberg.com.CourseManager.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_COURSE = "courses";
    public static final String COLUMN_COURSE_ID = "courseID";
    public static final String COLUMN_CTERM_ID = "cTermID";
    public static final String COLUMN_COURSE_TITLE = "title";
    public static final String COLUMN_COURSE_START = "courseStart";
    public static final String COLUMN_COURSE_END = "courseEnd";
    public static final String COLUMN_COURSE_STATUS = "status";
    public static final String COLUMN_COURSE_CMNAME = "cmName";
    public static final String COLUMN_COURSE_CMPHONE = "cmPhone";
    public static final String COLUMN_COURSE_CMEMAIL = "cmEmail";


    public static final String TABLE_TERM = "terms";
    public static final String COLUMN_TERM_ID = "termID";
    public static final String COLUMN_TERM_TITLE = "termTitle";
    public static final String COLUMN_TERM_START = "termStart";
    public static final String COLUMN_TERM_END = "termEnd";

    public static final String TABLE_ASSESSMENT = "assessments";
    public static final String COLUMN_ASSESSMENT_ID = "assessmentID";
    public static final String COLUMN_ASSESSMENT_ACOURSEID = "aCourseID";
    public static final String COLUMN_ASSESSMENT_TITLE = "assessmentTitle";
    public static final String COLUMN_ASSESSMENT_TYPE = "assessmentType";
    public static final String COLUMN_ASSESSMENT_DUE = "assessmentDue";
    public static final String COLUMN_ASSESSMENT_GOAL = "assessmentGoal";

    public static final String TABLE_NOTE = "notes";
    public static final String COLUMN_NOTE_ID = "noteID";
    public static final String COLUMN_NOTE_NCOURSEID = "nCourseID";
    public static final String COLUMN_NOTE_TITLE = "noteTitle";
    public static final String COLUMN_NOTE_TEXT = "noteText";



    private static final String DATABASE_NAME = "coursemanager.db";
    private static final int DATABASE_VERSION = 10;

    // Database creation sql statement
    private static final String TABLE_COURSE_CREATE =
            "create table "
            + TABLE_COURSE + "( " + COLUMN_COURSE_ID + " integer primary key autoincrement, "
            + COLUMN_CTERM_ID + " not null, "
            + COLUMN_COURSE_TITLE + " coursetitle, "
            + COLUMN_COURSE_START + " coursestart, "
            + COLUMN_COURSE_END + " courseend, "
            + COLUMN_COURSE_STATUS + " status, "
            + COLUMN_COURSE_CMNAME + " name, "
            + COLUMN_COURSE_CMPHONE + " phone, "
            + COLUMN_COURSE_CMEMAIL + " email); ";


    private static final String TABLE_TERM_CREATE =
            "create table "
            + TABLE_TERM + "( " + COLUMN_TERM_ID + " integer primary key autoincrement, "
            + COLUMN_TERM_TITLE + " termtitle, "
            + COLUMN_TERM_START + " termstart, "
            + COLUMN_TERM_END + " termend); ";

    private static final String TABLE_ASSESSMENT_CREATE =
            "create table "
            + TABLE_ASSESSMENT + "( "
            + COLUMN_ASSESSMENT_ID + " integer primary key autoincrement, "
            + COLUMN_ASSESSMENT_ACOURSEID + " not null, "
            + COLUMN_ASSESSMENT_TITLE + " assessmenttitle, "
            + COLUMN_ASSESSMENT_TYPE + " assessmenttype, "
            + COLUMN_ASSESSMENT_DUE + " assessmentdue, "
            + COLUMN_ASSESSMENT_GOAL + " assessmentgoal); ";

    private static final String TABLE_NOTE_CREATE =
            "create table "
            + TABLE_NOTE + "( "
            + COLUMN_NOTE_ID + " integer primary key autoincrement, "
            + COLUMN_NOTE_NCOURSEID + " not null, "
            + COLUMN_NOTE_TITLE + " notetitle, "
            + COLUMN_NOTE_TEXT + " notetext); ";



    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL(TABLE_COURSE_CREATE);
        database.execSQL(TABLE_TERM_CREATE);
        database.execSQL(TABLE_ASSESSMENT_CREATE);
        database.execSQL(TABLE_NOTE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSESSMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        onCreate(db);
    }

}