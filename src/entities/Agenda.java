package entities;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Agenda {
    private Map<LocalDateTime, Boolean> horarios = new HashMap<>();

    public Agenda(LocalDateTime data) {
        LocalDateTime inicio = data.with(LocalTime.of(8, 0));
        while (!inicio.isAfter(data.with(LocalTime.of(11, 0)))) {
            horarios.put(inicio, false);
            inicio = inicio.plusHours(1);
        }

        inicio = data.with(LocalTime.of(13, 0));
        while (!inicio.isAfter(data.with(LocalTime.of(17, 0)))) {
            horarios.put(inicio, false);
            inicio = inicio.plusHours(1);
        }
    }

    public boolean agendarHorario(LocalDateTime dataHora) {
        if (horarios.containsKey(dataHora) && !horarios.get(dataHora)) {
            horarios.put(dataHora, true);
            return true;
        }
        return false;
    }

    public void listarHorariosDisponiveis() {
        System.out.println("Horários disponíveis:");
        for (Map.Entry<LocalDateTime, Boolean> entry : horarios.entrySet()) {
            if (!entry.getValue()) {
                System.out.println(entry.getKey());
            }
        }
    }

    public Map<LocalDateTime, Boolean> getHorarios() {
        return horarios;
    }
}
