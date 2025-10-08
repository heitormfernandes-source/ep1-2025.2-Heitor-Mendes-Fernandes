package adm;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import entities.Medico;

public class AdmMedico {
    ArrayList<Medico> medicos = new ArrayList<>();
    private int nextIdM = 1;
    private Scanner sc = new Scanner(System.in);
    private final String File_Name =("dados/Medicos.csv");

    public AdmMedico(){
        carregarDados();
    }

    public void menuAdmMedico() {
        int opcao = 0;
        do {
            System.out.println("\nMENU ADMINISTRAÇÃO DE PACIENTES:");
            System.out.println("1 - Cadastrar Médico");
            System.out.println("2 - Listar Todos os Médicos");
            System.out.println("3 - Buscar Médico por ID");
            System.out.println("0 - Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = Integer.parseInt(sc.nextLine());

                switch (opcao) {
                    case 1:
                        cadastrarMedico();
                        break;
                    case 2:
                        listarMedicos();
                        break;
                    case 3:
                        buscarMedicoPorId();
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

    public void cadastrarMedico(){
        Medico medico = new Medico();
        medico.setIdM(nextIdM++);
        while(true){
            try{
                System.out.print("Digite o nome:");
                medico.setNome(sc.nextLine());
                break;
            }
            catch (IllegalArgumentException e) {
                System.out.print("Erro: " + e.getMessage());
            }
        }
       System.out.print("Digite o crm: ");
        medico.setCrm(sc.nextLine());
        while(true){
            try{
                System.out.print("Qual a especialidade? ");
                medico.setEspecialidade(sc.nextLine());
                break;
            }
            catch(IllegalArgumentException e){
                System.out.print("Erro: " + e.getMessage());
            }
        }
        while(true){
            try{
                System.out.print("Qual valor da consulta? ");
                medico.setPreco(Integer.parseInt(sc.nextLine()));
                break;
            }
            catch (IllegalArgumentException e){
                System.out.print("Erro: " + e.getMessage());
            }
        }
        medicos.add(medico);
        System.out.print("Médico registrado com sucesso. ID do médico: " + medico.getIdM());
        salvarDados();
    }

    public void listarMedicos() {
        if (medicos.isEmpty()) {
            System.out.println("Nenhum medico cadastrado.");
            return;
        }
        System.out.println("\nLista de Médicos:");
        for (Medico medico : medicos) {
            System.out.println("ID: " + medico.getIdM());
            System.out.println("Nome: " + medico.getNome());
            System.out.println("CRM: " + medico.getCrm());
            System.out.println("Especialidade: " + medico.getEspecialidade());
            System.out.println("Custo da consulta: " + medico.getPreco());
            System.out.println("-----------------------");
        }
    }

    public Medico buscarMedicoPorId() {
        System.out.print("Digite o ID do medico para buscar: ");
        int idM = Integer.parseInt(sc.nextLine());

        for (Medico medico : medicos) {
            if (medico.getIdM() == idM) {
                System.out.println("\nMédico encontrado!");
                System.out.println("ID: " + medico.getIdM());
                System.out.println("Nome: " + medico.getNome());
                System.out.println("CRM: " + medico.getCrm());
                System.out.println("Especialidade: " + medico.getEspecialidade());
                System.out.println("Valor da consulta: " + medico.getPreco());
                return medico;
            }
        }
        System.out.println("Médico com ID " + idM + " não encontrado.");
        return null;
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
                Medico medico = new Medico(
                        Integer.parseInt(campos[0]),
                        campos[1],
                        campos[2],
                        campos[3],
                        Double.parseDouble(campos[4])
                );
                medicos.add(medico);
                nextIdM = Math.max(nextIdM, medico.getIdM() + 1);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Arquivo de médico não existe ainda. Será criado na primeira gravação.");
        }
        catch (IOException e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
        }

    }
    private void salvarDados() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(File_Name))) {
            for (Medico medico : medicos) {
                String linha = medico.getIdM() + "--" + medico.getNome() + "--" +
                        medico.getCrm() + "--" + medico.getEspecialidade() + "--" + medico.getPreco() ;
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    public Medico buscaMedico(int idMedico){
        for (Medico medico : medicos) {
            if (idMedico == medico.getIdM()) {
                System.out.println("\nMédico encontrado!");
                System.out.println("ID: " + medico.getIdM());
                System.out.println("Nome: " + medico.getNome());
                System.out.println("CRM: " + medico.getCrm());
                System.out.println("Especialidade: " + medico.getEspecialidade());
                System.out.println("Valor da consulta: " + medico.getPreco());
                return medico;
            }
        }
        return null;
    }
}
