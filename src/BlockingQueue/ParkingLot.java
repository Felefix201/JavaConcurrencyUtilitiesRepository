package BlockingQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * A parking lot has a limited number of places. Cars can enter and leave the parking lot.
 * When the parking lot is full, cars have to wait until a place becomes available.
 * When a car leaves the parking lot, it notifies the waiting cars that a place has become available.
 * Create a ParkingLot class with methods enter and leave.
 */
public class ParkingLot {
    private final BlockingQueue<Car> cars;
    private final int maxPlaces;

    public ParkingLot(int maxPlaces) {
        this.maxPlaces = maxPlaces;
        cars = new LinkedBlockingQueue<>(maxPlaces);
    }

    public synchronized void enter(Car car) throws InterruptedException {
        while(cars.remainingCapacity() == 0){
            wait();
        }
        cars.put(car);
        System.out.println(car.getName() + " entered the parking lot. " + (maxPlaces - cars.size()) + " places left.");
        notifyAll();
    }

    public synchronized void leave() {
        try {
            cars.poll();
            System.out.println("A car left the parking lot. " + (maxPlaces - cars.size()) + " places left.");
        } finally {
            notifyAll();
        }
    }

    public static void main(String[] args) {
        ParkingLot parkingLot = new ParkingLot(8);
        for (int i = 0; i < 20; i++) {
            Car car = new Car(parkingLot, "Car " + i);
            car.start();
        }
    }
}


/**
 * A car enters the parking lot and leaves after a while.
 * The car waits if the parking lot is full.
 * The parking lot notifies the waiting cars when a place becomes available.
 * The parking lot has a limited number of places.
 */
class Car extends Thread {
    private final ParkingLot parkingLot;

    public Car(ParkingLot parkingLot, String name) {
        super(name);
        this.parkingLot = parkingLot;
    }

    @Override
    public void run() {
        try {
            parkingLot.enter(this);
        } catch (InterruptedException e) {
            System.out.println("An error while entering has occured");
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("An error while thread sleeping has occured");
        }
            parkingLot.leave();

    }
}

