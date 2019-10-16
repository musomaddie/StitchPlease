package project.comp5216.crossstitchorganiser;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;

public enum Colour {

	GREY {
		public String transformText() { 
			return "Grey"; 
		}
		public ColorDrawable findColourResource(Context context) {
			return new ColorDrawable(context.getResources().getColor(R.color.greyColour));
		}
	},
	WHITE {
		public String transformText() { 
			return "White"; 
		}
		public ColorDrawable findColourResource(Context context) {
			return new ColorDrawable(context.getResources().getColor(R.color.whiteColour));
		}
	},
	BLACK {
		public String transformText() {
			return "Black"; 
		}
		public ColorDrawable findColourResource(Context context) {
			return new ColorDrawable(context.getResources().getColor(R.color.blackColour));
		}
	},
	RED {
		public String transformText() { 
			return "Red"; 
		}
		public ColorDrawable findColourResource(Context context) {
			return new ColorDrawable(context.getResources().getColor(R.color.redColour));
		}
	},
	ORANGE {
		public String transformText() { 
			return "Orange"; 
		}
		public ColorDrawable findColourResource(Context context) {
			return new ColorDrawable(context.getResources().getColor(R.color.orangeColour));
		}
	},
	YELLOW {
		public String transformText() {
			return "Yellow"; 
		}
		public ColorDrawable findColourResource(Context context) {
			return new ColorDrawable(context.getResources().getColor(R.color.yellowColour));
		}
	},
    GREEN {
		public String transformText() { 
			return "Green"; 
		}
		public ColorDrawable findColourResource(Context context) {
			return new ColorDrawable(context.getResources().getColor(R.color.greenColour));
		}
	},
    BLUE {
		public String transformText() {
			return "Blue"; 
		}
		public ColorDrawable findColourResource(Context context) {
			return new ColorDrawable(context.getResources().getColor(R.color.blueColour));
		}
	},
    PURPLE {
		public String transformText() { 
			return "Purple"; 
		}
		public ColorDrawable findColourResource(Context context) {
			return new ColorDrawable(context.getResources().getColor(R.color.purpleColour));
		}
	},
    PINK {
		public String transformText() { 
			return "Pink"; 
		}
		public ColorDrawable findColourResource(Context context) {
			return new ColorDrawable(context.getResources().getColor(R.color.pinkColour));
		}
	};

	public abstract String transformText();
	public abstract ColorDrawable findColourResource(Context context);

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
			if (c.toString().equals(colour)) {
				return c;
			}
		} 
		return null;
	}

}
