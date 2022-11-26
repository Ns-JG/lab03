package Kucharz;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Random;
import model.File_reader;
import model.Order;

public class KucharzAPP extends Frame {
    public final int ID = File_reader.avaiable_acceses--;
    private final JPanel left_panel = new JPanel();
    private final Label danie = new Label("NONE", Label.CENTER);
    private final Label status_przyrzadzania = new Label("---", Label.CENTER);
    private final Button przyrzadz = new Button("Przyrządź");
    private Button target;
    public KucharzAPP() throws InterruptedException {
        JPanel right_panel = new JPanel();
        add(left_panel);
        add(right_panel);
        left_panel.setBackground(Color.BLACK); left_panel.setLayout(new BoxLayout(left_panel, BoxLayout.Y_AXIS));
        right_panel.setBackground(Color.BLACK); right_panel.setLayout(null);

        Label wybrany = new Label("Wybrane Danie:", Label.CENTER);
        wybrany.setBounds(5, 0 , 295,50);
        wybrany.setFont(new Font("Verdana", Font.BOLD, 14));
        wybrany.setForeground(Color.WHITE); wybrany.setBackground(Color.BLACK);
        right_panel.add(wybrany);

        status_przyrzadzania.setBounds(5, 500 , 295,50);
        status_przyrzadzania.setFont(new Font("Verdana", Font.BOLD, 15));
        status_przyrzadzania.setForeground(Color.WHITE); status_przyrzadzania.setBackground(Color.BLACK);
        right_panel.add(status_przyrzadzania);

        danie.setBounds(5, 55 , 295,50);
        danie.setFont(new Font("Verdana", Font.BOLD, 14));
        danie.setForeground(Color.WHITE); danie.setBackground(Color.BLACK);
        right_panel.add(danie);

        przyrzadz.setBounds(5,150, 295,300);
        przyrzadz.setFont(new Font("Verdana", Font.BOLD, 30));
        przyrzadz.setEnabled(false);
        przyrzadz.addActionListener(cook_b());
        right_panel.add(przyrzadz);

        setTitle("Kucharz.KucharzAPP");
        setLayout(new GridLayout(1,2));
        setSize(600,800);
        setVisible(true);
        setResizable(false);
        addWindowListener(new WindowAdapter() { public void windowClosing( WindowEvent e) { dispose(); System.exit(1); } });
        addMouseListener(new MouseListener() {
            @Override public void mouseClicked(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {
                try {
                    left_panel.removeAll();
                    var ready_orders_label = new Label("Zamówione:", Label.CENTER);
                    ready_orders_label.setFont(new Font("Verdana", Font.BOLD, 30));
                    ready_orders_label.setForeground(Color.WHITE);
                    left_panel.add(ready_orders_label);

                    var ready_orders = File_reader.pobierz_zamowienia(Order.status.ORDERED);
                    ready_orders.forEach(n -> {
                        var b = new Button(n.menu_name);
                        b.setName(n.id);
                        b.addActionListener(order_b_activate_cook(b));
                        b.setBackground(Color.BLACK);
                        left_panel.add(b);
                    } );
                } catch (IOException err) {err.printStackTrace();}
                SwingUtilities.updateComponentTreeUI(left_panel);
            }
            @Override public void mouseExited(MouseEvent e) {} });
    }
    public ActionListener order_b_activate_cook(Button button) { return  e-> {
        status_przyrzadzania.setText("Do przygotowania : "+button.getLabel());
        this.danie.setText(button.getLabel());
        target = button;
        przyrzadz.setEnabled(true);
    }; }
    public ActionListener cook_b() { return  e-> {
        int progres = 0;
        int speed = get_cooking_time();
        while ( progres++ < 100 ) {
            System.out.flush();
            System.out.println("gotowanie <"+target.getLabel()+"> "+progres+"%");
            try { Thread.sleep(speed); } catch (InterruptedException ex) { ex.printStackTrace(); }
        }
        status_przyrzadzania.setText("ukończono : "+target.getLabel());
        try { File_reader.zmien_stan_zamowienia_status(
                Integer.parseInt(target.getName()),
                Order.status.READY,
                null,
                Integer.toString(this.ID),
                null,
                null); }
        catch (IOException err) { err.printStackTrace(); }
    }; }
    private int get_cooking_time() { var random = new Random(); return random.nextInt(10,200); }
    public static void main(String[] args) { try { new KucharzAPP(); } catch (InterruptedException e) { throw new RuntimeException(e); }
    }
}