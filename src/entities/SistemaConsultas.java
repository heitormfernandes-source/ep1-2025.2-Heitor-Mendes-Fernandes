package entities;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SistemaConsultas {
    private List<Consulta> consultasAgendadas;
    private static final List<LocalTime> HORARIOS_DISPONIVEIS;

    static {
        HORARIOS_DISPONIVEIS = new ArrayList<>();
        for (int i = 8; i <= 11; i++) {
            HORARIOS_DISPONIVEIS.add(LocalTime.of(i, 0));
        }
        for (int i = 13; i <= 17; i++) {
            HORARIOS_DISPONIVEIS.add(LocalTime.of(i, 0));
        }
    }

    public SistemaConsultas() {
        this.consultasAgendadas = new ArrayList<>();
    }

    public void realizarConsulta(Medico medico, Paciente paciente) {
        System.out.println("\nHorários disponíveis:");
        listarHorariosDisponiveis();

        System.out.print("Digite o horário da consulta (HH:MM): ");
        LocalTime horario = LocalTime.parse(System.console().readLine());

        if (!HORARIOS_DISPONIVEIS.contains(horario)) {
            System.out.println("Horário inválido!");
            return;
        }

        if (verificarConflitoHorario(medico, horario)) {
            System.out.println("Médico já tem consulta neste horário!");
            return;
        }

        Consulta novaConsulta = new Consulta(medico, paciente, horario);
        consultasAgendadas.add(novaConsulta);
        paciente.adicionarConsulta(novaConsulta);
        System.out.println("Consulta agendada com sucesso!");
    }

    public boolean verificarConflitoHorario(Medico medico, LocalTime horario) {
        return consultasAgendadas.stream()
                .anyMatch(c -> c.getMedico().equals(medico) && c.getHorario().equals(horario));
    }

    public void listarHorariosDisponiveis() {
        HORARIOS_DISPONIVEIS.forEach(System.out::println);
    }

    public List<Consulta> getConsultasAgendadas() {
        return consultasAgendadas;
    }
}