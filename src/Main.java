import models.Frame;
import models.MySQLConnection;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws SQLException, ParseException, IOException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, BadLocationException, NoSuchMethodException {
        new Frame();

        // Obtenir la date actuelle
        LocalDate currentDate = LocalDate.now();
        String query = "SELECT E.numEmp, E.nom, E.prenom, E.poste, C.nbrjr, C.dateDemande, C.dateRetour " +
                "FROM EMPLOYE E " +
                "INNER JOIN CONGE C ON E.numEmp = C.numEmp " +
                "WHERE  \"" + currentDate + "\" BETWEEN C.dateDemande AND C.dateRetour";

        MySQLConnection cnx = new MySQLConnection();
        PreparedStatement statement = MySQLConnection.getConnection().prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
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

        }

    }
}