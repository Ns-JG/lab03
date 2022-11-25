package Kucharz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Random;
import model.File_reader;
import model.Order;

public class KucharzAPP extends Frame{

    private final JPanel left_panel = new JPanel();
    private final JPanel right_panel = new JPanel();

//    private Label wybrany = new Label();
//    private Button przyrzad = new Button();
    public KucharzAPP() {

        //contents
        add(left_panel); add(right_panel);
        left_panel.setBackground(Color.BLACK); left_panel.setLayout(new BoxLayout(left_panel, BoxLayout.Y_AXIS));
        right_panel.setBackground(Color.BLACK); right_panel.setLayout(null);

        //params
        setTitle("Kucharz.KucharzAPP");
        setLayout(new GridLayout(1,2));
        setSize(600,840);
        setVisible(true);
        setResizable(false);
        addWindowListener(new WindowAdapter() { public void windowClosing( WindowEvent e) { dispose(); System.exit(1); } });
        addMouseListener(new MouseListener() {

            @Override public void mouseClicked(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {
                try {
                    var ready_orders = File_reader.pobierz_zamowienia(Order.status.ORDERED);
                    left_panel.removeAll();
                    var ready_orders_label = new Label("Zamówione:", Label.CENTER);
                    ready_orders_label.setFont(new Font("Verdana", Font.BOLD, 30));
                    ready_orders_label.setForeground(Color.WHITE);
                    left_panel.add(ready_orders_label);
                    ready_orders.forEach(n -> {
                        var b = new Button(n.menu_name);
                        b.setName(n.id);
                        b.addActionListener(order_b_activate_cook(b));
                        b.setBackground(Color.BLACK);
                        left_panel.add(b);
                    } );
                } catch (IOException err) {err.printStackTrace();}
                SwingUtilities.updateComponentTreeUI(right_panel);
            }
            @Override public void mouseExited(MouseEvent e) {} });
    }
    public ActionListener order_b_activate_cook(Button button) { return  e-> {
        // System.out.println("PAY ID:\t"+button.getName()+" name "+button.getLabel());
        right_panel.remove(button);
        try { File_reader.zmien_stan_zamowienia_status(Integer.parseInt(button.getName()), Order.status.READY); }
        catch (IOException err) { err.printStackTrace(); }

    }; }

    private int get_cooking_time() { var random = new Random(); return random.nextInt(5,15); } // seconds
    public static void main(String[] args) { new KucharzAPP(); }
}
