package foodorder.management;

public class Foodisgood {
	package com.foodorder.management;

	import java.awt.*;
	import java.awt.event.*;
	import java.sql.*;

	public class FoodOrderAWT extends Frame implements ActionListener {

	    // ---------------- AWT COMPONENTS ----------------

	    TextField nameField, phoneField, emailField, addressField;
	    TextField foodIdField, quantityField, customerIdField;
	    TextField orderIdField, categoryField;

	    TextArea output;

	    Button registerBtn;
	    Button viewFoodBtn;
	    Button searchFoodBtn;
	    Button placeOrderBtn;
	    Button viewOrdersBtn;
	    Button cancelOrderBtn;
	    Button updateStatusBtn;
	    Button viewDetailsBtn;

	    // ---------------- DATABASE ----------------

	    static final String URL =
	            "jdbc:mysql://localhost:3306/food_order_db";

	    static final String USER = "root";

	    // Change this if your MySQL password is different
	    static final String PASSWORD = "root";


	    // ---------------- CONSTRUCTOR ----------------

	    FoodOrderAWT() {

	        setTitle("Online Food Order Management System");
	        setSize(850, 700);
	        setLayout(null);
	        setBackground(Color.LIGHT_GRAY);

	        // ---------------- TITLE ----------------

	        Label title = new Label(
	                "ONLINE FOOD ORDER MANAGEMENT SYSTEM",
	                Label.CENTER);

	        title.setBounds(150, 40, 550, 35);
	        title.setFont(new Font("Arial", Font.BOLD, 20));

	        add(title);


	        // =====================================================
	        // CUSTOMER DETAILS
	        // =====================================================

	        Label customerTitle =
	                new Label("CUSTOMER DETAILS");

	        customerTitle.setBounds(50, 90, 200, 25);
	        customerTitle.setFont(
	                new Font("Arial", Font.BOLD, 15));

	        add(customerTitle);


	        Label nameLabel = new Label("Name:");
	        nameLabel.setBounds(50, 130, 100, 25);
	        add(nameLabel);

	        nameField = new TextField();
	        nameField.setBounds(150, 130, 220, 25);
	        add(nameField);


	        Label phoneLabel = new Label("Phone:");
	        phoneLabel.setBounds(50, 165, 100, 25);
	        add(phoneLabel);

	        phoneField = new TextField();
	        phoneField.setBounds(150, 165, 220, 25);
	        add(phoneField);


	        Label emailLabel = new Label("Email:");
	        emailLabel.setBounds(50, 200, 100, 25);
	        add(emailLabel);

	        emailField = new TextField();
	        emailField.setBounds(150, 200, 220, 25);
	        add(emailField);


	        Label addressLabel = new Label("Address:");
	        addressLabel.setBounds(50, 235, 100, 25);
	        add(addressLabel);

	        addressField = new TextField();
	        addressField.setBounds(150, 235, 220, 25);
	        add(addressField);


	        registerBtn =
	                new Button("Register Customer");

	        registerBtn.setBounds(150, 275, 220, 30);
	        registerBtn.addActionListener(this);

	        add(registerBtn);


	        // =====================================================
	        // ORDER DETAILS
	        // =====================================================

	        Label orderTitle =
	                new Label("ORDER DETAILS");

	        orderTitle.setBounds(450, 90, 200, 25);
	        orderTitle.setFont(
	                new Font("Arial", Font.BOLD, 15));

	        add(orderTitle);


	        Label customerIdLabel =
	                new Label("Customer ID:");

	        customerIdLabel.setBounds(450, 130, 100, 25);
	        add(customerIdLabel);

	        customerIdField = new TextField();
	        customerIdField.setBounds(570, 130, 180, 25);
	        add(customerIdField);


	        Label foodIdLabel =
	                new Label("Food ID:");

	        foodIdLabel.setBounds(450, 165, 100, 25);
	        add(foodIdLabel);

	        foodIdField = new TextField();
	        foodIdField.setBounds(570, 165, 180, 25);
	        add(foodIdField);


	        Label quantityLabel =
	                new Label("Quantity:");

	        quantityLabel.setBounds(450, 200, 100, 25);
	        add(quantityLabel);

	        quantityField = new TextField();
	        quantityField.setBounds(570, 200, 180, 25);
	        add(quantityField);


	        Label orderIdLabel =
	                new Label("Order ID:");

	        orderIdLabel.setBounds(450, 235, 100, 25);
	        add(orderIdLabel);

	        orderIdField = new TextField();
	        orderIdField.setBounds(570, 235, 180, 25);
	        add(orderIdField);


	        placeOrderBtn =
	                new Button("Place Order");

	        placeOrderBtn.setBounds(570, 275, 180, 30);
	        placeOrderBtn.addActionListener(this);

	        add(placeOrderBtn);


	        // =====================================================
	        // FOOD SEARCH
	        // =====================================================

	        Label categoryLabel =
	                new Label("Category:");

	        categoryLabel.setBounds(50, 330, 80, 25);
	        add(categoryLabel);

	        categoryField = new TextField();
	        categoryField.setBounds(130, 330, 150, 25);
	        add(categoryField);


	        searchFoodBtn =
	                new Button("Search Food");

	        searchFoodBtn.setBounds(290, 330, 120, 25);
	        searchFoodBtn.addActionListener(this);

	        add(searchFoodBtn);


	        viewFoodBtn =
	                new Button("View Food");

	        viewFoodBtn.setBounds(425, 330, 120, 25);
	        viewFoodBtn.addActionListener(this);

	        add(viewFoodBtn);


	        // =====================================================
	        // OTHER BUTTONS
	        // =====================================================

	        viewOrdersBtn =
	                new Button("View Orders");

	        viewOrdersBtn.setBounds(50, 375, 120, 30);
	        viewOrdersBtn.addActionListener(this);

	        add(viewOrdersBtn);


	        cancelOrderBtn =
	                new Button("Cancel Order");

	        cancelOrderBtn.setBounds(185, 375, 120, 30);
	        cancelOrderBtn.addActionListener(this);

	        add(cancelOrderBtn);


	        updateStatusBtn =
	                new Button("Update Status");

	        updateStatusBtn.setBounds(320, 375, 120, 30);
	        updateStatusBtn.addActionListener(this);

	        add(updateStatusBtn);


	        viewDetailsBtn =
	                new Button("Order Details");

	        viewDetailsBtn.setBounds(455, 375, 120, 30);
	        viewDetailsBtn.addActionListener(this);

	        add(viewDetailsBtn);


	        // =====================================================
	        // OUTPUT
	        // =====================================================

	        Label outputLabel =
	                new Label("OUTPUT");

	        outputLabel.setBounds(50, 425, 100, 25);
	        outputLabel.setFont(
	                new Font("Arial", Font.BOLD, 15));

	        add(outputLabel);


	        output = new TextArea();

	        output.setBounds(50, 455, 700, 170);
	        output.setEditable(false);

	        add(output);


	        // =====================================================
	        // WINDOW CLOSING
	        // =====================================================

	        addWindowListener(new WindowAdapter() {

	            public void windowClosing(WindowEvent e) {
	                dispose();
	                System.exit(0);
	            }
	        });

	        setVisible(true);
	    }


