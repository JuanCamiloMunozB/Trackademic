-- Insert Countries
INSERT INTO COUNTRIES (code, name) VALUES 
(1, 'Colombia')
ON CONFLICT (code) DO NOTHING;

-- Insert Departments
INSERT INTO DEPARTMENTS (code, name, country_code) VALUES 
(1, 'Valle del Cauca', 1), 
(2, 'Cundinamarca', 1), 
(5, 'Antioquia', 1), 
(8, 'Atlántico', 1), 
(11, 'Bogotá D.C.', 1),
(3, 'Santander', 1),
(4, 'Nariño', 1),
(6, 'Caldas', 1),
(7, 'Risaralda', 1),
(9, 'Norte de Santander', 1),
(10, 'Bolívar', 1)
ON CONFLICT (code) DO NOTHING;

-- Insert Cities
INSERT INTO CITIES (code, name, dept_code) VALUES 
(101, 'Cali', 1), 
(102, 'Bogotá', 11), 
(103, 'Medellín', 5), 
(104, 'Barranquilla', 8), 
(105, 'Barranquilla', 8),
(106, 'Bucaramanga', 3),
(107, 'Pasto', 4),
(108, 'Manizales', 6),
(109, 'Pereira', 7),
(110, 'Cúcuta', 9),
(111, 'Cartagena', 10),
(112, 'Palmira', 1),
(113, 'Buga', 1),
(114, 'Tuluá', 1),
(115, 'Envigado', 5),
(116, 'Bello', 5),
(117, 'Soacha', 2),
(118, 'Facatativá', 2),
(119, 'Soledad', 8),
(120, 'Girón', 3)
ON CONFLICT (code) DO NOTHING;

-- Insert Campuses
INSERT INTO CAMPUSES (code, name, city_code) VALUES 
(1, 'Campus Cali', 101), 
(2, 'Campus Bogotá', 102), 
(3, 'Campus Medellín', 103), 
(4, 'Campus Barranquilla', 104),
(5, 'Campus Bucaramanga', 106),
(6, 'Campus Pasto', 107),
(7, 'Campus Manizales', 108),
(8, 'Campus Pereira', 109),
(9, 'Campus Sur Cali', 101),
(10, 'Campus Norte Bogotá', 102)
ON CONFLICT (code) DO NOTHING;

-- Insert Faculties SIN dean_id (para evitar FK circular)
INSERT INTO FACULTIES (code, name, location, phone_number) VALUES 
(1, 'Facultad de Ciencias Sociales', 'Cali', '555-1234'),
(2, 'Facultad de Ingeniería', 'Cali', '555-5678'),
(3, 'Facultad de Ciencias Naturales', 'Bogotá', '555-2345'),
(4, 'Facultad de Medicina', 'Medellín', '555-3456'),
(5, 'Facultad de Derecho', 'Cali', '555-4567'),
(6, 'Facultad de Artes', 'Bogotá', '555-5678'),
(7, 'Facultad de Economía', 'Barranquilla', '555-6789'),
(8, 'Facultad de Educación', 'Bucaramanga', '555-7890'),
(9, 'Facultad de Comunicación', 'Cali', '555-8901'),
(10, 'Facultad de Arquitectura', 'Medellín', '555-9012')
ON CONFLICT (code) DO NOTHING;

-- Insert Employee Types
INSERT INTO EMPLOYEE_TYPES (name) VALUES 
('Docente'), 
('Administrativo'),
('Investigador'),
('Directivo'),
('Auxiliar')
ON CONFLICT (name) DO NOTHING;

-- Insert Contract Types
INSERT INTO CONTRACT_TYPES (name) VALUES 
('Planta'), 
('Cátedra'),
('Ocasional'),
('Medio Tiempo'),
('Tiempo Completo'),
('Contratista')
ON CONFLICT (name) DO NOTHING;

