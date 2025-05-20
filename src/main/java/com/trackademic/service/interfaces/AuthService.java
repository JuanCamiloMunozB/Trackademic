package com.trackademic.service.interfaces;

import com.trackademic.nosql.document.Student;

public interface AuthService {

    boolean registerStudent(Student student);
}