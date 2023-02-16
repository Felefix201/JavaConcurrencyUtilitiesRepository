package WaitNotifyNotifyAll;

public class ParkingLot {
    // Parkhaus aufgabe
    // Ein Parkhaus hat 8 Plätze, es möchten 20 Autos hinein fahren, ein Auto bleibt 5 - 10 sekunden im Parkhaus

    private int availablePlaces;

    public ParkingLot(int places) {
        availablePlaces = places;
    }

    public synchronized void enter(Car car) throws InterruptedException {
        while (availablePlaces == 0) {
            wait();
        }
        availablePlaces --;
        System.out.println(car.getName() + " entered the parking lot. " + availablePlaces + " places left.");
    }

    public synchronized void leave() {
        availablePlaces ++;
        notifyAll();
        System.out.println("A car left the parking lot. " + availablePlaces + " places left.");
    }


    public static void main(String[] args) {
        ParkingLot pL = new ParkingLot(8);
        for (int i = 0; i < 20; i++) {
            Car car = new Car(pL, "Car " + i);
            car.start();
        }
    }
}


class Car extends Thread {

    private ParkingLot pL1;

    public Car(ParkingLot pL1, String name) {
        super(name);
        this.pL1 = pL1;
    }

    @Override
    public void run() {

        try {
            pL1.enter(this);
        } catch (InterruptedException e) {
            System.out.println("An Error while entering has occured");
        }

        try {
            sleep(0500);
        } catch (InterruptedException e) {
            System.out.println("An Error while Thread sleeping has occured");
        }

        pL1.leave();

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("An Error while Thread sleeping has occured");
        }

        //System.out.println(this.getName() + " has been in the parking lot for " + (System.nanoTime() - time) / 1000 + " microseconds.");
    }
}