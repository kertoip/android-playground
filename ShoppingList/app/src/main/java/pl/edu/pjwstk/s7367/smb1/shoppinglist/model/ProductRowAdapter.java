package pl.edu.pjwstk.s7367.smb1.shoppinglist.model;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import pl.edu.pjwstk.s7367.smb1.shoppinglist.OptionsActivity;
import pl.edu.pjwstk.s7367.smb1.shoppinglist.R;

/**
 * Created by kertoip on 2016-11-01.
 */

public class ProductRowAdapter extends ArrayAdapter<Product> {

    Context context;
    private static LayoutInflater inflater = null;

    public ProductRowAdapter(Context context, int resource) {
        super(context, resource);
        inflater = LayoutInflater.from(context); // TODO: 2016-11-01 czy to jest potrzebne?
    }

    public ProductRowAdapter(Context context, int resource, List<Product> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.product_row, null);
            view.setLongClickable(true);
        }

        TextView name = (TextView) view.findViewById(R.id.productName);
        name.setText(getItem(i).getName());
        name.setTextSize(getFontSize(view.getContext()));

        view.setBackgroundColor(isInDarkMode(view.getContext()) ? Color.GRAY : Color.WHITE);
        name.setTextColor(isInDarkMode(view.getContext()) ? Color.WHITE : Color.BLACK);

        CheckBox isChecked = (CheckBox) view.findViewById(R.id.productChbox);
        isChecked.setChecked(getItem(i).isChecked());

        return view;
    }

    private int getFontSize(Context ctx) {
        System.out.println("test");
        return ctx.getSharedPreferences(OptionsActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE).getInt(OptionsActivity.SHARED_PREF_FONT_SIZE, OptionsActivity.DEFAULT_FONT_SIZE);
    }

    private boolean isInDarkMode(Context ctx) {
        return ctx.getSharedPreferences(OptionsActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE).getBoolean(OptionsActivity.SHARED_PREF_IS_DARK_MODE, false);
    }



}
