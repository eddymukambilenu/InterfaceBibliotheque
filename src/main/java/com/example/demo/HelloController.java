package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloController {

    @FXML
    private TextField titreField;
    @FXML
    private TextField auteurField;
    @FXML
    private Button addButton;
    @FXML
    private Button listButton;
    @FXML
    private TableView<Livre> tableView;
    @FXML
    private TableColumn<Livre, Integer> idColumn;
    @FXML
    private TableColumn<Livre, String> titreColumn;
    @FXML
    private TableColumn<Livre, String> auteurColumn;

    private GestionDB gestionDB;

    @FXML
    public void initialize() {
        // Configuration des colonnes de la TableView
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        titreColumn.setCellValueFactory(cellData -> cellData.getValue().titreProperty());
        auteurColumn.setCellValueFactory(cellData -> cellData.getValue().auteurProperty());

        addButton.setOnAction(e -> addLivre());
        listButton.setOnAction(e -> listLivres());
    }

    public void setGestionDB(GestionDB gestionDB) {
        this.gestionDB = gestionDB;
    }

    private void addLivre() {
        String titre = titreField.getText();
        String auteur = auteurField.getText();
        if (!titre.isEmpty() && !auteur.isEmpty()) {
            try {
                gestionDB.insertLivre(titre, auteur);
                titreField.clear();
                auteurField.clear();
                // Actualise la liste des livres après ajout
                listLivres();
            } catch (Exception e) {
                e.printStackTrace();
                // Affiche une alerte utilisateur en cas d'erreur (facultatif)
            }
        }
    }

    private void listLivres() {
        ObservableList<Livre> livres = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM com.example.demo.Livre";
            ResultSet rs = gestionDB.getConnection().createStatement().executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String titre = rs.getString("titre");
                String auteur = rs.getString("auteur");
                livres.add(new Livre(id, titre, auteur));
            }
            tableView.setItems(livres);
        } catch (SQLException e) {
            e.printStackTrace();
            // Affiche une alerte utilisateur en cas d'erreur (facultatif)
        }
    }
}
