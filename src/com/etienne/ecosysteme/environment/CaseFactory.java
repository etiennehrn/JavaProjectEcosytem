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

    // Donne la case en fonction du code de la forme Numéro_Lettre (Numéro (2 chiffres) -> typeBase, Lettre(en maj) -> Element)
    private static Case generateCaseFromCode(String code) {
        // On sépare le code selon le caractère _
        String[] parts = code.split("_");
        if (parts.length == 0 || parts.length > 2) {
            throw new IllegalArgumentException("Invalid code format");
        }
        String baseCode = parts[0];
        String elementCode = parts.length == 2 ? parts[1]  : null;

        BaseType baseType = switch (baseCode) {
            // ICI LE CODE DES TYPES DE BASES
            case "00" -> new HerbeType(HerbeType.Variant.CLAIR);
            case "01" -> new HerbeType(HerbeType.Variant.DALLE);
            case "02" -> new EauType(EauType.Variant.CENTRE);
            case "03" -> new EauType(EauType.Variant.GAUCHE);
            case "04" -> new EauType(EauType.Variant.DROIT);
            case "05" -> new EauType(EauType.Variant.HAUT);
            case "06" -> new EauType(EauType.Variant.BAS);
            case "07" -> new EauType(EauType.Variant.HAUT_GAUCHE);
            case "08" -> new EauType(EauType.Variant.HAUT_DROITE);
            case "09" -> new EauType(EauType.Variant.BAS_GAUCHE);
            case "10" -> new EauType(EauType.Variant.BAS_DROITE);
            case "11" -> new HerbeType(HerbeType.Variant.FONCE);


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
                case "I" -> new CaillouxElement(CaillouxElement.Variant.PONT);

                default -> throw new IllegalArgumentException("Type d'élément inconnu : " + elementCode);
            };
        }
        return new Case(baseType, element);
    }
}
