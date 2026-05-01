package com.proyectora2.Vista;

import java.util.Scanner;

import com.proyectora2.Controller.TareaService;
import com.proyectora2.Model.Tarea;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        TareaService service = new TareaService();

        int opcion;

        do {
            System.out.println("\n1. Añadir tarea");
            System.out.println("2. Listar tareas");
            System.out.println("3. Buscar tarea");
            System.out.println("4. Eliminar tarea");
            System.out.println("5. Salir");
            System.out.print("Opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Título: ");
                    String titulo = sc.nextLine();

                    System.out.print("Prioridad: ");
                    int prioridad = sc.nextInt();

                    service.agregarTarea(new Tarea(id, titulo, prioridad));
                    break;

                case 2:
                    service.listarTareas();
                    break;

                case 3:
                    System.out.print("ID a buscar: ");
                    int buscarId = sc.nextInt();

                    service.buscarPorId(buscarId)
                            .ifPresentOrElse(
                                    System.out::println,
                                    () -> System.out.println("No encontrada")
                            );
                    break;

                case 4:
                    System.out.print("ID a eliminar: ");
                    int eliminarId = sc.nextInt();
                    service.eliminarTarea(eliminarId);
                    break;

                case 5:
                    System.out.println("Saliendo...");
                    break;
            }

        } while (opcion != 5);

        sc.close();
    }
}