	    // =========================================================
	    // DATABASE CONNECTION
	    // =========================================================

	    Connection getConnection() throws SQLException {

	        return DriverManager.getConnection(
	                URL,
	                USER,
	                PASSWORD);
	    }


	    // =========================================================
	    // REGISTER CUSTOMER
	    // =========================================================

	    void registerCustomer() {

	        String sql =
	                "INSERT INTO customers " +
	                "(name, phone, email, address) " +
	                "VALUES (?, ?, ?, ?)";

	        try (Connection con = getConnection();
	             PreparedStatement ps =
	                     con.prepareStatement(sql)) {

	            ps.setString(1, nameField.getText());
	            ps.setString(2, phoneField.getText());
	            ps.setString(3, emailField.getText());
	            ps.setString(4, addressField.getText());

	            ps.executeUpdate();

	            output.setText(
	                    "Customer registered successfully!");

	        } catch (SQLException e) {

	            output.setText(
	                    "Error: " + e.getMessage());
	        }
	    }


	    // =========================================================
	    // VIEW AVAILABLE FOOD
	    // =========================================================

	    void viewFood() {

	        String sql =
	                "SELECT * FROM food_items " +
	                "WHERE availability = TRUE";

	        try (Connection con = getConnection();
	             Statement st = con.createStatement();
	             ResultSet rs = st.executeQuery(sql)) {

	            output.setText(
	                    "========== AVAILABLE FOOD ==========\n\n");

	            boolean found = false;

	            while (rs.next()) {

	                found = true;

	                output.append(
	                        "Food ID: " +
	                        rs.getInt("food_id") +

	                        " | Name: " +
	                        rs.getString("food_name") +

	                        " | Category: " +
	                        rs.getString("category") +

	                        " | Price: ₹" +
	                        rs.getDouble("price") +

	                        "\n");
	            }

	            if (!found) {
	                output.append("No food items available.");
	            }

	        } catch (SQLException e) {

	            output.setText(
	                    "Error: " + e.getMessage());
	        }
	    }


