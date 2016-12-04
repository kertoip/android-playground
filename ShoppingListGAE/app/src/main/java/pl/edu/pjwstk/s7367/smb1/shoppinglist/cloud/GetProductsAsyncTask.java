package pl.edu.pjwstk.s7367.smb1.shoppinglist.cloud;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import pl.edy.pjwstk.s7367.smb3.shoppinglist.gae.productApi.ProductApi;
import pl.edy.pjwstk.s7367.smb3.shoppinglist.gae.productApi.model.Product;

/**
 * Created by kertoip on 2016-12-04.
 */

public class GetProductsAsyncTask extends AsyncTask<Void, Void, List<Product>> {

    private static ProductApi productApiService = null;
    private Context context;



    @Override
    protected List<Product> doInBackground(Void... params) {
        if (productApiService == null) {
            productApiService = new ProductApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setApplicationName("smb3-pjwstk")
                    .setRootUrl("https://smb3-pjwstk.appspot.com/_ah/api/") //todo PK zmiana na local!
                    .build();
        }
        try {
            return productApiService.list().execute().getItems();
        } catch (IOException e) {
            Log.w("Piotrek", "Piotrekkk - " + e.getStackTrace().toString());
            Log.w("Piotrek", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
