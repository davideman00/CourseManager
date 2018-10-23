package testapp.dgreenberg.com.CourseManager.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class AssessmentDataSource {

    public AssessmentDataSource() {
    }

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ASSESSMENT_ID,
            MySQLiteHelper.COLUMN_ASSESSMENT_ACOURSEID,
            MySQLiteHelper.COLUMN_ASSESSMENT_TITLE,
            MySQLiteHelper.COLUMN_ASSESSMENT_TYPE,
            MySQLiteHelper.COLUMN_ASSESSMENT_DUE,
            MySQLiteHelper.COLUMN_ASSESSMENT_GOAL};

    public AssessmentDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Assessment createAssessment(String assessmentTitle, String assessmentType, String assessmentDue, String assessmentGoal) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ASSESSMENT_ACOURSEID, "not null'");
        values.put(MySQLiteHelper.COLUMN_ASSESSMENT_TITLE, assessmentTitle);
        values.put(MySQLiteHelper.COLUMN_ASSESSMENT_TYPE, assessmentType);
        values.put(MySQLiteHelper.COLUMN_ASSESSMENT_DUE, assessmentDue);
        values.put(MySQLiteHelper.COLUMN_ASSESSMENT_GOAL, assessmentGoal);;

        long insertId = database.insert(MySQLiteHelper.TABLE_ASSESSMENT, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_ASSESSMENT,
                allColumns, MySQLiteHelper.COLUMN_ASSESSMENT_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Assessment newAssessment = cursorToAssessment(cursor);
        cursor.close();
        return newAssessment;

    }

    public Assessment updateAssessment(Long assessmentID, String assessmentTitle, String assessmentType, String assessmentDue, String assessmentGoal) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ASSESSMENT_TITLE, assessmentTitle);
        values.put(MySQLiteHelper.COLUMN_ASSESSMENT_TYPE, assessmentType);
        values.put(MySQLiteHelper.COLUMN_ASSESSMENT_DUE, assessmentDue);
        values.put(MySQLiteHelper.COLUMN_ASSESSMENT_GOAL, assessmentGoal);;

        String strFilter = "assessmentID=" + assessmentID;

        long rowsUpdated = database.update(MySQLiteHelper.TABLE_ASSESSMENT, values, strFilter, null);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_ASSESSMENT,
                allColumns, MySQLiteHelper.COLUMN_ASSESSMENT_ID + " = " + assessmentID, null,
                null, null, null);
        cursor.moveToFirst();
        Assessment updatedAssessment = cursorToAssessment(cursor);
        cursor.close();
        return updatedAssessment;

    }

    public Assessment updateAssessmentCourseID(Long assessmentID, Long courseID) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ASSESSMENT_ACOURSEID, courseID);

        String strFilter = "assessmentID=" + assessmentID;

        long rowsUpdated = database.update(MySQLiteHelper.TABLE_ASSESSMENT, values, strFilter, null);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_ASSESSMENT,
                allColumns, MySQLiteHelper.COLUMN_ASSESSMENT_ID + " = " + assessmentID, null,
                null, null, null);
        cursor.moveToFirst();
        Assessment updatedAssessment = cursorToAssessment(cursor);
        cursor.close();
        return updatedAssessment;

    }




    public void deleteAssessment(Assessment assessment) {
        long id = assessment.getId();
        database.delete(MySQLiteHelper.TABLE_ASSESSMENT, MySQLiteHelper.COLUMN_ASSESSMENT_ID
                + " = " + id, null);
    }

    public ArrayList<Assessment> getAllAssessments() {
        ArrayList<Assessment> assessments = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_ASSESSMENT,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Assessment assessment = cursorToAssessment(cursor);
            assessments.add(assessment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return assessments;
    }

    private Assessment cursorToAssessment(Cursor cursor) {
        Assessment assessment = new Assessment();
        assessment.setId(cursor.getLong(0));
        assessment.setCourseID(cursor.getLong(1));
        assessment.setTitle(cursor.getString(2));
        assessment.setType(cursor.getString(3));
        assessment.setDueDate(cursor.getString(4));
        assessment.setGoalDate(cursor.getString(5));

        return assessment;
    }
}