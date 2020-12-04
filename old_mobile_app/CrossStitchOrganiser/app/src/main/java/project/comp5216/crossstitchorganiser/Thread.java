package project.comp5216.crossstitchorganiser;

// TODO: problem: means thread can be added with ONLY an amount needed instead of an amount owned. Have a special case in add project for this.

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class Thread {

    private static final String APP_TAG = "Cross Stitch Organiser";

    private String dmc; // This is its number: the unique identifier
    // TODO: decide if we want the user to enter Colour or we calculate ourselves.
    private Colour colour; // The colour of the thread (For grouping later)
    private double amountOwned; // the amount of thread the user owns
    private double amountNeeded; // the amount of thread the user needs for all projects -> calculated by us based on projects reqs.
    private Map<String, Double> projects;

	public Thread(String dmc, Colour colour, double amountOwned, double amountNeeded) {
		this(dmc, colour, amountOwned);
		this.amountNeeded = amountNeeded;
        this.projects = new HashMap<String, Double>();
	}

    public Thread(String dmc, Colour colour, double amountOwned) {
        this.dmc = dmc;
        this.colour = colour;
        this.amountOwned = amountOwned;
        this.amountNeeded = 0;
        this.projects = new HashMap<String, Double>();
        Log.i(APP_TAG, "Created new thread: " + this.toString());
    }

    public String getDmc() {
        return this.dmc;
    }

    public Colour getColour() {
        return this.colour;
    }

    public double getAmountOwned() {
        return this.amountOwned;
    }

    public Map<String, Double> getProjects() {
        return this.projects;
    }

    public void addProject(String project, double amountNeeded) {
    	this.projects.put(project, Double.valueOf(amountNeeded));
    }

    public String toString() {
		return this.dmc;
    }

}
