package adm;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import entities.Consulta;
import entities.Medico;
import entities.Paciente;
import entities.SistemaConsultas;

public class AdmConsulta {
    private ArrayList<Consulta> consultas = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
    private final String File_Name = "dados/Consultas.csv";
    private SistemaConsultas sistemaConsultas = new SistemaConsultas();

    public AdmConsulta() {
        carregarDados();
    }

    public void menuAdmConsulta(AdmPaciente admPaciente, AdmMedico admMedico) {
        int opcao = -1;
        do {
            System.out.println("\nMENU DE CONSULTAS:");
            System.out.println("1 - Agendar Consulta");
            System.out.println("2 - Listar Todas as Consultas");
            System.out.println("3 - Buscar Consulta por Médico");
            System.out.println("4 - Buscar Consulta por Paciente");
            System.out.println("5 - Marcar Consulta como Realizada");
            System.out.println("6 - Listar Horários Disponíveis");
            System.out.println("0 - Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());

                switch (opcao) {
                    case 1:
                        agendarConsulta(admMedico, admPaciente);
                        break;
                    case 2:
                        listarConsultas();
                        break;
                    case 3:
                        buscarConsultaPorMedico(admMedico);
                        break;
                    case 4:
                        buscarConsultaPorPaciente(admPaciente);
                        break;
                    case 5:
                        marcarConsultaComoRealizada();
                        break;
                    case 6:
                        sistemaConsultas.listarHorariosDisponiveis();
                        break;
                    case 0:
                        System.out.println("Voltando ao menu principal...");
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Digite uma entrada válida!");
            }
        } while (opcao != 0);
    }

    private void agendarConsulta(AdmMedico admMedico, AdmPaciente admPaciente) {
        admMedico.listarMedicos();
        System.out.print("Digite o ID do médico: ");
        int idMedico = Integer.parseInt(sc.nextLine());
        Medico medico = admMedico.buscaMedico(idMedico);

        if (medico == null) {
            System.out.println("Médico não encontrado!");
            return;
        }

        admPaciente.listarPacientes();
        System.out.print("Digite o ID do paciente: ");
        int idPaciente = Integer.parseInt(sc.nextLine());
        Paciente paciente = admPaciente.buscaPaciente(idPaciente);

        if (paciente == null) {
            System.out.println("Paciente não encontrado!");
            return;
        }

        sistemaConsultas.listarHorariosDisponiveis();
        System.out.print("Digite o horário da consulta (HH:MM): ");
        String horarioStr = sc.nextLine();
        LocalTime horario = LocalTime.parse(horarioStr);

        if (sistemaConsultas.verificarConflitoHorario(medico, horario)) {
            System.out.println("Médico já tem consulta agendada neste horário!");
            return;
        }

        Consulta consulta = new Consulta(medico, paciente, horario);
        consultas.add(consulta);
        sistemaConsultas.getConsultasAgendadas().add(consulta);
        paciente.adicionarConsulta(consulta);

        System.out.println("Consulta agendada com sucesso!");
        salvarDados();
    }



    private void buscarConsultaPorMedico(AdmMedico admMedico) {
        admMedico.listarMedicos();
        System.out.print("Digite o ID do médico: ");
        int idMedico = Integer.parseInt(sc.nextLine());

        boolean encontrou = false;
        for (Consulta consulta : consultas) {
            if (consulta.getMedico().getIdM() == idMedico) {
                System.out.println(consulta);
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhuma consulta encontrada para este médico.");
        }
    }

    private void buscarConsultaPorPaciente(AdmPaciente admPaciente) {
        admPaciente.listarPacientes();
        System.out.print("Digite o ID do paciente: ");
        int idPaciente = Integer.parseInt(sc.nextLine());

        boolean encontrou = false;
        for (Consulta consulta : consultas) {
            if (consulta.getPaciente().getId() == idPaciente) {
                System.out.println(consulta);
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhuma consulta encontrada para este paciente.");
        }
    }

    private void marcarConsultaComoRealizada() {
        listarConsultas();
        System.out.print("Digite o índice da consulta a ser marcada como realizada: ");
        int indice = Integer.parseInt(sc.nextLine());

        if (indice >= 0 && indice < consultas.size()) {
            consultas.get(indice).setRealizada(true);
            System.out.println("Consulta marcada como realizada!");
            salvarDados();
        } else {
            System.out.println("Índice inválido!");
        }
    }
    private void listarConsultas() {
        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta agendada.");
            return;
        }

        System.out.println("\nLista de Consultas:");
        for (Consulta consulta : consultas) {
            System.out.println(consulta.toString());
            System.out.printf("Valor com desconto: R\\$%.2f\n", consulta.getValorComDesconto());
        }
    }

    private void carregarDados() {
        File pasta = new File("dados");
        if (!pasta.exists()) {
            pasta.mkdir();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(File_Name))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split("--");
                // Implemente a lógica para reconstruir as consultas a partir do arquivo
                // Você precisará buscar o médico e paciente pelos IDs
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de consultas não existe ainda. Será criado na primeira gravação.");
        } catch (IOException e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
        }
    }

    private void salvarDados() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(File_Name))) {
            for (Consulta consulta : consultas) {
                String linha = consulta.getMedico().getIdM() + "--" +
                        consulta.getPaciente().getId() + "--" +
                        consulta.getHorario().toString() + "--" +
                        consulta.isRealizada();
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }
}