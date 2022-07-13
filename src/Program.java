import ual.poo.TransportationCompany.*;

import java.io.*;
import java.util.*;

public class Program {
    public static void main(String[] args) throws FileNotFoundException {
        TransportationUnit transportationUnit = new TransportationUnit("Transportation Company");
        File file = new File("./scenarios/4.in");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (input.isBlank()) {
                scanner.close();
                System.exit(0);
            }
            String[] commands = input.split(" ");
            switch (commands[0].toUpperCase()) {
                case "RF":
                    RF(transportationUnit, commands);
                    break;
                case "RC":
                    RC(transportationUnit, commands);
                    break;
                case "RI":
                    RI(transportationUnit, commands, scanner);
                    break;
                case "RL":
                    RL(transportationUnit, commands);
                    break;
                case "RD":
                    RD(transportationUnit, commands, scanner);
                    break;
                case "RE":
                    RE(transportationUnit, commands, scanner);
                    break;
                case "CC":
                    CC(transportationUnit, commands);
                    break;
                case "CI":
                    CI(transportationUnit, commands);
                    break;
                case "CE":
                    CE(transportationUnit, commands);
                    break;
                case "CF":
                    CF(transportationUnit, commands);
                    break;
                case "G":
                    save(transportationUnit, commands);
                    break;
                case "L":
                    transportationUnit = read(transportationUnit, commands);

                    break;
                default:
                    System.out.println("Instrução Inválida.");
                    break;
            }
        }
    }

    //metodo de criação de um funcionario
    public static void RF (TransportationUnit transportationUnit, String[] commands) {
        String professionalCategory = commands[1];
        String professionalPermission = commands[2];
        StringBuilder professionalNameT = new StringBuilder();
        for (int i=3; i < commands.length; i++){
            professionalNameT.append(commands[i]);
            professionalNameT.append(" ");
        }
        String professionalName = professionalNameT.toString();
        if (!transportationUnit.hasProfessional(professionalName, professionalCategory)) {
            if (transportationUnit.hasCategory(professionalCategory)) {
                if (transportationUnit.hasCorrectPermission(professionalPermission, professionalCategory)) {
                    transportationUnit.professionalId ++;
                    ProfessionalClass professional = new ProfessionalClass(professionalName, professionalPermission.toUpperCase(), professionalCategory.toUpperCase(), transportationUnit.professionalId);
                    transportationUnit.regProfessional(professional);
                    System.out.println("Funcionário registado com o identificador " + professional.id);
                }   else {
                    System.out.println("permissão inexistente.");
                }
            } else {
                System.out.println("Categoria inexistente.");
            }
        } else {
            System.out.println("Funcionário existente.");
        }
    }

    //metodo De criação de um cliente
    private static void RC (TransportationUnit transportationUnit, String[] commands) {
        int professionalId = Integer.parseInt(commands[1]);
        StringBuilder clientNameT = new StringBuilder();
        for (int i=2; i < commands.length; i++){
            clientNameT.append(commands[i]);
            clientNameT.append(" ");
        }
        String clientName = clientNameT.toString();
        if(!transportationUnit.hasClient(clientName)) {
            if(transportationUnit.hasProfessionalId(professionalId)) {
                if(transportationUnit.hasCategoryById(professionalId)) {
                    transportationUnit.clientId++;
                    ClientClass client = new ClientClass(clientName, transportationUnit.clientId, professionalId);
                    transportationUnit.regClient(client);
                    System.out.println("Cliente registado com o identificador " + client.id);
                } else {
                    System.out.println("Funcionário incorreto.");
                }
            } else {
                System.out.println("Funcionário inexistente.");
            }
        } else {
            System.out.println("Cliente existente.");
        }

    }

    //Metodo de criação de novos Items para cada cliente
    //PROBLEMAS EM VERIFICAR SE A STRING PERMISSIONS NÃO TEM NADA, E POSTERIORMENTE FAZER O TESTE
    private static void RI(TransportationUnit transportationUnit, String[] commands, Scanner scanner) {
        int clientId = Integer.parseInt(commands[1]);
        StringBuilder itemNameT = new StringBuilder();
        for (int i=2; i < commands.length; i++){
            itemNameT.append(commands[i]);
            itemNameT.append(" ");
        }
        String itemName = itemNameT.toString();
        String[] permissions = scanner.nextLine().split(",");
        int itemQuantity = 0;

        if (transportationUnit.hasClientById(clientId)) {
            if(transportationUnit.hasPermission(permissions)) {
                ClientClass client = transportationUnit.getClientData(clientId);
                client.updateItem();
                ItemClass item = new ItemClass(itemName, itemQuantity, permissions, client.itemId);
                transportationUnit.regItem(item, client);
                System.out.println("Item registado para o cliente " + client.id + " com o identificador " + item.id);
            } else {
                System.out.println("Permissão inválida.");
            }
        } else {
            System.out.println("Cliente inexistente.");
        }
    }

    //Metodo para registar locais de possiveis entregas / depositos
    private static void RL (TransportationUnit transportationUnit, String[] commands){
        String localName = commands[1];
        if (!transportationUnit.hasLocal(localName)) {
            transportationUnit.localId ++;
            LocalClass local = new LocalClass(transportationUnit.localId, localName);
            transportationUnit.regLocal(local);
            System.out.println("Local registado com o identificador " + local.id);
        } else {
            System.out.println("local existente.");
        }
    }

    //Metodo para Registar novos depositos de items
    //Recebe varios valores e regista um novo deposito para um cliente referido
    private static void RD (TransportationUnit transportationUnit, String[] commands, Scanner scanner) {
        int clientId = Integer.parseInt(commands[1]);
        int localId = Integer.parseInt(commands[2]);
        String[] parameters1 = scanner.nextLine().split(" ");
        List<String> professionalsId =  new ArrayList<>(Arrays.asList(parameters1));
        List<Integer> itemsId = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String test = scanner.nextLine();
            if (test.isBlank()) {
                break;
            } else {
                String[] parameters2 = test.split(" ");
                itemsId.add(Integer.parseInt(parameters2[0]));
                quantities.add(Integer.parseInt(parameters2[1]));
            }
        }
        if (transportationUnit.hasClientById(clientId)) {
            if (transportationUnit.hasLocalById(localId)) {
                if (transportationUnit.hasItems(itemsId,clientId)) {
                    if (transportationUnit.hasListProfessionalById(professionalsId)) {
                        if (transportationUnit.hasDriverValidation(professionalsId)) {
                            if (transportationUnit.hasLoaderValidation(professionalsId, itemsId)) {
                                ClientClass client = transportationUnit.getClientData(clientId);
                                LocalClass local = transportationUnit.getLocalData(localId);
                                professionalsId.add(String.valueOf(client.professionalId));
                                DepositClass deposit = transportationUnit.createDeposit(itemsId, professionalsId, quantities, local);
                                transportationUnit.regDeposit(deposit, client);
                                System.out.println("Depósito registado com o identificador " + client.depositId);
                            } else {
                                System.out.println("Carregador sem permissões.");
                            }
                        } else {
                            System.out.println("Condutor sem permissões.");
                        }
                    } else {
                        System.out.println("Funcionário inexistente.");
                    }
                } else {
                    System.out.println("Item inexistente.");
                }
            } else {
                System.out.println("Local inexistente.");
            }
        } else {
            System.out.println("Cliente inexistente.");
        }
    }


    //Metodo para Registar novas Entregas
    //Recebe varios valores e regista uma nova Entrega para um cliente referido
    private static void RE (TransportationUnit transportationUnit, String[] commands, Scanner scanner) {
        int clientId = Integer.parseInt(commands[1]);
        int localId = Integer.parseInt(commands[2]);
        String[] parameters1 = scanner.nextLine().split(" ");
        List<String> professionalsId = new ArrayList<>(Arrays.asList(parameters1));
        List<Integer> itemsId = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String test = scanner.nextLine();
            if (test.isBlank()) {
                break;
            } else {
                String[] parameters2 = test.split(" ");
                itemsId.add(Integer.parseInt(parameters2[0]));
                quantities.add(Integer.parseInt(parameters2[1]));
            }
        }
        if (transportationUnit.hasClientById(clientId)){
            if (transportationUnit.hasLocalById(localId)){
                if(transportationUnit.hasItems(itemsId, clientId)){
                    if(transportationUnit.hasQuantity(itemsId, quantities)) {
                        if (transportationUnit.hasListProfessionalById(professionalsId)) {
                            if (transportationUnit.hasDriverValidation(professionalsId)) {
                                if (transportationUnit.hasLoaderValidation(professionalsId, itemsId)) {
                                    ClientClass client = transportationUnit.getClientData(clientId);
                                    LocalClass local = transportationUnit.getLocalData(localId);
                                    professionalsId.add(String.valueOf(client.professionalId));
                                    DeliveryClass delivery = transportationUnit.createDelivery(itemsId, professionalsId, quantities, local);
                                    transportationUnit.regDelivery(delivery, client);
                                    System.out.println("Entrega registada com o identificador " + client.deliveryId);
                                } else {
                                    System.out.println("Carregador sem permissões.");
                                }
                            } else {
                                System.out.println("Condutor sem permissões.");
                            }
                        } else {
                            System.out.println("Funcionário inexistente.");
                        }
                    } else {
                        System.out.println("Quantidade insuficiente.");
                    }
                }
                else {
                    System.out.println("Item inexistente.");
                }
            } else {
                System.out.println("Local inexistente.");
            }
        } else {
            System.out.println("Cliente inexistente.");
        }
    }

    //Faz a consulta de clientes, apresenta os items associados, assim como os depositos e entregas
    private static void CC(TransportationUnit transportationUnit, String[] commands) {
        int clientId = Integer.parseInt(commands[1]);
        if(transportationUnit.hasClientById(clientId)){
            ClientClass client = transportationUnit.getClientData(clientId);
            ProfessionalClass professional = transportationUnit.getProfessionalData(client.professionalId);
            System.out.println(client.name);
            System.out.println(professional.name);
            System.out.println("Items:");
            transportationUnit.printClientItemData(client);
            System.out.println("Depósitos:");
            transportationUnit.printClientDepositData(client);
            System.out.println("Entregas:");
            transportationUnit.printClientDeliveryData(client);
        } else {
            System.out.println("Cliente Inexistente.");
        }
    }

    // Faz a consulta de items, apresenta a quantidade existente, se já foram feitos depositos ou entregas dos items referidos
    private static void CI(TransportationUnit transportationUnit, String[] commands) {
        int clientId = Integer.parseInt(commands[1]);
        int itemId = Integer.parseInt(commands[2]);
        if(transportationUnit.hasClientById(clientId)){
            if(transportationUnit.hasItemById(itemId, clientId)){
                ItemClass item = transportationUnit.getItemDataClient(itemId, clientId);
                System.out.println(item.quantity + " " + Arrays.toString(item.permission) + " " + item.name);
                System.out.println("Depósitos:");
                transportationUnit.printItemDepositData(item, clientId);
                System.out.println("Entregas:");
                transportationUnit.printItemDeliveryData(item, clientId);
            } else{
                System.out.println("Item inexistente");
            }
        } else {
            System.out.println("Cliente inexistente.");
        }
    }

    //faz a consulta das entregas, assim como passa os dados das mesmas como dos clientes
    private static void CE(TransportationUnit transportationUnit, String[] commands) {
        int clientId = Integer.parseInt(commands[1]);
        int deliveryId = Integer.parseInt(commands[2]);
        if(transportationUnit.hasClientById(clientId)){
            if(transportationUnit.hasDeliveryId(deliveryId, clientId)){
                transportationUnit.printDeliveryData(deliveryId, clientId);
            } else {
                System.out.println("Entrega inexistente");
            }
        } else {
            System.out.println("Cliente inexistente.");
        }
    }

    //faz a consulta dos dados de Funcionarios, imprimindo tambem os depositos e entregas nos quais os mesmos possam estar relacionados
    private static void CF(TransportationUnit transportationUnit, String[] commands) {
        int professionalId = Integer.parseInt(commands[1]);
        if(transportationUnit.hasProfessionalById(professionalId)){
            ProfessionalClass pro = transportationUnit.getProfessionalData(professionalId);
            System.out.println(pro.name + "\n" + pro.category + "\n" + pro.permission);
            System.out.println("Depósitos:");
            transportationUnit.printProDepositData(pro);
            System.out.println("Entregas:");
            transportationUnit.printProDeliveryData(pro);
        } else {
            System.out.println("Funcionário inexistente.");
        }
    }

    private static TransportationUnit read(TransportationUnit transportationUnit, String[] commands) {
        String read = commands[1];
        try {
            FileInputStream fi = new FileInputStream(read);
            ObjectInputStream oi = new ObjectInputStream(fi);

            TransportationUnit transportationUnitRead = (TransportationUnit) oi.readObject();
            oi.close();
            fi.close();

            System.out.println("Ficheiro Lido com sucesso.");
            return transportationUnitRead;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return transportationUnit;
    }

    private static void save(TransportationUnit transportationUnit, String[] commands) {
        String save = commands[1];
        try {
            FileOutputStream f = new FileOutputStream(save);
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(transportationUnit);
            o.close();
            f.close();

            System.out.println("Ficheiro gravado com sucesso.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
