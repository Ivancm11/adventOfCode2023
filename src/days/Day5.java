package days;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.*;

public class Day5 {
    static TreeMap<Long, Long> seedToSoil = new TreeMap<>();
    static TreeMap<Long, Long> soilToFertilizer = new TreeMap<>();
    static TreeMap<Long, Long> fertilizerToWater = new TreeMap<>();
    static TreeMap<Long, Long> waterToLight = new TreeMap<>();
    static TreeMap<Long, Long> lightToTemperature = new TreeMap<>();
    static TreeMap<Long, Long> temperatureToHumidity = new TreeMap<>();
    static TreeMap<Long, Long> humidityToLocation = new TreeMap<>();
    static TreeMap<Long, Long> currentTreePopulating = new TreeMap<>();
    static List<Long> seeds = new ArrayList<>();
    static Long smallestLocation = Long.MAX_VALUE;
    public static void main(String[] args) {
        try {
            File myObj = new File("/Users/icorrea/IdeaProjects/AdventOfCode2023/src/inputs/input5.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine().replace("  ", " ");
                String typeOfMap = line.split(":")[0].replace(" map","");
                TreeMap<Long, Long> mapToPopulate = getTree(typeOfMap);

                if(typeOfMap.equals("seeds")){
                    String[] mySeeds = line.split(":")[1].split(" ");
                   /* for(String seed : mySeeds){
                        if(!seed.equals("")){
                            seeds.add(Long.parseLong(seed));
                        }
                    }*/

                    for(int i = 1; i < mySeeds.length; i = i +2){
                        long seedStart = Long.parseLong(mySeeds[i]);
                        long seedsRange = Long.parseLong(mySeeds[i+1]);

                        for(long j = seedStart; j < seedStart + seedsRange; j++){
                            seeds.add(j);
                        }
                    }
                }
                if(null == mapToPopulate && !typeOfMap.equals("seeds") && !line.equals("")){
                    String[] numberInfo = line.split(" ");
                    long rangeStart = Long.parseLong(numberInfo[0]);
                    long sourceRangeStart = Long.parseLong(numberInfo[1]);
                    long rangeLength = Long.parseLong(numberInfo[2]);

                    populateMap(rangeStart, sourceRangeStart, rangeLength);
                }
                else{
                    if(typeOfMap.equals("") && null != currentTreePopulating){
                        populateLastEntry();
                    }
                    currentTreePopulating = mapToPopulate;
                    if(null != currentTreePopulating){
                        currentTreePopulating.put(0L, 0L);
                    }
                }

            }
            populateLastEntry();
            for(long seed: seeds){
                smallestLocation = Math.min(smallestLocation, getLocation(seed));
            }
            myReader.close();
            System.out.println("RESULT: " + smallestLocation);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static TreeMap<Long, Long> getTree(String treeType){
        switch (treeType){
            case "seed-to-soil":
                return seedToSoil;
            case "soil-to-fertilizer":
                return soilToFertilizer;
            case "fertilizer-to-water":
                return fertilizerToWater;
            case "water-to-light":
                return waterToLight;
            case "light-to-temperature":
                return lightToTemperature;
            case "temperature-to-humidity":
                return temperatureToHumidity;
            case "humidity-to-location":
                return humidityToLocation;
        }
        return null;
    }

    private static void populateMap(long rangeStart, long sourceRangeStart, long rangeLength){
        currentTreePopulating.put(sourceRangeStart, rangeStart);
        currentTreePopulating.put(sourceRangeStart + rangeLength - 1, rangeStart + rangeLength - 1);
    }

    /*private static void calculateSeedsPartOne(String typeOfMap){
        if(typeOfMap.equals("seeds")){
            String[] mySeeds = line.split(":")[1].split(" ");
            for(String seed : mySeeds){
                if(!seed.equals("")){
                    seeds.add(Long.parseLong(seed));
                }
            }
        }
    }*/

    private static void populateLastEntry(){
        long lastKey = currentTreePopulating.lastEntry().getKey();
        currentTreePopulating.put(lastKey + 1, lastKey + 1);
    }

    private static long getMappedValue(TreeMap<Long, Long> treeMap, long key){
        BigInteger numOne = BigInteger.valueOf(treeMap.floorEntry(key).getValue());
        BigInteger numTwo = BigInteger.valueOf(key - treeMap.floorEntry(key).getKey());
        BigInteger result = numOne.add(numTwo);
        return result.longValue();
    }
    private static long getLocation(long seed){
        long soil = getMappedValue(seedToSoil, seed);
        long fertilizer = getMappedValue(soilToFertilizer, soil);
        long water = getMappedValue(fertilizerToWater, fertilizer);
        long light = getMappedValue(waterToLight, water);
        long temperature = getMappedValue(lightToTemperature, light);
        long humidity = getMappedValue(temperatureToHumidity, temperature);
        return getMappedValue(humidityToLocation, humidity);
    }
}
