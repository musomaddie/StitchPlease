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
        // Lookup view for colour population
        TextView colourName = convertView.findViewById(R.id.listItemThread);
        colourName.setText(thread.toString());

        return convertView;
    }


}


