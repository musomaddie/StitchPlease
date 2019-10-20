package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProjectSpecificPage  extends Activity {

    private static final String APP_TAG = "Cross Stitch Organiser";
	
	private String thisProjectTitle;
    private Project thisProject;
    ArrayAdapter<ProjectThread> threadAdapter;
    private ListView listView;
    private List<ProjectThread> threadsToView;

	private OrganiserDatabase db;
    private ProjectDao projectDao;
    private ProjectThreadDao projectThreadDao;
    private ThreadDao threadDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_specific);
		thisProjectTitle = getIntent().getStringExtra("name");

		// Setting up the DB
		db = OrganiserDatabase.getDatabase(this.getApplication().getApplicationContext());
		projectDao = db.projectDao();
		projectThreadDao = db.projectThreadDao();
		threadDao = db.threadDao();

		// Loading the project details from db
		loadDetailsFromDatabase();

		//Setting up title
		TextView title = findViewById(R.id.specificProjectTitle);
		title.setText(thisProject.getTitle());

		// Viewing the image
		ImageView image = findViewById(R.id.specificProjectImage);
		if (thisProject.getPathToImage() != null) {
			// TODO: source from stack overflow (again)
			File imageFile = new File(thisProject.getPathToImage());
			if (imageFile.exists()) {
				Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
				image.setImageBitmap(bitmap);
			}
		}

		// Viewing the buy button (only if wishlist)
		Button buyButton = findViewById(R.id.specificProjectBuy);
		if (thisProject.isWishlist()) {
			buyButton.setVisibility(View.VISIBLE);
		}

		// Loading the threads
		listView = findViewById(R.id.specificProjectThreadList);
        loadThreads();
		threadAdapter = new ProjectThreadAdapter(this, threadsToView);
		listView.setAdapter(threadAdapter);
		// TODO: sort them
		// TODO: handle clicks on items that don't have a thread buddy
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

    public void onProjectBuyClick(View view) {
    	Log.v(APP_TAG, "clicked buy project!");
    	// TODO: the work of moving this project to purchased. (should be
    	// straightfoward)
    }

    private void loadThreads() {
    	threadsToView = new ArrayList<ProjectThread>();
    	for (String key : thisProject.getThreadsAmountNeeded().keySet()) {
			threadsToView.add(new ProjectThread(key, thisProject.getThreadsAmountNeeded().get(key)));
		}
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
                    List<ProjectThreadDatabaseItem> threadsFromDB = projectThreadDao.findThreads(thisProjectTitle);
                    if (thisProject != null && threadsFromDB != null && threadsFromDB.size() > 0) {
                    	for (ProjectThreadDatabaseItem item: threadsFromDB) {
                    		thisProject.addThreadAmount(item.getThreadDmc(), item.getAmountNeeded());
						}
					}
                    return null;
                }
            }.execute().get();
        } catch (Exception ex) {
            Log.e(APP_TAG, ex.getStackTrace().toString());
        }
    }
}

/*
private class ThreadsComparator<Thread> implements Comparator {

	static boolean isInt(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	int compare(Thread t1, Thread t2) {
		// if only one of them is a number then it's last
		boolean t1_num = isInt(t1.getDmc());
		boolean t2_num = isInt(t2.getDmc());

		if (t1_num && !t2_num) {
			return 1;
		}
		else if (!t1_num && t2_num) {
			return -1;
		}
		else if (t1_num && t2_num) {
			return Integer.parseInt(t1.getDmc()).compareTo(Integer.parseInt(t2.getDmc()));
		}
		else {
			return t1.getDmc().compareTo(t2.getDmc());
		}
	}
}
*/
