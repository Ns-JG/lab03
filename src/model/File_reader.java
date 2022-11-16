package model;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.io.File;

public abstract class File_reader {
    public static ArrayList<String> menu_as_list;
    public static String menu_as_string;

    static {
        var menu_path = Paths.get(System.getProperty("user.dir"), "menu.txt");
        File menu = menu_path.toFile();
        try {
            Scanner scanner = new Scanner(menu);
            var menu_contents = new ArrayList<String>();
            while (scanner.hasNext()) { menu_contents.add(scanner.nextLine()); }
            menu_as_list = menu_contents;
            menu_as_string = String.join("\n", menu_contents);
            scanner.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static ArrayList<Order> parse_zamowienia() throws IOException { // will be updated with each action
        var zam_path = Paths.get(System.getProperty("user.dir"), "zamowienia.csv");
        File zam = zam_path.toFile();
        Scanner scanner = new Scanner(zam);
        var parsed = new ArrayList<Order>();
        while (scanner.hasNext()) {
            var items = scanner.nextLine().replace(" ", "").split(",");
            parsed.add(new Order(items));
        }
        scanner.close();
        return parsed;
    }
}
