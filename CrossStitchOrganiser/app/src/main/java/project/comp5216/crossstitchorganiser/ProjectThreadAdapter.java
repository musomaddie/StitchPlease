package project.comp5216.crossstitchorganiser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ProjectThreadAdapter extends ArrayAdapter {

	public ProjectThreadAdapter(Context context, List<ProjectThread> threads) {
		super(context, 0, threads);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ProjectThread thread = (ProjectThread) getItem(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext())
				.inflate(R.layout.list_item_project_thread, parent, false);
		}

		TextView dmc = convertView.findViewById(R.id.projectThreadDmc);
		dmc.setText(thread.dmc);

		TextView amount = convertView.findViewById(R.id.projectThreadAmount);
		Double a = thread.amountNeeded;
		amount.setText(a.toString());

		return convertView;
	}
}
