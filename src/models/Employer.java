package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Employer {
    private static Employer employer;
    private final MySQLConnection cnx = new MySQLConnection();
    private int congeRestants;
    private int joursRestants;
    private int nombreJours;
    private Date dateDebut;
    private Date dateFin;

    public Employer(String numEmp, String nom, String prenom, String poste, int nombreJours, int i, int joursLeaveRestants, Date dateDemande, Date dateRetour) {
        this.numEmp = Integer.parseInt(numEmp);
        this.nom = nom;
        this.prenom = prenom;
        this.poste = poste;
        this.nombreJours = nombreJours;
        this.congeRestants = i;
        this.joursRestants = joursLeaveRestants;
        this.dateDebut = dateDemande;
        this.dateFin = dateRetour;
    }

    public Employer(String numEmp, String nom, String prenom, String poste, int nombreJours, int joursRestants, Date dateDebut, Date dateFin) {
        this.numEmp = Integer.parseInt(numEmp);
        this.nom = nom;
        this.prenom = prenom;
        this.poste = poste;
        this.nombreJours = nombreJours;
        this.joursRestants = joursRestants;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public int getCongeRestants() {
        return congeRestants;
    }

    public void setCongeRestants(int congeRestants) {
        this.congeRestants = congeRestants;
    }

    public int getNombreJours() {
        return nombreJours;
    }

    public void setNombreJours(int nombreJours) {
        this.nombreJours = nombreJours;
    }

    public int getJoursRestants() {
        return joursRestants;
    }

    public void setJoursRestants(int joursRestants) {
        this.joursRestants = joursRestants;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }
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

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
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

    public List<Employer> getEmployeesOnLeaveNow() throws SQLException {

        System.out.println("JE SUIS DANS LA FUNCTION GETEMPLOYES ONLEAVE NOW");
        List<Employer> employeesOnLeave = new ArrayList<>();

        // Obtenir la date actuelle
        LocalDate currentDate = LocalDate.now();

        System.out.println(currentDate);
        // Effectuer la requête avec une jointure pour obtenir les employés en congé à la date actuelle
        String query = "SELECT E.numEmp, E.nom, E.prenom, E.poste, C.nbrjr, C.dateDemande, C.dateRetour " +
                "FROM EMPLOYE E " +
                "INNER JOIN CONGE C ON E.numEmp = C.numEmp " +
                "WHERE  \"" + currentDate + "\" BETWEEN C.dateDemande AND C.dateRetour";
        PreparedStatement statement = MySQLConnection.getConnection().prepareStatement(query);
        // statement.setString(1, String.valueOf(currentDate));
        ResultSet resultSet = statement.executeQuery();

        // Parcourir les résultats de la requête et créer les objets Employer correspondants
        while (resultSet.next()) {
            String numEmp = resultSet.getString("numEmp");
            String nom = resultSet.getString("nom");
            String prenom = resultSet.getString("prenom");
            String poste = resultSet.getString("poste");
            int nombreJours = resultSet.getInt("nbrjr");
            Date dateDemande = resultSet.getDate("dateDemande");
            Date dateRetour = resultSet.getDate("dateRetour");

            String temp = dateRetour.toString();
            String[] parts = temp.split("-");
            LocalDate localDateRetour = LocalDate.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));

            temp = dateDemande.toString();
            String[] part = temp.split("-");
            LocalDate localDateDemande = LocalDate.of(Integer.parseInt(part[0]), Integer.parseInt(part[1]), Integer.parseInt(part[2]));

            long diffDaysOfDateRAndDateD = ChronoUnit.DAYS.between(localDateDemande, localDateRetour);
            System.out.println("La difference entre la date du demande est la date du retour est de " + diffDaysOfDateRAndDateD);

            long diffDays = ChronoUnit.DAYS.between(localDateDemande, currentDate);
            System.out.println("La difference entre la date du demande est la date d'aujourd'hui est de " + diffDays);

            int joursRestants = Math.toIntExact(diffDaysOfDateRAndDateD - diffDays);
            // Créer l'objet Employer et l'ajouter à la liste des employés en congé
            Employer employee = new Employer(numEmp, nom, prenom, poste, nombreJours, 30 - nombreJours, joursRestants, dateDemande, dateRetour);
            System.out.println("YES");
            employeesOnLeave.add(employee);
        }

        return employeesOnLeave;


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
        String query = "SELECT numEmp, Nom, Prenom FROM EMPLOYE WHERE Nom LIKE ? OR Prenom LIKE ?";
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

    public List<Employer> getAbsentEmployeesForDate(Date startDate, Date endDate) throws SQLException {
        List<Employer> employesAbsents = new ArrayList<>();
        String query = "SELECT e.numEmp, e.Nom, e.Prenom FROM EMPLOYE e " +
                "INNER JOIN POINTAGE p ON e.numEmp = p.numEmp " +
                "WHERE DATE(p.datePointage) BETWEEN ? AND ? AND p.pointage = 'non'";

        PreparedStatement statement = MySQLConnection.getConnection().prepareStatement(query);
        statement.setDate(1, new java.sql.Date(startDate.getTime()));
        statement.setDate(2, new java.sql.Date(endDate.getTime()));

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


    public Comparable<String> getAbsenceDate() {
        return null;
    }
}
