package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ThreadChangeQuantityPage extends Activity {

    private static final String APP_TAG = "Cross Stitch Organiser";

    private String thisThreadDmc;
    private Thread thisThread;
    private double amount;

    private ThreadDao threadDao;
    private OrganiserDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_change_quantity);

        thisThreadDmc = getIntent().getStringExtra("dmc");

        // Setting up the db
        db = OrganiserDatabase.getDatabase(this.getApplication().getApplicationContext());
        threadDao = db.threadDao();

        loadThreadFromDatabase();

        // populate the old amount
        TextView oldA = findViewById(R.id.oldAmount);
        oldA.setText(Double.valueOf(thisThread.getAmountOwned()).toString());

    }

    public void onThreadsEditBackClick(View view) {
        finish();
    }

    public void onThreadsEditSubmitClick(View view) {
        // Update database
        EditText amountET = findViewById(R.id.threadsEditAmount);
        amount = Double.parseDouble(amountET.getText().toString());
        updateThreadInDatabase();
        Toast.makeText(getApplicationContext(), "Updated quantity successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void updateThreadInDatabase() {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    threadDao.updateAmountOwned(amount, thisThread.getDmc());
                    Log.i(APP_TAG, "Updated " + thisThread.getDmc() + " in database");
                    return null;
                }
            }.execute().get();
        } catch(Exception ex) {
            Log.e("readItemsFromDatabase", ex.getStackTrace().toString());
        }
    }

    private void loadThreadFromDatabase() {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    ThreadDatabaseItem threadFromDB = threadDao.findThread(thisThreadDmc);
                    if (threadFromDB != null) {
                        thisThread = new Thread(threadFromDB.getDmc(),
                                Colour.findColour(threadFromDB.getColour()),
                                threadFromDB.getAmountOwned());
                    }
                    return null;
                }
            }.execute().get();
        } catch(Exception ex) {
            Log.e(APP_TAG, ex.getStackTrace().toString());
        }
    }

}