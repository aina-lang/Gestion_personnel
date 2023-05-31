package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
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


    public List<Employer> getAbsentEmployeesForDate(Date date) throws SQLException {
        List<Employer> employesAbsents = new ArrayList<>();
        String query = "SELECT e.numEmp, e.Nom, e.Prenom FROM Employe e " +
                "INNER JOIN Conge c ON e.numEmp = c.numEmp " +
                "WHERE ? BETWEEN c.dateDemande AND c.dateRetour";

        PreparedStatement statement = MySQLConnection.getConnection().prepareStatement(query);
        statement.setDate(1, (java.sql.Date) date);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int numEmp = resultSet.getInt("numEmp");
            String nom = resultSet.getString("Nom");
            String prenom = resultSet.getString("Prenom");

            Employer employe = new Employer(numEmp, nom, prenom);
            employesAbsents.add(employe);
        }

        return employesAbsents;
    }

    public int getRemainingVacationDays(int numEmp) throws SQLException {
        String query = "SELECT SUM(nbrJour) AS totalConges FROM Conge WHERE numEmp = ?";
        PreparedStatement statement = MySQLConnection.getConnection().prepareStatement(query);
        statement.setInt(1, numEmp);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int totalConges = resultSet.getInt("totalConges");
            int remainingDays = 30 - totalConges; // Supposons que le nombre de congés par an est de 30 jours
            return remainingDays >= 0 ? remainingDays : 0;
        }

        return 0;
    }

    public List<Employer> searchEmployees(String keyword) throws SQLException {
        List<Employer> matchingEmployees = new ArrayList<>();
        String query = "SELECT numEmp, Nom, Prenom FROM Employe WHERE Nom LIKE ? OR Prenom LIKE ?";
        PreparedStatement statement = MySQLConnection.getConnection().prepareStatement(query);
        statement.setString(1, "%" + keyword + "%");
        statement.setString(2, "%" + keyword + "%");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int numEmp = resultSet.getInt("numEmp");
            String nom = resultSet.getString("Nom");
            String prenom = resultSet.getString("Prenom");

            Employer employe = new Employer(numEmp, nom, prenom);
            matchingEmployees.add(employe);
        }

        return matchingEmployees;
    }


}
