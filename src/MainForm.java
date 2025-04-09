import javax.swing.*;
import java.awt.*;

public class MainForm extends JFrame {
    public MainForm() {
        setTitle("Welcome to Classroom Management");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton studentButton = new JButton("Manage Students");
        studentButton.addActionListener(e -> {
            new StudentForm(this);
            dispose();
        });

        setLayout(new GridLayout(1, 1, 10, 10)); // Μόνο ένα κουμπί πλέον
        add(studentButton);

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainForm();
    }
}
