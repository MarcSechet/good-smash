# language: fr
Fonctionnalité: Récupération d'un character (personnage)

  Contexte: Initialisation des données
    Soit les personnages suivants :
      | id | name   | weight | floaty | skill_rating | tier |
      | 10 | Lucas  | HEAVY  | true   | 1            | A    |
      | 11 | Ken    | MEDIUM | false  | 5            | S    |
      | 12 | Mewtwo | LIGHT  | true   | 3            | B    |

  #TODO : image

  Scénario: Cas nominal
    Quand il tente de récupérer le personnage Lucas
    Alors la demande réussit
    Et il récupère le personnage suivant :
      | uuid                  | 0-0-0-0-1                                                                 |
      | issuer.uuid           | 1-0-0-0-1                                                                 |
      | issuer.iban           | FR7610011000205025946661433                                               |
      | document              | 2-0-0-0-1                                                                 |
      | creationDate          | 2020-01-01T01:00:00                                                       |
      | reference             | reference1                                                                |
      | label                 | description                                                               |
      | receiver.uuid         | 3-0-0-0-0                                                                 |
      | receiver.ibanValue    | FR7610011000202991145559962                                               |
      | receiver.ibanDocument | 0a685292-27aa-4101-a11e-0db3796dda8d                                      |
      | receiver.uuidMails[*] | d6d5ea5e-6dc3-4124-8e72-4047da8a3202,0abe4eff-701c-48a9-bc8b-3834bd5d34c9 |
      | payment.value         | 1233                                                                      |
      | status                | DRAFT                                                                     |

