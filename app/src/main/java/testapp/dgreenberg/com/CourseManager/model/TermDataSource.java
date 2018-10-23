package testapp.dgreenberg.com.CourseManager.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class TermDataSource {

    public TermDataSource() {
    }

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_TERM_ID,
            MySQLiteHelper.COLUMN_TERM_TITLE,
            MySQLiteHelper.COLUMN_TERM_START,
            MySQLiteHelper.COLUMN_TERM_END};

    public TermDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Term createTerm(String termTitle, String termStart, String termEnd) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TERM_TITLE, termTitle);
        values.put(MySQLiteHelper.COLUMN_TERM_START, termStart);
        values.put(MySQLiteHelper.COLUMN_TERM_END, termEnd);;

        long insertId = database.insert(MySQLiteHelper.TABLE_TERM, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_TERM,
                allColumns, MySQLiteHelper.COLUMN_TERM_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Term newTerm = cursorToTerm(cursor);
        cursor.close();
        System.out.println(
                " CREATED!: TITLE " + newTerm.getName()+
                " START: " + newTerm.getStart() +
                " END: " + newTerm.getEnd());
        return newTerm;

    }

    public Term updateTerm(Long termID, String termTitle, String termStart, String termEnd) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TERM_TITLE, termTitle);
        values.put(MySQLiteHelper.COLUMN_TERM_START, termStart);
        values.put(MySQLiteHelper.COLUMN_TERM_END, termEnd);

        String strFilter = "termID=" + termID;

        long rowsUpdated = database.update(MySQLiteHelper.TABLE_TERM, values, strFilter, null);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_TERM,
                allColumns, MySQLiteHelper.COLUMN_TERM_ID + " = " + termID, null,
                null, null, null);
        cursor.moveToFirst();
        Term updatedTerm = cursorToTerm(cursor);
        cursor.close();
        System.out.println(
                        " UPDATED!: Term ID " + updatedTerm.getId() +
                        " TITLE " + updatedTerm.getName()+
                        " START: " + updatedTerm.getStart() +
                        " END: " + updatedTerm.getEnd());

        return updatedTerm;

    }








    public void deleteTerm(Term term) {
        long id = term.getId();
        System.out.println("Term deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_TERM, MySQLiteHelper.COLUMN_TERM_ID
                + " = " + id, null);
    }

    public ArrayList<Term> getAllTerms() {
        ArrayList<Term> terms = new ArrayList<Term>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_TERM,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Term term = cursorToTerm(cursor);
            terms.add(term);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return terms;
    }

    private Term cursorToTerm(Cursor cursor) {
        Term term = new Term();
        term.setId(cursor.getLong(0));
        term.setName(cursor.getString(1));
        term.setStart(cursor.getString(2));
        term.setEnd(cursor.getString(3));

        return term;
    }
}