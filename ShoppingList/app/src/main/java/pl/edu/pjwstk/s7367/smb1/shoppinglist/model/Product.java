package pl.edu.pjwstk.s7367.smb1.shoppinglist.model;

/**
 * Created by kertoip on 2016-11-01.
 */

public class Product {

    private long id;
    private String name;
    private boolean isChecked;

    public Product(String name) {
        this.name = name;
        this.isChecked = false;
    }

    public Product(long id, String name, boolean isChecked) {
        this.id = id;
        this.name = name;
        this.isChecked = isChecked;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
