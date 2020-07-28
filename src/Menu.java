import java.sql.*;
import java.util.*;

public class Menu {

    public static void main(String[] args) {
        //Main.query();
        // Build menu
        boolean menu = true;
        Scanner input = new Scanner(System.in);

        System.out.println("");
        System.out.println("");

        System.out.println("############################");
        System.out.println("############################");
        System.out.println("######### WELCOME ##########");
        System.out.println("############################");
        System.out.println("############################");

        // Enter primary menu
        while(menu){


            // Primary menu options
            System.out.println("");
            System.out.println("What would you like to do?");
            System.out.println("");

            System.out.println("1.) Add new accident");
            System.out.println("2.) Accident lookup");
            System.out.println("3.) Check connection");
            System.out.println("4.) Test");
            System.out.println("");


            int reply = input.nextInt(); // User menu selection

            // Menu button functions
            switch (reply) {
                case 1:
                    boolean adding = true;

                    // Enter record adding menu
                    accidents: while(adding) {


                        // Enter date
                        input.nextLine();
                        System.out.println("Enter date:");
                        String date = input.nextLine();
                        System.out.println(date + " was read");
                        System.out.println("");

                        // Enter city
                        System.out.println("Enter city:");
                        String city = input.nextLine();
                        System.out.println(city + " was read");
                        System.out.println("");

                        // Enter state
                        System.out.println("Enter state:");
                        String state = input.nextLine();
                        System.out.println(state + " was read");
                        System.out.println("");

                        // Allow user to validate input or exit
                        System.out.println("Look correct? [y/n], 'q' to exit");
                        String choice = input.next();
                        System.out.println("");

                        // Case if information incorrect, reset entry
                        if (choice.equals("n")) {
                            System.out.println("Information not added. Try again.");

                        // Case if user wants to exit application
                        } else if (choice.equals("q")){
                            System.out.println("Are you sure? Current accident information will be lost! [y/n]");
                            String sure1 = input.next();

                            // Make sure user wants to exit
                            if (sure1.equals("y")){
                                System.out.println("Exiting...");
                                System.out.println("");
                                break;
                            } else {
                                choice = "n";
                            }
                        }
                        else if (choice.equals("y")){

                            boolean addingVehicle = true;
                            while(addingVehicle){

                                // Enter
                                input.nextLine();
                                System.out.println("Enter VIN:");
                                String vin = input.nextLine();
                                System.out.println(vin + " was read");
                                System.out.println("");

                                System.out.println("Enter damages:");
                                float damages = input.nextFloat();
                                input.nextLine();
                                System.out.println(damages + " was read");
                                System.out.println("");

                                System.out.println("Enter driver ssn:");
                                String driver_ssn= input.nextLine();
                                System.out.println(driver_ssn + " was read");
                                System.out.println("");

                                System.out.println("Look correct? [y/n], 'q' to return to main menu.");
                                String choice2 = input.next();
                                System.out.println("");

                                if (choice2.equals("n")) {
                                    System.out.println("Vehicle not added. Try again.");

                                } else if (choice2.equals("q")){
                                    System.out.println("Are you sure? Record information will be lost! [y/n]");
                                    String sure = input.next();
                                    if (sure.equals("y")){
                                        System.out.println("Exiting...");
                                        System.out.println("");
                                        break accidents;
                                    } else {
                                        choice2 = "n";
                                    }
                                }
                                else if (choice2.equals("y")){

                                    queries.addVehicle(vin, damages, driver_ssn);
                                    System.out.println("Vehicle added!");
                                    System.out.println("Add another vehicle? [y/n]");
                                    String cont2 = input.next();

                                    // Check user input
                                    if (cont2.equals("n")){
                                        queries.addAccident(date, city, state);
                                        System.out.println("Record added!");

                                        break;
                                    } else if (!cont2.equals("y")){

                                        // Prompt user again for correct input
                                        System.out.println("Invalid option enter 'n' to exit or 'y' to continue.");
                                        choice2 = input.nextLine();
                                    }

                                }}


                            System.out.println("Add another accident? [y/n]");
                            String cont = input.next();

                            // Check user input
                            if (cont.equals("n")){
                                System.out.println("Update successful!");
                                break;
                            } else if (!cont.equals("y")){

                                // Prompt user again for correct input
                                System.out.println("Invalid option enter 'n' to exit or 'y' to continue.");
                                choice = input.nextLine();
                            }
                        } else {
                            System.out.println("Invalid option type 'a' to add record, 'c' to cancel");
                            choice = input.nextLine();
                        }
                    }
                    break;
                case 2:
                    boolean querying = true;
                    outer: while (querying){

                        System.out.println("");
                        System.out.println("How would you like to search?");
                        System.out.println("1.) Look up by AID");
                        System.out.println("2.) Look up by criteria");
                        System.out.println("Press '0' to exit.");

                        int reply2 = input.nextInt();
                        switch (reply2){
                            case 1:
                                input.nextLine();
                                System.out.println("Enter Accident ID:");
                                int aid = input.nextInt();
                                queries.findAccidentbyID(aid);
                                break;

                            case 2:

                                // initialize default values
                                String dateRangeStart = "0000-00-00";
                                String dateRangeEnd = "9999-99-99";
                                float avgDamageMin = 0;
                                float avgDamageMax = 99999999;
                                float totalDamageMin = 0;
                                float totalDamageMax = 99999999;


                                input.nextLine();
                                System.out.println("Specify date range [Type 'none' to skip]");
                                System.out.println("Start date:");

                                dateRangeStart = input.nextLine();

                                System.out.println("Starting at: " + dateRangeStart);


                                if (!dateRangeStart.equals("none")){

                                    System.out.println("End date:");
                                    dateRangeEnd = input.nextLine();
                                    System.out.println("Ending at: " + dateRangeEnd);

                                } else {
                                dateRangeStart = "0000-00-00";// reset
                                }
                                System.out.println("Specify average damage range [Enter -1 to skip]");

                                System.out.println("Minimum value:");
                                avgDamageMin = input.nextFloat();

                                if (!(avgDamageMin == -1)){
                                    System.out.println("Maximum value:");
                                    avgDamageMax = input.nextFloat();
                                } else {

                                    avgDamageMin = 0; //reset
                                }
                                System.out.println("Specify total damage range [Enter -1 to skip]");


                                System.out.println("Minimum value:");
                                totalDamageMin = input.nextFloat();

                                if (!(totalDamageMin ==-1)){

                                    System.out.println("Maximum value:");
                                    totalDamageMax = input.nextFloat();
                                } else {
                                    totalDamageMin = 0; // reset
                                }


                                // call
                                queries.findAccidentbyCriteria(dateRangeStart, dateRangeEnd, avgDamageMin, avgDamageMax, totalDamageMin, totalDamageMax);

                                break;
                            case 0:
                                break outer;
                        }

                    }
                    break;

                case 3:
                    queries.checkConnection();
                    break;

                case 4:
                    break;
            }
        }


    }
}