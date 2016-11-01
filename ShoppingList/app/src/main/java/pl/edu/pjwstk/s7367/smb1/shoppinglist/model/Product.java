package pl.edu.pjwstk.s7367.smb1.shoppinglist.model;

/**
 * Created by kertoip on 2016-11-01.
 */

public class Product {

    private final String name;
    private final int quantity;
    private boolean isChecked;

    public Product(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
        this.isChecked = false;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
