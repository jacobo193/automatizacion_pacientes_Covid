package screenplay.steps;

import co.com.sofkaU.pacientes.test.data.RequestAutenticacion;
import co.com.sofkaU.pacientes.test.task.http.PostRequest;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.core.util.EnvironmentVariables;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.core.StringStartsWith.startsWith;

public class Autenticacion {
    private Actor actor;

    private EnvironmentVariables variables;
    @Dado("un {string} que conoce la ruta de la api de autenticacion")
    public void unQueConoceLaRutaDeLaApiDeAutenticacion(String name) {
        actor = Actor.named(name);
        String url = variables.getProperty("baseurl");
        actor.whoCan(CallAnApi.at(url));

    }

    @Cuando("el usuario tiene credenciales validas")
    public void elUsuarioTieneCredencialesValidas() {
        actor.attemptsTo(PostRequest.executed(variables.getProperty("pathLogin"),
                RequestAutenticacion.getValueUser()));
    }

    @Entonces("se puede autenticar en la api correctamente")
    public void sePuedeAutenticarEnLaApiCorrectamente() {
        actor.should(seeThatResponse("La Api entrego un codigo 200 correctamente",
                response -> response.statusCode(200))
        );
        actor.should(seeThatResponse("se genero el token correctamente",
                response -> response.body(startsWith("{\"jwt\":\"ey"))));
    }

    @Cuando("el usuario tiene credenciales incorrectas")
    public void elUsuarioTieneCredencialesIncorrectas() {
        actor.attemptsTo(PostRequest.executed(variables.getProperty("pathLogin"),
                RequestAutenticacion.getInvalidPassword()));
    }

    @Entonces("la api contesta con codigo de error {string}")
    public void laApiContestaConCodigoDeError(String codigo) {
        actor.should(seeThatResponse("se entrego el codigo de respuesta esperado",
                response -> response.statusCode(Integer.parseInt(codigo)
                )));
    }

    @Cuando(": el usuario tiene password incorrecta y user correcta")
    public void elUsuarioTienePasswordIncorrectaYUserCorrecta() {
        actor.attemptsTo(PostRequest.executed(variables.getProperty("pathLogin"),
                RequestAutenticacion.getInvalidUser()));
    }

    @Entonces(": la api contesta con un error {string}")
    public void laApiContestaConUnError(String codigo) {
        actor.should(seeThatResponse("se entrego el codigo de respuesta esperado",
                response -> response.statusCode(Integer.parseInt(codigo)
                )));
    }
}
