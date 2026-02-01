package com.madhav.studentbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.madhav.studentbackend.entity.Student;
import java.util.List;
import java.util.Optional;
import com.madhav.studentbackend.repository.StudentRepository;


@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;

    public List <Student> getEntries(){
        return studentRepository.findAll();
    }

    public Optional<Student> getEntry(int roll){
        return studentRepository.findById(roll);
    }

    public void saveEntry(Student data){
        studentRepository.save(data);
    }

    public boolean updateEntry(int roll, Student updatedData){
        Student existing = studentRepository.findById(roll).orElse(null);
        if(existing!=null){
                existing.setMarks(updatedData.getMarks());
        }else{
            return false;
        }
        studentRepository.save(existing);
        return true;
    }

    public void deleteEntry(int roll){
        studentRepository.deleteById(roll);
    }
}
