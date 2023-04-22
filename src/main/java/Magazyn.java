public class Magazyn
{
    private final int[] zasoby;
    private final int pojemnosc;
    private int zajetosc = 0;
    private final int n_prod;
    private final String TEXT_RESET = "\u001B[0m";
    private final String TEXT_GREEN = "\u001B[32m";
    private final String TEXT_ERROR = "\u001B[31m";
    private final String TEXT_WAIT = "\u001B[33m";
    private final String TEXT_INFO = "\u001B[34m";
    private final String TEXT_CYAN = "\u001B[36m";
    private int op_counter = 0;

    public int get_n_prod() {
        return n_prod;
    }

    public Magazyn(int n_prod, int pojemnosc) {
        this.n_prod = n_prod;
        this.zasoby = new int[n_prod];
        this.pojemnosc = pojemnosc;
    }

    void printColored(String text, String color){
        System.out.print(color);
        System.out.println(text);
        System.out.print(TEXT_RESET);
    }

    public synchronized void printStatus(){
        op_counter++;
        if(op_counter >= 10){
            StringBuilder info = new StringBuilder();
            for (var p : zasoby){
                info.append(p).append(" ");
            }
            printColored("Aktualna zajętość magazynu: "+zajetosc+"/"+pojemnosc+". Liczba produktów: "+info, TEXT_INFO);
            op_counter = 0;
        }
    }

    public synchronized void konsumuj(int id, int id_produktu, int ilosc){
        System.out.println("Konsument nr "+id+" pragnie otrzymać "+ilosc+" sztuk towaru o id "+id_produktu);
        int dl_oczekiwania = 0;
        while (zasoby[id_produktu] < ilosc){
            if(zasoby[id_produktu] > 0){
                int do_pobrania = zasoby[id_produktu];
                zasoby[id_produktu] -= do_pobrania;
                zajetosc -= do_pobrania;
                printColored("Konsument nr " + id + " pobrał "+do_pobrania+" z oczekiwanych "+ilosc+" szt. towaru o id "+id_produktu+".", TEXT_GREEN);
                ilosc -= do_pobrania;
            }
            else{
                dl_oczekiwania++;
                if(dl_oczekiwania > n_prod+1){
                    printColored("Konsument nr " + id + " rezygnuje z zakupu produktu o id "+id_produktu+".", TEXT_ERROR);
                    notifyAll();
                    return;
                }
            }
            try {
                printColored("Brak towaru w magazynie. Konsument nr "+id+" czeka na "+ilosc+" szt. towaru o id "+id_produktu, TEXT_WAIT);
                wait();
            } catch (InterruptedException ignored) { }
        }
        zasoby[id_produktu] -= ilosc;
        zajetosc -= ilosc;
        printColored("Konsument nr " + id + " otrzymał wszystkie zasoby (produkt o id "+id_produktu+").", TEXT_GREEN);
        printStatus();
        notifyAll();
    }

    public synchronized void produkuj(int id,  int id_produktu, int ilosc){
        System.out.println("Producent nr "+id+" wytworzył "+ilosc+" szt. towaru o id "+id_produktu);
        while (pojemnosc-zajetosc < ilosc || pojemnosc <= zajetosc){
            try {
                printColored("Producent nr " + id + " czeka na zwolnienie miejsca w magazynie.", TEXT_ERROR);
                System.out.println("Pojemność = "+ pojemnosc + ", Zajętość = " + zajetosc);
                wait();
            } catch (InterruptedException ignored) { }
        }
        zasoby[id_produktu] += ilosc;
        zajetosc += ilosc;
        printColored("Producent nr "+id+" włożył do magazynu "+ilosc+" szt. towaru o id "+id_produktu, TEXT_CYAN);
        printStatus();
        notifyAll();
    }
}
