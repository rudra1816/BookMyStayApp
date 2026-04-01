import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Inventory class (simplified from UC3)
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Deluxe", 2);
        inventory.put("Standard", 1);
    }

    public boolean isValidRoomType(String roomType) {
        return inventory.containsKey(roomType);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void reduceRoom(String roomType) throws InvalidBookingException {
        int count = getAvailability(roomType);

        if (count <= 0) {
            throw new InvalidBookingException("No rooms available for " + roomType);
        }

        inventory.put(roomType, count - 1);
    }
}

// Validator class
class BookingValidator {

    public static void validate(String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        // Check if room type exists
        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        // Check availability
        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException("Room not available: " + roomType);
        }
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        // Test cases (valid + invalid)
        String[] requests = {"Deluxe", "Suite", "Standard", "Standard"};

        for (String roomType : requests) {

            try {
                System.out.println("\nProcessing booking for: " + roomType);

                // Validation (fail-fast)
                BookingValidator.validate(roomType, inventory);

                // Allocation
                inventory.reduceRoom(roomType);

                System.out.println("Booking confirmed for " + roomType);

            } catch (InvalidBookingException e) {
                // Graceful failure
                System.out.println("Booking failed: " + e.getMessage());
            }
        }
    }
}