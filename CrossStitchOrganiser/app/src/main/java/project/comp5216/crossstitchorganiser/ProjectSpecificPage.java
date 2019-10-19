package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ProjectSpecificPage  extends Activity {

    private static final String APP_TAG = "Cross Stitch Organiser";

    private Project thisProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String thisProjectName = getIntent().getStringExtra("name");
        // TODO: this is dummy data: actually fetch the project from the db and load there.
        thisProject = new Project(thisProjectName, false);
        setContentView(R.layout.activity_project_specific);

        TextView title = findViewById(R.id.specificProjectTitle);
        title.setText(thisProject.getTitle());
        Log.v(APP_TAG, "Loading Colour specific page for: " + thisProjectName);

        // TODO: will need a list view to list all the threads
    }

    public  void onShoppingListNavClick(View view) {
        Log.v(APP_TAG, "clicked shopping list from project");
        // TODO: create a shopping list only from this project!
        Intent intent = new Intent(this, ShoppingListPage.class);
        intent.putExtra("projectsIncluded", thisProject.getTitle());
        startActivity(intent);
    }

    public void onSpecificProjectBackClick(View view) {
        Log.v(APP_TAG, "clicked back from thread specific page");
        finish();
    }
}
