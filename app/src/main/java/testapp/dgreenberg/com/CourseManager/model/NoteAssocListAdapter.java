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
import testapp.dgreenberg.com.CourseManager.activities.NoteEdit;

public class NoteAssocListAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Note> list;
    private Context context;

    private NoteDataSource datasource;



    public NoteAssocListAdapter(ArrayList<Note> list, Context context) {
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
            view = inflater.inflate(R.layout.note_assoc_listadapter, null);
        }
        context = parent.getContext();
        //Handle TextView and display string from your list
        //course name
        TextView textViewNoteTitle = (TextView)view.findViewById(R.id.note_title);
        textViewNoteTitle.setText(list.get(position).getTitle());


        //Handle buttons and add onClickListeners
        Button infoBtn = (Button)view.findViewById(R.id.note_info_btn);
        Button deleteBtn = (Button)view.findViewById(R.id.note_delete_btn);

        infoBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Note.selectedNote = list.get(position);
                launchNoteAdd();

            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Note.selectedNote = list.get(position);

                datasource = new NoteDataSource(context);
                datasource.open();
                datasource.deleteNote(Note.selectedNote);
                list.remove(list.get(position));
                notifyDataSetChanged();

                CharSequence text = "Note Deleted!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Note.selectedNote = null;
            }
        });


        return view;
    }



    private void launchNoteAdd() {
        Activity host = (Activity) context;
        Intent intent = new Intent(host, NoteEdit.class);
        host.startActivity(intent);

    }
}