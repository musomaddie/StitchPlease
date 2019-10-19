package project.comp5216.crossstitchorganiser;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class Project {

    private static final String APP_TAG = "Cross Stitch Organiser";

    private String title; // stores the name of the project
    // TODO: store the image associated with the project here
    private Map<String, Double> threadsAmountNeeded;
    // stores the thread needed mapped to how much is needed for this particular project.
    private boolean isWishlist;
    // true if on wishlist, false if not (owned)

    public Project(String title, boolean isWishlist) {
        this.title = title;
        this.threadsAmountNeeded = new HashMap<String, Double>();
        this.isWishlist = isWishlist;
        Log.i(APP_TAG, "Created new project: " + this.toString());
    }

    public String getTitle() {
        return this.title;
    }

    public void addThreadAmount(String thread, double a) {
        this.threadsAmountNeeded.put(thread, a);
    }

    public void buy() {
        this.isWishlist = false;
    }

    public Map<String, Double> getThreadsAmountNeeded() {
        return this.threadsAmountNeeded;
    }

    // use this when only interested in which threads, not how much.
    public String [] getThreadsNeeded() {
        return this.threadsAmountNeeded.keySet().toArray(
                new String[this.threadsAmountNeeded.size()]);
    }

	public boolean isWishlist() {
		return this.isWishlist;
	}

    public String toString() {
        return this.title + ": " + this.threadsAmountNeeded.toString();
    }
}
