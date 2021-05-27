package screenplay.steps;

import co.com.sofkaU.pacientes.test.data.RequestAutenticacion;
import co.com.sofkaU.pacientes.test.data.RequestCita;

import co.com.sofkaU.pacientes.test.model.JsonCitas;

import co.com.sofkaU.pacientes.test.task.http.PostRequestWithIvalidToken;
import co.com.sofkaU.pacientes.test.task.http.PostRequestWithToken;
import co.com.sofkaU.pacientes.test.task.project.SaveToken;
import co.com.sofkaU.pacientes.test.task.project.TokenRequest;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.core.util.EnvironmentVariables;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class citas {
    private Actor actor;
    EnvironmentVariables variables;
    private JsonCitas citaEnviada;
    private JsonCitas citaRecivida;




    @Dado(": que un {string} esta autenticado correctamente")
    public void queUnEstaAutenticadoCorrectamente(String name) {
        actor = Actor.named(name);
        actor.attemptsTo(TokenRequest.executed(RequestAutenticacion.getValueUser()));
        actor.attemptsTo(SaveToken.executed());
    }

    @Y(": el auxiliar de enfermeria conoce la ruta de la api de creacion de citas")
    public void elAuxiliarDeEnfermeriaConoceLaRutaDeLaApiDeCreacionDeCitas() {
        String urlbase = variables.getProperty("baseurl");
        actor.whoCan(CallAnApi.at(urlbase));
    }


    @Cuando(": se tiene la informacion de la cita y el Id de un paciente exisistente")
    public void seTieneLaInformacionDeLaCitaYElIdDeUnPacienteExisistente() {
        citaEnviada = RequestCita.getCitasInformation();
        actor.attemptsTo(PostRequestWithToken.executed(
                variables.getProperty("pathCitas"),
                citaEnviada)
        );
    }

    @Entonces(": se puede crear una cita exitosamente")
    public void sePuedeCrearUnaCitaExitosamente() {
        actor.should(
                seeThatResponse("La api entrego código 201 correctamente",
                        response -> response.statusCode(201)
                )
        );
        citaRecivida = SerenityRest.lastResponse()
                .jsonPath()
                .getObject("", JsonCitas.class);
        assertThat(citaEnviada).isEqualTo(citaRecivida);
    }

    @Cuando(": se tiene la informacion de una cita incompleta  y el Id de un paciente existente")
    public void seTieneLaInformacionDeUnaCitaIncompletaYElIdDeUnPacienteExistente() {
        citaEnviada = RequestCita.getCitasInformationInvalida();
        actor.attemptsTo(PostRequestWithToken.executed(
                variables.getProperty("pathCitas"),
                citaEnviada)//esta deberia fallar
        );
    }

    @Entonces(": no se puede crear una cita exitosamente")
    public void noSePuedeCrearUnaCitaExitosamente() {
        actor.should(
                seeThatResponse("La api entrego código 500 correctamente",
                        response -> response.statusCode(500)
                )
        );
    }

    @Cuando(": dado que el auxiliar no esta autenticado correctamente en la api")
    public void dadoQueElAuxiliarNoEstaAutenticadoCorrectamenteEnLaApi() {
        citaEnviada = RequestCita.getCitasInformation();
        actor.attemptsTo(PostRequestWithIvalidToken.executed(
                variables.getProperty("pathCitas"),
                citaEnviada)
        );
    }



    @Entonces(": entonces no se podra  crear una cita")
    public void entoncesNoSePodraCrearUnaCita() {
        actor.should(
                seeThatResponse("La api entrego código 403 correctamente",
                        response -> response.statusCode(403)
                )
        );

    }

    @Cuando(": se tiene la informacion de la cita y el Id de un paciente inexisistente")
    public void seTieneLaInformacionDeLaCitaYElIdDeUnPacienteInexisistente() {
        citaEnviada = RequestCita.getCitasInformationSinPatientId();
        actor.attemptsTo(PostRequestWithToken.executed(
                variables.getProperty("pathCitas"),
                citaEnviada)
        );
    }

    @Entonces(": el api respondera con un error")
    public void elApiResponderaConUnError() {
        actor.should(
                seeThatResponse("La api entrego código 500 correctamente",
                        response -> response.statusCode(500)
                )
        );

    }


}
