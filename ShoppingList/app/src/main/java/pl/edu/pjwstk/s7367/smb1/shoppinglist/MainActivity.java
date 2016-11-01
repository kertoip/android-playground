package pl.edu.pjwstk.s7367.smb1.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.view.View;
import android.widget.Button;

import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity {

    private Button productListBtn;

    private Button optionsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        productListBtn = (Button) findViewById(R.id.productListBtn);
        productListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toProductList();
            }
        });

        optionsBtn = (Button) findViewById(R.id.optionsBtn);
        optionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toOptionsActivity();
            }
        });


    }

    private void toOptionsActivity() {
        startActivity(new Intent(this, OptionsActivity.class));
    }

    private void toProductList() {
        startActivity(new Intent(this, ProductListActivity.class));
    }
}
