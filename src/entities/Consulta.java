package entities;

import java.time.LocalTime;

public class Consulta {
    private Medico medico;
    private Paciente paciente;
    private LocalTime horario;
    private boolean realizada;

    public Consulta(Medico medico, Paciente paciente, LocalTime horario) {
        this.medico = medico;
        this.paciente = paciente;
        this.horario = horario;
        this.realizada = false;
    }

    public Medico getMedico() {
        return medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public boolean isRealizada() {
        return realizada;
    }

    public void setRealizada(boolean realizada) {
        this.realizada = realizada;
    }

    public double getValorComDesconto() {
        double valorOriginal = medico.getPreco();
        if (paciente != null && paciente.getPlanoSaude() != null) {
            double desconto = paciente.getPlanoSaude().getDesconto();
            return valorOriginal * (1 - desconto);
        }
        return valorOriginal;
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "Médico: " + medico.getNome() +
                ", Paciente: " + paciente.getNome() +
                ", Horário: " + horario +
                ", Realizada: " + (realizada ? "Sim" : "Não") + "}";
    }
}