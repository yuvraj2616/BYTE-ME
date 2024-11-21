import java.util.*;
import java.util.stream.Collectors;

class Customer {
    private static int idNumber = 0;
    private int customerID;
    private String name;
    private boolean isVIP;
    private List<Order> orderHistory;
    private Map<MenuItem, Integer> cart;

    public Customer(String name, boolean isVIP) {
        this.customerID = ++idNumber;
        this.name = name;
        this.isVIP = isVIP;
        this.orderHistory = new ArrayList<>();
        this.cart = new HashMap<>();
    }

    public Map<MenuItem, Integer> getCart() {
        return cart;
    }

    public void setCart(Map<MenuItem, Integer> cart) {
        this.cart = cart;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public static int getIdNumber() {
        return idNumber;
    }

    public static void setIdNumber(int idNumber) {
        Customer.idNumber = idNumber;
    }

    public boolean isVIP() {
        return isVIP;
    }

    public void setVIP(boolean VIP) {
        isVIP = VIP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void addOrder(Order order) {
        orderHistory.add(order);
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    private static Scanner scanner = new Scanner(System.in);

    public void becomeVIP() {
        System.out.print("Enter amount for VIP upgrade: ");
        double paymentAmount = scanner.nextDouble();
        if (paymentAmount >= 50) {
            this.isVIP = true;
            System.out.println(name + " is now a VIP member!");
        } else {
            System.out.println("Insufficient amount to upgrade to VIP.");
        }
    }

    public void viewMenu(List<MenuItem> menu) {
        System.out.println("Menu:");
        for (MenuItem item : menu) {
            System.out.println(item);
        }
    }

    public void searchMenu(List<MenuItem> menu) {
        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine().trim();

        if (keyword.isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
            return;
        }

        List<MenuItem> results = menu.stream()
                .filter(item -> item.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            System.out.println("No items found for \"" + keyword + "\".");
        } else {
            System.out.println("Search results for \"" + keyword + "\":");
            results.forEach(System.out::println);
        }
    }

    public void viewOrderStatus() {
        List<Order> allOrders = getOrderHistory();
        if (allOrders.isEmpty()) {
            System.out.println("No orders to display.");
            return;
        }

        System.out.println("Order History:");
        for (int i = 0; i < allOrders.size(); i++) {
            Order order = allOrders.get(i);
            System.out.println((i + 1) + ". Order ID: " + order.getOrderID() + " - Status: " + order.getStatus());
        }

        System.out.print("Enter the order number to view details: ");
        int orderNumber = scanner.nextInt() - 1;

        if (orderNumber >= 0 && orderNumber < allOrders.size()) {
            Order selectedOrder = allOrders.get(orderNumber);
            displayOrderDetails(selectedOrder);
        } else {
            System.out.println("Invalid order selection.");
        }
    }

    private void displayOrderDetails(Order order) {
        if (order == null) {
            System.out.println("Invalid order.");
            return;
        }

        System.out.println("Order Details:");
        System.out.println("Order ID: " + order.getOrderID());
        System.out.println("Customer Name: " + order.getCustomer());
        System.out.println("Items Ordered:");

        List<MenuItem> orderedItems = order.getItems();
        if (orderedItems.isEmpty()) {
            System.out.println("  No items in this order.");
        } else {
            for (MenuItem item : orderedItems) {
                System.out.println("  - " + item.getName() + " - " + item.getPrice() + " (" + item.getCategory() + ")");
            }
        }

        System.out.println("Total Amount: " + order.getTotalPrice());
        System.out.println("Order Status: " + order.getStatus());

        switch (order.getStatus()) {
            case "Received":
                System.out.println(" Your order is being prepared.");
                break;
            case "In Progress":
                System.out.println(" Your order is being cooked.");
                break;
            case "Delivered":
                System.out.println(" Your order has been delivered.");
                break;
            case "Cancelled":
                System.out.println(" Your order has been cancelled.");
                break;
            case "Refunded":
                System.out.println(" Your order has been refunded.");
                break;
            default:
                System.out.println("  Unknown status.");
                break;
        }
    }

    public void addItemToCart(List<MenuItem> menu) {
        System.out.print("Enter item name to add to cart: ");
        String itemName = scanner.nextLine().trim();

        if (itemName.isEmpty()) {
            System.out.println("Item name cannot be empty. Please try again.");
            return;
        }

        System.out.print("Enter quantity: ");
        int quantity;
        try {
            quantity = Integer.parseInt(scanner.nextLine());
            if (quantity <= 0) {
                System.out.println("Quantity must be greater than zero.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity. Please enter a number.");
            return;
        }

        MenuItem itemToAdd = findMenuItemByName(itemName, menu);

        if (itemToAdd != null) {
            cart.put(itemToAdd, cart.getOrDefault(itemToAdd, 0) + quantity);
            System.out.println(quantity + " x " + itemToAdd.getName() + " added to cart.");
        } else {
            System.out.println("Item not found. Please check the item name and try again.");
        }
    }


    public void modifyCartQuantity() {
        System.out.print("Enter item name to modify quantity: ");
        String itemName = scanner.nextLine();

        MenuItem itemToModify = findMenuItemByName(itemName, new ArrayList<>(cart.keySet()));

        if (itemToModify != null) {
            System.out.print("Enter new quantity: ");
            int newQuantity = scanner.nextInt();

            if (newQuantity > 0) {
                cart.put(itemToModify, newQuantity);
                System.out.println("Cart updated: " + newQuantity + " x " + itemToModify.getName());
            } else {
                cart.remove(itemToModify);
                System.out.println(itemToModify.getName() + " removed from cart.");
            }
        } else {
            System.out.println("Item not found in cart.");
        }
    }

    public void filterMenu(List<MenuItem> menu) {
        System.out.print("Enter category (or leave blank): ");
        String category = scanner.nextLine().trim();

        System.out.print("Enter minimum price (or -1 for no min): ");
        double minPrice = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter maximum price (or -1 for no max): ");
        double maxPrice = scanner.nextDouble();
        scanner.nextLine();

        Double min = (minPrice >= 0) ? minPrice : null;
        Double max = (maxPrice >= 0) ? maxPrice : null;

        List<MenuItem> filteredMenu = menu.stream()
                .filter(item -> category.isEmpty() || item.getCategory().equalsIgnoreCase(category))
                .filter(item -> min == null || item.getPrice() >= min)
                .filter(item -> max == null || item.getPrice() <= max)
                .collect(Collectors.toList());

        if (filteredMenu.isEmpty()) {
            System.out.println("No items matched.");
        } else {
            System.out.println("Filtered Menu:");
            for (MenuItem item : filteredMenu) {
                System.out.println(item);
            }
        }
    }

    public void placeOrder() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty! Add items before placing an order.");
            return;
        }

        viewCart();

        System.out.print("Enter payment amount: ");
        double paymentAmount;
        try {
            paymentAmount = scanner.nextDouble();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input for payment amount. Please enter a valid number.");
            scanner.nextLine();
            return;
        }

        Order newOrder = new Order(this, isVIP);
        cart.forEach((item, quantity) -> {
            for (int i = 0; i < quantity; i++) {
                newOrder.addItem(item);
            }
        });

        double totalAmount = newOrder.getTotalPrice();

        if (paymentAmount < totalAmount) {
            System.out.println("Insufficient payment. You need to pay at least: " + totalAmount);
            return;
        }

        System.out.print("Enter delivery address: ");
        String deliveryAddress = scanner.nextLine();
        newOrder.setDeliveryAddress(deliveryAddress);

        cart.clear();
        addOrder(newOrder);

        System.out.println("Order placed successfully! Order ID: " + newOrder.getOrderID());
        System.out.println("Total amount: " + totalAmount);
        System.out.println("Payment received: " + paymentAmount);
        System.out.println("Delivery Address: " + deliveryAddress);
    }


    public void cancelOrder() {
        System.out.println("Order History:");
        for (int i = 0; i < orderHistory.size(); i++) {
            System.out.println((i + 1) + ". " + orderHistory.get(i));
        }
        System.out.print("Enter the order number to cancel: ");
        int orderIndex = scanner.nextInt() - 1;

        if (orderIndex >= 0 && orderIndex < orderHistory.size()) {
            Order orderToCancel = orderHistory.get(orderIndex);
            if ("Received".equals(orderToCancel.getStatus())) {
                orderToCancel.setStatus("Cancelled");
                System.out.println("Order ID: " + orderToCancel.getOrderID() + " has been cancelled.");
                System.out.println("Your amount will be refunded in 7-10 working days.");

            } else {
                System.out.println("Order cannot be cancelled as it is already being processed.");
            }
        } else {
            System.out.println("Invalid order selection.");
        }
    }


    public void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Items in your cart:");
            double total = 0;
            for (Map.Entry<MenuItem, Integer> entry : cart.entrySet()) {
                MenuItem item = entry.getKey();
                int quantity = entry.getValue();
                double itemTotal = item.getPrice() * quantity;
                total += itemTotal;
                System.out.println(quantity + " x " + item.getName() + " - " + item.getPrice() + " (Total: " + itemTotal + ")");
            }
            System.out.println("Total amount: " + total);
        }
    }

