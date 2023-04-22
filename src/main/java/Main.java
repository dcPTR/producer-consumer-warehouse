public class Main {
    public static void main(String[] args) {
        int liczba_produktow = 5;
        int pojemnosc_magazynu = 150;
        Magazyn m = new Magazyn(liczba_produktow, pojemnosc_magazynu);
        Thread p1 = new Thread(new Producent(m, 1));
        Thread p2 = new Thread(new Producent(m, 2));
        Thread p3 = new Thread(new Producent(m, 3));
        Thread c1 = new Thread(new Konsument(m, 1));
        Thread c2 = new Thread(new Konsument(m, 2));
        Thread c3 = new Thread(new Konsument(m, 3));
        Thread c4 = new Thread(new Konsument(m, 4));
        p1.start();
        c1.start();
        p2.start();
        c2.start();
        p3.start();
        c3.start();
        c4.start();
    }
}
