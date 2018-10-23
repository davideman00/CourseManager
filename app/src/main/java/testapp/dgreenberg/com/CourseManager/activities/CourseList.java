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
import testapp.dgreenberg.com.CourseManager.model.Course;
import testapp.dgreenberg.com.CourseManager.model.CourseDataSource;
import testapp.dgreenberg.com.CourseManager.model.CourseListAdapter;

public class CourseList extends AppCompatActivity {
    private CourseDataSource datasource;
    private Button mBtnAddCourse;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list);

        Course.selectedCourse = null;

        datasource = new CourseDataSource(this);
        datasource.open();

        ArrayList<Course> courses = datasource.getAllCourses();

        //Add new course
        mBtnAddCourse = (Button) findViewById(R.id.add);
        mBtnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCourseAdd();
            }
        });


        //instantiate custom adapter
        CourseListAdapter adapter = new CourseListAdapter(courses, this);

        //handle listview and assign adapter
        ListView lView = (ListView) findViewById(R.id.course_listview);
        lView.setAdapter(adapter);


    }


    public CourseList() {
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

    private void launchCourseAdd() {
        Intent intent = new Intent(this, CourseEdit.class);
        startActivity(intent);
    }


}


