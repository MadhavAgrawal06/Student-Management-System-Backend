package com.madhav.studentbackend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class StudentController {

    //JDBC details
private static final String URL =
        "jdbc:mysql://" +
        System.getenv("MYSQLHOST") + ":" +
        System.getenv("MYSQLPORT") + "/" +
        System.getenv("MYSQLDATABASE");

private static final String USER = System.getenv("MYSQLUSER");
private static final String PASS = System.getenv("MYSQLPASSWORD");

    /* ---------------- SHOW ALL ---------------- */
    @GetMapping("/students")
    public List<Student> showAll() throws Exception {

        List<Student> list = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement("SELECT * FROM students");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Student s = new Student();
                s.rollno = rs.getInt("ROLLNO");
                s.name = rs.getString("NAME");
                s.age = rs.getInt("AGE");
                s.marks = rs.getDouble("MARKS");
                list.add(s);
            }
        }

        return list;
    }


    /* ---------------- SHOW ONE ---------------- */
    @GetMapping("/students/{rollno}")
    public Student showOne(@PathVariable int rollno) throws Exception {

        Student s = null;

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement("SELECT * FROM students WHERE rollno=?")) {

            ps.setInt(1, rollno);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    s = new Student();
                    s.rollno = rs.getInt("ROLLNO");
                    s.name = rs.getString("NAME");
                    s.age = rs.getInt("AGE");
                    s.marks = rs.getDouble("MARKS");
                }
            }
        }

        return s;
    }


    /* ---------------- INSERT ---------------- */
    @PostMapping("/students")
    public String insert(@RequestBody Student s) throws Exception {

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement ps = con.prepareStatement("INSERT INTO students (rollno, name, age, marks) VALUES (?, ?, ?, ?)")) {

            ps.setInt(1, s.rollno);
            ps.setString(2, s.name);
            ps.setInt(3, s.age);
            ps.setDouble(4, s.marks);

            ps.executeUpdate();
        }

        return "Student Inserted";
    }


    /* ---------------- UPDATE ---------------- */
    @PutMapping("/students/{rollno}")
    public String update(@PathVariable int rollno,
                         @RequestBody Student s) throws Exception {

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement("UPDATE students SET marks=? WHERE rollno=?")) {

            ps.setDouble(1, s.marks);
            ps.setInt(2, rollno);

            ps.executeUpdate();
        }

        return "Student Updated";
    }

    /* ---------------- DELETE ---------------- */  
    @DeleteMapping("/students/{rollno}")
    public String delete(@PathVariable int rollno) throws Exception {

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement("DELETE FROM students WHERE rollno=?")) {

            ps.setInt(1, rollno);
            ps.executeUpdate();
        }

        return "Student Deleted";
    }
}
