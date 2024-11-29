# JavaProjectEcosytem

### Projet Java - ENSTA UE 3.1

Ce projet est développé pour la réalisation d'un jeu basé sur un **écosystème interactif** en Java. L'objectif principal est de permettre au joueur d'évoluer sur une carte générée à partir d'un fichier, tout en respectant les contraintes et en développant les interactions entre le joueur et son environnement.

---

## Méthodologie : SPEED 1 WEEKEND

Ce projet est divisé en plusieurs **jalons**, chacun représentant une étape clé du développement. Les fonctionnalités sont développées de manière progressive et itérative.

---

### **1er Jalon : Environnement**
L'objectif du premier jalon est de poser les bases du projet, avec une carte fonctionnelle et des déplacements du joueur.

#### Fonctionnalités :
- **Créer une map à partir d'un fichier texte** :
    - Génération d'une carte en lisant un fichier `map.txt` contenant les informations sur les cases (herbe, mur, etc.).
- **Classe `Game`** :
    - Gestion des éléments principaux du jeu (joueur, carte, interactions).
- **Classe `Player`** :
    - Déplacement du joueur sur la carte.
- **Déplacement du joueur** :
    - Gestion des contrôles pour permettre au joueur de se déplacer.
- **Vision par fenêtre** :
    - Mise en place d'une caméra centrée sur le joueur, affichant une portion limitée de la carte.
- **Ajout de graphiques** :
    - Textures pour représenter les différents types de cases (herbe, mur, etc.).
- **Collisions joueur/mur** :
    - Empêcher le joueur de traverser les obstacles (murs).
- **Gestion des bords de la map** :
    - Empêcher le joueur de sortir des limites de la carte.

#### **Objectif final :**
À la fin du premier jalon, le joueur doit pouvoir se déplacer sur la carte entière, avec une caméra qui le suit dynamiquement.
✅ **Terminé** le 29/11/2024.

---

### **2ème Jalon : Écosystème et Interactions**
L'objectif du second jalon est d'ajouter de nouveaux personnages, éléments et interactions dans l'écosystème.

#### Fonctionnalités prévues :

---

## Installation et Configuration

### Prérequis :
- **Java JDK** 17 ou supérieur.
- **JavaFX SDK** (configuré avec IntelliJ IDEA).

### Instructions :
1. Clonez le projet :
   ```bash
   git clone https://github.com/etiennehrn/JavaProjectEcosytem.git
## **Structure du projet**

Le projet est organisé selon la structure suivante :

```plaintext
src/
├── Main.java           # Classe principale (lance l'application)
├── Game.java           # Gestion des éléments du jeu (carte, joueur, interactions)
├── Player.java         # Classe pour le joueur et ses mouvements
├── Map.java            # Classe pour générer et gérer la carte
├── Case.java           # Classe pour représenter une case de la carte
└── ressources/         # Dossier contenant les fichiers nécessaires
    ├── map.txt         # Fichier texte pour générer la carte
    └── textures/       # Textures pour les éléments graphiques
        ├── herbe.jpg   # Texture pour les cases d'herbe
        ├── mur.jpg     # Texture pour les murs
        └── player.png  # Texture pour le joueur