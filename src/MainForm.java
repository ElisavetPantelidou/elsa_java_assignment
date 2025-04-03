import javax.swing.*;
import java.awt.*;

public class MainForm extends JFrame {
    public MainForm() {
        setTitle("Welcome to Classroom Management");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton teacherButton = new JButton("Manage Teachers");
        JButton studentButton = new JButton("Manage Students");
        teacherButton.addActionListener(e -> {
            new TeacherForm(this);
            dispose();
        });
        studentButton.addActionListener(e -> {
            new StudentForm(this);
            dispose();
        });
        setLayout(new GridLayout(2, 1, 10, 10));
        add(teacherButton);
        add(studentButton);

        setVisible(true);
    }
    public static void main(String[] args) {
        new MainForm();
    }
}
