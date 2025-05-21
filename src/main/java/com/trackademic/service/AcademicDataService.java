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
import org.springframework.util.StringUtils;

@Service 

public class AcademicDataService {

    @Autowired
    private  GroupRepository groupRepository;
    @Autowired
    private  SubjectRepository subjectRepository;
    @Autowired
    private  EmployeeRepository employeeRepository; 

    public Optional<Subject> getSubjectByCode(String code) {
    return subjectRepository.findById(code);
  }


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

    /**
     * Retrieves Groups filtered by subject code or name. Prioritizes code if both provided.
     * @param subjectCode Optional subject code.
     * @param subjectName Optional subject name.
     * @return A list of Group entities matching the subject.
     */
    public List<Group> getGroupsBySubject(String subjectCode, String subjectName) {
        if (StringUtils.hasText(subjectCode)) {
            return groupRepository.findBySubjectCode(subjectCode);
        } else if (StringUtils.hasText(subjectName)) {
            return groupRepository.findBySubjectSubjectName(subjectName);
        }

        return List.of();
    }

    /**
     * Retrieves distinct semesters for Groups filtered by subject code or name. Prioritizes code.
     * @param subjectCode Optional subject code.
     * @param subjectName Optional subject name.
     * @return A list of unique semester strings matching the subject.
     */
    public List<String> getSemestersBySubject(String subjectCode, String subjectName) {
        if (StringUtils.hasText(subjectCode)) {
            return groupRepository.findDistinctSemestersBySubjectCode(subjectCode);
        } else if (StringUtils.hasText(subjectName)) {
            return groupRepository.findDistinctSemestersBySubjectSubjectName(subjectName);
        }
        return List.of(); 
    }

    /**
     * Retrieves distinct professors associated with Groups filtered by subject code or name. Prioritizes code.
     * Fetches Employee entities based on distinct IDs from groups.
     * @param subjectCode Optional subject code.
     * @param subjectName Optional subject name.
     * @return A list of unique Employee entities (professors) matching the subject.
     */
    public List<Employee> getProfessorsBySubject(String subjectCode, String subjectName) {
        List<String> professorIds;
        if (StringUtils.hasText(subjectCode)) {
            professorIds = groupRepository.findDistinctProfessorIdsBySubjectCode(subjectCode);
        } else if (StringUtils.hasText(subjectName)) {
            professorIds = groupRepository.findDistinctProfessorIdsBySubjectSubjectName(subjectName);
        } else {
            return List.of(); 
        }

        if (professorIds.isEmpty()) {
            return List.of(); 
        }

        return employeeRepository.findAllById(professorIds);
    }


}