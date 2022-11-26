package model;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.*;
import java.io.File;

public abstract class File_reader {
    public static int avaiable_acceses = 0;
    public static int order_count = 0;
    public static ArrayList<String> menu_as_list;
    public static String menu_as_string;
    public static HashMap<Integer, ArrayList<String>> context_as_linker;
    private static final Path menu_path = Paths.get(System.getProperty("user.dir"), "menu.txt");
    private static final Path context_path = Paths.get(System.getProperty("user.dir"), "cena_czas_wykonania.txt");
    private static final Path zam_path = Paths.get(System.getProperty("user.dir"), "zamowienia.csv");
    static {
        try {
            Scanner menu_file = new Scanner(menu_path.toFile());
            var menu_contents = new ArrayList<String>();
            while (menu_file.hasNext()) { menu_contents.add(menu_file.nextLine()); }
            menu_as_list = menu_contents;
            menu_as_string = String.join("\n", menu_contents);
            menu_file.close();
            Scanner context_file = new Scanner(context_path.toFile());
            var context_contents = new HashMap<Integer, ArrayList<String>>(); int counter = 0;
            while (context_file.hasNext()) { context_contents.put(counter++, new ArrayList<>(Arrays.asList(context_file.nextLine().split(",")))); }
            context_as_linker = context_contents;
            context_file.close();
        } catch (IOException e) { e.printStackTrace(); }
        try {
            File zamowienia = zam_path.toFile();
            zamowienia.delete();
            zamowienia.createNewFile();
        } catch (IOException e) { e.printStackTrace(); }
    }
    public static ArrayList<Order> parse_zamowienia() throws IOException {
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
    public static void dodaj_zamowienie(String menu_name, Integer ID) throws IOException { // schemat zamowienia: [Order.to_file_from() -> String : "Status, ]
        File zam = File_reader.zam_path.toFile();
        FileWriter file_writer = new FileWriter(zam, true);
        file_writer.write(String.join(",",Integer.toString(++order_count), menu_name, Order.status.ORDERED.toString(), Integer.toString(ID), "-", "-", "-"+"\n")); // rozszerzyc o cook i seller
        file_writer.close();
    }
    public static ArrayList<Order> pobierz_zamowienia(Order.status status) throws IOException {
        var zamowienia = parse_zamowienia();
        var zamowienia_filtr = new ArrayList<Order>();
        zamowienia.forEach(n -> { if (n.current_status.equals(status.toString())) { zamowienia_filtr.add(n); }});
        return zamowienia_filtr;
    }
    public static void zmien_stan_zamowienia_status(Integer ID, Order.status status, String client_id, String cook_id, String price, String payment_method) throws IOException {
        var zamowienia = new HashMap<Integer, ArrayList<String>>();
        for (Order order : File_reader.parse_zamowienia()) {
            zamowienia.put(Integer.parseInt(order.id), new ArrayList<>(
                    Arrays.asList(order.menu_name, order.current_status, order.client_id, order.cook_id, order.price, order.payment_methd)));
        }
        zamowienia.get(ID).set(1, status.toString());
        if ( client_id == null ) { zamowienia.get(ID).set(2, zamowienia.get(ID).get(2)); } else { zamowienia.get(ID).set(2, client_id); }
        if ( cook_id == null ) { zamowienia.get(ID).set(3, zamowienia.get(ID).get(3)); } else { zamowienia.get(ID).set(3, cook_id); }
        if ( price == null ) { zamowienia.get(ID).set(4, zamowienia.get(ID).get(4)); } else { zamowienia.get(ID).set(4, price); }
        if ( payment_method== null ) { zamowienia.get(ID).set(5, zamowienia.get(ID).get(5)); } else { zamowienia.get(ID).set(5, payment_method); }
        File zam = File_reader.zam_path.toFile();
        FileWriter file_writer = new FileWriter(zam);
        zamowienia.keySet().forEach(n ->
        {
            try {
                file_writer.write(String.join(",",
                        Integer.toString(n),
                        zamowienia.get(n).get(0),
                        zamowienia.get(n).get(1),
                        zamowienia.get(n).get(2),
                        zamowienia.get(n).get(3),
                        zamowienia.get(n).get(4),
                        zamowienia.get(n).get(5)+"\n"
                ));
            } catch (IOException e) { throw new RuntimeException(e); }
        });
        file_writer.close();
    }
}