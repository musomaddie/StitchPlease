package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListPage extends Activity {

    private static final String APP_TAG = "Cross Stitch Organiser";

    private List<ShoppingListItem> items;
    private ListView listView;
    private ArrayAdapter<ShoppingListItem> shoppingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        Log.v(APP_TAG, "Loading shopping list page");

        // Get the intent with the information on what projects to include
        populateShoppingList(getIntent().getStringExtra("projectsIncluded"));

        // list all items

        // Setting up the list view
        listView = findViewById(R.id.shoppingListList);
        shoppingAdapter = new ShoppingListItemAdapter(this, items);
        listView.setAdapter(shoppingAdapter);


        // item listener time!!
        setUpShoppingItemListener();
    }

    public void onShoppingListBackClick(View view) {
        Log.v(APP_TAG, "clicked back from shopping list");
        finish();
    }

    private void setUpShoppingItemListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShoppingListItem updateItem = (ShoppingListItem) shoppingAdapter.getItem(position);
                Log.i(APP_TAG, "Clicked item " + position + ": " + updateItem.getThread());

                // TODO: display the updated change
                updateItem.mark();
            }
        });
    }

    private void populateShoppingList(String includedProjects) {
        // TODO: this is all dummy data, populate later
        items = new ArrayList<ShoppingListItem>();
        items.add(new ShoppingListItem("310", 2));
        items.add(new ShoppingListItem("550", 2));
    }
}
