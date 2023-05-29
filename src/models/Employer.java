package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Employer {
    private static Employer employer;
    private final MySQLConnection cnx = new MySQLConnection();
    private int numEmp;
    private String nom;
    private String prenom;
    private String poste;
    private float salaire;

    public Employer() {
    }

    public Employer(String nom, String prenom, String poste, float salaire) {
        this.nom = nom;
        this.prenom = prenom;
        this.poste = poste;
        this.salaire = salaire;
    }

    public Employer(int numEmp, String nom, String prenom, String poste, float salaire) {
        this.numEmp = numEmp;
        this.nom = nom;
        this.prenom = prenom;
        this.poste = poste;
        this.salaire = salaire;
    }

    public Employer(int id) {
        String query = "SELECT * FROM EMPLOYE WHERE numEmp=" + id;
        ResultSet resultSet = null;
        try {
            Statement statement = MySQLConnection.connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                setNumEmp(resultSet.getInt("numEmp"));
                setNom(resultSet.getString("nom"));
                setPrenom(resultSet.getString("prenom"));
                setPoste(resultSet.getString("poste"));
                setSalaire(resultSet.getFloat("salaire"));
            } else {
                System.out.println("Employe not found");
                System.exit(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Employer(int numEmp, String nom, String prenom) {
        this.numEmp = numEmp;
        this.nom = nom;
        this.prenom = prenom;
    }

    // Getters et Setters
    public int getNumEmp() {
        return numEmp;
    }

    public void setNumEmp(int numEmp) {
        this.numEmp = numEmp;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public float getSalaire() {
        return salaire;
    }

    public void setSalaire(float salaire) {
        this.salaire = salaire;
    }

    // Méthode pour calculer le salaire après déduction pour absence
    public void calculerSalaireApresAbsence(int montantDeduction) {
        salaire -= montantDeduction;
    }

    // Méthode pour créer un employé
    public void createEmployer(Employer employe) throws SQLException {
        String query = "INSERT INTO EMPLOYE (nom, prenom, poste, salaire) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = MySQLConnection.connection.prepareStatement(query);
        statement.setString(1, employe.nom);
        statement.setString(2, employe.prenom);
        statement.setString(3, employe.poste);
        statement.setFloat(4, employe.salaire);
        statement.executeUpdate();
    }

    // Méthode pour supprimer un employé
    public void deleteEmp(int numEmp) throws SQLException {
        String query = "DELETE FROM EMPLOYE WHERE numEmp=" + numEmp;
        PreparedStatement statement = MySQLConnection.connection.prepareStatement(query);
        try {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Méthode pour mettre à jour un employé
    public void updateEmployer(int numEmp, String nom, String prenom, String poste, float salaire) throws SQLException {
        String query = "UPDATE EMPLOYE SET nom=?, prenom=?, poste=?, salaire=? WHERE numEmp=?";
        PreparedStatement statement = MySQLConnection.connection.prepareStatement(query);
        statement.setString(1, nom);
        statement.setString(2, prenom);
        statement.setString(3, poste);
        statement.setFloat(4, salaire);
        statement.setInt(5, numEmp);
        statement.executeUpdate();
    }

    public Employer rechercherEmploye(int id) {
        Employer employer = null;
        return employer;
    }

    public List<Employer> listerEmployer() {
        List<Employer> employers = new ArrayList<>();
        Employer employer = null;
        String query = "SELECT * FROM EMPLOYE";
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = MySQLConnection.connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                employer = new Employer(resultSet.getInt("numEmp"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("poste"), resultSet.getFloat("salaire"));
                employers.add(employer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employers;
    }
}
