package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ThreadSpecificPage extends Activity {

    private static final String APP_TAG = "Cross Stitch Organiser";

	private List<Thread> allThreads;
    private Thread thisThread;

    private ThreadDao threadDao;
	private OrganiserDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_specific);
		// Loading the database
		db = OrganiserDatabase.getDatabase(this.getApplication().getApplicationContext());
		threadDao = db.threadDao();
		
		// Loading the details for the thread
        String thisDmc = getIntent().getStringExtra("dmc");
		findThread(thisDmc);
		// The title
        TextView title = findViewById(R.id.specificThreadTitle);
        title.setText(thisThread.toString());

		// The colour square
        View squareColour = findViewById(R.id.specificThreadColourRect);
		squareColour.setBackground(thisThread.getColour().findColourResource(this));
		
		// The amount owned
		TextView amountOwnedV = findViewById(R.id.specificThreadAmountOwned);
		amountOwnedV.setText(Double.valueOf(thisThread.getAmountOwned()).toString());

		// The amount needed
		TextView amountNeededV = findViewById(R.id.specificThreadAmountNeeded);
		amountNeededV.setText(Double.valueOf(thisThread.getAmountNeeded()).toString());

        // TODO: will need a list view to view all projects used in

		Log.v(APP_TAG, "Loading Colour specific page for: " + thisThread.getDmc());
    }

    public void onSpecificThreadBackClick(View view) {
        Log.v(APP_TAG, "clicked back from thread specific page");
        finish();
    }

	private void findThread(String thisDmc) {
		loadAllFromDatabase();
		for (Thread thread : allThreads) {
			if (thread.getDmc().equals(thisDmc)) {
				thisThread = thread;
				return;
			}
		}
	}

	private void loadAllFromDatabase() {
		// TODO: will also need to load the list of linked projects!!
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
					List<ThreadDatabaseItem> itemsFromDB = threadDao.listAll();
					allThreads = new ArrayList<Thread>();
                    if (itemsFromDB != null && itemsFromDB.size() > 0) {
						for (ThreadDatabaseItem item: itemsFromDB) {
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
