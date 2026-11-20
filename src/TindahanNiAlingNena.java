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
    static void login() {
        while (true) {
            System.out.println("\n========== SELECT ACCOUNT ==========");
            for (int i = 0; i < users.size(); i++) {
                System.out.println((i + 1) + ". " + users.get(i).name + " - " + users.get(i).role);
            }
            System.out.println("0. Add New User");
            int choice = readInt("Select account: ");
            try {
                if (choice < 0 || choice > users.size()) {
                    throw new InvalidLoginException("Invalid account selection.");
                }
            } catch (InvalidLoginException e) {
                System.out.println(e.getMessage());
                continue;
            }

            if (choice == 0) {
                if (activeUser != null && !isOwner()) {
                    System.out.println("Only an Owner can add users.");
                    continue;
                }
                String name = readText("Full name: ");
                String role = readRole();
                users.add(new User(name, role));
                activeUser = users.get(users.size() - 1);
                saveData();
                return;
            }
            if (choice >= 1 && choice <= users.size()) {
                activeUser = users.get(choice - 1);
                return;
            }
            System.out.println("Invalid account.");
        }
    }
    
        static String readRole() {
        while (true) {
            String role = readText("Role (OWNER/CASHIER): ").toUpperCase();
            if (role.equals("OWNER") || role.equals("CASHIER")) return role;
            System.out.println("Enter OWNER or CASHIER.");
        }
    }

    static boolean isOwner() {
        return activeUser != null && activeUser.role.equals("OWNER");
    }

    static void showDashboard() {
        double totalSales = 0, totalCost = 0, totalProfit = 0, totalDebt = 0;
        int[] quantities = new int[products.size()];
        int bestIndex = -1;

        for (Sale s : sales) {
            totalSales += s.revenue;
            totalCost += s.cost;
            totalProfit += s.netProfit;
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).name.equals(s.productName)) quantities[i] += s.quantity;
            }
        }
        for (Customer c : customers) totalDebt += c.balance;
        for (int i = 0; i < quantities.length; i++)
            if (bestIndex == -1 || quantities[i] > quantities[bestIndex]) bestIndex = i;

        System.out.println("\n========== DASHBOARD & REPORTS ==========");
        System.out.printf("Total Sales:       PHP %.2f%n", totalSales);
        if (isOwner()) {
            System.out.printf("Total Puhunan:     PHP %.2f%n", totalCost);
            System.out.printf("Net Profit:        PHP %.2f%n", totalProfit);
        } else {
            System.out.println("Total Puhunan:     ***** (Owner access only)");
            System.out.println("Net Profit:        ***** (Owner access only)");
        }
        System.out.printf("Total Utang:       PHP %.2f%n", totalDebt);
        if (bestIndex >= 0 && quantities[bestIndex] > 0)
            System.out.println("Best Seller:       " + products.get(bestIndex).name);
        else
            System.out.println("Best Seller:       None yet");

        System.out.println("\nLOW STOCK ALERT (5 or fewer):");
        boolean found = false;
        for (Product p : products) {
            if (p.stock <= 5) {
                System.out.println("- " + p.name + " (" + p.stock + " left)");
                found = true;
            }
        }
        if (!found) System.out.println("No low-stock products.");

        System.out.println("\nRECENT SALES:");
        if (sales.isEmpty()) System.out.println("No sales recorded.");
        for (Sale s : sales) {
            System.out.printf("%s | %s | Qty: %d | PHP %.2f | %s%n",
                    s.id, s.productName, s.quantity, s.revenue, s.saleType);
        }
    }
