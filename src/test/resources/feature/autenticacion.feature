
#language: es
Característica: Autenticación de usuario en la api
  Antecedentes:
    Dado un "auxiliar" que conoce la ruta de la api de autenticacion

  Escenario: Autenticacion exitosa
    Cuando el usuario tiene credenciales validas
    Entonces se puede autenticar en la api correctamente
  Escenario: Autenticacion fallida
    Cuando el usuario tiene credenciales incorrectas
    Entonces la api contesta con codigo de error "403"
  Escenario: segunda Autenticacion Fallida
    Cuando : el usuario tiene password incorrecta y user correcta
    Entonces : la api contesta con un error "403"