package days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day1 {

    private static Map<String, Integer> mappedValues = new HashMap<>(){
        {
            put("on", 1);
            put("tw", 2);
            put("thre", 3);
            put("fou", 4);
            put("fiv", 5);
            put("si", 6);
            put("seve", 7);
            put("eigh", 8);
            put("nin", 9);
        }
    };
    public static void main(String[] args) {
        try {
            File myObj = new File("src/inputs/input1.txt");
            Scanner myReader = new Scanner(myObj);
            int sum = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String calibrateNumber = getCalibrateNumber(data);
                System.out.println(data);
                System.out.println(calibrateNumber);
                sum+=Integer.parseInt(calibrateNumber);
                System.out.println("Current sum: " + sum);
            }
            myReader.close();
            System.out.println("FINAL SUM: " + sum);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static String getCalibrateNumber(String input) {
        Pattern pattern = Pattern.compile("\\d|on(?=e)|tw(?=o)|thre(?=e)|fou(?=r)|fiv(?=e)|si(?=x)|seve(?=n)|eigh(?=t)|nin(?=e)");
        Matcher matcher = pattern.matcher(input);
        int firstNumber = -1;
        int lastNumber = -1;

        while (matcher.find()) {
            if(firstNumber == -1){
                try{
                    firstNumber = Integer.parseInt(matcher.group());
                } catch (Exception e) {
                    firstNumber = mappedValues.get(matcher.group());
                }
            }
            try {
                lastNumber = Integer.parseInt(matcher.group());
            }
            catch (Exception e) {
                lastNumber = mappedValues.get(matcher.group());
            }
        }

        return firstNumber +String.valueOf(lastNumber);
    }



}
