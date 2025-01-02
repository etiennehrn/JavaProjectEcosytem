package com.etienne.ecosysteme.environment;


/**
 * La classe `Case` représente une case dans le jeu. Chaque case est composée d'un type de base
 * (par exemple, herbe, sable ou eau) et d'un élément (par exemple, un arbre, des cailloux, etc.).
 */
public class Case {
    /** Le type de base de la case (par exemple, herbe, sable, eau) */
    public BaseType baseType;

    /** L'élément présent sur la case (par exemple, un arbre ou un cailloux) */
    public Element element;

    /**
     * Constructeur de la classe `Case` qui initialise le type de base et l'élément de la case.
     *
     * @param baseType Le type de base de la case.
     * @param element L'élément de la case.
     */
    public Case(BaseType baseType, Element element) {
        this.baseType = baseType;
        this.element = element;
    }

    /** Méthode pour savoir si une case est un obstacle */
    public boolean isObstacle() {
        if (element != null) {
            if (element.canPassObstacle()) {
                return false;
            }
        }
        return baseType.isObstacle() || (element != null && element.isObstacle());
    }

    // Getters
    /**
     * Récupère l'élément de la case.
     *
     * @return L'élément de la case.
     */
    public Element getElement() {
        return element;
    }

    /**
     * Récupère le type de base de la case.
     *
     * @return Le type de base de la case.
     */
    public BaseType getBaseType() {
        return baseType;
    }

    /**
     * Récupère le code de la case sous forme de chaîne de caractères.
     * Le code est constitué du code du type de base et du code de l'élément (séparés par un underscore).
     *
     * @return Le code de la case, ou une chaîne vide si l'une des deux parties est absente.
     */
    public String getCode() {
        if(baseType != null && element != null) {
            return baseType.getCode() + "_" + element.getCode();
        }
        if(baseType != null && element == null) {
            return baseType.getCode() + "_";
        }
        return "";
    }


    // Setters
    /**
     * Modifie le type de base de la case.
     *
     * @param baseTypeSelected Le nouveau type de base de la case.
     */
    public void setBaseType(BaseType baseTypeSelected) {
        this.baseType = baseTypeSelected;
    }

    /**
     * Modifie l'élément de la case.
     *
     * @param elementSelected Le nouvel élément de la case.
     */
    public void setElement(Element elementSelected) {
        this.element = elementSelected;
    }
}