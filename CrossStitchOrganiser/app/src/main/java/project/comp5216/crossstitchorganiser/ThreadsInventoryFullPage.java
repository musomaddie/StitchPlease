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

public class ThreadsInventoryFullPage extends Activity {

    private static final String APP_TAG = "Cross Stitch Organiser";

    private ListView listView;
    private List<Thread> allThreads;
    private ArrayAdapter<Thread> threadAdapter;
    private ThreadDao threadDao;
	private OrganiserDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads_full_inventory);

		// Setting up the db
		db = OrganiserDatabase.getDatabase(this.getApplication().getApplicationContext());
		threadDao = db.threadDao();

        loadAllThreads();
        Collections.sort(allThreads, new ThreadComparator());

        // Setting up the list view
        listView = (ListView) findViewById(R.id.threadInventoryList);

        threadAdapter = new ThreadAdapter(this, allThreads);
        listView.setAdapter(threadAdapter);


        // item listener time!!
        setUpThreadItemListener();
        Log.v(APP_TAG, "Loading full inventory page");
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

	private void loadAllThreads() {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
					List<ThreadDatabaseItem> itemsFromDB = threadDao.listAll();
					allThreads = new ArrayList<Thread>();
                    if (itemsFromDB != null && itemsFromDB.size() > 0) {
						for (ThreadDatabaseItem item: itemsFromDB) {
							// For display in list view, we still don't care
							// about the projects
							allThreads.add(new Thread(
										item.getDmc(), 
										Colour.findColour(item.getColour()), 
										item.getAmountOwned()));
							Log.i(APP_TAG, "Read item from database: " + item.getDmc());
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
