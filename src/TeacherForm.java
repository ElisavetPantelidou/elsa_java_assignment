import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TeacherForm extends JFrame {

    public TeacherForm(MainForm mainForm) {
        setTitle("Teacher Management");
        setSize(300, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Custom close behavior

        // Handle X close
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                mainForm.setVisible(true);
                dispose();
            }
        });

        JButton insertBtn = new JButton("Insert New Teacher");
        JButton searchBtn = new JButton("Search / Edit Teacher");
        JButton backBtn = new JButton("Back to Main Menu");

        insertBtn.addActionListener(e -> {
            new TeacherEntryForm(this);
            setVisible(false);
        });

        searchBtn.addActionListener(e -> {
            new TeacherUpdateForm(this);
            setVisible(false);
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
