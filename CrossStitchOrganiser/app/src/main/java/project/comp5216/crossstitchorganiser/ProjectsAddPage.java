package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
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
	private List<Project> allExistingProjects;
	private boolean isWishlist;

	private OrganiserDatabase db;
    private ProjectDao projectDao;
    private ProjectThreadDao projectThreadDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_add);
        Log.v(APP_TAG, "Loading Projects Add Page");

        // Loading the dynamic thread amount adding!
        LinearLayout ll = (LinearLayout) findViewById(R.id.projectsAddThreadDets);
        threadDetails = new ArrayList<ThreadDetails>();
        for (int i = 0; i < 10; i++) {
			View view = LayoutInflater.from(this).inflate(R.layout.view_add_thread_to_project, null);
			EditText dmcET = view.findViewById(R.id.projectsAddThreadDmc);
			EditText amountET = view.findViewById(R.id.projectsAddThreadAmount);
			threadDetails.add(new ThreadDetails(dmcET, amountET));
			ll.addView(view);
		}

		// Loading the database
		db = OrganiserDatabase.getDatabase(this.getApplication().getApplicationContext());
		projectDao = db.projectDao();
		projectThreadDao = db.projectThreadDao();

    }

	public void onProjectsAddSubmitClick(View view) {
		EditText titleET = findViewById(R.id.projectsAddTitleChange);
		projectTitle = titleET.getText().toString();
		if (existing()) {
			return;
		}
		if (projectTitle.equals("")) {
			Toast.makeText(this,
					getResources().getString(R.string.failure_add_project_no_title),
					Toast.LENGTH_SHORT).show();
			return;
		}
		newProject = new Project(projectTitle, isWishlist);


		// Going through the thread info now
		for(ThreadDetails td : threadDetails) {
			// TODO: deal with the case where added to this project twice!!
			String dmc = td.getDmc();
			if (dmc.equals("")) {
				// No message as some might be naturally blank
				continue;
			}
			newProject.addThreadAmount(dmc, td.getAmount());
		}
		saveProjectToDatabase();

		Toast.makeText(this,
				getResources().getString(R.string.success_project_add),
				Toast.LENGTH_SHORT).show();
	}

	public void onProjectsAddMoreThreadsClick(View view) {
		// TODO: fix issue with adding more threads moves the submit buttons
		// (etc). off screen
		Log.v(APP_TAG, "Clicked add more threads");
        LinearLayout ll = (LinearLayout) findViewById(R.id.projectsAddThreadDets);
        for (int i = 0; i < 5; i++) {
			View viewLL = LayoutInflater.from(this).inflate(R.layout.view_add_thread_to_project, null);
			EditText dmcET = view.findViewById(R.id.projectsAddThreadDmc);
			EditText amountET = view.findViewById(R.id.projectsAddThreadAmount);
			threadDetails.add(new ThreadDetails(dmcET, amountET));
			ll.addView(viewLL);
		}

	}

	public void onProjectsAddCheckboxClick(View view) {
		boolean checked = ((CheckBox) view).isChecked();
		isWishlist = checked;
	}

    public void onProjectsAddBackClick(View view) {
        Log.v(APP_TAG, "Clicked back from projects add");
        finish();
    }

    private boolean existing() {
    	loadAllFromDatabase();
    	for (Project p : allExistingProjects) {
			if (p.getTitle().equals(projectTitle)) {
				// refuse to accept it
				Toast.makeText(this,
						getResources().getString(R.string.failure_add_project_exist_p1)
						+ " " + projectTitle + " "
						+ getResources().getString(R.string.failure_add_project_exist_p2),
						Toast.LENGTH_SHORT).show();
				return true;
			}
		}
		return false;
	}

	private void saveProjectToDatabase() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
				projectDao.insert(new ProjectDatabaseItem(newProject.getTitle(),
							newProject.isWishlist()));
				Log.i(APP_TAG, "Saved project: " + newProject.toString() + " to database");
				for (String threadDmc : newProject.getThreadsAmountNeeded().keySet()) {
					double amount = newProject.getThreadsAmountNeeded().get(threadDmc);
					projectThreadDao.insert(
							new ProjectThreadDatabaseItem(newProject.getTitle(), threadDmc, amount));
					Log.i(APP_TAG, "Saved project thread: " + newProject.getTitle() + " " + threadDmc + " to database");
				}
                return null;
            }
        }.execute();
	}

	private void loadAllFromDatabase() {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                	List<ProjectDatabaseItem> itemsFromDB = projectDao.listAll();
                	allExistingProjects = new ArrayList<Project>();
                    if (itemsFromDB != null && itemsFromDB.size() > 0) {
                    	for (ProjectDatabaseItem item: itemsFromDB) {
                    		allExistingProjects.add(new Project(
                    					item.getTitle(),
                    					item.isWishlist()));
							Log.i(APP_TAG, "Read item from database: " + item.getTitle());
                        }
                    }
                    return null;
                }
            }.execute().get();
        } catch(Exception ex) {
			Log.e(APP_TAG, ex.getStackTrace().toString());
        }
	}
}

class ThreadDetails {

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
		try {
			return Double.parseDouble(this.amountET.getText().toString());
		} catch (NumberFormatException e) {
			// Just returning 1 as a default value.
			// TODO: mention somewhere this happens!
			return 1.0;
		}
	}

	public String toString() {
		return this.dmcET.getText().toString();
	}
}
