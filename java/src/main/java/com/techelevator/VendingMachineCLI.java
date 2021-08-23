package com.techelevator;

import com.sun.source.tree.TryTree;
import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class VendingMachineCLI {

    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT};

    private Menu menu;
    private Scanner userInput = new Scanner(System.in);
    private PrintWriter out;
    private Transaction transaction = new Transaction();

    public static List<Inventory> informationForProducts = new ArrayList<Inventory>(); //will want this in main

    public VendingMachineCLI(Menu menu) {
        this.menu = menu;
    }

    public void run() {
        initializingStock();
        while (true) {
            String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

            if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
                // display vending machine items

                for (Inventory info : informationForProducts) {
                    System.out.println(info.getSlotLocation() + ": " + info.getProduct() + " | $" + info.getPrice()
                            + " | Quantity: " + info.getQuantityATS());
                }
            } else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
                // do purchase
                this.subMenu2();
            } else {
                //EXIT
                System.out.println("Goodbye"); // (YOU CAN'T HANDLE THE VENDING MACHINE!)
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI cli = new VendingMachineCLI(menu);
        createLog();
        cli.run();
    }

    public static void initializingStock() {
        //Locate File & Create Object
        String currentDirectory = System.getProperty("user.dir");
        File fileToRead = new File(currentDirectory + "/vendingmachine.csv");

        try (Scanner inputFileScanner = new Scanner(fileToRead)) {
            while (inputFileScanner.hasNextLine()) {
                String line = inputFileScanner.nextLine();

                //informationForProducts.add(line);
                String arrayOfSplitStrings[] = line.split("\\|");

                String slotLocation = arrayOfSplitStrings[0];
                String product = arrayOfSplitStrings[1];
                BigDecimal price = new BigDecimal(arrayOfSplitStrings[2]);
                String type = arrayOfSplitStrings[3];
                if (type.equals("Chip")) {
                    Inventory item = new Chip(slotLocation, product, price, type);
                    informationForProducts.add(item);
                } else if (type.equals("Candy")) {
                    Inventory item = new Candy(slotLocation, product, price, type);
                    informationForProducts.add(item);
                } else if (type.equals("Drink")) {
                    Inventory item = new Drink(slotLocation, product, price, type);
                    informationForProducts.add(item);
                } else if (type.equals("Gum")) {
                    Inventory item = new Gum(slotLocation, product, price, type);
                    informationForProducts.add(item);
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public void subMenu2() {
        createLog();
        while (true) {
            System.out.println("\n" + "Purchase menu options");

            Menu menu2SubMenu2 = new Menu(System.in, System.out);
            String subMenu2Option1 = "Feed Money";
            String subMenu2Option2 = "Select Product";
            String subMenu2Option3 = "Finish Transaction";
            String[] subMenu2Options = {subMenu2Option1, subMenu2Option2, subMenu2Option3};
            String subMenu2Choices = (String) menu2SubMenu2.getChoiceFromOptions(subMenu2Options);

            if (subMenu2Choices.equals(subMenu2Option1)) {
                //do stuff for option 1
                System.out.println("Please insert money in whole dollar amounts (accepts 1, 5, 10)");
                int moneyIn = Integer.parseInt(userInput.nextLine());
                BigDecimal fedMoney = new BigDecimal(moneyIn);
                transaction.addBalance(fedMoney);
                //money is being fed in -> transaction.getBalance() is reading Transaction
                System.out.println("You're balance is: $" + transaction.getBalance());
                String message = " FEED MONEY: " + "\\$" + fedMoney + " \\$" + transaction.getBalance();
                writeToFile(message);
            } else if (subMenu2Choices.equals(subMenu2Option2)) {
                //do stuff for option 2
                for (Inventory info : informationForProducts) {
                    System.out.println(info.getSlotLocation() + ": " + info.getProduct() + " | $" + info.getPrice()
                            + " | Quantity: " + info.getQuantityATS());
                }
                System.out.println("\n" + "Please select the product code you would like to purchase (Machine is case sensitive)");

                String userSelection = userInput.nextLine();

                boolean atsLessThan0 = false;
                boolean isFound = false;
                for (Inventory item : informationForProducts) {
                    if (item.getSlotLocation().equals(userSelection)) {
                        isFound = true;
                        //check if inventory for user selection is = 0
                        if (item.getQuantityATS() == 0) {
                            atsLessThan0 = true;
                            break;
                        }
                        //math (decrement the quantity for purchase)
                        if (transaction.getBalance().compareTo(item.getPrice()) > 0) {
                            //check user funds
                            item.lowerQuantity();

                            System.out.println("Dispensing: " + item.getProduct() + " | " + item.getSound());
                            // not calculating
                            System.out.println("Current Balance: $" + transaction.lowerBalance(item.getPrice()));
                            String message = " " + item.getProduct() + " " + item.getSlotLocation() + " \\$" +
                                    transaction.getBalance() + " \\$" + transaction.lowerBalance(item.getPrice());
                            writeToFile(message);

                            break;
                        } else {
                            System.out.println("Please insert money ");
                        }
                    }
                }
                if (atsLessThan0) {
                    System.out.println("SOLD OUT : Please select different item!");
                }
                if (!isFound) {
                    System.out.println("Product code does not exist, returning to menu");
                }
            } else if (subMenu2Choices.equals(subMenu2Option3)) {
                System.out.println("Remaining Balance: $" + transaction.getBalance());
                String message = " GIVE CHANGE: \\$" + transaction.getBalance() + " \\$0.00";
                writeToFile(message);
                Map<String, Integer> transactionChange = transaction.makeChange();
                System.out.println("Your change is: ");
                System.out.println("Quarters: " + transactionChange.get("Quarters") + " | Dimes: "
                        + transactionChange.get("Dimes") + " | Nickels: " + transactionChange.get("Nickels"));
                break;
            }
        }
    }

    //Create New File
    public static void createLog() {
        String currentDirectory = System.getProperty("user.dir");
        File salesLog = new File(currentDirectory + "/log.txt");
        try (PrintWriter pw = new PrintWriter(salesLog)) {
            pw.println("Vending Machine Transaction Log");
        } catch (Exception ex) {
            System.out.println("Error");
        }
    }
    public static void  writeToFile(String message){
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
        LocalDateTime currentDateTime = LocalDateTime.now();
        String dateTimeFormatted = currentDateTime.format(dateFormat);

        Path path = Paths.get("log.txt");
        File fileToWrite = new File(path.toAbsolutePath().toString());
        try (FileWriter fileWriter = new FileWriter(fileToWrite, true);
                PrintWriter pw = new PrintWriter(fileWriter)) {
            pw.println(dateTimeFormatted + message);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}




