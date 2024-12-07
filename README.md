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

✅ **Terminé** le 29/11/2024.
### **Jalon 3 : Améliorations et nouvelles fonctionnalités**
- **1. Améliorations des déplacements**
  - Correction des bugs de mouvements.
  - Les zombies cherchent activement les humains à proximité.
  - Les humains fuient les zombies lorsqu'ils sont détectés.

- **2. Ajouts esthétiques**
  - Ajout d'une icône pour représenter l'application.
  - Carte retravaillée pour être plus cohérente et immersive.
  - Ajout d'une musique d'ambiance pour enrichir l'expérience.

- **3. Interactions améliorées**
  - Les humains deviennent des zombies si un zombie se trouve à proximité.

✅ **Terminé** le 29/11/2024.
## **Jalon 4 : Améliorations de l'environnement et nouvelles fonctionnalités**

### **Nouvelles fonctionnalités implémentées :**

#### **1. Amélioration de l'environnement**
- Ajout de nouveaux composants dans l'environnement :
  - **Types de cases** supplémentaires (eau, arbres, rochers, etc.).
  - Diversification des textures pour les rendre plus immersives.

#### **2. Déplacement plus fluide pour le joueur**
- Optimisation des contrôles pour un déplacement sans latence.
- Meilleur suivi de la caméra centré sur le joueur.

#### **3. Ajout des animaux**
- Introduction d'animaux dans la carte (par exemple : cerfs, lapins).
- Pas de comportement pour l'instant
#### **4. Cycle jour/nuit**
- Transition progressive entre les phases de jour et de nuit.
- Effets visuels pour marquer les changements de lumière.

#### **5. Carte refaite et agrandie**
- Nouvelle carte repensée avec une plus grande diversité de structures :
  - **Zones naturelles** comme forêts et rivières.
- Taille de la carte augmentée pour offrir plus de possibilités d'exploration.

---
✅ **Terminé** le 29/11/2024.


# Jalon 5 : Gestion des animaux et interactions avancées

## Description
Ce jalon introduit une gestion approfondie des animaux et leurs interactions dans l'environnement. Il enrichit la simulation avec des comportements spécifiques, une répartition réaliste des animaux, et des améliorations visuelles et dynamiques.

---

## Nouvelles Fonctionnalités Implémentées

### 1. Déplacement des Animaux
Chaque espèce animale possède des comportements uniques qui influencent leurs déplacements :
- **Cerfs** : Se déplacent en groupe (troupeaux) et fuient à l'approche d'un prédateur (loups ou ours).
- **Lapins** : Errent de manière aléatoire et peuvent être chassés et mangés par les loups.
- **Ours** : Se déplacent seuls et effraient les autres animaux dans leur périmètre.

### 2. Amélioration de la Fonction `populate`
Les animaux sont répartis de façon cohérente dans l'environnement :
- **Cerfs** : Regroupés en troupeaux dans des zones dégagées.
- **Loups et ours** : Rares, répartis de manière stratégique sur la carte.

### 3. Ajout d'une Horloge
Introduction d'un cycle horaire avec un affichage dynamique à l'écran :
- **Cycle visuel** : Ombres allongées le matin et le soir, transitions fluides entre les différentes heures.
- **Horloge en temps réel** : Synchronisation avec les événements simulés.

### 4. Interactions entre les Animaux
Les comportements des animaux influencent leur environnement :
- Les cerfs fuient les loups et les ours.
- Les lapins sont chassés par les loups.
- Les ours effraient tous les autres animaux sauf les loups.
- Simulation d'un écosystème dynamique où les relations prédateurs-proies et les comportements de fuite créent une immersion réaliste.

### 5. Carte encore Plus Réaliste
Ajout d'éléments naturels pour améliorer l'immersion :
- Groupes d'arbres et de buissons plus denses.
- Zones rocheuses et troncs d'arbres tombés.
- Élargissement des chemins reliant les zones clés.
- Harmonisation visuelle et meilleure diversité des éléments naturels.

---

✅ **Terminé** le 06/12/2024.

# Jalon 6 : Amélioration des déplacements et corrections mineurs

## Description
Ce jalon a pour but de faire des déplacements plus génériques (dans la super classe EtreVivant) et de les adapter pour l'ensemble des êtres vivants
---

## Nouvelles Fonctionnalités Implémentées

### 1. Déplacement des Animaux
Chaque espèce animale possède des comportements uniques qui influencent leurs déplacements :
- **Cerfs** : Se déplacent en groupe (troupeaux) et fuient à l'approche d'un prédateur (loups ou ours).
- **Lapins** : Errent de manière aléatoire et peuvent être chassés et mangés par les loups.
- **Loups** : Chassent activement les lapins et provoquent la fuite des cerfs.

Les déplacements prennent en compte :
- Les obstacles naturels (arbres, rochers, buissons).
- Les interactions dynamiques entre les animaux.

### 2. Prise en compte possible des obstacles dans la vision Range
Les obstacles modifient la vision des être vivants
- Faire une méthode isVisionBloquante générique pour les cases
- Corriger la fonction qui recherche les êtres vivants dans visionRange

### 3. Ajout Sprites pour animaux
Faire en sorte que les mouvements paraissent plus fluide (donc ajout de sprites)
- Pour les ours
- Pour les cerfs
- Pours les lapins

### 4. Amélioration de l'horloge
- Faire des transitions encore plus fluides pour la luminosité

### 5. Amélioration des commentaires
- Commencer à faire quelques (pas trop, car les fonctions vont encore évoluer) commentaires formatés avec ChatGPT pour les fonctions (dans l'optique de faire une JavaDoc plus tard)

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
├── com/
│   ├── etienne/
│   │   ├── ecosysteme/
│   │   │   ├── core/
│   │   │   │   ├── DayNightCycle
│   │   │   │   ├── Game
│   │   │   │   └── Main
│   │   │   ├── entities/
│   │   │   │   ├── Animaux
│   │   │   │   ├── Bear
│   │   │   │   ├── Boar
│   │   │   │   ├── Bunny
│   │   │   │   ├── Deer
│   │   │   │   ├── EtreVivant
│   │   │   │   ├── Fox
│   │   │   │   ├── Humain
│   │   │   │   ├── Player
│   │   │   │   ├── Wolf
│   │   │   │   └── Zombie
│   │   │   ├── environment/
│   │   │   │   ├── Case
│   │   │   │   ├── MapEnvironnement
│   │   │   │   └── MapVivant
├── ressources/
│   ├── audio/
│   ├── icons/
│   ├── map/
│   ├── sprites/
│   └── textures/
└── test/
