package WaitNotifyNotifyAll;

public class ParkingLot {
    // Parkhaus aufgabe
    // Ein Parkhaus hat 8 Plätze, es möchten 20 Autos hinein fahren, ein Auto bleibt 5 - 10 sekunden im Parkhaus

    private int availablePlaces;
    private static int maxPlaces;

    public ParkingLot(int maxPlaces) {
        ParkingLot.maxPlaces = maxPlaces;
        availablePlaces = maxPlaces;
    }

    public synchronized void enter(Car car) throws InterruptedException {
        while (availablePlaces == 0) {
            wait();
        }
        availablePlaces -= 1;
        System.out.println(car.getName() + " entered the parking lot. " + availablePlaces + " places left.");
    }

    public synchronized void leave() {
        availablePlaces += 1;
        notifyAll();
        System.out.println("A car left the parking lot. " + availablePlaces + " places left.");
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
            System.out.println("An Error while entering has occured");
        }

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("An Error while Thread sleeping has occured");
        }

        parkingLot.leave();

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("An Error while Thread sleeping has occured");
        }

        //System.out.println(this.getName() + " has been in the parking lot for " + (System.nanoTime() - time) / 1000 + " microseconds.");
    }
}