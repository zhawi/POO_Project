package ual.poo.TransportationCompany;

import java.io.Serializable;
import java.util.ArrayList;

public class ClientClass extends Person implements ClientInterface,Serializable {
    public int id;
    public int professionalId;
    public ArrayList<ItemClass> items;
    public ArrayList<DepositClass> deposits;
    public ArrayList<DeliveryClass> deliveries;
    public int depositId = 0;
    public int deliveryId = 0;
    public int itemId = 0;

    public ClientClass (String clientName, int clientId, int professionalId) {
        super(clientName);
        this.id = clientId;
        this.professionalId = professionalId;
        this.items = new ArrayList<>();
        this.deposits = new ArrayList<>();
        this.deliveries = new ArrayList<>();
    }

    public void addItem (ItemClass item) {
        this.items.add(item);
    }

    public void updateItem (){
        this.itemId++;
    }

    public void addDeposit (DepositClass deposit){
        depositId++;
        deposit.addDepositId(depositId);
        this.deposits.add(deposit);
    }

    public void addDelivery (DeliveryClass delivery) {
        deliveryId++;
        delivery.addDeliveryId(deliveryId);
        this.deliveries.add(delivery);
    }
}
