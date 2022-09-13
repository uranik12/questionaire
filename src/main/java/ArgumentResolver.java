import java.text.ParseException;
import java.util.*;

/**
 * Responsible for parsing arguments and parameters passed to the application.
 * <p>
 * In the context of this app, parameters are double-dashed, no value arguments, such as --pretty;
 * Arguments are key-value pairs sent in succession, such as -t 1200, which means the total amount is 1200.
 */
public class ArgumentResolver {

    private final Map<String, String> arguments;
    private final Set<String> parameters;
    private final Set<String> possibleParameters;

    private final Set<String> requiredArguments;
    private final Map<String, Class<?>> possibleArguments;


    {
        possibleArguments = new HashMap<>();
        possibleParameters = new HashSet<>();
        requiredArguments = new HashSet<>();

        Collections.addAll(possibleParameters, "--pretty");

        possibleArguments.put("-t", Double.class);
        possibleArguments.put("-a", Integer.class);

        requiredArguments.add("-t");
        requiredArguments.add("-a");
    }

    /**
     * Creates a new argument resolver from an array of strings representing argument sent via command line.
     * Populates the set and map of parameters and arguments respectively. Finally, it checks whether all required
     * parameters have been passed.
     *
     * @param arguments an array of parameters, arguments, and their values
     * @throws IllegalStateException if required parameters are missing from the array
     */

    public ArgumentResolver(String[] arguments) {

        this.arguments = new LinkedHashMap<>();
        this.parameters = new LinkedHashSet<>();

        for (int i = 0; i < arguments.length; i++) {

            if (arguments[i].startsWith("--")) {
                addParameter(arguments[i]);
            } else if (arguments[i].startsWith("-")) {
                // In case the string is an argument, the next string must be its value, hence ++i
                addArgument(arguments[i], arguments[++i]);
            }

        }

        this.requiredArguments.forEach(arg -> {
            if (this.arguments.get(arg) == null)
                throw new IllegalStateException("Argument " + arg + " required, but not found.");
        });

    }

    /**
     * Adds the supplied parameter to the parameter set, if it's a valid parameter;
     *
     * @param parameter the parameter to be added
     * @throws IllegalArgumentException if the supplied parameter is not a valid parameter
     */
    private void addParameter(String parameter) {
        if (possibleParameters.contains(parameter))
            this.parameters.add(parameter);
        else throw new IllegalArgumentException("Unknown parameter \"" + parameter + "\"");
    }

    /**
     * Adds the supplied argument and its value to the collection of all arguments, if the argument
     *
     * @param argument    - the argument to be added
     * @param passedValue - the value of the argument to be added
     * @throws IllegalArgumentException if the supplied argument is not a valid argument
     */
    private void addArgument(String argument, String passedValue) {
        if (!possibleArguments.containsKey(argument))
            throw new IllegalArgumentException("Unknown argument " + argument);

        if (validateValue(argument, passedValue)) {
            this.arguments.put(argument, passedValue);
        }
    }

    /**
     * Validates whether the supplied value is a valid value for the supplied argument. Each argument
     * has a class assigned to them, which represents the value they accept. In case the value passes the
     * cast to that class, it's considered a valid value, and a true value is returned. Else, an exception is thrown.
     *
     * @param argument    the argument whose value is being validated
     * @param passedValue the value to be validated
     * @return true if the parameter is valid
     * @throws IllegalArgumentException if the supplied value is not valid for that parameter
     */

    private boolean validateValue(String argument, String passedValue) {

        Class<?> targetClass = possibleArguments.get(argument);

        if (targetClass == Double.class)
            try {
                Double.parseDouble(passedValue);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Argument " + argument + " of type Double "
                        + " cannot have invalid value " + passedValue);
            }
        else if (targetClass == Integer.class) {
            try {
                Integer.parseInt(passedValue);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Argument " + argument + " of type Integer "
                        + " cannot have invalid value " + passedValue);
            }
        }

        return true;
    }

    public String getArgumentValue(String parameterName) {
        return arguments.get(parameterName);
    }

    public boolean hasParameter(String parameter) {
        return parameters.contains(parameter);
    }

}
