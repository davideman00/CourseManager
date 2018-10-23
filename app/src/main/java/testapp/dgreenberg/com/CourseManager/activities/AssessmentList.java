package testapp.dgreenberg.com.CourseManager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import testapp.dgreenberg.com.CourseManager.R;
import testapp.dgreenberg.com.CourseManager.model.Assessment;
import testapp.dgreenberg.com.CourseManager.model.AssessmentDataSource;
import testapp.dgreenberg.com.CourseManager.model.AssessmentListAdapter;

public class AssessmentList extends AppCompatActivity {
    private AssessmentDataSource datasource;
    private Button mBtnAddAssessments;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_list);

        Assessment.selectedAssessment = null;

        datasource = new AssessmentDataSource(this);
        datasource.open();

        ArrayList<Assessment> assessments = datasource.getAllAssessments();

        //Add new course
        mBtnAddAssessments = (Button) findViewById(R.id.add);
        mBtnAddAssessments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAssessmentAdd();
            }
        });


        //instantiate custom adapter
        AssessmentListAdapter adapter = new AssessmentListAdapter(assessments, this);

        //handle listview and assign adapter
        ListView lView = (ListView) findViewById(R.id.listview);
        lView.setAdapter(adapter);


    }

    public AssessmentList() {
    }

    private void launchAssessmentAdd() {
        Intent intent = new Intent(this, AssessmentEdit.class);
        startActivity(intent);
    }

    //MENU------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_assessment_list, menu);
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
            launchCoursesList();
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

    private void launchCoursesList() {
        Intent intent = new Intent(this, CourseList.class);
        startActivity(intent);
    }
}