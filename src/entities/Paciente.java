package entities;

import adm.AdmInternacao;

import java.util.ArrayList;
import java.util.List;

public class Paciente {
    private String nome;
    private String cpf;
    private int idade;
    private int id;
    private PlanoSaude planoSaude;
    private List<Consulta> historicoConsultas = new ArrayList<>();

    public PlanoSaude getPlanoSaude() {
        return planoSaude;
    }

    public void setPlanoSaude(PlanoSaude planoSaude) {
        this.planoSaude = planoSaude;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome.matches("[a-zA-Z\s]+")) {
            this.nome = nome;
        }
        else {
            throw new IllegalArgumentException("Nome inválido. Dígite apenas letras.");
        }
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf.matches("\\d{11}")) {
            this.cpf = cpf;
        }
        else {
            throw new IllegalArgumentException("CPF inválido. Deve conter exatamente 11 dígitos.");
        }
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        if (idade >= 0 && idade <= 125) {
            this.idade = idade;
        } else {
            throw new IllegalArgumentException("Idade inválida. Deve estar entre 0 e 125.");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Paciente() {
    }

    public Paciente(int id, String nome, String cpf, int idade) {
        this.id = id;
        setNome(nome);
        setCpf(cpf);
        setIdade(idade);
    }
    public void adicionarConsulta(Consulta consulta) {
        historicoConsultas.add(consulta);
    }
    public List<Consulta> getHistoricoConsultas() {
        return new ArrayList<>(historicoConsultas);
    }
    public List<Internacao> getHistoricoInternacoes(AdmInternacao admInternacao) {
        if (admInternacao == null) {
            return new ArrayList<>();
        }
        return admInternacao.getInternacoes().stream()
                .filter(i -> i.getPaciente() != null && i.getPaciente().equals(this))
                .toList();
    }
    private List<Internacao> historicoInternacoes = new ArrayList<>();

    public void adicionarInternacao(Internacao internacao) {
        historicoInternacoes.add(internacao);
    }

    public List<Internacao> getHistoricoInternacoes() {
        return new ArrayList<>(historicoInternacoes);
    }

}