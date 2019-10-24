package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class ShoppingListPage extends Activity {

	private static final String APP_TAG = "Cross Stitch Organiser";

	private ListView listView;
	private List<ShoppingListItem> items;
	private ArrayAdapter<ShoppingListItem> shoppingAdapter;

	private Map<String, Project> allProjects;
	private Map<String, Double> allThreadAmounts;
	private Map<String, Thread> allThreads;
	private String thisProject;

	private OrganiserDatabase db;
	private ProjectThreadDao projectThreadDao;
	private ThreadDao threadDao;
	private ProjectDao projectDao;

	// data that will be shared
	private String shareText;


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
		thisProject = getIntent().getStringExtra("projectsIncluded");
		populateShoppingList();

		// list all items

		// Setting up the list view
		listView = findViewById(R.id.shoppingListList);
		shoppingAdapter = new ShoppingListItemAdapter(this, items);
		listView.setAdapter(shoppingAdapter);


		// item listener time!!
		setUpShoppingItemListener();


		shareText = "";
		for (ShoppingListItem item : items) {
			if (item != null) {
				// TODO: customise this based on tick or not
				shareText += "[ ] " + item.getThread() + " x " + item.getAmount() + "\n";
			}
		}



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
				updateItem.mark();


			}
		});
	}

	private void populateShoppingList() {
		loadAllProjectsFromDB();
		loadAllProjectsThreadsFromDB();
		loadAllThreadsFromDB();
		calculateThreadAmountAll();
	}

	private void calculateThreadAmountAll() {
		items = new ArrayList<ShoppingListItem>();
		for (String dmc: allThreadAmounts.keySet()) {
			if (!allThreads.containsKey(dmc)) {
				addItem(dmc, allThreadAmounts.get(dmc), 0.0);
				continue;
			}
			if (allThreadAmounts.get(dmc) > allThreads.get(dmc).getAmountOwned()) {
				addItem(dmc, allThreadAmounts.get(dmc), allThreads.get(dmc).getAmountOwned());
			}
		}
	}

	private void addItem(String dmc, double amountNeeded, double amountOwned) {
		double amountLeft = amountNeeded - amountOwned;
		int amountToAdd = (int) (amountLeft + 1);
		if (amountLeft - (int) amountLeft > 0.7) {
			amountToAdd ++;
		}
		items.add(new ShoppingListItem(dmc, amountToAdd));
	}

	private boolean inWishlist(ProjectThreadDatabaseItem item) {
		if (!thisProject.equals("all")) {
			return false;
		}
		return allProjects.get(item.getProjectName()).isWishlist();
	}

	private boolean inProject(ProjectThreadDatabaseItem item) {
		if (thisProject.equals("all")) { 
			return true;
		}
		return thisProject.equals(item.getProjectName());
	}

	private void loadAllProjectsFromDB() {
		try {
			new AsyncTask<Void, Void, Void>() {
				@Override
				public Void doInBackground(Void ... voids) {
					allProjects = new HashMap<String, Project>();
					List<ProjectDatabaseItem> projectsFromDB = projectDao.listAll();
					if (projectsFromDB != null && projectsFromDB.size() > 0) {
						for (ProjectDatabaseItem item : projectsFromDB) {
							allProjects.put(item.getTitle(),
									new Project(item.getTitle(), 
										item.isWishlist(), 
										item.getPicturePath()));
						}
					}
					return null;
				}
			}.execute().get();
		} catch(Exception ex) {
			Log.e(APP_TAG, ex.getStackTrace().toString());
		}
	}

	private void loadAllThreadsFromDB() {
		try {
			new AsyncTask<Void, Void, Void>() {
				@Override
				public Void doInBackground(Void ... voids) {
					allThreads = new HashMap<String, Thread>();
					List<ThreadDatabaseItem> threadsFromDB = threadDao.listAll();
					if (threadsFromDB != null && threadsFromDB.size() > 0) {
						for (ThreadDatabaseItem item : threadsFromDB) {
							allThreads.put(item.getDmc(),
									new Thread(item.getDmc(),
											Colour.findColour(item.getColour()),
											item.getAmountOwned()));
						}
					}
					return null;
				}
			}.execute().get();
		} catch(Exception ex) {
			Log.e(APP_TAG, ex.getStackTrace().toString());
		}
	}

	private void loadAllProjectsThreadsFromDB() {
		try {
			new AsyncTask<Void, Void, Void>() {
				@Override
				public Void doInBackground(Void ... voids) {
					allThreadAmounts = new HashMap<String, Double>();
					List<ProjectThreadDatabaseItem> threadsFromDB = projectThreadDao.listAll();
					if (threadsFromDB != null && threadsFromDB.size() > 0) {
						for (ProjectThreadDatabaseItem item : threadsFromDB) {
							if (inWishlist(item)) { continue; }
							if (!inProject(item)) { continue; }
							if (allThreadAmounts.containsKey(item.getThreadDmc())) {
								allThreadAmounts.put(item.getThreadDmc(),
										allThreadAmounts.get(item.getThreadDmc()) + item.getAmountNeeded());
							} else {
								allThreadAmounts.put(item.getThreadDmc(), item.getAmountNeeded());
							}
						}
					}
					return null;
				}
			}.execute().get();
		} catch(Exception ex) {
			Log.e(APP_TAG, ex.getStackTrace().toString());
		}
	}

	//sending shopping list as a plain text message
	public void share_onClick(View view){
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Shopping List");
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "threads to buy:\n"+shareText);
		startActivity(Intent.createChooser(sharingIntent, "Share text via"));
	}


}
