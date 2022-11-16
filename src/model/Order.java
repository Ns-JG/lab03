package model;

public class Order {
    public String id;
    public String menu_name;
    public String cook_id;
    public String seller_id;
    public String current_status;

     public enum status {
        ORDERED,
        PREPARING,
        READY,
        SOLD
    }
    public Order(String[] items) {
        this.id = items[0];
        this.menu_name = items[1];
        this.cook_id = items[2];
        this.seller_id = items[3];
        this.current_status = items[4];
    }

}
