package pl.edu.pjwstk.s7367.smb1.shoppinglist.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import static java.lang.String.format;
import static pl.edu.pjwstk.s7367.smb1.shoppinglist.data.DbAdapter.DB_CREATE_PRODUCT_TABLE;
import static pl.edu.pjwstk.s7367.smb1.shoppinglist.data.DbAdapter.DB_PRODUCT_TABLE;

/**
 * Created by pkolodziejczak on 05.11.16.
 */

public class ShoppingListProvider extends ContentProvider {

    public static final String CONTENT_AUTHORITY = "pl.edu.pjwstk.s7367.smb1.poduct";
    private static final int MATCH_CODE_PRODUCT = 100;
    private static final String CONTENT_DIR_TYPE = format("%s/%s/%s", ContentResolver.CURSOR_DIR_BASE_TYPE, CONTENT_AUTHORITY, DB_CREATE_PRODUCT_TABLE);
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(DB_CREATE_PRODUCT_TABLE).build();

    DbHelper dbHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = CONTENT_AUTHORITY;
        matcher.addURI(authority, DB_PRODUCT_TABLE, MATCH_CODE_PRODUCT);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (sUriMatcher.match(uri)) {
            case MATCH_CODE_PRODUCT : {
                return dbHelper.getReadableDatabase().query(
                        DB_PRODUCT_TABLE,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

            }
            default: throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case MATCH_CODE_PRODUCT: return CONTENT_DIR_TYPE;
            default: throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        Uri returnUri;

        switch (sUriMatcher.match(uri)) {
            case MATCH_CODE_PRODUCT : {
                long id = db.insert(DB_PRODUCT_TABLE, null, values);
                returnUri = buildProductUri(id);
                break;
            }
            default: throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    private Uri buildProductUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int numDeleted = 0;
        switch(sUriMatcher.match(uri)) {
            case MATCH_CODE_PRODUCT : {
                numDeleted = db.delete(DB_PRODUCT_TABLE, selection, selectionArgs);
                break;
            }
            default: throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (numDeleted > 0) {
            Log.i("test", format("usunalem %s rekordów", numDeleted));
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int numUpdated = 0;
        switch(sUriMatcher.match(uri)) {
            case MATCH_CODE_PRODUCT : {
                numUpdated = db.update(DB_PRODUCT_TABLE, values, selection, selectionArgs);
                break;
            }
            default: throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (numUpdated > 0) {
            Log.i("test", format("uaktualnilem %s rekordów", numUpdated));
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numUpdated;
    }
}
