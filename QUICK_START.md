# 🚀 Guide de Démarrage Rapide - SOA4

## 📋 Checklist Avant de Commencer

- [ ] JDK 11+ installé (`java -version`)
- [ ] Maven installé (`mvn -version`)
- [ ] MySQL démarré et accessible
- [ ] Base de données `tptest` créée
- [ ] Table `etudiant` créée

---

## ⚡ Démarrage en 5 Minutes

### 1️⃣ Créer la Base de Données (une seule fois)

```sql
CREATE DATABASE tptest;
USE tptest;

CREATE TABLE etudiant (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    dateNaissance VARCHAR(10),
    classe VARCHAR(50)
);
```

### 2️⃣ Compiler le Projet

```bash
cd c:\Users\chams\OneDrive\Bureau\projetPIDEV\SOA4
mvn clean install
```

### 3️⃣ Lancer le Serveur

```bash
mvn exec:java -Dexec.mainClass="model.Publisher"
```

**✅ Attendez ce message :**
```
INFO - Service web publié avec succès à l'adresse: http://localhost:8084/service
```

### 4️⃣ Tester (dans un nouveau terminal)

```bash
# Test local (recommandé pour débuter)
mvn exec:java -Dexec.mainClass="model.TestService"

# OU Test via client SOAP
mvn exec:java -Dexec.mainClass="model.Client"
```

---

## 🎯 Commandes Essentielles

| Action | Commande |
|--------|----------|
| Compiler | `mvn clean install` |
| Lancer serveur | `mvn exec:java -Dexec.mainClass="model.Publisher"` |
| Test local | `mvn exec:java -Dexec.mainClass="model.TestService"` |
| Client SOAP | `mvn exec:java -Dexec.mainClass="model.Client"` |
| Nettoyer | `mvn clean` |

---

## 🌐 URLs Importantes

| Description | URL |
|-------------|-----|
| Service SOAP | http://localhost:8084/service |
| WSDL | http://localhost:8084/service?wsdl |

---

## 🔍 Vérifications Rapides

### ✅ Vérifier Java

```bash
java -version
# Attendu: version 11 ou supérieure
```

### ✅ Vérifier Maven

```bash
mvn -version
# Attendu: Apache Maven 3.6+
```

### ✅ Vérifier MySQL

```bash
mysql -u root -p
# Puis dans MySQL:
SHOW DATABASES;
USE tptest;
SHOW TABLES;
```

### ✅ Vérifier le Service

Ouvrez dans un navigateur : `http://localhost:8084/service?wsdl`

Vous devriez voir le fichier WSDL XML.

---

## 🐛 Problèmes Fréquents

### ❌ "Port 8084 already in use"

**Solution :** Changez le port dans `Publisher.java` (ligne 9) :
```java
private static final String URL = "http://localhost:8085/service";
```

### ❌ "Communications link failure"

**Solution :** 
1. Vérifiez que MySQL est démarré
2. Vérifiez les identifiants dans `ConnexionDB.java`

### ❌ "Class not found: com.mysql.cj.jdbc.Driver"

**Solution :** 
```bash
mvn clean install -U
```

### ❌ Erreurs de compilation "java.lang.Object cannot be resolved"

**Solution dans VS Code :**
1. `Ctrl+Shift+P`
2. `Java: Clean Java Language Server Workspace`
3. Sélectionnez "Restart and delete"
4. Redémarrez VS Code

---

## 📊 Workflow Typique

```
┌─────────────────────────────────────────────┐
│ 1. Démarrer MySQL                           │
└────────────────┬────────────────────────────┘
                 ↓
┌─────────────────────────────────────────────┐
│ 2. Terminal 1: mvn exec:java Publisher     │
│    (Serveur SOAP démarre)                   │
└────────────────┬────────────────────────────┘
                 ↓
┌─────────────────────────────────────────────┐
│ 3. Terminal 2: mvn exec:java TestService   │
│    (Tests et ajout de données)              │
└────────────────┬────────────────────────────┘
                 ↓
┌─────────────────────────────────────────────┐
│ 4. Vérifier dans MySQL:                     │
│    SELECT * FROM etudiant;                  │
└─────────────────────────────────────────────┘
```

---

## 🎓 Exemple d'Utilisation Complète

### Scénario : Ajouter et consulter des étudiants

```bash
# Terminal 1 - Démarrer le serveur
cd c:\Users\chams\OneDrive\Bureau\projetPIDEV\SOA4
mvn exec:java -Dexec.mainClass="model.Publisher"

# Attendez le message de confirmation...

# Terminal 2 - Exécuter les tests
mvn exec:java -Dexec.mainClass="model.TestService"

# Vous verrez:
# - Ajout de 3 étudiants
# - Liste de tous les étudiants
# - Récupération d'un étudiant par ID
# - Modification d'un étudiant

# Terminal 3 - Vérifier dans MySQL
mysql -u root -p
USE tptest;
SELECT * FROM etudiant;
```

---

## 📝 Notes Importantes

1. **Le serveur doit rester actif** pendant les tests avec le client SOAP
2. **Ctrl+C** pour arrêter le serveur
3. **TestService** fonctionne même si le serveur n'est pas lancé (accès direct)
4. **Client** nécessite que le serveur soit lancé (communication SOAP)

---

## 🎯 Objectif de Chaque Fichier

| Fichier | Rôle | Quand l'utiliser |
|---------|------|------------------|
| `Publisher.java` | Serveur SOAP | Toujours en premier |
| `TestService.java` | Tests directs | Pour tester rapidement |
| `Client.java` | Client SOAP | Pour tester via le réseau |
| `test.java` | Tests alternatifs | Alternative à TestService |

---

**Prêt à commencer ? Suivez les 4 étapes ci-dessus ! 🚀**
