package pl.edu.pjwstk.s7367.smb1.shoppinglist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import pl.edu.pjwstk.s7367.smb1.shoppinglist.model.Product;

/**
 * Created by kertoip on 2016-11-03.
 */

public class EditDialog extends DialogFragment {

    EditText productName;

    public interface ProductEditor {
        public void addProductToList(Product p);
    }

    ProductEditor productEditor;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        productName = (EditText) v.findViewById(R.id.productName);
        return v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.edit_dialog, null));

        builder.setMessage("Test dialog")
                .setPositiveButton("positiveBtn", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        productEditor.addProductToList(new Product(productName.getText().toString(), 0));
                    }
                })
                .setNegativeButton("NegativeBtn", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

}