    public MenuItem findMenuItemByName(String name, List<MenuItem> menu) {
        return menu.stream()
                .filter(item -> item.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
    public void addReview(List<MenuItem> menu) {
        System.out.print("Enter the name of the menu item you want to give review: ");
        String itemName = scanner.nextLine();

        MenuItem menuItem = findMenuItemByName(itemName, menu);

        if (menuItem != null) {
            System.out.print("Enter your review: ");
            String review = scanner.nextLine();
            menuItem.addReview(review);
            System.out.println("Thnks for ur review on " + menuItem.getName() + "!");
        } else {
            System.out.println("Menu item not found.");
        }
    }
    public void sortMenuItems(List<MenuItem> menu) {
        System.out.print("Sort by price (1 for ascending, 2 for descending): ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            Collections.sort(menu, Comparator.comparingDouble(MenuItem::getPrice));
            System.out.println("Menu items sorted in ascending order by price.");
        } else if (choice == 2) {
            Collections.sort(menu, Comparator.comparingDouble(MenuItem::getPrice).reversed());
            System.out.println("Menu items sorted in descending order by price.");
        } else {
            System.out.println("Invalid choice. No sorting applied.");
            return;
        }

        displayMenu(menu);
    }
    private void displayMenu(List<MenuItem> menu) {
        System.out.println("Sorted Menu:");
        for (MenuItem item : menu) {
            System.out.println(item);
        }
    }
    public void viewReviews(List<MenuItem> menu) {
        if (menu.isEmpty()) {
            System.out.println("No menu items available.");
            return;
        }

        System.out.println("Reviews for all menu items:");
        for (MenuItem menuItem : menu) {
            System.out.println(menuItem.getName() + " Reviews:");
            menuItem.displayReviews();
        }
    }


}
