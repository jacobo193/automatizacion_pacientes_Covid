package screenplay.steps;

import co.com.sofkaU.pacientes.test.data.RequestAutenticacion;
import co.com.sofkaU.pacientes.test.data.RequestPacientes;
import co.com.sofkaU.pacientes.test.model.JsonPaciente;
import co.com.sofkaU.pacientes.test.task.http.PostRequestWithIvalidToken;
import co.com.sofkaU.pacientes.test.task.http.PostRequestWithToken;
import co.com.sofkaU.pacientes.test.task.project.SaveToken;
import co.com.sofkaU.pacientes.test.task.project.TokenRequest;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.core.util.EnvironmentVariables;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

public class PacienteSinAutorizacion {
    Actor actor;
    EnvironmentVariables variables;
    JsonPaciente pacienteEnviado;
    JsonPaciente pacienteRecibido;


    @Dado("un {string} autenticado fallidamente en la api")
    public void unAutenticadoFallidamenteEnLaApi(String name) {
        actor = Actor.named(name);
        actor.attemptsTo(TokenRequest.executed(RequestAutenticacion.getValueUser()));
        actor.attemptsTo(SaveToken.executed());
    }

    @Y("el auxiliar de enfermeria conoce esta en ruta de la api para creacion de pacientes")
    public void elAuxiliarDeEnfermeriaConoceEstaEnRutaDeLaApiParaCreacionDePacientes() {
        String urlbase = variables.getProperty("baseurl");
        actor.whoCan(CallAnApi.at(urlbase));
    }

    @Cuando("se tiene la informacion de un paciente completa}")
    public void seTieneLaInformacionDeUnPacienteCompleta() {
        pacienteEnviado = RequestPacientes.getPatienteInformation();
        actor.attemptsTo(PostRequestWithIvalidToken.executed(
                variables.getProperty("pathPatient"),
                pacienteEnviado)
        );
    }




    @Entonces("no se puede el paciente no se guardara y la api contestara con un error {string}")
    public void noSePuedeElPacienteNoSeGuardaraYLaApiContestaraConUnError(String codigo) {
        actor.should(
                seeThatResponse("La api entrego código 500 correctamente",
                        response -> response.statusCode(Integer.parseInt(codigo))
                )
        );
    }


}
