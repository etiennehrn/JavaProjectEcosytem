package com.etienne.ecosysteme.environment;

// Chaque case est composée d'un type de base (herbe, eau, sol) et d'un élément (arbre, cailloux, etc.) (ou rien comme élément)
public class Case {
    // Type de base
    private final BaseType baseType;

    // Element de la case, non final peut etre on modifiera
    private Element element;

    // Constructeur
    public Case(BaseType baseType, Element element) {
        this.baseType = baseType;
        this.element = element;
    }

    // Getter
    public BaseType getBaseType() {
        return baseType;
    }
    public Element getElement() {
        return element;
    }

    // Setter
    public void setElement(Element element) {
        this.element = element;
    }

    // Méthode pour savoir si une case est un obstacle
    public boolean isObstacle() {
        if (element != null) {
            if (element.canPassObstacle()) {
                return false;
            }
        }
        return baseType.isObstacle() || (element != null && element.isObstacle());
    }
}



































/*
public class Case {
    // Type de la case
    public enum Type {
        HERBE, MUR, DALLE, EAU, NENUPHAR_EAU
    }

    // Element sur le type de la case
    public enum Element {
        NONE, ARBRE, BUISSON, CAILLOUX, TRONC, CHAMPIGNON, SALADE
    }


    private static final Image HERBE_TEXTURE;
    private static final Image MUR_TEXTURE;
    private static final Image DALLE_TEXTURE;
    private static final Image EAU_TEXTURE;
    private static final Image NENUPHAR_EAU_TEXTURE;

    private static final Image ARBRE_TEXTURE;
    private static final Image BUISSON_TEXTURE;
    private static final Image CAILLOUX_TEXTURE;
    private static final Image TRONC_TEXTURE;
    private static final Image CHAMPIGNON_TEXTURE;
    private static final Image SALADE_TEXTURE;



    static {
        try {
            HERBE_TEXTURE = new Image(Objects.requireNonNull(Case.class.getResourceAsStream("/ressources/textures/herbe_clair.png"), "Texture herbe.jpg introuvable ! Assurez-vous que le fichier est dans le dossier ressources/textures."));
            MUR_TEXTURE = new Image(Objects.requireNonNull(Case.class.getResourceAsStream("/ressources/textures/cailloux_petit.jpg"), "Texture cailloux_petit.jpg introuvable ! Assurez-vous que le fichier est dans le dossier ressources/textures."));
            DALLE_TEXTURE = new Image(Objects.requireNonNull(Case.class.getResourceAsStream("/ressources/textures/herbe_dalle.png"), "Texture herbe_dalle.png introuvable ! Assurez-vous que le fichier est dans le dossier ressources/textures."));
            EAU_TEXTURE = new Image(Objects.requireNonNull(Case.class.getResourceAsStream("/ressources/textures/eau_centre.png"), "Texture eau_centre.png introuvable ! Assurez-vous que le fichier est dans le dossier ressources/textures."));
            NENUPHAR_EAU_TEXTURE = new Image(Objects.requireNonNull(Case.class.getResourceAsStream("/ressources/textures/nenuphar_eau.png"), "Texture eau_centre.png introuvable ! Assurez-vous que le fichier est dans le dossier ressources/textures."));

            ARBRE_TEXTURE = new Image(Objects.requireNonNull(Case.class.getResourceAsStream("/ressources/textures/arbre_clair.png"), "Texture abre1.jpg introuvable ! Assurez-vous que le fichier est dans le dossier ressources/textures."));
            BUISSON_TEXTURE = new Image(Objects.requireNonNull(Case.class.getResourceAsStream("/ressources/textures/arbre_buisson.png"), "Texture arbre_buisson.png introuvable ! Assurez-vous que le fichier est dans le dossier ressources/textures."));
            CAILLOUX_TEXTURE = new Image(Objects.requireNonNull(Case.class.getResourceAsStream("/ressources/textures/cailloux_moyen.png"), "Texture cailloux_moyen.png introuvable ! Assurez-vous que le fichier est dans le dossier ressources/textures."));
            TRONC_TEXTURE = new Image(Objects.requireNonNull(Case.class.getResourceAsStream("/ressources/textures/arbre_mort.png"), "Texture arbre_mort.png introuvable ! Assurez-vous que le fichier est dans le dossier ressources/textures."));
            CHAMPIGNON_TEXTURE = new Image(Objects.requireNonNull(Case.class.getResourceAsStream("/ressources/textures/champignon.png"), "Texture champignon.png introuvable ! Assurez-vous que le fichier est dans le dossier ressources/textures."));
            SALADE_TEXTURE = new Image(Objects.requireNonNull(Case.class.getResourceAsStream("/ressources/textures/salade.png"), "Texture salade.png introuvable ! Assurez-vous que le fichier est dans le dossier ressources/textures."));

        } catch (NullPointerException e) {
            throw new RuntimeException("Erreur lors du chargement des textures : " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Une erreur inattendue s'est produite lors du chargement des textures.", e);
        }
    }

    private final Type type;
    private final Image baseTexture;
    private final Image elementTexture;
    private Element element; // Pour la couche supérieur

    public Case(Type type, Element element) {
        this.type = type;
        this.baseTexture = switch (type) {
            case HERBE -> HERBE_TEXTURE;
            case MUR -> MUR_TEXTURE;
            case DALLE -> DALLE_TEXTURE;
            case EAU -> EAU_TEXTURE;
            case NENUPHAR_EAU -> NENUPHAR_EAU_TEXTURE;
        };
        this.element = element;

        this.elementTexture = switch (element) {
            case NONE -> null;
            case ARBRE -> ARBRE_TEXTURE;
            case BUISSON -> BUISSON_TEXTURE;
            case CAILLOUX -> CAILLOUX_TEXTURE;
            case TRONC -> TRONC_TEXTURE;
            case CHAMPIGNON -> CHAMPIGNON_TEXTURE;
            case SALADE -> SALADE_TEXTURE;
        };

    }

    // Getter
    public Image getBaseTexture() {
        return baseTexture;
    }
    public Type getType() {
        return type;
    }
    public Element getElement() {
        return element;
    }
    public void setElement(Element element) {
        this.element = element;
    }
    public boolean isObstacle() {
        return type == Type.MUR || type == Type.EAU || element == Element.ARBRE || element == Element.CAILLOUX;
    }
    public Image getElementTexture() {
        return elementTexture;
    }
}
*/