-- Insert Employees (ya se puede referenciar faculty_code)
INSERT INTO EMPLOYEES (id, first_name, last_name, email, contract_type, employee_type, faculty_code, campus_code, birth_place_code) VALUES 
('1001', 'Juan',   'Pérez',  'juan.perez@univcali.edu.co',  'Planta', 'Docente',       1, 1, 101),
('1002', 'María',  'Gómez',  'maria.gomez@univcali.edu.co', 'Planta', 'Administrativo',1, 2, 102),
('1003', 'Carlos', 'López',  'carlos.lopez@univcali.edu.co','Cátedra','Docente',       2, 1, 103),
('1004', 'Carlos', 'Mejía',  'carlos.mejia@univcali.edu.co','Planta', 'Docente',       1, 3, 103),
('1005', 'Sandra', 'Ortiz',  'sandra.ortiz@univcali.edu.co','Cátedra','Docente',       2, 4, 104),
('1006', 'Julián', 'Reyes',  'julian.reyes@univcali.edu.co','Planta', 'Administrativo',2, 1, 105),
('1007', 'Laura', 'Martínez', 'laura.martinez@univ.edu', 'Planta', 'Docente', 3, 2, 102),
('1008', 'Ricardo', 'Sánchez', 'ricardo.sanchez@univ.edu', 'Cátedra', 'Docente', 4, 3, 103),
('1009', 'Mónica', 'Torres', 'monica.torres@univ.edu', 'Planta', 'Investigador', 5, 1, 101),
('1010', 'Javier', 'Ramírez', 'javier.ramirez@univ.edu', 'Tiempo Completo', 'Docente', 6, 2, 102),
('1011', 'Patricia', 'Valencia', 'patricia.valencia@univ.edu', 'Planta', 'Directivo', 7, 4, 104),
('1012', 'Gabriel', 'Rodríguez', 'gabriel.rodriguez@univ.edu', 'Ocasional', 'Docente', 8, 5, 106),
('1013', 'Diana', 'Ospina', 'diana.ospina@univ.edu', 'Planta', 'Docente', 9, 1, 101),
('1014', 'Fernando', 'Castro', 'fernando.castro@univ.edu', 'Medio Tiempo', 'Investigador', 10, 3, 103),
('1015', 'Carmen', 'Díaz', 'carmen.diaz@univ.edu', 'Planta', 'Administrativo', 3, 2, 102),
('1016', 'Miguel', 'Vargas', 'miguel.vargas@univ.edu', 'Contratista', 'Auxiliar', 4, 3, 103),
('1017', 'Luisa', 'Mendoza', 'luisa.mendoza@univ.edu', 'Planta', 'Docente', 5, 1, 112),
('1018', 'Antonio', 'Gil', 'antonio.gil@univ.edu', 'Cátedra', 'Docente', 6, 2, 102),
('1019', 'Sofía', 'Herrera', 'sofia.herrera@univ.edu', 'Planta', 'Directivo', 7, 4, 104),
('1020', 'Daniel', 'Suárez', 'daniel.suarez@univ.edu', 'Ocasional', 'Docente', 8, 5, 106),
('1021', 'Natalia', 'Rivas', 'natalia.rivas@univ.edu', 'Planta', 'Docente', 9, 1, 101),
('1022', 'Alejandro', 'Molina', 'alejandro.molina@univ.edu', 'Medio Tiempo', 'Investigador', 10, 3, 103),
('1023', 'Isabel', 'Cardona', 'isabel.cardona@univ.edu', 'Planta', 'Administrativo', 1, 1, 101),
('1024', 'Eduardo', 'Pineda', 'eduardo.pineda@univ.edu', 'Contratista', 'Auxiliar', 2, 1, 101),
('1025', 'Valentina', 'Morales', 'valentina.morales@univ.edu', 'Planta', 'Docente', 3, 2, 102),
('1026', 'Roberto', 'Escobar', 'roberto.escobar@univ.edu', 'Cátedra', 'Docente', 4, 3, 115),
('1027', 'Carolina', 'Rojas', 'carolina.rojas@univ.edu', 'Planta', 'Directivo', 5, 1, 101),
('1028', 'José', 'Duarte', 'jose.duarte@univ.edu', 'Ocasional', 'Docente', 6, 2, 102),
('1029', 'Marcela', 'Quintero', 'marcela.quintero@univ.edu', 'Planta', 'Docente', 7, 4, 104),
('1030', 'Pablo', 'Arias', 'pablo.arias@univ.edu', 'Medio Tiempo', 'Investigador', 8, 5, 106)
ON CONFLICT (id) DO NOTHING;

-- Ahora sí, actualizamos dean_id en facultades
UPDATE FACULTIES SET dean_id = '1001' WHERE code = 1;
UPDATE FACULTIES SET dean_id = '1002' WHERE code = 2;
UPDATE FACULTIES SET dean_id = '1007' WHERE code = 3;
UPDATE FACULTIES SET dean_id = '1008' WHERE code = 4;
UPDATE FACULTIES SET dean_id = '1009' WHERE code = 5;
UPDATE FACULTIES SET dean_id = '1010' WHERE code = 6;
UPDATE FACULTIES SET dean_id = '1011' WHERE code = 7;
UPDATE FACULTIES SET dean_id = '1012' WHERE code = 8;
UPDATE FACULTIES SET dean_id = '1013' WHERE code = 9;
UPDATE FACULTIES SET dean_id = '1014' WHERE code = 10;

