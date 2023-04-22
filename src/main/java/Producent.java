import java.util.Random;
import static java.lang.Thread.sleep;

public class Producent implements Runnable
{
    Random random;
    Magazyn magazyn;
    int id;
    int n_prod;

    public Producent(Magazyn m, int id){
        this.magazyn = m;
        this.id = id;
        this.n_prod = magazyn.get_n_prod();
        random = new Random();
    }

    public void run(){
        while(true){
            int id_produktu = (random.nextInt() & Integer.MAX_VALUE)%n_prod;
            int ilosc = (random.nextInt() & Integer.MAX_VALUE)%8+1;
            magazyn.produkuj(id, id_produktu, ilosc);
            try {
                sleep(2000+(random.nextInt() & Integer.MAX_VALUE)%5000);
            } catch (InterruptedException ignored) { }
        }
    }
}
