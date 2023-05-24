package es.ucm.fdi.takeorder.model;

public class DrinksOrderElement {

    //segun se representen los campos en la base de datos, asi deben ir en el modelo
    private String name;
    private int amount;
    private boolean entregado;

    public DrinksOrderElement(){}

    public DrinksOrderElement(String name, int amount, boolean entregado) {
        
        this.name = name;
        this.amount = amount;
        this.entregado = entregado;
    }

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

    public boolean getEntregado() {
        return entregado;
    }

    public void setEntregado(boolean entregado) {
        this.entregado = entregado;
    }

}
