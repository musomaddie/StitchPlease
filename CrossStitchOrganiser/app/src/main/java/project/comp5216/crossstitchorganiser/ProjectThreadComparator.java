package project.comp5216.crossstitchorganiser;

import java.util.Comparator;

public class ProjectThreadComparator implements Comparator<ProjectThread> {

    private boolean isNumeric(String s) {
        return s.matches("^\\d+$");
    }

    @Override
    public int compare(ProjectThread t1, ProjectThread t2) {
        boolean t1N = isNumeric(t1.dmc);
        boolean t2N = isNumeric(t2.dmc);

        if (!t1N && !t2N) {
            if (t1.dmc.toUpperCase().equals("BLANC")) {
                return -1;
            }
            return 1;
        }
        if (t1N && !t2N) { return 1; }
        if (!t1N && t2N) {return -1; }
        if (t1N && t2N) {
            return Integer.parseInt(t1.dmc) - Integer.parseInt(t2.dmc);
        }

        return 0;
    }
}
