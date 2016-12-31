package pl.edu.pjwstk.s7367.smb1.shoppinglist;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.common.base.Optional;

import java.util.List;
import java.util.concurrent.ExecutionException;

import pl.edu.pjwstk.s7367.smb1.shoppinglist.cloud.AddProductAsyncTask;
import pl.edu.pjwstk.s7367.smb1.shoppinglist.cloud.DeleteProductAsyncTask;
import pl.edu.pjwstk.s7367.smb1.shoppinglist.cloud.GetProductsAsyncTask;
import pl.edu.pjwstk.s7367.smb1.shoppinglist.cloud.ProductRowAdapterGAE;
import pl.edu.pjwstk.s7367.smb1.shoppinglist.cloud.UpdateProductAsyncTask;
import pl.edu.pjwstk.s7367.smb1.shoppinglist.data.DbAdapter;
import pl.edu.pjwstk.s7367.smb1.shoppinglist.model.Product;
import pl.edu.pjwstk.s7367.smb1.shoppinglist.model.ProductRowAdapter;
import pl.edy.pjwstk.s7367.smb3.shoppinglist.gae.productApi.ProductApi;

public class ProductListActivity extends AppCompatActivity implements EditDialog.ProductEditor {

    private ProductRowAdapter productAdapter;
    private ProductRowAdapterGAE productRowAdapterGAE;
    private ListView listView;

    private DbAdapter dbAdapter;
    private Cursor productCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EditDialog().show(getFragmentManager(), getLocalClassName());
            }
        });

        listView = (ListView) findViewById(R.id.productList);

        dbAdapter = new DbAdapter(this);
        productCursor = dbAdapter.getAllProducts();

        try {
            List<pl.edy.pjwstk.s7367.smb3.shoppinglist.gae.productApi.model.Product> gaeList = new GetProductsAsyncTask().execute().get(); //todo pk wywalic row adpater z async taska?
            productRowAdapterGAE = new ProductRowAdapterGAE(this, gaeList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        productAdapter = new ProductRowAdapter(this, productCursor);

//        listView.setAdapter(productAdapter);
        listView.setAdapter(productRowAdapterGAE);

        registerForContextMenu(listView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbAdapter.close();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(productRowAdapterGAE.getItem(getPositionFromMenuInfo(menuInfo)).getName());
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.product_context_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editProduct:
                getEditDialog(item.getMenuInfo()).show(getFragmentManager(), getLocalClassName());
                break;
            case R.id.deleteProduct:
                pl.edy.pjwstk.s7367.smb3.shoppinglist.gae.productApi.model.Product p = productRowAdapterGAE.getItem(getPositionFromMenuInfo(item.getMenuInfo()));
                new DeleteProductAsyncTask().execute(p);
                productRowAdapterGAE.remove(p);
                Toast.makeText(this, R.string.product_removed, Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return super.onContextItemSelected(item);
    }

    private int getPositionFromMenuInfo(ContextMenu.ContextMenuInfo menuInfo) {
        return ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
    }

    private EditDialog getEditDialog(ContextMenu.ContextMenuInfo menuInfo) {
        pl.edy.pjwstk.s7367.smb3.shoppinglist.gae.productApi.model.Product product
                = productRowAdapterGAE.getItem(getPositionFromMenuInfo(menuInfo));
        return EditDialog.newInstance(product.getId(), getPositionFromMenuInfo(menuInfo), product.getName());
    }


    @Override
    public void addProductToList(pl.edy.pjwstk.s7367.smb3.shoppinglist.gae.productApi.model.Product p) {
        try {
            Optional<pl.edy.pjwstk.s7367.smb3.shoppinglist.gae.productApi.model.Product> added = new AddProductAsyncTask().execute(p).get();
            if (added.isPresent()) {
                productRowAdapterGAE.add(added.get());
                Toast.makeText(this, "Product has been added!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Product could not be added!", Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateProduct(long id, String name) {
        productAdapter.updateProduct(id, name);
    }

    @Override
    public void updateProduct(int position, String newName) {
        pl.edy.pjwstk.s7367.smb3.shoppinglist.gae.productApi.model.Product p = productRowAdapterGAE.getItem(position);
        String oldName = p.getName();
        p.setName(newName);
        new UpdateProductAsyncTask().execute(p);

    }
}
