package es.ucm.fdi.takeorder.model;

public class MenuElement {

    private String name;
    private String category;
    private int quantity_menu;
    private int quantity;
    private boolean in_menu;
    private boolean entregado;

    public MenuElement(){}

    public MenuElement(String name, String category, int quantity_menu, int quantity, boolean in_menu, boolean entregado) {
        this.name = name;
        this.category = category;
        this.quantity_menu = quantity_menu;
        this.quantity = quantity;
        this.in_menu = in_menu;
        this.entregado = entregado;
    }

    public int getQuantity_menu() {
        return quantity_menu;
    }

    public void setQuantity_menu(int quantity_menu) {
        this.quantity_menu = quantity_menu;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isIn_menu() {
        return in_menu;
    }

    public void setIn_menu(boolean in_menu) {
        this.in_menu = in_menu;
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
