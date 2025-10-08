package entities;

public abstract class Pessoa {
    public String nome;

    public Pessoa() {
    }

    public Pessoa(String nome) {
        setNome(nome);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || !nome.matches("[a-zA-Z\s]+")) {
            throw new IllegalArgumentException("Nome inválido. Deve conter apenas letras e espaços.");
        }
        this.nome = nome.trim();
    }
}