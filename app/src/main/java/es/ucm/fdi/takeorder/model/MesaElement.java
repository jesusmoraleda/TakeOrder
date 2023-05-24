package es.ucm.fdi.takeorder.model;

public class MesaElement {
    //segun se representen los campos en la base de datos, asi deben ir en el modelo
     private String nombre,numero;

     public MesaElement(){}

    public MesaElement(String nombre, String numero) {
        this.nombre = nombre;
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}

