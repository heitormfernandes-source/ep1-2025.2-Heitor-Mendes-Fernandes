package entities;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Medico extends Pessoa {
    private String crm;
    private String especialidade;
    private double preco;
    private int idM;
    private Map<LocalDateTime, Boolean> agenda = new HashMap<>();

    public Medico() {
        super();
    }

    public Medico(int idM, String nome, String crm, String especialidade, double preco) {
        super(nome);
        this.idM = idM;
        setCrm(crm);
        setEspecialidade(especialidade);
        setPreco(preco);
        inicializarAgenda();
    }

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
        } else {
            throw new IllegalArgumentException("Preço inválido. Deve ser maior que 0");
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
        if (especialidade == null || !especialidade.matches("[a-zA-Z\s]+")) {
            throw new IllegalArgumentException("Especialidade inválida. Deve conter apenas letras.");
        }
        this.especialidade = especialidade;
    }

    private void inicializarAgenda() {
        LocalDateTime hoje = LocalDateTime.now().toLocalDate().atStartOfDay();

        Agenda agendaDia = new Agenda(hoje);
        agenda.putAll(agendaDia.getHorarios());
    }

    public boolean agendarConsulta(LocalDateTime dataHora) {
        if (agenda.containsKey(dataHora) && !agenda.get(dataHora)) {
            agenda.put(dataHora, true);
            return true;
        }
        return false;
    }

    public void exibirHorariosDisponiveis() {
        System.out.println("Horários disponíveis para o Dr. " + nome + ":");
        for (LocalDateTime dataHora : agenda.keySet()) {
            if (!agenda.get(dataHora)) {
                System.out.println(dataHora);
            }
        }
    }
}
