package project.comp5216.crossstitchorganiser;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ShoppingListItemAdapter extends ArrayAdapter {
    private static final String APP_TAG = "Cross Stitch Organiser";

    public ShoppingListItemAdapter(Context context, List<ShoppingListItem> items) {
        super(context, 0, items);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ShoppingListItem item = (ShoppingListItem) getItem(position);

        // Check if existing view is being resused otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item_shopping, parent, false);
        }
        // Lookup view for colour population
        TextView itemName = convertView.findViewById(R.id.listItemShopping);
        itemName.setText(item.toString());

        return convertView;
    }
}
