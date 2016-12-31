package pl.edu.pjwstk.s7367.smb1.shoppinglist.cloud;

import android.util.Log;

import java.io.IOException;

import pl.edy.pjwstk.s7367.smb3.shoppinglist.gae.productApi.model.Product;

import static java.lang.Boolean.*;
import static java.lang.Boolean.FALSE;

public class DeleteProductAsyncTask extends BaseProductAsyncTask<Product, Boolean> {
    @Override
    protected Boolean doInBackground(Product... params) {
        try {
            getProductApiService().remove(params[0].getId()).execute();
            return TRUE;
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
            return FALSE;
        }
    }
}
