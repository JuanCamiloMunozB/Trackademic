# Requerimientos Funcionales:

### RF1. Registro y autenticación de estudiantes
El sistema deberá proporcionar un mecanismo de registro seguro mediante el cual cualquier estudiante pueda crear una cuenta personal, aportando datos básicos (nombre, correo institucional, código estudiantil y contraseña). Una vez registrado, el estudiante deberá autenticarse con correo institucional y contraseña para acceder a todas las funcionalidades de la aplicación, garantizando la privacidad de su información y la trazabilidad de sus acciones dentro del sistema .

### RF2. Consulta de planes de evaluación existentes
Antes de ingresar cualquier nota, el sistema deberá permitir al estudiante consultar planes de evaluación previamente definidos para los grupos en los que está matriculado, mostrando un catálogo navegable con sedes, semestres y grupos disponibles. Cada grupo incluirá información relevante como nombre del curso, código y profesor, obtenida desde la base de datos institucional. Al acceder a la vista de un grupo, se indicará si existe o no un plan de evaluación ya registrado; en caso afirmativo, se mostrará su estructura (actividades y porcentaje de peso) para consulta previa al registro de notas.

### RF3. Creación de un plan de evaluación
Si un grupo no tiene aún un plan de evaluación definido, o el estudiante prefiere elaborar uno propio, el sistema le permitirá crear un nuevo plan. Para ello deberá ingresar cada actividad (nombre de la evaluación y  porcentaje de peso) de forma iterativa, hasta completar la definición total del 100% del curso, quedando listo para la carga de notas. Luego, de su creación, este se publica para que cualquier estudiante lo vea.

### RF4. Validación automática de porcentajes
Cada vez que el estudiante agregue, modifique o elimine una actividad de un plan de evaluación, el sistema realizará de forma inmediata el cómputo de la suma de todos los porcentajes y solamente permitirá guardar cambios si ésta resulta exactamente igual al 100 %. En caso contrario, desplegará un mensaje de error aclarando el exceso o déficit acumulado, impidiendo la persistencia de un plan inválido .

### RF5. Modificación de planes de evaluación propios
El estudiante podrá editar actividades de cualquier plan de evaluación que él mismo haya creado. No podrá modificar el de los demás estudiantes. Las modificaciones incluirán cambio de nombre de la actividad o ajuste del porcentaje. Tras cada cambio el sistema validará nuevamente el total de porcentajes y requerirá corrección antes de permitir la actualización definitiva en la base de datos.

### RF6. Ingreso, modificación y eliminación de notas individuales
Una vez seleccionado o creado su plan de evaluación, el estudiante podrá utilizarlo como una plantilla para poder ingresar las calificaciones obtenidas en cada actividad. Adicionalmente, tendrá la libertad de modificar o eliminar cualquiera de las notas previamente registradas por él, de modo que refleje siempre su estado real de progreso académico.

### RF7. Visualización del consolidado de notas por semestre
El sistema generará un informe en pantalla donde se consolide, actividad por actividad y curso por curso, la totalidad de las calificaciones del estudiante en un semestre académico. Este reporte detallado incluirá (a) nombre del grupo, (b) profesor, (c) actividades y porcentaje de cada una, y (d) nota obtenida y contribución al promedio final. De esta forma el usuario podrá entender su desempeño global en ese periodo.

### RF8. Informe de progreso por asignatura
Complementariamente, se ofrecerá un segundo informe tipo “progreso” para cada curso: mostrará la nota acumulada hasta el momento versus la nota necesaria para alcanzar el umbral de aprobación (p. ej. 3.0/5.0 o 70 %). Este informe calculará automáticamente, según el porcentaje restante, el puntaje mínimo requerido en las actividades pendientes, apoyando la toma de decisiones del estudiante.

### RF9. Comentarios colaborativos sobre planes de evaluación
El sistema deberá habilitar un espacio de comentarios asociado a cada plan de evaluación, permitiendo que los estudiantes hagan aportes, sugerencias o preguntas de forma colaborativa. Cada comentario incluirá autor y fecha, y no afectará la estructura del plan hasta que el propietario decida incorporar las recomendaciones.

# Requerimientos No Funcionales:

- **RNF1**. El sistema debe estar implementado usando Spring Boot y Spring MVC, con vistas Thymeleaf.
- **RNF2**. La base de datos relacional debe estar implementado en PostgresSQL y contener las entidades proporcionadas por la universidad
- **RNF3**. El sistema debe utilizar MongoDB para manejar planes de evaluación, notas, de estudiantes y comentarios, por su flexibilidad estructurada. 
- **RNF4**. El sistema debe seguir el principio de separación de capas: controlador, servicio, repositorio y entidad.
- **RNF5**. La validación de que un plan tenga el 100% del total en pesos debe realizarse tanto a nivel de backend como de frontend
- **RNF6**. La aplicación debe ofrecer una interfaz clara, intuitiva y responsive para el uso por parte de estudiantes.
- **RNF7**. La solución debe poder desplegarse en servicios en la nube como Railway (PostgresSQL) y Railway (MongoDB).