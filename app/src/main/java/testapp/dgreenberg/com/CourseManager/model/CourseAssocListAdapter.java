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
import testapp.dgreenberg.com.CourseManager.activities.CourseEdit;

public class CourseAssocListAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Course> list;
    private Context context;

    private CourseDataSource datasource;



    public CourseAssocListAdapter(ArrayList<Course> list, Context context) {
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
            view = inflater.inflate(R.layout.course_assoc_listadapter, null);
        }
        context = parent.getContext();
        //Handle TextView and display string from your list
        //course name
        TextView textViewCourseName = (TextView)view.findViewById(R.id.course_name);
        textViewCourseName.setText(list.get(position).getName());


        //Handle buttons and add onClickListeners
        Button infoBtn = (Button)view.findViewById(R.id.course_info_btn);
        final ToggleButton toggleButton = (ToggleButton)view.findViewById(R.id.toggleButton);


        if(Term.selectedTerm != null) {
            if(Term.selectedTerm.getId() == list.get(position).getTermId()){
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
                Course.selectedCourse = list.get(position);
                launchCourseAdd();

            }
        });

        toggleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(Term.selectedTerm != null) {
                    if (toggleButton.isChecked()) {
                        long courseID = list.get(position).getId();
                        long termID = Term.selectedTerm.getId();
                        datasource = new CourseDataSource(context);
                        datasource.open();
                        System.out.println("set Term ID:" + termID + " on courseID: " + courseID);

                        Course updatedCourse = datasource.updateCourseTermID(courseID, termID);
                        System.out.println("updatedCourseTermID: " + updatedCourse.getTermId());
                        list.clear();
                        list = datasource.getAllCourses();
                        notifyDataSetChanged();

                    } else {
                        long courseID = list.get(position).getId();
                        long termID = 0;
                        datasource = new CourseDataSource(context);
                        datasource.open();
                        System.out.println("set Term ID:" + termID + " on courseID: " + courseID);

                        Course updatedCourse = datasource.updateCourseTermID(courseID, termID);
                        System.out.println("updatedCourseTermID: " + updatedCourse.getTermId());
                        list.clear();
                        list = datasource.getAllCourses();

                        notifyDataSetChanged();


                    }
                }
                else{
                    CharSequence text = "Save Term First!!";
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



    private void launchCourseAdd() {
        Activity host = (Activity) context;
        Intent intent = new Intent(host, CourseEdit.class);
        host.startActivity(intent);

    }
}