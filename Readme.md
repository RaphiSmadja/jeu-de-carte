# 🃏 Jeu de Cartes - Bataille

## 📝 Description
Ce projet est une application Spring Boot permettant de gérer un jeu de cartes. L'objectif est de créer, manipuler, et afficher un jeu de cartes (52 cartes réparties en 4 couleurs). Une version simplifiée du jeu de bataille est également implémentée.
### Objectifs :
* Initialiser et afficher un jeu de cartes complet.
* Créer une API REST pour gérer les cartes et jouer au jeu.
* Ajouter une interface web pour afficher les cartes ou jouer en ligne.
### ⚙ Prérequis
Avant de lancer le projet, il faut avoir :

Java 21 installé.
Maven installé et configuré.
Git pour cloner le projet.

## 🚀 Installation et Exécution
1. Cloner le projet
   ```
   git clone https://github.com/votre-utilisateur/jeu-de-cartes.git
   cd jeu-de-cartes
   ```
2. Construire et lancer l'application
   ```
   mvn clean install
   mvn spring-boot:run
   ```
3. Accéder au swagger de l'API : http://localhost:8080/swagger-ui/

## 🛠 Architecture du projet

```
src/
├── main/
│   ├── java/
│   │   └── fr/bpifrance/kyc/
│   │       ├── config/        # Swagger REST
│   │       ├── dto/           # DTO
│   │       ├── mapper/        # mapper
│   │       ├── controller/    # Contrôleurs REST
│   │       ├── model/         # Entités JPA
│   │       ├── repository/    # Repositories Spring Data JPA
│   │       └── service/       # Logique métier
│   ├── resources/
│   │   ├── application.properties  # Configuration H2 et Spring Boot
└── test/
└── java/                  # Tests unitaires et d'intégration
```

## 📈 Améliorations futures

## 👤 Auteur
Projet développé par Raphael SMADJA dans le cadre d’un exercice technique.

## 📜 Licence
Ce projet est KaizenCode

## 📧 Contact
Si vous avez des questions, n'hésitez pas à me contacter :

Email : raphael_smadja@hotmail.com
GitHub : RaphiSmadja
