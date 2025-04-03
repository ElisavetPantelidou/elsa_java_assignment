import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentEntryForm extends JFrame {
    private JTextField idField, fnameField, lnameField;
    private JButton submitButton, backButton;

    public StudentEntryForm(StudentForm studentForm) {
        setTitle("New Student Entry");
        setSize(350, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // custom behavior

        // Handle X button
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                studentForm.setVisible(true);
                dispose();
            }
        });

        idField = new JTextField();
        fnameField = new JTextField();
        lnameField = new JTextField();
        submitButton = new JButton("Submit");
        backButton = new JButton("Back");

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Student ID:"));
        panel.add(idField);
        panel.add(new JLabel("First Name:"));
        panel.add(fnameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lnameField);
        panel.add(submitButton);
        panel.add(backButton);

        add(panel);

        submitButton.addActionListener(e -> insertStudent());
        backButton.addActionListener(e -> {
            studentForm.setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void insertStudent() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String fname = fnameField.getText().trim();
            String lname = lnameField.getText().trim();
            Connection conn = DBConnection.getConnection();
            String query = "INSERT INTO students (student_id, student_fname, student_lname) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.setString(2, fname);
            ps.setString(3, lname);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Student inserted successfully!");
                idField.setText("");
                fnameField.setText("");
                lnameField.setText("");
            }
            ps.close();
            conn.close();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (java.sql.SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(this, "Student ID already exists.", "Insert Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error inserting student: " + ex.getMessage());
        }
    }
}
