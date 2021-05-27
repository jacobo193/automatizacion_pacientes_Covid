#language: es
  Característica: administrar informacion de citas
    Antecedentes:
      Dado : que un "auxiliar de enfermeria" esta autenticado correctamente
      Y : el auxiliar de enfermeria conoce la ruta de la api de creacion de citas
    Escenario: Creacion de citas exitosa
      Cuando : se tiene la informacion de la cita y el Id de un paciente exisistente
      Entonces : se puede crear una cita exitosamente
    Escenario:  Creacion de citas fallida
        Cuando : se tiene la informacion de una cita incompleta  y el Id de un paciente existente
        Entonces : no se puede crear una cita exitosamente
      Escenario: creacion de sin autorizacion
        Cuando : dado que el auxiliar no esta autenticado correctamente en la api
        Entonces : entonces no se podra  crear una cita
      Escenario: creacion sin id
        Cuando : se tiene la informacion de la cita y el Id de un paciente inexisistente
        Entonces : el api respondera con un error