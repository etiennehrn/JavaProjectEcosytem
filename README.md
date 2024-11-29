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
- **Créer une mapEnvironnement à partir d'un fichier texte** :
    - Génération d'une carte en lisant un fichier `mapEnvironnement.txt` contenant les informations sur les cases (herbe, mur, etc.).
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
- **Gestion des bords de la mapEnvironnement** :
    - Empêcher le joueur de sortir des limites de la carte.

#### **Objectif final :**
À la fin du premier jalon, le joueur doit pouvoir se déplacer sur la carte entière, avec une caméra qui le suit dynamiquement.
✅ **Terminé** le 29/11/2024.

---

### **2ème Jalon : Écosystème et Interactions**
L'objectif du second jalon est d'ajouter de nouveaux personnages, éléments et interactions dans l'écosystème.


### **Fonctionnalités prévues**

Le deuxième jalon vise à introduire des interactions entre les humains et les zombies dans l'écosystème. L'objectif est de modéliser un monde vivant dynamique avec des comportements aléatoires et des transformations.

#### **Objectifs :**
0. **Créer une superClasse 'Mob'** :
1. **Créer une classe `Humain`** :
  - Représente un Humain avec ses propres caractéristiques (position, état, etc.).
  - Ajout d'attributs pour gérer l'état de l'Humain (par exemple : "infecté").
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

---

### **Fonctionnalités implémentées**

#### **1. Classe `Humain`**
- Définit les attributs de l'Humain (position, état).
- Comportement de déplacement aléatoire.

#### **2. Classe `Zombie`**
- Définit les attributs du zombie (position).
- Déplacement aléatoire simulant un comportement erratique.

#### **3. Carte des vivants (`map_vivants`)**
- Structure de données permettant de suivre tous les vivants (humains et zombies) sur la carte.
git 
#### **4. Sprites**
- Ajout d'une texture distincte pour chaque type :
  - **Humain** : Sprite représentant un personnage.
  - **Zombie** : Sprite représentant une figure zombie.

#### **5. Interactions humaines/zombies**
- Les humains à proximité immédiate d’un zombie sont transformés en zombies :
  - Vérification de cases adjacentes (haut, bas, gauche, droite).
  - Transformation et mise à jour de l'état dans `map_vivants`.

---✅ **Terminé** le 29/11/2024.
### **Jalon 3 : Améliorations et nouvelles fonctionnalités**
- **1. Améliorations des déplacements**
  - Correction des bugs de mouvements.
  - Les zombies cherchent activement les humains à proximité.
  - Les humains fuient les zombies lorsqu'ils sont détectés.
  - Gestion de vitesses différentes pour les humains (si possible).

- **2. Ajouts esthétiques**
  - Ajout d'une icône pour représenter l'application.
  - Carte retravaillée pour être plus cohérente et immersive.
  - Ajout d'une musique d'ambiance pour enrichir l'expérience.

- **3. Interactions améliorées**
  - Les humains deviennent des zombies si un zombie se trouve à proximité.

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
├── Main.java                # Classe principale (lance l'application)
├── Game.java                # Gestion des éléments du jeu (cartes, vivants, interactions)
├── Player.java              # Classe pour le joueur et ses déplacements
├── EtreVivant.java          # Classe abstraite pour les vivants (humains, zombies)
├── Humain.java              # Classe pour les humains
├── Zombie.java              # Classe pour les zombies
├── MapEnvironnement.java    # Classe pour générer et gérer la carte environnementale
├── MapVivant.java           # Classe pour gérer les vivants (humains, zombies)
├── Case.java                # Classe pour représenter une case de la carte
└── ressources/              # Dossier contenant les fichiers nécessaires
    ├── mapEnvironnement.txt # Fichier texte pour générer la carte environnementale
    ├── textures/            # Textures pour les éléments graphiques
    │   ├── herbe.jpg        # Texture pour les cases d'herbe
    │   └── mur.jpg          # Texture pour les murs
    └── sprites/             # Sprites pour les vivants
        ├── player.png       # Sprite pour le joueur
        ├── human.png        # Sprite pour les humains
        └── zombie.png       # Sprite pour les zombies
