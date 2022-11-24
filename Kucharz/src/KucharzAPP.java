import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class KucharzAPP extends Frame implements KeyListener, MouseListener {

    private Label wybrany = new Label();
    private Button zrob_danie = new Button();
    public KucharzAPP() {




    }

    private ArrayList<Button> get_orders() { // te dostępne


        return null;
    }

    private int get_cooking_time() { var random = new Random(); return random.nextInt(5,15); } // seconds

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    public static void main(String[] args) { new KucharzAPP(); }
}
