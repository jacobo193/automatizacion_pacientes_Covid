package co.com.sofkaU.pacientes.test.task.http;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class GetRequestWithTokenInvalido implements Task {

    private String path;

    public GetRequestWithTokenInvalido(String path){
        this.path = path;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Get.resource(path).with(request -> request.auth().oauth2("InvalidToken")));
    }

    public static GetRequestWithTokenInvalido execute(String path){
        return Tasks.instrumented(GetRequestWithTokenInvalido.class, path);
    }
}
