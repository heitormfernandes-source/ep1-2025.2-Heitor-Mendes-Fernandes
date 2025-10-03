package entities;

public class Medico {
    private String nome;
    private String crm;
    private String especialidade;
    private double preco;
    private int idM;

    public int getIdM() {
        return idM;
    }

    public void setIdM(int idM) {
        this.idM = idM;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        if (preco > 0) {
            this.preco = preco;
        }
        else {
            throw new IllegalArgumentException("Preço inválido. Deve ser maior que 0");
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome.matches("[a-zA-Z\s]+")) {
            this.nome = nome;
        }
        else {
            throw new IllegalArgumentException("Nome inválido. Digite apenas letras.");
        }
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        if (especialidade.matches("[a-zA-Z\s]+")) {
            this.especialidade = especialidade;
        }
        else {
            throw new IllegalArgumentException("Nome inválido. Deve conter apenas letras.");
        }
    }
    public Medico(){
    }
    public Medico(int idM, String nome, String crm, String especialidade, double preco){
        this.idM=idM;
        setNome(nome);
        setCrm(crm);
        setEspecialidade(especialidade);
        setPreco(preco);
    }
}
