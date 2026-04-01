import java.util.*;

// Reservation Request
class ReservationRequest {
    private String guestName;
    private String roomType;

    public ReservationRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Shared Inventory (Thread-Safe)
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Deluxe", 2);
        inventory.put("Standard", 1);
    }

    // Critical Section (SYNCHRONIZED)
    public synchronized boolean allocateRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        } else {
            return false;
        }
    }

    public void displayInventory() {
        System.out.println("Final Inventory: " + inventory);
    }
}

// Shared Booking Queue
class BookingQueue {
    private Queue<ReservationRequest> queue = new LinkedList<>();

    public synchronized void addRequest(ReservationRequest request) {
        queue.add(request);
    }

    public synchronized ReservationRequest getRequest() {
        return queue.poll();
    }
}

// Thread (Guest)
class BookingProcessor extends Thread {

    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(BookingQueue queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            ReservationRequest request;

            // Get request safely
            synchronized (queue) {
                request = queue.getRequest();
            }

            if (request == null) {
                break;
            }

            // Allocate room (critical section inside inventory)
            boolean success = inventory.allocateRoom(request.getRoomType());

            if (success) {
                System.out.println(Thread.currentThread().getName() +
                        " booked " + request.getRoomType() +
                        " for " + request.getGuestName());
            } else {
                System.out.println(Thread.currentThread().getName() +
                        " FAILED booking for " + request.getGuestName() +
                        " (No rooms)");
            }
        }
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingQueue queue = new BookingQueue();

        // Simulate multiple booking requests
        queue.addRequest(new ReservationRequest("Rudra", "Deluxe"));
        queue.addRequest(new ReservationRequest("Amit", "Deluxe"));
        queue.addRequest(new ReservationRequest("Priya", "Deluxe"));
        queue.addRequest(new ReservationRequest("Karan", "Standard"));
        queue.addRequest(new ReservationRequest("Neha", "Standard"));

        // Multiple threads (simulating concurrent users)
        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);
        BookingProcessor t3 = new BookingProcessor(queue, inventory);

        t1.start();
        t2.start();
        t3.start();

        // Wait for threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final inventory check
        inventory.displayInventory();
    }
}