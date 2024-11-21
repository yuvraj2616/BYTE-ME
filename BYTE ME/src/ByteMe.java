import java.util.*;

public class ByteMe {
    private Admin admin;
    private Map<Integer, Customer> customers;
    private final String adminPassword = "admin123";

    public ByteMe() {
        this.admin = new Admin();
        this.customers = new HashMap<>();
    }

    public void registerCustomer(String name, boolean isVIP) {
        Customer newCustomer = new Customer(name, isVIP);
        customers.put(newCustomer.getCustomerID(), newCustomer);
        System.out.println("Customer registered: " + name + " (ID: " + newCustomer.getCustomerID() + ")");
    }

    public Customer getCustomer(int customerID) {
        return customers.get(customerID);
    }

    public Customer getCustomerByName(String name) {
        return customers.values().stream()
                .filter(customer -> customer.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    private boolean adminLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Admin Password: ");
        String inputPassword = scanner.nextLine();
        if (inputPassword.equals(adminPassword)) {
            System.out.println("Admin login successful.");
            return true;
        } else {
            System.out.println("Incorrect password. Access denied.");
            return false;
        }
    }

    private Customer customerLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Customer ID or Name: ");
        String input = scanner.nextLine();

        Customer customer;
        try {
            int customerID = Integer.parseInt(input);
            customer = getCustomer(customerID);
        } catch (NumberFormatException e) {
            customer = getCustomerByName(input);
        }

        if (customer != null) {
            System.out.println("Customer login successful. Welcome, " + customer.getName() + "!");
            return customer;
        } else {
            System.out.println("Customer not found. Please register or try again.");
            return null;
        }
    }

    public void adminMenu() {
        if (!adminLogin()) return;

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add Menu Item");
            System.out.println("2. Update Menu Item");
            System.out.println("3. Remove Menu Item");
            System.out.println("4. View Pending Orders");
            System.out.println("5. Update Order Status");
            System.out.println("6. Process Refund");
            System.out.println("7. Generate Daily Report");
            System.out.println("8. View Reviews");
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> admin.addMenuItem();
                case 2 -> admin.updateMenuItem();
                case 3 -> admin.removeMenuItem();
                case 4 -> admin.viewPendingOrders();
                case 5 -> admin.updateOrderStatus();
                case 6 -> admin.processRefund();
                case 7 -> admin.generateDailySalesReport();
                case 8 -> admin.viewReviews();
                case 0 -> {
                    System.out.println("Exiting Admin Menu.");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void customerMenu(Customer customer) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nCustomer Menu:");
            System.out.println("1. View Menu");
            System.out.println("2. Add Item to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Modify Cart Item Quantity");
            System.out.println("5. View Order status");
            System.out.println("6. Place Order");
            System.out.println("7. View Order History");
            System.out.println("8. Cancel Order");
            System.out.println("9. Upgrade to VIP");
            System.out.println("10. Search Menu");
            System.out.println("11. Filter Menu");
            System.out.println("12. Add Review");
            System.out.println("13. Sort Menu Items by Price");
            System.out.println("14. View Reviews"); // New option for viewing reviews
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> customer.viewMenu(admin.getMenuList());
                case 2 -> customer.addItemToCart(admin.getMenuList());
                case 3 -> customer.viewCart();
                case 4 -> customer.modifyCartQuantity();
                case 5 -> customer.viewOrderStatus();
                case 6 -> customer.placeOrder();
                case 7 -> customer.getOrderHistory().forEach(System.out::println);
                case 8 -> customer.cancelOrder();
                case 9 -> customer.becomeVIP();
                case 10 -> customer.searchMenu(admin.getMenuList());
                case 11 -> customer.filterMenu(admin.getMenuList());
                case 12 -> customer.addReview(admin.getMenuList());
                case 13 -> customer.sortMenuItems(admin.getMenuList());
                case 14 -> customer.viewReviews(admin.getMenuList());
                case 0 -> {
                    System.out.println("Exiting Customer Menu.");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }


    public static void main(String[] args) {
        ByteMe system = new ByteMe();
        Scanner scanner = new Scanner(System.in);

        system.registerCustomer("Alice", true);
        system.registerCustomer("Bob", false);

        system.admin.addMenuItem(new MenuItem("Burger", 5.99, "Food", true));
        system.admin.addMenuItem(new MenuItem("Fries", 2.99, "Food", true));
        system.admin.addMenuItem(new MenuItem("Soda", 1.49, "Beverage", true));

        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Login");
            System.out.println("0. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    system.adminMenu();
                    break;
                case 2:
                    Customer customer = system.customerLogin();
                    if (customer != null) {
                        system.customerMenu(customer);
                    }
                    break;
                case 0:
                    System.out.println("Exiting ByteMe System.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
