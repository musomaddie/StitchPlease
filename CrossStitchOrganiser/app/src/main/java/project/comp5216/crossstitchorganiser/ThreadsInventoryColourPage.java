package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ThreadsInventoryColourPage extends Activity {

    private static final String APP_TAG = "Cross Stitch Organiser";

    private ListView listView;
    private List<Colour> allColours;
    private ArrayAdapter<Colour> colourAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_threads_colour_inventory);

        Log.v(APP_TAG, "loading colour inventory page");

        // Setting up the list view
        listView = (ListView) findViewById(R.id.colourInventoryList);
        allColours = Arrays.asList(Colour.values());
        colourAdapter = new ColourAdapter(this, allColours);
        listView.setAdapter(colourAdapter);

        // item listener time!!
        setUpColourItemListener();
    }

    public void onColourInvBackClick(View view) {
        Log.v(APP_TAG, "Clicked back from colour inventory");
        finish();
    }

    private void setUpColourItemListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Colour updateColour = (Colour) colourAdapter.getItem(position);
                Log.i(APP_TAG, "Clicked item " + position + ": " + updateColour);

                Intent intent = new Intent(ThreadsInventoryColourPage.this,
                        ColourSpecificPage.class);

                if (intent != null) {
                    intent.putExtra("colour", updateColour);
                    startActivity(intent);
                }
            }
        });
    }


}
