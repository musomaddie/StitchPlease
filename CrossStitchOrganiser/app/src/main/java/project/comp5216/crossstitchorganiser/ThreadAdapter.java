package project.comp5216.crossstitchorganiser;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


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
		squareColour.setBackground(findColour(thread));

		// populate the amount text
		TextView amount = convertView.findViewById(R.id.listItemAmount);
		Double a = thread.getAmountOwned();
		amount.setText(a.toString());

        return convertView;
    }

	private ColorDrawable findColour(Thread thread) {
		Log.d(APP_TAG, thread.getColour().toString());
		if (thread.getColour() == Colour.GREY) {
			return new ColorDrawable(getContext().getResources().getColor(R.color.greyColour));
		}
		if (thread.getColour() == Colour.WHITE) {
			return new ColorDrawable(getContext().getResources().getColor(R.color.whiteColour));
		}
		if (thread.getColour() == Colour.BLACK) {
			return new ColorDrawable(getContext().getResources().getColor(R.color.blackColour));
		}
		if (thread.getColour() == Colour.RED) {
			return new ColorDrawable(getContext().getResources().getColor(R.color.redColour));
		}
		if (thread.getColour() == Colour.ORANGE) {
			return new ColorDrawable(getContext().getResources().getColor(R.color.orangeColour));
		}
		if (thread.getColour() == Colour.YELLOW) {
			return new ColorDrawable(getContext().getResources().getColor(R.color.yellowColour));
		}
		if (thread.getColour() == Colour.GREEN) {
			return new ColorDrawable(getContext().getResources().getColor(R.color.greenColour));
		}
		if (thread.getColour() == Colour.BLUE) {
			return new ColorDrawable(getContext().getResources().getColor(R.color.blueColour));
		}
		if (thread.getColour() == Colour.PURPLE) {
			return new ColorDrawable(getContext().getResources().getColor(R.color.purpleColour));
		}
		if (thread.getColour() == Colour.PINK) {
			return new ColorDrawable(getContext().getResources().getColor(R.color.pinkColour));
		}
		return new ColorDrawable(getContext().getResources().getColor(R.color.greyColour));
	}
}
