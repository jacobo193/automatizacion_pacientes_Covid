package co.com.sofkaU.pacientes.test.task.http;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class GetRequest implements Task {
    private String path;

    public GetRequest(String path) {
        this.path = path;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Get.resource(path).
                with(request -> request.auth().oauth2(Serenity.sessionVariableCalled("TOKEN")))
        );
    }

    public static GetRequest execute(String path) {
        return Tasks.instrumented(GetRequest.class, path);
    }
}
