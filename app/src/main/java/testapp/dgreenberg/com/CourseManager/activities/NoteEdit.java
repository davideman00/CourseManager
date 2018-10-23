package testapp.dgreenberg.com.CourseManager.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import testapp.dgreenberg.com.CourseManager.R;
import testapp.dgreenberg.com.CourseManager.model.Course;
import testapp.dgreenberg.com.CourseManager.model.Note;
import testapp.dgreenberg.com.CourseManager.model.NoteDataSource;

public class NoteEdit extends AppCompatActivity {
    private Button mBtnLaunchSaveNote;
    private Button mBtnLaunchShareNote;

    private NoteDataSource datasource;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_edit);

        datasource = new NoteDataSource(this);
        datasource.open();

        final EditText editNoteTitle = (EditText)findViewById(R.id.editNoteTitle);
        final EditText editNoteText = (EditText)findViewById(R.id.editNoteText);

        if(Note.selectedNote != null){
            editNoteTitle.setText(Note.selectedNote.getTitle());
            editNoteText.setText(Note.selectedNote.getText());
        }




        //BUTTONS-----------------------------------------------------------------------------------

        mBtnLaunchSaveNote = (Button) findViewById(R.id.btnSaveNote);

        mBtnLaunchSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fieldsFilledTest()) {
                    String noteTitle = editNoteTitle.getText().toString();
                    String noteText = editNoteText.getText().toString();
                    long nCourseID = Course.selectedCourse.getId();

                    if(Note.selectedNote != null) {
                        long noteID = Note.selectedNote.getId();
                        datasource.updateNote(noteID, nCourseID, noteTitle, noteText);

                        Context context = getApplicationContext();
                        CharSequence text = "Note Updated!!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        launchNoteList();

                    }
                    else {
                        datasource.createNote(nCourseID, noteTitle, noteText);

                        Context context = getApplicationContext();
                        CharSequence text = "Note Saved!!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        launchNoteList();

                    }
                }
                else{
                    Context context = getApplicationContext();
                    CharSequence text = "Fill all fields!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

        mBtnLaunchShareNote = (Button) findViewById(R.id.btnShareNote);

        mBtnLaunchShareNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fieldsFilledTest()) {
                    String noteTitle = editNoteTitle.getText().toString();
                    String noteText = editNoteText.getText().toString();
                    share(noteTitle, noteText);
                }
                else{
                    Context context = getApplicationContext();
                    CharSequence text = "Fill all fields!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });







    }

    //TESTS-----------------------------------------------------------------------------------------
    private boolean fieldsFilledTest(){
        boolean Test = true;

        final EditText editNoteTitle = (EditText)findViewById(R.id.editNoteTitle);
        final EditText editNoteText = (EditText)findViewById(R.id.editNoteText);
        if(
                editNoteTitle.getText().toString().isEmpty() ||
                editNoteText.getText().toString().isEmpty())

        {
            Test = false;
            return Test;
        }
        return Test;
    }


    //MENU------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.main:
            launchMain();
            return(true);
        case R.id.terms:
            launchTermsList();
            return(true);
        case R.id.courses:
            launchCourseList();
            return(true);
        case R.id.assessments:
            launchAssessmentsList();
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }

    private void launchMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void launchTermsList() {
        Intent intent = new Intent(this, TermList.class);
        startActivity(intent);
    }

    private void launchAssessmentsList() {
        Intent intent = new Intent(this, AssessmentList.class);
        startActivity(intent);
    }
    private void launchCourseList() {
        Intent intent = new Intent(this, CourseList.class);
        startActivity(intent);
    }

    private void launchCourseEdit() {
        Intent intent = new Intent(this, CourseEdit.class);
        startActivity(intent);
    }

    private void launchNoteList() {
        Intent intent = new Intent(this, NoteList.class);
        startActivity(intent);
    }


    //SHARE TO SMS----------------------------------------------------------------------------------
    public void share(String title,  String note) {

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Title: " + title+ " Body: " + note);
        startActivity(Intent.createChooser(sharingIntent, "Share Via SMS"));
    }





    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

}
