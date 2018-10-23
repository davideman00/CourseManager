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
import android.widget.Switch;
import android.widget.Toast;

import java.util.Date;

import testapp.dgreenberg.com.CourseManager.AlertReceiver;
import testapp.dgreenberg.com.CourseManager.R;
import testapp.dgreenberg.com.CourseManager.model.Assessment;
import testapp.dgreenberg.com.CourseManager.model.AssessmentDataSource;

public class AssessmentEdit extends AppCompatActivity {
    private Button mBtnLaunchSaveAssessment;
    private Switch switchDueAlert;
    private Switch switchGoalAlert;
    private AssessmentDataSource datasource;

    private PendingIntent pDueIntent;
    private PendingIntent pGoalIntent;
    private AlarmManager alarmManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_edit);

        datasource = new AssessmentDataSource(this);
        datasource.open();

        final EditText editAssessmentTitle = (EditText)findViewById(R.id.editAssessmentTitle);
        final EditText editAssessmentType = (EditText)findViewById(R.id.editAssessmentType);
        final EditText editAssessmentDue = (EditText)findViewById(R.id.editAssessmentDue);
        final EditText editAssessmentGoal = (EditText)findViewById(R.id.editAssessmentGoal);





        //Copy saved assessment to modify
        if(Assessment.selectedAssessment != null){
            editAssessmentTitle.setText(Assessment.selectedAssessment.getTitle());
            editAssessmentType.setText(Assessment.selectedAssessment.getType());
            editAssessmentDue.setText(Assessment.selectedAssessment.getDueDate());
            editAssessmentGoal.setText(Assessment.selectedAssessment.getGoalDate());


        }





        //BUTTONS-----------------------------------------------------------------------------------
        mBtnLaunchSaveAssessment = (Button) findViewById(R.id.btnSaveAssessment);
        switchDueAlert = (Switch) findViewById(R.id.switchDueAlert);
        switchGoalAlert = (Switch) findViewById(R.id.switchGoalAlert);


        mBtnLaunchSaveAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fieldsFilledTest()) {
                    String assessmentTitle = editAssessmentTitle.getText().toString();
                    String assessmentType = editAssessmentType.getText().toString();
                    String assessmentDue = editAssessmentDue.getText().toString();
                    String assessmentGoal = editAssessmentGoal.getText().toString();


                    if(Assessment.selectedAssessment != null) {
                        long assessmentID = Assessment.selectedAssessment.getId();
                        datasource.updateAssessment(assessmentID, assessmentTitle, assessmentType, assessmentDue, assessmentGoal);

                        Context context = getApplicationContext();
                        CharSequence text = "Assessment Updated!!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        launchAssessmentsList();

                    }
                    else {
                        datasource.createAssessment(assessmentTitle, assessmentType, assessmentDue, assessmentGoal);

                        Context context = getApplicationContext();
                        CharSequence text = "Assessment Saved!!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        launchAssessmentsList();

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

        //SWITCHES-----------------------------------------------------------------------------------
        switchDueAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchDueAlert.isChecked()){
                    final EditText editAssessmentDue = (EditText) findViewById(R.id.editAssessmentDue);
                    if(editAssessmentDue.getText().toString().isEmpty()){
                        Context context = getApplicationContext();
                        CharSequence text = "Assessment Due Date must be filled!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                    }
                    else {
                        String strDate = editAssessmentDue.getText().toString();
                        setDueAlert(strDate);
                        switchDueAlert.setText("Alert ON");
                    }
                }
                else{
                    cancelDueAlert();
                    switchDueAlert.setText("Alert OFF");

                }
            }
        });


        switchGoalAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchGoalAlert.isChecked()){
                    final EditText editAssessmentGoal = (EditText) findViewById(R.id.editAssessmentGoal);
                    if(editAssessmentGoal.getText().toString().isEmpty()){
                        Context context = getApplicationContext();
                        CharSequence text = "Assessment Goal Date must be filled!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                    }
                    else {
                        String strDate = editAssessmentGoal.getText().toString();
                        setGoalAlert(strDate);
                        switchGoalAlert.setText("Alert ON");
                    }
                }
                else{
                    cancelGoalAlert();
                    switchGoalAlert.setText("Alert OFF");

                }
            }
        });



    }

    //TESTS-----------------------------------------------------------------------------------------
    private boolean fieldsFilledTest(){
        boolean Test = true;

        final EditText editAssessmentTitle = (EditText)findViewById(R.id.editAssessmentTitle);
        final EditText editAssessmentType = (EditText)findViewById(R.id.editAssessmentType);
        final EditText editAssessmentDue = (EditText)findViewById(R.id.editAssessmentDue);
        final EditText editAssessmentGoal = (EditText)findViewById(R.id.editAssessmentGoal);



        if(
                editAssessmentTitle.getText().toString().isEmpty() ||
                editAssessmentType.getText().toString().isEmpty() ||
                editAssessmentDue.getText().toString().isEmpty() ||
                editAssessmentGoal.getText().toString().isEmpty())
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
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }


    //ALERT MANAGEMENT------------------------------------------------------------------------------
    public void setDueAlert(String strDate) {

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

        System.out.println("Set Assessment Due Alert for: "+ cal.getTime());

        alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

        AlertReceiver receiver = new AlertReceiver();
        IntentFilter filter = new IntentFilter("ALARM_ACTION");
        registerReceiver(receiver, filter);

        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("Reminder", "Assessment Due Today!");

        //intent.putExtra("param", "My scheduled action");
        pDueIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pDueIntent);
        Toast.makeText(this, "Alert set for Due date!",Toast.LENGTH_LONG).show();
    }

    public void cancelDueAlert() {
        // If the alarm has been set, cancel it.
        if (alarmManager != null) {
            alarmManager.cancel(pDueIntent);
            Toast.makeText(this, "Alert for Due Date Cancelled!", Toast.LENGTH_LONG).show();
        }
    }


    public void setGoalAlert(String strDate) {

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

        System.out.println("Set Assessment Goal Alert for: "+ cal.getTime());

        alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

        AlertReceiver receiver = new AlertReceiver();
        IntentFilter filter = new IntentFilter("ALARM_ACTION");
        registerReceiver(receiver, filter);

        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("Reminder", "Assessment Goal Date Today!");

        //intent.putExtra("param", "My scheduled action");
        pGoalIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pGoalIntent);
        Toast.makeText(this, "Alert set for Goal date!",Toast.LENGTH_LONG).show();
    }

    public void cancelGoalAlert() {
        // If the alarm has been set, cancel it.
        if (alarmManager != null) {
            alarmManager.cancel(pGoalIntent);
            Toast.makeText(this, "Alert for Goal Date Cancelled!", Toast.LENGTH_LONG).show();
        }
    }



}
