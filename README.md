# Talo

Version : 0.0.1

Talo est une application Android offline-first conçue pour les petits commerces (boutiques, bars, alimentations, quincailleries).

L’application permet de remplacer le cahier traditionnel par un système simple, rapide et fiable.

---

# 1. Problème

Aujourd’hui, beaucoup de commerçants :

- notent leurs ventes dans un cahier
- oublient certaines transactions
- gèrent mal les crédits clients
- ne savent pas combien ils gagnent réellement
- subissent des pertes (erreurs ou vols)

---

# 2. Solution

Talo agit comme :


un **cahier numérique intelligent**

Permettant :

- d’enregistrer toutes les ventes
- de suivre les crédits clients
- de gérer le stock
- de voir les performances journalières
- d’imprimer des tickets

---

# 3. Fonctionnalités MVP

## Vente (feature principale)
- ajout de produits
- panier
- calcul automatique du total
- paiement cash ou crédit

## Stock
- ajout produit
- modification stock
- décrément automatique après vente

## Crédit client
- création client
- dette client
- remboursement partiel ou total

## Rapport journalier
- total ventes
- total cash
- total crédit
- nombre de ventes

## Licence
- activation par code
- expiration locale
- blocage si non payé

---

# 4. Fonctionnement Offline

L'application fonctionne sans internet :

- stockage local via Room (SQLite)
- aucune dépendance serveur
- toutes les opérations critiques sont locales

Un backend pourra être ajouté plus tard pour :
- synchronisation
- gestion des licences
- analytics

---

# 5. Stack Technique

- Kotlin
- Jetpack Compose
- MVVM
- Repository Pattern
- Room (SQLite)
- Navigation Compose

---

# 6. Architecture


UI → ViewModel → Repository → Room


### UI
Affichage + interaction utilisateur

### ViewModel
Logique métier + état UI

### Repository
Accès aux données

### Room
Stockage local

---

# 7. Structure du projet

com.monshop.pos
│
├── data
│   ├── local
│   │   ├── dao
│   │   ├── database
│   │   └── entity
│   └── repository
│
├── ui
│   ├── navigation
│   ├── screens
│   │   ├── home
│   │   ├── sale
│   │   ├── stock
│   │   ├── credit
│   │   ├── report
│   │   └── license
│   └── theme
│
├── viewmodel
│
└── MainActivity.kt


---

# 8. Écrans MVP

- Accueil
- Vente
- Crédit
- Stock
- Rapport
- Licence

---

# 9. Modèle de données (simplifié)