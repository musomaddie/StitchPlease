package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ProjectsAddPage extends Activity {

    private static final String APP_TAG = "Cross Stitch Organiser";
	
	private String projectTitle;
	private Project newProject;

    private ProjectDao projectDao;
	private OrganiserDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(APP_TAG, "Loading Projects Add Page");

		// Loading the database
		db = OrganiserDatabase.getDatabase(this.getApplication().getApplicationContext());
		projectDao = db.projectDao();

        setContentView(R.layout.activity_projects_add);
    }

	public void onProjectsAddSubmitClick(View view) {
		EditText titleET = findViewById(R.id.projectsAddTitleChange);
		projectTitle = titleET.getText().toString();
		if (projectTitle.equals("")) {
			Toast.makeText(this,
					// TODO: update this error message to project
					getResources().getString(R.string.failure_add_thread_no_dmc),
					Toast.LENGTH_SHORT).show();
		}
		// TODO: need to check it doesn't already exist!
		newProject = new Project(projectTitle, false);
		saveProjectToDatabase();
	}

    public void onProjectsAddBackClick(View view) {
        Log.v(APP_TAG, "Clicked back from projects add");
        finish();
    }

	private void saveProjectToDatabase() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
				projectDao.insert(new ProjectDatabaseItem(newProject.getName(),
							newProject.isWishlist()));
				Log.i(APP_TAG, "Saved project: " + newProject.toString() + " to database");
                return null;
            }
        }.execute();
	}
}
