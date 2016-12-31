package pl.edu.pjwstk.s7367.smb1.shoppinglist.cloud;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import pl.edy.pjwstk.s7367.smb3.shoppinglist.gae.productApi.ProductApi;

public abstract class BaseProductAsyncTask<P, R> extends AsyncTask<P, Void, R> {

    protected String TAG = getClass().getSimpleName();
    private static final String APP_NAME = "smb3-pjwstk";
    private static final String ROOT_URL = "https://smb3-pjwstk.appspot.com/_ah/api/";

    private static ProductApi productApiService = null;

    protected ProductApi getProductApiService() {
        if (productApiService == null) {
            productApiService = new ProductApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setApplicationName(APP_NAME)
                    .setRootUrl(ROOT_URL)
                    .build();
        }
        return productApiService;
    }
}
