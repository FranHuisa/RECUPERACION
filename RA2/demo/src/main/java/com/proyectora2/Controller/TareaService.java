package com.proyectora2.Controller;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.proyectora2.Model.Tarea;

public class TareaService {

    private Repositorio<Tarea> repo = new Repositorio<>();

    // ALTA
    public void agregarTarea(Tarea tarea) {
        repo.guardar(tarea);
    }

    // LISTAR
    public void listarTareas() {
        repo.obtenerTodos().forEach(System.out::println);
    }

    // BUSCAR
    public Optional<Tarea> buscarPorId(int id) {
        return repo.obtenerTodos().stream()
                .filter(t -> t.getId() == id)
                .findFirst();
    }

    // ELIMINAR
    public void eliminarTarea(int id) {
        repo.obtenerTodos().removeIf(t -> t.getId() == id);
    }

    // ACTUALIZAR
    public void completarTarea(int id) {
        buscarPorId(id)
                .ifPresent(t -> t.setCompletada(true));
    }

    // FILTER
    public List<Tarea> tareasPendientes() {
        return repo.obtenerTodos().stream()
                .filter(t -> !t.isCompletada())
                .collect(Collectors.toList());
    }

    // MAP
    public List<String> obtenerTitulos() {
        return repo.obtenerTodos().stream()
                .map(Tarea::getTitulo)
                .collect(Collectors.toList());
    }

    // AGREGACIÓN
    public long contarCompletadas() {
        return repo.obtenerTodos().stream()
                .filter(Tarea::isCompletada)
                .count();
    }

    // SORTED
    public List<Tarea> ordenarPorPrioridad() {
        return repo.obtenerTodos().stream()
                .sorted(Comparator.comparing(Tarea::getPrioridad))
                .collect(Collectors.toList());
    }
}