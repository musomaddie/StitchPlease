package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ProjectsMainPage extends Activity {

    private static final String APP_TAG = "Cross Stitch Organiser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_main);
        Log.v(APP_TAG, "Loading projects main page");
    }

    public void onCurrentProNavClick(View view) {
        Log.v(APP_TAG, "clicked current project");
        Intent intent = new Intent(this, ProjectsCurrentPage.class);
        startActivity(intent);
    }

    public void onWishlistNavClick(View view) {
        Log.v(APP_TAG, "clicked wishlist");
        Intent intent = new Intent(this, ProjectsWishlistPage.class);
        startActivity(intent);
    }

    public void onProjectsAddNavClick(View view) {
        Log.v(APP_TAG, "clicked projects add");
        Intent intent = new Intent(this, ProjectsAddPage.class);
        startActivity(intent);
    }

    public void onProjectsBackClick(View view) {
        Log.v(APP_TAG, "clicked projects back");
        finish();
    }
}
