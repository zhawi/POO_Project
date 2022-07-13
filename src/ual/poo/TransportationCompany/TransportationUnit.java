package ual.poo.TransportationCompany;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TransportationUnit implements TransportationUnitInterface, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    public String name;
    public ArrayList<ProfessionalClass> professionals;
    public ArrayList<ClientClass> clients;
    public ArrayList<DeliveryClass> deliveries;
    public ArrayList<ItemClass> items;
    public ArrayList<LocalClass> locals;
    public int professionalId;
    public int clientId;
    public int localId;

    public TransportationUnit (String name) {
        this.clientId = 0;
        this.localId = 0;
        this.professionalId = 0;
        this.name = name;
        this.professionals = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.deliveries = new ArrayList<>();
        this.items = new ArrayList<>();
        this.locals = new ArrayList<>();
    }

    public boolean hasProfessional (String professionalName, String professionalCategory) {
        boolean hasPro = false;
        if (this.professionals.isEmpty()) {
            return false;
        } else {
            for (ProfessionalClass professional : this.professionals) {
                if (professional.category.toString().equals(professionalCategory.toUpperCase())) {
                    if (professionalName.equals(professional.name)) {
                        hasPro = true;
                    }
                }
            }
        }
        return hasPro;
    }

    public boolean hasCategory (String professionalCategory) {
        boolean hasCategory = false;
        for (Categories cat : Categories.values()) {
            if (cat.toString().equals(professionalCategory.toUpperCase())) {
                hasCategory = true;
            }
        }
        return hasCategory;
    }

    public boolean hasCorrectPermission (String professionalPermission, String professionalCategory) {
        boolean hasCorrectPermission = false;
        for (Categories cat : Categories.values()) {
            if (cat.toString().equals(professionalCategory.toUpperCase())) {
                for (Permission permission : Permission.values()) {
                    if (permission.toString().equals(professionalPermission.toUpperCase())) {
                        if (cat.toString().equals("CONDUTOR") && (permission.toString().equals("N") || permission.toString().equals("P"))) {
                            hasCorrectPermission = true;
                        }
                        if (cat.toString().equals("CARREGADOR") && (permission.toString().equals("N") || permission.toString().equals("s"))) {
                            hasCorrectPermission = true;
                        }
                        if (cat.toString().equals("GESTOR") && permission.toString().equals("N")) {
                            hasCorrectPermission = true;
                        }
                    }
                }
            }
        }
        return hasCorrectPermission;
    }

    public boolean hasPermission (String[] itemPermissions) {
        boolean hasPermission = false;
        for (Permission permission : Permission.values()) {
            for (String itemPerm : itemPermissions) {
                if (permission.toString().equals(itemPerm.toUpperCase())) {
                    hasPermission = true;
                }
            }
        }
        return hasPermission;
    }

    public void regProfessional (ProfessionalClass professional) {
        this.professionals.add(professional);
    }

    public boolean hasClient (String clientName) {
        boolean hasCli = false;
        if (this.clients.isEmpty()) {
            return false;
        } else {
            for (ClientClass client : this.clients) {
                if (clientName.equals(client.name)) {
                    hasCli = true;
                    break;
                }
            }
        }
        return hasCli;
    }

    public boolean hasProfessionalId (int professionalId) {
        boolean hasPro = false;
        if (this.professionals.isEmpty()) {
        } else {
            for (ProfessionalClass professional : this.professionals) {
                if (professionalId == professional.id) {
                    hasPro = true;
                    break;
                }
            }
        }
        return hasPro;
    }

    public boolean hasCategoryById (int professionalId) {
        boolean hasPro = false;
        if (this.professionals.isEmpty()) {
            return false;
        } else {
            for (ProfessionalClass professional : this.professionals) {
                if (professionalId == professional.id) {
                    if (professional.category.toString().equals("GESTOR")) {
                        hasPro = true;
                    }
                }
            }
        }
        return hasPro;
    }

    public void regClient (ClientClass client) {
        this.clients.add(client);
    }

    public void regItem (ItemClass item, ClientClass client) {
        this.items.add(item);
        client.addItem(item);
    }

    public void regLocal (LocalClass local) {
        this.locals.add(local);
    }

    public void regDeposit (DepositClass deposit, ClientClass client) {
        client.addDeposit(deposit);
    }

    public DepositClass createDeposit (List<Integer> itemIds, List<String> professionalsId, List<Integer> quantities, LocalClass local) {
        ArrayList<ItemClass> depositItems = new ArrayList<>();
        ArrayList<ProfessionalClass> professionalsDelivery = new ArrayList<>();
        int i = 0;

        for (String professionalId : professionalsId) {
            ProfessionalClass professional = getProfessionalData(Integer.parseInt(professionalId));
            professionalsDelivery.add(professional);

        }

        for (Integer itemId : itemIds) {
            ItemClass item = getItemData(itemId);
            item.quantityDeposit.add(quantities.get(i));
            item.quantity += quantities.get(i);
            depositItems.add(item);
            i++;
        }

        return new DepositClass(local, depositItems, professionalsDelivery);
    }

    public DeliveryClass createDelivery (List<Integer> itemsId, List<String> professionalsId, List<Integer> quantities, LocalClass local) {
        ArrayList<ItemClass> deliveryItems = new ArrayList<>();
        ArrayList<ProfessionalClass> professionalsDelivery = new ArrayList<>();
        int i = 0;
        for (Integer itemId : itemsId) {
            ItemClass item = getItemData(itemId);
            item.quantityDelivery.add(quantities.get(i));
            item.quantity = item.quantity - quantities.get(i);
            deliveryItems.add(item);
            i++;
        }

        for (String professionalId : professionalsId) {
            ProfessionalClass professional = getProfessionalData(Integer.parseInt(professionalId));
            professionalsDelivery.add(professional);
        }

        DeliveryClass delivery = new DeliveryClass(local, deliveryItems, professionalsDelivery);
        return delivery;
    }
    // get item object with Identification

    public ItemClass getItemData (int itemId) {
        for (ItemClass item : this.items) {
            if (itemId == item.id) {
                return item;
            }
        }
        return null;
    }

    public boolean hasItems (List<Integer> itemsId, int clientId) {
        boolean hasItems = false;
        int i = itemsId.size();
        int j = 0;
        ClientClass client = getClientData(clientId);
        if (this.items.isEmpty()) {
            return false;
        } else {
            for (Integer itemTempId : itemsId) {
                for (ItemClass item : client.items) {
                    if (itemTempId == item.id) {
                        j++;
                        if (i == j) {
                            hasItems = true;
                        }
                    }
                }
            }
        }
        return hasItems;
    }

    public boolean hasListProfessionalById (List<String> professionalsId) {
        boolean hasPros = false;
        ArrayList<Integer> intProfessionalsId = new ArrayList<>();
        int i = 0;
        int j = 0;
        for (String s: professionalsId){
            intProfessionalsId.add(Integer.valueOf(s));
            i++;
        }
        Collections.sort(intProfessionalsId);
        if (this.professionals.isEmpty()) {
            return false;
        } else {
            for (Integer professionalTempId : intProfessionalsId) {
                for (ProfessionalClass professional : this.professionals) {
                    if (professionalTempId == professional.id) {
                        j++;
                        if (j == i) {
                            hasPros = true;
                        }
                    }
                }
            }
        }
        return hasPros;
    }

    public boolean hasDriverValidation (List<String> professionalsId) {
        boolean isValid = false;
        ArrayList<Integer> intProfessionalsId = new ArrayList<>();
        for (String s: professionalsId){
            intProfessionalsId.add(Integer.valueOf(s));
        }
        Collections.sort(intProfessionalsId);
        if (this.professionals.isEmpty()) {
            return false;
        } else {
            for (Integer professionalTempId : intProfessionalsId) {
                for (ProfessionalClass professional : this.professionals) {
                    if (professionalTempId == professional.id) {
                        if (professional.category.toString().equals("CONDUTOR")) {
                            isValid = true;
                        }
                    } else {
                        isValid = false;
                    }
                }
            }
        }
        return isValid;
    }

    public boolean hasLoaderValidation (List<String> professionalsId, List<Integer> itemsId) {
        boolean isValid = false;
        if (this.professionals.isEmpty() || this.items.isEmpty()) {
            return false;
        } else {
            for (String professionalTempId : professionalsId) {
                for (ProfessionalClass professional : this.professionals) {
                    if (Integer.parseInt(professionalTempId) == professional.id) {
                        if (professional.category.toString().equals("CARREGADOR")) {
                            for (Integer itemIdTemp : itemsId) {
                                for (ItemClass item : this.items) {
                                    if (itemIdTemp == item.id) {
                                        for (String permission : item.permission) {
                                            if (permission.equals("N") || permission.equals("S")) {
                                                isValid = true;
                                            } else {
                                                isValid = false;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return isValid;
    }

    public boolean hasQuantity (List<Integer> itemsId, List<Integer> quantities) {
        boolean hasItems = false;
        if (this.items.isEmpty()) {
            return false;
        } else {
            for (Integer itemTempId : itemsId) {
                for (ItemClass item : this.items) {
                    if (itemTempId == item.id) {
                        for (int i = 0; i < quantities.size(); i++) {
                            if (item.quantity >= quantities.get(i)) {
                                hasItems = true;
                            } else {
                                hasItems = false;
                            }
                        }
                    }
                }
            }
            return hasItems;
        }
    }

    public void regDelivery (DeliveryClass delivery, ClientClass client) {
        client.addDelivery(delivery);
    }

    public ClientClass getClientData (int clientId) {
        for (ClientClass client : this.clients) {
            if (clientId == client.id) {
                return client;
            }
        }
        return null;
    }

    public boolean hasClientById (int clientId) {
        boolean hasCli = false;
        if (this.clients.isEmpty()) {
            return false;
        } else {
            for (ClientClass client : this.clients) {
                if (clientId == client.id) {
                    hasCli = true;
                }
            }
        }
        return hasCli;
    }

    public boolean hasLocal (String localName) {
        boolean hasLocal = false;
        if (this.locals.isEmpty()) {
        } else {
            for (LocalClass local : this.locals) {
                if (localName.equals(local.name)) {
                    hasLocal = true;
                    break;
                }
            }
        }
        return hasLocal;
    }

    public LocalClass getLocalData (int localId) {
        for (LocalClass local : this.locals) {
            if (localId == local.id) {
                return local;
            }
        }
        return null;
    }

    public ProfessionalClass getProfessionalData (int professionalId) {
        for (ProfessionalClass professional : this.professionals) {
            if (professionalId == professional.id) {
                return professional;
            }
        }
        return null;
    }

    public boolean hasItemById (int itemId, int clientId) {
        boolean hasItem = false;
        ClientClass client = getClientData(clientId);
        if (this.items.isEmpty()) {
            return false;
        } else {
            for (ItemClass item : client.items) {
                if (itemId == item.id) {
                    hasItem = true;
                }
            }
        }
        return hasItem;
    }

    public boolean hasProfessionalById (int professionalId) {
        boolean hasPro = false;
        if (this.professionals.isEmpty()) {
            return false;
        } else {
            for (ProfessionalClass professional : this.professionals) {
                if (professionalId == professional.id) {
                    hasPro = true;
                }
            }
        }
        return hasPro;
    }

    public boolean hasLocalById (int localId) {
        boolean hasLocal = false;
        if (this.locals.isEmpty()) {
            return false;
        } else {
            for (LocalClass local : this.locals) {
                if (localId == local.id) {
                    hasLocal = true;
                }
            }
        }
        return hasLocal;
    }

    public boolean hasDeliveryId (int deliveryId, int clientId) {
        ClientClass client = getClientData(clientId);
        boolean hasDeli = false;
        for (DeliveryClass delivery : client.deliveries) {
            if (delivery.id == deliveryId) {
                hasDeli = true;
            }
        }
        return hasDeli;
    }

    public void printClientItemData (ClientClass client) {
        for (ItemClass item : client.items) {
            System.out.println("  " + item.id + " (" + item.quantity + ") " + Arrays.toString(item.permission) + " " + item.name);
        }
    }

    public void printClientDepositData (ClientClass client) {
        for (DepositClass deposit : client.deposits) {
            System.out.println("  " + deposit.id + " (" + deposit.local.name + ")");
        }
    }

    public void printClientDeliveryData (ClientClass client) {
        for (DeliveryClass delivery : client.deliveries) {
            System.out.println("  " + delivery.id + " (" + delivery.local.name + ")");
        }
    }

    public void printItemDepositData (ItemClass item, int clientId) {
        for (ItemClass iterItem : getClientData(clientId).items) {
            if (iterItem.id == item.id) {
                for (DepositClass deposit : getClientData(clientId).deposits) {
                    for (ItemClass iterItemDep : deposit.items) {
                        if (iterItemDep.id == iterItem.id) {
                            System.out.println("  " + deposit.id + " " + iterItemDep.quantityDeposit);
                        }
                    }
                }
            }
        }
    }

    public void printItemDeliveryData (ItemClass item, int clientId) {
        for (ItemClass iterItem : getClientData(clientId).items) {
            if (iterItem.id == item.id) {
                for (DeliveryClass delivery : getClientData(clientId).deliveries) {
                    for (ItemClass iterItemDeli : delivery.items) {
                        if (iterItemDeli.id == iterItem.id) {
                            System.out.println("  " + delivery.id + " " + iterItemDeli.quantityDelivery);
                        }
                    }
                }
            }
        }
    }

    public void printProDeliveryData (ProfessionalClass pro) {
        for (ClientClass client : this.clients) {
            for (DeliveryClass delivery : client.deliveries) {
                for (ProfessionalClass professional : delivery.professionals) {
                    if (professional.id == pro.id) {
                        System.out.println("  " + client.id + " " + delivery.id + " (" + delivery.local.name + ") " + client.name);
                    }
                }
            }
        }
    }

    public void printProDepositData (ProfessionalClass pro) {
        for (ClientClass client : this.clients) {
            for (DepositClass deposit : client.deposits) {
                for (ProfessionalClass professional : deposit.professionals) {
                    if (professional.id == pro.id) {
                        System.out.println("  " + client.id + " " + deposit.id + " (" + deposit.local.name + ") " + client.name);
                    }
                }
            }
        }
    }

    public void printDeliveryData (int deliveryId, int clientId) {
        ClientClass client = getClientData(clientId);
        for (DeliveryClass delivery : client.deliveries) {
            if (delivery.id == deliveryId) {
                System.out.println(delivery.local.name);
                for (ProfessionalClass professional : delivery.professionals) {
                    if (professional.category.toString().equals("CONDUTOR")){
                        System.out.println(professional.permission.toString() + " " + professional.name);
                    } else if (professional.category.toString().equals("CARREGADOR")){
                        System.out.println(professional.permission.toString() + " " + professional.name);
                    }
                }
                for (ItemClass item : delivery.items) {
                    System.out.println(item.id + " " + item.quantityDelivery + " " + item.name);
                }
                System.out.println();
            }

        }
    }

    public ItemClass getItemDataClient (int itemId, int clientId) {
        ClientClass client= getClientData(clientId);
        for (ItemClass item : client.items) {
            if (itemId == item.id) {
                return item;
            }
        }
        return null;
    }
}