	    // =========================================================
	    // SEARCH FOOD BY CATEGORY
	    // =========================================================

	    void searchFood() {

	        String sql =
	                "SELECT * FROM food_items " +
	                "WHERE category = ? " +
	                "AND availability = TRUE";

	        try (Connection con = getConnection();
	             PreparedStatement ps =
	                     con.prepareStatement(sql)) {

	            ps.setString(
	                    1,
	                    categoryField.getText());

	            ResultSet rs =
	                    ps.executeQuery();

	            output.setText(
	                    "========== SEARCH RESULTS ==========\n\n");

	            boolean found = false;

	            while (rs.next()) {

	                found = true;

	                output.append(
	                        "Food ID: " +
	                        rs.getInt("food_id") +

	                        " | Name: " +
	                        rs.getString("food_name") +

	                        " | Price: ₹" +
	                        rs.getDouble("price") +

	                        "\n");
	            }

	            if (!found) {

	                output.append(
	                        "No food found in this category.");
	            }

	        } catch (SQLException e) {

	            output.setText(
	                    "Error: " + e.getMessage());
	        }
	    }


	    // =========================================================
	    // PLACE ORDER
	    // =========================================================

	    void placeOrder() {

	        Connection con = null;

	        try {

	            int customerId =
	                    Integer.parseInt(
	                            customerIdField.getText());

	            int foodId =
	                    Integer.parseInt(
	                            foodIdField.getText());

	            int quantity =
	                    Integer.parseInt(
	                            quantityField.getText());

	            if (quantity <= 0) {

	                output.setText(
	                        "Quantity must be greater than 0.");

	                return;
	            }

	            con = getConnection();


	            // Get food price

	            String foodSQL =
	                    "SELECT price FROM food_items " +
	                    "WHERE food_id = ? " +
	                    "AND availability = TRUE";

	            PreparedStatement foodPS =
	                    con.prepareStatement(foodSQL);

	            foodPS.setInt(1, foodId);

	            ResultSet rs =
	                    foodPS.executeQuery();


	            if (!rs.next()) {

	                output.setText(
	                        "Food item not available.");

	                return;
	            }


	            double price =
	                    rs.getDouble("price");

	            double total =
	                    price * quantity;


	            // Insert order

	            String orderSQL =
	                    "INSERT INTO orders " +
	                    "(customer_id, total_amount, status) " +
	                    "VALUES (?, ?, ?)";

	            PreparedStatement orderPS =
	                    con.prepareStatement(
	                            orderSQL,
	                            Statement.RETURN_GENERATED_KEYS);

	            orderPS.setInt(1, customerId);
	            orderPS.setDouble(2, total);
	            orderPS.setString(3, "Placed");

	            orderPS.executeUpdate();


	            ResultSet keys =
	                    orderPS.getGeneratedKeys();

	            int orderId = 0;

	            if (keys.next()) {

	                orderId =
	                        keys.getInt(1);
	            }


	            // Insert order item

	            String itemSQL =
	                    "INSERT INTO order_items " +
	                    "(order_id, food_id, quantity, subtotal) " +
	                    "VALUES (?, ?, ?, ?)";

	            PreparedStatement itemPS =
	                    con.prepareStatement(itemSQL);

	            itemPS.setInt(1, orderId);
	            itemPS.setInt(2, foodId);
	            itemPS.setInt(3, quantity);
	            itemPS.setDouble(4, total);

	            itemPS.executeUpdate();


	            output.setText(
	                    "========== ORDER PLACED ==========\n\n" +

	                    "Order ID: " +
	                    orderId +

	                    "\nFood ID: " +
	                    foodId +

	                    "\nQuantity: " +
	                    quantity +

	                    "\nPrice: ₹" +
	                    price +

	                    "\nTotal Amount: ₹" +
	                    total +

	                    "\nStatus: Placed");

	        } catch (NumberFormatException e) {

	            output.setText(
	                    "Please enter valid numeric values.");

	        } catch (SQLException e) {

	            output.setText(
	                    "Database Error: " +
	                    e.getMessage());

	        } finally {

	            try {

	                if (con != null)
	                    con.close();

	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }


	    // =========================================================
	    // VIEW CUSTOMER ORDERS
	    // =========================================================

	    void viewOrders() {

	        String sql =
	                "SELECT * FROM orders " +
	                "WHERE customer_id = ?";

	        try (Connection con = getConnection();
	             PreparedStatement ps =
	                     con.prepareStatement(sql)) {

	            int customerId =
	                    Integer.parseInt(
	                            customerIdField.getText());

	            ps.setInt(1, customerId);

	            ResultSet rs =
	                    ps.executeQuery();

	            output.setText(
	                    "========== CUSTOMER ORDERS ==========\n\n");

	            boolean found = false;

	            while (rs.next()) {

	                found = true;

	                output.append(
	                        "Order ID: " +
	                        rs.getInt("order_id") +

	                        " | Date: " +
	                        rs.getTimestamp("order_date") +

	                        " | Amount: ₹" +
	                        rs.getDouble("total_amount") +

	                        " | Status: " +
	                        rs.getString("status") +

	                        "\n");
	            }

	            if (!found) {
	                output.append("No orders found.");
	            }

	        } catch (NumberFormatException e) {

	            output.setText(
	                    "Enter a valid Customer ID.");

	        } catch (SQLException e) {

	            output.setText(
	                    "Error: " + e.getMessage());
	        }
	    }


	    // =========================================================
	    // CANCEL ORDER
	    // =========================================================

	    void cancelOrder() {

	        String sql =
	                "UPDATE orders " +
	                "SET status = 'Cancelled' " +
	                "WHERE order_id = ?";

	        try (Connection con = getConnection();
	             PreparedStatement ps =
	                     con.prepareStatement(sql)) {

	            int orderId =
	                    Integer.parseInt(
	                            orderIdField.getText());

	            ps.setInt(1, orderId);

	            int rows =
	                    ps.executeUpdate();

	            if (rows > 0) {

	                output.setText(
	                        "Order cancelled successfully!");

	            } else {

	                output.setText(
	                        "Order not found.");
	            }

	        } catch (NumberFormatException e) {

	            output.setText(
	                    "Enter a valid Order ID.");

	        } catch (SQLException e) {

	            output.setText(
	                    "Error: " + e.getMessage());
	        }
	    }


	    // =========================================================
	    // UPDATE ORDER STATUS
	    // =========================================================

	    void updateStatus() {

	        final Dialog dialog =
	                new Dialog(
	                        this,
	                        "Update Order Status",
	                        true);

	        dialog.setSize(320, 160);
	        dialog.setLayout(new FlowLayout());


	        Label label =
	                new Label("Select Status:");


	        Choice choice =
	                new Choice();

	        choice.add("Placed");
	        choice.add("Preparing");
	        choice.add("Out for Delivery");
	        choice.add("Delivered");
	        choice.add("Cancelled");


	        Button update =
	                new Button("Update");


	        dialog.add(label);
	        dialog.add(choice);
	        dialog.add(update);


	        update.addActionListener(
	                new ActionListener() {

	                    public void actionPerformed(
	                            ActionEvent e) {

	                        String sql =
	                                "UPDATE orders " +
	                                "SET status = ? " +
	                                "WHERE order_id = ?";

	                        try (Connection con =
	                                     getConnection();
	                             PreparedStatement ps =
	                                     con.prepareStatement(sql)) {

	                            int orderId =
	                                    Integer.parseInt(
	                                            orderIdField.getText());


	                            ps.setString(
	                                    1,
	                                    choice.getSelectedItem());

	                            ps.setInt(2, orderId);


	                            int rows =
	                                    ps.executeUpdate();


	                            if (rows > 0) {

	                                output.setText(
	                                        "Order status updated to: " +
	                                        choice.getS
}
