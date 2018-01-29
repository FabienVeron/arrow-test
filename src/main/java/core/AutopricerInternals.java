package core;

import java.util.concurrent.ThreadLocalRandom;

public class AutopricerInternals {
    public static Rfq read(Mail mail) throws MailSubjectNotReadableException, MailBodyNotReadableException {
        if (mail.subject.contains("*"))
            throw new MailSubjectNotReadableException();
        if (mail.body.contains("*"))
            throw new MailBodyNotReadableException();

        return new Rfq(mail.subject, "SHAREACCU");
    }

    public static PricedRfq price(Rfq rfq) throws ClientNotRecognizedException, ProductNotSupportedException, PricerNotAvailableException {
        if (!rfq.client.equals("SUPERPB"))
            throw new ClientNotRecognizedException();
        if (rfq.product != "SHAREACCU")
            throw new ProductNotSupportedException();
        if (!isPricerAvailable())
            throw new PricerNotAvailableException();
        return new PricedRfq(rfq.client, rfq.product, priceProduct(rfq));
    }

    public static Boolean isPricerAvailable() {
        return (ThreadLocalRandom.current().nextInt(0, 10) <= 5);
    }

    public static Double priceProduct(Rfq rfq) {
        return (ThreadLocalRandom.current().nextDouble(rfq.hashCode()));
    }
}
