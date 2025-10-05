package adm;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import entities.Paciente;
import entities.PlanoSaude;

public class AdmPaciente {
    private ArrayList<Paciente> pacientes = new ArrayList<>();
    private int nextIdP = 1;
    private Scanner sc = new Scanner(System.in);
    private final String File_name = "dados/pacientes.csv";

    public AdmPaciente() {
        carregarDados();
    }

    public void menuAdmPaciente() {
        int opcao = 0;
        do {
            System.out.println("\nMENU ADMINISTRAÇÃO DE PACIENTES:");
            System.out.println("1 - Cadastrar Paciente");
            System.out.println("2 - Listar Todos os Pacientes");
            System.out.println("3 - Buscar Paciente por ID");
            System.out.println("0 - Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = Integer.parseInt(sc.nextLine());

                switch (opcao) {
                    case 1:
                        cadastrarPaciente();
                        break;
                    case 2:
                        listarPacientes();
                        break;
                    case 3:
                        buscarPacientePorId();
                        break;
                    case 0:
                        System.out.println("Voltando ao menu principal...");
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            }
            catch(NumberFormatException e){
                System.out.println("Digite uma entrada válida!");
            }
        } while (opcao != 0);
    }

    private void cadastrarPaciente() {
        Paciente paciente = new Paciente();
        paciente.setId(nextIdP++);
        while (true) {
            try {
                System.out.print("Digite o nome: ");
                paciente.setNome(sc.nextLine());
                break;
            }
            catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        while (true) {
            try {
                System.out.print("Digite o CPF (11 dígitos): ");
                paciente.setCpf(sc.nextLine());
                break;
            }
            catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        while (true) {
            try {
                System.out.print("Digite a idade do paciente: ");
                paciente.setIdade(Integer.parseInt(sc.nextLine()));
                break;
            }
            catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        while (true) {
            System.out.print("O paciente possui plano de saúde? (S/N): ");
            String resposta = sc.nextLine().trim().toUpperCase();

            if (resposta.equals("S")) {
                // Listar os planos disponíveis
                AdmPlanoSaude admPlano = new AdmPlanoSaude();
                admPlano.listarPlanos();

                // Escolher um plano
                System.out.print("Informe o ID do plano escolhido: ");
                int idPlano = Integer.parseInt(sc.nextLine());

                PlanoSaude planoEscolhido = null;
                for (PlanoSaude plano : admPlano.getPlanos()) {
                    if (plano.getId() == idPlano) {
                        planoEscolhido = plano;
                        break;
                    }
                }

                if (planoEscolhido != null) {
                    paciente.setPlanoSaude(planoEscolhido); // Define o plano no paciente
                    System.out.println("Plano associado com sucesso!");
                    break;
                } else {
                    System.out.println("Plano não encontrado. Tente novamente.");
                }
            } else if (resposta.equals("N")) {
                paciente.setPlanoSaude(null); // Nenhum plano
                break;
            } else {
                System.out.println("Resposta inválida! Digite S para Sim ou N para Não.");
            }
        }

        pacientes.add(paciente);
        System.out.println("Paciente cadastrado com sucesso! ID: " + paciente.getId());
        salvarDados();
    }

    public void listarPacientes() {
        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }

        System.out.println("\nLista de Pacientes:");
        for (Paciente paciente : pacientes) {
            System.out.println("ID: " + paciente.getId());
            System.out.println("Nome: " + paciente.getNome());
            System.out.println("CPF: " + paciente.getCpf());
            System.out.println("Idade: " + paciente.getIdade());
            System.out.println("-----------------------");
        }
    }

    public Paciente buscarPacientePorId() {
        System.out.print("Digite o ID do paciente para buscar: ");
        int id = Integer.parseInt(sc.nextLine());

        for (Paciente paciente : pacientes) {
            if (paciente.getId() == id) {
                System.out.println("\nPaciente encontrado!");
                System.out.println("ID: " + paciente.getId());
                System.out.println("Nome: " + paciente.getNome());
                System.out.println("CPF: " + paciente.getCpf());
                System.out.println("Idade: " + paciente.getIdade());
                return paciente;
            }
        }
        System.out.println("Paciente com ID " + id + " não encontrado.");
        return null;
    }
    private void carregarDados() {
        File pasta = new File("dados");
        if (!pasta.exists()) {
            pasta.mkdir();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(File_name))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split("--");
                Paciente paciente = new Paciente(
                        Integer.parseInt(campos[0]),
                        campos[1],
                        campos[2],
                        Integer.parseInt(campos[3])
                );
                pacientes.add(paciente);
                nextIdP = Math.max(nextIdP, paciente.getId() + 1);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Arquivo de pacientes não existe ainda. Será criado na primeira gravação.");
        }
        catch (IOException e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
        }

    }
    private void salvarDados() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(File_name))) {
            for (Paciente paciente : pacientes) {
                String linha = paciente.getId() + "--" + paciente.getNome() + "--" +
                        paciente.getCpf() + "--" + paciente.getIdade();
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    public Paciente buscaPaciente(int idPaciente){
        for (Paciente paciente : pacientes) {
            if (idPaciente == paciente.getId()) {
                System.out.println("\nPaciente encontrado!");
                System.out.println("ID: " + paciente.getId());
                System.out.println("Nome: " + paciente.getNome());
                System.out.println("CPF: " + paciente.getCpf());
                System.out.println("Idade: " + paciente.getIdade());
                return paciente;
            }
        }
        return null;
    }
}