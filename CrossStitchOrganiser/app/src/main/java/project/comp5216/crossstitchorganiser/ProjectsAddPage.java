package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ProjectsAddPage extends Activity {

    private static final String APP_TAG = "Cross Stitch Organiser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(APP_TAG, "Loading Projects Add Page");
        setContentView(R.layout.activity_projects_add);
    }

    public void onProjectsAddBackClick(View view) {
        Log.v(APP_TAG, "Clicked back from projects add");
        finish();
    }
}
