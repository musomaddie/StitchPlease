package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ThreadSpecificPage extends Activity {

    private static final String APP_TAG = "Cross Stitch Organiser";

    private Thread thisThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String thisDmc = getIntent().getStringExtra("dmc");

        // TODO: this is dummy data: actually fetch the thread from the db and load there.
        thisThread = new Thread(thisDmc, Colour.BLACK, 1);
        setContentView(R.layout.activity_thread_specific);
        TextView title = findViewById(R.id.specificThreadTitle);
        title.setText(thisThread.toString());
        Log.v(APP_TAG, "Loading Colour specific page for: " + thisThread.getDmc());

        // TODO: will need a list view to view all projects used in
    }

    public void onSpecificThreadBackClick(View view) {
        Log.v(APP_TAG, "clicked back from thread specific page");
        finish();
    }
}
