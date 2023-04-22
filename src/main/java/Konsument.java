import java.util.Random;
import static java.lang.Thread.sleep;

public class Konsument implements Runnable
{
    Magazyn magazyn;
    Random random;
    int id;
    int n_prod;


    public Konsument(Magazyn m, int id){
        this.magazyn = m;
        this.id = id;
        this.n_prod = this.magazyn.get_n_prod();
        random = new Random();
    }

    public void run(){
        while(true){
            int id_produktu = (random.nextInt() & Integer.MAX_VALUE)%n_prod;
            int ilosc = (random.nextInt() & Integer.MAX_VALUE)%3+1;
            magazyn.konsumuj(id, id_produktu, ilosc);
            try {
                sleep((random.nextInt() & Integer.MAX_VALUE)%5000);
            } catch (InterruptedException ignored) { }
        }
    }
}

