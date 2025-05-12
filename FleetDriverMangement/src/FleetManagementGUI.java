import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class FleetManagementGUI extends JFrame {
    private FleetManager fleetManager;
    
    private static final Color PRIMARY_COLOR = new Color(63, 81, 181);   // Material Indigo
    private static final Color SECONDARY_COLOR = new Color(0, 150, 136); // Material Teal
    private static final Color ACCENT_COLOR = new Color(255, 64, 129);   // Material Pink
    private static final Color BG_COLOR = new Color(245, 245, 245);      // Light Gray
    private static final Color TEXT_COLOR = Color.WHITE;
    
    public FleetManagementGUI(FleetManager fm) {
        this.fleetManager = fm;
        setTitle("Fleet & Driver Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_COLOR);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(BG_COLOR);
        
        JLabel titleLabel = new JLabel("Fleet & Driver Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 2, 15, 15));
        buttonPanel.setBackground(BG_COLOR);
        
        JButton addDriverBtn = createStyledButton("Add Driver", PRIMARY_COLOR);
        JButton addVehicleBtn = createStyledButton("Add Vehicle", PRIMARY_COLOR);
        JButton assignDeliveryBtn = createStyledButton("Assign Delivery", SECONDARY_COLOR);
        JButton viewAssignmentsBtn = createStyledButton("View Assignments", SECONDARY_COLOR);
        JButton addLocationBtn = createStyledButton("Add Location", PRIMARY_COLOR);
        JButton addRouteBtn = createStyledButton("Add Route", PRIMARY_COLOR);
        JButton findRouteBtn = createStyledButton("Find Best Route", SECONDARY_COLOR);
        JButton exitBtn = createStyledButton("Exit", ACCENT_COLOR);
        
        buttonPanel.add(addDriverBtn);
        buttonPanel.add(addVehicleBtn);
        buttonPanel.add(assignDeliveryBtn);
        buttonPanel.add(viewAssignmentsBtn);
        buttonPanel.add(addLocationBtn);
        buttonPanel.add(addRouteBtn);
        buttonPanel.add(findRouteBtn);
        buttonPanel.add(exitBtn);
        
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        exitBtn.addActionListener(e -> System.exit(0));

        addDriverBtn.addActionListener(e -> {
            try {
                int driverId = Integer.parseInt(JOptionPane.showInputDialog("Enter Driver ID:"));
                String driverName = JOptionPane.showInputDialog("Enter Driver Name:");
                String license = JOptionPane.showInputDialog("Enter Driver's License Number:");
                
                if (driverName != null && license != null) {
                    fleetManager.addDriver(new Driver(driverId, driverName, license));
                    JOptionPane.showMessageDialog(this, "Driver added successfully.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid Driver ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        

        addVehicleBtn.addActionListener(e -> {
            try {
                int vehicleId = Integer.parseInt(JOptionPane.showInputDialog("Enter Vehicle ID:"));
                String model = JOptionPane.showInputDialog("Enter Vehicle Model:");
                
                if (model != null) {
                    fleetManager.addVehicle(new Vehicle(vehicleId, model));
                    JOptionPane.showMessageDialog(this, "Vehicle added successfully.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid Vehicle ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        assignDeliveryBtn.addActionListener(e -> {
            try {
                int driverId = Integer.parseInt(JOptionPane.showInputDialog("Enter Driver ID:"));

                if (!fleetManager.driverExists(driverId)) {
                    JOptionPane.showMessageDialog(this, 
                        "Error: Driver with ID " + driverId + " does not exist. Please add the driver first.",
                        "Driver Not Found", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                int vehicleId = Integer.parseInt(JOptionPane.showInputDialog("Enter Vehicle ID:"));

                if (!fleetManager.vehicleExists(vehicleId)) {
                    JOptionPane.showMessageDialog(this, 
                        "Error: Vehicle with ID " + vehicleId + " does not exist. Please add the vehicle first.",
                        "Vehicle Not Found", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                String destination = JOptionPane.showInputDialog("Enter the Destination place:");

                if (!fleetManager.locationExists(destination)) {
                    JOptionPane.showMessageDialog(this, 
                        "Error: Destination '" + destination + "' does not exist. Please add it as a location first.",
                        "Location Not Found", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int distance = Integer.parseInt(JOptionPane.showInputDialog("Enter the Approx Distance:"));
                
                if (destination != null) {
                    fleetManager.assignDelivery(driverId, vehicleId, destination, distance);
                    JOptionPane.showMessageDialog(this, "Delivery assigned successfully.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        

        viewAssignmentsBtn.addActionListener(e -> {
           
            StringBuilder assignmentsInfo = new StringBuilder();
            
            String assignments = fleetManager.getAssignmentsAsString();
            
            if (assignments.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No assignments found.");
            } else {
                JTextArea textArea = new JTextArea(20, 50);
                textArea.setText(assignments);
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                
                JOptionPane.showMessageDialog(this, scrollPane, "Current Assignments", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        addLocationBtn.addActionListener(e -> {
            String location = JOptionPane.showInputDialog("Enter Location Name:");
            
            if (location != null && !location.trim().isEmpty()) {
                fleetManager.addLocation(location);
                JOptionPane.showMessageDialog(this, "Location added successfully.");
            }
        });
        
        addRouteBtn.addActionListener(e -> {
            try {
                String source = JOptionPane.showInputDialog("Enter Source Location:");
                
                if (!fleetManager.locationExists(source)) {
                    JOptionPane.showMessageDialog(this, 
                        "Error: Source location '" + source + "' does not exist. Please add it first.",
                        "Location Not Found", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                String destination = JOptionPane.showInputDialog("Enter Destination Location:");
                
                if (!fleetManager.locationExists(destination)) {
                    JOptionPane.showMessageDialog(this, 
                        "Error: Destination location '" + destination + "' does not exist. Please add it first.",
                        "Location Not Found", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                int distance = Integer.parseInt(JOptionPane.showInputDialog("Enter Distance:"));
                
                if (source != null && destination != null) {
                    fleetManager.addRoute(source, destination, distance);
                    JOptionPane.showMessageDialog(this, "Route added successfully.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid distance.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        findRouteBtn.addActionListener(e -> {
            String start = JOptionPane.showInputDialog("Enter Start Location:");
            
            if (!fleetManager.locationExists(start)) {
                JOptionPane.showMessageDialog(this, 
                    "Error: Start location '" + start + "' does not exist. Please add it first.",
                    "Location Not Found", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String end = JOptionPane.showInputDialog("Enter End Location:");
            
            if (!fleetManager.locationExists(end)) {
                JOptionPane.showMessageDialog(this, 
                    "Error: End location '" + end + "' does not exist. Please add it first.",
                    "Location Not Found", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (start != null && end != null) {
                String result = fleetManager.findShortestPathAsString(start, end);
                
                JTextArea textArea = new JTextArea(10, 40);
                textArea.setText(result);
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                
                JOptionPane.showMessageDialog(this, scrollPane, "Best Route", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        add(mainPanel);
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(bgColor);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20));
                
                g2.setColor(TEXT_COLOR);
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(TEXT_COLOR);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                button.setBackground(bgColor.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
}
