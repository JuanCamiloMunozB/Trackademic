package com.trackademic.postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.trackademic.postgresql.entity.Group;
import com.trackademic.postgresql.entity.GroupId;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, GroupId> {
    @Query("SELECT DISTINCT g.semester FROM Group g")
    List<String> findDistinctSemesters();

    /**
     * Finds Groups associated with a specific Subject code.
     * @param subjectCode The code of the subject.
     * @return A list of Group entities for the given subject code.
     */
    List<Group> findBySubjectCode(String subjectCode); // Spring Data can generate this

    /**
     * Finds Groups associated with a specific Subject name.
     * Uses JPQL because subject name is in the related Subject entity.
     * @param subjectName The name of the subject.
     * @return A list of Group entities for the given subject name.
     */
    @Query("SELECT g FROM Group g JOIN g.subject s WHERE s.name = :subjectName")
    List<Group> findBySubjectSubjectName(@Param("subjectName") String subjectName);


    /**
     * Retrieves a list of distinct semester values for Groups associated with a specific Subject code.
     * @param subjectCode The code of the subject.
     * @return A list of unique semester strings for the given subject code.
     */
    @Query("SELECT DISTINCT g.semester FROM Group g WHERE g.subjectCode = :subjectCode")
    List<String> findDistinctSemestersBySubjectCode(@Param("subjectCode") String subjectCode);

     /**
     * Retrieves a list of distinct semester values for Groups associated with a specific Subject name.
     * @param subjectName The name of the subject.
     * @return A list of unique semester strings for the given subject name.
     */
    @Query("SELECT DISTINCT g.semester FROM Group g JOIN g.subject s WHERE s.name = :subjectName")
    List<String> findDistinctSemestersBySubjectSubjectName(@Param("subjectName") String subjectName);

    /**
     * Retrieves a list of distinct professor IDs associated with Groups for a specific Subject code.
     * @param subjectCode The code of the subject.
     * @return A list of unique professor IDs (String) for the given subject code.
     */
    @Query("SELECT DISTINCT g.professor.id FROM Group g WHERE g.subjectCode = :subjectCode")
    List<String> findDistinctProfessorIdsBySubjectCode(@Param("subjectCode") String subjectCode);

     /**
     * Retrieves a list of distinct professor IDs associated with Groups for a specific Subject name.
     * @param subjectName The name of the subject.
     * @return A list of unique professor IDs (String) for the given subject name.
     */
    @Query("SELECT DISTINCT g.professor.id FROM Group g JOIN g.subject s WHERE s.name = :subjectName")
    List<String> findDistinctProfessorIdsBySubjectSubjectName(@Param("subjectName") String subjectName);
}
