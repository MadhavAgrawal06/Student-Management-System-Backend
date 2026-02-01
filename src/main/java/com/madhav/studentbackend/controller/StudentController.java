package com.madhav.studentbackend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.madhav.studentbackend.entity.Student;
import com.madhav.studentbackend.service.StudentService;


@RestController
@CrossOrigin(
    origins = "https://fullstack-studentdb-madhav.netlify.app", 
    allowedHeaders = "*", 
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)

@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping({"","/"})
    public ResponseEntity<List<Student>> getAll(){
        return ResponseEntity.ok(studentService.getEntries());
    }

    @GetMapping("/{roll}")
    public ResponseEntity<Student> getOne(@PathVariable int roll){
        Optional<Student> data  = studentService.getEntry(roll);

        if(data.isPresent()){
            return ResponseEntity.ok(data.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<String> insertStudent(@RequestBody Student data){
        try{
            studentService.saveEntry(data);
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully added");
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add !");
        }
    }

    @PutMapping("/{roll}")
    public ResponseEntity<String> updateStudent(@PathVariable int roll, @RequestBody Student updatedData){
        boolean check = studentService.updateEntry(roll, updatedData);
        if(check){
            return ResponseEntity.ok("Updated");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{roll}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int roll){
        studentService.deleteEntry(roll);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
