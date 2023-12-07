package days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day7 implements Comparator<String>{
    static Map<String, Integer> handAndBid = new HashMap<>();
    static List<String> fiveOfKind = new ArrayList<>();
    static List<String> fourOfKind = new ArrayList<>();
    static List<String> fullHouse = new ArrayList<>();
    static List<String> threeOfKind = new ArrayList<>();
    static List<String> twoPair = new ArrayList<>();
    static List<String> aPair = new ArrayList<>();
    static List<String> highCard = new ArrayList<>();
    static long rank = 1;

    public static void main(String[] args) {
        try {
            File myObj = new File("src/inputs/input7.txt");
            Scanner myReader = new Scanner(myObj);


            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] splitData = data.split(" ");
                String hand = splitData[0];
                int bid = Integer.parseInt(splitData[1]);
                handAndBid.put(hand, bid);

                getValueTypePartTwo(hand);
            }

            fiveOfKind.sort(new Day7());
            fourOfKind.sort(new Day7());
            fullHouse.sort(new Day7());
            threeOfKind.sort(new Day7());
            twoPair.sort(new Day7());
            aPair.sort(new Day7());
            highCard.sort(new Day7());

            long solution = 0;

            solution = totalSolution(solution, highCard);
            solution = totalSolution(solution, aPair);
            solution = totalSolution(solution, twoPair);
            solution = totalSolution(solution, threeOfKind);
            solution = totalSolution(solution, fullHouse);
            solution = totalSolution(solution, fourOfKind);
            solution = totalSolution(solution, fiveOfKind);

            System.out.println("RESULT: " + solution);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static long totalSolution(long currentSolution, List<String> hands){
        for(String hand: hands){
            currentSolution = currentSolution + handAndBid.get(hand)*rank;
            rank++;
        }
        return currentSolution;
    }
    private static void getValueType(String hand){
        Map<Character, Integer> maxRepeatedValue = new HashMap<>();
        int maxValue = 1;
        int secondMaxValue = 1;

        for(int i = 0; i < 5; i++){
            Character currentCharacter = hand.charAt(i);
            int value = maxRepeatedValue.getOrDefault(currentCharacter, 0)  + 1;
            maxRepeatedValue.put(currentCharacter, value);
            if(value > maxValue){
                maxValue = value;
            }
            else if(value == maxValue){
                secondMaxValue = value;
            }
            else if(value > secondMaxValue){
                secondMaxValue = value;
            }
        }

        categorizeHand(maxValue, secondMaxValue, hand);

    }

    private static void getValueTypePartTwo(String hand){
        Map<Character, Integer> maxRepeatedValue = new HashMap<>();
        int maxValue = 0;
        int secondMaxValue = 0;

        for(int i = 0; i < 5; i++){
            Character currentCharacter = hand.charAt(i);

            int value = maxRepeatedValue.getOrDefault(currentCharacter, 0)  + 1;
            maxRepeatedValue.put(currentCharacter, value);
            if(!currentCharacter.equals('J')){
                if(value > maxValue){
                    maxValue = value;
                }
                else if(value == maxValue){
                    secondMaxValue = value;
                }
                else if(value > secondMaxValue){
                    secondMaxValue = value;
                }
            }
        }

        maxValue = maxValue + maxRepeatedValue.getOrDefault('J', 0);

        categorizeHand(maxValue, secondMaxValue, hand);

    }

    private static void categorizeHand(int maxValue, int secondMaxValue, String hand){
        if(maxValue == 5){
            fiveOfKind.add(hand);
        }
        else if(maxValue == 4){
            fourOfKind.add(hand);
        }
        else if(maxValue == 3){
            if(secondMaxValue == 2){
                fullHouse.add(hand);
            }
            else{
                threeOfKind.add(hand);
            }
        }
        else if(maxValue == 2){
            if(secondMaxValue == 2){
                twoPair.add(hand);
            }
            else{
                aPair.add(hand);
            }
        }
        else{
            highCard.add(hand);
        }
    }
    private static final String ORDER = "AKQJT98765432";
    private static final String ORDER_FOR_PART_TWO = "AKQT98765432J";

    @Override
    public int compare(String s1, String s2) {
        int minLength = Math.min(s1.length(), s2.length());

        for (int i = 0; i < minLength; i++) {
            char char1 = s1.charAt(i);
            char char2 = s2.charAt(i);

            int index1 = ORDER_FOR_PART_TWO.indexOf(char1);
            int index2 = ORDER_FOR_PART_TWO.indexOf(char2);

            if (index1 < index2) {
                return 1;
            } else if (index1 > index2) {
                return -1;
            }
        }

        return Integer.compare(s2.length(), s1.length());
    }
}