static void processSale() {
        ArrayList<Product> cartProducts = new ArrayList<>();
        ArrayList<Integer> cartQty = new ArrayList<>();
        int choice;

        do {
            System.out.println("\n============= PRODUCT CATALOG =============");
            showProducts(false);
            System.out.println("0. Proceed to Checkout");
            System.out.println("-1. Cancel Sale");
            choice = readInt("Enter product number: ");

            if (choice == -1) return;
            if (choice == 0) break;
            if (choice < 1 || choice > products.size()) {
                System.out.println("Invalid product.");
                continue;
            }

            Product p = products.get(choice - 1);
            if (p.stock <= 0) {
                System.out.println("Out of stock.");
                continue;
            }
            int existing = cartProducts.indexOf(p);
            int already = existing >= 0 ? cartQty.get(existing) : 0;
            int qty = readInt("Quantity: ");
            if (qty <= 0 || already + qty > p.stock) {
                System.out.println("Invalid quantity or insufficient stock.");
                continue;
            }
            if (existing >= 0) cartQty.set(existing, already + qty);
            else {
                cartProducts.add(p);
                cartQty.add(qty);
            }
        } while (true);

        if (cartProducts.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        double subtotal = 0;
        for (int i = 0; i < cartProducts.size(); i++)
            subtotal += cartProducts.get(i).sellingPrice * cartQty.get(i);
        System.out.printf("Subtotal: PHP %.2f%n", subtotal);

        System.out.println("1. Regular Sale");
        System.out.println("2. Discounted Sale");
        System.out.println("3. Utang / Credit Sale");
        int type = readInt("Sale type: ");
        if (type < 1 || type > 3) {
            System.out.println("Invalid sale type.");
            return;
        }
double discountRate = 0;
        String discountReason = "";
        Customer customer = null;
        double downpayment = 0;

        if (type == 2) {
            discountRate = readDouble("Discount percentage (e.g. 20): ") / 100.0;
            if (discountRate < 0 || discountRate > 1) {
                System.out.println("Invalid discount.");
                return;
            }
            discountReason = readText("Discount reason: ");
        } else if (type == 3) {
            customer = chooseCustomer();
            if (customer == null) return;
            downpayment = readDouble("Down payment: ");
            if (downpayment < 0 || downpayment > subtotal) {
                System.out.println("Invalid down payment.");
                return;
            }
        }

        double payable = subtotal * (1 - discountRate);
        System.out.printf("Total payable: PHP %.2f%n", payable);
        if (type != 3) {
            double received = readDouble("Cash received: ");
            if (received < payable) {
                System.out.println("Insufficient payment. Sale cancelled.");
                return;
            }
            System.out.printf("Change: PHP %.2f%n", received - payable);
        }
String saleType = type == 1 ? "REGULAR" : type == 2 ? "DISCOUNTED" : "CREDIT";
        String cashier = activeUser.name + " (" + activeUser.role + ")";
        for (int i = 0; i < cartProducts.size(); i++) {
            Product p = cartProducts.get(i);
            int qty = cartQty.get(i);
            p.stock -= qty;
            double revenue = p.sellingPrice * qty * (1 - discountRate);
            double tax = calculateTax(revenue, p.costPrice * qty);
            double paid = type == 3 ? Math.min(downpayment, revenue) : revenue;
            Sale sale = new Sale("S" + transactionNumber++, p, qty, saleType, cashier,
                    revenue, tax, paid, customer == null ? "" : customer.name);
            sales.add(sale);
            if (customer != null) customer.balance += sale.balance;
            printReceipt(sale, discountReason);
        }
        saveData();
    }

    static double calculateTax(double revenue, double cost) {
        if (taxPolicy.equals("VAT")) {
            double outputVat = revenue - (revenue / (1 + vatRate));
            double inputVat = cost - (cost / (1 + vatRate));
            return outputVat - inputVat;
        }
        if (taxPolicy.equals("PERCENTAGE")) return revenue * percentageTaxRate;
        return 0;
    }
    static void printReceipt(Sale s, String reason) {
        System.out.println("\n============= SALES RECEIPT =============");
        System.out.println("Tindahan ni Aling Nena");
        System.out.println("Transaction: " + s.id);
        System.out.println("Date: " + s.date);
        System.out.println("Cashier: " + s.cashier);
        System.out.println("Product: " + s.productName);
        System.out.println("Quantity: " + s.quantity);
        System.out.printf("Revenue: PHP %.2f%n", s.revenue);
        System.out.println("Sale type: " + s.saleType);
        if (!reason.isEmpty()) System.out.println("Discount reason: " + reason);
        System.out.println("Tax policy: " + taxPolicy);
        System.out.printf("Estimated tax: PHP %.2f%n", s.tax);
        if (isOwner()) System.out.printf("Net profit: PHP %.2f%n", s.netProfit);
        if (!s.customerName.isEmpty()) {
            System.out.println("Customer: " + s.customerName);
            System.out.printf("Paid: PHP %.2f%n", s.amountPaid);
            System.out.printf("Remaining utang: PHP %.2f%n", s.balance);
        }
        System.out.println("=========================================");
    }

    static void inventoryMenu() {
        int choice;
        do {
            System.out.println("\n========== INVENTORY MANAGEMENT ==========");
            System.out.println("1. View Products");
            System.out.println("2. Search Product");
            System.out.println("3. Add Product (Owner)");
            System.out.println("4. Edit Product (Owner)");
            System.out.println("5. Delete Product (Owner)");
            System.out.println("6. Restock (+10)");
            System.out.println("0. Back");
            choice = readInt("Choose: ");
            switch (choice) {
                case 1: showProducts(true); break;
                case 2: searchProduct(); break;
                case 3: addProduct(); break;
                case 4: editProduct(); break;
                case 5: deleteProduct(); break;
                case 6: restockProduct(); break;
                case 0: break;
                default: System.out.println("Invalid choice.");
            }
            saveData();
        } while (choice != 0);
    }

    static void showProducts(boolean showCost) {
        System.out.println("\nID | Product | Category | Selling | Stock" +
                (showCost && isOwner() ? " | Cost | Profit" : ""));
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            System.out.printf("%d. %s | %s | %s | %.2f | %d",
                    i + 1, p.id, p.name, p.category, p.sellingPrice, p.stock);
            if (showCost && isOwner())
                System.out.printf(" | %.2f | %.2f", p.costPrice, p.unitProfit());
            System.out.println();
        }
    }

    static void searchProduct() {
        String key = readText("Enter product name or ID: ").toLowerCase();
        boolean found = false;
        for (Product p : products) {
            if (p.name.toLowerCase().contains(key) || p.id.toLowerCase().contains(key)) {
                System.out.println(p.id + " | " + p.name + " | Stock: " + p.stock);
                found = true;
            }
        }
        if (!found) System.out.println("No matching product.");
    }
    
    static void addProduct() {
        if (!requireOwner()) return;
        String id = readText("Product ID: ");
        if (findProduct(id) != null) {
            System.out.println("Product ID already exists.");
            return;
        }
        String name = readText("Product name: ");
        String category = readText("Category (Ordinary/CigarettePack/DistilledSpirit/Wine/FermentedLiquor): ");
        double cost = readDouble("Cost price: ");
        double selling = readDouble("Selling price: ");
        int stock = readInt("Initial stock: ");
        if (cost < 0 || selling < 0 || stock < 0) {
            System.out.println("Values cannot be negative.");
            return;
        }
        Product p = new Product(id, name, category, cost, selling, stock);
        if (category.equals("CigarettePack")) p.packSize = readInt("Pack size (sticks): ");
        if (category.equals("DistilledSpirit") || category.equals("Wine") || category.equals("FermentedLiquor")) {
            p.volumeLiters = readDouble("Volume in liters: ");
        }
        if (category.equals("DistilledSpirit")) p.alcoholPercent = readDouble("Alcohol percentage: ");
        products.add(p);
        System.out.println("Product added.");
    }
