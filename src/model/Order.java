package model;

public class Order { // ID, menu_name, STATUS
    public String id;
    public String menu_name;
    public String cook_id;
    public String seller_id;
    public String current_status;
     static public enum status {
        ORDERED,
        PREPARING,
        READY,
        SOLD
    }
    public Order(String[] items) {
        this.id = items[0];
        this.menu_name = items[1];
        this.current_status = items[2];
        this.cook_id = null;//items[2];
        this.seller_id = null;//items[3];

    }
}
