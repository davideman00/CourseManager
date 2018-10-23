package testapp.dgreenberg.com.CourseManager.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import testapp.dgreenberg.com.CourseManager.AlertReceiver;
import testapp.dgreenberg.com.CourseManager.R;
import testapp.dgreenberg.com.CourseManager.model.Assessment;
import testapp.dgreenberg.com.CourseManager.model.AssessmentAssocListAdapter;
import testapp.dgreenberg.com.CourseManager.model.AssessmentDataSource;
import testapp.dgreenberg.com.CourseManager.model.Course;
import testapp.dgreenberg.com.CourseManager.model.CourseDataSource;

public class CourseEdit extends AppCompatActivity {
    private Button mBtnLaunchSaveCourse;
    private Button mBtnLaunchViewNotes;
    private Switch switchStartAlert;
    private Switch switchEndAlert;



    private CourseDataSource datasource;
    private AssessmentDataSource assessmentDataSource;

    private Calendar calendar;

    private PendingIntent pStartIntent;
    private PendingIntent pEndIntent;
    private AlarmManager alarmManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_edit);

        datasource = new CourseDataSource(this);
        datasource.open();

        final EditText editCourseTitle = (EditText) findViewById(R.id.editCourseTitle);
        final EditText editCourseStart = (EditText) findViewById(R.id.editCourseStart);
        final EditText editCourseEnd = (EditText) findViewById(R.id.editCourseEnd);
        final EditText editCourseStatus = (EditText) findViewById(R.id.editCourseStatus);
        final EditText editCourseMentorName = (EditText) findViewById(R.id.editCMName);
        final EditText editCourseMentorPhone = (EditText) findViewById(R.id.editCMPhone);
        final EditText editCourseMentorEmail = (EditText) findViewById(R.id.editCMEmail);


        if (Course.selectedCourse != null) {
            editCourseTitle.setText(Course.selectedCourse.getName());
            editCourseStart.setText(Course.selectedCourse.getStart());
            editCourseEnd.setText(Course.selectedCourse.getEnd());
            editCourseStatus.setText(Course.selectedCourse.getStatus());
            editCourseMentorName.setText(Course.selectedCourse.getCourseMentorName());
            editCourseMentorPhone.setText(Course.selectedCourse.getCourseMentorPhone());
            editCourseMentorEmail.setText(Course.selectedCourse.getCourseMentorEmail());
        }










        //BUTTONS-----------------------------------------------------------------------------------
        mBtnLaunchSaveCourse = (Button) findViewById(R.id.btnSaveCourse);
        mBtnLaunchViewNotes = (Button) findViewById(R.id.btnViewNotes);
        switchStartAlert = (Switch) findViewById(R.id.switchStartAlert);
        switchEndAlert = (Switch) findViewById(R.id.switchEndAlert);




        mBtnLaunchSaveCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fieldsFilledTest()) {
                    String courseTitle = editCourseTitle.getText().toString();
                    String courseStart = editCourseStart.getText().toString();
                    String courseEnd = editCourseEnd.getText().toString();
                    String courseStatus = editCourseStatus.getText().toString();
                    String courseMentorName = editCourseMentorName.getText().toString();
                    String courseMentorPhone = editCourseMentorPhone.getText().toString();
                    String courseMentorEmail = editCourseMentorEmail.getText().toString();

                    if (Course.selectedCourse != null) {
                        long courseID = Course.selectedCourse.getId();
                        datasource.updateCourse(courseID, courseTitle, courseStart, courseEnd, courseStatus, courseMentorName, courseMentorPhone, courseMentorEmail);

                        Context context = getApplicationContext();
                        CharSequence text = "Course Updated!!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        launchCourseList();

                    } else {
                        datasource.createCourse(courseTitle, courseStart, courseEnd, courseStatus, courseMentorName, courseMentorPhone, courseMentorEmail);

                        Context context = getApplicationContext();
                        CharSequence text = "Course Saved!!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        launchCourseList();

                    }
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "Fill all fields!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

        mBtnLaunchViewNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Course.selectedCourse != null) {
                    launchNotesList();
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "Save Course First!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

        switchStartAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchStartAlert.isChecked()){
                    final EditText editCourseStart = (EditText) findViewById(R.id.editCourseStart);
                    if(editCourseStart.getText().toString().isEmpty()){
                        Context context = getApplicationContext();
                        CharSequence text = "Start Date must be filled!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                    }
                    else {
                        String strDate = editCourseStart.getText().toString();
                        setStartAlert(strDate);
                        switchStartAlert.setText("Alert ON");
                    }
                }
                else{
                    cancelStartAlert();
                    switchStartAlert.setText("Alert OFF");

                }
            }
        });

        switchEndAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchEndAlert.isChecked()){
                    final EditText editCourseEnd = (EditText) findViewById(R.id.editCourseEnd);
                    if(editCourseEnd.getText().toString().isEmpty()){
                        Context context = getApplicationContext();
                        CharSequence text = "End Date must be filled!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                    }
                    else {
                        String strDate = editCourseEnd.getText().toString();
                        setEndAlert(strDate);
                        switchEndAlert.setText("Alert ON");
                    }
                }
                else{
                    cancelEndAlert();
                    switchEndAlert.setText("Alert OFF");

                }
            }
        });



        //ASSOC COURSE LIST DATA--------------------------------------------------------------------
        assessmentDataSource = new AssessmentDataSource(this);
        assessmentDataSource.open();

        ArrayList<Assessment> assessments = assessmentDataSource.getAllAssessments();

        Collections.sort(assessments);

        AssessmentAssocListAdapter assessmentAssocListAdapter = new AssessmentAssocListAdapter(assessments, this);

        ListView lView = (ListView) findViewById(R.id.assessment_assoc_listview);
        lView.setAdapter(assessmentAssocListAdapter);



    }

    //TESTS-----------------------------------------------------------------------------------------
    private boolean fieldsFilledTest() {
        boolean Test = true;

        final EditText editCourseTitle = (EditText) findViewById(R.id.editCourseTitle);
        final EditText editCourseStart = (EditText) findViewById(R.id.editCourseStart);
        final EditText editCourseEnd = (EditText) findViewById(R.id.editCourseEnd);
        final EditText editCourseStatus = (EditText) findViewById(R.id.editCourseStatus);
        final EditText editCourseMentorName = (EditText) findViewById(R.id.editCMName);
        final EditText editCourseMentorPhone = (EditText) findViewById(R.id.editCMPhone);
        final EditText editCourseMentorEmail = (EditText) findViewById(R.id.editCMEmail);


        if (
                        editCourseTitle.getText().toString().isEmpty() ||
                        editCourseStart.getText().toString().isEmpty() ||
                        editCourseEnd.getText().toString().isEmpty() ||
                        editCourseStatus.getText().toString().isEmpty() ||
                        editCourseMentorName.getText().toString().isEmpty() ||
                        editCourseMentorPhone.getText().toString().isEmpty() ||
                        editCourseMentorEmail.getText().toString().isEmpty()) {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main:
                launchMain();
                return (true);
            case R.id.terms:
                launchTermsList();
                return (true);
            case R.id.courses:
                launchCourseList();
                return (true);
            case R.id.assessments:
                launchAssessmentsList();
                return (true);
        }
        return (super.onOptionsItemSelected(item));
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

    private void launchNotesList() {
        Intent intent = new Intent(this, NoteList.class);
        startActivity(intent);
    }


    //ALERT MANAGEMENT------------------------------------------------------------------------------
    public void setStartAlert(String strDate) {

        Date date = null;

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
        try {
            date = format.parse(strDate);
            System.out.println(date);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }


        long time = 000;

        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        time = cal.getTimeInMillis();

        alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

        AlertReceiver receiver = new AlertReceiver();
        IntentFilter filter = new IntentFilter("ALARM_ACTION");
        registerReceiver(receiver, filter);

        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("Reminder", "Course Start Date Today!");

        //intent.putExtra("param", "My scheduled action");
        pStartIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pStartIntent);
        Toast.makeText(this, "Alarm set for Start date!",Toast.LENGTH_LONG).show();
    }

    public void cancelStartAlert() {
        // If the alarm has been set, cancel it.
        if (alarmManager != null) {
            alarmManager.cancel(pStartIntent);
            Toast.makeText(this, "Alert for Start Date Cancelled!", Toast.LENGTH_LONG).show();
        }
    }

    public void setEndAlert(String strDate) {

        Date date = null;

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
        try {
            date = format.parse(strDate);
            System.out.println(date);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }


        long time = 000;

        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        time = cal.getTimeInMillis();

        System.out.println("Set End Alert for: "+ cal.getTime());

        alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

        AlertReceiver receiver = new AlertReceiver();
        IntentFilter filter = new IntentFilter("ALARM_ACTION");
        registerReceiver(receiver, filter);

        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("Reminder", "Course End Date Today!!");

        pEndIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pEndIntent);
        Toast.makeText(this, "Alert set for End date!",Toast.LENGTH_LONG).show();
    }

    public void cancelEndAlert() {
        // If the alarm has been set, cancel it.
        if (alarmManager != null) {
            alarmManager.cancel(pEndIntent);
            Toast.makeText(this, "Alert for End Date Cancelled!", Toast.LENGTH_LONG).show();
        }
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
