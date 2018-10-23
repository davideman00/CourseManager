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
import testapp.dgreenberg.com.CourseManager.activities.TermEdit;

public class TermListAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Term> list;
    private Context context;

    private TermDataSource termDataSource;
    private CourseDataSource courseDataSource;

    public TermListAdapter(ArrayList<Term> list, Context context) {
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
            view = inflater.inflate(R.layout.term_listadapter, null);
        }
        context = parent.getContext();
        //Handle TextView and display string from your list
        //term name
        TextView textViewTermName = (TextView)view.findViewById(R.id.term_name);
        textViewTermName.setText(list.get(position).getName());




        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);
        Button infoBtn = (Button)view.findViewById(R.id.info_btn);


        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Term.selectedTerm = list.get(position);
                ArrayList<Course> courses = new ArrayList<Course>();
                courseDataSource = new CourseDataSource(context);
                courseDataSource.open();
                courses = courseDataSource.getAllCourses();
                //System.out.println("Size: " + courses.size());
                for (int i = 0; i < courses.size(); i++) {
                        //System.out.println("Loop");
                        long termId = Term.selectedTerm.getId();
                        long cTermId = courses.get(i).getTermId();
                        //System.out.println("i: " + i + " size: " + courses.size());
                        if (termId == cTermId) {
                            //System.out.println("TermMatch: " + termId + " CourseMatch: " + cTermId);
                            CharSequence text = "Can't Delete Term! Already Associated!";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            return;
                        }
                    }
                termDataSource = new TermDataSource(context);
                termDataSource.open();
                termDataSource.deleteTerm(Term.selectedTerm);
                list.remove(list.get(position));
                notifyDataSetChanged();

                CharSequence text = "Term Deleted!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
             }
        });


        infoBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Term.selectedTerm = list.get(position);
                launchTermAdd();

                notifyDataSetChanged();
            }
        });

        return view;
    }



    private void launchTermAdd() {
        Activity host = (Activity) context;
        Intent intent = new Intent(host, TermEdit.class);
        host.startActivity(intent);

    }
}