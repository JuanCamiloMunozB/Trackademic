package com.trackademic.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trackademic.nosql.document.Student;
import com.trackademic.nosql.repository.StudentRepository;
import com.trackademic.service.interfaces.AuthService;

@Service
public class AuthServiceI implements AuthService {

     @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean registerStudent(Student student){
        if (studentRepository.findByEmail(student.getEmail()).isPresent()) {
            return false;
        }
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        studentRepository.save(student);
        return true;
    }

}
