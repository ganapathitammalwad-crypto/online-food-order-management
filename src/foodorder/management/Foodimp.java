package foodorder.management;
	import java.awt.*;
	import java.awt.event.*;
	import java.sql.*;

	public class Foodimp extends Frame implements ActionListener {

	    TextField nameField, phoneField, emailField, addressField;
	    TextField foodIdField, quantityField, customerIdField, orderIdField;
	    TextArea output;

	    Button registerBtn, viewFoodBtn, placeOrderBtn;
	    Button viewOrdersBtn, cancelOrderBtn, updateStatusBtn;

	    static final String URL =
	            "jdbc:mysql://localhost:3306/food_order_db";

	    static final String USER = "root";
	    static final String PASSWORD = "root";

	    Foodimp() {

	        setTitle("Online Food Order Management System");
	        setSize(750, 650);
	        setLayout(null);
	        setBackground(Color.LIGHT_GRAY);

	        Label title = new Label(
	                "ONLINE FOOD ORDER MANAGEMENT SYSTEM",
	                Label.CENTER);
	        title.setBounds(150, 40, 450, 30);
	        title.setFont(new Font("Arial", Font.BOLD, 18));
	        add(title);

	        // Customer Details
	        Label name = new Label("Name:");
	        name.setBounds(50, 100, 100, 25);
	        add(name);

	        nameField = new TextField();
	        nameField.setBounds(160, 100, 200, 25);
	        add(nameField);

	        Label phone = new Label("Phone:");
	        phone.setBounds(50, 140, 100, 25);
	        add(phone);

	        phoneField = new TextField();
	        phoneField.setBounds(160, 140, 200, 25);
	        add(phoneField);

	        Label email = new Label("Email:");
	        email.setBounds(50, 180, 100, 25);
	        add(email);

	        emailField = new TextField();
	        emailField.setBounds(160, 180, 200, 25);
	        add(emailField);

	        Label address = new Label("Address:");
	        address.setBounds(50, 220, 100, 25);
	        add(address);

	        addressField = new TextField();
	        addressField.setBounds(160, 220, 200, 25);
	        add(addressField);

	        registerBtn = new Button("Register Customer");
	        registerBtn.setBounds(160, 260, 200, 30);
	        registerBtn.addActionListener(this);
	        add(registerBtn);

	        // Order Details
	        Label customerId = new Label("Customer ID:");
	        customerId.setBounds(420, 100, 100, 25);
	        add(customerId);

	        customerIdField = new TextField();
	        customerIdField.setBounds(530, 100, 150, 25);
	        add(customerIdField);

	        Label foodId = new Label("Food ID:");
	        foodId.setBounds(420, 140, 100, 25);
	        add(foodId);

	        foodIdField = new TextField();
	        foodIdField.setBounds(530, 140, 150, 25);
	        add(foodIdField);

	        Label quantity = new Label("Quantity:");
	        quantity.setBounds(420, 180, 100, 25);
	        add(quantity);

	        quantityField = new TextField();
	        quantityField.setBounds(530, 180, 150, 25);
	        add(quantityField);

	        placeOrderBtn = new Button("Place Order");
	        placeOrderBtn.setBounds(530, 220, 150, 30);
	        placeOrderBtn.addActionListener(this);
	        add(placeOrderBtn);

	        viewFoodBtn = new Button("View Food");
	        viewFoodBtn.setBounds(50, 320, 130, 30);
	        viewFoodBtn.addActionListener(this);
	        add(viewFoodBtn);

	        viewOrdersBtn = new Button("View Orders");
	        viewOrdersBtn.setBounds(200, 320, 130, 30);
	        viewOrdersBtn.addActionListener(this);
	        add(viewOrdersBtn);

	        Label orderId = new Label("Order ID:");
	        orderId.setBounds(350, 320, 80, 30);
	        add(orderId);

	        orderIdField = new TextField();
	        orderIdField.setBounds(430, 320, 100, 30);
	        add(orderIdField);

	        cancelOrderBtn = new Button("Cancel Order");
	        cancelOrderBtn.setBounds(540, 320, 140, 30);
	        cancelOrderBtn.addActionListener(this);
	        add(cancelOrderBtn);

	        updateStatusBtn = new Button("Update Status");
	        updateStatusBtn.setBounds(50, 370, 150, 30);
	        updateStatusBtn.addActionListener(this);
	        add(updateStatusBtn);

	        output = new TextArea();
	        output.setBounds(50, 420, 630, 160);
	        output.setEditable(false);
	        add(output);

	        addWindowListener(new WindowAdapter() {
	            public void windowClosing(WindowEvent e) {
	                dispose();
	            }
	        });

	        setVisible(true);
	    }

	    // Database connection
	    Connection getConnection() throws SQLException {
	        return DriverManager.getConnection(
	                URL, USER, PASSWORD);
	    }

	    // Register customer
	    void registerCustomer() {

	        String sql =
	                "INSERT INTO customers(name, phone, email, address) " +
	                "VALUES (?, ?, ?, ?)";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setString(1, nameField.getText());
	            ps.setString(2, phoneField.getText());
	            ps.setString(3, emailField.getText());
	            ps.setString(4, addressField.getText());

	            ps.executeUpdate();

	            output.setText(
	                    "Customer registered successfully!");

	        } catch (SQLException e) {
	            output.setText("Error: " + e.getMessage());
	        }
	    }

	    // Display food items
	    void viewFood() {

	        String sql =
	                "SELECT * FROM food_items " +
	                "WHERE availability = TRUE";

	        try (Connection con = getConnection();
	             Statement st = con.createStatement();
	             ResultSet rs = st.executeQuery(sql)) {

	            output.setText(
	                    "========== AVAILABLE FOOD ==========\n");

	            while (rs.next()) {

	                output.append(
	                        "Food ID: " +
	                        rs.getInt("food_id") +
	                        " | " +
	                        rs.getString("food_name") +
	                        " | Category: " +
	                        rs.getString("category") +
	                        " | Price: ₹" +
	                        rs.getDouble("price") +
	                        "\n");
	            }

	        } catch (SQLException e) {
	            output.setText("Error: " + e.getMessage());
	        }
	    }

	    // Place order
	    void placeOrder() {

	        Connection con = null;

	        try {

	            con = getConnection();

	            int customerId =
	                    Integer.parseInt(customerIdField.getText());

	            int foodId =
	                    Integer.parseInt(foodIdField.getText());

	            int quantity =
	                    Integer.parseInt(quantityField.getText());

	            String foodSQL =
	                    "SELECT price FROM food_items " +
	                    "WHERE food_id = ? AND availability = TRUE";

	            PreparedStatement foodPS =
	                    con.prepareStatement(foodSQL);

	            foodPS.setInt(1, foodId);

	            ResultSet rs = foodPS.executeQuery();

	            if (!rs.next()) {
	                output.setText("Food item not available.");
	                return;
	            }

	            double price = rs.getDouble("price");
	            double total = price * quantity;

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
	                orderId = keys.getInt(1);
	            }

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
	                    "========== ORDER PLACED ==========\n" +
	                    "Order ID: " + orderId + "\n" +
	                    "Total Amount: ₹" + total);

	        } catch (Exception e) {

	            output.setText(
	                    "Error: " + e.getMessage());

	        } finally {

	            try {
	                if (con != null)
	                    con.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    // View customer orders
	    void viewOrders() {

	        try (Connection con = getConnection()) {

	            int customerId =
	                    Integer.parseInt(
	                            customerIdField.getText());

	            String sql =
	                    "SELECT * FROM orders " +
	                    "WHERE customer_id = ?";

	            PreparedStatement ps =
	                    con.prepareStatement(sql);

	            ps.setInt(1, customerId);

	            ResultSet rs = ps.executeQuery();

	            output.setText(
	                    "========== ORDERS ==========\n");

	            while (rs.next()) {

	                output.append(
	                        "Order ID: " +
	                        rs.getInt("order_id") +
	                        " | Amount: ₹" +
	                        rs.getDouble("total_amount") +
	                        " | Status: " +
	                        rs.getString("status") +
	                        "\n");
	            }

	        } catch (Exception e) {
	            output.setText(
	                    "Error: " + e.getMessage());
	        }
	    }

	    // Cancel order
	    void cancelOrder() {

	        try (Connection con = getConnection()) {

	            int orderId =
	                    Integer.parseInt(
	                            orderIdField.getText());

	            String sql =
	                    "UPDATE orders " +
	                    "SET status = 'Cancelled' " +
	                    "WHERE order_id = ?";

	            PreparedStatement ps =
	                    con.prepareStatement(sql);

	            ps.setInt(1, orderId);

	            int rows = ps.executeUpdate();

	            if (rows > 0)
	                output.setText(
	                        "Order cancelled successfully!");
	            else
	                output.setText(
	                        "Order not found.");

	        } catch (Exception e) {
	            output.setText(
	                    "Error: " + e.getMessage());
	        }
	    }

	    // Update status
	    void updateStatus() {

	        try (Connection con = getConnection()) {

	            int orderId =
	                    Integer.parseInt(
	                            orderIdField.getText());

	            String[] options = {
	                    "Placed",
	                    "Preparing",
	                    "Out for Delivery",
	                    "Delivered"
	            };

	            Dialog dialog =
	                    new Dialog(this,
	                            "Update Order Status",
	                            true);

	            dialog.setSize(300, 150);
	            dialog.setLayout(new FlowLayout());

	            Choice choice = new Choice();

	            for (String option : options)
	                choice.add(option);

	            Button update =
	                    new Button("Update");

	            dialog.add(choice);
	            dialog.add(update);

	            update.addActionListener(e -> {

	                try {

	                    String sql =
	                            "UPDATE orders " +
	                            "SET status = ? " +
	                            "WHERE order_id = ?";

	                    PreparedStatement ps =
	                            con.prepareStatement(sql);

	                    ps.setString(
	                            1, choice.getSelectedItem());

	                    ps.setInt(2, orderId);

	                    ps.executeUpdate();

	                    output.setText(
	                            "Order status updated to: " +
	                            choice.getSelectedItem());

	                    dialog.dispose();

	                } catch (SQLException ex) {

	                    output.setText(
	                            "Error: " +
	                            ex.getMessage());
	                }
	            });

	            dialog.setVisible(true);

	        } catch (Exception e) {

	            output.setText(
	                    "Error: " + e.getMessage());
	        }
	    }

	    // Button actions
	    public void actionPerformed(ActionEvent e) {

	        if (e.getSource() == registerBtn) {
	            registerCustomer();
	        }

	        else if (e.getSource() == viewFoodBtn) {
	            viewFood();
	        }

	        else if (e.getSource() == placeOrderBtn) {
	            placeOrder();
	        }

	        else if (e.getSource() == viewOrdersBtn) {
	            viewOrders();
	        }

	        else if (e.getSource() == cancelOrderBtn) {
	            cancelOrder();
	        }

	        else if (e.getSource() == updateStatusBtn) {
	            updateStatus();
	        }
	    }

	    public static void main(String[] args) {
	        new Foodimp();
	    }
	}

