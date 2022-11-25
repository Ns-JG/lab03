package model;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.*;
import java.io.File;

public abstract class File_reader {
    public static int order_count = 0;
    public static ArrayList<String> menu_as_list;
    public static String menu_as_string;
    public static Boolean file_has_changed = false;
    private static Path menu_path = Paths.get(System.getProperty("user.dir"), "menu.txt");
    private static Path zam_path = Paths.get(System.getProperty("user.dir"), "zamowienia.csv");

    static {
        File menu = menu_path.toFile();
        try {
            Scanner scanner = new Scanner(menu);
            var menu_contents = new ArrayList<String>();
            while (scanner.hasNext()) { menu_contents.add(scanner.nextLine()); }
            menu_as_list = menu_contents;
            menu_as_string = String.join("\n", menu_contents);
            scanner.close();
        } catch (IOException e) { e.printStackTrace(); }
        try {
            File zamowienia = zam_path.toFile();
            zamowienia.delete();
            zamowienia.createNewFile();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static ArrayList<Order> parse_zamowienia() throws IOException { // will be updated with each action
        File zam = zam_path.toFile();
        Scanner scanner = new Scanner(zam);
        var parsed = new ArrayList<Order>();
        while (scanner.hasNext()) {
            var items = scanner.nextLine().split(",");
            parsed.add(new Order(items));
        }
        scanner.close();
        return parsed;
    }

    public static void dodaj_zamowienie(String menu_name) throws IOException { // schemat zamowienia: [Order.to_file_from() -> String : "Status, ]
        File zam = File_reader.zam_path.toFile();
        FileWriter file_writer = new FileWriter(zam, true);
        file_writer.write(String.join(",",Integer.toString(++order_count), menu_name, Order.status.ORDERED.toString(), "\n")); // rozszerzyc o cook i seller
        file_writer.close();
    }

    public static ArrayList<Order> pobierz_zamowienia(Order.status status) throws IOException {
        var zamowienia = parse_zamowienia();
        var zamowienia_filtr = new ArrayList<Order>();
        zamowienia.forEach(n -> { if (n.current_status.equals(status.toString())) { zamowienia_filtr.add(n); }});
        return zamowienia_filtr;
    }
    public static void zmien_stan_zamowienia_status(Integer ID, Order.status status) throws IOException {
        var zamowienia = new HashMap<Integer, ArrayList<String>>();
        for (Order order : File_reader.parse_zamowienia()) {
            zamowienia.put(Integer.parseInt(order.id), new ArrayList<String>(Arrays.asList(order.menu_name, order.current_status, order.cook_id, order.seller_id)));
        }
        zamowienia.get(ID).set(1, status.toString());
        File zam = File_reader.zam_path.toFile();
        FileWriter file_writer = new FileWriter(zam);
        zamowienia.keySet().forEach(n ->
        {
            try {
                System.out.println(String.join(",", Integer.toString(n), zamowienia.get(n).get(0), zamowienia.get(n).get(1)+ "\n"));
                file_writer.write(String.join(",", Integer.toString(n), zamowienia.get(n).get(0), zamowienia.get(n).get(1)+ "\n")); // rozszerzyc o cook i seller
            } catch (IOException e) { throw new RuntimeException(e); }
        });
        file_writer.close();
    }
    public static void zmien_stan_zamowienia_cook_id(String ID, String cook_id) throws IOException {
        var zamowienia = new HashMap<Integer, ArrayList<String>>();
        for (Order order : File_reader.parse_zamowienia()) {
            var id = Integer.parseInt(order.id);
            var cook_id_w = cook_id == null ? order.cook_id : cook_id;
            zamowienia.put(id, new ArrayList<>(Arrays.asList(order.menu_name, order.current_status, cook_id_w, order.seller_id)));
        }
    }
    public static void zmien_stan_zamowienia_seller_id(String ID, String seller_id) throws IOException {
        var zamowienia = new HashMap<Integer, ArrayList<String>>();
        for (Order order : File_reader.parse_zamowienia()) {
            var id = Integer.parseInt(order.id);
            var seller_id_w = seller_id == null ? order.seller_id : seller_id;
            zamowienia.put(id, new ArrayList<>(Arrays.asList(order.menu_name, order.current_status, order.cook_id, seller_id_w)));
        }
    }
    public static void zmien_stan_zamowienia(String ID, Order.status status, String cook_id, String seller_id) throws IOException {
        var zamowienia = new HashMap<Integer, ArrayList<String>>();
        for (Order order : File_reader.parse_zamowienia()) {
            var id = Integer.parseInt(order.id);
            var status_w = status == null ? order.current_status.toString() : status.toString();
            var cook_id_w = cook_id == null ? order.cook_id : cook_id;
            var seller_id_w = seller_id == null ? order.seller_id : seller_id;
            zamowienia.put(id, new ArrayList<>(Arrays.asList(order.menu_name, status_w, cook_id_w, seller_id_w)));
        }
    }
}
