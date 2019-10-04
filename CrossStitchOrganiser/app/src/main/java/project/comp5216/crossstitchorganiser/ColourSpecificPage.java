package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ColourSpecificPage extends Activity {

    private static final String APP_TAG = "Cross Stitch Organiser";

    private ListView listView;
    private List<Thread> threadsToView;
    ArrayAdapter<Thread> threadAdapter;
    private Colour thisColour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour_specific);

        // Find the correct colour and load the appropriate information.
        thisColour = (Colour) getIntent().getSerializableExtra("colour");
        TextView title = findViewById(R.id.specificColourTitle);
        title.setText(thisColour.toString());
        // TODO: currently this is just dummy data, will need to fetch appropriate info from database.
        // load all the threads of this colour
        Log.v(APP_TAG, "Loading Colour specific page for: " + thisColour);

        listView = findViewById(R.id.threadListForSpecificColour);
        threadsToView = Colour.loadThreads(thisColour);
        threadAdapter = new ThreadAdapter(this, threadsToView);
        listView.setAdapter(threadAdapter);

        setUpThreadItemListener();

    }

    public void onSpecificColourBackClick(View view) {
        Log.v(APP_TAG, "clicked back from colour specific page");
        finish();
    }

    private void setUpThreadItemListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Thread updateThread = (Thread) threadAdapter.getItem(position);
                Log.i(APP_TAG, "Clicked item " + position + ": " + updateThread.getDmc());

                Intent intent = new Intent(ColourSpecificPage.this,
                        ThreadSpecificPage.class);

                if (intent != null) {
                    intent.putExtra("dmc", updateThread.getDmc());
                    startActivity(intent);
                }
            }
        });
    }
}
