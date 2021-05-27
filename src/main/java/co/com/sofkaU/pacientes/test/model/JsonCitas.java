package co.com.sofkaU.pacientes.test.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JsonCitas {
    private boolean conclude;
    private String date;
    private Long patientId;
    private String recommendations;
    private String time;


}
