package model;

public class Order { // ID, menu_name, STATUS, klientID, COOKID, price, payment_method
    public String id;
    public String menu_name;
    public String cook_id;
    public String client_id;
    public String current_status;
    public String price;
    public String payment_methd;
     public enum status {
        ORDERED,
        READY,
        PAID,
        GIVEN
    }
    public Order(String[] items) {
        this.id = items[0];
        this.menu_name = items[1];
        this.current_status = items[2];
        this.client_id = items[3];
        this.cook_id = items[4];
        this.price = items[5];
        this.payment_methd = items[6];
    }
}