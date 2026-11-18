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

        } catch (Exception e) {
            System.out.println("Could not read saved data (" + e.getMessage() + "). Starting with sample data.");
            products = new ArrayList<>();
            customers = new ArrayList<>();
            users = new ArrayList<>();
            sales = new ArrayList<>();
            return false;
        }
    }
    public static void main(String[] args) {
        if (!loadData()) {
            loadSampleData();
            saveData();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(TindahanNiAlingNena::saveData));

        System.out.println("==============================================");
        System.out.println("       TINDAHAN NI ALING NENA");
        System.out.println("  Sales and Profit Margin System for MSME's  ");
        System.out.println("==============================================");
        login();
        saveData();

        int choice;
        do {
            System.out.println("\nLogged in: " + activeUser.name + " (" + activeUser.role + ")");
            System.out.println("\n============= MAIN MENU =============");
            System.out.println("1. Dashboard and Reports");
            System.out.println("2. POS / Process Sale");
            System.out.println("3. Inventory Management");
            System.out.println("4. Utang Tracker");
            System.out.println("5. Settings");
            System.out.println("6. Switch User / Log Out");
            System.out.println("0. Exit");
            choice = readInt("Choose: ");

            switch (choice) {
                case 1: showDashboard(); break;
                case 2: processSale(); break;
                case 3: inventoryMenu(); break;
                case 4: utangMenu(); break;
                case 5: settingsMenu(); break;
                case 6: login(); break;
                case 0: System.out.println("Thank you for using Tindahan ni Aling Nena!"); break;
                default: System.out.println("Invalid choice.");
            }
            saveData();
        } while (choice != 0);
    }

    static void loadSampleData() {
        users.add(new User("Aling Nena", "OWNER"));
        users.add(new User("Juan Dela Cruz", "CASHIER"));
        users.add(new User("Deomampo (Leader)", "OWNER"));
        users.add(new User("Maningas (Design)", "OWNER"));
        users.add(new User("Dela Cruz (Lead Dev)", "OWNER"));

        products.add(new Product("P001", "Cigarettes, pack of 20", "CigarettePack", 130, 140, 45));
        products.add(new Product("P002", "Sinandomeng Rice (1kg)", "Ordinary", 45, 55, 85));
        Product beer = new Product("P003", "San Miguel Pale Pilsen 330ml", "FermentedLiquor", 42, 52, 4);
        beer.volumeLiters = 0.33;
        products.add(beer);
        Product spirit = new Product("P004", "Ginebra San Miguel 700ml", "DistilledSpirit", 110, 135, 18);
        spirit.volumeLiters = 0.70;
        spirit.alcoholPercent = 40;
        products.add(spirit);
        Product wine = new Product("P005", "Novellino Red Wine 750ml", "Wine", 280, 350, 8);
        wine.volumeLiters = 0.75;
        products.add(wine);
        products.add(new Product("P006", "Lucky Me Instant Pancit Canton", "Ordinary", 12.50, 16, 3));
        products.add(new Product("P007", "Nescafe 3-in-1 Original Pack", "Ordinary", 7, 9.50, 120));

        customers.add(new Customer("C001", "Mang Jose", "0917-123-4567"));
        customers.add(new Customer("C002", "Aling Nida", "0918-987-6543"));
        customers.add(new Customer("C003", "Kuya Cardo", "0920-555-1234"));
    }
