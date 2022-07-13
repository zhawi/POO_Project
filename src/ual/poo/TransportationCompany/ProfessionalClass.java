package ual.poo.TransportationCompany;

import java.io.Serializable;
import java.util.ArrayList;

public class ProfessionalClass extends Person implements ProfessionalInterface,Serializable {
    public Categories category;
    public Permission permission;
    public int id;
    ArrayList<Integer> deliveryId;


    public ProfessionalClass(String name, String permission, String category, int professionalId) {
        super(name);
        this.category = Categories.valueOf(category.toUpperCase());
        this.permission = Permission.valueOf(permission.toUpperCase());
        this.id = professionalId;
        this.deliveryId = new ArrayList<>();
    }
}
