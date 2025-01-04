package com.etienne.ecosysteme.environment;

/**
 * Classe représentant une case de la carte.
 * Une case est composée d'un type de base (par exemple, herbe, eau, sable) et,
 * éventuellement, d'un élément (par exemple, arbre, rocher).
 */
public class Case {

    // Type de base de la case (par exemple, herbe, eau, sable)
    private final BaseType baseType;

    // Élément présent sur la case, qui peut être null
    private Element element;

    /**
     * Constructeur de la classe Case.
     * Initialise une case avec un type de base et un élément optionnel.
     *
     * @param baseType Type de base de la case.
     * @param element Élément présent sur la case (peut être null).
     */
    public Case(BaseType baseType, Element element) {
        this.baseType = baseType;
        this.element = element;
    }

    /**
     * Retourne le type de base de la case.
     *
     * @return Type de base de la case.
     */
    public BaseType getBaseType() {
        return baseType;
    }

    /**
     * Retourne l'élément présent sur la case.
     *
     * @return Élément de la case (peut être null).
     */
    public Element getElement() {
        return element;
    }

    /**
     * Définit un nouvel élément pour la case.
     *
     * @param element Nouvel élément à associer à la case.
     */
    public void setElement(Element element) {
        this.element = element;
    }

    /**
     * Détermine si la case est un obstacle.
     * Une case est un obstacle si son type de base ou son élément est un obstacle,
     * sauf si l'élément permet de contourner l'obstacle.
     *
     * @return {@code true} si la case est un obstacle, {@code false} sinon.
     */
    public boolean isObstacle() {
        if (element != null) {
            // Si l'élément permet de passer l'obstacle, la case n'est pas un obstacle
            if (element.canPassObstacle()) {
                return false;
            }
        }
        // La case est un obstacle si son type de base ou son élément est un obstacle
        return baseType.isObstacle() || (element != null && element.isObstacle());
    }
}
