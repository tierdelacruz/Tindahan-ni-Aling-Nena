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
