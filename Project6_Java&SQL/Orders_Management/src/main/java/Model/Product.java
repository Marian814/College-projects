package Model;

import java.util.Date;

public class Product {
    int ProductID;
    String ProductName;
    double Price;
    int StockQuantity;
    String Category;
    String Description;
    Date CreatedAt;
    Date ExpireAt;

    public Product(int productID, String productName, double price, int stockQuantity, String category, String description, Date createdAt, Date expireAt) {
        ProductID = productID;
        ProductName = productName;
        Price = price;
        StockQuantity = stockQuantity;
        Category = category;
        Description = description;
        CreatedAt = createdAt;
        ExpireAt = expireAt;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getStockQuantity() {
        return StockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        StockQuantity = stockQuantity;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        CreatedAt = createdAt;
    }

    public Date getExpireAt() {
        return ExpireAt;
    }

    public void setExpireAt(Date expireAt) {
        ExpireAt = expireAt;
    }

    @Override
    public String toString() {
        return "Product{" +
                "ProductID=" + ProductID +
                ", ProductName='" + ProductName + '\'' +
                ", Price=" + Price +
                ", StockQuantity=" + StockQuantity +
                ", Category='" + Category + '\'' +
                ", Description='" + Description + '\'' +
                ", CreatedAt='" + CreatedAt + '\'' +
                ", ExpireAt='" + ExpireAt + '\'' +
                '}';
    }
}
