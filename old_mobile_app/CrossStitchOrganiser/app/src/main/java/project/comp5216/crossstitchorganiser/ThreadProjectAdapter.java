package project.comp5216.crossstitchorganiser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ThreadProjectAdapter extends ArrayAdapter {

	public ThreadProjectAdapter(Context context, List<ThreadProject> projects) {
		super(context, 0, projects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ThreadProject project = (ThreadProject) getItem(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext())
				.inflate(R.layout.list_item_thread_project, parent, false);
		}

		TextView title = convertView.findViewById(R.id.threadProjectTitle);
		title.setText(project.projectName);

		TextView amount = convertView.findViewById(R.id.threadProjectAmount);
		amount.setText(Double.valueOf(project.amountNeeded).toString());
		
		return convertView;

	}
}
