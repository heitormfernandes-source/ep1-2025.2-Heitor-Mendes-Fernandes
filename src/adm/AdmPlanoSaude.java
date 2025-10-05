package adm;

import entities.Medico;
import entities.PlanoSaude;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AdmPlanoSaude {
    private ArrayList<PlanoSaude> planos = new ArrayList<>();
    private int nextId = 1;
    private Scanner sc = new Scanner(System.in);
    private final String File_NAME =("dados/PlanoSaude.csv");

    public AdmPlanoSaude(){
        carregarDados();
    }

    public void menuAdmPlano() {
        int opcao = -1;
        do {
            System.out.println("\nMENU ADMINISTRAÇÃO DE PLANOS DE SAÚDE:");
            System.out.println("1 - Cadastrar Plano de Saúde");
            System.out.println("2 - Listar Todos os Planos");
            System.out.println("3 - Buscar Plano por ID");
            System.out.println("0 - Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());

                switch (opcao) {
                    case 1:
                        cadastrarPlano();
                        break;
                    case 2:
                        listarPlanos();
                        break;
                    case 3:
                        buscarPlanoPorId();
                        break;
                    case 0:
                        System.out.println("Voltando ao menu principal...");
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número.");
            }
        } while (opcao != 0);
    }

    private void cadastrarPlano() {
        System.out.print("Digite o nome do plano: ");
        String nome = sc.nextLine();

        double desconto;
        while (true) {
            try {
                System.out.print("Digite o desconto aplicado pelo plano (em %): ");
                desconto = Double.parseDouble(sc.nextLine()) / 100;
                if (desconto >= 0 && desconto <= 1) {
                    break;
                } else {
                    System.out.println("O desconto deve estar entre 0 e 100%.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro! Insira um valor numérico.");
            }
        }

        PlanoSaude plano = new PlanoSaude(nextId++, nome, desconto);
        planos.add(plano);

        System.out.println("Plano cadastrado com sucesso! ID: " + plano.getId());
        salvarDados();
    }

    public ArrayList<PlanoSaude> getPlanos() {
        return planos; // Retorna a lista de planos
    }

    public void listarPlanos() {
        if (planos.isEmpty()) {
            System.out.println("Nenhum plano de saúde cadastrado.");
            return;
        }

        System.out.println("\nLista de Planos de Saúde:");
        for (PlanoSaude plano : planos) {
            System.out.println("ID: " + plano.getId() + " -> " + plano.getNome() +
                    " (Desconto: " + (plano.getDesconto() * 100) + "%)");
        }
    }

    private void buscarPlanoPorId() {
        System.out.print("Digite o ID do plano: ");
        int id = Integer.parseInt(sc.nextLine());

        for (PlanoSaude plano : planos) {
            if (plano.getId() == id) {
                System.out.println("Plano encontrado: " + plano.getNome());
                return;
            }
        }
        System.out.println("Plano de saúde com ID " + id + " não encontrado.");
    }

    private void carregarDados() {
        File pasta = new File("dados");
        if (!pasta.exists()) {
            pasta.mkdir();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(File_NAME))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split("--");
                PlanoSaude plano = new PlanoSaude(
                        Integer.parseInt(campos[0]),
                        campos[1],
                        Double.parseDouble(campos[2])
                );
                planos.add(plano);
                nextId = Math.max(nextId, plano.getId() + 1);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Arquivo de plano não existe ainda. Será criado na primeira gravação.");
        }
        catch (IOException e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
        }

    }

    private void salvarDados() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(File_NAME))) {
            for (PlanoSaude plano : planos) {
                String linha = plano.getId() + "--" + plano.getNome() + "--" +
                        plano.getDesconto();
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }
}