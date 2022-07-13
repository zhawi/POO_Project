package ual.poo.TransportationCompany;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemClass implements ItemInterface,Serializable {
    public String name;
    public int id;
    public int quantity;
    public String[] permission;
    ArrayList<Integer> quantityDeposit;
    ArrayList<Integer> quantityDelivery;

    public ItemClass (String itemName, int itemQuantity, String[] permission, int itemId) {
        this.name = itemName;
        this.quantity = itemQuantity;
        this.id = itemId;
        this.permission = permission;
        this.quantityDeposit = new ArrayList<>();
        this.quantityDelivery = new ArrayList<>();
    }
}
