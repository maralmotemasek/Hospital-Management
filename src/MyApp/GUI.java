package MyApp;
import DB.*;
import Hospital.Hospital;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class GUI {
    private Admin adminDB;
    private final JFrame jFrame;
    private Hospital hospital;

    DepartmentDB departmentDB;
    DoctorDB doctorDB;
    NurseDB nurseDB;
    RoomDB roomDB;
    StaffDB staffDB;
    Hospitalization hospitalization;
    PatientDB patientDB;

    public GUI() {
        this.adminDB = new Admin();
        this.departmentDB = new DepartmentDB();
        this.doctorDB = new DoctorDB();
        this.nurseDB = new NurseDB();
        this.roomDB = new RoomDB();
        this.patientDB = new PatientDB();
        this.staffDB = new StaffDB();
        this.hospitalization = new Hospitalization();

        adminDB.createTableIfNotExists();
        this.jFrame = new JFrame();
        this.hospital = new Hospital();
    }

    public void run() {
        jFrame.setTitle("Hospital Management");
        jFrame.setSize(500, 500);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showLoginFrame();
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);
    }

    private void showLoginFrame() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(240, 240, 240));


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField userText = new JTextField(15);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JPasswordField passwordText = new JPasswordField(15);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(0, 122, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 12));

        // Grid positions
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(userText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordText, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(loginButton, gbc);

        gbc.gridy = 3;
        panel.add(messageLabel, gbc);

        jFrame.getContentPane().removeAll();
        jFrame.getContentPane().add(panel);
        jFrame.revalidate();
        jFrame.repaint();

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());

                if (adminDB.login(username, password)) {
                    showMainMenu();
                } else {
                    messageLabel.setText("Invalid credentials. Try again.");
                }
            }
        });
    }

    private void showMainMenu() {
        jFrame.getContentPane().removeAll();
        JOptionPane.showMessageDialog(jFrame, "Login Successful! Welcome.");
        departmentDB.createTableIfNotExists();
        doctorDB.createTableIfNotExists();
        patientDB.createTableIfNotExists();
        nurseDB.createTableIfNotExists();
        roomDB.createTableIfNotExists();
        hospitalization.createTableIfNotExists();
        staffDB.createTableIfNotExists();

        jFrame.setLayout(new BorderLayout());

        URL imageUrl = getClass().getResource("/img/starter_menu.png");
        if (imageUrl == null) {
            System.err.println("Error: Image not found!");
            return;
        }

        ImageIcon originalIcon = new ImageIcon(imageUrl);
        Image originalImage = originalIcon.getImage();

        int frameWidth = jFrame.getWidth();
        int frameHeight = jFrame.getHeight();

        Image scaledImage = originalImage.getScaledInstance(frameWidth, frameHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel background = new JLabel(scaledIcon);
        background.setLayout(new BorderLayout());

        JLabel instruction = new JLabel("Press any key or click to continue...", SwingConstants.CENTER);
        instruction.setFont(new Font("Arial", Font.BOLD, 20));
        instruction.setForeground(Color.BLACK);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.add(instruction);

        background.add(textPanel, BorderLayout.SOUTH);
        jFrame.add(background);

        // Add Key Listener
        jFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                hospitalManagerMenu();
            }
        });

        // Add Mouse Listener
        jFrame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hospitalManagerMenu();
            }
        });

        jFrame.setFocusable(true);
        jFrame.requestFocusInWindow();
        jFrame.revalidate();
        jFrame.repaint();
    }



    public void hospitalManagerMenu() {
        for (KeyListener kl : jFrame.getKeyListeners()) {
            jFrame.removeKeyListener(kl);
        }
        for (MouseListener ml : jFrame.getMouseListeners()) {
            jFrame.removeMouseListener(ml);
        }

        // Clear existing components
        jFrame.getContentPane().removeAll();
        jFrame.setLayout(new BorderLayout());

        // Header Label
        JLabel welcomeLabel = new JLabel("Select an Appropriate Option", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.DARK_GRAY);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Top & Bottom Padding

        // Button Panel (Vertical Layout)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 20, 20)); // 4 Rows, 1 Column, Spacing
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Padding around buttons

        // Buttons with Styling
        JButton addButton = createStyledButton("New Items");
        JButton showButton = createStyledButton("Show & Edit");
        JButton removeButton = createStyledButton("Add Hospitalization");
        JButton changeAdminButton = createStyledButton("Change Admin Info"); // New Button

        // Add buttons to panel
        buttonPanel.add(addButton);
        buttonPanel.add(showButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(changeAdminButton); // Add new button

        // Button Actions
        addButton.addActionListener(e -> addMenu());
        showButton.addActionListener(e -> showMenu());
        removeButton.addActionListener(e -> newHospitalization());
        changeAdminButton.addActionListener(e -> showChangeAdminForm()); // New action

        // Add components to JFrame
        jFrame.add(welcomeLabel, BorderLayout.NORTH);
        jFrame.add(buttonPanel, BorderLayout.CENTER);

        jFrame.revalidate();
        jFrame.repaint();
    }

    // Helper method to create styled buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(70, 130, 180)); // Steel Blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding
        return button;
    }

    private void showChangeAdminForm() {
        JDialog dialog = new JDialog(jFrame, "Change Admin Info", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(jFrame);
        dialog.setLayout(new GridBagLayout());
        dialog.getContentPane().setBackground(new Color(240, 240, 240)); // Light gray background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel userLabel = new JLabel("New Username:");
        JTextField userText = new JTextField(15);

        JLabel passwordLabel = new JLabel("New Password:");
        JPasswordField passwordText = new JPasswordField(15);

        JButton updateButton = new JButton("Update");
        updateButton.setFont(new Font("Arial", Font.BOLD, 14));
        updateButton.setBackground(new Color(34, 177, 76)); // Green
        updateButton.setForeground(Color.WHITE);
        updateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 12));

        // Layout Positioning
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(userLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        dialog.add(userText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        dialog.add(passwordText, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 2;
        dialog.add(updateButton, gbc);

        gbc.gridy = 3;
        dialog.add(messageLabel, gbc);

        updateButton.addActionListener(e -> {
            String newUsername = userText.getText();
            String newPassword = new String(passwordText.getPassword());

            if (newUsername.isEmpty() || newPassword.isEmpty()) {
                messageLabel.setText("Fields cannot be empty.");
                return;
            }

            if (!newPassword.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
                messageLabel.setText("Password must contain letters & numbers (min 8 chars).");
                return;
            }

            boolean updated = adminDB.updateAdminInfo(newUsername, newPassword);
            if (updated) {
                JOptionPane.showMessageDialog(dialog, "Admin info updated successfully!");
                dialog.dispose();
            } else {
                messageLabel.setText("Update failed. Try again.");
            }
        });

        dialog.setVisible(true);
    }

}
