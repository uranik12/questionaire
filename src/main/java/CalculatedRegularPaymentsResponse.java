public class CalculatedRegularPaymentsResponse {
    private final double regularAmount;
    private final double lastAmount;

    public CalculatedRegularPaymentsResponse(double regularAmount, double lastAmount) {
        this.regularAmount = regularAmount;
        this.lastAmount = lastAmount;
    }

    public double getRegularAmount() {
        return regularAmount;
    }

    public double getLastAmount() {
        return lastAmount;
    }
}