-- Insert Areas
INSERT INTO AREAS (code, name, faculty_code, coordinator_id) VALUES 
(1, 'Área de Ciencias Sociales', 1, '1001'),
(2, 'Área de Ingeniería', 2, '1003'),
(3, 'Área de Biología', 3, '1007'),
(4, 'Área de Medicina General', 4, '1008'),
(5, 'Área de Derecho Civil', 5, '1009'),
(6, 'Área de Artes Visuales', 6, '1010'),
(7, 'Área de Economía Aplicada', 7, '1011'),
(8, 'Área de Pedagogía', 8, '1012'),
(9, 'Área de Comunicación Social', 9, '1013'),
(10, 'Área de Diseño Arquitectónico', 10, '1014'),
(11, 'Área de Psicología Clínica', 1, '1004'),
(12, 'Área de Desarrollo de Software', 2, '1006'),
(13, 'Área de Ecología', 3, '1015'),
(14, 'Área de Cirugía', 4, '1016'),
(15, 'Área de Derecho Penal', 5, '1017'),
(16, 'Área de Música', 6, '1018'),
(17, 'Área de Finanzas', 7, '1019'),
(18, 'Área de Educación Especial', 8, '1020'),
(19, 'Área de Periodismo', 9, '1021'),
(20, 'Área de Urbanismo', 10, '1022')
ON CONFLICT (code) DO NOTHING;


-- Insert Programs
INSERT INTO PROGRAMS (code, name, area_code) VALUES 
(1, 'Psicología', 1),
(2, 'Ingeniería de Sistemas', 2),
(3, 'Biología Molecular', 3),
(4, 'Medicina', 4),
(5, 'Derecho', 5),
(6, 'Bellas Artes', 6),
(7, 'Economía', 7),
(8, 'Licenciatura en Matemáticas', 8),
(9, 'Comunicación Social y Periodismo', 9),
(10, 'Arquitectura', 10),
(11, 'Trabajo Social', 1),
(12, 'Sociología', 1),
(13, 'Ingeniería Industrial', 2),
(14, 'Ingeniería Electrónica', 2),
(15, 'Ingeniería Civil', 2),
(16, 'Biotecnología', 3),
(17, 'Enfermería', 4),
(18, 'Fisioterapia', 4),
(19, 'Derecho Internacional', 5),
(20, 'Diseño Gráfico', 6),
(21, 'Administración de Empresas', 7),
(22, 'Contaduría', 7),
(23, 'Licenciatura en Literatura', 8),
(24, 'Periodismo Digital', 9),
(25, 'Diseño Interior', 10),
(26, 'Neuropsicología', 11),
(27, 'Desarrollo de Aplicaciones Móviles', 12),
(28, 'Conservación Ambiental', 13),
(29, 'Cirugía Plástica', 14),
(30, 'Derecho Constitucional', 15)
ON CONFLICT (code) DO NOTHING;

