# 🎓  Système de Gestion des Étudiants via Web Service SOAP

![Java](https://img.shields.io/badge/Java-11-orange)
![Maven](https://img.shields.io/badge/Maven-3.6+-blue)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![Jakarta EE](https://img.shields.io/badge/Jakarta%20EE-9.1.0-green)

## 📖 Vue d'ensemble

Ce projet implémente un **système de gestion des étudiants** basé sur l'architecture **SOA (Service-Oriented Architecture)** utilisant le protocole **SOAP (Simple Object Access Protocol)**. Il permet d'effectuer des opérations CRUD (Create, Read, Update, Delete) sur les données des étudiants via un service web accessible à distance.

### 🎯 Objectifs du projet

- Créer un service web SOAP pour gérer les étudiants
- Implémenter une architecture SOA avec JAX-WS
- Utiliser Jakarta EE pour les services web modernes
- Persister les données dans une base MySQL
- Fournir des clients pour tester le service (local et SOAP)

### 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────┐
│                    Client SOAP                           │
│              (Client.java ou SoapUI)                     │
└────────────────────┬────────────────────────────────────┘
                     │ HTTP/SOAP Request
                     ↓
┌─────────────────────────────────────────────────────────┐
│              Service Web SOAP (Publisher)                │
│           http://localhost:8084/service                  │
├─────────────────────────────────────────────────────────┤
│    Interface: GestionEtudiantService.java                │
│    - ajouterEtudiant()                                   │
│    - supprimerEtudiant()                                 │
│    - modifierEtudiant()                                  │
│    - getEtudiant()                                       │
│    - getAllEtudiants()                                   │
├─────────────────────────────────────────────────────────┤
│  Implémentation: GestionEtudiantsServiceImpl.java        │
└────────────────────┬────────────────────────────────────┘
                     │ JDBC
                     ↓
┌─────────────────────────────────────────────────────────┐
│              Base de Données MySQL                       │
│              Database: tptest                            │
│              Table: etudiant                             │
└─────────────────────────────────────────────────────────┘
```

### 🗂️ Structure du Projet

```
SOA4/
│
├── src/model/
│   ├── Etudiant.java                    # 📦 Entité (POJO) représentant un étudiant
│   ├── ConnexionDB.java                 # 🔌 Singleton pour la connexion MySQL
│   ├── GestionEtudiantService.java      # 📋 Interface du service web (@WebService)
│   ├── GestionEtudiantsServiceImpl.java # ⚙️ Implémentation des opérations CRUD
│   ├── Publisher.java                   # 🚀 Serveur - Publication du service SOAP
│   ├── Client.java                      # 💻 Client SOAP pour consommer le service
│   ├── TestService.java                 # 🧪 Tests unitaires (accès direct)
│   └── test.java                        # 🧪 Tests alternatifs
│
├── pom.xml                              # 📦 Configuration Maven & dépendances
├── README.md                            # 📖 Ce fichier
└── INSTRUCTIONS.md                      # 📝 Guide d'utilisation détaillé
```

### 💾 Modèle de Données

**Classe Etudiant** :
```java
public class Etudiant {
    private int id;                    // Auto-généré (PRIMARY KEY)
    private String nom;                // Nom de famille
    private String prenom;             // Prénom
    private String dateNaissance;      // Date de naissance (format: YYYY-MM-DD)
    private String classe;             // Classe (ex: 4TWIN, 3INFO, etc.)
}
```

**Table MySQL** :
```sql
CREATE TABLE etudiant (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100),
    prenom VARCHAR(100),
    dateNaissance VARCHAR(10),
    classe VARCHAR(50)
);
```

### 🛠️ Technologies & Frameworks

| Technologie | Version | Rôle |
|-------------|---------|------|
| Java | 11+ | Langage de programmation |
| Jakarta EE | 9.1.0 | Framework entreprise (successeur de Java EE) |
| JAX-WS | 3.0 | API pour les services web SOAP |
| MySQL | 8.0+ | Base de données relationnelle |
| Maven | 3.6+ | Gestion des dépendances et build |
| SLF4J + Logback | 1.7.36 | Système de logging |
| JDBC | - | Connexion à la base de données |

---

## ⚠️ RÉSOLUTION DES ERREURS DE COMPILATION

Si vous voyez des erreurs comme **"java.lang.Object cannot be resolved"** ou **"String cannot be resolved"**, suivez ces étapes **DANS L'ORDRE** :

### Méthode 1 : Dans VS Code (RECOMMANDÉ)
1. Fermez tous les fichiers ouverts
2. Ouvrez la palette de commandes : `Ctrl+Shift+P`
3. Tapez et exécutez : **`Java: Clean Java Language Server Workspace`**
4. Sélectionnez **"Restart and delete"** quand demandé
5. Redémarrez VS Code complètement
6. Ouvrez le projet et attendez que l'indexation se termine (voir coin inférieur droit)

### Méthode 2 : Maven Clean & Compile
```powershell
cd c:\Users\chams\OneDrive\Bureau\projetPIDEV\SOA4
mvn clean install
```

### Méthode 3 : Vérifier/Configurer le JDK
1. Palette de commandes : `Ctrl+Shift+P`
2. Exécutez : **`Java: Configure Java Runtime`**
3. Assurez-vous qu'un **JDK 11+** est configuré
4. Si aucun JDK n'est listé, téléchargez-en un depuis : https://adoptium.net/

---

---

## 🚀 Installation et Démarrage Rapide

### Prérequis

Avant de commencer, assurez-vous d'avoir :

- ✅ **JDK 11 ou supérieur** ([Télécharger](https://adoptium.net/))
- ✅ **Apache Maven 3.6+** ([Télécharger](https://maven.apache.org/download.cgi))
- ✅ **MySQL Server 8.0+** ([Télécharger](https://dev.mysql.com/downloads/mysql/))
- ✅ **VS Code avec Extension Pack for Java** (optionnel mais recommandé)

### Étape 1 : Configurer la Base de Données

```sql
-- Créer la base de données
CREATE DATABASE tptest;

-- Utiliser la base de données
USE tptest;

-- Créer la table etudiant
CREATE TABLE etudiant (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    dateNaissance VARCHAR(10),
    classe VARCHAR(50)
);

-- Insérer des données de test (optionnel)
INSERT INTO etudiant (nom, prenom, dateNaissance, classe) VALUES
('Doula', 'Chamseddine', '2000-01-15', '4TWIN'),
('Chokri', 'Yassine', '2000-05-20', '4TWIN'),
('Yaacoub', 'Eya', '2000-08-10', '4TWIN');
```

### Étape 2 : Configurer la Connexion MySQL

Modifiez le fichier `src/model/ConnexionDB.java` selon votre configuration :

```java
private static final String URL = "jdbc:mysql://localhost:3306/tptest";
private static final String USER = "root";              // Votre utilisateur MySQL
private static final String PASSWORD = "";              // Votre mot de passe MySQL
```

### Étape 3 : Compiler le Projet

```bash
cd c:\Users\chams\OneDrive\Bureau\projetPIDEV\SOA4
mvn clean install
```

### Étape 4 : Lancer le Service Web

**Option A - Via Maven :**
```bash
mvn exec:java -Dexec.mainClass="model.Publisher"
```

**Option B - Via votre IDE :**
1. Ouvrez `Publisher.java`
2. Clic droit → Run

**Résultat attendu :**
```
INFO - Database connection established
INFO - Service web publié avec succès à l'adresse: http://localhost:8084/service
```

🌐 **Le service est maintenant accessible à :**
- Service : `http://localhost:8084/service`
- WSDL : `http://localhost:8084/service?wsdl`

### Étape 5 : Tester le Service

**Test 1 - Vérifier le WSDL :**

Ouvrez votre navigateur et allez à : `http://localhost:8084/service?wsdl`

Vous devriez voir le fichier WSDL XML décrivant le service.

**Test 2 - Utiliser le client local :**

```bash
# Dans un nouveau terminal
mvn exec:java -Dexec.mainClass="model.TestService"
```

**Test 3 - Utiliser le client SOAP :**

```bash
# Le serveur doit être lancé (étape 4)
mvn exec:java -Dexec.mainClass="model.Client"
```

---

## 📚 Fonctionnalités du Service

Le service web expose les opérations suivantes :

### 1. 🆕 Ajouter un Étudiant

**Méthode :** `ajouterEtudiant(Etudiant e)`

**Exemple :**
```java
Etudiant etudiant = new Etudiant("Doula", "Chamseddine", "2000-01-15", "4TWIN");
boolean resultat = service.ajouterEtudiant(etudiant);
```

### 2. 🗑️ Supprimer un Étudiant

**Méthode :** `supprimerEtudiant(int id)`

**Exemple :**
```java
boolean resultat = service.supprimerEtudiant(1);
```

### 3. ✏️ Modifier un Étudiant

**Méthode :** `modifierEtudiant(Etudiant e)`

**Exemple :**
```java
Etudiant etudiant = service.getEtudiant(1);
etudiant.setClasse("5TWIN");
boolean resultat = service.modifierEtudiant(etudiant);
```

### 4. 🔍 Récupérer un Étudiant

**Méthode :** `getEtudiant(int id)`

**Exemple :**
```java
Etudiant etudiant = service.getEtudiant(1);
System.out.println(etudiant);
```

### 5. 📋 Récupérer Tous les Étudiants

**Méthode :** `getAllEtudiants()`

**Exemple :**
```java
List<Etudiant> etudiants = service.getAllEtudiants();
etudiants.forEach(System.out::println);
```

---

## 🧪 Tests

Le projet contient plusieurs fichiers de test :

### TestService.java
Tests directs sans passer par le réseau SOAP.

```bash
mvn exec:java -Dexec.mainClass="model.TestService"
```

### test.java
Tests alternatifs avec plus d'exemples.

```bash
mvn exec:java -Dexec.mainClass="model.test"
```

### Client.java
Client SOAP complet qui consomme le service via HTTP.

```bash
mvn exec:java -Dexec.mainClass="model.Client"
```

---

## 🔧 Dépannage

### ❌ Problème : Erreurs "java.lang.Object cannot be resolved"

**Solution :**
1. `Ctrl+Shift+P` → `Java: Clean Java Language Server Workspace`
2. Sélectionnez "Restart and delete"
3. Redémarrez VS Code

### ❌ Problème : Port 8084 déjà utilisé

**Solution :**

Modifiez le port dans `Publisher.java` :
```java
private static final String URL = "http://localhost:8085/service";
```

Puis mettez à jour l'URL dans `Client.java` également.

### ❌ Problème : Connexion à MySQL échoue

**Vérifications :**
1. MySQL est démarré : `mysql -u root -p`
2. La base `tptest` existe : `SHOW DATABASES;`
3. Les identifiants dans `ConnexionDB.java` sont corrects

### ❌ Problème : Le client ne trouve pas le service

**Vérifications :**
1. Le serveur `Publisher` est lancé
2. L'URL dans `Client.java` correspond à celle du `Publisher`
3. Le pare-feu ne bloque pas le port 8084

---

## 📖 Utilisation avec SoapUI

Vous pouvez également tester le service avec **SoapUI** :

1. Téléchargez [SoapUI](https://www.soapui.org/downloads/soapui/)
2. Créez un nouveau projet SOAP
3. URL WSDL : `http://localhost:8084/service?wsdl`
4. Testez les opérations disponibles

---

## 🎓 Concepts SOA Utilisés

### Service-Oriented Architecture (SOA)
- **Modularité** : Séparation des préoccupations (service, données, client)
- **Réutilisabilité** : Le service peut être consommé par plusieurs clients
- **Interopérabilité** : Standard SOAP compatible avec différentes plateformes

### Design Patterns
- **Singleton** : `ConnexionDB` pour gérer la connexion unique à la DB
- **DAO (Data Access Object)** : Séparation de la logique d'accès aux données
- **Service Layer** : Couche de service entre le client et la base de données

### Bonnes Pratiques
- ✅ Utilisation de PreparedStatement (protection contre SQL injection)
- ✅ Try-with-resources pour la gestion des ressources
- ✅ Logging avec SLF4J
- ✅ Gestion des exceptions appropriée
- ✅ Migration vers Jakarta EE (moderne)

---

## 👨‍💻 Auteur

**Chamseddine Doula**  
Projet PIDEV - SOA4  
École Supérieure Privée d'Ingénierie et de Technologies (ESPRIT)

---

## 📄 Licence

Ce projet est développé à des fins éducatives dans le cadre du cours SOA.

---

## 📞 Support

Pour toute question ou problème :
1. Consultez le fichier `INSTRUCTIONS.md` pour des détails supplémentaires
2. Vérifiez les logs du serveur pour des messages d'erreur
3. Assurez-vous que tous les prérequis sont installés

---

**Bon développement ! 🚀**
