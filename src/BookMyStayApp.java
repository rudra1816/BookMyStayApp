class RoomInventory {

    java.util.HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new java.util.HashMap<>();

        inventory.put("Single Room", 10);
        inventory.put("Double Room", 7);
        inventory.put("Suite Room", 3);
    }

    int getAvailability(String roomType) {
        return inventory.get(roomType);
    }

    void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }

    void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " -> " + inventory.get(roomType));
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Book My Stay App v3.1");
        System.out.println("---------------------------");

        RoomInventory inventory = new RoomInventory();

        inventory.displayInventory();

        System.out.println("\nChecking availability of Double Room:");
        System.out.println("Available: " + inventory.getAvailability("Double Room"));

        System.out.println("\nUpdating availability of Double Room...");

        inventory.updateAvailability("Double Room", 5);

        System.out.println("\nInventory After Update:");
        inventory.displayInventory();
    }
}