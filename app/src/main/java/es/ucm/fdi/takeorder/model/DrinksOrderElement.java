package es.ucm.fdi.takeorder.model;

public class DrinksOrderElement {

    //segun se representen los campos en la base de datos, asi deben ir en el modelo
    String name;

    public DrinksOrderElement(){}

    public DrinksOrderElement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
