package ual.poo.TransportationCompany;

import java.io.Serializable;
import java.util.ArrayList;

public class DepositClass implements DepositInterface,Serializable {
    public LocalClass local;
    public ArrayList<ItemClass> items;
    public ArrayList<ProfessionalClass> professionals;
    public int id;

    public DepositClass (LocalClass local, ArrayList<ItemClass> items, ArrayList<ProfessionalClass> professionals) {
        this.local = local;
        this.items = items;
        this.professionals = professionals;
    }

    public void addDepositId(int depositId){
        this.id = depositId;
    }
}
