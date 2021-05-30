package screenplay.steps;

import co.com.sofkaU.pacientes.test.data.RequestAutenticacion;
import co.com.sofkaU.pacientes.test.model.JsonCitas;
import co.com.sofkaU.pacientes.test.task.http.GetRequest;
import co.com.sofkaU.pacientes.test.task.http.GetRequestWithTokenInvalido;
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


import java.util.List;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CitasDePacienteId {
    private Actor actor;
    EnvironmentVariables variables;
    private String documento = "642336182";
    private Integer documentoInvalido = 0;

    @Dado(": que un {string} autenticado correctamente en la api")
    public void queUnAutenticadoCorrectamenteEnLaApi(String name) {
        actor = Actor.named(name);
        actor.attemptsTo(TokenRequest.executed(RequestAutenticacion.getValueUser()));
        actor.attemptsTo(SaveToken.executed());
    }


    @Y("que conoce la ruta para obtencion de citas")
    public void queConoceLaRutaParaObtencionDeCitas() {

        String urlbase = variables.getProperty("baseurl");
        actor.whoCan(CallAnApi.at(urlbase));
    }

    @Cuando(": introduce un Id de un pacient existente")
    public void introduceUnIdDeUnPacientExistente() {
        actor.attemptsTo(GetRequest.execute("cita/paciente/"+documento));
    }

    @Entonces(": el api respondera con un status {string} y una lista de las citas por paciente")
    public void elApiResponderaConUnStatusYUnaListaDeLasCitasPorPaciente(String codigo) {
        actor.should(
                seeThatResponse("La api respondera con un estatus 200",
                        response -> response.statusCode(Integer.parseInt(codigo)))
        );

        List<JsonCitas> Citas = SerenityRest.lastResponse().jsonPath().getList("", JsonCitas.class);
        for (JsonCitas cita : Citas) {
            assertThat(documento).isEqualTo(String.valueOf(cita.getPatientId()));
        }

    }

    @Cuando(": se introduce un Id de un paciente que no existe")
    public void seIntroduceUnIdDeUnPacienteQueNoExiste() {
        actor.attemptsTo(TokenRequest.executed(RequestAutenticacion.getValueUser()));
        actor.attemptsTo(SaveToken.executed());
        String urlbase = variables.getProperty("baseurl");
        actor.whoCan(CallAnApi.at(urlbase));
        actor.attemptsTo(GetRequest.execute("cita/paciente/"+ documentoInvalido));
    }

    @Entonces(": el Api no mostrara nada y respondera con un estatus {string}")
    public void elApiNoMostraraNadaYResponderaConUnEstatus(String codigo) {
        actor.should(
                seeThatResponse("La api respondera con un estatus 404",
                        response -> response.statusCode(Integer.parseInt(codigo)))
        );
    }

    @Cuando(": el auxiliar intenta realizar una busqueda sin ser autorizado")
    public void elAuxiliarIntentaRealizarUnaBusquedaSinSerAutorizado() {
        String urlbase = variables.getProperty("baseurl");

        actor.whoCan(CallAnApi.at(urlbase));
        actor.attemptsTo(GetRequestWithTokenInvalido.execute("cita/paciente/1"));
    }

    @Entonces(": el Api mostrara estatus {string}")
    public void elApiMostraraEstatus(String codigo) {
        actor.should(
                seeThatResponse("La api respondera con un estatus 403",
                        response -> response.statusCode(Integer.parseInt(codigo)))
        );
    }
}
