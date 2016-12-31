package pl.edu.pjwstk.s7367.smb2.sender;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;
import static java.lang.String.format;

public class SendActivity extends AppCompatActivity {

    public static final String ACTION_NAME = "pl.edu.pjwstk.s7367.smb2.CUSTOM_ACTION";
    public static final String MESSAGE_KEY = "message";
    EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        message = (EditText) findViewById(R.id.inputMessage);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(ACTION_NAME);
                intent.putExtra(MESSAGE_KEY, message.getText().toString());
                sendBroadcast(intent);

                Toast.makeText(getApplicationContext(), format("Intent with text %s has been broadcasted", message.getText()), LENGTH_SHORT ).show();
            }
        });
    }
}
