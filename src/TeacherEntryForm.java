import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TeacherEntryForm extends JFrame {
    private JTextField idField, fnameField, lnameField;
    private JButton submitButton, backButton;

    public TeacherEntryForm(TeacherForm teacherForm) {
        setTitle("New Teacher Entry");
        setSize(350, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // handle manually

        // Handle window close (X)
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                teacherForm.setVisible(true);
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
        panel.add(new JLabel("Teacher ID:"));
        panel.add(idField);
        panel.add(new JLabel("First Name:"));
        panel.add(fnameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lnameField);
        panel.add(submitButton);
        panel.add(backButton);

        add(panel);

        // Submit to DB
        submitButton.addActionListener(e -> insertTeacher());

        // Back to TeacherForm
        backButton.addActionListener(e -> {
            teacherForm.setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void insertTeacher() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String fname = fnameField.getText().trim();
            String lname = lnameField.getText().trim();

            Connection conn = DBConnection.getConnection();
            String query = "INSERT INTO teachers (teacher_id, teacher_fname, teacher_lname) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.setString(2, fname);
            ps.setString(3, lname);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Teacher inserted successfully!");
                idField.setText("");
                fnameField.setText("");
                lnameField.setText("");
            }

            ps.close();
            conn.close();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (java.sql.SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(this, "Teacher ID already exists.", "Insert Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error inserting teacher: " + ex.getMessage());
        }
    }
}
