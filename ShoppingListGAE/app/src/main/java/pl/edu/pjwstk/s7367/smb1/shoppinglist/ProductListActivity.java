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

import java.util.List;
import java.util.concurrent.ExecutionException;

import pl.edu.pjwstk.s7367.smb1.shoppinglist.cloud.GetProductsAsyncTask;
import pl.edu.pjwstk.s7367.smb1.shoppinglist.data.DbAdapter;
import pl.edu.pjwstk.s7367.smb1.shoppinglist.model.Product;
import pl.edu.pjwstk.s7367.smb1.shoppinglist.model.ProductRowAdapter;

public class ProductListActivity extends AppCompatActivity implements EditDialog.ProductEditor {

    private ProductRowAdapter productAdapter;
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
            List<pl.edy.pjwstk.s7367.smb3.shoppinglist.gae.productApi.model.Product> gaeList = new GetProductsAsyncTask().execute().get();
            Toast.makeText(this, gaeList.size(), Toast.LENGTH_LONG ).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        productAdapter = new ProductRowAdapter(this, productCursor);

        listView.setAdapter(productAdapter);

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
        menu.setHeaderTitle(productAdapter.getNameByPosition(getPositionFromMenuInfo(menuInfo)));
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
                productAdapter.removeProduct(getPositionFromMenuInfo(item.getMenuInfo()));
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
        int position = getPositionFromMenuInfo(menuInfo);
        return EditDialog.newInstance(productAdapter.getItemId(position), productAdapter.getNameByPosition(position));
    }


    @Override
    public void addProductToList(Product p) {
        productAdapter.addProduct(p.getName());
    }

    @Override
    public void updateProduct(long id, String name) {
        productAdapter.updateProduct(id, name);
    }
}
