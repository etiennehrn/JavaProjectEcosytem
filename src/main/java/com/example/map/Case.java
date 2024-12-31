package com.example.map;

public class Case {
    public BaseType baseType;
    public Element element;

    public Case(BaseType baseType, Element element) {
        this.baseType = baseType;
        this.element = element;
    }

    // Getters
    public Element getElement() {return element;}
    public BaseType getBaseType() {return baseType;}

    public String getCode() {
        if(baseType != null && element != null) {
            return baseType.getCode() + "_" + element.getCode();
        }
        if(baseType != null && element == null) {
            return baseType.getCode() + "_";
        }
        return "";
    }

    //Setters
    public void setBaseType(BaseType baseTypeSelected) {
        this.baseType = baseTypeSelected;
    }

    public void setElement(Element elementSelected) {
        this.element = elementSelected;
    }
}
