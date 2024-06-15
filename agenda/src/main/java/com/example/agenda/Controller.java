package agenda.src.main.java.com.example.agenda;



import com.example.agenda.exception.ResourceNotFoundException;
import com.example.agenda.model.Tarea;
import com.example.agenda.repository.TareaRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tareas")
@Api(tags = "Tareas", description = "Operaciones relacionadas con las tareas")
public class Controller {

    @Autowired
    private TareaRepository tareaRepository;

    @ApiOperation(value = "Crear una nueva tarea")
    @PostMapping
    public Tarea crearTarea(@Valid @RequestBody Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    @ApiOperation(value = "Obtener todas las tareas")
    @GetMapping
    public List<Tarea> obtenerTareas() {
        return tareaRepository.findAll();
    }

    @ApiOperation(value = "Actualizar una tarea existente por ID")
    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizarTarea(@PathVariable Long id, @Valid @RequestBody Tarea detallesTarea) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada: " + id));

        tarea.setTitulo(detallesTarea.getTitulo());
        tarea.setDescripcion(detallesTarea.getDescripcion());

        Tarea tareaActualizada = tareaRepository.save(tarea);
        return ResponseEntity.ok(tareaActualizada);
    }

    @ApiOperation(value = "Eliminar una tarea por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada: " + id));

        tareaRepository.delete(tarea);
        return ResponseEntity.noContent().build();
    }
}
