package project.comp5216.crossstitchorganiser;

import java.util.ArrayList;
import java.util.List;

public enum Colour {
	GREY,
	WHITE,
	BLACK,
	RED,
	ORANGE,
	YELLOW,
    GREEN,
    BLUE,
    PURPLE,
    PINK;

    public static List<Thread> loadThreads(Colour c) {
        String colourName = c.toString();
        // query the database using the appropriate colour name
        List<Thread> theseThreads = new ArrayList<Thread>();
        theseThreads.add(new Thread("310", Colour.BLACK, 1.2));
        theseThreads.add(new Thread("550", Colour.PURPLE, 1.1));
        return theseThreads;
    }
}
