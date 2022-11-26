package Klient;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import model.File_reader;
import model.Order;
import javax.swing.*;

public class KlientAPP extends Frame{
    public static int ID = 1;
    private final Label picked = new Label("Wybrane: NIC", Label.CENTER);
    private final Button order_b = new Button("Zamów");
    private final JPanel right_panel = new JPanel();
    private Button choosen;
    public KlientAPP() {
        KlientAPP.ID++;
        File_reader.avaiable_acceses++;

        JPanel left_panel = new JPanel(); left_panel.setBackground(Color.BLACK); left_panel.setLayout(new BoxLayout(left_panel, BoxLayout.Y_AXIS));
        JPanel center_panel = new JPanel(); center_panel.setBackground(Color.BLACK); center_panel.setLayout(null);
        right_panel.setBackground(Color.BLACK); right_panel.setLayout(new BoxLayout(right_panel, BoxLayout.Y_AXIS));

        picked.setBounds(10, 100 , 180,50);
        picked.setFont(new Font("Verdana", Font.BOLD, 11));
        picked.setForeground(Color.WHITE); picked.setBackground(Color.BLACK);

        order_b.setBounds(10,150, 180,300);
        order_b.setFont(new Font("Verdana", Font.BOLD, 30));
        order_b.setEnabled(false);
        order_b.addActionListener(this.order_b_write());

        var menu_label = new Label("\t\tMENU", Label.CENTER);
        menu_label.setFont(new Font("Verdana", Font.BOLD, 30));
        menu_label.setForeground(Color.WHITE);

        center_panel.add(picked);
        center_panel.add(order_b);

        left_panel.add(menu_label);
        File_reader.menu_as_list.forEach(n -> { var b = new Button(n); b.setBackground(Color.BLACK); b.addActionListener(this.menu_b_action(b)); left_panel.add(b); } );
        add(left_panel);
        add(center_panel);
        add(right_panel);

        // params
        setTitle("Klient.KlientAPP");
        setLayout(new GridLayout(1,3));
        addMouseListener(new MouseListener() {

            @Override public void mouseClicked(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {
                try {
                    var ready_orders = File_reader.pobierz_zamowienia(Order.status.READY);
                    right_panel.removeAll();
                    var ready_orders_label = new Label("Zapłać za:", Label.CENTER);
                    ready_orders_label.setFont(new Font("Verdana", Font.BOLD, 30));
                    ready_orders_label.setForeground(Color.WHITE);
                    right_panel.add(ready_orders_label);
                    ready_orders.forEach(n -> {
                        var b = new Button(n.menu_name);
                        b.setName(n.id);
                        b.addActionListener(order_b_activate_pay(b));
                        b.setBackground(Color.BLACK);
                        right_panel.add(b);
                    } );
                } catch (IOException err) {err.printStackTrace();}
                SwingUtilities.updateComponentTreeUI(right_panel);
            }
            @Override public void mouseExited(MouseEvent e) {} });
        addWindowListener(new WindowAdapter() { public void windowClosing( WindowEvent e) { dispose(); System.exit(1); } });
        setSize(600,840);
        setVisible(true);
        setResizable(false);
    }
    public ActionListener menu_b_action(Button button) {
        return e ->  {
            this.order_b.setEnabled(true);
            this.picked.setText("Wybrane: "+button.getLabel());
            this.choosen = button;
    }; }
    public ActionListener order_b_write() { return e -> {
            var contents = this.picked.getText().split(" ");
            contents[0] = "";
            try { File_reader.dodaj_zamowienie(this.choosen.getLabel(), KlientAPP.ID); }
            catch (IOException err) {err.printStackTrace();}
    }; }
    public ActionListener order_b_activate_pay(Button button) { return  e-> {
        right_panel.remove(button);
        try { File_reader.zmien_stan_zamowienia_status(
                Integer.parseInt(button.getName()),
                Order.status.PAID,
                null,
                null,
                null,
                null); }
        catch (IOException err) { err.printStackTrace(); }

    }; }
    static public void main(String[] args) { new KlientAPP(); }
}