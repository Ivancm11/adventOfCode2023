package days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day6 {

    static List<Long> allTimes = new ArrayList<>();
    static List<Long> allDistances = new ArrayList<>();

    public static void main(String[] args) {
        try {
            File myObj = new File("src/inputs/input6.txt");
            Scanner myReader = new Scanner(myObj);
            long solution = 1;

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine().replace("  ", " ").replace("  ", " ");
                String clearData = clearMultipleSpaces(data);

                String[] typeAndNumbers = clearData.split(":");
                String typeOfData = clearData.split(":")[0];

                String[] numbers = Arrays.copyOfRange(typeAndNumbers, 1, typeAndNumbers.length);
                numbers = numbers[0].split(" ");

                if(typeOfData.equals("Time")){
                    for(String time: numbers){
                        if(!time.equals("")){
                            allTimes.add(Long.parseLong(time));
                        }
                    }
                }
                if(typeOfData.equals("Distance")){
                    for(String distance: numbers){
                        if(!distance.equals("")){
                            allDistances.add(Long.parseLong(distance));
                        }
                    }
                }

            }

            for(int i = 0; i < allDistances.size(); i++){
                solution = solution * calculateSolutions(allTimes.get(i), allDistances.get(i));
            }
            System.out.println("RESULT: " + solution);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static String clearMultipleSpaces(String line){
        String regex = "\s+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        return matcher.replaceAll(" ");
    }

    private static long calculateSolutions(long time, long distance){
        long solution = 0;
        for(long i = 1; i < time; i++){
            if((time - i) * i > distance){
                solution++;
            }
        }
        return solution;
    }

}
