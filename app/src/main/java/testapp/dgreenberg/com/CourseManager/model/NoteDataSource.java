package testapp.dgreenberg.com.CourseManager.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class NoteDataSource {

    public NoteDataSource() {
    }

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_NOTE_ID,
            MySQLiteHelper.COLUMN_NOTE_NCOURSEID,
            MySQLiteHelper.COLUMN_NOTE_TITLE,
            MySQLiteHelper.COLUMN_NOTE_TEXT};

    public NoteDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Note createNote(long nCourseID, String noteTitle, String noteText) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NOTE_NCOURSEID, nCourseID);
        values.put(MySQLiteHelper.COLUMN_NOTE_TITLE, noteTitle);
        values.put(MySQLiteHelper.COLUMN_NOTE_TEXT, noteText);

        long insertId = database.insert(MySQLiteHelper.TABLE_NOTE, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_NOTE,
                allColumns, MySQLiteHelper.COLUMN_NOTE_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Note newNote = cursorToNote(cursor);
        cursor.close();
        System.out.println(
                " CREATED!: NOTE ID " + newNote.getId()+
                        "COURSE ID: " + newNote.getCourseID() +
                        " NOTE TITLE: " + newNote.getTitle() +
                        " NOTE TEXT: " + newNote.getText());

        return newNote;

    }

    public Note updateNote(Long noteID, Long nCourseID, String noteTitle, String noteText) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NOTE_NCOURSEID, nCourseID);
        values.put(MySQLiteHelper.COLUMN_NOTE_TITLE, noteTitle);
        values.put(MySQLiteHelper.COLUMN_NOTE_TEXT, noteText);

        String strFilter = "noteID=" + noteID;

        long rowsUpdated = database.update(MySQLiteHelper.TABLE_NOTE, values, strFilter, null);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_NOTE,
                allColumns, MySQLiteHelper.COLUMN_NOTE_ID + " = " + noteID, null,
                null, null, null);
        cursor.moveToFirst();
        Note updatedNote = cursorToNote(cursor);
        cursor.close();
        System.out.println(
                " CREATED!: NOTE ID " + updatedNote.getId()+
                        "COURSE ID: " + updatedNote.getCourseID() +
                        " NOTE TITLE: " + updatedNote.getTitle() +
                        " NOTE TEXT: " + updatedNote.getText());


        return updatedNote;


    }

    public void deleteNote(Note note) {
        long id = note.getId();
        database.delete(MySQLiteHelper.TABLE_NOTE, MySQLiteHelper.COLUMN_NOTE_ID
                + " = " + id, null);
    }

    public ArrayList<Note> getAllAssocNotes(Long courseID) {
        ArrayList<Note> notes = new ArrayList<>();

        String strFilter = MySQLiteHelper.COLUMN_NOTE_NCOURSEID + " = " + courseID;
        Cursor cursor = database.query(MySQLiteHelper.TABLE_NOTE,
                allColumns, strFilter,null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Note note = cursorToNote(cursor);
            notes.add(note);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return notes;
    }

    private Note cursorToNote(Cursor cursor) {
        Note note = new Note();
        note.setId(cursor.getLong(0));
        note.setCourseID(cursor.getLong(1));
        note.setTitle(cursor.getString(2));
        note.setText(cursor.getString(3));

        return note;
    }
}