import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentForm extends JFrame {

    public StudentForm(MainForm mainForm) {
        setTitle("Student Management");
        setSize(300, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // custom behavior

        // Handle X close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainForm.setVisible(true);
                dispose();
            }
        });

        JButton insertBtn = new JButton("Insert New Student");
        JButton searchBtn = new JButton("Search / Edit Student");
        JButton backBtn = new JButton("Back to Main Menu");

        insertBtn.addActionListener(e -> {
            new StudentEntryForm(this);
            setVisible(false); // Hide this form
        });

        searchBtn.addActionListener(e -> {
            new StudentUpdateForm(this);
            setVisible(false); // Hide this form
        });

        backBtn.addActionListener(e -> {
            mainForm.setVisible(true);
            dispose();
        });

        setLayout(new GridLayout(3, 1, 10, 10));
        add(insertBtn);
        add(searchBtn);
        add(backBtn);

        setVisible(true);
    }
}
