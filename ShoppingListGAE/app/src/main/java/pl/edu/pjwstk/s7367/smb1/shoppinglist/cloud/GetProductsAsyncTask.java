package pl.edu.pjwstk.s7367.smb1.shoppinglist.cloud;

import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import pl.edy.pjwstk.s7367.smb3.shoppinglist.gae.productApi.model.Product;

public class GetProductsAsyncTask extends BaseProductAsyncTask<Void, List<Product>> {
    @Override
    protected List<Product> doInBackground(Void... params) {
        try {
            return getProductApiService().list().execute().getItems();
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
            return Collections.emptyList();
        }
    }
}
