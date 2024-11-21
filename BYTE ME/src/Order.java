import java.util.*;

class Order {
    private static int idNumber = 0;
    private int orderID;
    private Customer customer;
    private List<MenuItem> items;
    private String status;
    private boolean isVIP;
    private double totalPrice;
    private String deliveryAddress;
    private static List<Order> allOrders = new ArrayList<>();



    public Order(Customer customer, boolean isVIP) {
        this.orderID = ++idNumber;
        this.customer = customer;
        this.items = new ArrayList<>();
        this.status = "Received";
        this.isVIP = isVIP;
        this.totalPrice = 0.0;
        this.deliveryAddress = "";
        allOrders.add(this);
    }
    public static List<Order> getAllOrders() {
        return allOrders;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public static int getIdNumber() {
        return idNumber;
    }

    public static void setIdNumber(int idNumber) {
        Order.idNumber = idNumber;
    }

    public void setVIP(boolean VIP) {
        isVIP = VIP;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }


    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isVIP() {
        return isVIP;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addItem(MenuItem item) {
        items.add(item);
        totalPrice += item.getPrice();
    }

    public List<MenuItem> getItems() { return items; }
    public double getTotalPrice() { return totalPrice; }


    public boolean containsItem(String itemName) {
        for (MenuItem item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Order{" +
                "customer=" + customer +
                ", orderID=" + orderID +
                ", items=" + items +
                ", status='" + status + '\'' +
                ", isVIP=" + isVIP +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
