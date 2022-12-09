package es.ucm.fdi.takeorder;

public class MesaElement {
    public String color;
    public String name;
    public String status;

    public MesaElement(String name) {
        this.color = "775447";
        this.name = name;
        this.status = "Activo";
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

