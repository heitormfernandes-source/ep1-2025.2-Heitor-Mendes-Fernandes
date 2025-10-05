package entities;

public class PlanoSaude {
    private int id;
    private String nome;
    private double desconto;

    public PlanoSaude(int id, String nome, double desconto) {
        this.id = id;
        this.nome = nome;
        this.desconto = desconto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        if(desconto > 0 && desconto < 1){
        this.desconto = desconto;}
        else{
            throw new IllegalArgumentException("O desconto deve estar entre 0 e 1.");
        }
    }
    @Override
    public String toString(){
        return "Plano" + nome + "(Desconto:" + desconto * 100 + "%)";
    }

}
