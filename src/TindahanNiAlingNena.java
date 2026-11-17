import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TindahanNiAlingNena {
    static Scanner input = new Scanner(System.in);
    static ArrayList<Product> products = new ArrayList<>();
    static ArrayList<Customer> customers = new ArrayList<>();
    static ArrayList<User> users = new ArrayList<>();
    static ArrayList<Sale> sales = new ArrayList<>();
    static User activeUser;

    static String taxPolicy = "VAT";
    static double vatRate = 0.12;
    static double percentageTaxRate = 0.03;
    static double cigaretteRate = 69.46;
    static double spiritsRate = 74.16;
    static double spiritsAdValorem = 0.22;
    static double wineRate = 70.92;
    static double beerRate = 48.31;
    static int transactionNumber = 1003;

    static final String DATA_FILE = "tindahan_data.dat";

      static synchronized void saveData() {
        SaveState s = new SaveState();
        s.products = products;
        s.customers = customers;
        s.users = users;
        s.sales = sales;
        s.taxPolicy = taxPolicy;
        s.vatRate = vatRate;
        s.percentageTaxRate = percentageTaxRate;
        s.cigaretteRate = cigaretteRate;
        s.spiritsRate = spiritsRate;
        s.spiritsAdValorem = spiritsAdValorem;
        s.wineRate = wineRate;
        s.beerRate = beerRate;
        s.transactionNumber = transactionNumber;

        Path target = Paths.get(DATA_FILE);
        Path temp = Paths.get(DATA_FILE + ".tmp");
        try {
            try (ObjectOutputStream out = new ObjectOutputStream(
                    new BufferedOutputStream(Files.newOutputStream(temp)))) {
                out.writeObject(s);
            }
            try {
                Files.move(temp, target, StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(temp, target, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            System.out.println("Warning: could not save data (" + e.getMessage() + ")");
        }
    }

    static boolean loadData() {
        Path file = Paths.get(DATA_FILE);
        if (!Files.exists(file)) return false;
        try (ObjectInputStream in = new ObjectInputStream(
                new BufferedInputStream(Files.newInputStream(file)))) {
            SaveState s = (SaveState) in.readObject();
            products = s.products;
            customers = s.customers;
            users = s.users;
            sales = s.sales;
            taxPolicy = s.taxPolicy;
            vatRate = s.vatRate;
            percentageTaxRate = s.percentageTaxRate;
            cigaretteRate = s.cigaretteRate;
            spiritsRate = s.spiritsRate;
            spiritsAdValorem = s.spiritsAdValorem;
            wineRate = s.wineRate;
            beerRate = s.beerRate;
            transactionNumber = s.transactionNumber;
            System.out.println("Saved data loaded from " + DATA_FILE);
            return true;
