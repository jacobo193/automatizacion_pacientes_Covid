#language: es

Característica: obtencion de información de citas
  Antecedentes:
    Dado que un "auxiliar"esta autenticado correctamente y conoce la ruta get para obtener la informacion de una cita

  Escenario: obtencion de informacion de citas
    Cuando se introcude el Id de una cita existe
    Entonces La api retornara la informacion de la cita
  Escenario: Obtencion sin autorizacion
    Cuando : el auxiliar no esta autenticado correctamente en la api e introducte el Id de una cita  existente
    Entonces : El api mostrara un error
  Escenario: cita inexisten inextistente
    Cuando : dado que el auxiliar esta autenticado de manera correcta e introduce un id de una cita inexistente
    Entonces : el api arrojara un error