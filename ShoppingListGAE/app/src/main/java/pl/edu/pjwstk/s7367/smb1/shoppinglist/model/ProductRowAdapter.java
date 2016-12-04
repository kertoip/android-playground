package pl.edu.pjwstk.s7367.smb1.shoppinglist.model;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.List;

import pl.edu.pjwstk.s7367.smb1.shoppinglist.OptionsActivity;
import pl.edu.pjwstk.s7367.smb1.shoppinglist.R;
import pl.edu.pjwstk.s7367.smb1.shoppinglist.data.DbAdapter;

/**
 * Created by kertoip on 2016-11-01.
 */

public class ProductRowAdapter extends CursorAdapter {

    private Context context;
    private DbAdapter dbAdapter;

    public ProductRowAdapter(Context context, Cursor c) {
        super(context, c, 0);
        this.context = context;
        this.dbAdapter = new DbAdapter(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.product_row, null);
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {

        TextView name = (TextView) view.findViewById(R.id.productName);
        name.setText(cursor.getString(DbAdapter.NAME_COLUMN_IDX));
        name.setTextSize(getFontSize(view.getContext()));

        view.setBackgroundColor(isInDarkMode(view.getContext()) ? Color.GRAY : Color.WHITE);
        name.setTextColor(isInDarkMode(view.getContext()) ? Color.WHITE : Color.BLACK);

        CheckBox isChecked = (CheckBox) view.findViewById(R.id.productChbox);
        isChecked.setChecked(cursor.getInt(DbAdapter.IS_CHECKED_COLUMN_IDX) > 0);
        isChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setIsCheckedByPosition(cursor.getPosition(), isChecked);
            }
        });
    }

    private int getFontSize(Context ctx) {
        System.out.println("test");
        return ctx.getSharedPreferences(OptionsActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE).getInt(OptionsActivity.SHARED_PREF_FONT_SIZE, OptionsActivity.DEFAULT_FONT_SIZE);
    }

    private boolean isInDarkMode(Context ctx) {
        return ctx.getSharedPreferences(OptionsActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE).getBoolean(OptionsActivity.SHARED_PREF_IS_DARK_MODE, false);
    }

    public void removeProduct(int position) {
        if (getCursor().moveToPosition(position)) {
            dbAdapter.deleteProduct(getCursor().getLong(DbAdapter.ID_COLUMN_IDX));
            swapCursor(dbAdapter.getAllProducts());
        }
    }

    public void addProduct(String productName) {
        dbAdapter.insertProduct(productName);
        swapCursor(dbAdapter.getAllProducts());
    }

    public void updateProduct(long id, String name) {
        dbAdapter.updateProduct(id, name, false);
        swapCursor(dbAdapter.getAllProducts());
    }

    public String getNameByPosition(int position) {
        Cursor cursor = (Cursor) getItem(position);
        return cursor.getString(DbAdapter.NAME_COLUMN_IDX);
    }

    private void setIsCheckedByPosition(int position, boolean isChecked) {
        dbAdapter.updateProduct(getItemId(position), getNameByPosition(position), isChecked);

    }
}
