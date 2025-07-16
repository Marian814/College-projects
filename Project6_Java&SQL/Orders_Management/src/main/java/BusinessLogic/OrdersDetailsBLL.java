package BusinessLogic;

import DataAccess.OrdersDetailsDAO;
import Model.OrdersDetails;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class OrdersDetailsBLL {
    private OrdersDetailsDAO ordersDetailsDAO;

    /**
     * Constructor for OrdersDetailsBLL class.
     */
    public OrdersDetailsBLL() {
        ordersDetailsDAO = new OrdersDetailsDAO();
    }

    /**
     * Find OrdersDetails by OrderID and ProductID.
     * @param orderId
     * @param productId
     * @return
     */
    public OrdersDetails findOrdersDetailsById(int orderId, int productId) {
        OrdersDetails od = ordersDetailsDAO.findByOrderAndProductId(orderId, productId);
        if (od == null) {
            throw new NoSuchElementException("OrdersDetails with OrderID = " + orderId + " and ProductID = " + productId + " was not found!");
        }
        return od;
    }

    /**
     * Find all OrdersDetails.
     * @return
     */
    public ArrayList<OrdersDetails> findAllOrdersDetails() {
        ArrayList<OrdersDetails> list = ordersDetailsDAO.findAllOrdersDetailsDAO();
        if (list == null) {
            throw new NoSuchElementException("OrdersDetails list was not found!");
        }
        return list;
    }

    /**
     * Insert OrdersDetails.
     * @param ordersDetails
     * @return
     */
    public OrdersDetails insertOrdersDetails(OrdersDetails ordersDetails) {
        OrdersDetails inserted = ordersDetailsDAO.insertOrdersDetailsDAO(ordersDetails);
        if (inserted == null) {
            throw new IllegalArgumentException("OrdersDetails could not be inserted!");
        }
        return inserted;
    }
}
