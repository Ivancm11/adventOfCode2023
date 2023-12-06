package days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Day4 {

    public static void main(String[] args) {
        try {
            File myObj = new File("src/inputs/input4.txt");
            Scanner myReader = new Scanner(myObj);
            partTwo(myReader);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void partOne(Scanner myReader){
        int sum = 0;
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine().replace("  ", " ");
            String allNumbers = data.split(":")[1];
            String[] winningNumbers = allNumbers.split("\\|")[0].split(" ");
            Set<String> winningSet = Arrays.stream(winningNumbers).collect(Collectors.toSet());
            String[] participantNumbers = allNumbers.split("\\|")[1].split( " ");

            int totalWinningNumbers = getWinningNumbers(winningSet, participantNumbers);
            long individualResult = calculateIndividualResult(totalWinningNumbers);
            sum+=individualResult;
        }
        myReader.close();
        System.out.println("RESULT: " + sum);
    }

    private static void partTwo(Scanner myReader){
        Map<Integer, Integer> copiesOfCard = new HashMap<>(){{
            put(1, 1);
        }};
        int sum = 0;
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine().replace("  ", " ").replace("  ", " ");
            String allNumbers = data.split(":")[1];
            String currentGameId = data.split(":")[0].split(" ")[1];
            String[] winningNumbers = allNumbers.split("\\|")[0].split(" ");
            Set<String> winningSet = Arrays.stream(winningNumbers).collect(Collectors.toSet());
            String[] participantNumbers = allNumbers.split("\\|")[1].split( " ");

            int totalWinningNumbers = getWinningNumbers(winningSet, participantNumbers);
            addCopies(copiesOfCard, currentGameId, totalWinningNumbers);
        }
        for(int totalCopies: copiesOfCard.values()){
            sum+= totalCopies;
        }
        System.out.println("RESULT: " + sum);
        myReader.close();
    }
    private static int getWinningNumbers(Set winningSet, String[] participantNumbers){
        return Arrays.stream(participantNumbers)
                .filter(number -> winningSet.contains(number) && number != "").collect(Collectors.toList()).size();
    }

    private static long calculateIndividualResult(int totalWinningNumbers){
        if(totalWinningNumbers == 0){
            return 0;
        }
        long result = 1;
        for(int i = 1; i < totalWinningNumbers; i++){
            result = result * 2;
        }
        return result;
    }

    private static void addCopies(Map<Integer, Integer> copiesOfCard, String currentGameId, int totalWinningNumbers){
        int currentIdNumber = Integer.parseInt(currentGameId);
        int numberOfCopies = copiesOfCard.getOrDefault(currentIdNumber, 0);

        if(currentIdNumber != 1){
            copiesOfCard.put(currentIdNumber, numberOfCopies + 1);
            numberOfCopies+=1;
        }
        for(int i = 1; i <=  totalWinningNumbers; i++){
            int currentCopies = copiesOfCard.getOrDefault(currentIdNumber + i, 0);
            copiesOfCard.put(currentIdNumber + i, currentCopies + numberOfCopies);
        }
    }

}
