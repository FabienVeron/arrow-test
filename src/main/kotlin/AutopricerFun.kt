import arrow.data.*
import arrow.typeclasses.binding
import core.*

fun `process one mail func style`(mail: Mail): String {
    val pricedRfq = Try.monad().binding {
        val rfq = Try { AutopricerInternals.read(mail) }.bind()
        yields(Try { AutopricerInternals.price(rfq) }.bind())
    }.ev()
    return when (pricedRfq) {
        is Success -> pricedRfq.value.product + " = " + pricedRfq.value.price
        is Failure -> {
            when (pricedRfq.exception) {
                is MailSubjectNotReadableException -> "Subject contains unrecognized characters"
                is MailBodyNotReadableException -> "Subject contains unrecognized characters"
                is ClientNotRecognizedException -> "I am sorry but we don't know you ¯\\_(ツ)_/¯ "
                is ProductNotSupportedException -> "We are not supporting this product at this time"
                is PricerNotAvailableException -> "Sorry unable to answer you now. Please come back in 10min. [_]3"
                else -> "We have no solution for you..."
            }
        }
    }
}