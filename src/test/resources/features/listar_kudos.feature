@Kudos
Feature: Consulta de listado de Kudos con datos controlados
  Como empleado de Sofka
  Quiero ver los reconocimientos que he enviado y los del equipo
  Para validar que la información se visualiza correctamente en el listado

  Background:
    Given que se ha ejecutado el seeder de Kudos para "christopher@sofkianos.com" con las categorías:
      | categoria  | receptor               | mensaje                     |
      | Teamwork   | backend@sofkianos.com  | Gran apoyo en el despliegue |
      | Innovation | frontend@sofkianos.com | Excelente propuesta técnica |
      | Passion    | santiago@sofkianos.com | Entrega impecable           |

  @LlenadoDeDatos @Felicidad
  Scenario: Visualización de registros dinámicos y validación de emisor
    When el empleado visualiza el listado de Kudos
    Then el listado debe mostrar los registros cargados por el emisor
    And el contador "totalElements" debe reflejar la cantidad de registros insertados
    And cada registro en pantalla debe mostrar a "christopher@sofkianos.com" como el emisor

  @Filtros @BusquedaDinámica
  Scenario Outline: Filtrado secuencial por categoría y búsqueda por mensaje
    When el empleado filtra el listado por la categoría "<categoria>"
    And el empleado busca por el mensaje "<mensaje>"
    Then el listado debe mostrar los resultados que coincidan con la categoría "<categoria>"
    And el mensaje de los registros visibles debe ser "<mensaje>"

    Examples:
      | categoria  | mensaje                     |
      | Teamwork   | Gran apoyo en el despliegue |
      | Innovation | Excelente propuesta técnica |
      | Passion    | Entrega impecable           |
