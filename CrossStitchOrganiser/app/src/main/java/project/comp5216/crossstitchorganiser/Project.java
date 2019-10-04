package project.comp5216.crossstitchorganiser;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class Project {

    private static final String APP_TAG = "Cross Stitch Organiser";

    private String name; // stores the name of the project
    // TODO: store the image associated with the project here
    private Map<Thread, Double> threadsAmountNeeded;
    // stores the thread needed mapped to how much is needed for this particular project.
    private boolean isWishlist; // true if on wishlist, false if not (owned)
    // TODO: not sure if isWishlist is needed in final product -> could just store
    //  in two different places

    //TODO: not sure which constructor is more appropriate so currently have both
    public Project(String name, boolean isWishlist) {
        this.name = name;
        this.threadsAmountNeeded = new HashMap<Thread, Double>();
        this.isWishlist = isWishlist;
        Log.i(APP_TAG, "Created new project: " + this.toString());
    }

    public Project(String name, Map<Thread, Double> threads, boolean w) {
        this.name = name;
        this.threadsAmountNeeded = threads;
        this.isWishlist = w;
        Log.i(APP_TAG, "Created new project: " + this.toString());
    }

    public String getName() {
        return this.name;
    }

    public void addThreadAmount(Thread thread, double a) {
        this.threadsAmountNeeded.put(thread, a);
    }

    public void buy() {
        this.isWishlist = false;
    }

    public Map<Thread, Double> getThreadsAmountNeeded() {
        return this.threadsAmountNeeded;
    }

    // use this when only interested in which threads, not how much.
    public Thread [] getThreadsNeeded() {
        return this.threadsAmountNeeded.keySet().toArray(
                new Thread[this.threadsAmountNeeded.size()]);
    }

    public String toString() {
        return this.name + ": " + this.threadsAmountNeeded.toString();
    }
}
