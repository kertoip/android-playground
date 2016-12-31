package pl.edu.pjwstk.s7367.smb1.shoppinglist.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import pl.edu.pjwstk.s7367.smb1.shoppinglist.model.Product;

import static java.lang.String.format;

/**
 * Created by pkolodziejczak on 05.11.16.
 */

public class DbAdapter {
    private static final String DEBUG_TAG = DbAdapter.class.toString();

    static final int DB_VERSION = 1;
    static final String DB_NAME = "ShoppingList.db";
    static final String DB_PRODUCT_TABLE = "Product";

    public static final String KEY_ID = "_id";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final int ID_COLUMN_IDX = 0;

    public static final String KEY_NAME = "name";
    public static final String NAME_OPTIONS = "TEXT NOT NULL";
    public static final int NAME_COLUMN_IDX = 1;

    public static final String KEY_IS_CHECKED = "is_checked";
    public static final String IS_CHECKED_OPTIONS = "INTEGER DEFAULT 0";
    public static final int IS_CHECKED_COLUMN_IDX = 2;

    public static final String DB_CREATE_PRODUCT_TABLE =
            format("CREATE TABLE %s (%s %s, %s %s, %s %s);",
                    DB_PRODUCT_TABLE,
                    KEY_ID, ID_OPTIONS,
                    KEY_NAME, NAME_OPTIONS,
                    KEY_IS_CHECKED, IS_CHECKED_OPTIONS);

    public static final String DB_DROP_PRODUCT_TABEL = format("DROP TABLE ID EXISTS %s", DB_PRODUCT_TABLE);

    private SQLiteDatabase db;
    private Context context;
    private DbHelper dbHelper;

    public DbAdapter(Context context) {
        this.context = context;
    }

    public DbAdapter open() {
        dbHelper = new DbHelper(context);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long insertProduct(String productName) {
        ContentValues newProductValues = new ContentValues();
        newProductValues.put(KEY_NAME, productName);

        open();
        return db.insert(DB_PRODUCT_TABLE, null, newProductValues);
    }

    public boolean updateProduct(long id, String name, boolean isCompleted) {
        ContentValues updateProductValues = new ContentValues();
        updateProductValues.put(KEY_NAME, name);
        updateProductValues.put(KEY_IS_CHECKED, isCompleted ? 1 : 0);

        open();
        return db.update(DB_PRODUCT_TABLE, updateProductValues, format("%s = %s", KEY_ID, id), null) > 0;
    }

    public boolean deleteProduct(long id) {
        open();
        return db.delete(DB_PRODUCT_TABLE, format("%s = %s", KEY_ID, id), null) > 0;
    }

    public Cursor getAllProducts() {
        open();
        String[] columns = {KEY_ID, KEY_NAME, KEY_IS_CHECKED};
        return db.query(DB_PRODUCT_TABLE, columns, null, null, null, null, null);
    }

    public Product getProductById(long id) {
        String[] columns = {KEY_ID, KEY_NAME, KEY_IS_CHECKED};
        Cursor cursor = db.query(DB_PRODUCT_TABLE, columns, format("%s = %s", KEY_ID, id), null, null, null, null);
        Product p = null;
        if (cursor != null && cursor.moveToFirst()) {
            p = new Product(id,cursor.getString(NAME_COLUMN_IDX), cursor.getInt(IS_CHECKED_COLUMN_IDX) > 0);
        }
        return p;
    }

}
