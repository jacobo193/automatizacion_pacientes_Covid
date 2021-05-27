#language: es

Característica: Administrar información de pacientes incorrecto

  Escenario: Creacion de paciente fallida
    Dado un "auxiliar de enfermeria" autenticado exitosamente en la api
    Y el auxiliar de enfermeria conoce la ruta de la api para creacion de pacientes
    Cuando se tiene la informacion de un paciente incompleta
    Entonces no se puede crear el paciente en la api
