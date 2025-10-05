# Instructions d'utilisation - Projet SOA4

## ✅ Fichiers corrigés

Tous les fichiers Java ont été corrigés et sont maintenant fonctionnels :

1. **ConnexionDB.java** - Connexion à la base de données avec logging
2. **Etudiant.java** - Entité avec les champs : id, nom, prenom, dateNaissance, classe
3. **GestionEtudiantService.java** - Interface du service web
4. **GestionEtudiantsServiceImpl.java** - Implémentation avec méthodes CRUD
5. **Publisher.java** - Publication du service web
6. **TestService.java** - Tests locaux du service
7. **test.java** - Tests alternatifs
8. **Client.java** - Client SOAP pour consommer le service

## 📋 Étapes pour exécuter le projet

### 1️⃣ Préparer la base de données

```sql
CREATE DATABASE tptest;
USE tptest;

CREATE TABLE etudiant (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100),
    prenom VARCHAR(100),
    dateNaissance VARCHAR(10),
    classe VARCHAR(50)
);
```

### 2️⃣ Vérifier la connexion MySQL

Modifiez `src/model/ConnexionDB.java` si nécessaire :
```java
private static final String URL = "jdbc:mysql://localhost:3306/tptest";
private static final String USER = "root";
private static final String PASSWORD = "";  // Votre mot de passe MySQL
```

### 3️⃣ Compiler le projet

```powershell
cd c:\Users\chams\OneDrive\Bureau\projetPIDEV\SOA4
mvn clean compile
```

### 4️⃣ Lancer le serveur SOAP

**Option A : Via Maven**
```powershell
mvn exec:java -Dexec.mainClass="model.Publisher"
```

**Option B : Via votre IDE**
- Exécutez la classe `Publisher.java`

**Le service sera disponible à :**
- URL du service : `http://localhost:8084/service`
- WSDL : `http://localhost:8084/service?wsdl`

### 5️⃣ Tester le service

**Option 1 : Tests locaux (sans passer par le réseau)**
```powershell
mvn exec:java -Dexec.mainClass="model.TestService"
```
ou
```powershell
mvn exec:java -Dexec.mainClass="model.test"
```

**Option 2 : Client SOAP (via le réseau)**

⚠️ **Le serveur doit être lancé d'abord** (étape 4)

```powershell
mvn exec:java -Dexec.mainClass="model.Client"
```

## 🔍 Vérifier que tout fonctionne

### 1. Vérifier le WSDL
Ouvrez dans un navigateur : `http://localhost:8084/service?wsdl`

Vous devriez voir le fichier WSDL du service.

### 2. Vérifier les logs
Le serveur affiche des logs quand il reçoit des requêtes :
```
INFO - Database connection established
INFO - Service web publié avec succès à l'adresse: http://localhost:8084/service
```

### 3. Vérifier la base de données
```sql
USE tptest;
SELECT * FROM etudiant;
```

## 📝 Méthodes disponibles dans le service

Le service web expose ces méthodes :

| Méthode | Paramètres | Retour | Description |
|---------|-----------|--------|-------------|
| `ajouterEtudiant` | Etudiant e | boolean | Ajoute un étudiant |
| `supprimerEtudiant` | int id | boolean | Supprime un étudiant |
| `modifierEtudiant` | Etudiant e | boolean | Modifie un étudiant |
| `getEtudiant` | int id | Etudiant | Récupère un étudiant par ID |
| `getAllEtudiants` | - | List<Etudiant> | Récupère tous les étudiants |

## 🐛 Résolution des problèmes

### Problème : Port 8084 déjà utilisé
**Solution :** Changez le port dans `Publisher.java` :
```java
private static final String URL = "http://localhost:8085/service";
```

### Problème : Erreur de connexion à MySQL
**Solution :**
1. Vérifiez que MySQL est démarré
2. Vérifiez les identifiants dans `ConnexionDB.java`
3. Vérifiez que la base `tptest` existe

### Problème : Erreurs de compilation "java.lang.Object cannot be resolved"
**Solution :**
1. `Ctrl+Shift+P` → `Java: Clean Java Language Server Workspace`
2. Redémarrez VS Code
3. Ou exécutez : `mvn clean install`

### Problème : Le client ne trouve pas le service
**Solution :**
1. Vérifiez que le serveur (`Publisher`) est lancé
2. Vérifiez l'URL dans `Client.java` (doit correspondre à celle du `Publisher`)
3. Vérifiez le QName (namespace et nom du service)

## 📊 Exemple de test complet

```powershell
# Terminal 1 : Lancer le serveur
mvn exec:java -Dexec.mainClass="model.Publisher"

# Terminal 2 : Tester avec le client local
mvn exec:java -Dexec.mainClass="model.TestService"

# Terminal 3 : Tester avec le client SOAP
mvn exec:java -Dexec.mainClass="model.Client"
```

## 🎯 Prochaines étapes

1. ✅ Les fichiers sont corrigés
2. ✅ La structure du projet est bonne
3. ⏭️ Lancez le serveur (`Publisher`)
4. ⏭️ Testez avec `TestService` ou `Client`
5. ⏭️ Vérifiez les données dans MySQL

## 📚 Technologies utilisées

- **Jakarta EE 9.1.0** - Framework d'entreprise
- **JAX-WS 3.0** - Services web SOAP
- **MySQL 8.0.27** - Base de données relationnelle
- **SLF4J + Logback** - Système de logging
- **Maven** - Gestion des dépendances et build

Bon développement ! 🚀
