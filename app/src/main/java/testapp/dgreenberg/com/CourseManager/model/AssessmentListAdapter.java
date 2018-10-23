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
import testapp.dgreenberg.com.CourseManager.activities.AssessmentEdit;

public class AssessmentListAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Assessment> list;
    private Context context;

    private AssessmentDataSource datasource;


    public AssessmentListAdapter(ArrayList<Assessment> list, Context context) {
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
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.assessment_listadapter, null);
        }
        context = parent.getContext();
        //Handle TextView and display string from your list
        //term name
        TextView textViewAssessmentTitle = (TextView)view.findViewById(R.id.assessment_title);
        textViewAssessmentTitle.setText(list.get(position).getTitle());


        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);
        Button infoBtn = (Button)view.findViewById(R.id.info_btn);


        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Assessment.selectedAssessment = list.get(position);

                datasource = new AssessmentDataSource(context);
                datasource.open();
                datasource.deleteAssessment(Assessment.selectedAssessment);
                list.remove(list.get(position));
                notifyDataSetChanged();

                CharSequence text = "Assessment Deleted!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });


        infoBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Assessment.selectedAssessment = list.get(position);
                launchAssessmentAdd();

                notifyDataSetChanged();
            }
        });

        return view;
    }



    private void launchAssessmentAdd() {
        Activity host = (Activity) context;
        Intent intent = new Intent(host, AssessmentEdit.class);
        host.startActivity(intent);

    }
}