package screenplay.steps;

import co.com.sofkaU.pacientes.test.data.RequestAutenticacion;
import co.com.sofkaU.pacientes.test.model.JsonPaciente;
import co.com.sofkaU.pacientes.test.task.http.GetRequest;
import co.com.sofkaU.pacientes.test.task.http.GetRequestWithTokenInvalido;
import co.com.sofkaU.pacientes.test.task.project.SaveToken;
import co.com.sofkaU.pacientes.test.task.project.TokenRequest;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.core.util.EnvironmentVariables;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class GetPaciente {
    private Actor actor;
    EnvironmentVariables variables;
    JsonPaciente pacienteEnviado;
    JsonPaciente pacienteRecibido;
    private String documento = "1";
    @Dado("que un {string}esta autenticado correctamente y conoce la ruta get para obtener la informacion de un paciente")
    public void queUnEstaAutenticadoCorrectamenteYConoceLaRutaGetParaObtenerLaInformacionDeUnPaciente(String name) {

        actor = Actor.named(name);
        actor.attemptsTo(TokenRequest.executed(RequestAutenticacion.getValueUser()));
        actor.attemptsTo(SaveToken.executed());
        String urlbase = variables.getProperty("baseurl");
        actor.whoCan(CallAnApi.at(urlbase));
    }

    @Cuando("se introcude el Id de un paciente existe")
    public void seIntrocudeElIdDeUnPacienteExiste() {
        actor.attemptsTo(GetRequest.execute("paciente/1"));
    }

    @Entonces("La api retornara la informacion del paciente")
    public void laApiRetornaraLaInformacionDelPaciente() {
        actor.should(
                seeThatResponse("La api respondera con un estatus 200",
                        response -> response.statusCode(200))
        );

        pacienteRecibido = SerenityRest.lastResponse().jsonPath().getObject("", JsonPaciente.class);
        assertThat(documento).isEqualTo(String.valueOf(pacienteRecibido.getPatientId()));
    }

    @Cuando(": el auxiliar no esta autenticado correctamente en la api e introducte el Id de un paciente existente")
    public void elAuxiliarNoEstaAutenticadoCorrectamenteEnLaApiEIntroducteElIdDeUnPacienteExistente() {
        String urlbase = variables.getProperty("baseurl");

        actor.whoCan(CallAnApi.at(urlbase));
        actor.attemptsTo(GetRequestWithTokenInvalido.execute("paciente/"+ documento));

    }

    @Entonces(": la Api no mostrara nada")
    public void laApiNoMostraraNada() {
        actor.should(
                seeThatResponse("La api entrego código 403 correctamente",
                        response -> response.statusCode(403)
                )
        );
    }
    @Cuando(": dado que el auxiliar esta autenticado de manera correcta e introduce un id de un paciente que no existente")
    public void dadoQueElAuxiliarEstaAutenticadoDeManeraCorrectaEIntroduceUnIdDeUnPacienteQueNoExistente() {

        actor.attemptsTo(TokenRequest.executed(RequestAutenticacion.getValueUser()));
        actor.attemptsTo(SaveToken.executed());
        String urlbase = variables.getProperty("baseurl");
        actor.whoCan(CallAnApi.at(urlbase));
        actor.attemptsTo(GetRequest.execute("paciente/1234567"));


    }


    @Entonces(": el api arrojara un error {string}")
    public void elApiArrojaraUnError(String codigo) {
        actor.should(
                seeThatResponse("La api entrego código 404 correctamente",
                        response -> response.statusCode(Integer.parseInt(codigo))
                )
        );
    }



}
