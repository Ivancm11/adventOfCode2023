package days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day8 {

    static Map<String, List<String>> directions = new HashMap<>();
    static String instruction = "";
    static String startPosition = "AAA";
    static List<String> startPositions = new ArrayList<>();
    static List<Long> lcms = new ArrayList<>();

    public static void main(String[] args) {
        try {
            File myObj = new File("src/inputs/input8.txt");
            Scanner myReader = new Scanner(myObj);
            long solution = 1;

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                if(instruction.equals("")){
                    instruction = data;
                }
                else if(!data.equals("")){
                    data = data.replace(" ", "");
                    String[] splitData = data.split("=");
                    String origin = splitData[0];
                    if(startPosition.equals("")){
                        startPosition = origin;
                    }
                    if(origin.charAt(2) == 'A'){
                        startPositions.add(origin);
                    }
                    String[] leftAndRight = splitData[1].replace("(","")
                            .replace(")","").split(",");
                    String left = leftAndRight[0];
                    String right = leftAndRight[1];

                    directions.put(origin, new ArrayList<>(){{add(left); add(right);}});
                }

            }

            // solution = iterateMap();
            populateLcms();
            long finalSolution = calculateLCM(lcms);

            System.out.println("RESULT: " + solution);
            System.out.println("RESULT2: " + finalSolution);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static int iterateMap(){
        int index = 0;
        int iterations = 0;
        String currentPosition = startPosition;

        while(!currentPosition.equals("ZZZ")){
            int leftOrRight = instruction.charAt(index) == 'L' ? 0 : 1;
            currentPosition = directions.get(currentPosition).get(leftOrRight);

            index = (index+1) % instruction.length();
            iterations++;
        }

        return iterations;
    }

    private static void populateLcms(){
        for(int i = 0; i < startPositions.size(); i++){
            int iterations = 1;
            boolean isFinished = false;
            int index = 0;
            String currentPosition = startPositions.get(i);
            while(!isFinished){
                int leftOrRight = instruction.charAt(index) == 'L' ? 0 : 1;
                currentPosition = directions.get(currentPosition).get(leftOrRight);

                if(currentPosition.charAt(2) == 'Z'){
                    isFinished = true;
                    lcms.add((long)iterations);
                }
                index = (index+1) % instruction.length();
                iterations++;
            }
        }
    }

    static long calculateLCM(List<Long> numbers)
    {
        return numbers.stream().reduce(
                1L, (x, y) -> (x * y) / gcd(x, y));
    }

    static long gcd(long a, long b)
    {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }
}
