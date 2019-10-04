package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreadsInventoryFullPage extends Activity {

    private static final String APP_TAG = "Cross Stitch Organiser";

    private ListView listView;
    private List<Thread> allThreads;
    private ArrayAdapter<Thread> threadAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads_full_inventory);
        Log.v(APP_TAG, "Loading full inventory page");

        // Setting up the list view
        listView = (ListView) findViewById(R.id.threadInventoryList);
        setUpThreadData();
        threadAdapter = new ThreadAdapter(this, allThreads);
        listView.setAdapter(threadAdapter);


        // item listener time!!
        setUpThreadItemListener();
    }

    public void onFullInvBackClick(View view) {
        Log.v(APP_TAG, "Clicked back from threads full inventory");
        finish();
    }

    private void setUpThreadItemListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Thread updateThread = (Thread) threadAdapter.getItem(position);
                Log.i(APP_TAG, "Clicked item " + position + ": " + updateThread.getDmc());

                Intent intent = new Intent(ThreadsInventoryFullPage.this,
                        ThreadSpecificPage.class);

                if (intent != null) {
                    intent.putExtra("dmc", updateThread.getDmc());
                    startActivity(intent);
                }
            }
        });
    }

    private void setUpThreadData() {
        // TODO: this is dummy data, actually load the data from the user
        allThreads = new ArrayList<Thread>();
        allThreads.add(new Thread("310", Colour.BLACK, 1.2));
        allThreads.add(new Thread("666", Colour.RED, 0.3));
        allThreads.add(new Thread("550", Colour.PURPLE, 4));
    }
}
