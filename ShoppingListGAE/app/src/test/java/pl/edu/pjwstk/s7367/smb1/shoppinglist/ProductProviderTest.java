package pl.edu.pjwstk.s7367.smb1.shoppinglist;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;
import android.util.Log;

import pl.edu.pjwstk.s7367.smb1.shoppinglist.data.DbAdapter;
import pl.edu.pjwstk.s7367.smb1.shoppinglist.data.ShoppingListProvider;

/**
 * Created by pkolodziejczak on 05.11.16.
 */

public class ProductProviderTest extends ProviderTestCase2 {

    private static final String TAG = ProductProviderTest.class.getSimpleName();

    MockContentResolver mockContentResolver;

    public ProductProviderTest() {
        super(ProductProviderTest.class, ShoppingListProvider.CONTENT_AUTHORITY);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Log.d(TAG, "setUp: ");
        mockContentResolver = getMockContentResolver();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        Log.d(TAG, "tearDown:");
    }

    public void testInsertvalidRecord() {
        Uri uri = mockContentResolver.insert(ShoppingListProvider.CONTENT_URI, getContentValues());
        assertEquals(1L, ContentUris.parseId(uri));
    }

    public ContentValues getContentValues() {
        ContentValues v = new ContentValues(7);
        v.put(DbAdapter.KEY_ID, 1);
        v.put(DbAdapter.KEY_NAME, "mleko");
        v.put(DbAdapter.KEY_IS_CHECKED, 1);
        return v;
    }
}
