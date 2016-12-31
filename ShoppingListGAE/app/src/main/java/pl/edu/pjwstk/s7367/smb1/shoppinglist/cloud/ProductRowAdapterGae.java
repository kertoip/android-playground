package pl.edu.pjwstk.s7367.smb1.shoppinglist.cloud;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import pl.edu.pjwstk.s7367.smb1.shoppinglist.OptionsActivity;
import pl.edu.pjwstk.s7367.smb1.shoppinglist.R;
import pl.edy.pjwstk.s7367.smb3.shoppinglist.gae.productApi.model.Product;

public class ProductRowAdapterGAE extends ArrayAdapter<Product> {

    CheckBox isChecked;


    public ProductRowAdapterGAE(Context context, List<Product> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.product_row, null);

        TextView name = (TextView) view.findViewById(R.id.productName);
        name.setText(getItem(position).getName());
        name.setTextSize(getFontSize(view.getContext()));

        view.setBackgroundColor(isInDarkMode(view.getContext()) ? Color.GRAY : Color.WHITE);
        name.setTextColor(isInDarkMode(view.getContext()) ? Color.WHITE : Color.BLACK);

        isChecked = (CheckBox) view.findViewById(R.id.productChbox);
        isChecked.setChecked(getItem(position).getChecked());
        isChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setIsCheckedByPosition(position, isChecked);
            }
        });

        return view;
    }

    private int getFontSize(Context ctx) {
        System.out.println("test");
        return ctx.getSharedPreferences(OptionsActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE).getInt(OptionsActivity.SHARED_PREF_FONT_SIZE, OptionsActivity.DEFAULT_FONT_SIZE);
    }

    private boolean isInDarkMode(Context ctx) {
        return ctx.getSharedPreferences(OptionsActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE).getBoolean(OptionsActivity.SHARED_PREF_IS_DARK_MODE, false);
    }
    private void setIsCheckedByPosition(int position, boolean isChecked) {
        Product p = getItem(position);
        p.setChecked(isChecked);
        new UpdateProductAsyncTask().execute(p);
        this.isChecked.setChecked(isChecked);

    }
}
