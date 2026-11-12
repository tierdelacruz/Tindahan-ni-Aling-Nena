
import java.io.Serializable;

class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    String id, name, category;
    double costPrice, sellingPrice, volumeLiters, alcoholPercent;
    int stock, packSize;

    Product(String id, String name, String category, double cost, double selling, int stock) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.costPrice = cost;
        this.sellingPrice = selling;
        this.stock = stock;
        this.packSize = 20;
        this.volumeLiters = 0.0;
        this.alcoholPercent = 40.0;
    }

    double unitProfit() {
        return sellingPrice - costPrice;
    }
}
