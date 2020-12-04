package project.comp5216.crossstitchorganiser;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class Project {

    private static final String APP_TAG = "Cross Stitch Organiser";

    private String title;
    private Map<String, Double> threadsAmountNeeded;
    private String pathToImage;
    private boolean isWishlist;
    // true if on wishlist, false if not (owned)

    public Project(String title, boolean isWishlist) {
        this.title = title;
        this.threadsAmountNeeded = new HashMap<String, Double>();
        this.isWishlist = isWishlist;
        this.pathToImage = null;
        Log.i(APP_TAG, "Created new project: " + this.toString());
    }

    // Project constructor with images
    public Project(String name, boolean isWishlist, String image) {
        this.title = name;
        this.threadsAmountNeeded = new HashMap<String, Double>();
        this.isWishlist = isWishlist;
        this.pathToImage = image;
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

    public String toString() {
        return this.title + ": " + this.threadsAmountNeeded.toString();
    }

    public boolean isWishlist() {
        return isWishlist;
    }

    public String getPathToImage() {
    	return this.pathToImage;
	}
}
