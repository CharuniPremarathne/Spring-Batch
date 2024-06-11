package com.example.camelProject.repository;

import com.example.camelProject.model.StudentNew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<StudentNew, Integer> {
}
