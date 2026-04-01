import java.util.*;

// Add-On Service class
class Service {
    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    // Map<ReservationID, List of Services>
    private Map<String, List<Service>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    // Add service to a reservation
    public void addService(String reservationId, Service service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    // Get services for a reservation
    public List<Service> getServices(String reservationId) {
        return serviceMap.getOrDefault(reservationId, new ArrayList<>());
    }

    // Calculate total add-on cost
    public double calculateTotalCost(String reservationId) {
        double total = 0.0;

        List<Service> services = serviceMap.get(reservationId);
        if (services != null) {
            for (Service s : services) {
                total += s.getCost();
            }
        }

        return total;
    }

    // Display services
    public void displayServices(String reservationId) {
        List<Service> services = getServices(reservationId);

        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("Add-On Services for Reservation " + reservationId + ":");
        for (Service s : services) {
            System.out.println("- " + s.getServiceName() + " : ₹" + s.getCost());
        }
    }
}

// Main Class (IMPORTANT: your naming preference)
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        // Example reservation (comes from UC6)
        String reservationId = "RES101";

        // Guest selects services
        manager.addService(reservationId, new Service("Breakfast", 500));
        manager.addService(reservationId, new Service("Airport Pickup", 1200));
        manager.addService(reservationId, new Service("Extra Bed", 800));

        // Display selected services
        manager.displayServices(reservationId);

        // Calculate total cost
        double totalCost = manager.calculateTotalCost(reservationId);
        System.out.println("Total Add-On Cost: ₹" + totalCost);
    }
}