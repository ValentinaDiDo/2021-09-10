/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.yelp;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.yelp.model.Business;
import it.polito.tdp.yelp.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	Graph<Business, DefaultWeightedEdge> grafo;
	private boolean grafoCreato = false;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnDistante"
    private Button btnDistante; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalcolaPercorso"
    private Button btnCalcolaPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtX2"
    private TextField txtX2; // Value injected by FXMLLoader

    @FXML // fx:id="cmbCitta"
    private ComboBox<String> cmbCitta; // Value injected by FXMLLoader

    @FXML // fx:id="cmbB1"
    private ComboBox<Business> cmbB1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbB2"
    private ComboBox<?> cmbB2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    void doCreaGrafo(ActionEvent event) {
    		if(this.cmbCitta.getValue()==null) {
    			txtResult.setText("Per favore seleziona una città");

    		}else {
    			String citta = this.cmbCitta.getValue();
        			
    			txtResult.appendText("\nCreazione grafo in corso...\n");
    			this.model.creaGrafo(citta);
    			
    			this.grafo = this.model.getGrafo();
    			this.grafoCreato = true;
    			
    			txtResult.setText("GRAFO CREATO\n");
    			txtResult.appendText("\nVERTICI: "+this.grafo.vertexSet().size());
    			txtResult.appendText("\nARCHI: "+this.grafo.edgeSet().size());
    			
    			//POPOLO LISTA BUSINESS
    			List<Business> business = new ArrayList<>(this.grafo.vertexSet());
    			
    			Collections.sort(business);
    			this.cmbB1.getItems().clear();
    			this.cmbB1.getItems().addAll(business);
    		}	
    }

    @FXML
    void doCalcolaLocaleDistante(ActionEvent event) {
    	if(this.grafoCreato == false) {
    		txtResult.setText("DEVI PRIMA CREARE IL GRAFO");
    	}else {
    		Business scelto = this.cmbB1.getValue();
    		if(scelto == null) {
    			txtResult.appendText("\nSELEZIONA UN BUSINESS");
    		}else {
    			
    			double dMax = 0.0;
    			Business vicinoMax = null;
    			
    			for(Business b : Graphs.neighborListOf(this.grafo, scelto)) {
    				double d =  this.grafo.getEdgeWeight(this.grafo.getEdge(b, scelto));
    				if( d > dMax) {
    					vicinoMax = b;
    					dMax = d;
    				}
    			}
    			
    			txtResult.setText("LOCALE PIU' DISTANTE DA: "+scelto.toString());
    			txtResult.appendText("\n"+vicinoMax.toString()+" DISTANZA: "+dMax+" Km");
    		}
    	}
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {

    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDistante != null : "fx:id=\"btnDistante\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX2 != null : "fx:id=\"txtX2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCitta != null : "fx:id=\"cmbCitta\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbB1 != null : "fx:id=\"cmbB1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbB2 != null : "fx:id=\"cmbB2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	this.cmbCitta.getItems().clear();
    	this.cmbCitta.getItems().addAll(this.model.getCities());
    }
}
