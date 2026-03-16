abstract class Room {

    String roomType;
    int beds;
    int size;
    double price;

    Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq ft");
        System.out.println("Price: $" + price);
    }
}

class SingleRoom extends Room {

    SingleRoom() {
        super("Single Room", 1, 200, 1000);
    }
}

class DoubleRoom extends Room {

    DoubleRoom() {
        super("Double Room", 2, 350, 1800);
    }
}

class SuiteRoom extends Room {

    SuiteRoom() {
        super("Suite Room", 3, 500, 3000);
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Book My Stay App v2.1");
        System.out.println("-----------------------------");

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleAvailability = 10;
        int doubleAvailability = 7;
        int suiteAvailability = 3;

        System.out.println("\nSingle Room Details:");
        single.displayRoomDetails();
        System.out.println("Available Rooms: " + singleAvailability);

        System.out.println("\nDouble Room Details:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + doubleAvailability);

        System.out.println("\nSuite Room Details:");
        suite.displayRoomDetails();
        System.out.println("Available Rooms: " + suiteAvailability);
    }
}