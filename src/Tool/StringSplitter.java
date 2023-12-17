package Tool;

import LinkedList.LinkedList;

public class StringSplitter {

    public static LinkedList<Integer> splitStringToList(String input) {
        LinkedList<Integer> result = new LinkedList<Integer>();

        if (input == null || input.isEmpty()) {
            return result;
        }

        String[] numbers = input.split(",");
        for (String number : numbers) {
            try {
                int parsedNumber = Integer.parseInt(number.trim());
                result.append(parsedNumber);
            } catch (NumberFormatException e) {
                // Handle the case where a non-integer value is encountered
                System.err.println("Invalid number format: " + number);
            }
        }

        return result;
    }
}