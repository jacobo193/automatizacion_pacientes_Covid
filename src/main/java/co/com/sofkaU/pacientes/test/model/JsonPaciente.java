package co.com.sofkaU.pacientes.test.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class JsonPaciente {

    private String address;
    private Long cellPhoneNumber;
    private String email;
    private String firstName;
    private String lastName;
    private Long patientId;


}
