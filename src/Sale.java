import java.io.Serializable;
import java.time.LocalDate;

public class Sale implements Serializable {
    private static final long serialVersionUID = 1L;
    public String id, productName, saleType, cashier, date, customerName;
    public int quantity;
    public double revenue, cost, tax, netProfit, amountPaid, balance;

    public Sale(String id, Product p, int quantity, String type, String cashier,
                double revenue, double tax, double amountPaid, String customerName) {
        this.id = id;
        this.productName = p.name;
        this.quantity = quantity;
        this.saleType = type;
        this.cashier = cashier;
        this.date = LocalDate.now().toString();
        this.revenue = revenue;
        this.cost = p.costPrice * quantity;
        this.tax = tax;
        this.netProfit = revenue - this.cost - tax;
        this.amountPaid = amountPaid;
        this.balance = Math.max(0, revenue - amountPaid);
        this.customerName = customerName;
    }
}
