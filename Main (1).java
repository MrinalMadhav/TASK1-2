import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Product {
    private String productId;
    private String productName;
    private double price;
    private String manufacturingDate;
    private int quantity;

    public Product(String productId, String productName, double price, String manufacturingDate, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.manufacturingDate = manufacturingDate;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public String getManufacturingDate() {
        return manufacturingDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

class InventoryManager {
    private List<Product> inventory;

    public InventoryManager() {
        inventory = new ArrayList<>();
    }

    public void addProduct(String productId, String productName, double price, String manufacturingDate, int quantity) {
        // Check if the product ID is unique
        boolean uniqueProductId = inventory.stream().noneMatch(p -> p.getProductId().equals(productId));

        if (uniqueProductId) {
            Product newProduct = new Product(productId, productName, price, manufacturingDate, quantity);
            inventory.add(newProduct);

            // Check if the quantity has reached 5
            if (quantity <= 5) {
                sendAlertToManager();
            }
        } else {
            System.out.println("Product ID already exists.");
        }
    }

    public Product searchProduct(String productId) {
        return inventory.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public void displayAllProducts() {
        System.out.println("All Products:");
        for (Product product : inventory) {
            System.out.println("Product ID: " + product.getProductId() +
                    ", Product Name: " + product.getProductName() +
                    ", Price: " + product.getPrice() +
                    ", Manufacturing Date: " + product.getManufacturingDate() +
                    ", Quantity: " + product.getQuantity());
        }
    }

    public void removeDefectiveProducts() {
        inventory.removeIf(product -> product.getQuantity() <= 0);
    }

    private void sendAlertToManager() {
        System.out.println("Alert: Product quantity has reached 5 or below. Please restock!");
    }
}

public class Main {
    public static void main(String[] args) {
        InventoryManager inventoryManager = new InventoryManager();
        Scanner sc = new Scanner(System.in);

        for (int i = 1; i <= 5; i++) {
            System.out.println("Enter details for product " + i + ":");
            String id = "P" + i;

            System.out.print("Product Name: ");
            String name = sc.nextLine();

            System.out.print("Price: ");
            double price = sc.nextDouble();
            sc.nextLine(); // Consume the newline character

            System.out.print("Manufacturing Date: ");
            String manufacturingDate = sc.nextLine();

            System.out.print("Quantity: ");
            int quantity = sc.nextInt();
            sc.nextLine(); // Consume the newline character

            inventoryManager.addProduct(id, name, price, manufacturingDate, quantity);
        }

        inventoryManager.displayAllProducts();

        System.out.println("Enter the search product ID:");
        String searchID = sc.nextLine();
        Product searchResult = inventoryManager.searchProduct(searchID);
        if (searchResult != null) {
            System.out.println("Found Product: " + searchResult.getProductId());
        } else {
            System.out.println("Product not found!");
        }

        System.out.println("Removing defective products");
        inventoryManager.removeDefectiveProducts();

        inventoryManager.displayAllProducts();

        sc.close();
    }
}
