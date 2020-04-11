package fi.metropolia.group8.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class VictimGenerator {

    private List<String> firstNames = null;
    private List<String> lastNames = null;
    private List<String> addresses = null;
    private List<String> streetType = Arrays.asList(new String[]{" Street", " Drive", " Avenue"});
    private final ObservableList<Victim> generatedVictimList = FXCollections.observableArrayList();

    private static VictimGenerator instance;

    public VictimGenerator() { }

    /**
     * Retrieves the global instance
     * @return returns the singleton instance
     */
    public static VictimGenerator getInstance() {
        if(instance == null) {
            instance = new VictimGenerator();
        }
        return instance;
    }


    /**
     * Returns the current randomly generated list of victim objects.
     * @return returns a list of Victim objects
     */
    public ObservableList<Victim> getGeneratedVictimList() {
        return generatedVictimList;
    }

    /**
     * Builds lists of data from .txt files. Used for generating randomized victim objects.
     */
    public void readFiles() {
       try {
           Scanner scanner = new Scanner(new File("/..../resources/fi/metropolia/group8/data/firstnames.txt"));
           while(scanner.hasNextLine()) {
               firstNames.add(scanner.nextLine());
           }
       } catch (FileNotFoundException ex) {
           ex.printStackTrace();
       }

       try {
           Scanner scanner = new Scanner(new File("/..../resources/fi/metropolia/group8/data/lastnames.txt"));
           while(scanner.hasNextLine()) {
               lastNames.add(scanner.nextLine());
           }
       } catch (FileNotFoundException ex) {
           ex.printStackTrace();
       }

       try {
            Scanner scanner = new Scanner(new File("/..../resources/fi/metropolia/group8/data/address.txt"));
            while(scanner.hasNextLine()) {
                addresses.add(scanner.nextLine());
            }
       } catch (FileNotFoundException ex) {
            ex.printStackTrace();
       }
   }

    /**
     * Generate a list of randomized victim objects. Picks names randomly from .txt files.
     * @param listSize number of victim objects to be created
     */
   public void generateVictimList(int listSize) {
        readFiles();
        generatedVictimList.clear();
        for(int i = 0; i < listSize; i++) {
            String rngName = firstNames.get(new Random().nextInt(firstNames.size())) + " " + lastNames.get(new Random().nextInt(lastNames.size()));
            String rngAddress = new Random().nextInt(90 + 1)  + 1 + addresses.get(new Random().nextInt(addresses.size())) + streetType.get(new Random().nextInt(streetType.size()));
            Victim victim = new Victim(rngName, rngAddress, "placeholder description");
            generatedVictimList.addAll(victim);
        }
   }


}
