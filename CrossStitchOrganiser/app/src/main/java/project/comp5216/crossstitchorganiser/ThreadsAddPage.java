package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class ThreadsAddPage extends Activity implements AdapterView.OnItemSelectedListener {
    private static final String APP_TAG = "Cross Stitch Organiser";
	private String dmc;
	private double amount;
	private Colour colour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads_add);
		setUpColourSpinner();
        Log.v(APP_TAG, "Loading threads add page");
    }

	public void onThreadsAddSubmitClick(View view) {
		EditText dmcET = findViewById(R.id.threadsAddTextDmc);
		dmc = dmcET.getText().toString();
		if (dmc.equals("")) {
			return;
		}
		EditText amountET = findViewById(R.id.threadsAddAmount);
		try {
			amount = Double.parseDouble(amountET.getText().toString());
		} catch (NumberFormatException e) {
			amount = 1.0;
		}
		// TODO: check if it already exists: if so just ADD the amount to it
		Thread thread = new Thread(dmc, colour, amount);
	}

    public void onThreadsAddBackClick(View view) {
        Log.v(APP_TAG, "Clicked back in threads add");
        finish();
    }

	private void setUpColourSpinner() {
		Spinner spinner = findViewById(R.id.threadsAddColourSpinner);
		ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
				Colour.makeList());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	}

	// Spinner methods needed for interface
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		colour = Colour.findColour((String) parent.getItemAtPosition(pos));
    }

    public void onNothingSelected(AdapterView<?> parent) {
		colour = null;
    }
}
