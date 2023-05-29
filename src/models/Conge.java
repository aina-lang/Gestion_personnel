package models;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Conge {
    private final MySQLConnection cnx = new MySQLConnection();
    private String motif;
    private Date dateDemande;
    private Date dateRetour;
    private int numConge;
    private int numEmp;
    private Object dateDebut;
    private Object dateFin;
    private Object id;
    private int nbrjr;


    public Conge(int numConge, String motif, int nbrjr, Date dateDemande, Date dateRetour, int numEmp) {
        this.motif = motif;
        this.dateDemande = dateDemande;
        this.numEmp = numEmp;
        this.numConge = numConge;
        this.dateRetour = dateRetour;
        this.nbrjr = nbrjr;
    }

    public Conge(int numConge, String motif, Date dateDemande, Date dateRetour, int numEmp) {
        this.motif = motif;
        this.dateDemande = dateDemande;
        this.numEmp = numEmp;
        this.numConge = numConge;
        this.dateRetour = dateRetour;
    }

    public Conge(int numEmp, String motif, Date dateDemande, Date dateRetour) {
        this.numEmp = numEmp;
        this.motif = motif;
        this.dateDemande = Date.valueOf(String.valueOf(dateDemande));
        this.dateRetour = Date.valueOf(String.valueOf(dateRetour));
    }

    public Conge(int numEmp, String text, String text1, Date dateRetour) {
    }

    public Conge() {

    }

    public Conge(int numEmp, String motifText, int nbJr, Date dateDemande, Date dateRetour) {
        this.numEmp = numEmp;
        this.motif = motifText;
        this.dateDemande = Date.valueOf(String.valueOf(dateDemande));
        this.dateRetour = Date.valueOf(String.valueOf(dateRetour));
        this.nbrjr = nbJr;
    }

    public Conge(int congeId, int numEmp, String motifText, int nbJr, Date dateDemande, Date dateRetour) {
        this.numConge = congeId;
        this.numEmp = numEmp;
        this.motif = motifText;
        this.dateDemande = Date.valueOf(String.valueOf(dateDemande));
        this.dateRetour = Date.valueOf(String.valueOf(dateRetour));
        this.nbrjr = nbJr;
    }

    public int getNbrjr() {
        return nbrjr;
    }

    public void setNbrjr(int nbrjr) {
        this.nbrjr = nbrjr;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public Date getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(String dateDemande) {
        this.dateDemande = Date.valueOf(dateDemande);
    }

    public Date getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(String dateRetour) {
        this.dateRetour = Date.valueOf(dateRetour);
    }

    public int getNumConge() {
        return numConge;
    }

    public void setNumConge(int numConge) {
        this.numConge = numConge;
    }

    public int getNumEmp() {
        return numEmp;
    }

    public void setNumEmp(int numEmp) {
        this.numEmp = numEmp;
    }

    public void delete(int numConge) {
        // Récupérer la connexion existante à la base de données
        Connection connection = MySQLConnection.getConnection();
        PreparedStatement statement = null;

        try {
            // Requête SQL pour supprimer le congé
            String sql = "DELETE FROM CONGE WHERE numConge = ?";

            // Création de la déclaration préparée
            statement = connection.prepareStatement(sql);

            // Définition des valeurs des paramètres
            statement.setInt(1, numConge);

            // Exécution de la requête de suppression
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Congé supprimé avec succès !");
            } else {
                System.out.println("Échec de la suppression du congé. Numéro de congé invalide.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fermeture des ressources
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void update(Conge updatedConge) throws SQLException, ParseException {
        String query = "UPDATE CONGE SET numEmp = ?, motif = ?, nbrjr = ?, dateDemande = ?, dateRetour = ? WHERE numConge = ?";
        PreparedStatement statement = MySQLConnection.getConnection().prepareStatement(query);
        statement.setInt(1, updatedConge.getNumEmp());
        statement.setString(2, updatedConge.getMotif());
        statement.setInt(3, updatedConge.getNbrjr());
        statement.setDate(4, updatedConge.getDateDemande());
        statement.setDate(5, updatedConge.getDateRetour());
        statement.setInt(6, updatedConge.getNumConge());

        statement.executeUpdate();
    }


    public void create(Conge conge) throws SQLException, ParseException {
        String query = "INSERT INTO CONGE (numEmp, motif , nbrjr, dateDemande, dateRetour) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = MySQLConnection.getConnection().prepareStatement(query);

        // Utilisez SimpleDateFormat pour convertir les dates
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateDemande = null;
        Date dateRetour = null;

        // Vérifiez si conge.dateDemande n'est pas null avant la conversion
        if (conge.dateDemande != null) {
            dateDemande = new Date(sdf.parse(String.valueOf(conge.dateDemande)).getTime());
        }

        // Vérifiez si conge.dateRetour n'est pas null avant la conversion
        if (conge.dateRetour != null) {
            dateRetour = new Date(sdf.parse(String.valueOf(conge.dateRetour)).getTime());
        }

        // Utilisez maintenant dateDemande et dateRetour dans votre requête SQL

        statement.setInt(1, conge.numEmp);
        statement.setString(2, conge.motif);
        statement.setInt(3, conge.nbrjr);
        statement.setDate(4, dateDemande);
        statement.setDate(5, dateRetour);
        statement.executeUpdate();
    }


    public List<Conge> lister() {
        List<Conge> conges = new ArrayList<>();
        try {
            // Établir la connexion à la base de données
            MySQLConnection cnx = new MySQLConnection();
            // Exécuter la requête pour récupérer les congés
            String query = "SELECT CONGE.*, EMPLOYE.*\n" +
                    "FROM CONGE\n" +
                    "INNER JOIN EMPLOYE ON CONGE.numEmp = EMPLOYE.numEmp;\n";
            Statement statement = MySQLConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            // Parcourir les résultats et créer les objets Conge correspondants
            while (resultSet.next()) {
                this.numConge = resultSet.getInt("numConge");
                this.motif = resultSet.getString("motif");
                this.nbrjr = resultSet.getInt("nbrjr");
                this.dateDemande = resultSet.getDate("dateDemande");
                this.dateRetour = resultSet.getDate("dateRetour");
                this.numEmp = resultSet.getInt("numEmp");

                Conge conge = new Conge(this.numConge, this.motif, this.nbrjr, this.dateDemande, this.dateRetour, this.numEmp);
                System.out.println(numConge + " " + motif);
                conges.add(conge);
            }

            // Fermer les ressources (résultat, instruction, connexion, etc.)
            resultSet.close();
            statement.close();
            MySQLConnection.getConnection().close();

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception ou la propager vers l'appelant
            // ...
        }

        return conges;
    }


    // Méthode pour obtenir l'ID du congé
    public Object getId() {
        return id;
    }

    // Méthode pour obtenir l'employé lié au congé
    public Object getEmploye() {
        return new Employer(numEmp);
    }

    // Méthode pour obtenir la date de début du congé
    public Object getDateDebut() {
        return dateDebut;
    }

    // Méthode pour obtenir la date de fin du congé
    public Object getDateFin() {
        return dateFin;
    }

}
