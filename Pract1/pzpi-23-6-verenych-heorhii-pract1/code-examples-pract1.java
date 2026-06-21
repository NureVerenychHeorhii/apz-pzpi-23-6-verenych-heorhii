package ua.nure.verenych.pz;

import java.util.ArrayList;
import java.util.List;

interface BidObserver {
    void update(String lotName, double newPrice, String bidderName);
}

class AuctionLot {
    private final List<BidObserver> observers = new ArrayList<>();
    private final String lotName;
    private double currentPrice;
    private String lastBidder;

    public AuctionLot(String lotName, double startPrice) {
        this.lotName = lotName;
        this.currentPrice = startPrice;
        this.lastBidder = "—";
    }

    public void subscribe(BidObserver observer) {
        observers.add(observer);
    }

    public void unsubscribe(BidObserver observer) {
        observers.remove(observer);
    }

    public void placeBid(String bidderName, double amount) {
        if (amount <= currentPrice) {
            System.out.println("Ставку " + amount
                    + " відхилено: вона не перевищує ціну " + currentPrice);
            return;
        }
        this.currentPrice = amount;
        this.lastBidder = bidderName;
        notifyObservers();
    }

    private void notifyObservers() {
        for (BidObserver observer : observers) {
            observer.update(lotName, currentPrice, lastBidder);
        }
    }
}

class PriceDisplay implements BidObserver {
    @Override
    public void update(String lotName, double newPrice, String bidderName) {
        System.out.println("[Табло] Лот \"" + lotName
                + "\": поточна ціна " + newPrice + " грн");
    }
}

class BidderNotificationService implements BidObserver {
    @Override
    public void update(String lotName, double newPrice, String bidderName) {
        System.out.println("[Сповіщення] Учасник " + bidderName
                + " зробив ставку " + newPrice + " грн за лот \"" + lotName + "\"");
    }
}

class BidHistoryLogger implements BidObserver {
    @Override
    public void update(String lotName, double newPrice, String bidderName) {
        System.out.println("[Журнал] Прийнято ставку: " + bidderName
                + " -> " + newPrice + " грн (лот \"" + lotName + "\")");
    }
}

public class ObserverDemo {
    public static void main(String[] args) {
        AuctionLot lot = new AuctionLot("Картина \"Світанок\"", 1000.0);

        BidObserver display = new PriceDisplay();
        BidObserver notifier = new BidderNotificationService();
        BidObserver logger = new BidHistoryLogger();

        lot.subscribe(display);
        lot.subscribe(notifier);
        lot.subscribe(logger);

        lot.placeBid("Олена", 1200.0);
        lot.placeBid("Андрій", 1500.0);
        lot.placeBid("Олена", 1400.0);
        lot.placeBid("Ігор", 1800.0);
    }
}
