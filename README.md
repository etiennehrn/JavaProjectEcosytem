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

## **Deuxième Jalon : Interactions dans l'écosystème**

### **Fonctionnalités prévues**

Le deuxième jalon vise à introduire des interactions entre les humains et les zombies dans l'écosystème. L'objectif est de modéliser un monde vivant dynamique avec des comportements aléatoires et des transformations.

#### **Objectifs :**
1. **Créer une classe `Humain`** :
  - Représente un humain avec ses propres caractéristiques (position, état, etc.).
  - Ajout d'attributs pour gérer l'état de l'humain (par exemple : "infecté").
2. **Créer une classe `Zombie`** :
  - Représente un zombie avec des comportements spécifiques.
  - Ajout de mouvements aléatoires sur la carte.
3. **Créer une `map_vivants`** :
  - Une structure de données pour recenser tous les êtres vivants (humains et zombies) sur la carte.
  - Permet de suivre leurs positions en temps réel.
4. **Ajouter des sprites pour les humains et les zombies** :
  - Associer une texture (sprite) distincte à chaque type d’être vivant.
  - Les sprites seront affichés sur la carte à leur position respective.
5. **Ajouter des mouvements aléatoires et gérer les collisions** :
  - Les humains et zombies se déplacent de manière aléatoire à chaque tour.
  - Gestion des collisions entre êtres vivants et obstacles.
6. **Interactions : Transformation humain -> zombie** :
  - Si un humain est sur une case adjacente à un zombie, il devient un zombie.

---

### **Fonctionnalités implémentées**

#### **1. Classe `Humain`**
- Définit les attributs de l'humain (position, état).
- Comportement de déplacement aléatoire.

#### **2. Classe `Zombie`**
- Définit les attributs du zombie (position).
- Déplacement aléatoire simulant un comportement erratique.

#### **3. Carte des vivants (`map_vivants`)**
- Structure de données permettant de suivre tous les vivants (humains et zombies) sur la carte.

#### **4. Sprites**
- Ajout d'une texture distincte pour chaque type :
  - **Humain** : Sprite représentant un personnage.
  - **Zombie** : Sprite représentant une figure zombie.

#### **5. Interactions humaines/zombies**
- Les humains à proximité immédiate d’un zombie sont transformés en zombies :
  - Vérification de cases adjacentes (haut, bas, gauche, droite).
  - Transformation et mise à jour de l'état dans `map_vivants`.

---
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