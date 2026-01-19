** **API de gestion de réservations** **

Ce projet implémente une API REST backend en Java et Spring Boot permettant la gestion de réservations de ressources partagées sur des créneaux temporels.

**Technologies utilisées:**

- Java 17

- Spring Boot 4

- Spring Data JPA

- Hibernate

- MySQL

- H2 (pour l'environnement de test)

- Docker & Docker Compose

**Règles métier retenues**

- Les règles suivantes ont été identifiées, formalisées et implémentées :

- Une ressource ne peut pas être réservée sur des créneaux qui se chevauchent

- Une ressource désactivée ne peut pas être réservée

- Un utilisateur désactivé ne peut pas créer de réservation

- Un créneau est valide uniquement si start < end

- La disponibilité est calculée dynamiquement à partir des réservations existantes

    **Une réservation annulée:**

- n’est pas supprimée

- libère la disponibilité de la ressource

**Gestion de la concurrence:**

Deux utilisateurs peuvent tenter de réserver la même ressource au même moment.

**Solution mise en place**

- Les opérations de création de réservation sont exécutées dans une transaction

- La ressource concernée est chargée avec un verrou pessimiste

- La vérification des conflits et l’enregistrement sont faits dans la même transaction

**Les tests:**

Les tests se concentrent sur les zones critiques du système.
Le projet privilégie des tests d’intégration plutôt que des tests unitaires isolés, car :
- les problèmes de concurrence n’apparaissent qu’avec un vrai contexte
- les transactions et verrous ne peuvent pas être simulés efficacement avec des mocks

**Lancer le projet:**

- Avec Docker
***
    docker compose build
***
***
    docker compose up -d
***

**Lancer les tests**

***
    mvn test
***

**Limites connues**

- Pas d’authentification / autorisation

- Pas de gestion avancée des fuseaux horaires

- Pas de pagination