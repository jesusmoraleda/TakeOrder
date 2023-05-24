package es.ucm.fdi.takeorder.model;

public class Ingredient {

    private String name;
    private String id;
    private double quantity;

    public Ingredient() {
    }

    public Ingredient(String name, String id, double quantity) {
        this.name = name;
        this.id = id;
        this.quantity = quantity;
    }

    // Métodos getter y setter para los campos "name" y "quantity"
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
