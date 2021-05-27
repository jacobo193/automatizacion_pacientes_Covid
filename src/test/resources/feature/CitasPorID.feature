#language: es
 Característica: administrar informacion de citas
   Antecedentes:
     Dado : que un "auxiliar de enfermeria" autenticado correctamente en la api
     Y  que conoce la ruta para obtencion de citas
   Escenario: Consulta exitosa de citas por paciente
     Cuando : introduce un Id de un pacient existente
     Entonces : el api respondera con un status "200" y una lista de las citas por paciente
     Escenario: Consulta fallida
       Cuando : se introduce un Id de un paciente que no existe
       Entonces : el Api no mostrara nada y respondera con un estatus "404"
       Escenario: consulta sin autorizacion
         Cuando : el auxiliar intenta realizar una busqueda sin ser autorizado
         Entonces : el Api mostrara estatus "403"
