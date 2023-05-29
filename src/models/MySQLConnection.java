package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnection {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/projetJavaL2";
    private static final String DB_USER = "pma";
    private static final String DB_PASSWORD = "";

    public static Connection connection = null;

    public MySQLConnection() {
        try {
            setConnection();
            // System.out.println("Connexion établie avec succès !");
            createTables(); // Appel de la méthode pour créer les tables
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }

    static Connection getConnection() {
        return connection;
    }

    public void setConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur lors du chargement du pilote MySQL : " + e.getMessage());
        }
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private void createTables() {
        try (Statement statement = connection.createStatement()) {
            // Requête pour créer la table "EMPLOYE" si elle n'existe pas
            String createEmployeTableQuery = "CREATE TABLE IF NOT EXISTS EMPLOYE ("
                    + "numEmp INT AUTO_INCREMENT PRIMARY KEY,"
                    + "Nom VARCHAR(255),"
                    + "Prenom VARCHAR(255),"
                    + "poste VARCHAR(255),"
                    + "salaire FLOAT"
                    + ")";
            statement.executeUpdate(createEmployeTableQuery);

            // Requête pour créer la table "POINTAGE" si elle n'existe pas
            String createPointageTableQuery = "CREATE TABLE IF NOT EXISTS POINTAGE ("
                    + "datePointage DATETIME,"
                    + "numEmp INT,"
                    + "pointage VARCHAR(255),"
                    + "FOREIGN KEY (numEmp) REFERENCES EMPLOYE(numEmp)"
                    + ")";
            statement.executeUpdate(createPointageTableQuery);

            // Requête pour créer la table "CONGE" si elle n'existe pas
            String createCongeTableQuery = "CREATE TABLE IF NOT EXISTS CONGE ("
                    + "numConge INT AUTO_INCREMENT PRIMARY KEY ,"
                    + "numEmp INT,"
                    + "motif VARCHAR(255),"
                    + "nbrjr INT,"
                    + "dateDemande DATE,"
                    + "dateRetour DATE,"
                    + "FOREIGN KEY (numEmp) REFERENCES EMPLOYE(numEmp)"
                    + ")";
            statement.executeUpdate(createCongeTableQuery);

            // System.out.println("Tables créées avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création des tables : " + e.getMessage());
        }
    }
}
