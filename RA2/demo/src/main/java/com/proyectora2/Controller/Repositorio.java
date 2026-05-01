package com.proyectora2.Controller;
import java.util.ArrayList;
import java.util.List;

public class Repositorio<T> {
    private List<T> elementos = new ArrayList<>();

    public void guardar(T elemento) {
        elementos.add(elemento);
    }

    public List<T> obtenerTodos() {
        return elementos;
    }
}