package es.ucm.fdi.takeorder.model;

public class DrinksOrderElement {

    //segun se representen los campos en la base de datos, asi deben ir en el modelo
    String name;
    String amount;
    String ultAmount;
    boolean entregado;

    public DrinksOrderElement(){}

    public DrinksOrderElement(String name, String amount,String ultAmount, boolean entregado) {
        
        this.name = name;
        this.amount = amount;
        this.ultAmount = ultAmount;
        this.entregado = entregado;
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

    public boolean getEntregado() {
        return entregado;
    }

    public void setEntregado(boolean entregado) {
        this.entregado = entregado;
    }

    public String getUltAmount() {return ultAmount;}

    public void setUltAmount(String ultAmount) {this.ultAmount = ultAmount;}


}
