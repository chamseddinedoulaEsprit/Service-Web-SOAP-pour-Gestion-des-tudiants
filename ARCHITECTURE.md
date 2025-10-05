# 📊 Architecture et Diagrammes - SOA4

## 🏗️ Architecture Globale

```
┌─────────────────────────────────────────────────────────────────┐
│                        COUCHE CLIENT                             │
│                                                                   │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐           │
│  │  Client.java │  │TestService   │  │   SoapUI     │           │
│  │  (SOAP)      │  │   (Direct)   │  │  (Externe)   │           │
│  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘           │
│         │                 │                  │                   │
└─────────┼─────────────────┼──────────────────┼───────────────────┘
          │                 │                  │
          │ HTTP/SOAP       │ Direct Call      │ HTTP/SOAP
          │                 │                  │
┌─────────▼─────────────────▼──────────────────▼───────────────────┐
│                     COUCHE SERVICE WEB                            │
│                                                                   │
│  ┌─────────────────────────────────────────────────────────────┐ │
│  │              Publisher.java (JAX-WS Endpoint)               │ │
│  │              http://localhost:8084/service                  │ │
│  └─────────────────────────────────────────────────────────────┘ │
│                              │                                    │
│  ┌─────────────────────────────────────────────────────────────┐ │
│  │        @WebService Interface                                │ │
│  │        GestionEtudiantService.java                          │ │
│  │        • ajouterEtudiant()                                  │ │
│  │        • supprimerEtudiant()                                │ │
│  │        • modifierEtudiant()                                 │ │
│  │        • getEtudiant()                                      │ │
│  │        • getAllEtudiants()                                  │ │
│  └─────────────────────────────────────────────────────────────┘ │
│                              │                                    │
│  ┌─────────────────────────────────────────────────────────────┐ │
│  │        @WebService Implementation                           │ │
│  │        GestionEtudiantsServiceImpl.java                     │ │
│  │        • Logique métier                                     │ │
│  │        • Validation                                         │ │
│  │        • Logging                                            │ │
│  └─────────────────────────────────────────────────────────────┘ │
│                              │                                    │
└──────────────────────────────┼────────────────────────────────────┘
                               │
                               │ JDBC
                               │
┌──────────────────────────────▼────────────────────────────────────┐
│                     COUCHE PERSISTANCE                            │
│                                                                   │
│  ┌─────────────────────────────────────────────────────────────┐ │
│  │             ConnexionDB.java (Singleton)                    │ │
│  │             • getConnection()                               │ │
│  │             • closeConnection()                             │ │
│  └─────────────────────────────────────────────────────────────┘ │
│                              │                                    │
│  ┌─────────────────────────────────────────────────────────────┐ │
│  │                   MySQL Database                            │ │
│  │                   Database: tptest                          │ │
│  │                   Table: etudiant                           │ │
│  │                   • id (PK, AUTO_INCREMENT)                 │ │
│  │                   • nom, prenom                             │ │
│  │                   • dateNaissance, classe                   │ │
│  └─────────────────────────────────────────────────────────────┘ │
│                                                                   │
└───────────────────────────────────────────────────────────────────┘
```

---

## 📋 Diagramme de Séquence - Ajouter un Étudiant

### Via Client SOAP

```
Client.java          Service SOAP         ServiceImpl         ConnexionDB         MySQL
    │                     │                    │                   │               │
    │ 1. Créer Etudiant   │                    │                   │               │
    │─────────────────────┤                    │                   │               │
    │                     │                    │                   │               │
    │ 2. ajouterEtudiant()│                    │                   │               │
    │────────────────────>│                    │                   │               │
    │                     │                    │                   │               │
    │                     │ 3. ajouterEtudiant()                   │               │
    │                     │───────────────────>│                   │               │
    │                     │                    │                   │               │
    │                     │                    │ 4. getConnection()│               │
    │                     │                    │──────────────────>│               │
    │                     │                    │                   │               │
    │                     │                    │ 5. Connection     │               │
    │                     │                    │<──────────────────│               │
    │                     │                    │                   │               │
    │                     │                    │ 6. PreparedStatement              │
    │                     │                    │──────────────────────────────────>│
    │                     │                    │                   │               │
    │                     │                    │ 7. INSERT INTO ...│               │
    │                     │                    │──────────────────────────────────>│
    │                     │                    │                   │               │
    │                     │                    │ 8. Résultat (true/false)          │
    │                     │                    │<──────────────────────────────────│
    │                     │                    │                   │               │
    │                     │ 9. boolean result  │                   │               │
    │                     │<───────────────────│                   │               │
    │                     │                    │                   │               │
    │ 10. Résultat        │                    │                   │               │
    │<────────────────────│                    │                   │               │
    │                     │                    │                   │               │
```

