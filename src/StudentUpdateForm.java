import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentUpdateForm extends JFrame {
    private JTextField idField, fnameField, lnameField;
    private JButton searchButton, updateButton, deleteButton, backButton;

    public StudentUpdateForm(StudentForm studentForm) {
        setTitle("Search / Edit Student");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // handle manually

        // Handle X close
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                studentForm.setVisible(true);
                dispose();
            }
        });

        idField = new JTextField();
        fnameField = new JTextField();
        lnameField = new JTextField();

        searchButton = new JButton("Search");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        backButton = new JButton("Back");

        fnameField.setEnabled(false);
        lnameField.setEnabled(false);
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Student ID:"));
        panel.add(idField);
        panel.add(new JLabel("First Name:"));
        panel.add(fnameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lnameField);
        panel.add(searchButton);
        panel.add(new JLabel());
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(new JLabel());
        panel.add(backButton);

        add(panel);

        searchButton.addActionListener(e -> searchStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        backButton.addActionListener(e -> {
            studentForm.setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void searchStudent() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM students WHERE student_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                fnameField.setText(rs.getString("student_fname"));
                lnameField.setText(rs.getString("student_lname"));

                fnameField.setEnabled(true);
                lnameField.setEnabled(true);
                updateButton.setEnabled(true);
                deleteButton.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Student not found.");
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void updateStudent() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String fname = fnameField.getText().trim();
            String lname = lnameField.getText().trim();
            Connection conn = DBConnection.getConnection();
            String query = "UPDATE students SET student_fname = ?, student_lname = ? WHERE student_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setInt(3, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Student updated.");
            }
            ps.close();
            conn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void deleteStudent() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this student?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                Connection conn = DBConnection.getConnection();
                String query = "DELETE FROM students WHERE student_id = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, id);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Student deleted.");
                    fnameField.setText("");
                    lnameField.setText("");
                    fnameField.setEnabled(false);
                    lnameField.setEnabled(false);
                    updateButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                }
                ps.close();
                conn.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }
}
