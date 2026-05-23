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

    // BUSCAR
    public Optional<Tarea> buscarPorId(int id) {
        return tareas.stream()
                .filter(t -> t.getId() == id)
                .findFirst();
    }
    
    // ELIMINAR
    public void eliminarTarea(int id) {
        tareas.removeIf(t -> t.getId() == id);
    }
    // ACTUALIZAR
    public void completarTarea(int id) {
        buscarPorId(id)
                .ifPresent(t -> t.setCompletada(true));
    }

    // Filter
    public List<Tarea> tareasPendientes() {
        return tareas.stream()
                .filter(t -> !t.isCompletada())
                .collect(Collectors.toList());
    }

    // Map
    public List<String> obtenerTitulos() {
        return tareas.stream()
                .map(Tarea::getTitulo)
                .collect(Collectors.toList());
    }

    // Count
    public long contarCompletadas() {
        return tareas.stream()
                .filter(Tarea::isCompletada)
                .count();
    }

    // Sorted
    public List<Tarea> ordenarPorPrioridad() {
        return tareas.stream()
                .sorted(Comparator.comparing(Tarea::getPrioridad))
                .collect(Collectors.toList());
    }
}