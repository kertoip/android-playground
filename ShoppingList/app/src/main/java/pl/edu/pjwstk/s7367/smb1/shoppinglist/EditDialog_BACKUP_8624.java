package pl.edu.pjwstk.s7367.smb1.shoppinglist;

<<<<<<< HEAD
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
=======
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
>>>>>>> cf163687d0f2c5fab0d6d6eb0824a2c034c45486
import android.widget.EditText;

import pl.edu.pjwstk.s7367.smb1.shoppinglist.model.Product;

/**
 * Created by kertoip on 2016-11-03.
 */

public class EditDialog extends DialogFragment {

<<<<<<< HEAD
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
=======
    private static final String DEBUG_TAG = EditDialog.class.toString();

    private static final String KEY_PRODUCT_ID = "KEY_PRODUCT_ID";
    private static final String KEY_PRODUCT_NAME = "KEY_PRODUCT_NAME";
    private static final Long NO_PRODUCT_ID = -1L;
    private static final String NO_PRODUCT_NAME = "";

    public interface ProductEditor {
        void addProductToList(Product p);
        void updateProduct(long id, String name);
    }

    public static EditDialog newInstance(final long productId, final String productName) {

        Bundle args = new Bundle();
        args.putLong(KEY_PRODUCT_ID, productId);
        args.putString(KEY_PRODUCT_NAME, productName);

        EditDialog fragment = new EditDialog();
        fragment.setArguments(args);
        return fragment;
>>>>>>> cf163687d0f2c5fab0d6d6eb0824a2c034c45486
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

<<<<<<< HEAD
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
=======
        return createDialog();
    }

    private Dialog createDialog() {
        final EditText productName = getProductNameTf();

        return new AlertDialog.Builder(getActivity())
                .setView(productName)
                .setTitle(R.string.edit_dialog_title)
                .setPositiveButton(R.string.edit_dialog_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (getActivity() instanceof ProductEditor) {
                            if (isEditMode()) {
                                ((ProductEditor) getActivity()).updateProduct(getProductIdFromArgs(), productName.getText().toString());
                                Log.d(DEBUG_TAG, "Product has been edited!");
                            } else {
                                ((ProductEditor) getActivity()).addProductToList(new Product(productName.getText().toString()));
                                Log.d(DEBUG_TAG, "New product added");
                            }
                        } else {
                            Log.w(DEBUG_TAG, "Not a ProductEditor instance...");
                        }
                    }
                })
                .setNegativeButton(R.string.edit_dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(DEBUG_TAG, "Canceling edit dialog");
                    }
                })
                .create();
    }

    @NonNull
    private EditText getProductNameTf() {
        final EditText productName = new EditText(getActivity());
        productName.setHint(R.string.edit_dialog_hint);
        productName.setMaxLines(1);
        if (isEditMode()) {
            String text = getArguments().getString(KEY_PRODUCT_NAME, NO_PRODUCT_NAME);
            productName.setText(text);
        }
        return productName;
    }

    private boolean isEditMode() {
        return getArguments() != null && !getArguments().isEmpty();
    }

    private long getProductIdFromArgs() {
        return getArguments().getLong(KEY_PRODUCT_ID);
>>>>>>> cf163687d0f2c5fab0d6d6eb0824a2c034c45486
    }

}
