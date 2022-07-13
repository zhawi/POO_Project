package ual.poo.TransportationCompany;

import java.util.List;

public interface TransportationUnitInterface {

    boolean hasProfessional (String professionalName, String professionalCategory);

    boolean hasCategory (String professionalCategory);

    boolean hasCorrectPermission (String professionalPermission, String professionalCategory);

    boolean hasPermission (String[] itemPermissions);

    void regProfessional (ProfessionalClass professional);

    boolean hasClient (String clientName);

    boolean hasProfessionalId (int professionalId);

    boolean hasCategoryById (int professionalId);

    void regClient (ClientClass client);

    void regItem (ItemClass item, ClientClass client);

    void regLocal (LocalClass local);

    void regDeposit (DepositClass deposit, ClientClass client);

    DepositClass createDeposit (List<Integer> itemIds, List<String> professionalsId, List<Integer> quantities, LocalClass local);

    DeliveryClass createDelivery (List<Integer> itemsId, List<String> professionalsId, List<Integer> quantities, LocalClass local);


    ItemClass getItemData (int itemId);

    boolean hasItems (List<Integer> itemsId, int clientId);

    boolean hasListProfessionalById (List<String> professionalsId);

    boolean hasDriverValidation (List<String> professionalsId);

    boolean hasLoaderValidation (List<String> professionalsId, List<Integer> itemsId);

    boolean hasQuantity (List<Integer> itemsId, List<Integer> quantities);

    void regDelivery (DeliveryClass delivery, ClientClass client);

    ClientClass getClientData (int clientId);

    boolean hasClientById (int clientId);

    boolean hasLocal (String localName);

    LocalClass getLocalData (int localId);

    ProfessionalClass getProfessionalData (int professionalId);

    boolean hasItemById (int itemId, int clientId);

    boolean hasProfessionalById (int professionalId);

    boolean hasLocalById (int localId);

    boolean hasDeliveryId (int deliveryId, int clientId);

    void printClientItemData (ClientClass client);

    void printClientDepositData (ClientClass client);

    void printClientDeliveryData (ClientClass client);

    void printItemDepositData (ItemClass item, int clientId);

    void printItemDeliveryData (ItemClass item, int clientId);

    void printProDeliveryData (ProfessionalClass pro);

    void printProDepositData (ProfessionalClass pro);

    void printDeliveryData (int deliveryId, int clientId);

    ItemClass getItemDataClient (int itemId, int clientId);
}
