package project.comp5216.crossstitchorganiser;

import java.util.Comparator;

public class ThreadComparator implements Comparator<Thread> {

    private boolean isNumeric(String s) {
        return s.matches("^\\d+$");
    }

    @Override
    public int compare(Thread t1, Thread t2) {
        boolean t1N = isNumeric(t1.getDmc());
        boolean t2N = isNumeric(t2.getDmc());

        if (!t1N && !t2N) {
            if (t1.getDmc().toUpperCase().equals("BLANC")) {
                return -1;
            }
            return 1;
        }
        if (t1N && !t2N) { return 1; }
        if (!t1N && t2N) {return -1; }
        if (t1N && t2N) {
            return Integer.parseInt(t1.getDmc()) - Integer.parseInt(t2.getDmc());
        }

        return 0;
    }
}
