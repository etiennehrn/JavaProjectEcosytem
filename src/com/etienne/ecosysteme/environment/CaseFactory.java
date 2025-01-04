package com.etienne.ecosysteme.environment;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe représentant une fabrique de cases pour la carte.
 * Cette fabrique utilise un cache pour optimiser la création et réutiliser les instances de cases.
 */
public class CaseFactory {

    // Cache statique pour stocker les instances de cases déjà créées
    private static final Map<String, Case> caseCache = new HashMap<>();

    /**
     * Crée une instance de case en fonction d'un code.
     * Si la case existe déjà dans le cache, elle est réutilisée.
     *
     * @param code Code représentant la case (par exemple, "00_A").
     * @return Une instance de case correspondant au code.
     */
    public static Case createCase(String code) {
        // Vérifie si la case existe dans le cache
        if (!caseCache.containsKey(code)) {
            // Si elle n'existe pas, on la génère et l'ajoute au cache
            Case newCase = generateCaseFromCode(code);
            caseCache.put(code, newCase);
        }
        return caseCache.get(code);
    }

    /**
     * Génère une case à partir d'un code.
     * Le code est sous la forme "Numéro_Lettre", où :
     * - Numéro (2 chiffres) correspond au type de base (par exemple, herbe, eau, sable).
     * - Lettre (en majuscules) correspond à un élément (par exemple, arbre, rocher).
     *
     * @param code Code représentant la case.
     * @return Une instance de case.
     */
    private static Case generateCaseFromCode(String code) {
        // Sépare le code en deux parties : base et élément
        String[] parts = code.split("_");
        if (parts.length == 0 || parts.length > 2) {
            throw new IllegalArgumentException("Format de code invalide : " + code);
        }

        String baseCode = parts[0];
        String elementCode = parts.length == 2 ? parts[1] : null;

        // Associe le code de base à un type de terrain
        BaseType baseType = switch (baseCode) {
            case "00" -> new HerbeType(HerbeType.Variant.CLAIR);
            case "01" -> new HerbeType(HerbeType.Variant.DALLE);
            case "02" -> new EauType(EauType.Variant.CENTRE);
            case "03" -> new EauType(EauType.Variant.GAUCHE);
            case "04" -> new EauType(EauType.Variant.DROITE);
            case "05" -> new EauType(EauType.Variant.HAUT);
            case "06" -> new EauType(EauType.Variant.BAS);
            case "07" -> new EauType(EauType.Variant.HAUT_GAUCHE);
            case "08" -> new EauType(EauType.Variant.HAUT_DROITE);
            case "09" -> new EauType(EauType.Variant.BAS_GAUCHE);
            case "10" -> new EauType(EauType.Variant.BAS_DROITE);
            case "11" -> new EauType(EauType.Variant.VERTICAL_HAUT);
            case "12" -> new EauType(EauType.Variant.VERTICAL_BAS);
            case "13" -> new EauType(EauType.Variant.VERTICAL_MILIEU);
            case "14" -> new EauType(EauType.Variant.HORIZONTAL_GAUCHE);
            case "15" -> new EauType(EauType.Variant.HORIZONTAL_DROITE);
            case "16" -> new EauType(EauType.Variant.HORIZONTAL_MILIEU);
            case "17" -> new EauType(EauType.Variant.UNIQUE);

            case "18" -> new SableType(SableType.Variant.CENTRE);
            case "19" -> new SableType(SableType.Variant.GAUCHE);
            case "20" -> new SableType(SableType.Variant.DROITE);
            case "21" -> new SableType(SableType.Variant.HAUT);
            case "22" -> new SableType(SableType.Variant.BAS);
            case "23" -> new SableType(SableType.Variant.HAUT_GAUCHE);
            case "24" -> new SableType(SableType.Variant.HAUT_DROITE);
            case "25" -> new SableType(SableType.Variant.BAS_GAUCHE);
            case "26" -> new SableType(SableType.Variant.BAS_DROITE);
            case "27" -> new SableType(SableType.Variant.VERTICAL_HAUT);
            case "28" -> new SableType(SableType.Variant.VERTICAL_BAS);
            case "29" -> new SableType(SableType.Variant.VERTICAL_MILIEU);
            case "30" -> new SableType(SableType.Variant.HORIZONTAL_GAUCHE);
            case "31" -> new SableType(SableType.Variant.HORIZONTAL_DROITE);
            case "32" -> new SableType(SableType.Variant.HORIZONTAL_MILIEU);
            case "33" -> new SableType(SableType.Variant.UNIQUE);

            default -> throw new IllegalArgumentException("Type de base inconnu : " + baseCode);
        };

        // Associe le code d'élément à un type d'élément, s'il existe
        Element element = null;
        if (elementCode != null) {
            element = switch (elementCode) {
                case "A" -> new ArbreElement(ArbreElement.Variant.CLAIR);
                case "B" -> new ArbreElement(ArbreElement.Variant.MORT);
                case "C" -> new ArbreElement(ArbreElement.Variant.BUISSON);
                case "D" -> new ArbreElement(ArbreElement.Variant.CHAMPIGNON);
                case "E" -> new ArbreElement(ArbreElement.Variant.NENUPHAR);
                case "F" -> new CaillouxElement(CaillouxElement.Variant.MOYEN);
                case "G" -> new CaillouxElement(CaillouxElement.Variant.PETIT);
                case "H" -> new ArbreElement(ArbreElement.Variant.DENSE_CLAIR);
                case "I" -> new CaillouxElement(CaillouxElement.Variant.PONT);

                default -> throw new IllegalArgumentException("Type d'élément inconnu : " + elementCode);
            };
        }

        // Retourne une instance de case avec le type de base et l'élément
        return new Case(baseType, element);
    }
}
