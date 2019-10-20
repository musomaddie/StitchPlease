package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectsWishlistPage extends Activity {

    private static final String APP_TAG = "Cross Stitch Organiser";


    private ListView listView;
    private List<Project> wishlistProjects;
    private ArrayAdapter<Project> projectAdapter;

    private ProjectDao projectDao;
	private OrganiserDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_wishlist);
        Log.v(APP_TAG, "Loading projects wishlist");

        // Setting up the database
		db = OrganiserDatabase.getDatabase(this.getApplication().getApplicationContext());
		projectDao = db.projectDao();

		loadWishlistProjects();

		// Setting up list view
        listView = (ListView) findViewById(R.id.projectsWishlistList);
        projectAdapter = new ProjectAdapter(this, wishlistProjects);
        listView.setAdapter(projectAdapter);
        setUpProjectItemListener();

    }

    public void onProjectsWishlistBackClick(View view) {
        Log.d(APP_TAG, "clicked back from projects wishlist");
        finish();
    }

    private void setUpProjectItemListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Project updateProject = (Project) projectAdapter.getItem(position);
                Log.i(APP_TAG, "Clicked item " + position + ": " + updateProject.getTitle());

                Intent intent = new Intent(ProjectsWishlistPage.this,
                        ProjectSpecificPage.class);

                if (intent != null) {
                    intent.putExtra("name", updateProject.getTitle());
                    startActivity(intent);
                }
            }
        });
    }

	private void loadWishlistProjects() {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                	List<ProjectDatabaseItem> itemsFromDB = projectDao.listAll();
                	wishlistProjects = new ArrayList<Project>();
                    if (itemsFromDB != null && itemsFromDB.size() > 0) {
                    	for (ProjectDatabaseItem item: itemsFromDB) {
                    		// For display in list view, we don't care about the
                    		// thread information
                    		Project project = new Project(item.getTitle(), item.isWishlist(), item.getPicturePath());
                    		if (project.isWishlist()) {
                    			wishlistProjects.add(project);
							}
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
