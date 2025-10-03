import adm.AdmMedico;
import adm.AdmPaciente;

import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static AdmPaciente admPaciente = new AdmPaciente();
    private static AdmMedico admMedico = new AdmMedico();
    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\nMENU PRINCIPAL:");
            System.out.println("1 - Administrar de Pacientes");
            System.out.println("2 - Administrar de Médicos");
            System.out.println("3 - Administrar de Planos de Saúde");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    admPaciente.menuAdmPaciente();
                    break;
                case 2:
                    admMedico.menuAdmMedico();
                    break;
                case 3:

                    break;
                case 0:
                    System.out.println("Saindo do sistema");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 0);

        sc.close();
    }
}