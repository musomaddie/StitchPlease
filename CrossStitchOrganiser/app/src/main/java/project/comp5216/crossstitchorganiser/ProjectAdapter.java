package project.comp5216.crossstitchorganiser;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ProjectAdapter extends ArrayAdapter {

    public ProjectAdapter(Context context, List<Project> projects) {
        super(context, 0, projects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Project project = (Project) getItem(position);

        // Check if existing view is being resused otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item_project, parent, false);
        }
        // Lookup view for colour population
        TextView projectName = convertView.findViewById(R.id.listItemProject);
        projectName.setText(project.toString());

        return convertView;
    }
}
