Feature: Common - Tile+ E2E language testing

  Scenario Outline: Tile+ Languages - SC1 user profile
    Given User is accessing TilePlus
    When he logins as default "Management"
    When he selects "English" version
    When he opens profile wizard
    Then he should see supported languages in profile wizard
      | English  |
      | Français |
      | Español  |
    When he selects "<language>" version
    Then he should see supported mode in profile wizard
      | <light> |
      | <dark>  |
    And he should see Appearance text is "<appearanceText>" and language text is "<languageText>"
    And he should see sign out button text is "<bntText>"
    And he should see Term text is "<termText>" and clicks it
    And he should see page title contains "<termText>"
    When he logout

#    DONE in tile+ staging
    Examples:
      | language | languageText | appearanceText | bntText       | termText               | light | dark   |
      | English  | Language     | Appearance     | Sign Out      | Terms of Service       | Light | Dark   |
      | Français | Langue       | Affichage      | Déconnexion   | Termes et conditions   | Clair | Sombre |
      | Español  | Idioma       | Apariencia     | Cerrar sesión | Términos y condiciones | Claro | Oscuro |


  Scenario Outline: Tile+ languages - SC2 side bar and profile
    Given User is accessing TilePlus
    When he logins as default "Management"
    When he selects "English" version
    When he opens profile wizard
    When he selects "<language>" version
    Then he should see default factory is displayed
    Then he should see links in side bars
      | <Overview>             |
      | <Dashboard>            |
      | <ProductionDataExport> |
      | <Admin>                |
#    Prod does not contain this item
#      | <StyleGuide>           |
    When he expends "<Admin>" menu
    And he should see links in side bars
      | <Users>           |
      | <ShiftSchedules>  |
      | <DowntimeReasons> |
      | <RejectReasons>   |
      | <Products>        |
      | <ProductionUnits> |
      | <Devices>         |
      | <Alerts>          |
    When he logout

    #    DONE in tile+ staging
    Examples:
      | language | Dashboard      | Overview        | ProductionDataExport            | Admin         | Users        | ShiftSchedules     | DowntimeReasons         | RejectReasons      | Products  | ProductionUnits        | Devices       | Alerts  | StyleGuide      |
      | English  | Overview       | Dashboard       | Production Data Export          | Admin         | Users        | Shift Schedules    | Downtime Reasons        | Reject Reasons     | Products  | Production Units       | Devices       | Alerts  | Style Guide     |
      | Français | Vue d’ensemble | Tableau de bord | Export de données de production | Admin         | Utilisateurs | Quarts de travail  | Causes de temps d’arrêt | Causes de rejet    | Produits  | Unités de production   | Périphériques | Alertes | Guide de style  |
      | Español  | Vista general  | Tablero         | Exportar datos de producción    | Administrador | Usuarios     | Horarios de turnos | Razones de inactividad  | Razones de rechazo | Productos | Unidades de producción | Dispositivos  | Alertas | Guía de estilo. |


  Scenario Outline: Tile+ languages - SC3 main overview and tile details
    Given User is accessing TilePlus
    When he logins as default "Management"
    When he selects "English" version
    When he opens profile wizard
    When he selects "<language>" version
    Then he should see default factory is displayed
    Then he validates links of application
    Then he validates overview page titles
    Then he validate PU tiles in overview page
    Then he validates KPI list
      | <OEE>                    |
      | <Availability>           |
      | <Performance>            |
      | <Quality>                |
      | <ProductQuantity>        |
      | <CurrentProductQuantity> |
      | <RatePerHour>            |
      | <RatePerMinute>          |
      | <Speed5Minutes>          |
    Then he validates overview time period
      | <calendar>  |
      | <today>     |
      | <yesterday> |
      | <2DaysAgo>  |
    Then he validate PU tiles in overview page
    Then he should see PU tiles
    When he toggles the detailed view
    Then he should see three articles
      | <KPIIndicator>     |
      | <downtimes>        |
      | <timeDistribution> |
    Then he should see KPI list with details
      | <OEE>                    |
      | <Availability>           |
      | <Performance>            |
      | <Quality>                |
      | <ProductQuantity>        |
      | <CurrentProductQuantity> |
      | <RatePerHour>            |
      | <RatePerMinute>          |
      | <Speed5Minutes>          |
    When he logout

#    DONE in tile+ staging
    Examples:
      | language | languageText | appearanceText | bntText       | termText               | light | dark   | OEE | Availability   | Performance | Quality | ProductQuantity            | CurrentProductQuantity         | RatePerHour         | RatePerMinute        | Speed5Minutes       | 2DaysAgo    | yesterday | today       | calendar                       | KPIIndicator                     | downtimes              | timeDistribution       |
      | English  | Language     | Appearance     | Sign Out      | Terms of Service       | Light | Dark   | OEE | Availability   | Performance | Quality | Product Quantity           | Current Product Quantity       | Rate (per hour)     | Rate (per minute)    | Speed 5 Minute      | 2 Days Ago  | Yesterday | Today       | Choose from calendar           | Key Performance Indicators       | Downtimes              | Time Distribution      |
