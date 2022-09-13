import java.math.BigDecimal;
import java.text.DecimalFormat;

public class App {

    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_YELLOW = "\u001B[33m";
    static final String ANSI_CYAN = "\u001B[36m";

    static final DecimalFormat decimalFormatter = new DecimalFormat("#0.00");

    public static void main(String[] args) {

        ArgumentResolver argumentResolver = new ArgumentResolver(args);
        PaymentsService paymentsService = new PaymentsService();

        double totalAmount = Double.parseDouble(argumentResolver.getArgumentValue("-t"));
        int paymentAmounts = Integer.parseInt(argumentResolver.getArgumentValue("-a"));

        CalculatedRegularPaymentsResponse response = paymentsService.calculateRegularRecurringPayment(
                totalAmount, paymentAmounts
        );

        boolean prettyPrintingEnabled = argumentResolver.hasParameter("--pretty");
        String total = decimalFormatter.format(totalAmount);
        String regularAmount = decimalFormatter.format(response.getRegularAmount());
        String lastAmount = decimalFormatter.format(response.getLastAmount());

        if (!prettyPrintingEnabled) {
            System.out.println("Total amount to be paid is $" + total + ", in " + paymentAmounts + " payments.");
            System.out.println("Regular amount - $" + regularAmount);
            if (response.getLastAmount() != 0.0) {
                System.out.println("Last amount - $" + lastAmount);
            }
        } else {
            printlnYellow("Total amount to be paid is $" + total + ", in " + paymentAmounts + " payments.");
            printGreen("Regular amount - $" + regularAmount);
            if (response.getLastAmount() != 0.0) {
                printCyan("Last amount - $" + lastAmount);
            }
        }
    }

    private static void printlnYellow(Object o) {
        System.out.println(ANSI_YELLOW + o + ANSI_RESET);
    }

    private static void printGreen(Object o) {
        System.out.println(ANSI_GREEN + o + ANSI_RESET);
    }

    private static void printCyan(Object o) {
        System.out.println(ANSI_CYAN + o + ANSI_RESET);
    }

}
