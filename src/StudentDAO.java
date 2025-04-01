import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // 1. Insert
    public void addStudent(Student student) {
        String sql = "INSERT INTO STUDENTS (STUDENT_FNAME, STUDENT_LNAME) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getFname());
            stmt.setString(2, student.getLname());
            stmt.executeUpdate();

            System.out.println("Student added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. Search by Last Name
    public List<Student> getStudentsByLastName(String lname) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM STUDENTS WHERE STUDENT_LNAME = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lname);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Student s = new Student(
                    rs.getInt("STUDENT_ID"),
                    rs.getString("STUDENT_FNAME"),
                    rs.getString("STUDENT_LNAME")
                );
                students.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    // 3. Update
    public void updateStudent(Student student) {
        String sql = "UPDATE STUDENTS SET STUDENT_FNAME = ?, STUDENT_LNAME = ? WHERE STUDENT_ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getFname());
            stmt.setString(2, student.getLname());
            stmt.setInt(3, student.getId());
            stmt.executeUpdate();

            System.out.println("🔄 Student updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. Delete
    public void deleteStudent(int id) {
        String sql = "DELETE FROM STUDENTS WHERE STUDENT_ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Student deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM STUDENTS";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                students.add(new Student(
                    rs.getInt("STUDENT_ID"),
                    rs.getString("STUDENT_FNAME"),
                    rs.getString("STUDENT_LNAME")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
    
}
