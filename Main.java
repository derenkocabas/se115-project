import java.io.*;
import java.util.*;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
                              "July","August","September","October","November","December"};

    
    static int[][][] data = new int[MONTHS][DAYS][COMMS];

    public static void loadData() {
        for (int month = 0; month < MONTHS; month++) {
            String filename = "Data_Files/" + months[month] + ".txt";
            try {
                Scanner scanner = new Scanner(new File(filename));

                
                if (scanner.hasNextLine()) {
                    String firstLine = scanner.nextLine().trim();
                    if (!firstLine.equals("Day,Commodity,Profit")) {
                        processDataLine(firstLine, month);
                    }
                }
                
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    if (!line.isEmpty()) {
                        processDataLine(line, month);
                    }
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                
            }
        }
    }

    private static void processDataLine(String line, int month) {
        String[] parts = line.split(",");
        if (parts.length == 3) {
            try {
                int day = Integer.parseInt(parts[0].trim()) - 1; 
                String commodityName = parts[1].trim();
                int profit = Integer.parseInt(parts[2].trim());
                
                int commodityIndex = getCommodityIndex(commodityName);
                if (commodityIndex != -1 && day >= 0 && day < DAYS) {
                    data[month][day][commodityIndex] = profit;
                }
            } catch (NumberFormatException e) {
                
            }
        }
    }

    private static int getCommodityIndex(String name) {
        for (int i = 0; i < commodities.length; i++) {
            if (commodities[i].equals(name)) {
                return i;
            }
        }
        return -1;
    }

    
    public static String mostProfitableCommodityInMonth(int month) {
        if (month < 0 || month >= MONTHS) return "INVALID_MONTH";

        int maxProfit = Integer.MIN_VALUE;
        int bestComm = -1;
        for (int c = 0; c < COMMS; c++) {
            int total = 0;
            for (int d = 0; d < DAYS; d++) {
                total += data[month][d][c];
            }
            if (total > maxProfit) {
                maxProfit = total;
                bestComm = c;
            }
        }
        return commodities[bestComm] + " " + maxProfit;
    }

    
    public static int totalProfitOnDay(int month, int day) {
        if (month < 0 || month >= MONTHS || day < 1 || day > DAYS) return -99999;

        int total = 0;
        for (int c = 0; c < COMMS; c++) {
            total += data[month][day - 1][c];
        }
        return total;
    }


    public static int commodityProfitInRange(String commodity, int from, int to) {
        int cIdx = getCommodityIndex(commodity);
        if (cIdx == -1 || from < 1 || to > DAYS || from > to) return -99999;

        int totalProfit = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = from - 1; d < to; d++) {
                totalProfit += data[m][d][cIdx];
            }
        }
        return totalProfit;
    }

    
    public static int bestDayOfMonth(int month) {
        if (month < 0 || month >= MONTHS) return -1;

        int bestDay = -1;
        int maxProfit = Integer.MIN_VALUE;

        for (int d = 0; d < DAYS; d++) {
            int dailyTotal = 0;
            for (int c = 0; c < COMMS; c++) {
                dailyTotal += data[month][d][c];
            }
            if (dailyTotal > maxProfit) {
                maxProfit = dailyTotal;
                bestDay = d + 1;
            }
        }
        return bestDay;
    }

    
    public static String bestMonthForCommodity(String comm) {
        int idx = getCommodityIndex(comm);
        if (idx == -1) return "INVALID_COMMODITY";

        int bestMonthIndex = -1;
        int maxProfit = Integer.MIN_VALUE;

        for (int m = 0; m < MONTHS; m++) {
            int monthlyTotal = 0;
            for (int d = 0; d < DAYS; d++) {
                monthlyTotal += data[m][d][idx];
            }
            if (monthlyTotal > maxProfit) {
                maxProfit = monthlyTotal;
                bestMonthIndex = m;
            }
        }
        return months[bestMonthIndex];
    }

    public static int consecutiveLossDays(String comm) { 
        return 1234; 
    }
    
    public static int daysAboveThreshold(String comm, int threshold) { 
        return 1234; 
    }

    public static int biggestDailySwing(int month) { 
        return 1234; 
    }
    
    public static String compareTwoCommodities(String c1, String c2) { 
        return "DUMMY is better by 1234"; 
    }
    
    public static String bestWeekOfMonth(int month) { 
        return "DUMMY"; 
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}
