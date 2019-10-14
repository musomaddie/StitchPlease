package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ThreadsAddPage extends Activity implements AdapterView.OnItemSelectedListener {
    private static final String APP_TAG = "Cross Stitch Organiser";

	private String dmc;
	private double amount;
	private Colour colour;
	private Thread newThread;
	private List<Thread> allExistingThreads;

    private ThreadDao threadDao;
	private OrganiserDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		// Loading visual content
        setContentView(R.layout.activity_threads_add);
		setUpColourSpinner();
		// Loading the database
		db = OrganiserDatabase.getDatabase(this.getApplication().getApplicationContext());
		threadDao = db.threadDao();

        Log.v(APP_TAG, "Loading threads add page");
    }

	public void onThreadsAddSubmitClick(View view) {
		EditText dmcET = findViewById(R.id.threadsAddTextDmc);
		dmc = dmcET.getText().toString();
		if (dmc.equals("")) {
			Toast.makeText(this,
					getResources().getString(R.string.failure_add_thread_no_dmc),
					Toast.LENGTH_SHORT).show();
			return;
		}

		EditText amountET = findViewById(R.id.threadsAddAmount);
		try {
			amount = Double.parseDouble(amountET.getText().toString());
		} catch (NumberFormatException e) {
			amount = 1.0;
		}
		if (existing()) {
			// TODO: handle correct database updates here
			return;
		}
		// TODO: check if it already exists: if so just ADD the amount to it,
		// otherwise create new thread
		newThread = new Thread(dmc, colour, amount);
		saveThreadToDatabase();
		Toast.makeText(this,
				getResources().getString(R.string.success_thread_creation) + newThread.toString(),
				Toast.LENGTH_SHORT).show();
	}

    public void onThreadsAddBackClick(View view) {
        Log.v(APP_TAG, "Clicked back in threads add");
        finish();
    }

	private void setUpColourSpinner() {
		Spinner spinner = findViewById(R.id.threadsAddColourSpinner);
		ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
				Colour.makeList());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	}

	// Spinner methods needed for interface
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		colour = Colour.findColour((String) parent.getItemAtPosition(pos));
    }

    public void onNothingSelected(AdapterView<?> parent) {
		colour = null;
    }

	private boolean existing() {
		loadAllFromDatabase();
		for (Thread thread : allExistingThreads) {
			if (thread.getDmc().equals(dmc)) {
				newThread = new Thread(
						dmc, colour, thread.getAmountOwned() + amount);
				updateDatabase();
				return true;
			}
		}
		return false;
	}

	private void updateDatabase() {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
					threadDao.updateAmountOwned
						(newThread.getAmountOwned(), newThread.getDmc());
					Log.i(APP_TAG, "Updated " + newThread.getDmc() + " in database");
                    return null;
                }
            }.execute().get();
        } catch(Exception ex) {
            Log.e("readItemsFromDatabase", ex.getStackTrace().toString());
		}
	}

	private void loadAllFromDatabase() {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
					List<ThreadDatabaseItem> itemsFromDB = threadDao.listAll();
					allExistingThreads = new ArrayList<Thread>();
                    if (itemsFromDB != null && itemsFromDB.size() > 0) {
						for (ThreadDatabaseItem item: itemsFromDB) {
							// these are the only attributes we care about right
							// now: no need to load the project(s)
							allExistingThreads.add(new Thread(
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
            Log.e("readItemsFromDatabase", ex.getStackTrace().toString());
        }
	}


    private void saveThreadToDatabase() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
				threadDao.insert(new ThreadDatabaseItem(newThread.getDmc(),
							newThread.getColour().toString(),
							newThread.getAmountOwned(),
							newThread.getAmountNeeded()));
				Log.i(APP_TAG, "Saved thread: " + newThread.toString() + " to database");
                return null;
            }
        }.execute();
    }
}
