package ual.poo.TransportationCompany;

import java.io.Serializable;

public class LocalClass implements LocalInterface,Serializable {
    public int id;
    public String name;

    public LocalClass (int localId, String localName) {
        this.id = localId;
        this.name = localName;
    }
}
