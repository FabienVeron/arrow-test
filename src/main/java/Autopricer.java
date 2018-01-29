import core.*;

class Autopricer {
    static String processOneMail(Mail mail) {
        String result;
        try {
            Rfq rfq = AutopricerInternals.read(mail);
            PricedRfq pricedRfq = AutopricerInternals.price(rfq);
            result = pricedRfq.product + " = " + pricedRfq.price;
        } catch (MailSubjectNotReadableException e) {
            result = "Subject contains unrecognized characters";
        } catch (MailBodyNotReadableException e) {
            result = "Body contains unrecognized characters";
        } catch (ClientNotRecognizedException e) {
            result = "I am sorry but we don't know you ¯\\_(ツ)_/¯ ";
        } catch (ProductNotSupportedException e) {
            result = "We are not supporting this product at this time";
        } catch (PricerNotAvailableException e) {
            result = "Sorry unable to answer you now. Please come back in 10min. [_]3";
        }
        return result;
    }

    public static void main(String[] args) {
        processOneMail(new Mail(args[0],args[1]));
    }
}


