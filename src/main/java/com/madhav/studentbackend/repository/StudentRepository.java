package com.madhav.studentbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.madhav.studentbackend.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

}
