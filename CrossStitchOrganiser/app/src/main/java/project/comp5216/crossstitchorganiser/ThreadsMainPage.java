package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ThreadsMainPage extends Activity {

    private static final String APP_TAG = "Cross Stitch Organiser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(APP_TAG, "loading threads main page");
        setContentView(R.layout.activity_threads_main);
    }

    public void onFullInvNavClick(View v) {
        Log.v(APP_TAG, "Clicked view full inventory");
        Intent intent = new Intent(this, ThreadsInventoryFullPage.class);
        startActivity(intent);
    }

    public void onInvColourNavClick(View v) {
        Log.v(APP_TAG, "clicked view inventory by colour");
        Intent intent = new Intent(this, ThreadsInventoryColourPage.class);
        startActivity(intent);

    }

    public void onAddThreadNavClick(View v) {
        Log.v(APP_TAG, "clicked add thread");
        Intent intent = new Intent(this, ThreadsAddPage.class);
        startActivity(intent);
    }

    public void onThreadsBackClick(View v) {
        Log.v(APP_TAG, "clicked back from threads main");
        finish();
    }

}
