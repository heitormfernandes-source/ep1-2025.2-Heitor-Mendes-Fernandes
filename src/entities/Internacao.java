package entities;

import java.time.LocalDate;

public class Internacao {
    private int id;
    private Paciente paciente;
    private Medico medicoResponsavel;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private String motivo;
    private String quarto;
    private boolean alta;

    public Internacao(int id, Paciente paciente, Medico medicoResponsavel, LocalDate dataEntrada, String motivo, String quarto) {
        this.id = id;
        this.paciente = paciente;
        this.medicoResponsavel = medicoResponsavel;
        this.dataEntrada = dataEntrada;
        this.motivo = motivo;
        this.quarto = quarto;
        this.alta = false;
    }

    public int getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedicoResponsavel() {
        return medicoResponsavel;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getQuarto() {
        return quarto;
    }

    public boolean isAlta() {
        return alta;
    }

    public void darAlta(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
        this.alta = true;
    }

    @Override
    public String toString() {
        return "Internação ID: " + id +
                "\nPaciente: " + paciente.getNome() +
                "\nMédico Responsável: " + medicoResponsavel.getNome() +
                "\nData de Entrada: " + dataEntrada +
                (alta ? "\nData de Saída: " + dataSaida : "") +
                "\nMotivo: " + motivo +
                "\nQuarto: " + quarto +
                "\nStatus: " + (alta ? "ALTA" : "INTERNADO");
    }
}