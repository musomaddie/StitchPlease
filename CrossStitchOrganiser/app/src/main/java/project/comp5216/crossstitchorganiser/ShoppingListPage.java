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
import java.util.Arrays;
import java.util.List;

public class ShoppingListPage extends Activity {

    private static final String APP_TAG = "Cross Stitch Organiser";

    private List<ShoppingListItem> items;
	private List<String> projects;
	private List<ProjectThread> threadsNeeded;
    private ListView listView;
    private ArrayAdapter<ShoppingListItem> shoppingAdapter;

	// Following is used to calculate for each thread
	private List<ThreadProject> projectsForThisThread;
	private Thread thisThread;
	private String thisDmc;

	private OrganiserDatabase db;
    private ProjectThreadDao projectThreadDao;
    private ThreadDao threadDao;
	private ProjectDao projectDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        Log.v(APP_TAG, "Loading shopping list page");

		// Setting up the DB
		db = OrganiserDatabase.getDatabase(this.getApplication().getApplicationContext());
		projectThreadDao = db.projectThreadDao();
		threadDao = db.threadDao();
		projectDao = db.projectDao();

        // Get the intent with the information on what projects to include
        populateShoppingList(getIntent().getStringExtra("projectsIncluded"));

        // list all items

        // Setting up the list view
        listView = findViewById(R.id.shoppingListList);
        shoppingAdapter = new ShoppingListItemAdapter(this, items);
        listView.setAdapter(shoppingAdapter);


        // item listener time!!
        setUpShoppingItemListener();
    }

    public void onShoppingListBackClick(View view) {
        Log.v(APP_TAG, "clicked back from shopping list");
        finish();
    }

    private void setUpShoppingItemListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShoppingListItem updateItem = (ShoppingListItem) shoppingAdapter.getItem(position);
                Log.i(APP_TAG, "Clicked item " + position + ": " + updateItem.getThread());

                // TODO: display the updated change
                updateItem.mark();
            }
        });
    }

    private void populateShoppingList(String includedProjects) {
		// Check if it is equal to ALL projects or not.
		if (includedProjects.equals("all")) {
			Log.d(APP_TAG, "CALCULATING SHOPPING LIST FOR ALL PROJECTS!");
			// TODO: this is all dummy data, populate later
			items = new ArrayList<ShoppingListItem>();
			items.add(new ShoppingListItem("310", 2));
			items.add(new ShoppingListItem("550", 2));
			return;
		}
		// Otherwise it's a particular project
		projects = Arrays.asList(new String[] {includedProjects});
		// have the list of threads needed
        loadDetailsFromDatabase();
		calculateThreadAmount();
    }

	private void calculateThreadAmount() {
		items = new ArrayList<ShoppingListItem>();
		for (ProjectThread thread : threadsNeeded) {
            // find the thread
            thisDmc = thread.dmc;
            findThreadFromDatabase();
            double amountOwned = 0;
            if (thisThread != null) {
                amountOwned = thisThread.getAmountOwned();
            }
            // find how much is needed for all projects
			findProjectsForThreadFromDatabase();
			double amountNeededForThis = 0;
            double amountNeeded = 0;
			for (ThreadProject project : projectsForThisThread) {
				if (projects.contains(project.projectName)) {
					// Add it to amount needed for this
					amountNeededForThis += project.amountNeeded;
					continue;
				}
				amountNeeded += project.amountNeeded;
			}
			double amountLeft = amountOwned - amountNeeded;
			if (amountLeft <= amountNeededForThis) {
				// we need to add it to the shopping list
				// HOW MUCH: the difference rounded up
				double amountToAdd = amountNeededForThis - amountLeft;
				// if it is under 0.7 round up
				if (amountToAdd - (int) amountToAdd > 0.7) {
					amountToAdd ++;
				}
				items.add(new ShoppingListItem(thisDmc, (int)amountToAdd + 1));
			}
        }
		// Find the amount owned
		// For each thread in threads needed:
		// How much is needed = 
		// 		amountOwned - amountNeededForAllProjects
		// if this is < amountNeeded for THIS project: add to the shopping list
	}

	private void findProjectsForThreadFromDatabase() {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
					projectsForThisThread = new ArrayList<ThreadProject>();
					List<ProjectThreadDatabaseItem> projectsFromDB = projectThreadDao.findProjects(thisDmc);
					if (projectsFromDB != null) {
						for (ProjectThreadDatabaseItem item : projectsFromDB) {
							projectsForThisThread.add(new ThreadProject(
										item.getProjectName(),
										item.getAmountNeeded()));
						}
					}
                    return null;
                }
            }.execute().get();
        } catch (Exception ex) {
            Log.e(APP_TAG, ex.getStackTrace().toString());
        }
	}

	private void findThreadFromDatabase() {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
					// find the thread
					ThreadDatabaseItem threadFromDB = threadDao.findThread(thisDmc);
					if (threadFromDB != null) {
						thisThread = new Thread(threadFromDB.getDmc(),
								Colour.findColour(threadFromDB.getColour()),
								threadFromDB.getAmountOwned());
					} else {
						thisThread = null;
					}
                    return null;
                }
            }.execute().get();
        } catch (Exception ex) {
            Log.e(APP_TAG, ex.getStackTrace().toString());
        }
    }

	private void loadDetailsFromDatabase() {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
					// Find all the threads associated with each project
					threadsNeeded = new ArrayList<ProjectThread>();
					for (String projectTitle : projects) {
						List<ProjectThreadDatabaseItem> threadsFromDB = projectThreadDao.findThreads(projectTitle);
						if (threadsFromDB != null && threadsFromDB.size() > 0) {
							for (ProjectThreadDatabaseItem item: threadsFromDB) {
								// TODO: deal with case of duplicate threads
								threadsNeeded.add(new ProjectThread(item.getThreadDmc(), item.getAmountNeeded()));
							}
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
