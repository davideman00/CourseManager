package testapp.dgreenberg.com.CourseManager.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import testapp.dgreenberg.com.CourseManager.R;
import testapp.dgreenberg.com.CourseManager.activities.CourseEdit;

public class CourseListAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Course> list;
    private Context context;

    private CourseDataSource datasource;


    public CourseListAdapter(ArrayList<Course> list, Context context) {
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
            view = inflater.inflate(R.layout.course_listadapter, null);
        }
        context = parent.getContext();
        //Handle TextView and display string from your list
        //course name
        TextView textViewCourseName = (TextView)view.findViewById(R.id.course_name);
        textViewCourseName.setText(list.get(position).getName());

        //TextView textViewCourseStart = (TextView)view.findViewById(R.id.course_start);
        //textViewCourseStart.setText(list.get(position).getStart());


        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);
        Button infoBtn = (Button)view.findViewById(R.id.info_btn);


        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Course selectedCourse = list.get(position);
                System.out.println(selectedCourse.getName());

                datasource = new CourseDataSource(context);
                datasource.open();
                datasource.deleteCourse(selectedCourse);
                list.remove(list.get(position));
                notifyDataSetChanged();

                CharSequence text = "Course Deleted!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });


        infoBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Course.selectedCourse = list.get(position);
                launchCourseAdd();

                notifyDataSetChanged();
            }
        });

        return view;
    }



    private void launchCourseAdd() {
        Activity host = (Activity) context;
        Intent intent = new Intent(host, CourseEdit.class);
        host.startActivity(intent);

    }
}