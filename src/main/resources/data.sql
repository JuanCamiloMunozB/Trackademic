-- Insert Countries
INSERT INTO COUNTRIES (code, name) VALUES
(1, 'Colombia');

-- Insert Departments
INSERT INTO DEPARTMENTS (code, name, country_code) VALUES
(1, 'Valle del Cauca', 1),
(2, 'Cundinamarca', 1),
(5, 'Antioquia', 1),
(8, 'Atlántico', 1),
(11, 'Bogotá D.C.', 1);

-- Insert Cities
INSERT INTO CITIES (code, name, dept_code) VALUES
(101, 'Cali', 1),
(102, 'Bogotá', 11),
(103, 'Medellín', 5),
(104, 'Barranquilla', 8),
(105, 'Barranquilla', 8);

-- Insert Campuses
INSERT INTO CAMPUSES (code, name, city_code) VALUES
(1, 'Campus Cali', 101),
(2, 'Campus Bogotá', 102),
(3, 'Campus Medellín', 103),
(4, 'Campus Barranquilla', 104);

-- Insert Faculties SIN dean_id (para evitar FK circular)
INSERT INTO FACULTIES (code, name, location, phone_number) VALUES
(1, 'Facultad de Ciencias Sociales', 'Cali', '555-1234'),
(2, 'Facultad de Ingeniería', 'Cali', '555-5678');

-- Insert Employee Types
INSERT INTO EMPLOYEE_TYPES (name) VALUES
('Docente'),
('Administrativo');

-- Insert Contract Types
INSERT INTO CONTRACT_TYPES (name) VALUES
('Planta'),
('Cátedra');

-- Insert Employees (ya se puede referenciar faculty_code)
INSERT INTO EMPLOYEES (id, first_name, last_name, email, contract_type, employee_type, faculty_code, campus_code, birth_place_code) VALUES
('1001', 'Juan', 'Pérez', 'juan.perez@univcali.edu.co', 'Planta', 'Docente', 1, 1, 101),
('1002', 'María', 'Gómez', 'maria.gomez@univcali.edu.co', 'Planta', 'Administrativo', 1, 2, 102),
('1003', 'Carlos', 'López', 'carlos.lopez@univcali.edu.co', 'Cátedra', 'Docente', 2, 1, 103),
('1004', 'Carlos', 'Mejía', 'carlos.mejia@univcali.edu.co', 'Planta', 'Docente', 1, 3, 103),
('1005', 'Sandra', 'Ortiz', 'sandra.ortiz@univcali.edu.co', 'Cátedra', 'Docente', 2, 4, 104),
('1006', 'Julián', 'Reyes', 'julian.reyes@univcali.edu.co', 'Planta', 'Administrativo', 2, 1, 105);

-- Ahora sí, actualizamos dean_id en facultades
UPDATE FACULTIES SET dean_id = '1001' WHERE code = 1;
UPDATE FACULTIES SET dean_id = '1002' WHERE code = 2;

-- Insert Areas
INSERT INTO AREAS (code, name, faculty_code, coordinator_id) VALUES
(1, 'Área de Ciencias Sociales', 1, '1001'),
(2, 'Área de Ingeniería', 2, '1003');

-- Insert Programs
INSERT INTO PROGRAMS (code, name, area_code) VALUES
(1, 'Psicología', 1),
(2, 'Ingeniería de Sistemas', 2);

-- Insert Subjects
INSERT INTO SUBJECTS (code, name, program_code) VALUES
('S101', 'Psicología General', 1),
('S102', 'Cálculo I', 2),
('S103', 'Programación', 2),
('S104', 'Estructuras de Datos', 2),
('S105', 'Bases de Datos', 2),
('S106', 'Redes de Computadores', 2),
('S107', 'Sistemas Operativos', 2),
('S108', 'Algoritmos Avanzados', 2);

-- Insert Groups
INSERT INTO GROUPS (number, semester, subject_code, professor_id) VALUES
(1, '2023-2', 'S101', '1001'),
(2, '2023-2', 'S102', '1003'),
(3, '2023-2', 'S103', '1004');

-- -- Insert Foreign Key Constraints
-- ALTER TABLE AREAS ADD CONSTRAINT AREAS_EMPLOYEES_FK FOREIGN KEY (coordinator_id) REFERENCES EMPLOYEES (id);
-- ALTER TABLE AREAS ADD CONSTRAINT AREAS_FACULTIES_FK FOREIGN KEY (faculty_code) REFERENCES FACULTIES (code);
-- ALTER TABLE SUBJECTS ADD CONSTRAINT SUBJECTS_PROGRAMS_FK FOREIGN KEY (program_code) REFERENCES PROGRAMS (code);
-- ALTER TABLE CITIES ADD CONSTRAINT CITIES_DEPARTMENTS_FK FOREIGN KEY (dept_code) REFERENCES DEPARTMENTS (code);
-- ALTER TABLE DEPARTMENTS ADD CONSTRAINT DEPARTMENTS_COUNTRIES_FK FOREIGN KEY (country_code) REFERENCES COUNTRIES (code);
-- ALTER TABLE EMPLOYEES ADD CONSTRAINT EMPLOYEES_CONTRACT_TYPES_FK FOREIGN KEY (contract_type) REFERENCES CONTRACT_TYPES (name);
-- ALTER TABLE EMPLOYEES ADD CONSTRAINT EMPLOYEES_CITIES_FK FOREIGN KEY (birth_place_code) REFERENCES CITIES (code);
-- ALTER TABLE EMPLOYEES ADD CONSTRAINT EMPLOYEES_FACULTIES_FK FOREIGN KEY (faculty_code) REFERENCES FACULTIES (code);
-- ALTER TABLE EMPLOYEES ADD CONSTRAINT EMPLOYEES_CAMPUSES_FK FOREIGN KEY (campus_code) REFERENCES CAMPUSES (code);
-- ALTER TABLE EMPLOYEES ADD CONSTRAINT EMPLOYEES_EMPLOYEE_TYPES_FK FOREIGN KEY (employee_type) REFERENCES EMPLOYEE_TYPES (name);
-- ALTER TABLE FACULTIES ADD CONSTRAINT FACULTIES_EMPLOYEES_FK FOREIGN KEY (dean_id) REFERENCES EMPLOYEES (id);
-- ALTER TABLE GROUPS ADD CONSTRAINT GROUPS_SUBJECTS_FK FOREIGN KEY (subject_code) REFERENCES SUBJECTS (code);
-- ALTER TABLE GROUPS ADD CONSTRAINT GROUPS_EMPLOYEES_FK FOREIGN KEY (professor_id) REFERENCES EMPLOYEES (id);
-- ALTER TABLE PROGRAMS ADD CONSTRAINT PROGRAMS_AREAS_FK FOREIGN KEY (area_code) REFERENCES AREAS (code);
-- ALTER TABLE CAMPUSES ADD CONSTRAINT CAMPUSES_CITIES_FK FOREIGN KEY (city_code) REFERENCES CITIES (code);
