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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import testapp.dgreenberg.com.CourseManager.R;
import testapp.dgreenberg.com.CourseManager.model.Course;
import testapp.dgreenberg.com.CourseManager.model.CourseAssocListAdapter;
import testapp.dgreenberg.com.CourseManager.model.CourseDataSource;
import testapp.dgreenberg.com.CourseManager.model.Term;
import testapp.dgreenberg.com.CourseManager.model.TermDataSource;

public class TermEdit extends AppCompatActivity {
    private Button mBtnLaunchSaveTerm;

    private TermDataSource termDataSource;
    private CourseDataSource courseDataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_edit);

        termDataSource = new TermDataSource(this);
        termDataSource.open();

        final EditText editTermTitle = (EditText)findViewById(R.id.editTermTitle);
        final EditText editTermStart = (EditText)findViewById(R.id.editTermStart);
        final EditText editTermEnd = (EditText)findViewById(R.id.editTermEnd);


        //Load Saved Data
        if(Term.selectedTerm != null){
            editTermTitle.setText(Term.selectedTerm.getName());
            editTermStart.setText(Term.selectedTerm.getStart());
            editTermEnd.setText(Term.selectedTerm.getEnd());
        }

        //BUTTONS-----------------------------------------------------------------------------------
        mBtnLaunchSaveTerm = (Button) findViewById(R.id.btnSaveTerm);

        mBtnLaunchSaveTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fieldsFilledTest()) {
                    String termTitle = editTermTitle.getText().toString();
                    String termStart = editTermStart.getText().toString();
                    String termEnd = editTermEnd.getText().toString();



                    if(Term.selectedTerm != null) {
                        long termID = Term.selectedTerm.getId();
                        termDataSource.updateTerm(termID, termTitle, termStart, termEnd);

                        Context context = getApplicationContext();
                        CharSequence text = "Term Updated!!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();Term.selectedTerm = null;
                        launchTermsList();

                        }
                        else {
                        termDataSource.createTerm(termTitle, termStart, termEnd);

                        Context context = getApplicationContext();
                        CharSequence text = "Term Saved!!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        launchTermsList();

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



        //ASSOCIATED COURSE LIST ADAPTER------------------------------------------------------------
        courseDataSource = new CourseDataSource(this);
        courseDataSource.open();

        ArrayList<Course> courses = courseDataSource.getAllCourses();

        Collections.sort(courses);

        //instantiate custom adapter
        CourseAssocListAdapter courseAssocListAdapter = new CourseAssocListAdapter(courses, this);

        //handle listview and assign adapter
        ListView lView = (ListView) findViewById(R.id.course_assoc_listview);
        lView.setAdapter(courseAssocListAdapter);


    }

    //TESTS-----------------------------------------------------------------------------------------
    private boolean fieldsFilledTest(){
        boolean Test = true;

        final EditText editTermTitle = (EditText)findViewById(R.id.editTermTitle);
        final EditText editTermStart = (EditText)findViewById(R.id.editTermStart);
        final EditText editTermEnd = (EditText)findViewById(R.id.editTermEnd);



        if(
                editTermTitle.getText().toString().isEmpty() ||
                editTermStart.getText().toString().isEmpty() ||
                editTermEnd.getText().toString().isEmpty() )
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



    @Override
    protected void onResume() {
        termDataSource.open();
        courseDataSource.open();
        super.onResume();

    }

    @Override
    protected void onPause() {
        termDataSource.close();
        courseDataSource.close();
        super.onPause();
    }

}
