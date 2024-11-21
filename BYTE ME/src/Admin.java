import java.util.*;

public class Admin {
    private List<MenuItem> menuList;
    private List<Order> orders;
    private double totalSales;
    private Map<String, Integer> itemPopularity;

    public Admin() {
        this.menuList = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.totalSales = 0.0;
        this.itemPopularity = new HashMap<>();
    }

    public Map<String, Integer> getItemPopularity() {
        return itemPopularity;
    }

    public void setItemPopularity(Map<String, Integer> itemPopularity) {
        this.itemPopularity = itemPopularity;
    }

    public List<MenuItem> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuItem> menuList) {
        this.menuList = menuList;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }
    public void addMenuItem(MenuItem item) {
        menuList.add(item);
        System.out.println("Item added: " + item);
    }

    public void addMenuItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();

        double price;
        while (true) {
            System.out.print("Enter price: ");
            try {
                price = Double.parseDouble(scanner.nextLine());
                if (price < 0) {
                    System.out.println("Price cannot be negative. Please enter a valid price.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value for the price.");
            }
        }

        System.out.print("Enter category: ");
        String category = scanner.nextLine();

        if (menuList.stream().anyMatch(item -> item.getName().equalsIgnoreCase(name))) {
            System.out.println("An item with this name already exists. Please use a different name.");
            return;
        }

        MenuItem newItem = new MenuItem(name, price, category, true);
        menuList.add(newItem);
        System.out.println("Item added: " + newItem);
    }


    public void updateMenuItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter item name to update: ");
        String itemName = scanner.nextLine();

        MenuItem item = findMenuItemByName(itemName);

        if (item != null) {
            System.out.print("Enter new price: ");
            double newPrice = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Is available (true/false): ");
            String availabilityInput = scanner.nextLine();
            boolean isAvailable = availabilityInput.equalsIgnoreCase("true");

            item.setPrice(newPrice);
            item.setAvailable(isAvailable);

            System.out.println("Item updated: " + item);
        } else {
            System.out.println("Menu item not found.");
        }
    }

    private MenuItem findMenuItemByName(String itemName) {
        for (MenuItem item : menuList) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }


    public void removeMenuItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter item name to remove: ");
        String removeName = scanner.nextLine();
        boolean removed = menuList.removeIf(item -> item.getName().equalsIgnoreCase(removeName));
        if (removed) {
            System.out.println("Item removed: " + removeName);
            updatePendingOrdersWithRemovedItem(removeName);
        } else {
            System.out.println("Item not found in menu.");
        }
    }

    private void updatePendingOrdersWithRemovedItem(String itemName) {
        for (Order order : orders) {
            if (order.containsItem(itemName) && "Received".equals(order.getStatus())) {
                order.setStatus("Denied");
                System.out.println("Updated order status to 'Denied' for order ID: " + order.getOrderID());
            }
        }
    }
    public void viewPendingOrders() {
        List<Order> pendingOrders = new ArrayList<>(Order.getAllOrders());
        pendingOrders.sort(Comparator.comparing(Order::isVIP).reversed());

        boolean hasPendingOrders = false;

        for (Order order : pendingOrders) {
            if ("Received".equals(order.getStatus())) {
                System.out.println(order);
                hasPendingOrders = true;
            }
        }

        if (!hasPendingOrders) {
            System.out.println("No pending orders.");
        }
    }


    public void updateOrderStatus() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter order ID to update status: ");

        int orderId;
        try {
            orderId = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input for order ID. Please enter a valid number.");
            scanner.nextLine();
            return;
        }

        System.out.print("Enter new status (Received, In Progress, Completed): ");
        String status = scanner.nextLine().trim();

        List<String> validStatuses = Arrays.asList("Received", "In Progress", "Completed");

        if (!validStatuses.contains(status)) {
            System.out.println("Invalid status. Please enter a valid status (Received, In Progress, Completed).");
            return;
        }

        for (Order order : Order.getAllOrders()) {
            if (order.getOrderID() == orderId) {
                order.setStatus(status);
                System.out.println("Order status updated: " + order);
                if ("Completed".equals(status)) {
                    updateSalesData(order);
                }
                return;
            }
        }
        System.out.println("Order not found.");
    }

    private void updateSalesData(Order order) {
        totalSales += order.getTotalPrice();
        for (MenuItem item : order.getItems()) {
            itemPopularity.put(item.getName(), itemPopularity.getOrDefault(item.getName(), 0) + 1);
        }
    }

    public void processRefund() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter order ID for refund: ");
        int refundId = scanner.nextInt();
        for (Order order : orders) {
            if (order.getOrderID() == refundId && !"Refunded".equals(order.getStatus())) {
                order.setStatus("Refunded");
                totalSales -= order.getTotalPrice();
                System.out.println("Refund processed for order ID: " + refundId);
                return;
            }
        }
        System.out.println("Order not found or already refunded.");
    }

    public void generateDailySalesReport() {
        System.out.println("Daily Sales Report:");
        System.out.println("Total Sales: $" + totalSales);
        System.out.println("Total Orders Processed: " + orders.size());
        System.out.println("Most Popular Items:");

        itemPopularity.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue() + " orders"));
    }
    public void viewReviews() {
        if (menuList.isEmpty()) {
            System.out.println("No menu items available.");
            return;
        }

        System.out.println("Reviews for all menu items:");
        for (MenuItem menuItem : menuList) {
            System.out.println(menuItem.getName() + " Reviews:");
            menuItem.displayReviews();
        }
    }

}
