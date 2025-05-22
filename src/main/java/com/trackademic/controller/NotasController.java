package com.trackademic.controller;

import com.trackademic.nosql.document.Semester;
import com.trackademic.nosql.document.SubjectEvaluationPlan;
import com.trackademic.security.CustomUserDetail;
import com.trackademic.service.interfaces.SemesterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/notas")
public class NotasController {

    @Autowired
    private SemesterService semesterService;

    
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
    @GetMapping("")
    public String verNotas(@AuthenticationPrincipal CustomUserDetail user,
                        @RequestParam(value = "subjectName", required = false) String subjectName,
                        Model model) {
        String studentId = user.getId();
        List<Semester> semestresOriginales = semesterService.obtenerSemestresPorEstudiante(studentId);

        // Creamos una copia para mostrar (sin modificar los originales)
        List<Semester> semestresParaMostrar = semestresOriginales.stream().map(s -> {
            Semester copia = new Semester();
            copia.setId(s.getId());
            copia.setSemester(s.getSemester());
            copia.setStudentId(s.getStudentId());

            // Filtrar solo si hay filtro activo
            if (subjectName != null && !subjectName.trim().isEmpty()) {
                List<SubjectEvaluationPlan> filtradas = s.getSubjectsEvaluationPlan().stream()
                    .filter(p -> p.getSubjectName().equalsIgnoreCase(subjectName))
                    .toList();
                copia.setSubjectsEvaluationPlan(filtradas);
            } else {
                copia.setSubjectsEvaluationPlan(s.getSubjectsEvaluationPlan());
            }

            return copia;
        }).toList();

        List<String> subjectNames = semestresOriginales.stream()
            .flatMap(s -> s.getSubjectsEvaluationPlan().stream())
            .map(SubjectEvaluationPlan::getSubjectName)
            .distinct()
            .sorted()
            .toList();

        model.addAttribute("semesters", semestresParaMostrar);
        model.addAttribute("subjects", subjectNames); // para el menú desplegable
        model.addAttribute("selectedSubjectName", subjectName); // para mantener la selección

        return "notas";
    }


    @GetMapping("/editar")
    public String editarNotas(@RequestParam String semesterId,
                            @RequestParam String subjectCode,
                            Model model,
                            @AuthenticationPrincipal CustomUserDetail user) {

        // Obtener el semestre usando el ID
        Semester semestre = semesterService.obtenerSemestrePorId(semesterId);

        if (semestre == null) {
            model.addAttribute("errorMessage", "Semestre no encontrado.");
            return "redirect:/notas";
        }

        // Buscar el plan de evaluación correspondiente al código de asignatura
        List<SubjectEvaluationPlan> planes = semestre.getSubjectsEvaluationPlan();
        SubjectEvaluationPlan plan = planes.stream()
            .filter(p -> p.getSubjectCode().equals(subjectCode))
            .findFirst()
            .orElse(null);

        if (plan == null) {
            model.addAttribute("errorMessage", "Asignatura no encontrada.");
            return "redirect:/notas";
        }

        int subjectIndex = planes.indexOf(plan);

        // Agregar todos los datos necesarios al modelo
        model.addAttribute("semesterId", semesterId);        // para usar en el formulario
        model.addAttribute("subjectCode", subjectCode);      // para usar en el formulario
        model.addAttribute("subjectIndex", subjectIndex);    // útil si usas índices
        model.addAttribute("plan", plan);                    // contiene las actividades

        return "editar_notas"; // Nombre del template Thymeleaf (editar_notas.html)
    }





    @PostMapping("/guardar")
    public String guardarNotas(@RequestParam String semesterId,
                                @RequestParam int subjectIndex,
                                @ModelAttribute SubjectEvaluationPlan updatedPlan,
                                RedirectAttributes redirectAttributes) {
        semesterService.actualizarNotas(semesterId, subjectIndex, updatedPlan);
        redirectAttributes.addFlashAttribute("successMessage", "Notas actualizadas correctamente.");
        return "redirect:/notas";
    }

    @PostMapping("/eliminar")
    public String eliminarActividad(@RequestParam String semesterId,
                                    @RequestParam int subjectIndex,
                                    @RequestParam int activityIndex,
                                    RedirectAttributes redirectAttributes) {
        semesterService.eliminarActividad(semesterId, subjectIndex, activityIndex);
        redirectAttributes.addFlashAttribute("successMessage", "Actividad eliminada.");
        return "redirect:/notas";
    }
}

