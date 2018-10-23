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
import testapp.dgreenberg.com.CourseManager.model.Term;
import testapp.dgreenberg.com.CourseManager.model.TermDataSource;
import testapp.dgreenberg.com.CourseManager.model.TermListAdapter;

public class TermList extends AppCompatActivity {
    private TermDataSource datasource;
    private Button mBtnAddTerms;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_list);


        Term.selectedTerm = null;

        datasource = new TermDataSource(this);
        datasource.open();

        ArrayList<Term> terms = datasource.getAllTerms();

        //Add new course
        mBtnAddTerms = (Button) findViewById(R.id.add);
        mBtnAddTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchTermAdd();
            }
        });


        //instantiate custom adapter
        TermListAdapter adapter = new TermListAdapter(terms, this);

        //handle listview and assign adapter
        ListView lView = (ListView) findViewById(R.id.term_listview);
        lView.setAdapter(adapter);


    }



    public TermList() {
    }

    private void launchTermAdd() {
        Intent intent = new Intent(this, TermEdit.class);
        startActivity(intent);
    }


    //MENU------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_terms_list, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.main:
            launchMain();
            return(true);
        case R.id.courses:
            launchCoursesList();
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

    private void launchCoursesList() {
        Intent intent = new Intent(this, CourseList.class);
        startActivity(intent);
    }

    private void launchAssessmentsList() {
        Intent intent = new Intent(this, AssessmentList.class);
        startActivity(intent);
    }
}



