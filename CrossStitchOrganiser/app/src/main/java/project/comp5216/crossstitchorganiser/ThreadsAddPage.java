package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ThreadsAddPage extends Activity {
    private static final String APP_TAG = "Cross Stitch Organiser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads_add);
        Log.v(APP_TAG, "Loading threads add page");
    }

    public void onThreadsAddBackClick(View view) {
        Log.v(APP_TAG, "Clicked back in threads add");
        finish();
    }


}
