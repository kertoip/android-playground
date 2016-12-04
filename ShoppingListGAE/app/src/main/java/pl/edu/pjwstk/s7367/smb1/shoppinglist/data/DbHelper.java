package pl.edu.pjwstk.s7367.smb1.shoppinglist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static java.lang.String.format;
import static pl.edu.pjwstk.s7367.smb1.shoppinglist.data.DbAdapter.DB_CREATE_PRODUCT_TABLE;
import static pl.edu.pjwstk.s7367.smb1.shoppinglist.data.DbAdapter.DB_DROP_PRODUCT_TABEL;
import static pl.edu.pjwstk.s7367.smb1.shoppinglist.data.DbAdapter.DB_NAME;
import static pl.edu.pjwstk.s7367.smb1.shoppinglist.data.DbAdapter.DB_PRODUCT_TABLE;
import static pl.edu.pjwstk.s7367.smb1.shoppinglist.data.DbAdapter.DB_VERSION;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DEBUG_TAG = DbAdapter.class.toString();

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(DEBUG_TAG, format("Creating table %s ver. %s", DB_PRODUCT_TABLE, DB_VERSION));
        db.execSQL(DB_CREATE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(DEBUG_TAG, format("Updating table %s from ver. %s to ver. %s", DB_PRODUCT_TABLE, oldVersion, newVersion));

        db.execSQL(DB_DROP_PRODUCT_TABEL);

        Log.d(DEBUG_TAG, "All previous data is deleted!");

        onCreate(db);
    }
}