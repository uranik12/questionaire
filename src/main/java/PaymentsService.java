/**
 * Handles all payment related business logic.
 */
public class PaymentsService {

    /**
     * Given a total amount of money to be paid, and a number of times it will be paid over a period of time,
     * calculates the regular amount of money to be paid each time. In the case where a given total cannot
     * be paid in equal amounts each time, the last payment will be calculated separately.
     * <p>
     * Initially, the regular amount is calculated through integer division of the total and amounts. This is
     * then rounded to 2 decimal points. If the total amount cannot be equally divided into regular payments,
     * the last payment is calculated separately, by subtracting from the total the sum of n-1 regular payments,
     * also rounded to two decimal points.
     *
     * @param total          the total amount of money to be paid
     * @param paymentAmounts the number of times the total has to be paid for regularly
     * @return the calculated regular amount and possibly different last amount
     */
    public CalculatedRegularPaymentsResponse calculateRegularRecurringPayment(double total, int paymentAmounts) {

        double regularPaymentAmount = total / paymentAmounts;
        regularPaymentAmount = Math.floor(regularPaymentAmount * 100.00) / 100.00;
        double lastPaymentAmount = 0;

        double shouldBeTotal = regularPaymentAmount * paymentAmounts;

        if (total != shouldBeTotal) {
            lastPaymentAmount = total - (paymentAmounts - 1) * regularPaymentAmount;
            lastPaymentAmount = Math.round(lastPaymentAmount * 100.0) / 100.0;
        }

        return new CalculatedRegularPaymentsResponse(regularPaymentAmount, lastPaymentAmount);

    }

}
