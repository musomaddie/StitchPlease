package project.comp5216.crossstitchorganiser;

// Special class just to store thread how it appears in the project list
public class ProjectThread {

	public final String dmc;
	public final double amountNeeded;

	public ProjectThread(String dmc, double amountNeeded) {
		this.dmc = dmc;
		this.amountNeeded = amountNeeded;
	}
}
