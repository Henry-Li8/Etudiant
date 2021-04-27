package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EdudiantController implements Initializable {

	@FXML
	private TableColumn<Etudiant, String> prenomColumn;

	@FXML
	private TextField txtPrenom;

	@FXML
	private TableView<Etudiant> etudiantsTable;

	@FXML
	private TextField txtAge;

	@FXML
	private Button btnEffacer;

	@FXML
	private TableColumn<Etudiant, Double> deptColumn;

	@FXML
	private Button btnAge;

	@FXML
	private TableColumn<Etudiant, String> ageColumn;

	@FXML
	private ComboBox<String> cboDept;

	@FXML
	private Button btnRecommencer;

	@FXML
	private Button btnModifier;

	@FXML
	private TableColumn<Etudiant, String> nomColumn;

	@FXML
	private TextField txtNom;


	private ObservableList<String> list = (ObservableList<String>) FXCollections.observableArrayList("Sciences", "Droit", "Medcine");

	public ObservableList <Etudiant> etudiantData = FXCollections.observableArrayList();

	public ObservableList <Etudiant> getetudiantData(){

		return etudiantData;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		cboDept.setItems(list);
		prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
		nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
		deptColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
		ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
		etudiantsTable.setItems(etudiantData);

		btnModifier.setDisable(true);
		btnEffacer.setDisable(true);
		btnRecommencer.setDisable(true);

		showEtudiants(null);
		etudiantsTable.getSelectionModel().selectedItemProperty().addListener((
				observable, oldValue, newValue)-> showEtudiants(newValue));


	}

	@FXML
	void ajouter() {
		
		if (noEmptyInput()) {
		Etudiant tmp = new Etudiant ();
		tmp = new Etudiant();
		tmp.setNom(txtNom.getText());
		tmp.setPrenom(txtPrenom.getText());
		tmp.setAge(Double.parseDouble(txtAge.getText()));
		tmp.setDepartment(cboDept.getValue());
		etudiantData.add(tmp);
		clearFields();
		}


	}

	@FXML
	void clearFields() {
		cboDept.setValue(null);
		txtNom.setText("");
		txtPrenom.setText("");
		txtAge.setText("");
	}

	@FXML
	public void showEtudiants(Etudiant etudiant) {
		if (etudiant != null) {
			cboDept.setValue(etudiant.getDepartment());
			txtNom.setText(etudiant.getNom());;
			txtPrenom.setText(etudiant.getPrenom());
			txtAge.setText(Double.toString(etudiant.getAge()));
			btnModifier.setDisable(false);
			btnEffacer.setDisable(false);
			btnRecommencer.setDisable(false);
		}
		else {
			clearFields();
		}
	}
	
	@FXML
	public void updateEtudiant() {
		
		if(noEmptyInput()) {
		Etudiant etudiant = etudiantsTable.getSelectionModel().getSelectedItem();
		etudiant.setNom(txtNom.getText());
		etudiant.setPrenom(txtPrenom.getText());
		etudiant.setAge(Double.parseDouble(txtAge.getText()));
		etudiant.setDepartment(cboDept.getValue());
		etudiantsTable.refresh();
		}
		
	}
	
	public void deleteEtudiant() {

		
		int selectedIndex = etudiantsTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Effacer");
			alert.setContentText("confirmer la suppression");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK)
				etudiantsTable.getItems().remove(selectedIndex);
		}
	}
	
	private boolean noEmptyInput() {
		String errorMessage = "";
		if (txtNom.getText() == null||txtNom.getText().length()==0){
			errorMessage+="Le champ nom ne doit pas etre vide \n";
		}
		if (txtPrenom.getText() == null||txtNom.getText().length()==0){
			errorMessage+="Le champ prenom ne doit pas etre vide \n";
		}
		if (txtAge.getText() == null||txtNom.getText().length()==0){
			errorMessage+="Le champ age ne doit pas etre vide \n";
		}
		if (cboDept.getValue() == null||txtNom.getText().length()==0){
			errorMessage+="Le champ departement ne doit pas etre vide \n";
		}
		
		if (errorMessage.length()==0) {
			return true;
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Champs manquants");
			alert.setHeaderText("Completer les champs manquants");
			alert.setContentText(errorMessage);
			alert.showAndWait();
			return false;
		}
		
	}
	@FXML
	void handleStats(){
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("AgeStat.FXML"));
			AnchorPane pane = loader.load();
			Scene scene = new Scene (pane);
			AgeStat agestat = loader.getController();
			agestat.SetStats(etudiantData);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Statistiques");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public File getEtudiantFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(Main.class);
		String filePath = prefs.get("filePath", null);
		if (filePath != null) {
			return new File(filePath);
		}else {
			return null;
		}
	}
	public void setEtudiantFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(Main.class);
		if (file != null) {
			prefs.put("filePath", file.getPath());
		}else {
			prefs.remove("filePath");
		}
	}
	public void loadEtudiantDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(EtudiantListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();
			
			EtudiantListWrapper wrapper = (EtudiantListWrapper) um.unmarshal(file);
			etudiantData.clear();
			etudiantData.addAll(wrapper.getEtudiants());
			setEtudiantFilePath(file);
			
			Stage pStage = (Stage) etudiantsTable.getScene().getWindow();
			pStage.setTitle(file.getName());
			
		} catch (Exception e) {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("les donnees n'ont pas ete trouvees");
			alert.setContentText("Les donnees no pouvaient pas etre trouvees dans le fichier : \n" + file.getPath());
			alert.showAndWait(); 
		}
	}
	
	 public void saveEtudiantDataToFile(File file) {
		 try {
			 JAXBContext context = JAXBContext.newInstance(EtudiantListWrapper.class);
			 Marshaller m = context.createMarshaller();
			 m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			 EtudiantListWrapper wrapper = new EtudiantListWrapper();
			 wrapper.setEtudiants(etudiantData);
			 
			 m.marshal(wrapper, file);
			 setEtudiantFilePath(file);
			 Stage pStage = (Stage) etudiantsTable.getScene().getWindow();
			 pStage.setTitle(file.getName());
			 
		 }catch (Exception e) {
			 Alert alert = new Alert(AlertType.ERROR);
			 alert.setTitle("Erreur");
			 alert.setHeaderText("Donnees no sauvegardees");
			 alert.setContentText("Les donnees ne pouvait pas etre sauvegardees dans le fichier: \n" + file.getPath());
			 alert.showAndWait();
		 }
	 }
	 
	 @FXML
	 private void handleNew() {
		 getetudiantData().clear();
		 setEtudiantFilePath(null);
		 
	 }
	 
	 @FXML
	 private void handleOpen() {
		 FileChooser fileChooser = new FileChooser();
		 FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		 
		 fileChooser.getExtensionFilters().add(extFilter);
		 File file = fileChooser.showOpenDialog(null);
		 
		 if (file != null) {
			 loadEtudiantDataFromFile(file);
		 }
	 }
	 @FXML
	 private void handleSave() {
		 File etudiantFile = getEtudiantFilePath();
		 if (etudiantFile != null) {
			 saveEtudiantDataToFile(etudiantFile);
			
		 }else {
			 handleSaveAs(); 
		 }
	 }
	 @FXML
	 private void handleSaveAs() {
		 FileChooser fileChooser = new FileChooser();
		 FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		 
		 fileChooser.getExtensionFilters().add(extFilter);
		 File file = fileChooser.showSaveDialog(null);
		 
		 if (file != null) {
			 if (!file.getPath().endsWith(".xml")){
				 file = new File(file.getPath()+ ".xml");
			 }
			 saveEtudiantDataToFile(file);
		 }
	 }
	 
	 @FXML
	 public void verifNum() {
		 txtAge.textProperty().addListener((observable, oldValue, newValue)->
		 {
			 if (!newValue.matches("^[0-9](\\.[0-9]+)?$")) {
				 txtAge.setText(newValue.replaceAll("[^\\d*\\.]", ""));
			 }
		 });
	 }
	
	
	
	
	
	
	
}
