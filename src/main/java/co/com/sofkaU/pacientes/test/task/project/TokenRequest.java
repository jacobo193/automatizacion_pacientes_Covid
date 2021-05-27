package co.com.sofkaU.pacientes.test.task.project;

import co.com.sofkaU.pacientes.test.task.http.PostRequest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.core.util.EnvironmentVariables;
import org.jetbrains.annotations.NotNull;

public class TokenRequest implements Task {
    EnvironmentVariables variables = null;
    Object body;

    public TokenRequest(Object body) {
        this.body = body;
    }

    @Override
    public <T extends Actor> void performAs(@NotNull T actor) {
        String baseurl = variables.getProperty("baseurl");
        actor.whoCan(CallAnApi.at(baseurl));
        actor.attemptsTo(
                PostRequest.executed(
                        variables.getProperty("pathLogin"),
                        body
                )
        );
    }

    public static TokenRequest executed(Object body) {
        return Tasks.instrumented(TokenRequest.class, body);
    }

}
