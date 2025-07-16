package Model;

public class OrdersDetails {
    int OrderID;
    int ProductID;
    int Quantity;
    double PriceAtOrderTime;
    double TotalPerProduct;

    public OrdersDetails(int orderID, int productID, int quantity, double priceAtOrderTime) {
        OrderID = orderID;
        ProductID = productID;
        Quantity = quantity;
        PriceAtOrderTime = priceAtOrderTime;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public double getPriceAtOrderTime() {
        return PriceAtOrderTime;
    }

    public void setPriceAtOrderTime(double priceAtOrderTime) {
        PriceAtOrderTime = priceAtOrderTime;
    }

    public double getTotalPerProduct() {
        return TotalPerProduct;
    }

    @Override
    public String toString() {
        return "OrdersDetails{" +
                "OrderID=" + OrderID +
                ", ProductID=" + ProductID +
                ", Quantity=" + Quantity +
                ", PriceAtOrderTime=" + PriceAtOrderTime +
                ", TotalPerProduct=" + TotalPerProduct +
                '}';
    }
}
