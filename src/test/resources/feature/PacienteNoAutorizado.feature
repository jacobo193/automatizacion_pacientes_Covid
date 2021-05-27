#language: es

Característica: Administrar información de pacientes incorrecto
  Escenario: Creacion de paciente sin autorizacion
    Dado un "auxiliar de enfermeria" autenticado fallidamente en la api
    Y el auxiliar de enfermeria conoce esta en ruta de la api para creacion de pacientes
    Cuando se tiene la informacion de un paciente completa}
    Entonces no se puede el paciente no se guardara y la api contestara con un error "403"
