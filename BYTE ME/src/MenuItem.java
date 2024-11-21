import java.util.ArrayList;
import java.util.List;

class MenuItem {
    private String name;
    private double price;
    private String category;
    private boolean available;
    private List<String> reviews;

    // Constructor
    public MenuItem(String name, double price, String category, boolean available) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.available = available;
        this.reviews = new ArrayList<>();
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void addReview(String review) {
        if (review != null && !review.trim().isEmpty()) {
            reviews.add(review);
            System.out.println("Review added: " + review);
        } else {
            System.out.println("Invalid review. Please provide a non-empty review.");
        }
    }

    public List<String> getReviews() {
        return new ArrayList<>(reviews);
    }

    public void displayReviews() {
        if (reviews.isEmpty()) {
            System.out.println("No reviews yet for " + name);
        } else {
            System.out.println("Reviews for " + name + ":");
            for (String review : reviews) {
                System.out.println("- " + review);
            }
        }
    }
    @Override
    public String toString() {
        return String.format("%s - $%.2f (%s) Available: %s", name, price, category, available ? "Yes" : "No");
    }
}
