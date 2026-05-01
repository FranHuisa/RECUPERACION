package com.proyectora2.Controller;
import java.util.*;
import java.util.stream.Collectors;

import com.proyectora2.Model.Tarea;

public class TareaService {

    private List<Tarea> tareas = new ArrayList<>();

    // ALTA
    public void agregarTarea(Tarea tarea) {
        tareas.add(tarea);
    }

    // LISTAR
    public void listarTareas() {
        tareas.forEach(System.out::println);
    }

    // BUSCAR (Optional)
    public Optional<Tarea> buscarPorId(int id) {
        return tareas.stream()
                .filter(t -> t.getId() == id)
                .findFirst();
    }

    // ELIMINAR
    public void eliminarTarea(int id) {
        tareas.removeIf(t -> t.getId() == id);
    }

    // STREAMS OBLIGATORIOS

    // 1. Filter
    public List<Tarea> tareasPendientes() {
        return tareas.stream()
                .filter(t -> !t.isCompletada())
                .collect(Collectors.toList());
    }

    // 2. Map
    public List<String> obtenerTitulos() {
        return tareas.stream()
                .map(Tarea::getTitulo)
                .collect(Collectors.toList());
    }

    // 3. Count (agregación)
    public long contarCompletadas() {
        return tareas.stream()
                .filter(Tarea::isCompletada)
                .count();
    }

    // 4. Sorted
    public List<Tarea> ordenarPorPrioridad() {
        return tareas.stream()
                .sorted(Comparator.comparing(Tarea::getPrioridad))
                .collect(Collectors.toList());
    }
}