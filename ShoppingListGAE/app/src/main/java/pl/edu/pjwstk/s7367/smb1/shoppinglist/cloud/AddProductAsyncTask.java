package pl.edu.pjwstk.s7367.smb1.shoppinglist.cloud;

import android.util.Log;

import com.google.common.base.Optional;

import java.io.IOException;

import pl.edy.pjwstk.s7367.smb3.shoppinglist.gae.productApi.model.Product;

public class AddProductAsyncTask extends BaseProductAsyncTask<Product, Optional<Product>> {

    @Override
    protected Optional<Product> doInBackground(Product... params) {
        try {
            Product p = getProductApiService().insert(params[0]).execute();
            return Optional.of(p);
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
            return Optional.absent();
        }
    }
}
