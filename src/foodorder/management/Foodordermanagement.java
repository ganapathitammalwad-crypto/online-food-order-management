package foodorder.management;

	import java.sql.*;
	import java.util.Scanner;

	public class Foodordermanagement {

	    static final String URL = "jdbc:mysql://localhost:3306/food_order_db";
	    static final String USER = "root";
	    static final String PASSWORD = "root";   // Change this to your MySQL password

	    static Scanner sc = new Scanner(System.in);

	    // Database Connection
	    public static Connection getConnection() throws SQLException {
	        return DriverManager.getConnection(URL, USER, PASSWORD);
	    }

	    // 1. Register Customer
	    static void registerCustomer() {

	        try (Connection con = getConnection()) {

	            System.out.print("Enter customer name: ");
	            String name = sc.nextLine();

	            System.out.print("Enter phone: ");
	            String phone = sc.nextLine();

	            System.out.print("Enter email: ");
	            String email = sc.nextLine();

	            System.out.print("Enter address: ");
	            String address = sc.nextLine();

	            String sql = "INSERT INTO customers(name, phone, email, address) "
	                       + "VALUES (?, ?, ?, ?)";

	            PreparedStatement ps = con.prepareStatement(sql);

	            ps.setString(1, name);
	            ps.setString(2, phone);
	            ps.setString(3, email);
	            ps.setString(4, address);

	            ps.executeUpdate();

	            System.out.println("Customer registered successfully!");

	        } catch (SQLException e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }

	    // 2. View Customers
	    static void viewCustomers() {

	        String sql = "SELECT * FROM customers";

	        try (Connection con = getConnection();
	             Statement st = con.createStatement();
	             ResultSet rs = st.executeQuery(sql)) {

	            System.out.println("\n--- CUSTOMER DETAILS ---");

	            while (rs.next()) {
	                System.out.println(
	                    "ID: " + rs.getInt("customer_id") +
	                    " | Name: " + rs.getString("name") +
	                    " | Phone: " + rs.getString("phone") +
	                    " | Email: " + rs.getString("email") +
	                    " | Address: " + rs.getString("address")
	                );
	            }

	        } catch (SQLException e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }

	    // 3. Add Food Item
	    static void addFoodItem() {

	        try (Connection con = getConnection()) {

	            System.out.print("Enter food name: ");
	            String name = sc.nextLine();

	            System.out.print("Enter category: ");
	            String category = sc.nextLine();

	            System.out.print("Enter price: ");
	            double price = sc.nextDouble();

	            sc.nextLine();

	            String sql = "INSERT INTO food_items "
	                       + "(food_name, category, price, availability) "
	                       + "VALUES (?, ?, ?, TRUE)";

	            PreparedStatement ps = con.prepareStatement(sql);

	            ps.setString(1, name);
	            ps.setString(2, category);
	            ps.setDouble(3, price);

	            ps.executeUpdate();

	            System.out.println("Food item added successfully!");

	        } catch (SQLException e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }

	    // 4. View Available Food
	    static void viewFoodItems() {

	        String sql = "SELECT * FROM food_items WHERE availability = TRUE";

	        try (Connection con = getConnection();
	             Statement st = con.createStatement();
	             ResultSet rs = st.executeQuery(sql)) {

	            System.out.println("\n--- AVAILABLE FOOD ITEMS ---");

	            while (rs.next()) {

	                System.out.println(
	                    "Food ID: " + rs.getInt("food_id") +
	                    " | Name: " + rs.getString("food_name") +
	                    " | Category: " + rs.getString("category") +
	                    " | Price: ₹" + rs.getDouble("price")
	                );
	            }

	        } catch (SQLException e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }

	    // 5. Search Food by Category
	    static void searchFood() {

	        try (Connection con = getConnection()) {

	            System.out.print("Enter category: ");
	            String category = sc.nextLine();

	            String sql = "SELECT * FROM food_items "
	                       + "WHERE category = ? AND availability = TRUE";

	            PreparedStatement ps = con.prepareStatement(sql);

	            ps.setString(1, category);

	            ResultSet rs = ps.executeQuery();

	            System.out.println("\n--- SEARCH RESULTS ---");

	            while (rs.next()) {

	                System.out.println(
	                    "ID: " + rs.getInt("food_id") +
	                    " | " + rs.getString("food_name") +
	                    " | ₹" + rs.getDouble("price")
	                );
	            }

	        } catch (SQLException e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }

	    // 6. Place Order
	    static void placeOrder() {

	        Connection con = null;

	        try {

	            con = getConnection();

	            System.out.print("Enter Customer ID: ");
	            int customerId = sc.nextInt();

	            viewFoodItems();

	            System.out.print("\nEnter Food ID: ");
	            int foodId = sc.nextInt();

	            System.out.print("Enter Quantity: ");
	            int quantity = sc.nextInt();

	            // Get food price
	            String foodSQL =
	                    "SELECT price FROM food_items "
	                  + "WHERE food_id = ? AND availability = TRUE";

	            PreparedStatement foodPS =
	                    con.prepareStatement(foodSQL);

	            foodPS.setInt(1, foodId);

	            ResultSet rs = foodPS.executeQuery();

	            if (!rs.next()) {
	                System.out.println("Food item not available.");
	                return;
	            }

	            double price = rs.getDouble("price");
	            double subtotal = price * quantity;

	            // Insert Order
	            String orderSQL =
	                    "INSERT INTO orders(customer_id, total_amount, status) "
	                  + "VALUES (?, ?, ?)";

	            PreparedStatement orderPS =
	                    con.prepareStatement(
	                        orderSQL,
	                        Statement.RETURN_GENERATED_KEYS
	                    );

	            orderPS.setInt(1, customerId);
	            orderPS.setDouble(2, subtotal);
	            orderPS.setString(3, "Placed");

	            orderPS.executeUpdate();

	            ResultSet generatedKeys =
	                    orderPS.getGeneratedKeys();

	            int orderId = 0;

	            if (generatedKeys.next()) {
	                orderId = generatedKeys.getInt(1);
	            }

	            // Insert Order Item
	            String itemSQL =
	                    "INSERT INTO order_items "
	                  + "(order_id, food_id, quantity, subtotal) "
	                  + "VALUES (?, ?, ?, ?)";

	            PreparedStatement itemPS =
	                    con.prepareStatement(itemSQL);

	            itemPS.setInt(1, orderId);
	            itemPS.setInt(2, foodId);
	            itemPS.setInt(3, quantity);
	            itemPS.setDouble(4, subtotal);

	            itemPS.executeUpdate();

	            System.out.println("\nOrder placed successfully!");
	            System.out.println("Order ID: " + orderId);
	            System.out.println("Total Amount: ₹" + subtotal);

	        } catch (SQLException e) {
	            System.out.println("Error: " + e.getMessage());

	        } finally {

	            try {
	                if (con != null)
	                    con.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    // 7. View Customer Orders
	    static void viewOrders() {

	        try (Connection con = getConnection()) {

	            System.out.print("Enter Customer ID: ");
	            int customerId = sc.nextInt();

	            String sql =
	                    "SELECT * FROM orders WHERE customer_id = ?";

	            PreparedStatement ps =
	                    con.prepareStatement(sql);

	            ps.setInt(1, customerId);

	            ResultSet rs = ps.executeQuery();

	            System.out.println("\n--- CUSTOMER ORDERS ---");

	            while (rs.next()) {

	                System.out.println(
	                    "Order ID: " + rs.getInt("order_id") +
	                    " | Date: " + rs.getTimestamp("order_date") +
	                    " | Amount: ₹" + rs.getDouble("total_amount") +
	                    " | Status: " + rs.getString("status")
	                );
	            }

	        } catch (SQLException e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }

	    // 8. Update Order Status
	    static void updateOrderStatus() {

	        try (Connection con = getConnection()) {

	            System.out.print("Enter Order ID: ");
	            int orderId = sc.nextInt();

	            sc.nextLine();

	            System.out.println(
	                "1. Placed\n" +
	                "2. Preparing\n" +
	                "3. Out for Delivery\n" +
	                "4. Delivered"
	            );

	            System.out.print("Choose status: ");
	            int choice = sc.nextInt();

	            String status = "";

	            switch (choice) {

	                case 1:
	                    status = "Placed";
	                    break;

	                case 2:
	                    status = "Preparing";
	                    break;

	                case 3:
	                    status = "Out for Delivery";
	                    break;

	                case 4:
	                    status = "Delivered";
	                    break;

	                default:
	                    System.out.println("Invalid choice.");
	                    return;
	            }

	            String sql =
	                    "UPDATE orders SET status = ? "
	                  + "WHERE order_id = ?";

	            PreparedStatement ps =
	                    con.prepareStatement(sql);

	            ps.setString(1, status);
	            ps.setInt(2, orderId);

	            int rows = ps.executeUpdate();

	            if (rows > 0)
	                System.out.println("Order status updated!");
	            else
	                System.out.println("Order not found.");

	        } catch (SQLException e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }

	    // 9. Cancel Order
	    static void cancelOrder() {

	        try (Connection con = getConnection()) {

	            System.out.print("Enter Order ID: ");
	            int orderId = sc.nextInt();

	            String sql =
	                    "UPDATE orders SET status = 'Cancelled' "
	                  + "WHERE order_id = ?";

	            PreparedStatement ps =
	                    con.prepareStatement(sql);

	            ps.setInt(1, orderId);

	            int rows = ps.executeUpdate();

	            if (rows > 0)
	                System.out.println("Order cancelled successfully!");
	            else
	                System.out.println("Order not found.");

	        } catch (SQLException e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }

	    // 10. View Order Details
	    static void viewOrderDetails() {

	        try (Connection con = getConnection()) {

	            System.out.print("Enter Order ID: ");
	            int orderId = sc.nextInt();

	            String sql =
	                    "SELECT o.order_id, c.name, f.food_name, "
	                  + "oi.quantity, oi.subtotal, o.total_amount, "
	                  + "o.status "
	                  + "FROM orders o "
	                  + "JOIN customers c ON o.customer_id = c.customer_id "
	                  + "JOIN order_items oi ON o.order_id = oi.order_id "
	                  + "JOIN food_items f ON oi.food_id = f.food_id "
	                  + "WHERE o.order_id = ?";

	            PreparedStatement ps =
	                    con.prepareStatement(sql);

	            ps.setInt(1, orderId);

	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {

	                System.out.println("\n--- ORDER DETAILS ---");

	                System.out.println(
	                    "Order ID: " + rs.getInt("order_id")
	                );

	                System.out.println(
	                    "Customer: " + rs.getString("name")
	                );

	                System.out.println(
	                    "Food: " + rs.getString("food_name")
	                );

	                System.out.println(
	                    "Quantity: " + rs.getInt("quantity")
	                );

	                System.out.println(
	                    "Subtotal: ₹" + rs.getDouble("subtotal")
	                );

	                System.out.println(
	                    "Total: ₹" + rs.getDouble("total_amount")
	                );

	                System.out.println(
	                    "Status: " + rs.getString("status")
	                );

	            } else {
	                System.out.println("Order not found.");
	            }

	        } catch (SQLException e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }

	    // Main Menu
	    public static void main(String[] args) {

	        while (true) {

	            System.out.println("\n================================");
	            System.out.println(" ONLINE FOOD ORDER MANAGEMENT");
	            System.out.println("================================");

	            System.out.println("1. Register Customer");
	            System.out.println("2. View Customers");
	            System.out.println("3. Add Food Item");
	            System.out.println("4. View Available Food");
	            System.out.println("5. Search Food by Category");
	            System.out.println("6. Place Food Order");
	            System.out.println("7. View Customer Orders");
	            System.out.println("8. Update Order Status");
	            System.out.println("9. Cancel Order");
	            System.out.println("10. View Order Details");
	            System.out.println("11. Exit");

	            System.out.print("\nEnter your choice: ");

	            int choice = sc.nextInt();
	            sc.nextLine();

	            switch (choice) {

	                case 1:
	                    registerCustomer();
	                    break;

	                case 2:
	                    viewCustomers();
	                    break;

	                case 3:
	                    addFoodItem();
	                    break;

	                case 4:
	                    viewFoodItems();
	                    break;

	                case 5:
	                    searchFood();
	                    break;

	                case 6:
	                    placeOrder();
	                    break;

	                case 7:
	                    viewOrders();
	                    break;

	                case 8:
	                    updateOrderStatus();
	                    break;

	                case 9:
	                    cancelOrder();
	                    break;

	                case 10:
	                    viewOrderDetails();
	                    break;

	                case 11:
	                    System.out.println("Thank you!");
	                    System.exit(0);

	                default:
	                    System.out.println("Invalid choice.");
	            }
	        }
	    }
	}

