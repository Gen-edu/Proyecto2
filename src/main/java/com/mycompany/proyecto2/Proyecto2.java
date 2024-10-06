
package com.mycompany.proyecto2;

import MapeoBD.SecurityItem;
import MapeoBD.SecurityItemJpaController;
import MapeoBD.Stock;
import MapeoBD.StockJpaController;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Proyecto2 {
    public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_Proyecto2_jar_1.0-SNAPSHOTPU");

        
        SecurityItemJpaController securityController = new SecurityItemJpaController();
        StockJpaController stockController = new StockJpaController();
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n Menú Inventario");
            System.out.println("1. Crear Security Item");
            System.out.println("2. Leer Security Items");
            System.out.println("3. Actualizar Security Item");
            System.out.println("4. Eliminar Security Item");
            System.out.println("5. Crear Stock");
            System.out.println("6. Leer Stock");
            System.out.println("7. Actualizar Stock");
            System.out.println("8. Eliminar Stock");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            option = scanner.nextInt();
            scanner.nextLine();  

            switch (option) {
                case 1:
                    // Crear Security Item
                    System.out.println("Ingrese el nombre del Security Item:");
                    String securityItemName = scanner.nextLine();
                    System.out.println("Ingrese la descripción del Security Item:");
                    String description = scanner.nextLine();
                    System.out.println("Ingrese el precio del Security Item:");
                    Double price = scanner.nextDouble();
                    System.out.println("¿Aplica instalación? (true/false):");
                    Boolean appliesInstalation = scanner.nextBoolean();

                    SecurityItem newItem = new SecurityItem();
                    newItem.setSecurityItemName(securityItemName);
                    newItem.setDescription(description);
                    newItem.setPrice(price);
                    newItem.setAppliesInstalation(appliesInstalation);

                    securityController.create(newItem);
                    System.out.println("¡Security Item creado!");
                    break;

                case 2:
                    // Leer Security Items
                    List<SecurityItem> items = securityController.findSecurityItemEntities();
                    System.out.println("\n Listado de Security Items ");
                    for (SecurityItem item : items) {
                        System.out.println(item);
                    }
                    break;

                case 3:
                    // Actualizar Security Item
                    System.out.println("Ingrese el ID del Security Item a actualizar:");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();  // Consumir nueva línea
                    SecurityItem updateItem = securityController.findSecurityItem(updateId);

                    if (updateItem != null) {
                        System.out.println("Ingrese el nuevo nombre del Security Item:");
                        updateItem.setSecurityItemName(scanner.nextLine());
                        System.out.println("Ingrese la nueva descripción:");
                        updateItem.setDescription(scanner.nextLine());
                        System.out.println("Ingrese el nuevo precio:");
                        updateItem.setPrice(scanner.nextDouble());
                        System.out.println("¿Aplica instalación? (true/false):");
                        updateItem.setAppliesInstalation(scanner.nextBoolean());

                        try {
                            securityController.edit(updateItem);
                            System.out.println("¡Security Item actualizado!");
                        } catch (Exception e) {
                            System.out.println("Error al actualizar el Security Item.");
                        }
                    } else {
                        System.out.println("Security Item no encontrado.");
                    }
                    break;

                case 4:
                    // Eliminar Security Item
                    System.out.println("Ingrese el ID del Security Item a eliminar:");
                    int deleteId = scanner.nextInt();

                    try {
                        securityController.destroy(deleteId);
                        System.out.println("¡Security Item eliminado!");
                    } catch (Exception e) {
                        System.out.println("Error al eliminar el Security Item.");
                    }
                    break;

                case 5:
                    // Crear Stock
                    System.out.println("Ingrese el ID del Security Item para agregar stock:");
                    int itemId = scanner.nextInt();
                    scanner.nextLine();  // Consumir nueva línea
                    SecurityItem stockItem = securityController.findSecurityItem(itemId);

                    if (stockItem != null) {
                        System.out.println("Ingrese la cantidad de stock:");
                        int quantity = scanner.nextInt();

                        Stock newStock = new Stock();
                        newStock.setQuantity(quantity);
                        newStock.setSecurityItemId(stockItem);

                        stockController.create(newStock);
                        System.out.println("¡Stock creado para el Security Item!");
                    } else {
                        System.out.println("Security Item no encontrado.");
                    }
                    break;

                case 6:
                    
                    List<Stock> stocks = stockController.findStockEntities();
                    System.out.println("\n*** Listado de Stocks ***");
                    for (Stock stock : stocks) {
                        System.out.println(stock);
                    }
                    break;

                case 7:
                    
                    System.out.println("Ingrese el ID del Stock a actualizar:");
                    int stockUpdateId = scanner.nextInt();
                    Stock updateStock = stockController.findStock(stockUpdateId);

                    if (updateStock != null) {
                        System.out.println("Ingrese la nueva cantidad de stock:");
                        updateStock.setQuantity(scanner.nextInt());

                        try {
                            stockController.edit(updateStock);
                            System.out.println("¡Stock actualizado!");
                        } catch (Exception e) {
                            System.out.println("Error al actualizar el stock.");
                        }
                    } else {
                        System.out.println("Stock no encontrado.");
                    }
                    break;

                case 8:
                     
                    System.out.println("Ingrese el ID del Stock a eliminar:");
                    int stockDeleteId = scanner.nextInt();

                    try {
                        stockController.destroy(stockDeleteId);
                        System.out.println("¡Stock eliminado!");
                    } catch (Exception e) {
                        System.out.println("Error al eliminar el stock.");
                    }
                    break;

                case 0:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (option != 0);

        
        emf.close();
    }
}
