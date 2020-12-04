package project.comp5216.crossstitchorganiser;

import java.util.Comparator;

public class ShoppingListComparator implements Comparator<ShoppingListItem>{

    private boolean isNumeric(String s) {
        return s.matches("^\\d+$");
    }

    @Override
    public int compare(ShoppingListItem t1, ShoppingListItem t2) {
        boolean t1N = isNumeric(t1.getThread());
        boolean t2N = isNumeric(t2.getThread());

        if (!t1N && !t2N) {
            if (t1.getThread().toUpperCase().equals("BLANC")) {
                return -1;
            }
            return 1;
        }
        if (t1N && !t2N) { return 1; }
        if (!t1N && t2N) {return -1; }
        if (t1N && t2N) {
            return Integer.parseInt(t1.getThread()) - Integer.parseInt(t2.getThread());
        }

        return 0;
    }
}
