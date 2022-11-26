package Sprzedawca;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import model.Order;
import model.File_reader;

public class SprzedawcaAPP extends Frame {
    private final JPanel left_panel = new JPanel();
    private final Label danie = new Label("NONE", Label.CENTER);
    private final Button wydaj = new Button("Wydaj");
    private Button target;
    private final Label rabat = new Label("RABAT", Label.CENTER);
    public SprzedawcaAPP() {
        add(left_panel);
        JPanel right_panel = new JPanel();
        add(right_panel);
        left_panel.setBackground(Color.BLACK); left_panel.setLayout(new BoxLayout(left_panel, BoxLayout.Y_AXIS));
        right_panel.setBackground(Color.BLACK); right_panel.setLayout(null);

        Label wybrany = new Label("Wybrane Danie:", Label.CENTER);
        wybrany.setBounds(5, 0 , 295,50);
        wybrany.setFont(new Font("Verdana", Font.BOLD, 14));
        wybrany.setForeground(Color.WHITE); wybrany.setBackground(Color.BLACK);
        right_panel.add(wybrany);

        danie.setBounds(5, 55 , 295,50);
        danie.setFont(new Font("Verdana", Font.BOLD, 14));
        danie.setForeground(Color.WHITE); danie.setBackground(Color.BLACK);
        right_panel.add(danie);

        rabat.setBounds(5, 180 , 295,50);
        rabat.setFont(new Font("Verdana", Font.BOLD, 22));
        rabat.setForeground(Color.RED); rabat.setBackground(Color.BLACK);
        right_panel.add(rabat);

        wydaj.setBounds(5,250, 295,300);
        wydaj.setFont(new Font("Verdana", Font.BOLD, 30));
        wydaj.setEnabled(false);
        wydaj.addActionListener(sell_b());
        right_panel.add(wydaj);

        setTitle("SprzedawcaAPP");
        setLayout(new GridLayout(1,2));
        setSize(600,800);
        setVisible(true);
        setResizable(false);
        addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent e) { dispose(); System.exit(1); } });
        addMouseListener(new MouseListener() {
            @Override public void mouseClicked(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {
                try {
                    left_panel.removeAll();
                    var ready_orders_label = new Label("Przygotowane:", Label.CENTER);
                    ready_orders_label.setFont(new Font("Verdana", Font.BOLD, 30));
                    ready_orders_label.setForeground(Color.WHITE);
                    left_panel.add(ready_orders_label);

                    var ready_orders = File_reader.pobierz_zamowienia(Order.status.PAID);
                    ready_orders.forEach(n -> {
                        var b = new Button(n.menu_name);
                        b.setName(n.id);
                        b.addActionListener(order_b_activate_sell(b));
                        b.setBackground(Color.BLACK);
                        left_panel.add(b);
                    } );
                } catch (IOException err) {err.printStackTrace();}
                SwingUtilities.updateComponentTreeUI(left_panel);
            }
            @Override public void mouseExited(MouseEvent e) {} });
    }
    public ActionListener order_b_activate_sell(Button button) { return e-> {
        this.danie.setText(button.getLabel());
        target = button;
        wydaj.setEnabled(true);
    }; }
    public ActionListener sell_b() { return  e-> {
        try { File_reader.zmien_stan_zamowienia_status(
                Integer.parseInt(target.getName()),
                Order.status.GIVEN,
                null,
                null,
                File_reader.context_as_linker.get(Integer.parseInt(target.getName())).get(0),
                File_reader.context_as_linker.get(Integer.parseInt(target.getName())).get(1)); }
        catch (IOException err) { err.printStackTrace(); }
        //DOTO rabat ?
    }; }
    public static void main(String[] args) { new SprzedawcaAPP(); }
}