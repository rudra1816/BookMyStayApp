import java.util.*;

// Reservation class
class Reservation {
    private String reservationId;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }
}

// Inventory Service
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Deluxe", 1);
        inventory.put("Standard", 1);
    }

    public void increaseRoom(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory: " + inventory);
    }
}

// Booking History (with cancellation tracking)
class BookingHistory {
    private Map<String, Reservation> confirmedBookings;

    public BookingHistory() {
        confirmedBookings = new HashMap<>();
    }

    public void addReservation(Reservation r) {
        confirmedBookings.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String id) {
        return confirmedBookings.get(id);
    }

    public void removeReservation(String id) {
        confirmedBookings.remove(id);
    }

    public boolean exists(String id) {
        return confirmedBookings.containsKey(id);
    }
}

// Cancellation Service
class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();

    public void cancelBooking(String reservationId,
                              BookingHistory history,
                              RoomInventory inventory) {

        // Validation
        if (!history.exists(reservationId)) {
            System.out.println("Cancellation failed: Reservation not found");
            return;
        }

        // Get reservation
        Reservation r = history.getReservation(reservationId);

        // Push roomId to stack (rollback tracking)
        rollbackStack.push(r.getRoomId());

        // Restore inventory
        inventory.increaseRoom(r.getRoomType());

        // Remove booking
        history.removeReservation(reservationId);

        System.out.println("Cancellation successful for " + reservationId);
        System.out.println("Released Room ID: " + r.getRoomId());
    }

    public void showRollbackStack() {
        System.out.println("Rollback Stack: " + rollbackStack);
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();
        CancellationService cancelService = new CancellationService();

        // Simulating confirmed bookings (from UC6)
        history.addReservation(new Reservation("RES101", "Deluxe", "D1"));
        history.addReservation(new Reservation("RES102", "Standard", "S1"));

        System.out.println("Before Cancellation:");
        inventory.displayInventory();

        // Cancel a booking
        cancelService.cancelBooking("RES101", history, inventory);

        System.out.println("\nAfter Cancellation:");
        inventory.displayInventory();

        // Try invalid cancellation
        cancelService.cancelBooking("RES999", history, inventory);

        // Show rollback stack
        cancelService.showRollbackStack();
    }
}