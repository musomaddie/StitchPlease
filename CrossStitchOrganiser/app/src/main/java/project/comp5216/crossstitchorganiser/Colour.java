package project.comp5216.crossstitchorganiser;

import java.util.ArrayList;
import java.util.List;

public enum Colour {
	GREY {public String transformText() { return "Grey"; }},
	WHITE {public String transformText() { return "White"; }},
	BLACK {public String transformText() { return "Black"; }},
	RED {public String transformText() { return "Red"; }},
	ORANGE {public String transformText() { return "Orange"; }},
	YELLOW {public String transformText() { return "Yellow"; }},
    GREEN {public String transformText() { return "Green"; }},
    BLUE {public String transformText() { return "Blue"; }},
    PURPLE {public String transformText() { return "Purple"; }},
    PINK {public String transformText() { return "Pink"; }};

	public abstract String transformText();

    public static List<Thread> loadThreads(Colour c) {
        String colourName = c.toString();
        // query the database using the appropriate colour name
        List<Thread> theseThreads = new ArrayList<Thread>();
        theseThreads.add(new Thread("310", Colour.BLACK, 1.2));
        theseThreads.add(new Thread("550", Colour.PURPLE, 1.1));
        return theseThreads;
    }

	public static List<String> makeList() {
		List<String> colourList = new ArrayList<String>();
		for (Colour c : Colour.values()) {
			colourList.add(c.transformText());
		}
		return colourList;
	}

	public static Colour findColour(String colour) {
		for (Colour c : Colour.values()) {
			if (c.transformText().equals(colour)) {
				return c;
			}
		} 
		return null;
	}
}
