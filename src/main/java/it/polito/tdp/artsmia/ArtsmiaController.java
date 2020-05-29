package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.db.CoppiaArtisti;
import it.polito.tdp.artsmia.model.Artist;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	txtResult.clear();
    	List<CoppiaArtisti> coppie = this.model.getElencoCoppie();
    	
    	for(CoppiaArtisti c : coppie) {
    		txtResult.appendText(c.toString()+"\n");
    	}
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	int i = 0;
    	
    	try {
    		i = Integer.parseInt(txtArtista.getText());
    	} catch (NumberFormatException e) {
    		txtResult.appendText("Devi inserire un numero intero valido!");
    		return;
    	}
    	
    	List<Artist> artisti = this.model.getCamminoMax(i);
    	double d = 0;
    	
    	for(Artist a : artisti)
    		txtResult.appendText(a.toString()+"\n");
    	
    	d = this.model.getPesoArco(artisti.get(0), artisti.get(1));
    	
    	txtResult.appendText("Il numero di esposizioni per cui il percorso risulta massimo è: "+d);
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	
    	String s = boxRuolo.getValue();
    	if(s==null) {
    		txtResult.appendText("Seleziona un ruolo!\n");
    		return;
    	}
    	
    	this.model.generateGraph(s);
    	txtResult.appendText("Il grafo è stato creato!\n");
    	//txtResult.appendText(""+model.getNumVertici()+" ");
    	//txtResult.appendText(""+model.getNumArchi());
    }

    public void setModel(Model model) {
    	this.model = model;
    	
    	this.boxRuolo.getItems().setAll(this.model.listRoles());
    }

    
    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }
}
