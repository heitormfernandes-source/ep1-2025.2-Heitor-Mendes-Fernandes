package entities;

import adm.AdmInternacao;
import java.util.ArrayList;
import java.util.List;

public class Paciente extends Pessoa {
    private String cpf;
    private int idade;
    private int id;
    private PlanoSaude planoSaude;
    private static List<Consulta> historicoConsultas = new ArrayList<>();
    private static List<Internacao> historicoInternacoes = new ArrayList<>();

    public Paciente() {
        super();
    }

    public Paciente(int id, String nome, String cpf, int idade) {
        super(nome);
        this.id = id;
        setCpf(cpf);
        setIdade(idade);
    }

    public PlanoSaude getPlanoSaude() {
        return planoSaude;
    }

    public void setPlanoSaude(PlanoSaude planoSaude) {
        this.planoSaude = planoSaude;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido. Deve conter exatamente 11 dígitos.");
        }
        this.cpf = cpf;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        if (idade < 0 || idade > 125) {
            throw new IllegalArgumentException("Idade inválida. Deve estar entre 0 e 125.");
        }
        this.idade = idade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void adicionarConsulta(Consulta consulta) {
        historicoConsultas.add(consulta);
    }

    public static List<Consulta> getHistoricoConsultas() {
        return new ArrayList<>(historicoConsultas);
    }

    public void adicionarInternacao(Internacao internacao) {
        historicoInternacoes.add(internacao);
    }

    public static List<Internacao> getHistoricoInternacoes() {
        return new ArrayList<>(historicoInternacoes);
    }
}