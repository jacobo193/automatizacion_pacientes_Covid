package screenplay.steps;

import co.com.sofkaU.pacientes.test.data.RequestAutenticacion;
import co.com.sofkaU.pacientes.test.data.RequestPacientes;
import co.com.sofkaU.pacientes.test.model.JsonPaciente;
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

public class Paciente {

    Actor actor;
    EnvironmentVariables variables;
    JsonPaciente pacienteEnviado;
    JsonPaciente pacienteRecibido;

    @Dado("un {string} autenticado correctamente en la api")
    public void unAutenticadoCorrectamenteEnLaApi(String name) {
        actor = Actor.named(name);
        actor.attemptsTo(TokenRequest.executed(RequestAutenticacion.getValueUser()));
        actor.attemptsTo(SaveToken.executed());
    }

    @Y("el auxiliar de enfermeria conoce la ruta de la api de creacion de pacientes")
    public void elAuxiliarDeEnfermeriaConoceLaRutaDeLaApiDeCreacionDePacientes() {
        String urlbase = variables.getProperty("baseurl");
        actor.whoCan(CallAnApi.at(urlbase));
    }

    @Cuando("se tiene la informacion de un paciente")
    public void seTieneLaInformacionDeUnPaciente() {
        pacienteEnviado = RequestPacientes.getPatienteInformation();
        actor.attemptsTo(PostRequestWithToken.executed(
                variables.getProperty("pathPatient"),
                pacienteEnviado)
        );
    }

    @Entonces("se puede crear el paciente en la api")
    public void sePuedeCrearElPacienteEnLaApi() {
        actor.should(
                seeThatResponse("La api entrego código 201 correctamente",
                        response -> response.statusCode(201)
                )
        );
        pacienteRecibido = SerenityRest.lastResponse()
                .jsonPath()
                .getObject("", JsonPaciente.class);
        assertThat(pacienteEnviado).isEqualTo(pacienteRecibido);
    }



}
