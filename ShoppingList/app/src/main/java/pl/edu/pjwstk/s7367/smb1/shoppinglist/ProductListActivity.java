package pl.edu.pjwstk.s7367.smb1.shoppinglist;

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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import pl.edu.pjwstk.s7367.smb1.shoppinglist.model.Product;
import pl.edu.pjwstk.s7367.smb1.shoppinglist.model.ProductRowAdapter;

public class ProductListActivity extends AppCompatActivity {

    private ProductRowAdapter productAdapter;
    private ArrayList<Product> productList;
    private ListView listView;

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
                productAdapter.add(new Product("Produkt", 1));
            }
        });

        listView = (ListView) findViewById(R.id.productList);

        productList = new ArrayList<>();
        productList.add(new Product("mleko", 1));
        productList.add(new Product("chleb", 2));

        productAdapter = new ProductRowAdapter(this, R.layout.product_row, productList);

        listView.setAdapter(productAdapter);

        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(productAdapter.getItem(((AdapterView.AdapterContextMenuInfo) menuInfo).position).getName());
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.product_context_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo infor = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.editProduct:
                System.out.println("Editing...");
                break;
            case R.id.deleteProduct:
                productList.remove(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
                productAdapter.notifyDataSetChanged();
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return super.onContextItemSelected(item);
    }
}
