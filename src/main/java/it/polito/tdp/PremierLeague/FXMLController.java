
/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Migliore;
import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Simulator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	private Simulator sim;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnGiocatoreMigliore"
    private Button btnGiocatoreMigliore; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMatch"
    private ComboBox<Match> cmbMatch; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	Match match = cmbMatch.getValue();
    	txtResult.clear();
    	String msg = model.creaGrafo(match);
    	txtResult.appendText(msg);
    	
    }

    @FXML
    void doGiocatoreMigliore(ActionEvent event) {  
    	
    	Match match = cmbMatch.getValue();
    	Migliore msg = model.getBest(match);
    	txtResult.appendText("Giocatore migiore: \n");
    	txtResult.appendText(msg.toString());
    	
    }
    
    @FXML
    void doSimula(ActionEvent event) {
    	
	    	String ns = this.txtN.getText();
	    	Match m = this.cmbMatch.getValue();
	    	try {
	    		int N = Integer.parseInt(ns);
	    		this.sim.init(m, N);
	    		this.sim.run();
	    		this.txtResult.appendText("\n\n"+m.getTeamHomeNAME()+" "+this.sim.gethGoal()+"-"+this.sim.getaGoal()+" "+m.getTeamAwayNAME());
	    		this.txtResult.appendText("\nEspulsioni "+m.getTeamHomeNAME()+": "+this.sim.gethReds());
	    		this.txtResult.appendText("\nEspulsioni "+m.getTeamAwayNAME()+": "+this.sim.getaReds());
	    	}
	    	catch(NumberFormatException nfe) {
	    		this.txtResult.setText("Inserire un intero");
	    		return;
	    	}
    	}


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnGiocatoreMigliore != null : "fx:id=\"btnGiocatoreMigliore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMatch != null : "fx:id=\"cmbMatch\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.cmbMatch.getItems().addAll(model.getAllMatch());
    }
}
