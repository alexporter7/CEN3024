//Author Name:Alex Porter
//Date: 8/27/2020
//Program Name: chp1_drone
//Purpose: Create a CLI based drone application to track movements

import java.io.IOException;
import java.util.Scanner;

class chp1_drone {

    //Position Variables
    static int posX = 0;
    static int posY = 0;
    static int posZ = 0;

    //Directions (used to make reading main method easier)
    static final int UP = 1;
    static final int DOWN = 2;
    static final int LEFT = 3;
    static final int RIGHT = 4;
    static final int FORWARD = 5;
    static final int BACK = 6;

    public static void main(final String args[]) throws IOException {
        
        //init scanner
        final Scanner sc = new Scanner(System.in); 

        //Menu Loop
        while(true) {

            printMenu();
            System.out.print("Option> ");
            final String response = sc.nextLine();
            switch(response) {
                case "1": moveDrone(UP); break;
                case "2": moveDrone(DOWN); break;
                case "3": moveDrone(FORWARD); break;
                case "4": moveDrone(BACK); break;
                case "5": moveDrone(LEFT); break;
                case "6": moveDrone(RIGHT); break;
                case "7": printStats(); break;
                case "8": break;
                default:
                    System.out.println("The response [" + response + "] was invalid.");

            }

            //exit if response is 8
            if (response.equals("8")) {
                break;
            }

        }

        sc.close();

    }

    public static void printStats() {

        //Create the array of stats
        final String statArray[] = {"======================", 
                                "Drone Position:", 
                                "X: " + posX + " Y: " + posY + " Z: " + posZ,
                                "======================"};

        //For each loop to print out array
        for(final String line : statArray) {
            System.out.println(line);
        }

    }

    public static void printMenu() {

        //Create the array of menu
        final String menuArray[] = {"1 - Move Up", "2 - Move Down", "3 - Move Forward", 
                                "4 - Move Backward", "5 - Turn Left", "6 - Turn Right", 
                                "7 - Display Position", "8 - Exit Navigation"};

        //For Each loop to print out array
        for(final String line : menuArray) {
            System.out.println(line);
        }

    }

    public static void moveDrone(final int direction) {

        switch(direction) {
            case 1: posY++; break;
            case 2: posY--; break;
            case 3: posZ++; break;
            case 4: posZ--; break;
            case 5: posX++; break;
            case 6: posX--; break;
        }

    }

}