-- Insert Subjects
INSERT INTO SUBJECTS (code, name, program_code) VALUES 
('S101', 'Psicología General',    1),
('S102', 'Cálculo I',            2),
('S103', 'Programación',         2),
('S104', 'Estructuras de Datos', 2),
('S105', 'Bases de Datos',       2),
('S106', 'Redes de Computadores',2),
('S107', 'Sistemas Operativos',  2),
('S108', 'Algoritmos Avanzados', 2),
('S109', 'Biología Celular', 3),
('S110', 'Anatomía', 4),
('S111', 'Intro Derecho', 5),
('S112', 'Historia Arte', 6),
('S113', 'Microeconomía', 7),
('S114', 'Didáctica Matemát', 8),
('S115', 'Teoría Comunicación', 9),
('S116', 'Diseño Arq I', 10),
('S117', 'Psico Desarrollo', 1),
('S118', 'Prog Orient Objetos', 2),
('S119', 'Genética', 3),
('S120', 'Fisiología', 4),
('S121', 'Derecho Civil', 5),
('S122', 'Dibujo', 6),
('S123', 'Macroeconomía', 7),
('S124', 'Pedagogía', 8),
('S125', 'Redacción Period', 9),
('S126', 'Estructuras', 10),
('S127', 'Psicopatología', 1),
('S128', 'Ing Software', 2),
('S129', 'Ecología', 3),
('S130', 'Farmacología', 4),
('S131', 'Derecho Penal', 5),
('S132', 'Pintura', 6),
('S133', 'Estadística Econ', 7),
('S134', 'Evaluación Educ', 8),
('S135', 'Prod Audiovisual', 9),
('S136', 'Urbanismo', 10),
('S137', 'Interv Psicosocial', 11),
('S138', 'Métodos Invest', 12),
('S139', 'Gestión Operaciones', 13),
('S140', 'Circuitos Electr', 14),
('S141', 'Mecánica Suelos', 15),
('S142', 'Bioingeniería', 16),
('S143', 'Cuidados Intens', 17),
('S144', 'Rehab Física', 18),
('S145', 'Derecho Int Público', 19),
('S146', 'Ilustración Digital', 20),
('S147', 'Gestión RRHH', 21),
('S148', 'Auditoría', 22),
('S149', 'Lit Latinoamericana', 23),
('S150', 'Periodismo Invest', 24),
('S151', 'Renderizado 3D', 25),
('S152', 'Neurociencia Cogn', 26),
('S153', 'Desarrollo Android', 27),
('S154', 'Cambio Climático', 28),
('S155', 'Técnicas Quirúrg', 29),
('S156', 'Jurisprud Const', 30)
ON CONFLICT (code) DO NOTHING;

-- Insert Groups
INSERT INTO GROUPS (number, semester, subject_code, professor_id) VALUES 
(1, '2023-2', 'S101', '1001'),
(2, '2023-2', 'S102', '1003'),
(3, '2023-2', 'S103', '1004'),
(4, '2023-2', 'S104', '1002'),
(1, '2024-1', 'S101', '1001'),
(2, '2024-1', 'S102', '1003'),
(3, '2024-1', 'S103', '1006'),
(1, '2024-1', 'S104', '1003'),
(2, '2024-1', 'S105', '1006'),
(1, '2024-1', 'S106', '1003'),
(1, '2024-1', 'S107', '1004'),
(1, '2024-1', 'S108', '1006'),
(1, '2024-1', 'S109', '1007'),
(1, '2024-1', 'S110', '1008'),
(1, '2024-1', 'S111', '1009'),
(1, '2024-1', 'S112', '1010'),
(1, '2024-1', 'S113', '1011'),
(1, '2024-1', 'S114', '1012'),
(1, '2024-1', 'S115', '1013'),
(1, '2024-1', 'S116', '1014'),
(1, '2024-1', 'S117', '1017'),
(1, '2024-1', 'S118', '1018'),
(1, '2024-1', 'S119', '1019'),
(1, '2024-1', 'S120', '1020'),
(2, '2024-1', 'S117', '1021'),
(2, '2024-1', 'S118', '1022'),
(2, '2024-1', 'S119', '1023'),
(2, '2024-1', 'S120', '1024'),
(1, '2024-1', 'S121', '1025'),
(1, '2024-1', 'S122', '1026'),
(1, '2024-1', 'S123', '1027'),
(1, '2024-1', 'S124', '1028'),
(1, '2024-1', 'S125', '1029'),
(1, '2024-1', 'S126', '1030'),
(1, '2023-2', 'S127', '1001'),
(1, '2023-2', 'S128', '1003'),
(1, '2023-2', 'S129', '1007'),
(1, '2023-2', 'S130', '1008'),
(1, '2023-2', 'S131', '1009'),
(1, '2023-2', 'S132', '1010'),
(1, '2023-2', 'S133', '1011'),
(1, '2023-2', 'S134', '1012'),
(1, '2023-2', 'S135', '1013'),
(1, '2023-2', 'S136', '1014'),
(2, '2023-2', 'S127', '1017'),
(2, '2023-2', 'S128', '1018'),
(2, '2023-2', 'S129', '1019'),
(2, '2023-2', 'S130', '1020'),
(1, '2024-1', 'S137', '1021'),
(1, '2024-1', 'S138', '1022'),
(1, '2024-1', 'S139', '1023'),
(1, '2024-1', 'S140', '1024'),
(1, '2024-1', 'S141', '1025'),
(1, '2024-1', 'S142', '1026')
ON CONFLICT (number, subject_code, semester) DO NOTHING;

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
