package com.etienne.ecosysteme.environment;

import java.util.HashMap;
import java.util.Map;

/**
 * La classe `CaseFactory` est responsable de la création des cases dans le jeu.
 * Elle utilise un cache pour éviter de recréer les mêmes cases plusieurs fois et optimise ainsi la gestion de la mémoire.
 */
public class CaseFactory {
    /**
     * Cache pour stocker les cases créées afin de réutiliser celles qui ont déjà été créées.
     */
    private static final Map<String, Case> caseCache = new HashMap<>();

    /**
     * Crée une case en fonction de son code. Si la case a déjà été créée, elle est récupérée depuis le cache.
     *
     * @param code Le code de la case, sous forme de chaîne de caractères (par exemple "00_A").
     * @return La case correspondant au code spécifié.
     */
    public static Case createCase(String code) {
        // On vérifie si la case est dans le cache
        if (!caseCache.containsKey(code)) {
            // Dans ce cas on créer la case
            Case newCase = generateCaseFromCode(code);
            caseCache.put(code, newCase);
        }
        return caseCache.get(code);
    }

    /**
     * Génère une case à partir de son code.
     * Le code se compose d'un numéro de type de base suivi d'un élément optionnel (séparés par un underscore).
     * Par exemple, "00_A" pour une case de type herbe avec un arbre.
     *
     * @param code Le code de la case à générer.
     * @return La case générée correspondant au code spécifié.
     * @throws IllegalArgumentException Si le format du code est invalide ou si le type de base ou l'élément est inconnu.
     */
    static Case generateCaseFromCode(String code) {
        // On sépare le code selon le caractère _
        String[] parts = code.split("_");
        if (parts.length == 0 || parts.length > 2) {
            throw new IllegalArgumentException("Invalid code format");
        }
        String baseCode = parts[0];
        String elementCode = parts.length == 2 ? parts[1]  : null;

        BaseType baseType = switch (baseCode) {
            // ICI LE CODE DES TYPES DE BASES
            case "00" -> new HerbeType(HerbeType.VariantHerbe.CLAIR);
            case "01" -> new HerbeType(HerbeType.VariantHerbe.DALLE);
            case "02" -> new EauType(EauType.VariantWater.CENTRE);
            case "03" -> new EauType(EauType.VariantWater.GAUCHE);
            case "04" -> new EauType(EauType.VariantWater.DROITE);
            case "05" -> new EauType(EauType.VariantWater.HAUT);
            case "06" -> new EauType(EauType.VariantWater.BAS);
            case "07" -> new EauType(EauType.VariantWater.HAUT_GAUCHE);
            case "08" -> new EauType(EauType.VariantWater.HAUT_DROITE);
            case "09" -> new EauType(EauType.VariantWater.BAS_GAUCHE);
            case "10" -> new EauType(EauType.VariantWater.BAS_DROITE);
            case "11" -> new EauType(EauType.VariantWater.VERTICAL_HAUT);
            case "12" -> new EauType(EauType.VariantWater.VERTICAL_BAS);
            case "13" -> new EauType(EauType.VariantWater.VERTICAL_MILIEU);
            case "14" -> new EauType(EauType.VariantWater.HORIZONTAL_GAUCHE);
            case "15" -> new EauType(EauType.VariantWater.HORIZONTAL_DROITE);
            case "16" -> new EauType(EauType.VariantWater.HORIZONTAL_MILIEU);
            case "17" -> new EauType(EauType.VariantWater.UNIQUE);

            case "18" -> new SableType(SableType.VariantSable.CENTRE);
            case "19" -> new SableType(SableType.VariantSable.GAUCHE);
            case "20" -> new SableType(SableType.VariantSable.DROITE);
            case "21" -> new SableType(SableType.VariantSable.HAUT);
            case "22" -> new SableType(SableType.VariantSable.BAS);
            case "23" -> new SableType(SableType.VariantSable.HAUT_GAUCHE);
            case "24" -> new SableType(SableType.VariantSable.HAUT_DROITE);
            case "25" -> new SableType(SableType.VariantSable.BAS_GAUCHE);
            case "26" -> new SableType(SableType.VariantSable.BAS_DROITE);
            case "27" -> new SableType(SableType.VariantSable.VERTICAL_HAUT);
            case "28" -> new SableType(SableType.VariantSable.VERTICAL_BAS);
            case "29" -> new SableType(SableType.VariantSable.VERTICAL_MILIEU);
            case "30" -> new SableType(SableType.VariantSable.HORIZONTAL_GAUCHE);
            case "31" -> new SableType(SableType.VariantSable.HORIZONTAL_DROITE);
            case "32" -> new SableType(SableType.VariantSable.HORIZONTAL_MILIEU);
            case "33" -> new SableType(SableType.VariantSable.UNIQUE);

            default -> throw new IllegalArgumentException("Type de base inconnu : " + baseCode);
        };

        Element element = null;
        if (elementCode != null) {
            element = switch (elementCode) {
                // ICI LE CODE DES ELEMENTS
                case "A" -> new ArbreElement(ArbreElement.VariantArbre.CLAIR);
                case "B" -> new ArbreElement(ArbreElement.VariantArbre.MORT);
                case "C" -> new ArbreElement(ArbreElement.VariantArbre.BUISSON);
                case "D" -> new ArbreElement(ArbreElement.VariantArbre.CHAMPIGNON);
                case "E" -> new ArbreElement(ArbreElement.VariantArbre.NENUPHAR);
                case "F" -> new ArbreElement(ArbreElement.VariantArbre.DENSE_CLAIR);
                case "G" -> new CaillouxElement(CaillouxElement.VariantCailloux.MOYEN);
                case "H" -> new CaillouxElement(CaillouxElement.VariantCailloux.PETIT);

                default -> throw new IllegalArgumentException("Type d'élément inconnu : " + elementCode);
            };
        }
        return new Case(baseType, element);
    }
}