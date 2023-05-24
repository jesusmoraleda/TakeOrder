package es.ucm.fdi.takeorder.model;
/*package es.ucm.fdi.takeorder.model;

public class PlatesOrderElement {
    //segun se representen los campos en la base de datos, asi deben ir en el modelo
    String name;
    String amount;

    public PlatesOrderElement(){}

    public PlatesOrderElement(String name, String amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}*/

import java.util.List;


public class PlatesOrderElement {
    private String name;
    private int amount;
    private boolean entregado;
    private String category;
    private List<Ingredient> ingredients;

    public PlatesOrderElement() {}

    public PlatesOrderElement(String name, int amount, boolean entregado, String category, List<Ingredient> ingredients) {
        this.name = name;
        this.amount = amount;
        this.entregado = entregado;
        this.category = category;
        this.ingredients = ingredients;
    }

    // Métodos getter y setter para el campo "ingredients"
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    // Métodos getter y setter para los campos "name", "amount", "available" y "category"
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isEntregado() {
        return entregado;
    }

    public void setEntregado(boolean entregado) {
        this.entregado = entregado;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

