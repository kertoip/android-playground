package pl.edu.pjwstk.s7367.smb1.shoppinglist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;

import pl.edu.pjwstk.s7367.smb1.shoppinglist.model.Product;

/**
 * Created by kertoip on 2016-11-03.
 */

public class EditDialog extends DialogFragment {

    private static final String DEBUG_TAG = EditDialog.class.toString();

    private static final String KEY_PRODUCT_ID = "KEY_PRODUCT_ID";
    private static final String KEY_PRODUCT_POSITION = "KEY_PRODUCT_POSITION";
    private static final String KEY_PRODUCT_NAME = "KEY_PRODUCT_NAME";
    private static final Long NO_PRODUCT_ID = -1L;
    private static final String NO_PRODUCT_NAME = "";

    public interface ProductEditor {
        void addProductToList(pl.edy.pjwstk.s7367.smb3.shoppinglist.gae.productApi.model.Product p);
        void updateProduct(long id, String name);
        void updateProduct(int position, String newName);
    }

    public static EditDialog newInstance(final long productId, final int position, final String productName) {

        Bundle args = new Bundle();
        args.putLong(KEY_PRODUCT_ID, productId);
        args.putString(KEY_PRODUCT_NAME, productName);
        args.putInt(KEY_PRODUCT_POSITION, position);

        EditDialog fragment = new EditDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

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
                                ((ProductEditor) getActivity()).updateProduct(getProductPosition(), productName.getText().toString());
//                                ((ProductEditor) getActivity()).updateProduct(getProductIdFromArgs(), productName.getText().toString());
                                Log.d(DEBUG_TAG, "Product has been edited!");
                            } else {
                                pl.edy.pjwstk.s7367.smb3.shoppinglist.gae.productApi.model.Product p = new pl.edy.pjwstk.s7367.smb3.shoppinglist.gae.productApi.model.Product();
                                p.setName(productName.getText().toString());
                                p.setChecked(Boolean.FALSE);
                                ((ProductEditor) getActivity()).addProductToList(p);
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
    }

    private int getProductPosition() {
        return getArguments().getInt(KEY_PRODUCT_POSITION);
    }

}
