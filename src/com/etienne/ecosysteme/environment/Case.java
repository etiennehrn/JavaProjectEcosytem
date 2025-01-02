package com.etienne.ecosysteme.environment;

// Chaque case est composée d'un type de base (herbe, eau, sol) et d'un élément (arbre, cailloux, etc.) (ou rien comme élément)
public class Case {
    // Type de base
    private final BaseType baseType;

    // Element de la case, non final peut-être, on modifiera
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