#      | Français | Langue       | Affichage      | Déconnexion   | Termes et conditions   | Clair | Sombre | TRG | Disponibilité  | Efficacité  | Qualité | Quantité de produit        | Quantité du produit en cours   | Cadence (par heure) | Cadence (par minute) | Vitesse 5 min       | Avant-Hier  | Hier      | Aujourd’hui | Choisir à partir du calendrier | Indicateurs clé de performance   | Arrêts                 | Distribution du temps  |
#      | Español  | Idioma       | Apariencia     | Cerrar sesión | Términos y condiciones | Claro | Oscuro | OEE | Disponibilidad | Rendimiento | Calidad | Cantidad Total de producto | Cantidad del producto en curso | Índice (por hora)   | Tarifa (por minuto)  | Velocidad 5 minutos | Hace 2 días | Ayer      | Hoy         | Elija del calendario           | Indicadores clave de rendimiento | Tiempos de inactividad | Distribución de tiempo |


  Scenario Outline: Tile+ languages - SC4 PU dashboard tiles
    Given User is accessing TilePlus
    When he logins as default "Management"
    When he selects "English" version
    Then he should see default factory is displayed
    When he accesses dashboard of the "SF4" PU
#    TODO: so far, only support English
    Then he validates Timeline tile with "<tileOptionCount>" options
      | Availability                 |
      | Production Completion        |
      | Current Product Quantity     |
      | Rate (units/h)               |
      | Rate (units/min)             |
      | Speed 5 min (units/h)        |
      | Speed 5 min (units/min)      |
      | Product Quantity             |
      | Performance                  |
      | OEE                          |
      | Quality                      |
      | Net/Reject Quantity          |
      | Uptime                       |
      | Planned/Unplanned Downtime   |
      | Giveaway                     |
      | Giveaway %                   |
      | Average Weight/Length/Volume |

    Then he should see "<tileCount>" tiles

    Then he validates time period list in Overview page
      | Production Day |
      | Shift          |
      | Hour           |
    When he logout

    Examples:
      | tileCount | tileOptionCount |
      | 5         | 17              |


#    Examples:
#      | language | languageText | appearanceText | bntText       | termText               | light | dark   | OEE | Availability   | Performance | Quality | ProductQuantity            | CurrentProductQuantity         | RatePerHour         | RatePerMinute        | Speed5Minutes       |
#      | English  | Language     | Appearance     | Sign Out      | Terms of Service       | Light | Dark   | OEE | Availability   | Performance | Quality | Product Quantity           | Current Product Quantity       | Rate (per hour)     | Rate (per minute)    | Speed 5 Minute      |
#      | Français | Langue       | Affichage      | Déconnexion   | Termes et conditions   | Clair | Sombre | TRG | Disponibilité  | Efficacité  | Qualité | Quantité de produit        | Quantité du produit en cours   | Cadence (par heure) | Cadence (par minute) | Vitesse 5 min       |
#      | Español  | Idioma       | Apariencia     | Cerrar sesión | Términos y condiciones | Claro | Oscuro | OEE | Disponibilidad | Rendimiento | Calidad | Cantidad Total de producto | Cantidad del producto en curso | Índice (por hora)   | Tarifa (por minuto)  | Velocidad 5 minutos |



  Scenario Outline: Tile+ languages - SC5 SF4 PU measurement-activity
    Given User is accessing TilePlus
    When he logins as default "wxadmin"
    When he selects "English" version
    When he opens profile wizard
    When he selects "<language>" version
    Then he should see default factory is displayed
    When he accesses dashboard of the "SF4" PU
    And he should see buttons
      | <toggleToDowntime>     |
      | <toggleBackToTimeline> |
      | <rejects>              |
    When he logout
#    Done
    Examples:
      | language | toggleToDowntime                                               | toggleBackToTimeline                  | rejects                      |
      | English  | Show downtime justifications form                              | Hide justifications form              | Manually enter rejects       |
      | Français | Afficher le formulaire de justification des temps d’arrêt      | Fermer le formulaire de justification | Entrez la quantité de rejets |
      | Español  | Mostrar formulario de justificaciones de tiempo de inactividad | Cerrar el formulario de justificación | Formulario de rechazos       |



#  Scenario Outline: Tile+ languages - SC5 SF4 PU measurement-activity
#    Given User is accessing TilePlus
#    When he logins as default "wxadmin"
#    When he selects "English" version
#    When he opens profile wizard
#    When he selects "<language>" version
#    Then he should see default factory is displayed
#    When he accesses admin "Production Units"
#    Then he should see "PU" list of default factory
#    When he edits the "SF4" PU
#    Then he should see page with title PU name of type "SF4"
#    And he should see buttons
#      | <toggleBackToTimeline> |
#      | <toggleToDowntime>     |
#      | <rejects>              |
#    When he logout
#
##    Done
#    Examples:
#      | language | toggleBackToTimeline                                            | toggleToDowntime                                               | rejects                      |
#      | English  | Toggle back to timeline initial view with tiles                 | Toggle to downtime justification form                          | Manually enter rejects       |
##      | Français | Basculer vers la vue initiale de la chronologie avec les tuiles | Afficher les temps d’arrêts et son formulaire de justification | Entrez la quantité de rejets |
##      | Español  | Cambiar a la vista de línea de tiempo inicial con mosaicos      | Mostrar el tiempo de inactividad y su justificación            | Formulario de rechazos       |


#  Scenario Outline: Tile+ languages: SC6 SF4 PU dashboard tiles
#    Given User is accessing TilePlus
#    When he logins as default "Management"
#    When he selects "English" version
#    Then he should see default factory is displayed
#    Then he should see default factory is displayed
##    TODO : query from DB
#    When he accesses dashboard PU "Line - Giveaway"
#    Then he should see two views
#    When he clicks on "Giveaway" view button
#    Then he should see "Giveaway" overview page
#    Then he validates PU Giveaway page with "3" tiles
#    Then he validates Giveaway tile with "3" options
#      | Giveaway         |
#      | Giveaway %       |
#      | AAverage Weight/Length/Volume |
#    Then he should see "<giveawayTileCount>" tiles
#
#    When he logout
#    Examples:
#      | giveawayTileCount |
#      | 5                 |

