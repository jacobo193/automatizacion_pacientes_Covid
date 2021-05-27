#language: es

Característica: Administrar información de pacientes

  Escenario: Creacion de paciente exitosa
    Dado un "auxiliar de enfermeria" autenticado correctamente en la api
    Y el auxiliar de enfermeria conoce la ruta de la api de creacion de pacientes
    Cuando se tiene la informacion de un paciente
    Entonces se puede crear el paciente en la api
