package screenplay.steps;

import co.com.sofkaU.pacientes.test.data.RequestAutenticacion;
import co.com.sofkaU.pacientes.test.data.RequestPacientes;
import co.com.sofkaU.pacientes.test.model.JsonPaciente;
import co.com.sofkaU.pacientes.test.model.JsonPacienteInvalido;
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

public class PacienteInvalido {

    Actor actor;
    EnvironmentVariables variables;
    JsonPacienteInvalido pacienteEnviado;
    JsonPacienteInvalido pacienteRecibido;

    @Dado("un {string} autenticado exitosamente en la api")
    public void unAutenticadoExitosamenteEnLaApi(String name) {
        actor = Actor.named(name);
        actor.attemptsTo(TokenRequest.executed(RequestAutenticacion.getValueUser()));
        actor.attemptsTo(SaveToken.executed());
    }

    @Y("el auxiliar de enfermeria conoce la ruta de la api para creacion de pacientes")
    public void elAuxiliarDeEnfermeriaConoceLaRutaDeLaApiParaCreacionDePacientes() {
        String urlbase = variables.getProperty("baseurl");
        actor.whoCan(CallAnApi.at(urlbase));
    }


    @Cuando("se tiene la informacion de un paciente incompleta")
    public void seTieneLaInformacionDeUnPacienteIncompleta() {
        pacienteEnviado = RequestPacientes.getPatienteInvalidInformation();
        actor.attemptsTo(PostRequestWithToken.executed(
                variables.getProperty("pathPatient"),
                pacienteEnviado)
        );
    }

    @Entonces("no se puede crear el paciente en la api")
    public void noSePuedeCrearElPacienteEnLaApi() {
        actor.should(
                seeThatResponse("La api entrego código 500 correctamente",
                        response -> response.statusCode(500)
                )
        );
        pacienteRecibido = SerenityRest.lastResponse()
                .jsonPath()
                .getObject("", JsonPacienteInvalido.class);
        assertThat(pacienteEnviado).isEqualTo(pacienteRecibido);
    }


}
