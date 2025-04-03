import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class TeacherUpdateForm extends JFrame {
    private JTextField idField, fnameField, lnameField;
    private JButton searchButton, updateButton, deleteButton, backButton;

    public TeacherUpdateForm(TeacherForm teacherForm) {
        setTitle("Search / Edit Teacher");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // custom behavior

        // Handle X close
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                teacherForm.setVisible(true);
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

        panel.add(new JLabel("Teacher ID:"));
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

        searchButton.addActionListener(e -> searchTeacher());
        updateButton.addActionListener(e -> updateTeacher());
        deleteButton.addActionListener(e -> deleteTeacher());

        backButton.addActionListener(e -> {
            teacherForm.setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void searchTeacher() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM teachers WHERE teacher_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                fnameField.setText(rs.getString("teacher_fname"));
                lnameField.setText(rs.getString("teacher_lname"));

                fnameField.setEnabled(true);
                lnameField.setEnabled(true);
                updateButton.setEnabled(true);
                deleteButton.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Teacher not found.");
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID must be a number.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void updateTeacher() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String fname = fnameField.getText().trim();
            String lname = lnameField.getText().trim();
            Connection conn = DBConnection.getConnection();
            String query = "UPDATE teachers SET teacher_fname = ?, teacher_lname = ? WHERE teacher_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setInt(3, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Teacher updated.");
            }
            ps.close();
            conn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void deleteTeacher() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this teacher?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                Connection conn = DBConnection.getConnection();
                String query = "DELETE FROM teachers WHERE teacher_id = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, id);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Teacher deleted.");
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
