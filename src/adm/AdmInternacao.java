package adm;

import entities.Internacao;
import entities.Medico;
import entities.Paciente;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdmInternacao {
    private List<Internacao> internacoes;
    private int nextId;
    private Scanner sc;
    private final String FILE_NAME = "dados/Internacoes.csv";

    public AdmInternacao() {
        this.internacoes = new ArrayList<>();
        this.sc = new Scanner(System.in);
        this.nextId = 1;
        carregarDados();
    }

    public void menuAdmInternacao(AdmPaciente admPaciente, AdmMedico admMedico) {
        int opcao = -1;
        do {
            System.out.println("\nMENU DE INTERNAÇÕES:");
            System.out.println("1 - Nova Internação");
            System.out.println("2 - Listar Todas as Internações");
            System.out.println("3 - Buscar Internação por Paciente");
            System.out.println("4 - Dar Alta a Paciente");
            System.out.println("5 - Listar Pacientes Internados");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());

                switch(opcao) {
                    case 1:
                        novaInternacao(admPaciente, admMedico);
                        break;
                    case 2:
                        listarInternacoes();
                        break;
                    case 3:
                        buscarPorPaciente(admPaciente);
                        break;
                    case 4:
                        darAlta();
                        break;
                    case 5:
                        listarInternados();
                        break;
                    case 0:
                        System.out.println("Voltando...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch(NumberFormatException e) {
                System.out.println("Digite um número válido!");
            }
        } while(opcao != 0);
    }

    private void novaInternacao(AdmPaciente admPaciente, AdmMedico admMedico) {
        System.out.println("\nNOVA INTERNAÇÃO:");

        admPaciente.listarPacientes();
        System.out.print("ID do Paciente: ");
        int idPaciente = Integer.parseInt(sc.nextLine());
        Paciente paciente = admPaciente.buscaPaciente(idPaciente);

        if(paciente == null) {
            System.out.println("Paciente não encontrado!");
            return;
        }

        if(isInternado(paciente)) {
            System.out.println("Paciente já está internado!");
            return;
        }

        admMedico.listarMedicos();
        System.out.print("ID do Médico Responsável: ");
        int idMedico = Integer.parseInt(sc.nextLine());
        Medico medico = admMedico.buscaMedico(idMedico);

        if(medico == null) {
            System.out.println("Médico não encontrado!");
            return;
        }

        LocalDate dataEntrada = LocalDate.now();

        System.out.print("Motivo da Internação: ");
        String motivo = sc.nextLine();

        System.out.print("Número do Quarto: ");
        String quarto = sc.nextLine();

        Internacao internacao = new Internacao(nextId++, paciente, medico, dataEntrada, motivo, quarto);
        internacoes.add(internacao);
        System.out.println("Paciente internado com sucesso! ID: " + internacao.getId());
        paciente.adicionarInternacao(internacao);

        salvarDados();
    }

    private void listarInternacoes() {
        if(internacoes.isEmpty()) {
            System.out.println("Nenhuma internação registrada.");
            return;
        }

        System.out.println("\nLISTA DE INTERNAÇÕES:");
        internacoes.forEach(System.out::println);
    }

    private void buscarPorPaciente(AdmPaciente admPaciente) {
        admPaciente.listarPacientes();
        System.out.print("ID do Paciente: ");
        int idPaciente = Integer.parseInt(sc.nextLine());
        Paciente paciente = admPaciente.buscaPaciente(idPaciente);

        if(paciente == null) {
            System.out.println("Paciente não encontrado!");
            return;
        }

        List<Internacao> internacoesPaciente = internacoes.stream()
                .filter(i -> i.getPaciente().equals(paciente))
                .toList();

        if(internacoesPaciente.isEmpty()) {
            System.out.println("Nenhuma internação encontrada para este paciente.");
        } else {
            System.out.println("\nINTERNAÇÕES DO PACIENTE:");
            internacoesPaciente.forEach(System.out::println);
        }
    }

    private void darAlta() {
        listarInternados();
        System.out.print("ID da Internação para dar alta: ");
        int idInternacao = Integer.parseInt(sc.nextLine());

        Internacao internacao = internacoes.stream()
                .filter(i -> i.getId() == idInternacao && !i.isAlta())
                .findFirst()
                .orElse(null);

        if(internacao == null) {
            System.out.println("Internação não encontrada ou paciente já teve alta!");
            return;
        }

        internacao.darAlta(LocalDate.now());
        System.out.println("Alta registrada com sucesso para " + internacao.getPaciente().getNome());

        salvarDados();
    }

    private void listarInternados() {
        List<Internacao> internados = internacoes.stream()
                .filter(i -> !i.isAlta())
                .toList();

        if(internados.isEmpty()) {
            System.out.println("Nenhum paciente internado no momento.");
        } else {
            System.out.println("\nPACIENTES INTERNADOS:");
            internados.forEach(i -> System.out.println(
                    "ID: " + i.getId() +
                            " | Paciente: " + i.getPaciente().getNome() +
                            " | Quarto: " + i.getQuarto() +
                            " | Entrada: " + i.getDataEntrada()
            ));
        }
    }

    private boolean isInternado(Paciente paciente) {
        return internacoes.stream()
                .anyMatch(i -> i.getPaciente().equals(paciente) && !i.isAlta());
    }
    public List<Internacao> getInternacoes() {
        return new ArrayList<>(internacoes);
    }

    private void carregarDados() {
        File pasta = new File("dados");
        if(!pasta.exists()) {
            pasta.mkdir();
        }

        try(BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String linha;

            while((linha = br.readLine()) != null) {
                String[] campos = linha.split("--");

                // Implementar conforme sua estrutura de dados
                // Exemplo: id--idPaciente--idMedico--dataEntrada--dataSaida--motivo--quarto--alta
            }
        } catch(FileNotFoundException e) {
            System.out.println("Arquivo de internações não existe ainda. Será criado.");
        } catch(IOException e) {
            System.out.println("Erro ao carregar internações: " + e.getMessage());
        }
    }

    private void salvarDados() {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for(Internacao i : internacoes) {
                String linha = String.join("--",
                        String.valueOf(i.getId()),
                        String.valueOf(i.getPaciente().getId()),
                        String.valueOf(i.getMedicoResponsavel().getIdM()),
                        i.getDataEntrada().format(formatter),
                        (i.getDataSaida() != null ? i.getDataSaida().format(formatter) : "null"),
                        i.getMotivo(),
                        i.getQuarto(),
                        String.valueOf(i.isAlta())
                );
                bw.write(linha);
                bw.newLine();
            }
        } catch(IOException e) {
            System.out.println("Erro ao salvar internações: " + e.getMessage());
        }
    }
}
