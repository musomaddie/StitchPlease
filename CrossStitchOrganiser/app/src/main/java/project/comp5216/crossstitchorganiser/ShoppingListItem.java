package project.comp5216.crossstitchorganiser;

public class ShoppingListItem {

    private String thread;
    private int amount; // this is an int because you can only buy whole skeins at a time
    private boolean found; // stores whether or not the user has found it

    public ShoppingListItem(String threadName, int amount) {
        this.thread = threadName;
        this.amount = amount;
        this.found = false;
    }

    public String getThread() {
        return this.thread;
    }

    public int getAmount() {
        return this.amount;
    }

    public void mark() {
        this.found = !this.found;
    }

    public String toString() {
        return this.thread + ": " + this.amount;
    }

}
