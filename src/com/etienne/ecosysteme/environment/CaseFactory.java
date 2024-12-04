package com.etienne.ecosysteme.environment;

import java.util.HashMap;
import java.util.Map;

// Fabrique les cases différentes
public class CaseFactory {
    // Cache pour les cases
    private static final Map<String, Case> caseCache = new HashMap<>();

    // Créer la case si elle n'existe pas, sinon utilise le cache
    public static Case createCase(String code) {
        // On vérifie si la case est dans le cache
        if (!caseCache.containsKey(code)) {
            // Dans ce cas on créer la case
            Case newCase = generateCaseFromCode(code);
            caseCache.put(code, newCase);
        }
        return caseCache.get(code);
    }

    // Donne la case en fonction du code de la forme LettreNuméro (Lettre(en maj) -> TypeBase et Numéro -> Element)
    private static Case generateCaseFromCode(String code) {
        String baseCode = code.substring(0, 1);
        String elementCode = code.length() > 1 ? code.substring(1) : null;

        BaseType baseType = switch (baseCode) {
            // ICI LE CODE DES TYPES DE BASES
            case "0" -> new HerbeType(HerbeType.Variant.CLAIR);
            case "1" -> new HerbeType(HerbeType.Variant.DALLE);
            case "2" -> new EauType(EauType.Variant.CENTRE);
            case "3" -> new EauType(EauType.Variant.GAUCHE);
            case "4" -> new EauType(EauType.Variant.DROIT);
            case "5" -> new EauType(EauType.Variant.HAUT);
            case "6" -> new EauType(EauType.Variant.BAS);
            case "7" -> new EauType(EauType.Variant.HAUT_GAUCHE);
            case "8" -> new EauType(EauType.Variant.HAUT_DROITE);
            case "9" -> new EauType(EauType.Variant.BAS_GAUCHE);
            case "10" -> new EauType(EauType.Variant.BAS_DROITE);

            default -> throw new IllegalArgumentException("Type de base inconnu : " + baseCode);
        };

        Element element = null;
        if (elementCode != null) {
            element = switch (elementCode) {
                // ICI LE CODE DES ELEMENTS
                case "A" -> new ArbreElement(ArbreElement.Variant.CLAIR);
                case "B" -> new ArbreElement(ArbreElement.Variant.MORT);
                case "C" -> new ArbreElement(ArbreElement.Variant.BUISSON);
                case "D" -> new ArbreElement(ArbreElement.Variant.CHAMPIGNON);
                case "E" -> new ArbreElement(ArbreElement.Variant.NENUPHAR);
                case "F" -> new CaillouxElement(CaillouxElement.Variant.MOYEN);
                case "G" -> new CaillouxElement(CaillouxElement.Variant.PETIT);
                case "H" -> new ArbreElement(ArbreElement.Variant.DENSE_CLAIR);
                default -> throw new IllegalArgumentException("Type d'élément inconnu : " + elementCode);
            };
        }
        return new Case(baseType, element);
    }
}
/*
    // Méthode pour convertir Value en Case
    private Case parseCase(String value) {
        return switch (value) {
            case "0" -> herbeCase;
            case "1" -> caillouxPetitCase;
            case "2" -> herbeArbreCase;
            case "3" -> herbeBuissonCase;
            case "4" -> herbeCaillouxMoyenCase;
            case "5" -> herbeTroncCase;
            case "6" -> dalleCase;
            case "7" -> herbeChampigonCase;
            case "8" -> eauCase;
            case "9" -> nenupharEauCase;
            default -> throw new IllegalArgumentException("Le valeur de case n'existe pas");
        };
    }
    */