package com.example.map;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

public class Selectionneur {

    // Sélection des types et éléments
    public BaseType BaseTypeSelected;
    public Element ElementSelected;

    // Menus principaux
    public Menu BaseType;
    public Menu Element;

    // Sous Menus
    public Menu BaseTypeGrass;
    public Menu BaseTypeWater;
    public Menu BaseTypeYellowGrass;


    public Menu ElementArbre;
    public Menu ElementCailloux;
    public Menu ElementFleurs;
    public Menu ElementLongGrass;

    // Dictionnaires des variants
    private final Map<MenuItem, EauType.VariantWater> menuItemWaterMap = new HashMap<>();
    private final Map<MenuItem, HerbeType.VariantHerbe> menuItemHerbeMap = new HashMap<>();
    private final Map<MenuItem, SandType.VariantHerbeJaune> menuItemHerbeJauneMap = new HashMap<>();
    private final Map<MenuItem, ArbreElement.VariantArbre> menuItemArbreMap = new HashMap<>();
    private final Map<MenuItem, CaillouxElement.VariantCailloux> menuItemCaillouxMap = new HashMap<>();

    // Dimensions des images
    public int Height = 50;
    public int Width = 50;

    public Selectionneur() {
        // Création des menus principaux
        BaseType = new Menu("Base Type");
        Element = new Menu("Element");

        // Création des sous-menus
        BaseTypeGrass = new Menu("Grass");
        BaseTypeWater = new Menu("Water");
        BaseTypeYellowGrass = new Menu("Yellow Grass");
        ElementArbre = new Menu("Tree");
        ElementCailloux = new Menu("Stone");


        // Ajout des sous-menus aux menus principaux
        BaseType.getItems().addAll(BaseTypeGrass, BaseTypeYellowGrass, BaseTypeWater);
        Element.getItems().addAll(ElementArbre, ElementCailloux);
    }

    public void Initialisation() {
        // Initialisation des ressources
        initMenu(BaseTypeWater, EauType.VariantWater.values(), EauType.getTextureMap(), menuItemWaterMap);
        initMenu(BaseTypeGrass, HerbeType.VariantHerbe.values(), HerbeType.getTextureMap(), menuItemHerbeMap);
        initMenu(BaseTypeYellowGrass, SandType.VariantHerbeJaune.values(), SandType.getTextureMap(), menuItemHerbeJauneMap);
        initMenu(ElementArbre, ArbreElement.VariantArbre.values(), ArbreElement.getTextureMap(), menuItemArbreMap);
        initMenu(ElementCailloux, CaillouxElement.VariantCailloux.values(), CaillouxElement.getTextureMap(), menuItemCaillouxMap);
        }

    private <T> void initMenu(Menu menu, T[] variants, Map<T, Image> textureMap, Map<MenuItem, T> menuItemMap) {
        for (T variant : variants) {
            ImageView imageView = createImageView(textureMap.get(variant));
            MenuItem item = new MenuItem(variant.toString(), imageView);
            menuItemMap.put(item, variant);
            menu.getItems().add(item);
            setupActionButton(item, menuItemMap);
        }
    }

    private ImageView createImageView(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(Width);
        imageView.setFitHeight(Height);
        return imageView;
    }

    private <T> void setupActionButton(MenuItem item, Map<MenuItem, T> menuItemMap) {
        item.setOnAction(event -> {
            if (menuItemMap.containsKey(item)) {
                T variant = menuItemMap.get(item);

                if (variant instanceof EauType.VariantWater) {
                    this.ElementSelected = null;
                    this.BaseTypeSelected = new EauType((EauType.VariantWater) variant);
                } else if (variant instanceof HerbeType.VariantHerbe) {
                    this.ElementSelected = null;
                    this.BaseTypeSelected = new HerbeType((HerbeType.VariantHerbe) variant);
                }else if (variant instanceof SandType.VariantHerbeJaune) {
                    this.ElementSelected = null;
                    this.BaseTypeSelected = new SandType((SandType.VariantHerbeJaune) variant);
                } else if (variant instanceof ArbreElement.VariantArbre) {
                    this.ElementSelected = new ArbreElement((ArbreElement.VariantArbre) variant);
                    this.BaseTypeSelected = null;
                } else if (variant instanceof CaillouxElement.VariantCailloux) {
                    this.ElementSelected = new CaillouxElement((CaillouxElement.VariantCailloux) variant);
                    this.BaseTypeSelected = null;
                }
            } else {
                this.ElementSelected = null;
                this.BaseTypeSelected = null;
            }
        });
    }
}
