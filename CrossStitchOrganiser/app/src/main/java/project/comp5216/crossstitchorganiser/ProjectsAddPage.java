package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProjectsAddPage extends Activity {

    private static final String APP_TAG = "Cross Stitch Organiser";
	
	private String projectTitle;
	private Project newProject;
	private List<ThreadDetails> threadDetails;

    private ProjectDao projectDao;
	private OrganiserDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_add);
        Log.v(APP_TAG, "Loading Projects Add Page");


        // Loading the dynamic thread amount adding!
        LinearLayout ll = (LinearLayout) findViewById(R.id.projectsAddThreadDets);
        threadDetails = new ArrayList<ThreadDetails>();
        for (int i = 0; i < 3; i++) {
			View view = LayoutInflater.from(this).inflate(R.layout.view_add_thread_to_project, null);
			EditText dmcET = view.findViewById(R.id.projectsAddThreadDmc);
			EditText amountET = view.findViewById(R.id.projectsAddThreadAmount);
			threadDetails.add(new ThreadDetails(dmcET, amountET));
			ll.addView(view);
		}


		// Loading the database
		db = OrganiserDatabase.getDatabase(this.getApplication().getApplicationContext());
		projectDao = db.projectDao();

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

		// TODO: save the threads and amount in the DB. for now just add them
		// (successfully) to the project

		// Going through the thread info now
		for(ThreadDetails td : threadDetails) {
			// TODO: sanity check this. (What about invalid inputs?)
			newProject.addThreadAmount(td.getDmc(), td.getAmount());
		}

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

class ThreadDetails {

	// TODO: fix up this class (it's own file??)
	// needs to store the edit text for each 
	private EditText dmcET;
	private EditText amountET;

	public ThreadDetails(EditText dmc, EditText amount) {
		this.dmcET = dmc;
		this.amountET = amount;
	}

	public String getDmc() {
		return this.dmcET.getText().toString();
	}

	public double getAmount() {
		return Double.parseDouble(this.amountET.getText().toString());
	}

	public String toString() {
		return this.dmcET.getText().toString();
	}
}
