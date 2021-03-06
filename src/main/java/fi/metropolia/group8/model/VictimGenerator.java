package fi.metropolia.group8.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Class for generating randomized victim objects. Creates a list of GeneratedVictim objects which are displayed
 * as a list of potential debtors for the user. User can issue loans directly to the generated victims after which the
 * actual victim and loan objects are created and saved to the database.
 */
public class VictimGenerator {

    private List<String> firstNames = null;
    private List<String> lastNames = null;
    private List<String> addresses = null;
    private List<String> streetType = Arrays.asList(" Street", " Drive", " Avenue", " Road");

    // TODO replace value lists with properly generated lists
    private List<Integer> loanValues = Arrays.asList(15, 25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700);
    private List<Integer> interestValues = Arrays.asList(10, 15, 20, 25, 30, 35, 40);

    private final ObservableList<GeneratedVictim> generatedVictimList = FXCollections.observableArrayList();
    private final ObjectProperty<GeneratedVictim> currentGeneratedVictim = new SimpleObjectProperty<>(null);
    public ObjectProperty<GeneratedVictim> currentVictimProperty() {
        return currentGeneratedVictim;
    }
    public final GeneratedVictim getCurrentVictim() { return currentVictimProperty().get(); }
    public final void setCurrentVictim(GeneratedVictim generatedVictim) {
        currentVictimProperty().set(generatedVictim);
    }

    private static VictimGenerator instance;

    public VictimGenerator() {
    }

    /**
     * Retrieves the global instance
     *
     * @return returns the singleton instance
     */
    public static VictimGenerator getInstance() {
        if (instance == null) {
            instance = new VictimGenerator();
        }
        return instance;
    }


    /**
     * Returns the current randomly generated list of victim objects.
     *
     * @return returns a list of Victim objects
     */
    public ObservableList<GeneratedVictim> getGeneratedVictimList() {
        return generatedVictimList;
    }

    // temporary delete
    public void deleteVictim(GeneratedVictim victim){ generatedVictimList.remove(victim); }

    /**
     * Builds lists of data from .txt files. Used for generating randomized victim objects.
     */
    public void readFiles() {
        try (Scanner sc = new Scanner(new FileInputStream("src/main/resources/fi/metropolia/group8/data/firstnames.txt"), StandardCharsets.UTF_8)) {
            firstNames = new ArrayList<>();
            while (sc.hasNextLine()) {
                firstNames.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try (Scanner sc = new Scanner(new FileInputStream("src/main/resources/fi/metropolia/group8/data/lastnames.txt"), StandardCharsets.UTF_8)) {
            lastNames = new ArrayList<>();
            while (sc.hasNextLine()) {
                lastNames.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try (Scanner sc = new Scanner(new FileInputStream("src/main/resources/fi/metropolia/group8/data/address.txt"), StandardCharsets.UTF_8)) {
            addresses = new ArrayList<>();
            while (sc.hasNextLine()) {
                addresses.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generate a list of randomized victim objects. Picks values randomly from data lists.
     *
     * @param listSize number of victim objects to be created
     */
    public void generateVictimList(int listSize) {
        readFiles();
        // Clears the previous generated list
        generatedVictimList.clear();
        for (int i = 0; i < listSize; i++) {
            // Generates a random first + last name combination
            String rngName = firstNames.get(new Random().nextInt(firstNames.size() - 1)) + " " + lastNames.get(new Random().nextInt(lastNames.size() - 1));
            // Generates a random address from a random street number + random street name + random street suffix
            String rngAddress = new Random().nextInt(90) + " " + addresses.get(new Random().nextInt(addresses.size() - 1)) + streetType.get(new Random().nextInt(streetType.size() - 1));
            // Picks a random loan value from the value list
            int rngValue = loanValues.get(new Random().nextInt(loanValues.size() - 1));
            // Picks a random interest value from the value list
            int rngInterest = interestValues.get(new Random().nextInt(interestValues.size() - 1));

            // Creates the GeneratedVictim object using the random values
            GeneratedVictim generatedVictim = new GeneratedVictim(
                    rngName,
                    rngAddress,
                    rngValue,
                    rngInterest,
                    DataModel.getInstance().getCurrentUser().getCurrentDate(),
                    DataModel.getInstance().getCurrentUser().getCurrentDate().plusDays(14)
            );
            // Adds the created object to the list
            generatedVictimList.addAll(generatedVictim);
        }
    }


}
