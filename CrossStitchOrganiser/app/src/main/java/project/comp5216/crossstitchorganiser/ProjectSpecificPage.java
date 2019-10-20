package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class ProjectSpecificPage  extends Activity {

    private static final String APP_TAG = "Cross Stitch Organiser";
	
	private String thisProjectTitle;
    private Project thisProject;
	private OrganiserDatabase db;
    private ProjectDao projectDao;
    private ProjectThreadDao projectThreadDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_specific);
		thisProjectTitle = getIntent().getStringExtra("name");

		// Setting up the DB
		db = OrganiserDatabase.getDatabase(this.getApplication().getApplicationContext());
		projectDao = db.projectDao();
		projectThreadDao = db.projectThreadDao();

		// Loading the project details from db
		loadDetailsFromDatabase();

		//Setting up title
		TextView title = findViewById(R.id.specificProjectTitle);
		title.setText(thisProject.getTitle());
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

	private void loadDetailsFromDatabase() {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    // Find the project from the database
                    ProjectDatabaseItem projectFromDB = projectDao.loadProject(thisProjectTitle);
                    if (projectFromDB != null) {
                        thisProject = new Project(projectFromDB.getTitle(),
                                projectFromDB.isWishlist(),
                                projectFromDB.getPicturePath());
                    }
                    Log.i(APP_TAG, "Read item from database: " + projectFromDB.getTitle());
                    // Load all thread details with it
                    return null;
                }
            }.execute().get();
        } catch (Exception ex) {
            Log.e(APP_TAG, ex.getStackTrace().toString());
        }
    }
}
