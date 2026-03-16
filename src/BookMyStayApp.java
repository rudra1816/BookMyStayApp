class Reservation {

    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class BookingRequestQueue {

    java.util.Queue<Reservation> queue;

    BookingRequestQueue() {
        queue = new java.util.LinkedList<>();
    }

    void addRequest(Reservation r) {
        queue.add(r);
    }

    Reservation getNextRequest() {
        return queue.poll();
    }

    boolean hasRequests() {
        return !queue.isEmpty();
    }
}

class RoomInventory {

    java.util.HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new java.util.HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    int getAvailability(String roomType) {
        return inventory.get(roomType);
    }

    void decreaseAvailability(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

class BookingService {

    java.util.HashMap<String, java.util.Set<String>> allocatedRooms;
    int roomCounter = 1;

    BookingService() {
        allocatedRooms = new java.util.HashMap<>();
    }

    void processRequests(BookingRequestQueue queue, RoomInventory inventory) {

        while (queue.hasRequests()) {

            Reservation request = queue.getNextRequest();
            String roomType = request.roomType;

            if (inventory.getAvailability(roomType) > 0) {

                String roomId = roomType.replace(" ", "") + "-" + roomCounter++;

                java.util.Set<String> rooms = allocatedRooms.get(roomType);

                if (rooms == null) {
                    rooms = new java.util.HashSet<>();
                    allocatedRooms.put(roomType, rooms);
                }

                rooms.add(roomId);

                inventory.decreaseAvailability(roomType);

                System.out.println("Reservation Confirmed");
                System.out.println("Guest: " + request.guestName);
                System.out.println("Room Type: " + roomType);
                System.out.println("Room ID: " + roomId);
                System.out.println();
            }
            else {
                System.out.println("Reservation Failed for " + request.guestName +
                        " (No rooms available)");
            }
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Book My Stay App v6.1");
        System.out.println("-----------------------------");

        BookingRequestQueue queue = new BookingRequestQueue();

        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Double Room"));
        queue.addRequest(new Reservation("Charlie", "Suite Room"));
        queue.addRequest(new Reservation("David", "Suite Room"));

        RoomInventory inventory = new RoomInventory();

        BookingService bookingService = new BookingService();

        bookingService.processRequests(queue, inventory);
    }
}