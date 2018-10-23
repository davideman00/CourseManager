package testapp.dgreenberg.com.CourseManager.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Collections;

import testapp.dgreenberg.com.CourseManager.R;
import testapp.dgreenberg.com.CourseManager.activities.AssessmentEdit;

public class AssessmentAssocListAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Assessment> list;
    private Context context;

    private AssessmentDataSource datasource;



    public AssessmentAssocListAdapter(ArrayList<Assessment> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return list.get(pos).getId();
        }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.assessment_assoc_listadapter, null);
        }
        context = parent.getContext();
        //Handle TextView and display string from your list
        //course name
        TextView textViewAssessmentName = (TextView)view.findViewById(R.id.assessment_name);
        textViewAssessmentName.setText(list.get(position).getTitle());


        //Handle buttons and add onClickListeners
        Button infoBtn = (Button)view.findViewById(R.id.assessment_info_btn);
        final ToggleButton toggleButton = (ToggleButton)view.findViewById(R.id.toggleButton);


        if(Course.selectedCourse != null) {
            if(Course.selectedCourse.getId() == list.get(position).getCourseID()){
                toggleButton.setChecked(true);
                view.setBackgroundColor(Color.rgb(54,101,175));
            }
            else{
                view.setBackgroundColor(Color.GRAY);
                toggleButton.setChecked(false);
            }

        }




        infoBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Assessment.selectedAssessment = list.get(position);
                launchAssessmentAdd();

            }
        });

        toggleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (Course.selectedCourse != null) {


                    if (toggleButton.isChecked()) {
                        long assessmentID = list.get(position).getId();
                        long courseID = Course.selectedCourse.getId();
                        datasource = new AssessmentDataSource(context);
                        datasource.open();
                        System.out.println("set Course ID:" + courseID + " on assessmentID: " + assessmentID);

                        Assessment updatedAssessment = datasource.updateAssessmentCourseID(assessmentID, courseID);
                        System.out.println("updatedAssessmentCourseID: " + updatedAssessment.getCourseID());
                        list.clear();
                        list = datasource.getAllAssessments();
                        notifyDataSetChanged();

                    } else {
                        long assessmentID = list.get(position).getId();
                        long courseID = 0;
                        datasource = new AssessmentDataSource(context);
                        datasource.open();
                        System.out.println("set Course ID:" + courseID + " on AssessmentID: " + assessmentID);

                        Assessment updatedAssessment = datasource.updateAssessmentCourseID(assessmentID, courseID);
                        System.out.println("updatedAssessmentCourseID: " + updatedAssessment.getCourseID());
                        list.clear();
                        list = datasource.getAllAssessments();

                        notifyDataSetChanged();


                    }
                }
                else{

                    CharSequence text = "Save Course First!!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    toggleButton.setChecked(false);
                }
            }

        });



        return view;
    }


    @Override
    public void notifyDataSetChanged() {
        Collections.sort(list);
        super.notifyDataSetChanged();
    }



    private void launchAssessmentAdd() {
        Activity host = (Activity) context;
        Intent intent = new Intent(host, AssessmentEdit.class);
        host.startActivity(intent);

    }
}