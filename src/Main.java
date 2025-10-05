import adm.AdmInternacao;
import adm.AdmMedico;
import adm.AdmPaciente;
import adm.AdmPlanoSaude;
import entities.Medico;
import entities.Paciente;
import entities.SistemaConsultas;

import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static AdmPaciente admPaciente = new AdmPaciente();
    private static AdmMedico admMedico = new AdmMedico();
    private static AdmPlanoSaude admPlanoSaude = new AdmPlanoSaude();
    private static SistemaConsultas sistemaConsultas = new SistemaConsultas();
    private static AdmInternacao admInternacao = new AdmInternacao();

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\nMENU PRINCIPAL:");
            System.out.println("1 - Administrar de Pacientes");
            System.out.println("2 - Administrar de Médicos");
            System.out.println("3 - Administrar de Planos de Saúde");
            System.out.println("4 - Realizar Consulta");
            System.out.println("5 - Listar Todas as Consultas");
            System.out.println("6 - Administrar Internações");
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
                    admPlanoSaude.menuAdmPlano();
                    break;
                case 4:
                    realizarConsultaMenu();
                    break;
                case 5:
                    listarConsultasMenu();
                    break;
                case 6:
                    admInternacao.menuAdmInternacao(admPaciente, admMedico);
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

    public static void realizarConsultaMenu() {
        admMedico.listarMedicos();
        System.out.print("Digite o ID do médico para consulta: ");
        int idMedico = Integer.parseInt(sc.nextLine());
        Medico medico = admMedico.buscaMedico(idMedico);

        if (medico == null) {
            System.out.println("Médico não encontrado.");
            return;
        }

        admPaciente.listarPacientes();
        System.out.print("Digite o ID do paciente: ");
        int idPaciente = Integer.parseInt(sc.nextLine());
        Paciente paciente = admPaciente.buscaPaciente(idPaciente);

        if (paciente == null) {
            System.out.println("Paciente não encontrado.");
            return;
        }

        sistemaConsultas.realizarConsulta(medico, paciente);
    }

    public static void listarConsultasMenu() {
        System.out.println("\nConsultas Agendadas:");
        sistemaConsultas.getConsultasAgendadas().forEach(System.out::println);
    }
}