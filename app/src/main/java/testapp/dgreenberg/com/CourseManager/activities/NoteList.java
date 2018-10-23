package testapp.dgreenberg.com.CourseManager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import testapp.dgreenberg.com.CourseManager.R;
import testapp.dgreenberg.com.CourseManager.model.Course;
import testapp.dgreenberg.com.CourseManager.model.Note;
import testapp.dgreenberg.com.CourseManager.model.NoteAssocListAdapter;
import testapp.dgreenberg.com.CourseManager.model.NoteDataSource;

public class NoteList extends AppCompatActivity {
    private NoteDataSource noteDataSource;
    private Button mBtnAddNote;
    private Button mBtnBacktoCourse;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_list);

        Course selectedCourse = Course.selectedCourse;

        Note.selectedNote = null;

        noteDataSource = new NoteDataSource(this);
        noteDataSource.open();

        ArrayList<Note> notes = noteDataSource.getAllAssocNotes(selectedCourse.getId());


        //BUTTONS-----------------------------------------------------------------------------------
        mBtnAddNote = (Button) findViewById(R.id.add);
        mBtnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchNoteAdd();
            }
        });


        mBtnBacktoCourse = (Button) findViewById(R.id.back);
        mBtnBacktoCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCourseEdit();
            }
        });



        //LIST ADAPTER------------------------------------------------------------------------------
        //instantiate custom adapter
        NoteAssocListAdapter adapter = new NoteAssocListAdapter(notes, this);

        //handle listview and assign adapter
        ListView lView = (ListView) findViewById(R.id.notes_listview);
        lView.setAdapter(adapter);

        TextView courseTitleView = (TextView) findViewById(R.id.assoc_course_title);
        courseTitleView.setText("Notes for: " + Course.selectedCourse.getName());

    }



    public NoteList() {
    }


    //MENU------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_list, menu);
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

    private void launchNoteAdd() {
        Intent intent = new Intent(this, NoteEdit.class);
        startActivity(intent);
    }

    private void launchCourseEdit() {
        Intent intent = new Intent(this, CourseEdit.class);
        startActivity(intent);
    }

}


