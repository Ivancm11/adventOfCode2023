package days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day2 {

    private static Map<String, Integer> initialConfiguration = new HashMap<>(){{
        put("red", 12);
        put("green", 13);
        put("blue", 14);
    }};
    private static Map<String, Integer> initialConfiguration2 = new HashMap<>(){{
        put("red", 0);
        put("green", 0);
        put("blue", 0);
    }};

    public static void mainOne(String[] args) {
        try {
            File myObj = new File("src/inputs/input2.txt");
            Scanner myReader = new Scanner(myObj);
            int sum = 0;
            while (myReader.hasNextLine()) {
                Map<String, Integer> remainingColors = new HashMap<>();
                remainingColors.putAll(initialConfiguration);
                Boolean isValidGame = true;
                String game = myReader.nextLine();
                String justTurns = game.split(":")[1];
                String gameId = game.split(":")[0].split(" ")[1];
                String[] everyTurn = justTurns.split(";");

                for(String turn : everyTurn){
                    remainingColors = new HashMap<>();
                    remainingColors.putAll(initialConfiguration);
                    String[] singleColor = turn.split(",");
                    for(String color : singleColor){
                        String[] valueAndColor = color.split(" ");
                        remainingColors.put(valueAndColor[2], remainingColors.get(valueAndColor[2]) - Integer.valueOf(valueAndColor[1]));

                        if( remainingColors.get(valueAndColor[2]) < 0){
                            System.out.println(remainingColors.get(valueAndColor[2]));
                            isValidGame = false;
                            break;
                        }

                    }
                }

                if(isValidGame){
                    System.out.println("VALID GAME: " + gameId);
                    sum+=Integer.valueOf(gameId);
                }

                System.out.println(game);
            }
            System.out.print("FINAL SUM: " + sum);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            File myObj = new File("src/inputs/input2.txt");
            Scanner myReader = new Scanner(myObj);
            int sum = 0;
            while (myReader.hasNextLine()) {
                Map<String, Integer> minimumColors = new HashMap<>();
                minimumColors.putAll(initialConfiguration2);
                String game = myReader.nextLine();
                String justTurns = game.split(":")[1];
                String[] everyTurn = justTurns.split(";");

                for(String turn : everyTurn){
                    String[] singleColor = turn.split(",");
                    for(String color : singleColor){
                        String[] valueAndColor = color.split(" ");
                        int minValue =  Math.max(minimumColors.get(valueAndColor[2]), Integer.valueOf(valueAndColor[1]));
                        minimumColors.put(valueAndColor[2], minValue);
                        System.out.println(minimumColors.get(valueAndColor[2]));
                    }
                }

                int power = 1;
                for(Integer colorValue : minimumColors.values()){
                    // System.out.println("Color " + colorValue);
                    power = power * colorValue;
                }
                // System.out.println(power);
                sum+=power;
                System.out.println(game);
            }
            System.out.print("FINAL SUM: " + sum);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