---

## 🔄 Flux de Données - getAllEtudiants()

```
┌──────────────┐
│   Client     │  1. Appel getAllEtudiants()
└──────┬───────┘
       │
       ↓
┌──────────────────────────────┐
│  Service SOAP (JAX-WS)       │  2. Route vers l'implémentation
└──────┬───────────────────────┘
       │
       ↓
┌──────────────────────────────┐
│ GestionEtudiantsServiceImpl  │  3. Exécute la logique métier
└──────┬───────────────────────┘
       │
       ↓
┌──────────────────────────────┐
│     ConnexionDB              │  4. Obtient la connexion
└──────┬───────────────────────┘
       │
       ↓
┌──────────────────────────────┐
│   MySQL Database             │  5. SELECT * FROM etudiant
│   SELECT * FROM etudiant     │
└──────┬───────────────────────┘
       │
       │ 6. ResultSet
       ↓
┌──────────────────────────────┐
│ GestionEtudiantsServiceImpl  │  7. Transforme en List<Etudiant>
│ while(rs.next()) {           │
│   Etudiant e = new Etudiant()│
│   e.setId(rs.getInt("id"))   │
│   ...                        │
│   etudiants.add(e)           │
│ }                            │
└──────┬───────────────────────┘
       │
       │ 8. List<Etudiant>
       ↓
┌──────────────────────────────┐
│  Service SOAP (JAX-WS)       │  9. Sérialise en XML (SOAP)
└──────┬───────────────────────┘
       │
       │ 10. SOAP Response XML
       ↓
┌──────────────────────────────┐
│       Client                 │  11. Désérialise et affiche
│  etudiants.forEach(          │
│    e -> println(e)           │
│  )                           │
└──────────────────────────────┘
```

---

## 🎭 Diagramme de Classes

```
┌─────────────────────────────┐
│      <<Entity>>             │
│       Etudiant              │
├─────────────────────────────┤
│ - id: int                   │
│ - nom: String               │
│ - prenom: String            │
│ - dateNaissance: String     │
│ - classe: String            │
├─────────────────────────────┤
│ + getId(): int              │
│ + setId(int): void          │
│ + getNom(): String          │
│ + setNom(String): void      │
│ + getPrenom(): String       │
│ + setPrenom(String): void   │
│ + getDateNaissance(): String│
│ + setDateNaissance(...): void│
│ + getClasse(): String       │
│ + setClasse(String): void   │
│ + toString(): String        │
└─────────────────────────────┘
           △
           │ uses
           │
┌──────────┴──────────────────┐
│   <<Interface>>             │
│   @WebService               │
│ GestionEtudiantService      │
├─────────────────────────────┤
│ + ajouterEtudiant(...): bool│
│ + supprimerEtudiant(...): bool│
│ + modifierEtudiant(...): bool│
│ + getEtudiant(int): Etudiant│
│ + getAllEtudiants(): List   │
└─────────────────────────────┘
           △
           │ implements
           │
┌──────────┴──────────────────┐
│   <<Service>>               │
│   @WebService               │
│GestionEtudiantsServiceImpl  │
├─────────────────────────────┤
│ - logger: Logger            │
├─────────────────────────────┤
│ + ajouterEtudiant(...): bool│
│ + supprimerEtudiant(...): bool│
│ + modifierEtudiant(...): bool│
│ + getEtudiant(int): Etudiant│
│ + getAllEtudiants(): List   │
└─────────────────────────────┘
           │ uses
           │
           ↓
┌─────────────────────────────┐
│   <<Singleton>>             │
│     ConnexionDB             │
├─────────────────────────────┤
│ - URL: String               │
│ - USER: String              │
│ - PASSWORD: String          │
│ - connection: Connection    │
├─────────────────────────────┤
│ + getConnection(): Connection│
│ + closeConnection(): void   │
└─────────────────────────────┘
```

---

