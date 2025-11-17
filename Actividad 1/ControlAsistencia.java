
import java.util.Scanner;

public class ControlAsistencia {
    static final int DIAS_SEMANA = 5;
    static final int NUM_ESTUDIANTES = 4;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[][] asistencia = new char[NUM_ESTUDIANTES][DIAS_SEMANA];

        boolean salir = false;

        while (!salir) {
            System.out.println("\nMenú de opciones:");
            System.out.println("1. Ver asistencia individual");
            System.out.println("2. Ver resumen general");
            System.out.println("3. Volver a registrar");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese número de estudiante (1-" + NUM_ESTUDIANTES + "): ");
                    int estudiante = sc.nextInt();
                    if (estudiante < 1 || estudiante > NUM_ESTUDIANTES) {
                        System.out.println("Estudiante no válido.");
                    } else {
                        mostrarAsistenciaIndividual(asistencia, estudiante - 1);
                    }
                    break;

                case 2:
                    mostrarResumenGeneral(asistencia);
                    break;

                case 3:
                    registrarAsistencia(sc, asistencia);
                    break;

                case 4:
                    salir = true;
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }

        sc.close();
        System.out.println("Programa finalizado.");
    }

    static void registrarAsistencia(Scanner sc, char[][] asistencia) {
        for (int i = 0; i < NUM_ESTUDIANTES; i++) {
            for (int j = 0; j < DIAS_SEMANA; j++) {
                char entrada;
                do {
                    System.out.print("Ingrese asistencia para estudiante " + (i + 1) + ", día " + (j + 1) + " (P/A): ");
                    entrada = Character.toUpperCase(sc.next().charAt(0));
                    if (entrada != 'P' && entrada != 'A') {
                        System.out.println("Entrada inválida. Ingrese 'P' para presente o 'A' para ausente.");
                    }
                } while (entrada != 'P' && entrada != 'A');
                asistencia[i][j] = entrada;
            }
        }
    }

    static void mostrarAsistenciaIndividual(char[][] asistencia, int estudiante) {
        System.out.println("Asistencia del estudiante " + (estudiante + 1) + ":");
        int totalPresente = 0;
        for (int j = 0; j < DIAS_SEMANA; j++) {
            System.out.println("Día " + (j + 1) + ": " + asistencia[estudiante][j]);
            if (asistencia[estudiante][j] == 'P') totalPresente++;
        }
        System.out.println("Total asistencias: " + totalPresente);
    }

    static void mostrarResumenGeneral(char[][] asistencia) {
        System.out.println("\nResumen general:");

        for (int i = 0; i < NUM_ESTUDIANTES; i++) {
            int totalPresente = 0;
            for (int j = 0; j < DIAS_SEMANA; j++) {
                if (asistencia[i][j] == 'P') totalPresente++;
            }
            System.out.println("Estudiante " + (i + 1) + " asistió " + totalPresente + " días.");
        }

        System.out.print("Estudiantes que asistieron todos los días: ");
        boolean alguno = false;
        for (int i = 0; i < NUM_ESTUDIANTES; i++) {
            boolean todos = true;
            for (int j = 0; j < DIAS_SEMANA; j++) {
                if (asistencia[i][j] != 'P') {
                    todos = false;
                    break;
                }
            }
            if (todos) {
                System.out.print((i + 1) + " ");
                alguno = true;
            }
        }
        if (!alguno) System.out.print("Ninguno");
        System.out.println();

        int maxAusencias = -1;
        int diaMaxAusencias = -1;
        for (int j = 0; j < DIAS_SEMANA; j++) {
            int ausencias = 0;
            for (int i = 0; i < NUM_ESTUDIANTES; i++) {
                if (asistencia[i][j] == 'A') ausencias++;
            }
            if (ausencias > maxAusencias) {
                maxAusencias = ausencias;
                diaMaxAusencias = j;
            }
        }
        System.out.println("Día con mayor número de ausencias: Día " + (diaMaxAusencias + 1) + " con " + maxAusencias + " ausencias.");
    }
}