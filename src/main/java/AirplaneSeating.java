import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AirplaneSeating {

    public static void main(String[] args) {

//        int[][] array = {{3, 2}, {4, 3}, {2, 3}, {3, 4}};
//        int totalSeatCount = 30;

        int maxRowNumber;
        List<String[][]> seats;
        int capacity;
        int totalSeatCount;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(" Enter the total compartment : ");
            int compartmentSize = scanner.nextInt();
            int[][] inputArray = new int[compartmentSize][];
            maxRowNumber = 0;

            seats = new ArrayList<>();
            capacity = 0;
            for (int i = 0; i < inputArray.length; i++) {
                System.out.println(" Enter the row of the compartment: ");
                int row = scanner.nextInt();
                System.out.println(" Enter the column of the compartment: ");
                int column = scanner.nextInt();

                if (row > maxRowNumber) maxRowNumber = row;
                seats.add(new String[column][row]);
                capacity += (column * row);
            }

            System.out.println("Enter the total passenger count : ");
            totalSeatCount = scanner.nextInt();
        }

        if (capacity < totalSeatCount) {
            System.out.println(" Seating capacity is less than total number of passengers.");
        }

        AirplaneSeating airplaneSeating = new AirplaneSeating();
        airplaneSeating.bookSeats(seats, totalSeatCount, maxRowNumber);
    }

    /**
     * Method to book all the seats based on the given priority.
     * 1. All the aisle seats need to be booked.
     * 2. Then, all the window seats need to be booked.
     * 3. Finally all the center seats need to be booked.
     *
     * @param seats          List of seat in 2D array format
     * @param totalSeatCount total number passengers
     * @param maxRowNumber   highest row number among the given 2D arrays.
     */
    private void bookSeats(List<String[][]> seats, int totalSeatCount, int maxRowNumber) {

        int currentSeatNumber = aisleSeatBooking(seats, totalSeatCount, maxRowNumber);
        currentSeatNumber = windowSeatBooking(seats, totalSeatCount, maxRowNumber, currentSeatNumber);
        currentSeatNumber = centerSeatBooking(seats, totalSeatCount, maxRowNumber, currentSeatNumber);

        printSeats(seats, maxRowNumber);
    }

    /**
     * Method to book all the possible aisle seats.
     *
     * @param seats          List of seat in 2D array format
     * @param totalSeatCount total number passengers
     * @param maxRowNumber   highest row number among the given 2D arrays.
     * @return returns the seating count booked so far.
     */
    private int aisleSeatBooking(List<String[][]> seats, int totalSeatCount, int maxRowNumber) {
        int currentSeatNumber = 0;
        for (int currentRowIndex = 0; currentRowIndex < maxRowNumber; currentRowIndex++) {
            int currentArrayIndex = 0;
            String[][] compartment = seats.get(currentArrayIndex);

            //Last 2D array's aisle seat booking will be done here
            if (currentRowIndex < compartment.length) {
                if (totalSeatCount > currentSeatNumber)
                    compartment[currentRowIndex][compartment[currentRowIndex].length - 1] = "A-" + (++currentSeatNumber);
                else break; //If booking is done for given number of passengers, then immediately break the loop.
            }

            currentArrayIndex++;

            //Middle array's aisle seat booking will be done here.
            for (; currentArrayIndex < seats.size() - 1; currentArrayIndex++) {
                compartment = seats.get(currentArrayIndex);
                if (currentRowIndex < compartment.length) {
                    if (totalSeatCount > currentSeatNumber)
                        compartment[currentRowIndex][0] = " A-" + (++currentSeatNumber);
                    else break;
                    if (totalSeatCount > currentSeatNumber)
                        compartment[currentRowIndex][compartment[currentRowIndex].length - 1] = " A-" + (++currentSeatNumber);
                    else break;
                }
            }

            //Last 2D array's aisle seat booking will be done here.
            compartment = seats.get(currentArrayIndex);
            if (currentRowIndex < compartment.length) {
                if (totalSeatCount > currentSeatNumber)
                    compartment[currentRowIndex][0] = " A-" + (++currentSeatNumber);
                else break;
            }
        }

        return currentSeatNumber;
    }

    /**
     * Method to book window seat. Window seats are only possible in first and last 2D arrays. So here we are extracting
     * the first and last 2D array and setting up the seat numbers.
     *
     * @param seats             List of seat in 2D array format
     * @param totalSeatCount    total number passengers
     * @param maxRowNumber      highest row number among the given 2D arrays.
     * @param currentSeatNumber seating count booked so far.
     * @return returns the last booked seat number.
     */
    private int windowSeatBooking(List<String[][]> seats, int totalSeatCount, int maxRowNumber, int currentSeatNumber) {
        for (int currentRowIndex = 0; currentRowIndex < maxRowNumber; currentRowIndex++) {
            String[][] compartment = seats.get(0);
            if (currentRowIndex < compartment.length) {
                if (totalSeatCount > currentSeatNumber)
                    compartment[currentRowIndex][0] = " W-" + (++currentSeatNumber);
                else break;
            }
            compartment = seats.get(seats.size() - 1);
            if (currentRowIndex < compartment.length) {
                if (totalSeatCount > currentSeatNumber)
                    compartment[currentRowIndex][compartment[currentRowIndex].length - 1] = " W-" + (++currentSeatNumber);
                else break;
            }
        }
        return currentSeatNumber;
    }

    /**
     * Method to book center seats in the given array. When we book center seats, we don't need to bother about first and
     * last column of the array. So we have started from 1st index and ended with n-1th index.
     *
     * @param seats             List of seat in 2D array format
     * @param totalSeatCount    total number passengers
     * @param maxRowNumber      highest row number among the given 2D arrays.
     * @param currentSeatNumber seating count booked so far.
     * @return returns the last booked seat number.
     */
    private int centerSeatBooking(List<String[][]> seats, int totalSeatCount, int maxRowNumber, int currentSeatNumber) {
        for (int currentRowIndex = 0; currentRowIndex < maxRowNumber; currentRowIndex++) {
            int currentArrayIndex = 0;
            while (currentArrayIndex < seats.size()) {
                String[][] compartment = seats.get(currentArrayIndex++);
                int start = 1;
                int end;

                if (currentRowIndex < compartment.length)
                    end = compartment[currentRowIndex].length - 1;
                else
                    continue;

                while (start < end) {
                    if (totalSeatCount > currentSeatNumber)
                        compartment[currentRowIndex][start++] = " C-" + (++currentSeatNumber);
                    else break;
                }
            }
        }
        return currentSeatNumber;
    }

    /**
     * Method to print all the seats with proper space
     *
     * @param seats       List of seat in 2D array format
     * @param maxRowCount highest row number among the given 2D arrays.
     */
    private void printSeats(List<String[][]> seats, int maxRowCount) {
        for (int currentRowIndex = 0; currentRowIndex < maxRowCount; currentRowIndex++) {
            for (String[][] compartment : seats) {
                if (currentRowIndex >= compartment.length) {
                    for (int j = 0; j < compartment[0].length; j++) {
                        System.out.printf("%6s", "   ");
                    }
                    continue;
                }
                for (int j = 0; j < compartment[currentRowIndex].length; j++) {
                    if (null != compartment[currentRowIndex][j])
                        System.out.printf("%5s", compartment[currentRowIndex][j]);
                    else
                        System.out.printf("%5s", "---");
                }
                System.out.print(" | ");
            }
            System.out.println("\n");
        }
    }

}
