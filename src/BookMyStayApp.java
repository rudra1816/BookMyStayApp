import java.io.*;
import java.util.*;

// Reservation class (Serializable)
class Reservation implements Serializable {
    private String reservationId;
    private String roomType;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }
}

// System State (Inventory + Bookings)
class SystemState implements Serializable {
    Map<String, Integer> inventory;
    Map<String, Reservation> bookings;

    public SystemState(Map<String, Integer> inventory,
                       Map<String, Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // SAVE (Serialization)
    public void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    // LOAD (Deserialization)
    public SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state loaded successfully.");
            return (SystemState) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Error loading data. Starting with safe state.");
        }
        return null;
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        PersistenceService service = new PersistenceService();

        // Try loading existing data
        SystemState state = service.load();

        Map<String, Integer> inventory;
        Map<String, Reservation> bookings;

        if (state != null) {
            // Restore state
            inventory = state.inventory;
            bookings = state.bookings;

            System.out.println("Recovered Inventory: " + inventory);
            System.out.println("Recovered Bookings: " + bookings.keySet());

        } else {
            // Fresh start
            inventory = new HashMap<>();
            inventory.put("Deluxe", 2);
            inventory.put("Standard", 1);

            bookings = new HashMap<>();

            // Simulate new booking
            bookings.put("RES101", new Reservation("RES101", "Deluxe"));
            inventory.put("Deluxe", inventory.get("Deluxe") - 1);

            System.out.println("New System Started.");
        }

        // Show current state
        System.out.println("Current Inventory: " + inventory);
        System.out.println("Current Bookings: " + bookings.keySet());

        // Save before shutdown
        SystemState newState = new SystemState(inventory, bookings);
        service.save(newState);
    }
}