#language: es

Característica: obtencion de información de pacientes
  Antecedentes:
    Dado que un "auxiliar"esta autenticado correctamente y conoce la ruta get para obtener la informacion de un paciente

  Escenario: obtencion de informacion de paciente
    Cuando se introcude el Id de un paciente existe
    Entonces La api retornara la informacion del paciente
    Escenario: Obtencion sin autorizacion
      Cuando : el auxiliar no esta autenticado correctamente en la api e introducte el Id de un paciente existente
      Entonces : la Api no mostrara nada
      Escenario: paciente inextistente
        Cuando : dado que el auxiliar esta autenticado de manera correcta e introduce un id de un paciente que no existente
        Entonces : el api arrojara un error "404"
