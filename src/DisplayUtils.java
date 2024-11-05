public class DisplayUtils {
    // ANSI escape codes for colors
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String BOLD = "\u001B[1m";


    public static void printBanner(){
        System.out.println(" ___   ___   ___   _____           ____        ____                   _                         ");
        System.out.println("|_ _| |_ _| |_ _| |_   _|         |  _ \\      / ___|   __ _   _ __   | |_    ___    ___   _ __  ");
        System.out.println(" | |   | |   | |    | |    _____  | | | |    | |      / _` | | '_ \\  | __|  / _ \\  / _ \\ | '_ \\ ");
        System.out.println(" | |   | |   | |    | |   |_____| | |_| |    | |___  | (_| | | | | | | |_  |  __/ |  __/ | | | |");
        System.out.println("|___| |___| |___|   |_|           |____/      \\____|  \\__,_| |_| |_|  \\__|  \\___|  \\___| |_| |_|");
    }
    public static void clearConsole(){
        try {
            String operatingSystem = System.getProperty("os.name"); // Check the current operating system

            if(operatingSystem.contains("Windows")){
                // for windows like
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else{
                // for linux / mac ANSI excape codes
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Could not clear the console. Error: " + e.getMessage());
        }
    }

    public static void pause(){
        System.out.println("\nPress Enter to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
            // Handle exceptions if needed
            System.out.println("Error during pause: " + e.getMessage());
        }
    }

    public static void printSuccess(String message){
        System.out.println(GREEN + message + RESET);
    }

    public static void printFailure(String message){
        System.out.println(RED + message + RESET);
    }

    public static void printHeading(String title){
        String line = "******************************";
        System.out.println(line);
        System.out.println(BLUE + BOLD + title + RESET);
        System.out.println(line);
    }

    public static void printInfo(String message){
        System.out.println(message);
    }

}
