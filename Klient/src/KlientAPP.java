import java.awt.*;
import java.awt.event.*;
import model.File_reader;

public class KlientAPP extends Frame implements KeyListener, MouseListener {

    public KlientAPP() {
        TextArea menu_label = new TextArea(File_reader.menu_as_string, 20,1);
        menu_label.setEditable(false);
        menu_label.setBounds(50,50, 150,350);

        add(menu_label);
        addWindowListener(new WindowAdapter() { public void windowClosing( WindowEvent e) { dispose(); System.exit(1); } });

        setSize(500,500);
        setLayout(null);
        setVisible(true);

    }

    // contents



    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    static public void main(String[] args) { new KlientAPP(); }
}
