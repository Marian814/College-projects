package Model;

import java.sql.Date;

public class Orders {
    int OrderID;
    int ClientID;
    Date OrderDate;
    double TotalAmount;
    String Status;

    public Orders(int orderID, int clientID, Date orderDate, double totalAmount, String status) {
        OrderID = orderID;
        ClientID = clientID;
        OrderDate = orderDate;
        TotalAmount = totalAmount;
        Status = status;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public int getClientID() {
        return ClientID;
    }

    public void setClientID(int clientID) {
        ClientID = clientID;
    }

    public Date getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(Date orderDate) {
        OrderDate = orderDate;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "OrderID=" + OrderID +
                ", ClientID=" + ClientID +
                ", OrderDate='" + OrderDate + '\'' +
                ", TotalAmount=" + TotalAmount +
                ", Status='" + Status + '\'' +
                '}';
    }
}
