package models;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pointage {
    private int id;
    private String datePointage;
    private int numEmp;
    private String pointage;

    public Pointage() {
    }

    public Pointage(String datePointage, int numEmp, String pointage) {
        this.datePointage = datePointage;
        this.numEmp = numEmp;
        this.pointage = pointage;
    }

    // Méthode pour récupérer un pointage par son ID
    public static Pointage getPointageById(int id) {
        Pointage pointage = null;
        try (Connection connection = MySQLConnection.getConnection()) {
            String query = "SELECT * FROM POINTAGE WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                pointage = new Pointage();
                pointage.id = resultSet.getInt("id");
                pointage.datePointage = resultSet.getString("datePointage");
                pointage.numEmp = resultSet.getInt("numEmp");
                pointage.pointage = resultSet.getString("pointage");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du pointage : " + e.getMessage());
        }
        return pointage;
    }

    // Méthode pour récupérer tous les pointages
    public static List<Pointage> listerPointage() {
        List<Pointage> pointages = new ArrayList<>();

        try {
            MySQLConnection cnx = new MySQLConnection();
            String query = "SELECT * FROM POINTAGE";
            Statement statement = MySQLConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Pointage pointage = new Pointage();
                pointage.id = resultSet.getInt("id");
                pointage.datePointage = resultSet.getString("datePointage");
                pointage.numEmp = resultSet.getInt("numEmp");
                pointage.pointage = resultSet.getString("pointage");
                pointages.add(pointage);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des pointages : " + e.getMessage());
        }
        return pointages;
    }

    public int getId() {
        return id;
    }

    public String getDatePointage() {
        return datePointage;
    }

    public void setDatePointage(String datePointage) {
        this.datePointage = datePointage;
    }

    public int getNumEmp() {
        return numEmp;
    }

    public void setNumEmp(int numEmp) {
        this.numEmp = numEmp;
    }

    public String getPointage() {
        return pointage;
    }

    public void setPointage(String pointage) {
        this.pointage = pointage;
    }

    // Méthode pour obtenir l'heure d'entrée du pointage
    public String getHeureEntree() {
        // Logique pour obtenir l'heure d'entrée du pointage
        // Par exemple, si vous avez une colonne "heureEntree" dans votre table "Pointage"
        try (Connection connection = MySQLConnection.getConnection()) {
            String query = "SELECT heureEntree FROM POINTAGE WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("heureEntree");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'heure d'entrée du pointage : " + e.getMessage());
        }
        return null; // Ou une valeur par défaut appropriée si aucune heure d'entrée n'est trouvée
    }

    // Méthode pour obtenir l'heure de sortie du pointage
    public String getHeureSortie() {
        // Logique pour obtenir l'heure de sortie du pointage
        // Par exemple, si vous avez une colonne "heureSortie" dans votre table "Pointage"
        String heureSortie = "";

        try (Connection connection = MySQLConnection.getConnection()) {
            String query = "SELECT heureSortie FROM Pointage WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                heureSortie = resultSet.getString("heureSortie");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'heure de sortie du pointage : " + e.getMessage());
        }

        return heureSortie;
    }

    // Méthode pour créer un nouveau pointage
    public void createPointage(int numEmp) {
        try (Connection connection = MySQLConnection.getConnection()) {
            String query = "INSERT INTO POINTAGE (datePointage, numEmp, pointage) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            // Obtention de la date d'aujourd'hui
            LocalDate today = LocalDate.now();
            // Conversion de la date en objet java.sql.Date
            java.sql.Date dateAujourdhui = java.sql.Date.valueOf(today);

            // Assignation de la date d'aujourd'hui au paramètre de la déclaration préparée
            statement.setDate(1, dateAujourdhui);
            statement.setInt(2, numEmp);
            statement.setString(3, "Oui");

            // Récupérer l'ID généré pour le nouveau pointage

            statement.executeUpdate();
            System.out.println("Nouveau pointage créé avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création du pointage : " + e.getMessage());
        }
    }

    // Méthode pour mettre à jour un pointage existant
    public void updatePointage(int id, String datePointage, String pointage) {
        try (Connection connection = MySQLConnection.getConnection()) {
            String query = "UPDATE POINTAGE SET datePointage = ?, pointage = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, datePointage);
            // statement.setInt(2, numEmp);
            statement.setString(2, pointage);
            statement.setInt(3, id);
            statement.executeUpdate();

            System.out.println("Pointage mis à jour avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du pointage : " + e.getMessage());
        }
    }

    // Méthode pour supprimer un pointage existant
    public void deletePointage(int pointageId) {
        try (Connection connection = MySQLConnection.getConnection()) {
            String query = "DELETE FROM POINTAGE WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, pointageId);
            statement.executeUpdate();

            System.out.println("Pointage supprimé avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du pointage : " + e.getMessage());
        }
    }

    // Méthode pour obtenir l'employé lié au pointage
    public Employer getEmploye() {
        return new Employer(numEmp);
    }

    // Méthode pour obtenir la date du pointage
    public String getDate() {
        return datePointage;
    }
}
