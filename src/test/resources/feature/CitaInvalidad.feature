#language: es
Característica: Administrar información de pacientes incorrecto

  Escenario: Creacion de citas fallida
    Dado :  que el "auxiliar de enfermeria" esta autenticado exitosamente en la api
    Y :  el auxiliar de enfermeria tiene la ruta de la api para creacion de citas
    Cuando : se tiene la informacion de una incompleta
    Entonces : no se puede crear la cita en la api
