package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ColourSpecificPage extends Activity {

    private static final String APP_TAG = "Cross Stitch Organiser";

    private ListView listView;
    private List<Thread> threadsToView;
    ArrayAdapter<Thread> threadAdapter;
    private Colour thisColour;
    private ThreadDao threadDao;
	private OrganiserDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour_specific);

		// Setting up db
		db = OrganiserDatabase.getDatabase(this.getApplication().getApplicationContext());
		threadDao = db.threadDao();

        // Find the correct colour and load the appropriate information.
        thisColour = (Colour) getIntent().getSerializableExtra("colour");

        Log.v(APP_TAG, "Loading Colour specific page for: " + thisColour);

        listView = findViewById(R.id.threadListForSpecificColour);
		loadThreads();
		Collections.sort(threadsToView, new ThreadComparator());
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

	private void loadThreads() {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
					List<ThreadDatabaseItem> itemsFromDB = threadDao.listAll();
					threadsToView = new ArrayList<Thread>();
                    if (itemsFromDB != null && itemsFromDB.size() > 0) {
						for (ThreadDatabaseItem item: itemsFromDB) {
							// For display in list view, we still don't care
							// about the projects
							Colour threadColour = Colour.findColour(item.getColour());
							if (thisColour == threadColour) {
								threadsToView.add(new Thread(
											item.getDmc(), 
											threadColour,
											item.getAmountOwned()));
								Log.i(APP_TAG, "Read item from database: " + item.getDmc());
							}
                        }
                    }
                    return null;
                }
            }.execute().get();
        } catch(Exception ex) {
			Log.e(APP_TAG, ex.getStackTrace().toString());
        }
	}


}
