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

    public void addDepartment() {
        jFrame.getContentPane().removeAll();
        jFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel label = new JLabel("Specialty");
        JTextField specialty = new JTextField(15);
        String[] specialtyVar = new String[1];

        specialty.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { updateSpecialty(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updateSpecialty(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updateSpecialty(); }

            private void updateSpecialty() {
                specialtyVar[0] = specialty.getText();
            }
        });

        JLabel label1 = new JLabel("Department Number");
        JTextField departmentNumber = new JTextField(15);
        String[] departmentNumberVar = new String[1];

        departmentNumber.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { updateDepartmentNumber(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updateDepartmentNumber(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updateDepartmentNumber(); }

            private void updateDepartmentNumber() {
                departmentNumberVar[0] = departmentNumber.getText();
            }
        });

        JLabel label2 = new JLabel("Room");
        JTextField room = new JTextField(15);
        String[] roomVar = new String[1];

        room.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { updateRoom(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updateRoom(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updateRoom(); }

            private void updateRoom() {
                roomVar[0] = room.getText();
            }
        });

        JLabel label3 = new JLabel("Doctor");
        JTextField doctor = new JTextField(15);
        String[] doctorVar = new String[1];

        doctor.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { updateDoctor(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updateDoctor(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updateDoctor(); }

            private void updateDoctor() {
                doctorVar[0] = doctor.getText();
            }
        });

        gbc.gridy = 0;
        jFrame.add(label, gbc);
        jFrame.add(specialty, gbc);

        gbc.gridy = 1;
        jFrame.add(label1, gbc);
        jFrame.add(departmentNumber, gbc);

        gbc.gridy = 2;
        jFrame.add(label2, gbc);
        jFrame.add(room, gbc);

        gbc.gridy = 3;
        jFrame.add(label3, gbc);
        jFrame.add(doctor, gbc);

        gbc.gridy = 4;
        JButton backButton = new JButton("Back");
        jFrame.add(backButton, gbc);
        JButton submitButton = new JButton("Submit");
        jFrame.add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            if (specialtyVar[0] == null || specialtyVar[0].trim().isEmpty() ||
                    departmentNumberVar[0] == null || departmentNumberVar[0].trim().isEmpty() ||
                    roomVar[0] == null || roomVar[0].trim().isEmpty() ||
                    doctorVar[0] == null || doctorVar[0].trim().isEmpty()) {
                JOptionPane.showMessageDialog(jFrame, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                System.out.println("Final Values: ");
                System.out.println("Specialty: " + specialtyVar[0]);
                System.out.println("Department Number: " + departmentNumberVar[0]);
                System.out.println("Room: " + roomVar[0]);
                System.out.println("Doctor: " + doctorVar[0]);
                departmentDB.addDepartment(specialtyVar[0], Integer.parseInt(departmentNumberVar[0]), true);
                addDepartment();
            }
        });
        backButton.addActionListener(e -> addMenu());

        jFrame.revalidate();
        jFrame.repaint();
    }


    public void addRoom() {
        jFrame.getContentPane().removeAll();
        jFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel label = new JLabel("Type");
        String[] roomTypes = {"Single", "Double", "ICU", "General"};
        JComboBox<String> type = new JComboBox<>(roomTypes);
        String[] typeVar = new String[1];

        type.addActionListener(e -> typeVar[0] = (String) type.getSelectedItem());

        JLabel label1 = new JLabel("Room Number");
        JTextField roomNumber = new JTextField(15);

        JLabel label2 = new JLabel("Bed Count");
        JTextField bedCount = new JTextField(15);

        JLabel label3 = new JLabel("Price");
        JTextField price = new JTextField(15);

        gbc.gridy = 0;
        jFrame.add(label, gbc);
        jFrame.add(type, gbc);

        gbc.gridy = 1;
        jFrame.add(label1, gbc);
        jFrame.add(roomNumber, gbc);

        gbc.gridy = 2;
        jFrame.add(label2, gbc);
        jFrame.add(bedCount, gbc);

        gbc.gridy = 3;
        jFrame.add(label3, gbc);
        jFrame.add(price, gbc);

        gbc.gridy = 4;
        JButton backButton = new JButton("Back");
        jFrame.add(backButton, gbc);
        JButton submitButton = new JButton("Submit");
        jFrame.add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            try {
                String selectedType = (String) type.getSelectedItem();
                int roomNum = Integer.parseInt(roomNumber.getText().trim());
                int bedCnt = Integer.parseInt(bedCount.getText().trim());
                double roomPrice = Double.parseDouble(price.getText().trim());

                if (roomNum <= 0 || bedCnt <= 0 || roomPrice < 0) {
                    JOptionPane.showMessageDialog(jFrame, "Invalid numeric values!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                System.out.println("Final Values:");
                System.out.println("Type: " + selectedType);
                System.out.println("Room Number: " + roomNum);
                System.out.println("Bed Count: " + bedCnt);
                System.out.println("Price: " + roomPrice);

                roomDB.addRoom(selectedType, roomNum, bedCnt, true, roomPrice);
                addRoom();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(jFrame, "Please enter valid numbers for Room Number, Bed Count, and Price.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> addMenu());
        jFrame.revalidate();
        jFrame.repaint();
    }


    public void addStaff() {
        jFrame.getContentPane().removeAll();
        jFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel label = new JLabel("Name");
        JTextField name = new JTextField(15);
        String[] nameVar = new String[1];

        name.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { updateName(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updateName(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updateName(); }

            private void updateName() {
                nameVar[0] = name.getText();
            }
        });

        JLabel label1 = new JLabel("Last Name");
        JTextField lastName = new JTextField(15);
        String[] lastNameVar = new String[1];

        lastName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { updateLastName(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updateLastName(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updateLastName(); }

            private void updateLastName() {
                lastNameVar[0] = lastName.getText();
            }
        });

        JLabel label2 = new JLabel("Gender");
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ButtonGroup genderGroup = new ButtonGroup();
        JRadioButton maleButton = new JRadioButton("Male");
        JRadioButton femaleButton = new JRadioButton("Female");
        JRadioButton otherButton = new JRadioButton("Other");

        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        genderGroup.add(otherButton);

        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        genderPanel.add(otherButton);

        String[] genderVar = new String[1];

        ActionListener genderListener = e -> {
            genderVar[0] = e.getActionCommand();
        };

        maleButton.addActionListener(genderListener);
        femaleButton.addActionListener(genderListener);
        otherButton.addActionListener(genderListener);

        JLabel label3 = new JLabel("Position");
        JTextField position = new JTextField(15);
        String[] positionVar = new String[1];

        position.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { updatePosition(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updatePosition(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updatePosition(); }

            private void updatePosition() {
                positionVar[0] = position.getText();
            }
        });

        gbc.gridy = 0;
        jFrame.add(label, gbc);
        jFrame.add(name, gbc);

        gbc.gridy = 1;
        jFrame.add(label1, gbc);
        jFrame.add(lastName, gbc);

        gbc.gridy = 2;
        jFrame.add(label2, gbc);
        jFrame.add(genderPanel, gbc);

        gbc.gridy = 3;
        jFrame.add(label3, gbc);
        jFrame.add(position, gbc);

        gbc.gridy = 4;
        JButton backButton = new JButton("Back");
        jFrame.add(backButton, gbc);
        JButton submitButton = new JButton("Submit");
        jFrame.add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            if (nameVar[0] == null || nameVar[0].trim().isEmpty() ||
                    lastNameVar[0] == null || lastNameVar[0].trim().isEmpty() ||
                    genderVar[0] == null || genderVar[0].trim().isEmpty() ||
                    positionVar[0] == null || positionVar[0].trim().isEmpty()) {
                JOptionPane.showMessageDialog(jFrame, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                System.out.println("Final Values: ");
                System.out.println("Name: " + nameVar[0]);
                System.out.println("Last Name: " + lastNameVar[0]);
                System.out.println("Gender: " + genderVar[0]);
                System.out.println("Position: " + positionVar[0]);
                staffDB.addStaff(nameVar[0], lastNameVar[0], genderVar[0], positionVar[0]);
                addStaff();
            }
        });
        backButton.addActionListener(e -> addMenu());

        jFrame.revalidate();
        jFrame.repaint();
    }

    public void addPatient() {
        jFrame.getContentPane().removeAll();
        jFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel label = new JLabel("Name");
        JTextField name = new JTextField(15);
        String[] nameVar = new String[1];

        name.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { updateName(); }
            @Override public void removeUpdate(DocumentEvent e) { updateName(); }
            @Override public void changedUpdate(DocumentEvent e) { updateName(); }

            private void updateName() { nameVar[0] = name.getText(); }
        });

        JLabel label1 = new JLabel("Last Name");
        JTextField lastName = new JTextField(15);
        String[] lastNameVar = new String[1];

        lastName.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { updateLastName(); }
            @Override public void removeUpdate(DocumentEvent e) { updateLastName(); }
            @Override public void changedUpdate(DocumentEvent e) { updateLastName(); }

            private void updateLastName() { lastNameVar[0] = lastName.getText(); }
        });

        JLabel label2 = new JLabel("Gender");
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ButtonGroup genderGroup = new ButtonGroup();
        JRadioButton maleButton = new JRadioButton("Male");
        JRadioButton femaleButton = new JRadioButton("Female");
        JRadioButton otherButton = new JRadioButton("Other");

        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        genderGroup.add(otherButton);

        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        genderPanel.add(otherButton);

        String[] genderVar = new String[1];

        JLabel label3 = new JLabel("Age");
        JTextField age = new JTextField(15);
        String[] ageVar = new String[1];

        age.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { updateAge(); }
            @Override public void removeUpdate(DocumentEvent e) { updateAge(); }
            @Override public void changedUpdate(DocumentEvent e) { updateAge(); }

            private void updateAge() { ageVar[0] = age.getText(); }
        });

        JLabel label4 = new JLabel("National Code");
        JTextField nationalCode = new JTextField(10);
        String[] nationalCodeVar = new String[1];

        nationalCode.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { updateNationalCode(); }
            @Override public void removeUpdate(DocumentEvent e) { updateNationalCode(); }
            @Override public void changedUpdate(DocumentEvent e) { updateNationalCode(); }

            private void updateNationalCode() { nationalCodeVar[0] = nationalCode.getText(); }
        });

        JLabel label5 = new JLabel("Illness");
        JTextField illness = new JTextField(15);
        String[] illnessVar = new String[1];

        illness.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { updateIllness(); }
            @Override public void removeUpdate(DocumentEvent e) { updateIllness(); }
            @Override public void changedUpdate(DocumentEvent e) { updateIllness(); }

            private void updateIllness() { illnessVar[0] = illness.getText(); }
        });

        JLabel label6 = new JLabel("Insurance");
        String[] insuranceOptions = {"None", "Basic", "Premium", "VIP"};
        JComboBox<String> insurance = new JComboBox<>(insuranceOptions);
        String[] insuranceVar = new String[1];
        insurance.addActionListener(e -> insuranceVar[0] = (String) insurance.getSelectedItem());

        JLabel label7 = new JLabel("Pregnancy");
        JPanel pregnantPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ButtonGroup pregnantGroup = new ButtonGroup();
        JRadioButton yesPregnant = new JRadioButton("Yes");
        JRadioButton noPregnant = new JRadioButton("No");

        pregnantGroup.add(yesPregnant);
        pregnantGroup.add(noPregnant);
        pregnantPanel.add(yesPregnant);
        pregnantPanel.add(noPregnant);

        String[] pregnantVar = new String[1];

        // Disable pregnancy selection initially
        yesPregnant.setEnabled(true);
        noPregnant.setEnabled(false);

        ActionListener pregnantListener = e -> pregnantVar[0] = e.getActionCommand();
        yesPregnant.addActionListener(pregnantListener);
        noPregnant.addActionListener(pregnantListener);

        // Gender selection listener to control pregnancy options
        ActionListener genderListener = e -> {
            genderVar[0] = e.getActionCommand();

            if ("Female".equalsIgnoreCase(genderVar[0])) {
                yesPregnant.setEnabled(true);
                noPregnant.setEnabled(true);
            } else {
                yesPregnant.setEnabled(false);
                noPregnant.setEnabled(false);
                pregnantGroup.clearSelection(); // Clear selection if not female
                pregnantVar[0] = null;
            }
        };

        maleButton.addActionListener(genderListener);
        femaleButton.addActionListener(genderListener);
        otherButton.addActionListener(genderListener);

        gbc.gridy = 0;
        jFrame.add(label, gbc);
        jFrame.add(name, gbc);

        gbc.gridy = 1;
        jFrame.add(label1, gbc);
        jFrame.add(lastName, gbc);

        gbc.gridy = 2;
        jFrame.add(label2, gbc);
        jFrame.add(genderPanel, gbc);

        gbc.gridy = 3;
        jFrame.add(label3, gbc);
        jFrame.add(age, gbc);

        gbc.gridy = 4;
        jFrame.add(label4, gbc);
        jFrame.add(nationalCode, gbc);

        gbc.gridy = 5;
        jFrame.add(label5, gbc);
        jFrame.add(illness, gbc);

        gbc.gridy = 6;
        jFrame.add(label6, gbc);
        jFrame.add(insurance, gbc);
        gbc.gridy = 7;
        jFrame.add(label7, gbc);
        jFrame.add(pregnantPanel, gbc);

        gbc.gridy = 8;
        JButton backButton = new JButton("Back");
        jFrame.add(backButton, gbc);
        JButton submitButton = new JButton("Submit");
        jFrame.add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            if(nationalCodeVar[0] == null)
                JOptionPane.showMessageDialog(jFrame, "Where is your National Code", "Error", JOptionPane.ERROR_MESSAGE);

            else if (nameVar[0] == null || nameVar[0].trim().isEmpty() ||
                    lastNameVar[0] == null || lastNameVar[0].trim().isEmpty() ||
                    genderVar[0] == null || genderVar[0].trim().isEmpty() ||
                    ageVar[0] == null || nationalCodeVar[0].trim().isEmpty() ||
                    illnessVar[0] == null || (genderVar[0].equalsIgnoreCase("Female") && pregnantVar[0] == null)) {
                JOptionPane.showMessageDialog(jFrame, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                System.out.println("Final Values: ");
                System.out.println("Name: " + nameVar[0]);
                System.out.println("Last Name: " + lastNameVar[0]);
                System.out.println("Gender: " + genderVar[0]);
                System.out.println("Age: " + ageVar[0]);
                System.out.println("National Code: " + nationalCodeVar[0]);
                System.out.println("Illness: " + illnessVar[0]);
                System.out.println("Insurance: " + insuranceVar[0]);
                System.out.println("Pregnant: " + pregnantVar[0]);
                patientDB.addPatient(nationalCodeVar[0], nameVar[0], lastNameVar[0], genderVar[0], Integer.parseInt(ageVar[0]), illnessVar[0],insuranceVar[0], Boolean.valueOf(pregnantVar[0]));
                addPatient();
            }
        });

        backButton.addActionListener(e -> addMenu());

        jFrame.revalidate();
        jFrame.repaint();
    }


    public void addNurse() {
        jFrame.getContentPane().removeAll();
        jFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel label = new JLabel("Name");
        JTextField name = new JTextField(15);
        String[] nameVar = new String[1];

        name.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { updateName(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updateName(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updateName(); }

            private void updateName() {
                nameVar[0] = name.getText();
            }
        });

        JLabel label1 = new JLabel("Last Name");
        JTextField lastName = new JTextField(15);
        String[] lastNameVar = new String[1];

        lastName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { updateLastName(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updateLastName(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updateLastName(); }

            private void updateLastName() {
                lastNameVar[0] = lastName.getText();
            }
        });

        JLabel label2 = new JLabel("Gender");
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ButtonGroup genderGroup = new ButtonGroup();
        JRadioButton maleButton = new JRadioButton("Male");
        JRadioButton femaleButton = new JRadioButton("Female");
        JRadioButton otherButton = new JRadioButton("Other");

        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        genderGroup.add(otherButton);

        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        genderPanel.add(otherButton);

        String[] genderVar = new String[1];
        ActionListener genderListener = e -> genderVar[0] = e.getActionCommand();
        maleButton.addActionListener(genderListener);
        femaleButton.addActionListener(genderListener);
        otherButton.addActionListener(genderListener);

        JLabel label3 = new JLabel("National Code");
        JTextField nationalCode = new JTextField(15);
        String[] nationalCodeVar = new String[1];

        nationalCode.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { updateNationalCode(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updateNationalCode(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updateNationalCode(); }

            private void updateNationalCode() {
                nationalCodeVar[0] = nationalCode.getText();
            }
        });

        gbc.gridy = 0; jFrame.add(label, gbc); jFrame.add(name, gbc);
        gbc.gridy = 1; jFrame.add(label1, gbc); jFrame.add(lastName, gbc);
        gbc.gridy = 2; jFrame.add(label2, gbc); jFrame.add(genderPanel, gbc);
        gbc.gridy = 3; jFrame.add(label3, gbc); jFrame.add(nationalCode, gbc);

        gbc.gridy = 4;
        JButton backButton = new JButton("Back");
        jFrame.add(backButton, gbc);
        JButton submitButton = new JButton("Submit");
        jFrame.add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            System.out.println("Final Values: ");
            System.out.println("Name: " + nameVar[0]);
            System.out.println("Last Name: " + lastNameVar[0]);
            System.out.println("Gender: " + genderVar[0]);
            System.out.println("National Code: " + nationalCodeVar[0]);
            nurseDB.addNurse(nameVar[0], lastNameVar[0], genderVar[0], Integer.parseInt(nationalCodeVar[0]));
            addNurse();
        });
        backButton.addActionListener(e -> addMenu());

        jFrame.revalidate();
        jFrame.repaint();
    }

    public void addDoctor() {
        jFrame.getContentPane().removeAll();
        jFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel label = new JLabel("Name");
        JTextField name = new JTextField(15);
        String[] nameVar = new String[1];

        name.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { updateName(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updateName(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updateName(); }

            private void updateName() {
                nameVar[0] = name.getText();
            }
        });

        JLabel label1 = new JLabel("Last Name");
        JTextField lastName = new JTextField(15);
        String[] lastNameVar = new String[1];

        lastName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { updateLastName(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updateLastName(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updateLastName(); }

            private void updateLastName() {
                lastNameVar[0] = lastName.getText();
            }
        });

        JLabel label2 = new JLabel("Gender");
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ButtonGroup genderGroup = new ButtonGroup();
        JRadioButton maleButton = new JRadioButton("Male");
        JRadioButton femaleButton = new JRadioButton("Female");
        JRadioButton otherButton = new JRadioButton("Other");

        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        genderGroup.add(otherButton);

        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        genderPanel.add(otherButton);

        String[] genderVar = new String[1];

        ActionListener genderListener = e -> {
            genderVar[0] = e.getActionCommand();
        };

        maleButton.addActionListener(genderListener);
        femaleButton.addActionListener(genderListener);
        otherButton.addActionListener(genderListener);

        JLabel label3 = new JLabel("Medical Code");
        JTextField medicalCode = new JTextField(15);
        String[] medicalCodeVar = new String[1];

        medicalCode.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { updateMedicalCode(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updateMedicalCode(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updateMedicalCode(); }

            private void updateMedicalCode() {
                medicalCodeVar[0] = medicalCode.getText();
            }
        });

        JLabel label4 = new JLabel("Specialty");
        JTextField specialty = new JTextField(15);
        String[] specialtyVar = new String[1];

        specialty.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { updateSpecialty(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updateSpecialty(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updateSpecialty(); }

            private void updateSpecialty() {
                specialtyVar[0] = specialty.getText();
            }
        });

        gbc.gridy = 0;
        jFrame.add(label, gbc);
        jFrame.add(name, gbc);

        gbc.gridy = 1;
        jFrame.add(label1, gbc);
        jFrame.add(lastName, gbc);

        gbc.gridy = 2;
        jFrame.add(label2, gbc);
        jFrame.add(genderPanel, gbc);

        gbc.gridy = 3;
        jFrame.add(label3, gbc);
        jFrame.add(medicalCode, gbc);

        gbc.gridy = 4;
        jFrame.add(label4, gbc);
        jFrame.add(specialty, gbc);

        gbc.gridy = 5;
        JButton backButton = new JButton("Back");
        jFrame.add(backButton, gbc);
        JButton submitButton = new JButton("Submit");
        jFrame.add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            if (nameVar[0] == null || nameVar[0].trim().isEmpty() ||
                    lastNameVar[0] == null || lastNameVar[0].trim().isEmpty() ||
                    genderVar[0] == null || genderVar[0].trim().isEmpty() ||
                    medicalCodeVar[0] == null || medicalCodeVar[0].trim().isEmpty() ||
                    specialtyVar[0] == null || specialtyVar[0].trim().isEmpty()) {
                JOptionPane.showMessageDialog(jFrame, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                System.out.println("Final Values: ");
                System.out.println("Name: " + nameVar[0]);
                System.out.println("Last Name: " + lastNameVar[0]);
                System.out.println("Gender: " + genderVar[0]);
                System.out.println("Medical Code: " + medicalCodeVar[0]);
                System.out.println("Specialty: " + specialtyVar[0]);
                doctorDB.addDoctor(nameVar[0], lastNameVar[0], genderVar[0], Integer.parseInt(medicalCodeVar[0]), specialtyVar[0]);
                addDoctor();
            }
        });
        backButton.addActionListener(e -> addMenu());

        jFrame.revalidate();
        jFrame.repaint();
    }

    // Add options
    public void addMenu() {
        // Clear existing components
        jFrame.getContentPane().removeAll();
        jFrame.setLayout(new BorderLayout());

        // Header Label
        JLabel titleLabel = new JLabel("Add New Entry", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(7, 1, 15, 15)); // Rows, Columns, Vertical & Horizontal Spacing
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Padding around panel

        JButton departmentButton = createStyledButton("Department");
        JButton roomButton = createStyledButton("Room");
        JButton staffButton = createStyledButton("Staff");
        JButton patientButton = createStyledButton("Patient");
        JButton nurseButton = createStyledButton("Nurse");
        JButton doctorButton = createStyledButton("Doctor");
        JButton backButton = createStyledButton("Back", Color.RED, Color.WHITE);

        // Add buttons to panel
        buttonPanel.add(departmentButton);
        buttonPanel.add(roomButton);
        buttonPanel.add(staffButton);
        buttonPanel.add(patientButton);
        buttonPanel.add(nurseButton);
        buttonPanel.add(doctorButton);
        buttonPanel.add(backButton);

        // Button Actions
        departmentButton.addActionListener(e -> addDepartment());
        roomButton.addActionListener(e -> addRoom());
        staffButton.addActionListener(e -> addStaff());
        patientButton.addActionListener(e -> addPatient());
        nurseButton.addActionListener(e -> addNurse());
        doctorButton.addActionListener(e -> addDoctor());
        backButton.addActionListener(e -> hospitalManagerMenu());

        // Add components to JFrame
        jFrame.add(titleLabel, BorderLayout.NORTH);
        jFrame.add(buttonPanel, BorderLayout.CENTER);

        jFrame.revalidate();
        jFrame.repaint();
    }


    // Overloaded method for custom button colors
    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    private void showDepartment() {
        jFrame.getContentPane().removeAll();
        jFrame.setLayout(new BorderLayout());

        ArrayList<HashMap<String, Object>> departments = departmentDB.showAll();
        String[] columnNames = {"ID", "Speciality", "Department Number", "Availability"};

        Object[][] data = new Object[departments.size()][4];
        for (int i = 0; i < departments.size(); i++) {
            HashMap<String, Object> department = departments.get(i);
            data[i][0] = department.get("id");
            data[i][1] = department.get("name");
            data[i][2] = department.get("departmentNumber").toString();
            data[i][3] = department.get("isAvailable").toString();
        }

        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // Make ID column non-editable
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setPreferredScrollableViewportSize(new Dimension(600, table.getRowHeight() * 5));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        jFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        jFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showMenu());
        buttonPanel.add(backButton);

        // Edit Button
        JButton editButton = new JButton("Edit Selected");
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                String name = table.getValueAt(selectedRow, 1).toString();
                int departmentNumber = Integer.parseInt(table.getValueAt(selectedRow, 2).toString());
                boolean isAvailable = Boolean.parseBoolean(table.getValueAt(selectedRow, 3).toString());

                editDepartment(id, name, departmentNumber, isAvailable);
            }
        });
        buttonPanel.add(editButton);

        // Delete Button
        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());

                int confirm = JOptionPane.showConfirmDialog(
                        jFrame,
                        "Are you sure you want to delete this department?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    departmentDB.removeDepartment(id);
                    showDepartment(); // Refresh table
                }
            } else {
                JOptionPane.showMessageDialog(jFrame, "Please select a department to delete.");
            }
        });
        buttonPanel.add(deleteButton);

        jFrame.pack();
        jFrame.setSize(800, 600);
        jFrame.setVisible(true);
        jFrame.revalidate();
        jFrame.repaint();
    }

    private void editDepartment(int departmentId, String name, int departmentNumber, boolean isAvailable) {
        JTextField nameField = new JTextField(name);
        JTextField departmentNumberField = new JTextField(String.valueOf(departmentNumber));
        JCheckBox availabilityCheckBox = new JCheckBox("Available", isAvailable);

        Object[] fields = {
                "Speciality:", nameField,
                "Department Number:", departmentNumberField,
                "Availability:", availabilityCheckBox
        };

        int option = JOptionPane.showConfirmDialog(jFrame, fields, "Edit Department", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String updatedName = nameField.getText();
            int updatedDepartmentNumber = Integer.parseInt(departmentNumberField.getText());
            boolean updatedAvailability = availabilityCheckBox.isSelected();

            departmentDB.updateDepartment(departmentId, updatedName, updatedAvailability, updatedDepartmentNumber);
            showDepartment();
        }
    }



    private void showRoom() {
        jFrame.getContentPane().removeAll();
        jFrame.setLayout(new BorderLayout());

        // Fetch room data from the database
        ArrayList<HashMap<String, Object>> rooms = roomDB.showAll();

        String[] columnNames = {"ID", "Type", "Room Number", "Bed Count", "Availability", "Price"};
        Object[][] data = new Object[rooms.size()][6];

        for (int i = 0; i < rooms.size(); i++) {
            HashMap<String, Object> room = rooms.get(i);
            data[i][0] = room.get("id");
            data[i][1] = room.get("type");
            data[i][2] = room.get("roomNumber").toString();
            data[i][3] = room.get("bedCount").toString();
            data[i][4] = room.get("isAvailable").toString();
            data[i][5] = room.get("price").toString();
        }

        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // ID column should not be editable
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setPreferredScrollableViewportSize(new Dimension(700, table.getRowHeight() * 6));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        jFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        jFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showMenu());
        buttonPanel.add(backButton);

        // Edit Button
        JButton editButton = new JButton("Edit Selected");
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                String type = table.getValueAt(selectedRow, 1).toString();
                int roomNumber = Integer.parseInt(table.getValueAt(selectedRow, 2).toString());
                int bedCount = Integer.parseInt(table.getValueAt(selectedRow, 3).toString());
                boolean isAvailable = Boolean.parseBoolean(table.getValueAt(selectedRow, 4).toString());
                double price = Double.parseDouble(table.getValueAt(selectedRow, 5).toString());

                editRoom(id, type, roomNumber, bedCount, isAvailable, price);
            }
        });
        buttonPanel.add(editButton);

        // Delete Button
        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());

                int confirm = JOptionPane.showConfirmDialog(
                        jFrame, "Are you sure you want to delete this room?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    roomDB.removeRoom(id);
                    showRoom(); // Refresh table after deletion
                }
            } else {
                JOptionPane.showMessageDialog(jFrame, "Please select a room to delete.");
            }
        });
        buttonPanel.add(deleteButton);

        jFrame.pack();
        jFrame.setSize(900, 600);
        jFrame.setVisible(true);
        jFrame.revalidate();
        jFrame.repaint();
    }


    private void editRoom(int id, String type, int roomNumber, int bedCount, boolean isAvailable, double price) {
        JTextField typeField = new JTextField(type);
        JTextField roomNumberField = new JTextField(String.valueOf(roomNumber));
        JTextField bedCountField = new JTextField(String.valueOf(bedCount));
        JCheckBox availabilityCheckBox = new JCheckBox("Available", isAvailable);
        JTextField priceField = new JTextField(String.valueOf(price));

        Object[] fields = {
                "Type:", typeField,
                "Room Number:", roomNumberField,
                "Bed Count:", bedCountField,
                "Availability:", availabilityCheckBox,
                "Price:", priceField
        };

        int option = JOptionPane.showConfirmDialog(jFrame, fields, "Edit Room", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String updatedType = typeField.getText();
            int updatedRoomNumber = Integer.parseInt(roomNumberField.getText());
            int updatedBedCount = Integer.parseInt(bedCountField.getText());
            boolean updatedAvailability = availabilityCheckBox.isSelected();
            double updatedPrice = Double.parseDouble(priceField.getText());

            roomDB.updateRoom(id, updatedType, updatedAvailability, updatedRoomNumber, updatedBedCount, updatedPrice);
            showRoom();
        }
    }

    private void showStaff() {
        jFrame.getContentPane().removeAll();
        jFrame.setLayout(new BorderLayout());

        // Fetch staff data from the database
        ArrayList<HashMap<String, Object>> staffList = staffDB.showAll();

        String[] columnNames = {"ID", "First Name", "Last Name", "Gender", "Position"};
        Object[][] data = new Object[staffList.size()][5];

        for (int i = 0; i < staffList.size(); i++) {
            HashMap<String, Object> staff = staffList.get(i);
            data[i][0] = staff.get("id");
            data[i][1] = staff.get("firstName");
            data[i][2] = staff.get("lastName");
            data[i][3] = staff.get("gender");
            data[i][4] = staff.get("position");
        }

        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setPreferredScrollableViewportSize(new Dimension(700, table.getRowHeight() * 6));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        jFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        jFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showMenu());
        buttonPanel.add(backButton);

        JButton editButton = new JButton("Edit Selected");
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                String firstName = table.getValueAt(selectedRow, 1).toString();
                String lastName = table.getValueAt(selectedRow, 2).toString();
                String gender = table.getValueAt(selectedRow, 3).toString();
                String position = table.getValueAt(selectedRow, 4).toString();

                editStaff(id, firstName, lastName, gender, position);
            }
        });
        buttonPanel.add(editButton);

        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());

                int confirm = JOptionPane.showConfirmDialog(
                        jFrame,
                        "Are you sure you want to delete this staff member?", "Confirm Deletion", JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    staffDB.removeById(id);
                    showStaff();
                }
            } else {
                JOptionPane.showMessageDialog(jFrame, "Please select a staff member to delete.");
            }
        });
        buttonPanel.add(deleteButton);

        jFrame.pack();
        jFrame.setSize(900, 600);
        jFrame.setVisible(true);
        jFrame.revalidate();
        jFrame.repaint();
    }

    private void editStaff(int id, String firstName, String lastName, String gender, String position) {
        JTextField firstNameField = new JTextField(firstName);
        JTextField lastNameField = new JTextField(lastName);
        JTextField genderField = new JTextField(gender);
        JTextField positionField = new JTextField(position);

        Object[] fields = {
                "First Name:", firstNameField,
                "Last Name:", lastNameField,
                "Gender:", genderField,
                "Position:", positionField
        };

        int option = JOptionPane.showConfirmDialog(jFrame, fields, "Edit Staff", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String updatedFirstName = firstNameField.getText();
            String updatedLastName = lastNameField.getText();
            String updatedGender = genderField.getText();
            String updatedPosition = positionField.getText();

            staffDB.updateById(id, updatedFirstName, updatedLastName, updatedGender, updatedPosition);
            showStaff();
        }
    }

    private void showPatient() {
        jFrame.getContentPane().removeAll();
        jFrame.setLayout(new BorderLayout());

        // Fetch patient data from the database
        ArrayList<HashMap<String, Object>> patients = patientDB.showAll();

        String[] columnNames = {"National Code", "First Name", "Last Name", "Gender", "Age", "Illness", "Insurance", "Pregnant"};
        Object[][] data = new Object[patients.size()][8];

        for (int i = 0; i < patients.size(); i++) {
            HashMap<String, Object> patient = patients.get(i);
            data[i][0] = patient.get("nationalCode");
            data[i][1] = patient.get("firstName");
            data[i][2] = patient.get("lastName");
            data[i][3] = patient.get("gender");
            data[i][4] = patient.get("age").toString();
            data[i][5] = patient.get("illness");
            data[i][6] = patient.get("insurance");
            data[i][7] = patient.get("isPregnant") != null ? patient.get("isPregnant").toString() : "N/A";
        }

        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // National Code should not be editable
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setPreferredScrollableViewportSize(new Dimension(800, table.getRowHeight() * 6));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        jFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        jFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showMenu());
        buttonPanel.add(backButton);

        // Edit Button
        JButton editButton = new JButton("Edit Selected");
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String nationalCode = table.getValueAt(selectedRow, 0).toString();
                String firstName = table.getValueAt(selectedRow, 1).toString();
                String lastName = table.getValueAt(selectedRow, 2).toString();
                String gender = table.getValueAt(selectedRow, 3).toString();
                int age = Integer.parseInt(table.getValueAt(selectedRow, 4).toString());
                String illness = table.getValueAt(selectedRow, 5).toString();
                String insurance = table.getValueAt(selectedRow, 6).toString();
                Boolean isPregnant = "true".equalsIgnoreCase(table.getValueAt(selectedRow, 7).toString());

                editPatient(nationalCode, firstName, lastName, gender, age, illness, insurance, isPregnant);
            }
        });
        buttonPanel.add(editButton);

        // Delete Button
        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String nationalCode = table.getValueAt(selectedRow, 0).toString();

                int confirm = JOptionPane.showConfirmDialog(
                        jFrame,
                        "Are you sure you want to delete this patient?", "Confirm Deletion", JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    patientDB.removeByNationalCode(nationalCode);
                    showPatient(); // Refresh table after deletion
                }
            } else {
                JOptionPane.showMessageDialog(jFrame, "Please select a patient to delete.");
            }
        });
        buttonPanel.add(deleteButton);

        jFrame.pack();
        jFrame.setSize(1000, 600);

        jFrame.setVisible(true);
        jFrame.revalidate();
        jFrame.repaint();
    }

    private void editPatient(String nationalCode, String firstName, String lastName, String gender, int age, String illness, String insurance, Boolean isPregnant) {
        JTextField firstNameField = new JTextField(firstName);
        JTextField lastNameField = new JTextField(lastName);
        JTextField genderField = new JTextField(gender);
        JTextField ageField = new JTextField(String.valueOf(age));
        JTextField illnessField = new JTextField(illness);
        JTextField insuranceField = new JTextField(insurance);
        JCheckBox isPregnantCheckBox = new JCheckBox("Pregnant", isPregnant);

        // Show pregnancy checkbox only if gender is female
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Gender:"));
        panel.add(genderField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel("Illness:"));
        panel.add(illnessField);
        panel.add(new JLabel("Insurance:"));
        panel.add(insuranceField);
        if ("Female".equalsIgnoreCase(gender)) {
            panel.add(isPregnantCheckBox);
        }

        int option = JOptionPane.showConfirmDialog(jFrame, panel, "Edit Patient", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String updatedFirstName = firstNameField.getText();
            String updatedLastName = lastNameField.getText();
            String updatedGender = genderField.getText();
            int updatedAge = Integer.parseInt(ageField.getText());
            String updatedIllness = illnessField.getText();
            String updatedInsurance = insuranceField.getText();
            Boolean updatedIsPregnant = "Female".equalsIgnoreCase(updatedGender) ? isPregnantCheckBox.isSelected() : null;

            patientDB.updateByNationalCode(nationalCode, updatedFirstName, updatedLastName, updatedGender, updatedAge, updatedIllness, updatedInsurance, updatedIsPregnant);
            showPatient(); // Refresh the patient list after update
        }
    }


    private void showDoctor() {
        jFrame.getContentPane().removeAll();
        jFrame.setLayout(new BorderLayout());

        // Fetch doctor data from the database
        ArrayList<HashMap<String, Object>> doctors = doctorDB.showAll();

        String[] columnNames = {"ID", "Name", "Last Name", "Gender", "Medical Code", "Speciality"};
        Object[][] data = new Object[doctors.size()][6];

        for (int i = 0; i < doctors.size(); i++) {
            HashMap<String, Object> doctor = doctors.get(i);
            data[i][0] = doctor.get("id");
            data[i][1] = doctor.get("name");
            data[i][2] = doctor.get("lastName");
            data[i][3] = doctor.get("gender");
            data[i][4] = doctor.get("medicalCode").toString();
            data[i][5] = doctor.get("speciality");
        }

        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // Make ID column non-editable
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setPreferredScrollableViewportSize(new Dimension(700, table.getRowHeight() * 6));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        jFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        jFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showMenu());
        buttonPanel.add(backButton);

        // Edit Button
        JButton editButton = new JButton("Edit Selected");
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                String name = table.getValueAt(selectedRow, 1).toString();
                String lastName = table.getValueAt(selectedRow, 2).toString();
                String gender = table.getValueAt(selectedRow, 3).toString();
                int medicalCode = Integer.parseInt(table.getValueAt(selectedRow, 4).toString());
                String speciality = table.getValueAt(selectedRow, 5).toString();

                editDoctor(id, name, lastName, gender, medicalCode, speciality);
            }
        });
        buttonPanel.add(editButton);

        // Delete Button
        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());

                int confirm = JOptionPane.showConfirmDialog(
                        jFrame,
                        "Are you sure you want to delete this doctor?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    doctorDB.removeDoctor(id);
                    showDoctor(); // Refresh table after deletion
                }
            } else {
                JOptionPane.showMessageDialog(jFrame, "Please select a doctor to delete.");
            }
        });
        buttonPanel.add(deleteButton);

        jFrame.pack();
        jFrame.setSize(900, 600);
        jFrame.setVisible(true);
        jFrame.revalidate();
        jFrame.repaint();
    }

    private void editDoctor(int id, String name, String lastName, String gender, int medicalCode, String speciality) {
        JTextField nameField = new JTextField(name);
        JTextField lastNameField = new JTextField(lastName);
        JTextField genderField = new JTextField(gender);
        JTextField medicalCodeField = new JTextField(String.valueOf(medicalCode));
        JTextField specialityField = new JTextField(speciality);

        Object[] fields = {
                "Name:", nameField,
                "Last Name:", lastNameField,
                "Gender:", genderField,
                "Medical Code:", medicalCodeField,
                "Speciality:", specialityField
        };

        int option = JOptionPane.showConfirmDialog(jFrame, fields, "Edit Doctor", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String updatedName = nameField.getText();
            String updatedLastName = lastNameField.getText();
            String updatedGender = genderField.getText();
            int updatedMedicalCode = Integer.parseInt(medicalCodeField.getText());
            String updatedSpeciality = specialityField.getText();

            doctorDB.updateDoctor(id, updatedName, updatedLastName, updatedGender, updatedMedicalCode, updatedSpeciality);
            showDoctor();
        }
    }

    private void showNurse() {
        jFrame.getContentPane().removeAll();
        jFrame.setLayout(new BorderLayout());

        ArrayList<HashMap<String, Object>> nurses = nurseDB.showAll();

        String[] columnNames = {"ID", "Name", "Last Name", "Gender", "National Code"};
        Object[][] data = new Object[nurses.size()][5];

        for (int i = 0; i < nurses.size(); i++) {
            HashMap<String, Object> nurse = nurses.get(i);
            data[i][0] = nurse.get("id");
            data[i][1] = nurse.get("name");
            data[i][2] = nurse.get("lastName");
            data[i][3] = nurse.get("gender");
            data[i][4] = nurse.get("nationalCode").toString();
        }

        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // ID column should not be editable
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setPreferredScrollableViewportSize(new Dimension(700, table.getRowHeight() * 6));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        jFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        jFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showMenu());
        buttonPanel.add(backButton);

        // Edit Button
        JButton editButton = new JButton("Edit Selected");
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                String name = table.getValueAt(selectedRow, 1).toString();
                String lastName = table.getValueAt(selectedRow, 2).toString();
                String gender = table.getValueAt(selectedRow, 3).toString();
                int nationalCode = Integer.parseInt(table.getValueAt(selectedRow, 4).toString());

                editNurse(id, name, lastName, gender, nationalCode);
            }
        });
        buttonPanel.add(editButton);

        // Delete Button
        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());

                int confirm = JOptionPane.showConfirmDialog(
                        jFrame,
                        "Are you sure you want to delete this nurse?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    nurseDB.removeNurse(id);
                    showNurse(); // Refresh table after deletion
                }
            } else {
                JOptionPane.showMessageDialog(jFrame, "Please select a nurse to delete.");
            }
        });
        buttonPanel.add(deleteButton);

        jFrame.pack();
        jFrame.setSize(900, 600);
        jFrame.setVisible(true);
        jFrame.revalidate();
        jFrame.repaint();
    }


    private void editNurse(int id, String name, String lastName, String gender, int nationalCode) {
        JTextField nameField = new JTextField(name);
        JTextField lastNameField = new JTextField(lastName);
        JTextField genderField = new JTextField(gender);
        JTextField nationalCodeField = new JTextField(String.valueOf(nationalCode));

        Object[] fields = {
                "Name:", nameField,
                "Last Name:", lastNameField,
                "Gender:", genderField,
                "National Code:", nationalCodeField
        };

        int option = JOptionPane.showConfirmDialog(jFrame, fields, "Edit Nurse", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String updatedName = nameField.getText();
            String updatedLastName = lastNameField.getText();
            String updatedGender = genderField.getText();
            int updatedNationalCode = Integer.parseInt(nationalCodeField.getText());

            nurseDB.updateNurse(id, updatedName, updatedLastName, updatedGender, updatedNationalCode);
            showNurse();
        }
    }

    private void showReserves() {
        jFrame.getContentPane().removeAll();
        jFrame.setLayout(new BorderLayout());

        ArrayList<HashMap<String, Object>> hospitalizations = hospitalization.showAll();

        String[] columnNames = {"ID", "Patient Name", "Room Number", "Doctor Name", "Date"};
        Object[][] data = new Object[hospitalizations.size()][5];

        for (int i = 0; i < hospitalizations.size(); i++) {
            HashMap<String, Object> record = hospitalizations.get(i);
            data[i][0] = record.get("id");
            data[i][1] = record.get("patientName");
            data[i][2] = record.get("roomNumber");
            data[i][3] = record.get("doctorName");
            data[i][4] = record.get("date").toString();
        }

        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // ID column should not be editable
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setPreferredScrollableViewportSize(new Dimension(700, table.getRowHeight() * 6));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        jFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        jFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showMenu());
        buttonPanel.add(backButton);

        JButton editButton = new JButton("Edit Selected");
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                String patientName = table.getValueAt(selectedRow, 1).toString();
                String roomNumber = table.getValueAt(selectedRow, 2).toString();
                String doctorName = table.getValueAt(selectedRow, 3).toString();
                String date = table.getValueAt(selectedRow, 4).toString();

                editHospitalization(id, patientName, roomNumber, doctorName, date);
            }
        });
        buttonPanel.add(editButton);

        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());

                int confirm = JOptionPane.showConfirmDialog(
                        jFrame, "Are you sure you want to delete this hospitalization record?", "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    hospitalization.removeById(id);
                    showReserves(); // Refresh table after deletion
                }
            } else {
                JOptionPane.showMessageDialog(jFrame, "Please select a hospitalization record to delete.");
            }
        });
        buttonPanel.add(deleteButton);

        jFrame.pack();
        jFrame.setSize(900, 600);
        jFrame.setVisible(true);
        jFrame.revalidate();
        jFrame.repaint();
    }

    private void editHospitalization(int id, String patientName, String roomNumber, String doctorName, String date) {
        jFrame.getContentPane().removeAll();
        jFrame.setLayout(new BorderLayout());

        // Title Label
        JLabel titleLabel = new JLabel("Edit Hospitalization", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Fetch Data from Database
        ArrayList<HashMap<String, String>> patients = hospitalization.getPatients();
        ArrayList<HashMap<String, String>> rooms = hospitalization.getRooms();
        ArrayList<HashMap<String, String>> doctors = hospitalization.getDoctors();

        // Convert lists to arrays for JComboBox
        String[] patientNames = patients.stream().map(p -> p.get("name")).toArray(String[]::new);
        String[] roomNames = rooms.stream().map(r -> r.get("name")).toArray(String[]::new);
        String[] doctorNames = doctors.stream().map(d -> d.get("name")).toArray(String[]::new);

        // Find the pre-selected indexes
        int selectedPatientIndex = -1;
        int selectedRoomIndex = -1;
        int selectedDoctorIndex = -1;

        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).get("name").equals(patientName)) {
                selectedPatientIndex = i;
                break;
            }
        }

        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).get("name").equals("Room " + roomNumber)) {
                selectedRoomIndex = i;
                break;
            }
        }

        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).get("name").equals(doctorName)) {
                selectedDoctorIndex = i;
                break;
            }
        }

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                "Edit Hospitalization Details",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                Color.DARK_GRAY
        ));

        // Labels and Inputs
        JLabel patientLabel = new JLabel("Select Patient:");
        patientLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JComboBox<String> patientCombo = new JComboBox<>(patientNames);
        if (selectedPatientIndex != -1) patientCombo.setSelectedIndex(selectedPatientIndex);

        JLabel roomLabel = new JLabel("Select Room:");
        roomLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JComboBox<String> roomCombo = new JComboBox<>(roomNames);
        if (selectedRoomIndex != -1) roomCombo.setSelectedIndex(selectedRoomIndex);

        JLabel doctorLabel = new JLabel("Select Doctor:");
        doctorLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JComboBox<String> doctorCombo = new JComboBox<>(doctorNames);
        if (selectedDoctorIndex != -1) doctorCombo.setSelectedIndex(selectedDoctorIndex);

        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField dateField = new JTextField(date);
        dateField.setFont(new Font("Arial", Font.PLAIN, 16));

        // Adding Components to Form Panel
        formPanel.add(patientLabel);
        formPanel.add(patientCombo);
        formPanel.add(roomLabel);
        formPanel.add(roomCombo);
        formPanel.add(doctorLabel);
        formPanel.add(doctorCombo);
        formPanel.add(dateLabel);
        formPanel.add(dateField);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton saveButton = createStyledButton("Save Changes");
        JButton backButton = createStyledButton("Back");

        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);

        // Action Listeners
        saveButton.addActionListener(e -> {
            int updatedPatientIndex = patientCombo.getSelectedIndex();
            int updatedRoomIndex = roomCombo.getSelectedIndex();
            int updatedDoctorIndex = doctorCombo.getSelectedIndex();

            String updatedPatientId = patients.get(updatedPatientIndex).get("id");
            int updatedRoomId = Integer.parseInt(rooms.get(updatedRoomIndex).get("id"));
            int updatedDoctorId = Integer.parseInt(doctors.get(updatedDoctorIndex).get("id"));
            String updatedDate = dateField.getText().trim();

            if (updatedDate.isEmpty()) {
                JOptionPane.showMessageDialog(jFrame, "Date field is required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                hospitalization.updateById(id, updatedPatientId, updatedRoomId, updatedDoctorId, updatedDate);
                JOptionPane.showMessageDialog(jFrame, "Hospitalization record updated successfully.");
                showReserves();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(jFrame, "Error updating hospitalization record.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> showReserves());

        jFrame.add(titleLabel, BorderLayout.NORTH);
        jFrame.add(formPanel, BorderLayout.CENTER);
        jFrame.add(buttonPanel, BorderLayout.SOUTH);

        jFrame.revalidate();
        jFrame.repaint();
    }



    public void showMenu() {
        jFrame.getContentPane().removeAll();
        jFrame.setSize(500, 500);
        jFrame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("View Hospital Data", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(8, 1, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton departmentButton = createStyledButton("Show Departments");
        JButton roomButton = createStyledButton("Show Rooms");
        JButton staffButton = createStyledButton("Show Staffs");
        JButton patientButton = createStyledButton("Show Patients");
        JButton nurseButton = createStyledButton("Show Nurses");
        JButton doctorButton = createStyledButton("Show Doctors");
        JButton reserveButton = createStyledButton("Show Reserves");
        JButton backButton = createStyledButton("Back", Color.RED, Color.WHITE);

        buttonPanel.add(departmentButton);
        buttonPanel.add(roomButton);
        buttonPanel.add(staffButton);
        buttonPanel.add(patientButton);
        buttonPanel.add(nurseButton);
        buttonPanel.add(doctorButton);
        buttonPanel.add(reserveButton);
        buttonPanel.add(backButton);

        departmentButton.addActionListener(e -> showDepartment());
        roomButton.addActionListener(e -> showRoom());
        staffButton.addActionListener(e -> showStaff());
        patientButton.addActionListener(e -> showPatient());
        nurseButton.addActionListener(e -> showNurse());
        doctorButton.addActionListener(e -> showDoctor());
        reserveButton.addActionListener(e -> showReserves());
        backButton.addActionListener(e -> hospitalManagerMenu());

        jFrame.add(titleLabel, BorderLayout.NORTH);
        jFrame.add(buttonPanel, BorderLayout.CENTER);

        jFrame.revalidate();
        jFrame.repaint();
    }

    public void newHospitalization() {
        jFrame.getContentPane().removeAll();
        jFrame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("New Hospitalization", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        ArrayList<HashMap<String, String>> patients = hospitalization.getPatients();
        ArrayList<HashMap<String, String>> rooms = hospitalization.getRooms();
        ArrayList<HashMap<String, String>> doctors = hospitalization.getDoctors();

        String[] patientNames = patients.stream().map(p -> p.get("name")).toArray(String[]::new);
        String[] roomNames = rooms.stream().map(r -> r.get("name")).toArray(String[]::new);
        String[] doctorNames = doctors.stream().map(d -> d.get("name")).toArray(String[]::new);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                "Hospitalization Details",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                Color.DARK_GRAY
        ));

        JLabel patientLabel = new JLabel("Select Patient:");
        patientLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JComboBox<String> patientCombo = new JComboBox<>(patientNames);

        JLabel roomLabel = new JLabel("Select Room:");
        roomLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JComboBox<String> roomCombo = new JComboBox<>(roomNames);

        JLabel doctorLabel = new JLabel("Select Doctor:");
        doctorLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JComboBox<String> doctorCombo = new JComboBox<>(doctorNames);

        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField dateField = new JTextField();
        dateField.setFont(new Font("Arial", Font.PLAIN, 16));

        formPanel.add(patientLabel);
        formPanel.add(patientCombo);
        formPanel.add(roomLabel);
        formPanel.add(roomCombo);
        formPanel.add(doctorLabel);
        formPanel.add(doctorCombo);
        formPanel.add(dateLabel);
        formPanel.add(dateField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton saveButton = createStyledButton("Save");
        JButton backButton = createStyledButton("Back");

        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);

        saveButton.addActionListener(e -> {
            int selectedPatientIndex = patientCombo.getSelectedIndex();
            int selectedRoomIndex = roomCombo.getSelectedIndex();
            int selectedDoctorIndex = doctorCombo.getSelectedIndex();

            String patientId = patients.get(selectedPatientIndex).get("id");
            int roomId = Integer.parseInt(rooms.get(selectedRoomIndex).get("id"));
            int doctorId = Integer.parseInt(doctors.get(selectedDoctorIndex).get("id"));
            String date = dateField.getText().trim();

            if (date.isEmpty()) {
                JOptionPane.showMessageDialog(jFrame, "Date field is required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                hospitalization.addHospitalization(patientId, roomId, doctorId, date);
                JOptionPane.showMessageDialog(jFrame, "Hospitalization record added successfully.");
                showMenu();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(jFrame, "Error saving hospitalization record.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> hospitalManagerMenu());

        // Adding Components to Frame
        jFrame.add(titleLabel, BorderLayout.NORTH);
        jFrame.add(formPanel, BorderLayout.CENTER);
        jFrame.add(buttonPanel, BorderLayout.SOUTH);

        jFrame.revalidate();
        jFrame.repaint();
    }
}