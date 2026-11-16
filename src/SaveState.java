import java.io.Serializable;
import java.util.ArrayList;

public class SaveState implements Serializable {
    private static final long serialVersionUID = 1L;
    public ArrayList<Product> products;
    public ArrayList<Customer> customers;
    public ArrayList<User> users;
    public ArrayList<Sale> sales;
    public String taxPolicy;
    public double vatRate, percentageTaxRate, cigaretteRate, spiritsRate,
            spiritsAdValorem, wineRate, beerRate;
    public int transactionNumber;
}
