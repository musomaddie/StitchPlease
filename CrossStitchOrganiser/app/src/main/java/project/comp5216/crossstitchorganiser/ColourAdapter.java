package project.comp5216.crossstitchorganiser;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

// ACKNOWLEDGEMENT: Adapted from tutorial

public class ColourAdapter extends ArrayAdapter {

    private static final String APP_TAG = "Cross Stitch Organiser";

    public ColourAdapter(Context context, List<Colour> colours) {
        super(context, 0, colours);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Colour colour = (Colour) getItem(position);

        // Check if existing view is being resused otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item_colour, parent, false);
        }
        // Lookup view for colour population
        TextView colourName = convertView.findViewById(R.id.listItemColour);
        colourName.setText(colour.toString());

        return convertView;
    }


}
