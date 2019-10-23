package project.comp5216.crossstitchorganiser;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;


// ACKNOWLEDGEMENT: Adapted from tutorial

public class ThreadAdapter extends ArrayAdapter {

    private static final String APP_TAG = "Cross Stitch Organiser";

    public ThreadAdapter(Context context, List<Thread> threads) {
        super(context, 0, threads);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Thread thread = (Thread) getItem(position);

        // Check if existing view is being resused otherwise inflate the view
        if (convertView == null) {
            Log.d(APP_TAG, "REUSING VIEW");
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item_thread, parent, false);
        }
		// populate the DMC text
        TextView dmc = convertView.findViewById(R.id.listItemThread);
        dmc.setText(thread.toString());

		// Set the colour of the rectangle
		View squareColour = convertView.findViewById(R.id.colourRectangle);
		Map<String, String> dmcToHex = new DMC_toHex().hexColours;
		if (dmcToHex.containsKey(thread.getDmc())) {
		    squareColour.setBackgroundColor(Color.parseColor(dmcToHex.get(thread.getDmc())));
        } else {
            squareColour.setBackground(thread.getColour().findColourResource(getContext()));
        }

		// populate the amount text
		TextView amount = convertView.findViewById(R.id.listItemAmount);
		Double a = thread.getAmountOwned();
		amount.setText(a.toString());

        return convertView;
    }
}
