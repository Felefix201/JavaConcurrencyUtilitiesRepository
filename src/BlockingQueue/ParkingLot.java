package BlockingQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ParkingLot {

    private BlockingQueue<Car> cars;
    private int maxPlaces;

    public ParkingLot(int maxPlaces) {
        this.maxPlaces = maxPlaces;
        cars = new LinkedBlockingQueue<>(maxPlaces);
    }

    public synchronized void enter(Car car) throws InterruptedException {
        while(cars.size() == maxPlaces){
            wait();
        }
        cars.put(car);
        System.out.println(car.getName() + " entered the parking lot. " + (maxPlaces - cars.size()) + " places left.");
    }

    public synchronized void leave() {
        cars.poll();
        System.out.println("A car left the parking lot. " + (maxPlaces - cars.size()) + " places left.");
        notifyAll();
    }

    public static void main(String[] args) {
        ParkingLot parkingLot = new ParkingLot(8);
        for (int i = 0; i < 20; i++) {
            Car car = new Car(parkingLot, "Car " + i);
            car.start();
        }
    }
}

class Car extends Thread {

    private ParkingLot parkingLot;

    public Car(ParkingLot parkingLot, String name) {
        super(name);
        this.parkingLot = parkingLot;
    }

    @Override
    public void run() {
        long time = System.nanoTime();

        try {
            parkingLot.enter(this);
        } catch (InterruptedException e) {
            System.out.println("An error while entering has occured");
        }

        try {
            Thread.sleep((long)(Math.random() * 5000 + 5000));
        } catch (InterruptedException e) {
            System.out.println("An error while thread sleeping has occured");
        }

            parkingLot.leave();

        System.out.println(this.getName() + " has been in the parking lot for " + (System.nanoTime() - time) / 1000000 + " milliseconds.");
    }
}

