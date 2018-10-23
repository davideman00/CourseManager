package testapp.dgreenberg.com.CourseManager.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class CourseDataSource {

    public CourseDataSource() {
    }

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_COURSE_ID,
            MySQLiteHelper.COLUMN_CTERM_ID,
            MySQLiteHelper.COLUMN_COURSE_TITLE,
            MySQLiteHelper.COLUMN_COURSE_START,
            MySQLiteHelper.COLUMN_COURSE_END,
            MySQLiteHelper.COLUMN_COURSE_STATUS,
            MySQLiteHelper.COLUMN_COURSE_CMNAME,
            MySQLiteHelper.COLUMN_COURSE_CMPHONE,
            MySQLiteHelper.COLUMN_COURSE_CMEMAIL};

    public CourseDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Course createCourse(String courseTitle, String courseStart, String courseEnd, String courseStatus, String courseMentorName, String courseMentorPhone, String courseMentorEmail) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_CTERM_ID, "not null");
        values.put(MySQLiteHelper.COLUMN_COURSE_TITLE, courseTitle);
        values.put(MySQLiteHelper.COLUMN_COURSE_START, courseStart);
        values.put(MySQLiteHelper.COLUMN_COURSE_END, courseEnd);
        values.put(MySQLiteHelper.COLUMN_COURSE_STATUS, courseStatus);
        values.put(MySQLiteHelper.COLUMN_COURSE_CMNAME, courseMentorName);
        values.put(MySQLiteHelper.COLUMN_COURSE_CMPHONE, courseMentorPhone);
        values.put(MySQLiteHelper.COLUMN_COURSE_CMEMAIL, courseMentorEmail);
        long insertId = database.insert(MySQLiteHelper.TABLE_COURSE, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COURSE,
                allColumns, MySQLiteHelper.COLUMN_COURSE_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Course newCourse = cursorToCourse(cursor);
        cursor.close();
        System.out.println(
                " CREATED!: TITLE " + newCourse.getName()+
                " START: " + newCourse.getStart() +
                " END: " + newCourse.getEnd() +
                " STATUS: " + newCourse.getStatus() +
                " CMNAME: " + newCourse.getCourseMentorName() +
                " CMPHONE: " + newCourse.getCourseMentorPhone() +
                " CMEMAIL: " + newCourse.getCourseMentorEmail());
        return newCourse;

    }

    public Course updateCourse(Long courseID, String courseTitle, String courseStart, String courseEnd, String courseStatus, String courseMentorName, String courseMentorPhone, String courseMentorEmail) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_COURSE_TITLE, courseTitle);
        values.put(MySQLiteHelper.COLUMN_COURSE_START, courseStart);
        values.put(MySQLiteHelper.COLUMN_COURSE_END, courseEnd);
        values.put(MySQLiteHelper.COLUMN_COURSE_STATUS, courseStatus);
        values.put(MySQLiteHelper.COLUMN_COURSE_CMNAME, courseMentorName);
        values.put(MySQLiteHelper.COLUMN_COURSE_CMPHONE, courseMentorPhone);
        values.put(MySQLiteHelper.COLUMN_COURSE_CMEMAIL, courseMentorEmail);

        String strFilter = "courseID=" + courseID;

        long rowsUpdated = database.update(MySQLiteHelper.TABLE_COURSE, values, strFilter, null);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_COURSE,
                allColumns, MySQLiteHelper.COLUMN_COURSE_ID + " = " + courseID, null,
                null, null, null);
        cursor.moveToFirst();
        Course updatedCourse = cursorToCourse(cursor);
        cursor.close();
        System.out.println(
                        " UPDATED!: COURSE ID " + updatedCourse.getId() +
                        " TITLE " + updatedCourse.getName()+
                        " START: " + updatedCourse.getStart() +
                        " END: " + updatedCourse.getEnd() +
                        " STATUS: " + updatedCourse.getStatus() +
                        " CMNAME: " + updatedCourse.getCourseMentorName() +
                        " CMPHONE: " + updatedCourse.getCourseMentorPhone() +
                        " CMEMAIL: " + updatedCourse.getCourseMentorEmail());
        return updatedCourse;

    }

    public Course updateCourseTermID(Long courseID, Long termID) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_CTERM_ID, termID);


        String strFilter = "courseID=" + courseID;

        long rowsUpdated = database.update(MySQLiteHelper.TABLE_COURSE, values, strFilter, null);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_COURSE,
                allColumns, MySQLiteHelper.COLUMN_COURSE_ID + " = " + courseID, null,
                null, null, null);
        cursor.moveToFirst();
        Course updatedCourse = cursorToCourse(cursor);
        cursor.close();
        System.out.println(
                " UPDATED!: COURSE ID " + updatedCourse.getId() +
                        " CTERMID " + updatedCourse.getTermId()+ " rowsupdated: "+rowsUpdated);
        return updatedCourse;

    }






    public void deleteCourse(Course course) {
        long id = course.getId();
        System.out.println("Course deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_COURSE, MySQLiteHelper.COLUMN_COURSE_ID
                + " = " + id, null);
    }

    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> courses = new ArrayList<Course>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_COURSE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Course course = cursorToCourse(cursor);
            courses.add(course);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return courses;
    }

    private Course cursorToCourse(Cursor cursor) {
        Course course = new Course();
        course.setId(cursor.getLong(0));
        course.setTermId(cursor.getLong(1));
        course.setName(cursor.getString(2));
        course.setStart(cursor.getString(3));
        course.setEnd(cursor.getString(4));
        course.setStatus(cursor.getString(5));
        course.setCourseMentorName(cursor.getString(6));
        course.setCourseMentorPhone(cursor.getString(7));
        course.setCourseMentorEmail(cursor.getString(8));

        return course;
    }
}