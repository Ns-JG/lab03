package model;
import Klient.KlientAPP;
import Kucharz.KucharzAPP;
import Sprzedawca.SprzedawcaAPP;

public class Main {
    public static void main(String[] args) {
        new KlientAPP();
        //var klient_2 = new KlientAPP();
        try {
            new KucharzAPP();
            //var kucharz_2 = new KucharzAPP();
        } catch (InterruptedException err) { err.printStackTrace();}
       new SprzedawcaAPP();
    }
}