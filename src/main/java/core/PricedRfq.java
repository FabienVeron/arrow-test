package core;

public class PricedRfq extends Rfq {
    public double price;

    PricedRfq(String client, String product, double price) {
        super(client, product);
        this.price = price;
    }
}
