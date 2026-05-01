package com.proyectora2.Model;

public class Tarea {
    private int id;
    private String titulo;
    private boolean completada;
    private int prioridad;

    public Tarea(int id, String titulo, int prioridad) {
        this.id = id;
        this.titulo = titulo;
        this.prioridad = prioridad;
        this.completada = false;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public boolean isCompletada() { return completada; }
    public int getPrioridad() { return prioridad; }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    @Override
    public String toString() {
        return id + " - " + titulo + " (Prioridad: " + prioridad + ") " +
                (completada ? "[COMPLETADA]" : "[PENDIENTE]");
    }
}