import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class StudentForm extends JFrame {

    private JTextField txtId;
    private JTextField txtFname;
    private JTextField txtLname;
    private JButton btnAdd, btnSearch, btnUpdate, btnDelete, btnViewAll, btnClear;
    private JTable table;
    private DefaultTableModel tableModel;
    private StudentDAO dao;

    public StudentForm() {
        dao = new StudentDAO();

        setTitle("Student Manager");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(20, 20, 80, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(100, 20, 200, 25);
        add(txtId);

        JLabel lblFname = new JLabel("First Name:");
        lblFname.setBounds(20, 60, 80, 25);
        add(lblFname);

        txtFname = new JTextField();
        txtFname.setBounds(100, 60, 200, 25);
        add(txtFname);

        JLabel lblLname = new JLabel("Last Name:");
        lblLname.setBounds(20, 100, 80, 25);
        add(lblLname);

        txtLname = new JTextField();
        txtLname.setBounds(100, 100, 200, 25);
        add(txtLname);

        btnAdd = new JButton("Add");
        btnAdd.setBounds(320, 20, 100, 25);
        add(btnAdd);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(430, 20, 100, 25);
        add(btnSearch);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(320, 60, 100, 25);
        add(btnUpdate);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(430, 60, 100, 25);
        add(btnDelete);

        btnViewAll = new JButton("View All");
        btnViewAll.setBounds(320, 100, 100, 25);
        add(btnViewAll);

        btnClear = new JButton("Clear");
        btnClear.setBounds(430, 100, 100, 25);
        add(btnClear);

        // Table setup
        tableModel = new DefaultTableModel(new String[]{"ID", "First Name", "Last Name"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 150, 540, 280);
        add(scrollPane);

        // Action Listeners
        btnAdd.addActionListener(e -> {
            String fname = txtFname.getText();
            String lname = txtLname.getText();
            if (!fname.isEmpty() && !lname.isEmpty()) {
                dao.addStudent(new Student(0, fname, lname));
                showMessage("Student added!");
                loadTable();
                clearForm();
            } else {
                showMessage("Please fill in both names.");
            }
        });

        btnSearch.addActionListener(e -> {
            String lname = txtLname.getText();
            if (lname.isEmpty()) {
                showMessage("Enter last name to search.");
                return;
            }

            List<Student> results = dao.getStudentsByLastName(lname);
            tableModel.setRowCount(0);
            for (Student s : results) {
                tableModel.addRow(new Object[]{s.getId(), s.getFname(), s.getLname()});
            }

            if (results.isEmpty()) {
                showMessage("No students found.");
            } else {
                Student s = results.get(0);
                txtId.setText(String.valueOf(s.getId()));
                txtFname.setText(s.getFname());
                txtLname.setText(s.getLname());
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                String fname = txtFname.getText();
                String lname = txtLname.getText();
                dao.updateStudent(new Student(id, fname, lname));
                showMessage("Student updated.");
                loadTable();
                clearForm();
            } catch (Exception ex) {
                showMessage("Invalid ID for update.");
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                dao.deleteStudent(id);
                showMessage("Student deleted.");
                loadTable();
                clearForm();
            } catch (Exception ex) {
                showMessage("Invalid ID for delete.");
            }
        });

        btnViewAll.addActionListener(e -> {
            loadTable();
        });

        btnClear.addActionListener(e -> {
            clearForm();
        });

        loadTable(); // load data at start
    }

    private void loadTable() {
        List<Student> students = dao.getStudentsByLastName("%"); // all students
        tableModel.setRowCount(0);
        for (Student s : dao.getAllStudents()) {
            tableModel.addRow(new Object[]{s.getId(), s.getFname(), s.getLname()});
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtFname.setText("");
        txtLname.setText("");
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentForm().setVisible(true));
    }
}
