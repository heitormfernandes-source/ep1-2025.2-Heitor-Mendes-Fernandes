import adm.*;
import entities.*;

import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static AdmPaciente admPaciente = new AdmPaciente();
    private static AdmMedico admMedico = new AdmMedico();
    private static AdmPlanoSaude admPlanoSaude = new AdmPlanoSaude();
    private static AdmConsulta admConsulta = new AdmConsulta();
    private static AdmInternacao admInternacao = new AdmInternacao();
    private static SistemaConsultas sistemaConsultas = new SistemaConsultas();

    public static void main(String[] args) {
        int opcao = -1;
        do {
            System.out.println("\nMENU PRINCIPAL:");
            System.out.println("1 - Administrar Pacientes");
            System.out.println("2 - Administrar Médicos");
            System.out.println("3 - Administrar Planos de Saúde");
            System.out.println("4 - Realizar Consulta");
            System.out.println("5 - Administrar Consultas");
            System.out.println("6 - Administrar Internações");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());

                switch (opcao) {
                    case 1:
                        admPaciente.menuAdmPaciente();
                        break;
                    case 2:
                        admMedico.menuAdmMedico();
                        break;
                    case 3:
                        admPlanoSaude.menuAdmPlano();
                        break;
                    case 4:
                        realizarConsulta();
                        break;
                    case 5:
                        admConsulta.menuAdmConsulta(admPaciente, admMedico);
                        break;
                    case 6:
                        admInternacao.menuAdmInternacao(admPaciente, admMedico);
                        break;
                    case 0:
                        System.out.println("Saindo do sistema...");
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número.");
            }
        } while (opcao != 0);

        sc.close();
    }

    private static void realizarConsulta() {
        System.out.println("\n--- AGENDAMENTO RÁPIDO DE CONSULTA ---");

        admMedico.listarMedicos();
        System.out.print("Digite o ID do médico: ");
        int idMedico = Integer.parseInt(sc.nextLine());
        Medico medico = admMedico.buscaMedico(idMedico);

        if (medico == null) {
            System.out.println("Médico não encontrado!");
            return;
        }

        // Seleciona paciente
        admPaciente.listarPacientes();
        System.out.print("Digite o ID do paciente: ");
        int idPaciente = Integer.parseInt(sc.nextLine());
        Paciente paciente = admPaciente.buscaPaciente(idPaciente);

        if (paciente == null) {
            System.out.println("Paciente não encontrado!");
            return;
        }

        sistemaConsultas.realizarConsulta(medico, paciente);
    }
}
