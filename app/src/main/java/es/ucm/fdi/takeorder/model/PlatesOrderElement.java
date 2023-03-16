package es.ucm.fdi.takeorder.model;

public class PlatesOrderElement {
    //segun se representen los campos en la base de datos, asi deben ir en el modelo
    String nombre;

    public PlatesOrderElement(){}

    public PlatesOrderElement(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
