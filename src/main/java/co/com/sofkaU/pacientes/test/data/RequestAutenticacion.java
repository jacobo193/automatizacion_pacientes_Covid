package co.com.sofkaU.pacientes.test.data;

import co.com.sofkaU.pacientes.test.model.JsonAutenticacion;

public class RequestAutenticacion {

    public static JsonAutenticacion getValueUser(){
        return JsonAutenticacion.builder()
                .username("Covid")
                .password("Covid")
                .build();
    }

    public static JsonAutenticacion getInvalidPassword(){
        return JsonAutenticacion.builder()
                .username("Covid")
                .password("rkvmw op")
                .build();
    }

    public static JsonAutenticacion getInvalidUser(){
        return JsonAutenticacion.builder()
                .username("Covi")
                .password("Covid")
                .build();
    }


}
