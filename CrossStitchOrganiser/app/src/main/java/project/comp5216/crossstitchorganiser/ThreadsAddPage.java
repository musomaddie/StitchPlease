package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class ThreadsAddPage extends Activity implements AdapterView.OnItemSelectedListener {
    private static final String APP_TAG = "Cross Stitch Organiser";
	private String dmc;
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
		Log.d(APP_TAG, "User entered: " + dmc);
		Log.d(APP_TAG, "Colour was: " + colour);
	}

    public void onThreadsAddBackClick(View view) {
        Log.v(APP_TAG, "Clicked back in threads add");
        finish();
    }

	private void setUpColourSpinner() {
		Spinner spinner = (Spinner) findViewById(R.id.threadsAddColourSpinner);
		List<String> colourList = new ArrayList<String>();
		Colour [] colours = Colour.values();
		for (Colour c : colours) {
			colourList.add(c.toString());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
				colours);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	}

	// Spinner methods needed for interface
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		colour = (Colour) parent.getItemAtPosition(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
		colour = null;
    }
}
