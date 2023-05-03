package es.ucm.fdi.takeorder.model;

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
}
