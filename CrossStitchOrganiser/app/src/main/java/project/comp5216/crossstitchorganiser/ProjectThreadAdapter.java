package project.comp5216.crossstitchorganiser;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

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

		View squareColour = convertView.findViewById(R.id.colourRectangle);
		Map<String, String> dmcToHex = new DMC_toHex().hexColours;
		if (dmcToHex.containsKey(thread.dmc)) {
			squareColour.setBackgroundColor(Color.parseColor(dmcToHex.get(thread.dmc)));
		}

		TextView dmc = convertView.findViewById(R.id.projectThreadDmc);
		dmc.setText(thread.dmc);

		TextView amount = convertView.findViewById(R.id.projectThreadAmount);
		amount.setText(Double.valueOf(thread.amountNeeded).toString());

		return convertView;
	}
}