## 🔐 Flux d'Authentification et Sécurité

```
┌──────────────────────────────────────────────────────────┐
│                    Sécurité du Service                    │
├──────────────────────────────────────────────────────────┤
│                                                           │
│  1. Protection SQL Injection                             │
│     ✓ Utilisation de PreparedStatement                  │
│     ✓ Paramètres bindés (?, ?, ?)                       │
│                                                           │
│  2. Gestion des Ressources                               │
│     ✓ Try-with-resources pour auto-fermeture            │
│     ✓ Connexion DB fermée automatiquement                │
│                                                           │
│  3. Gestion des Erreurs                                  │
│     ✓ Catch SQLException                                 │
│     ✓ Logging des erreurs                                │
│     ✓ Retour de valeurs appropriées (null/false)         │
│                                                           │
│  4. Logging                                              │
│     ✓ SLF4J pour traçabilité                            │
│     ✓ Logs d'erreurs et d'informations                   │
│                                                           │
└──────────────────────────────────────────────────────────┘
```

---

## 📦 Déploiement

```
┌─────────────────────────────────────────────────────────┐
│                   Environnement de Développement         │
│                                                          │
│  ┌────────────────┐         ┌────────────────┐          │
│  │   VS Code      │         │   Eclipse      │          │
│  │   + Java Ext   │   OU    │   + Maven      │          │
│  │   + Maven      │         │                │          │
│  └────────────────┘         └────────────────┘          │
│                                                          │
│  ┌──────────────────────────────────────────┐           │
│  │  JDK 11+                                 │           │
│  │  (java, javac, jar)                      │           │
│  └──────────────────────────────────────────┘           │
│                                                          │
│  ┌──────────────────────────────────────────┐           │
│  │  Apache Maven 3.6+                       │           │
│  │  (mvn)                                   │           │
│  └──────────────────────────────────────────┘           │
│                                                          │
│  ┌──────────────────────────────────────────┐           │
│  │  MySQL Server 8.0+                       │           │
│  │  Port: 3306                              │           │
│  └──────────────────────────────────────────┘           │
│                                                          │
└─────────────────────────────────────────────────────────┘

                        ↓
              mvn clean package
                        ↓

┌─────────────────────────────────────────────────────────┐
│                   Artefact de Build                      │
│                                                          │
│  target/                                                 │
│  └── classes/                                            │
│      └── model/                                          │
│          ├── Etudiant.class                              │
│          ├── ConnexionDB.class                           │
│          ├── GestionEtudiantService.class                │
│          ├── GestionEtudiantsServiceImpl.class           │
│          ├── Publisher.class                             │
│          └── Client.class                                │
│                                                          │
└─────────────────────────────────────────────────────────┘

                        ↓
            mvn exec:java Publisher
                        ↓

┌─────────────────────────────────────────────────────────┐
│              Service Web en Exécution                    │
│                                                          │
│  ┌──────────────────────────────────────────┐           │
│  │  JAX-WS Endpoint                         │           │
│  │  http://localhost:8084/service           │           │
│  │  WSDL: ?wsdl                             │           │
│  └──────────────────────────────────────────┘           │
│                                                          │
└─────────────────────────────────────────────────────────┘
```

---

## 🎯 Points Clés de l'Architecture

### ✅ Avantages

1. **Séparation des Préoccupations**
   - Interface (contrat) séparée de l'implémentation
   - Couches distinctes (Client, Service, DAO, Database)

2. **Réutilisabilité**
   - Le service peut être consommé par n'importe quel client SOAP
   - Indépendant du langage du client

3. **Maintenabilité**
   - Code organisé et modulaire
   - Facile à tester et à déboguer
   - Logging centralisé

4. **Sécurité**
   - PreparedStatement (anti SQL injection)
   - Gestion appropriée des exceptions
   - Try-with-resources (pas de fuite mémoire)

5. **Standards**
   - Utilisation de Jakarta EE (standard moderne)
   - Protocole SOAP (interopérable)
   - WSDL pour la documentation automatique

### 📊 Métriques du Projet

- **Fichiers Java** : 8
- **Lignes de code** : ~600
- **Dépendances Maven** : 9
- **Méthodes du service** : 5 (CRUD)
- **Technologies** : 6 principales

---

**Documentation créée pour SOA4 - Projet PIDEV**
