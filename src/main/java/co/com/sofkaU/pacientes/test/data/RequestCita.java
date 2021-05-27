package co.com.sofkaU.pacientes.test.data;

import co.com.sofkaU.pacientes.test.model.JsonCitas;
import com.github.javafaker.Faker;

import java.security.SecureRandom;
import java.util.Locale;

public class RequestCita {
    private static Faker faker = Faker.instance(new Locale("es", "CO"), new SecureRandom());

    public static JsonCitas getCitasInformation(){
            return JsonCitas.builder()
                    .conclude(true)
                    .date("05/08/2022")
                    .patientId(Long.parseLong("642336182"))
                    .recommendations("ayunar1")
                    .time("8:32 AM")
                    .build();
        }
    public static JsonCitas getCitasInformationInvalida(){
        return JsonCitas.builder()
                .conclude(true)
                .date("")
                .patientId(Long.parseLong("1"))
                .recommendations("s")
                .time("")
                .build();
    }

    public static JsonCitas getCitasInformationSinPatientId(){
        return JsonCitas.builder()
                .conclude(true)
                .date("oNJPDFP")
                .patientId(Long.parseLong("12345"))
                .recommendations("ANVFJWH")
                .time("vnwp")
                .build();
    }
}
