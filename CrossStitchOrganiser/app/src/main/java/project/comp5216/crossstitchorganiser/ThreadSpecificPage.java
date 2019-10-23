package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ThreadSpecificPage extends Activity {

    private static final String APP_TAG = "Cross Stitch Organiser";

    private Thread thisThread;
	private String thisDmc;
    private ListView listView;
	private List<ThreadProject> projectsToView;
    ArrayAdapter<ThreadProject> projectAdapter;

	private OrganiserDatabase db;
    private ThreadDao threadDao;
    private ProjectThreadDao projectThreadDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_specific);
		// Loading the database
		db = OrganiserDatabase.getDatabase(this.getApplication().getApplicationContext());
		threadDao = db.threadDao();
		projectThreadDao = db.projectThreadDao();
			
		
		// Loading the details for the thread
        thisDmc = getIntent().getStringExtra("dmc");
		loadThread();

		// The title
        TextView title = findViewById(R.id.specificThreadTitle);
        title.setText(thisThread.toString());

		// The colour square
        View squareColour = findViewById(R.id.specificThreadColourRect);
		Map<String, String> dmcToHex = new DMC_toHex().hexColours;
		if (dmcToHex.containsKey(thisThread.getDmc())) {
			squareColour.setBackgroundColor(Color.parseColor(dmcToHex.get(thisThread.getDmc())));
		} else {
			squareColour.setBackground(thisThread.getColour().findColourResource(this));
		}

		// The amount owned
		TextView amountOwnedV = findViewById(R.id.specificThreadAmountOwned);
		amountOwnedV.setText(Double.valueOf(thisThread.getAmountOwned()).toString());

		// All the projects
		listView = findViewById(R.id.specificThreadProjectList);
		loadProjects();
		projectAdapter = new ThreadProjectAdapter(this, projectsToView);
		listView.setAdapter(projectAdapter);

		Log.v(APP_TAG, "Loading Thread specific page for: " + thisThread.getDmc());
    }

    public void onSpecificThreadBackClick(View view) {
        Log.v(APP_TAG, "clicked back from thread specific page");
        finish();
    }

    private void loadProjects() {
    	projectsToView = new ArrayList<ThreadProject>();
		for (String projectTitle : thisThread.getProjects().keySet()) {
			projectsToView.add(new ThreadProject(projectTitle, thisThread.getProjects().get(projectTitle)));
		}
	}

	private void loadThread() {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                	ThreadDatabaseItem threadFromDB = threadDao.findThread(thisDmc);
                	if (threadFromDB != null) {
                		thisThread = new Thread(threadFromDB.getDmc(),
                				Colour.findColour(threadFromDB.getColour()),
                				threadFromDB.getAmountOwned());
					}
					List<ProjectThreadDatabaseItem> projects = projectThreadDao.findProjects(thisDmc);
					if (threadFromDB != null && projects != null && projects.size() > 0) {
						for (ProjectThreadDatabaseItem project : projects) {
							thisThread.addProject(project.getProjectName(), project.getAmountNeeded());
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
