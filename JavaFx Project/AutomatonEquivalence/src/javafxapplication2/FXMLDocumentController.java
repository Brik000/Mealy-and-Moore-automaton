/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.automatons.MealyAutomaton;
import model.automatons.MooreAutomaton;

/**
 * FXML Controller class
 *
 * @author titi-
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField mealyStates1;
    @FXML
    private TextField mealyStates2;
    @FXML
    private TextField mealyInputs;
    @FXML
    private Button mealyBtn;
    @FXML
    private GridPane mealyGrid1;
    @FXML
    private GridPane mealyGrid2;
    @FXML
    private TextField mooreStates1;
    @FXML
    private TextField mooreStates2;
    @FXML
    private TextField mooreInputs;
    @FXML
    private Button mooreBtn;
    @FXML
    private GridPane mooreGrid1;
    @FXML
    private GridPane mooreGrid2;
    
    private TextField[][] itemsMealy1;
    private TextField[][] itemsMealy2;
    private TextField[][] itemsMoore1;
    private TextField[][] itemsMoore2;
    
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
        //Setup of moore listeners
        mooreStates1.textProperty().addListener((observable, oldValue, newValue) -> {
        //System.out.println("textfield changed from " + oldValue + " to " + newValue);
        if(newValue.matches("[0-9]*")){
            try{
                updateGrid(mooreGrid1, "moore", Integer.parseInt(newValue), Integer.parseInt(mooreInputs.getText()));
            }
            catch(Exception e){
                
            }
            
        }
        else{
            generateWarningAlert("Data input error", "The value '"+newValue+"' is not alphanumeric");
            mooreStates1.setText(oldValue);
        }
        });
        
        mooreStates2.textProperty().addListener((observable, oldValue, newValue) -> {
        //System.out.println("textfield changed from " + oldValue + " to " + newValue);
        if(newValue.matches("[0-9]*")){
            try{
              updateGrid(mooreGrid2, "moore", Integer.parseInt(newValue), Integer.parseInt(mooreInputs.getText()));  
            }
            catch(Exception e){
                
            }
            
        }
        else{
            generateWarningAlert("Data input error", "The value '"+newValue+"' is not alphanumeric");
            mooreStates2.setText(oldValue);
        }
        });
        
        mooreInputs.textProperty().addListener((observable, oldValue, newValue) -> {
        //System.out.println("textfield changed from " + oldValue + " to " + newValue);
        if(newValue.matches("[0-9]*")){
            try{
              updateGrid(mooreGrid1, "moore", Integer.parseInt(mooreStates1.getText()),Integer.parseInt(newValue));
            updateGrid(mooreGrid2, "moore", Integer.parseInt(mooreStates2.getText()),Integer.parseInt(newValue));
            }
            catch(Exception e){
                
            }
            
        }
        else{
            generateWarningAlert("Data input error", "The value '"+newValue+"' is not alphanumeric");
            mooreInputs.setText(oldValue);
        }
        });
        
        
        //Setup of mealy listener
        mealyStates1.textProperty().addListener((observable, oldValue, newValue) -> {
        //System.out.println("textfield changed from " + oldValue + " to " + newValue);
        if(newValue.matches("[0-9]*")){
            
            try{
            updateGrid(mealyGrid1, "mealy", Integer.parseInt(newValue), Integer.parseInt(mealyInputs.getText()));
            }
            catch(Exception e){
                
            }
            
        }
        else{
            generateWarningAlert("Data input error", "The value '"+newValue+"' is not alphanumeric");
            mealyStates1.setText(oldValue);
        }
        });
        
        mealyStates2.textProperty().addListener((observable, oldValue, newValue) -> {
        //System.out.println("textfield changed from " + oldValue + " to " + newValue);
        if(newValue.matches("[0-9]*")){
            try{
            updateGrid(mealyGrid2, "mealy", Integer.parseInt(newValue), Integer.parseInt(mealyInputs.getText()));
            }
            catch(Exception e){
                
            }
            
        }
        else{
            generateWarningAlert("Data input error", "The value '"+newValue+"' is not alphanumeric");
            mealyStates2.setText(oldValue);
        }
        });
        
        mealyInputs.textProperty().addListener((observable, oldValue, newValue) -> {
        //System.out.println("textfield changed from " + oldValue + " to " + newValue);
        if(newValue.matches("[0-9]*")){
            try{
            updateGrid(mealyGrid1, "mealy", Integer.parseInt(mealyStates1.getText()),Integer.parseInt(newValue));
            updateGrid(mealyGrid2, "mealy", Integer.parseInt(mealyStates2.getText()),Integer.parseInt(newValue));
            }
            catch(Exception e){
                
            }
            
        }
        else{
            generateWarningAlert("Data input error", "The value '"+newValue+"' is not alphanumeric");
            mealyInputs.setText(oldValue);
        }
        });
        
        mealyBtn.setOnAction((event)-> {
            try {
               String[] q1= generateMealy1States();
               String[] q2=generateMealy2States();
               String[] s1=generateMealy1Inputs();
               String[] s2=generateMealy2Inputs();
               String[] r1=generateMealy1Outputs();
               String[] r2=generateMealy2Outputs();
               MealyAutomaton m1=new MealyAutomaton("M1", q1, s1, r1, q1[0]);
               MealyAutomaton m2=new MealyAutomaton("M2", q2, s2, r2, q2[0]);
               loadMappingsMealy1(m1);
               loadMappingsMealy2(m2);
               generateWarningAlert("Results", "Is M1 equal to M2?: "+m1.equals(m2));
                
            } catch (Exception ex) {
                generateWarningAlert("Data input error", ex.getMessage());
            }
        });
        
        mooreBtn.setOnAction((event)-> {
            try {
               String[] q1= generateMoore1States();
               String[] q2=generateMoore2States();
               String[] s1=generateMoore1Inputs();
               String[] s2=generateMoore2Inputs();
               String[] r1=generateMoore1Outputs();
               String[] r2=generateMoore2Outputs();
               MooreAutomaton m1=new MooreAutomaton("M1", q1, s1, r1, q1[0]);
               MooreAutomaton m2=new MooreAutomaton("M2", q2, s2, r2, q2[0]);
               loadMappingsMoore1(m1);
               loadMappingsMoore2(m2);
               generateWarningAlert("Results", "Is M1 equal to M2?: "+m1.equals(m2));
                
            } catch (Exception ex) {
                ex.printStackTrace();
                generateWarningAlert("Data input error", ex.getMessage());
            }
        });
        
        updateGrid(mealyGrid1, "mealy", Integer.parseInt(mealyStates1.getText()),Integer.parseInt(mealyInputs.getText()));
        updateGrid(mealyGrid2, "mealy", Integer.parseInt(mealyStates2.getText()),Integer.parseInt(mealyInputs.getText()));
        updateGrid(mooreGrid1, "moore", Integer.parseInt(mooreStates1.getText()),Integer.parseInt(mooreInputs.getText()));
        updateGrid(mooreGrid2, "moore", Integer.parseInt(mooreStates2.getText()),Integer.parseInt(mooreInputs.getText()));
        
    }  
    /**
     * Creates machine grid according to size labels.
     */
    public void updateGrid(GridPane grid, String tipo,int numStates,int numInputs){
        int states=numStates;
        int inputs=numInputs;
        if(tipo.equals("moore")){
            inputs++;
        }
        TextField[][] items=new TextField[0][0];
        if(grid.equals(mealyGrid1)){
            itemsMealy1=new TextField[states+1][inputs+1];
            items=itemsMealy1;
        }
        else if(grid.equals(mealyGrid2)){
            itemsMealy2=new TextField[states+1][inputs+1];
            items=itemsMealy2;
        }
        else if(grid.equals(mooreGrid1)){
            itemsMoore1=new TextField[states+1][inputs+1];
            items=itemsMoore1;
        }
        else if(grid.equals(mooreGrid2)){
            itemsMoore2=new TextField[states+1][inputs+1];
            items=itemsMoore2;
        }
        
        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < items[0].length; j++) {
                items[i][j]=new TextField();
            }
        }
        
        items[0][0].setText("STATES\\INPUTS");
        items[0][0].editableProperty().set(false);
        if(tipo.equals("moore")){
            items[0][inputs].setText("Outs");
            items[0][inputs].editableProperty().set(false);
        }
        
        List<TextField> toAdd = new ArrayList<>();
    for (int column = 0; column < states+1; column++) {
        for (int row = 0; row < inputs+1; row++) {
            TextField current = items[column][row];

            // if it is null it wont be painted e.g. empty/blank item
            if (current != null) {
                toAdd.add(current);
                GridPane.setColumnIndex(current, row);
                GridPane.setRowIndex(current, column);
            }
        }
    }
    // add all at once for better performance
    grid.getChildren().setAll(toAdd);
    }
    /**
     * Sends a pop up alert.
     */
    public void generateWarningAlert(String title, String text){
        Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(text);
            alert.showAndWait();
    }
    /**
     * The next six methods are in charge on reading the data in the machines matrices
     * to create the states, input and output alphabets for the mealy automatons
     */
    public String[] generateMealy1States() throws Exception {
        String [] states=new String[itemsMealy1.length-1];
        for (int i = 1; i < states.length+1; i++) {
            states[i - 1] = itemsMealy1[i][0].getText();
            if(states[i-1].isEmpty()){
                throw new Exception("No input representation of machine 1 can be an empty string");
            }
        }
        System.out.println("Q1: "+String.join(",", states));
        if(areDistinct(states)){
            return states;
        }
        else{
            throw new Exception("All state representations of machine 1 are not unique");
        }
    }   
    public String[] generateMealy2States() throws Exception {
        String [] states=new String[itemsMealy2.length-1];
        for (int i = 1; i < states.length+1; i++) {
            states[i - 1] = itemsMealy2[i][0].getText();
            if(states[i-1].isEmpty()){
                throw new Exception("No input representation of machine 1 can be an empty string");
            }
        }
        System.out.println("Q2: "+String.join(",", states));
        if(areDistinct(states)){
            return states;
        }
        else{
            throw new Exception("All state representations of machine 2 are not unique");
        }
    }
    public String[] generateMealy1Inputs() throws Exception {
        String [] inputs=new String[itemsMealy1[0].length-1];
        for (int i = 1; i < inputs.length+1; i++) {
            inputs[i - 1] = itemsMealy1[0][i].getText();
            if(inputs[i-1].isEmpty()){
                throw new Exception("No input representation of machine 1 can be an empty string");
            }
        }
        System.out.println("S1: "+String.join(",", inputs));
        if(areDistinct(inputs)){
            return inputs;
        }
        else{
            throw new Exception("All input representations of machine 1 are not unique");
        }
    }
    public String[] generateMealy2Inputs() throws Exception {
        String [] inputs=new String[itemsMealy2[0].length-1];
        for (int i = 1; i < inputs.length+1; i++) {
            inputs[i - 1] = itemsMealy2[0][i].getText();
            if(inputs[i-1].isEmpty()){
                throw new Exception("No input representation of machine 2 can be an empty string");
            }
        }
        System.out.println("S2: "+String.join(",", inputs));
        if(areDistinct(inputs)){
            return inputs;
        }
        else{
            throw new Exception("All input representations of machine 2 are not unique");
        }
    }
    public String[] generateMealy1Outputs() throws Exception{
        ArrayList<String> outputs=new ArrayList<String>();
        for (int i = 1; i < itemsMealy1.length ; i++) {
            for (int j = 1; j < itemsMealy1[0].length; j++) {
                if(!itemsMealy1[i][j].getText().isEmpty()){
                    String[] split=itemsMealy1[i][j].getText().split("-");
                    if(split.length!=2){
                        throw new Exception("All transition state/output pairs of machine 1 \n have to be written in the format: Q-R");
                    }
                    else{
                        outputs.add(split[1]);
                    }
                    
                }
                else{
                    throw new Exception("No transition state/output pair can be empty for machine 1");
                }
            }
        }
        System.out.println("R1: "+String.join(",", getDistinct(outputs.toArray(new String[0]))));
        return getDistinct(outputs.toArray(new String[0]));
    }
    public String[] generateMealy2Outputs() throws Exception{
        ArrayList<String> outputs=new ArrayList<String>();
        for (int i = 1; i < itemsMealy2.length ; i++) {
            for (int j = 1; j < itemsMealy2[0].length; j++) {
                if(!itemsMealy2[i][j].getText().isEmpty()){
                    String[] split=itemsMealy2[i][j].getText().split("-");
                    if(split.length!=2){
                        throw new Exception("All transition state/output pairs of machine 2 \n have to be written in the format: Q-R");
                    }
                    else{
                        outputs.add(split[1]);
                    }
                    
                }
                else{
                    throw new Exception("No transition state/output pair can be empty for machine 2");
                }
            }
        }
        System.out.println("R2: "+String.join(",", getDistinct(outputs.toArray(new String[0]))));
        return getDistinct(outputs.toArray(new String[0]));
    }
     /**
     * The next two methods are in charge of reading the mealy machines matrices to
     * add the mappings(go to state and output) of each mealy machine
     */
    public void loadMappingsMealy1(MealyAutomaton m1) throws Exception{
        for (int i = 1; i < itemsMealy1.length ; i++) {
           for (int j = 1; j < itemsMealy1[0].length; j++) {
               String q=itemsMealy1[i][0].getText();
               String s=itemsMealy1[0][j].getText();
               String[] split=itemsMealy1[i][j].getText().split("-");
               m1.addMapping(q, s, split[0], split[1]);
            } 
        }
    }
    public void loadMappingsMealy2(MealyAutomaton m2) throws Exception{
        for (int i = 1; i < itemsMealy2.length ; i++) {
           for (int j = 1; j < itemsMealy2[0].length; j++) {
               String q=itemsMealy2[i][0].getText();
               String s=itemsMealy2[0][j].getText();
               String[] split=itemsMealy2[i][j].getText().split("-");
               m2.addMapping(q, s, split[0], split[1]);
            } 
        }
    } 
    /**
     * The next six methods are in charge on reading the data in the machines matrices
     * to create the states, input and output alphabets for the moore automatons
     */
    public String[] generateMoore1States() throws Exception {
        String [] states=new String[itemsMoore1.length-1];
        for (int i = 1; i < states.length+1; i++) {
            states[i - 1] = itemsMoore1[i][0].getText();
            if(states[i-1].isEmpty()){
                throw new Exception("No input representation of machine 1 can be an empty string");
            }
        }
        System.out.println("Q1: "+String.join(",", states));
        if(areDistinct(states)){
            return states;
        }
        else{
            throw new Exception("All state representations of machine 1 are not unique");
        }
    }   
    public String[] generateMoore2States() throws Exception {
        String [] states=new String[itemsMoore2.length-1];
        for (int i = 1; i < states.length+1; i++) {
            states[i - 1] = itemsMoore2[i][0].getText();
            if(states[i-1].isEmpty()){
                throw new Exception("No input representation of machine 1 can be an empty string");
            }
        }
        System.out.println("Q2: "+String.join(",", states));
        if(areDistinct(states)){
            return states;
        }
        else{
            throw new Exception("All state representations of machine 2 are not unique");
        }
    }
    public String[] generateMoore1Inputs() throws Exception {
        String [] inputs=new String[itemsMoore1[0].length-2];
        for (int i = 1; i < inputs.length+1; i++) {
            inputs[i - 1] = itemsMoore1[0][i].getText();
            if(inputs[i-1].isEmpty()){
                throw new Exception("No input representation of machine 1 can be an empty string");
            }
        }
        System.out.println("S1: "+String.join(",", inputs));
        if(areDistinct(inputs)){
            return inputs;
        }
        else{
            throw new Exception("All input representations of machine 1 are not unique");
        }
    }
    public String[] generateMoore2Inputs() throws Exception {
        String [] inputs=new String[itemsMoore2[0].length-2];
        for (int i = 1; i < inputs.length+1; i++) {
            inputs[i - 1] = itemsMoore2[0][i].getText();
            if(inputs[i-1].isEmpty()){
                throw new Exception("No input representation of machine 2 can be an empty string");
            }
        }
        System.out.println("S2: "+String.join(",", inputs));
        if(areDistinct(inputs)){
            return inputs;
        }
        else{
            throw new Exception("All input representations of machine 2 are not unique");
        }
    }
    public String[] generateMoore1Outputs() throws Exception{
        String [] outputs=new String[itemsMoore1.length-1];
        for (int i = 1; i < outputs.length+1; i++) {
            outputs[i - 1] = itemsMoore1[i][itemsMoore1[0].length-1].getText();
            if(outputs[i-1].isEmpty()){
                throw new Exception("No output representation of machine 1 can be an empty string");
            }
        }
        System.out.println("R1: "+String.join(",", getDistinct(outputs)));
        return getDistinct(outputs);
    }
    public String[] generateMoore2Outputs() throws Exception{
        String [] outputs=new String[itemsMoore2.length-1];
        for (int i = 1; i < outputs.length+1; i++) {
            outputs[i - 1] = itemsMoore2[i][itemsMoore2[0].length-1].getText();
            if(outputs[i-1].isEmpty()){
                throw new Exception("No output representation of machine 2 can be an empty string");
            }
        }
        System.out.println("R2: "+String.join(",", getDistinct(outputs)));
        return getDistinct(outputs);
    }
     /**
     * The next two methods are in charge of reading the moore machines matrices to
     * add the mappings(go to state and output) of each moore machine
     */
    public void loadMappingsMoore1(MooreAutomaton m1) throws Exception{
        for (int i = 1; i < itemsMoore1.length ; i++) {
           for (int j = 1; j < itemsMoore1[0].length-1; j++) {
               String q=itemsMoore1[i][0].getText();
               String s=itemsMoore1[0][j].getText();
               String q1=itemsMoore1[i][j].getText();
               String r=itemsMoore1[i][itemsMoore1[0].length-1].getText();
               m1.addOutput(q, r);
               m1.addTransition(q, s, q1);
            } 
        }
    }
    public void loadMappingsMoore2(MooreAutomaton m2) throws Exception{
        for (int i = 1; i < itemsMoore2.length ; i++) {
           for (int j = 1; j < itemsMoore2[0].length-1; j++) {
               String q=itemsMoore2[i][0].getText();
               String s=itemsMoore2[0][j].getText();
               String q1=itemsMoore2[i][j].getText();
               String r=itemsMoore2[i][itemsMoore2[0].length-1].getText();
               m2.addOutput(q, r);
               m2.addTransition(q, s, q1);
            } 
        }
    }
    
     /**
     * Tells if all the elements on a String array are unique
     * True if all are unique
     * False if not
     */
    public static boolean areDistinct(String arr[]) { 
        // Put all array elements in a HashSet 
        Set<String> s =  
           new HashSet<String>(Arrays.asList(arr)); 
  
        // If all elements are distinct, size of 
        // HashSet should be same array. 
        return (s.size() == arr.length); 
    }
     /**
     * Returns a String array containing the unique elements of
     * another string array
     */
    public static String[] getDistinct(String arr[]){
        Set<String> s =  
           new HashSet<String>(Arrays.asList(arr));
        return new ArrayList<String>(s).toArray(new String [0]);
    }
    
}
