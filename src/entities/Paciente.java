package entities;

public class Paciente {
    private String nome;
    private String cpf;
    private int idade;
    private int id;

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
}