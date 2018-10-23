package testapp.dgreenberg.com.CourseManager.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import testapp.dgreenberg.com.CourseManager.R;
import testapp.dgreenberg.com.CourseManager.model.AssessmentDataSource;
import testapp.dgreenberg.com.CourseManager.model.CourseDataSource;
import testapp.dgreenberg.com.CourseManager.model.NoteDataSource;
import testapp.dgreenberg.com.CourseManager.model.TermDataSource;

public class MainActivity extends AppCompatActivity {

    private Button mBtnLaunchCourseList;
    private Button mBtnLaunchTermsList;
    private Button mBtnLaunchAssessmentsList;

    private TermDataSource termDataSource;
    private AssessmentDataSource assessmentDataSource;
    private CourseDataSource courseDataSource;
    private NoteDataSource noteDataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    //BUTTONS---------------------------------------------------------------------------------------
        mBtnLaunchCourseList = (Button) findViewById(R.id.btnCourseList);
        mBtnLaunchCourseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCourseList();
            }
        });

        mBtnLaunchTermsList = (Button) findViewById(R.id.btnTermsList);
        mBtnLaunchTermsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchTermsList();
            }
        });

        mBtnLaunchAssessmentsList = (Button) findViewById(R.id.btnAssessmentsList);
        mBtnLaunchAssessmentsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAssessmentsList();
            }
        });

        mBtnLaunchCourseList = (Button) findViewById(R.id.load_sample);
        mBtnLaunchCourseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadSampleData();
            }
        });


    }

    //MENU------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
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



    //BUTTON METHODS--------------------------------------------------------------------------------

    private void launchCourseList() {
        Intent intent = new Intent(this, CourseList.class);
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

    private void loadSampleData(){
        termDataSource = new TermDataSource(this);
        termDataSource.open();

        courseDataSource = new CourseDataSource(this);
        courseDataSource.open();

        assessmentDataSource = new AssessmentDataSource(this);
        assessmentDataSource.open();

        noteDataSource = new NoteDataSource(this);
        noteDataSource.open();

        termDataSource.createTerm("TermTitle1","8/1/18", "12/1/18" );
        termDataSource.createTerm("TermTitle2","8/1/18", "12/1/18" );
        termDataSource.createTerm("TermTitle3","8/1/18", "12/1/18" );

        courseDataSource.createCourse("CourseTitle1", "8/26/18", "8/26/18", "Complete", "Bob", "555-555-5555", "Bob@Bob.com");
        courseDataSource.createCourse("CourseTitle2", "9/1/18", "10/1/18", "Current", "Jim", "555-555-5555", "Jim@Jim.com");
        courseDataSource.createCourse("CourseTitle3", "10/1/18", "11/1/18", "Not Started", "Sue", "555-555-5555", "Sue@Sue.com");

        assessmentDataSource.createAssessment("Test1", "Objective", "9/1/18", "8/25/18");
        assessmentDataSource.createAssessment("Test2", "Performance", "10/1/18", "10/25/18");
        assessmentDataSource.createAssessment("Test3", "Objective", "11/1/18", "11/25/18");

        noteDataSource.createNote(1, "note1Ttitle", "note1Text");
        noteDataSource.createNote(1, "note2Ttitle", "note2Text");
        noteDataSource.createNote(2, "note3Ttitle", "note3Text");


        Context context = getApplicationContext();
        CharSequence text = "Sample data generated!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();



    }


}
