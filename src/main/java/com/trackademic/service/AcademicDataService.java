package com.trackademic.service;

import com.trackademic.postgresql.entity.Group;
import com.trackademic.postgresql.entity.Subject;
import com.trackademic.postgresql.entity.Employee;
import com.trackademic.postgresql.repository.GroupRepository;
import com.trackademic.postgresql.repository.SubjectRepository;
import com.trackademic.postgresql.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service 

public class AcademicDataService {

    @Autowired
    private  GroupRepository groupRepository;
    @Autowired
    private  SubjectRepository subjectRepository;
    @Autowired
    private  EmployeeRepository employeeRepository; 


    /**
     * Retrieves all Groups from PostgreSQL.
     * @return A list of all Group entities.
     */
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    /**
     * Retrieves all Subjects from PostgreSQL.
     * @return A list of all Subject entities.
     */
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    /**
     * Retrieves all Employees (Professors) from PostgreSQL.
     * In a real scenario, you might filter by employee type 'Docente'.
     * @return A list of all Employee entities.
     */
    public List<Employee> getAllProfessors() {

         return employeeRepository.findAll();
    }

    /**
     * Retrieves an Employee by their PostgreSQL ID.
     * @param id The String ID of the employee.
     * @return An Optional containing the Employee if found, or empty if not.
     */
    public Optional<Employee> getEmployeeById(String id) {
        return employeeRepository.findById(id);
    }

    /**
     * Retrieves a list of all distinct semesters from the PostgreSQL groups table.
     * This is used to populate the semester filter dropdown in the UI.
     * @return A list of unique semester strings.
     */
    public List<String> getAllSemestersFromPostgres() {
        return groupRepository.findDistinctSemesters();
    }

}