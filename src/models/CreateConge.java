package models;

import com.raven.datechooser.DateChooser;
import com.raven.datechooser.EventDateChooser;
import com.raven.datechooser.SelectedAction;
import com.raven.datechooser.SelectedDate;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class CreateConge extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField jourDemande;
    private JTextField moisDemande;
    private JTextField anneeDemande;
    private JComboBox<String> employe;
    private JTextArea motif;
    private JTextField jourRetour;
    private JTextField moisRetour;
    private JTextField anneeRetour;
    private JButton dateDemandeBtn;
    private JButton dateRetourBtn;
    private JTextField textField1;

    private JSpinner nbrJour;
    private Conge conge;
    private final MySQLConnection cnx = new MySQLConnection();
    private final int congeId;

    public CreateConge(int congeId, String motif, int nbrJours, Date dateDemande, Date dateRetour, int numEmp) throws SQLException {
        this.congeId = congeId;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        populateComboBox(employe);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        // Utilisez les paramètres pour pré-remplir les champs du formulaire
        LocalDate localDateDemande = dateDemande.toLocalDate();
        LocalDate localDateRetour = dateRetour.toLocalDate();

        this.jourDemande.setText(String.valueOf(localDateDemande.getDayOfMonth()));
        this.moisDemande.setText(String.valueOf(localDateDemande.getMonthValue()));
        this.anneeDemande.setText(String.valueOf(localDateDemande.getYear()));
        this.motif.setText(motif);
        this.jourRetour.setText(String.valueOf(localDateRetour.getDayOfMonth()));
        this.moisRetour.setText(String.valueOf(localDateRetour.getMonthValue()));
        this.anneeRetour.setText(String.valueOf(localDateRetour.getYear()));
        this.nbrJour.setValue(nbrJours);
        this.employe.setSelectedItem(numEmp + " - " + getEmployeName(numEmp));
    }

    public CreateConge(int congeId) throws SQLException {
        this.congeId = congeId;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        populateComboBox(employe);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public CreateConge() throws SQLException {
        this(0);
        dateDemandeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateChooser dateChooser1 = new DateChooser();
                dateChooser1.setTextRefernce(textField1);

                dateChooser1.showPopup();
                dateChooser1.addEventDateChooser(new EventDateChooser() {
                    @Override
                    public void dateSelected(SelectedAction selectedAction, SelectedDate selectedDate) {
                        jourDemande.setText(String.valueOf(selectedDate.getDay()));
                        moisDemande.setText(String.valueOf(selectedDate.getMonth()));
                        anneeDemande.setText(String.valueOf(selectedDate.getYear()));
                    }
                });
            }
        });
        dateRetourBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateChooser dateChooser2 = new DateChooser();
                dateChooser2.setTextRefernce(textField1);
                dateChooser2.showPopup();
                dateChooser2.addEventDateChooser(new EventDateChooser() {
                    @Override
                    public void dateSelected(SelectedAction selectedAction, SelectedDate selectedDate) {
                        jourRetour.setText(String.valueOf(selectedDate.getDay()));
                        moisRetour.setText(String.valueOf(selectedDate.getMonth()));
                        anneeRetour.setText(String.valueOf(selectedDate.getYear()));
                    }
                });
            }
        });
    }

    public String getEmployeName(int numEmp) throws SQLException {
        String query = "SELECT Nom, Prenom FROM EMPLOYE WHERE numEmp = " + numEmp;
        Statement statement = MySQLConnection.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            String nom = resultSet.getString("Nom");
            String prenom = resultSet.getString("Prenom");
            return nom + " " + prenom;
        }

        return "";
    }

    private void onOK() {
        // Récupérer les valeurs des champs de texte
        String jourDemandeText = jourDemande.getText();
        String moisDemandeText = moisDemande.getText();
        String anneeDemandeText = anneeDemande.getText();
        String motifText = motif.getText();
        String jourRetourText = jourRetour.getText();
        String moisRetourText = moisRetour.getText();
        String anneeRetourText = anneeRetour.getText();

        String employeText = (String) employe.getSelectedItem();
        String selectedEmploye = (String) employe.getSelectedItem();
        String[] parts = selectedEmploye.split(" - ");
        int numEmp = Integer.parseInt(parts[0]);

        // Vérifier si tous les champs sont remplis
        if (jourDemandeText.isEmpty() || moisDemandeText.isEmpty() || anneeDemandeText.isEmpty() ||
                motifText.isEmpty() || jourRetourText.isEmpty() || moisRetourText.isEmpty() ||
                anneeRetourText.isEmpty() || employeText == null) {
            JOptionPane.showMessageDialog(contentPane, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convertir les valeurs en types appropriés
        int jourDemande = Integer.parseInt(jourDemandeText);
        int moisDemande = Integer.parseInt(moisDemandeText);
        int anneeDemande = Integer.parseInt(anneeDemandeText);
        int jourRetour = Integer.parseInt(jourRetourText);
        int moisRetour = Integer.parseInt(moisRetourText);
        int anneeRetour = Integer.parseInt(anneeRetourText);


        // Vérifier si la date de demande est supérieure à la date d'aujourd'hui
        LocalDate dateDemande = LocalDate.of(anneeDemande, moisDemande, jourDemande);
        LocalDate currentDate = LocalDate.now();
        if (!dateDemande.isAfter(currentDate)) {
            JOptionPane.showMessageDialog(contentPane, "La date de demande doit être supérieure à la date d'aujourd'hui.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Vérifier si la date de retour est supérieure à la date de demande
        LocalDate dateRetour = LocalDate.of(anneeRetour, moisRetour, jourRetour);
        if (!dateRetour.isAfter(dateDemande)) {
            JOptionPane.showMessageDialog(contentPane, "La date de retour doit être supérieure à la date de demande.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }


        // Créer les objets Date pour dateDemande et dateRetour
        Date dateD = new Date(anneeDemande - 1900, moisDemande - 1, jourDemande);
        Date dateR = new Date(anneeRetour - 1900, moisRetour - 1, jourRetour);

        String temp = dateD.toString();
        String[] part = temp.split("-");
        // LocalDate dateDemande = LocalDate.of(Integer.parseInt(part[0]), Integer.parseInt(part[1]), Integer.parseInt(part[2]));

        temp = dateR.toString();
        String[] part1 = temp.split("-");
        // LocalDate dateRetour = LocalDate.of(Integer.parseInt(part1[0]), Integer.parseInt(part1[1]), Integer.parseInt(part1[2]));

        // Calculer le nombre de jours entre dateDemande et dateRetour
        long nbJr = ChronoUnit.DAYS.between(dateDemande, dateRetour);

        // Vérifier si le nombre de jours de congé dépasse 30
        if (nbJr > 30) {
            JOptionPane.showMessageDialog(contentPane, "Le nombre de jours de congé ne doit pas dépasser 30 jours.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int nbCongesPris = 0;

        try {
            nbCongesPris = getNbCongesPris(numEmp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (nbCongesPris >= 30) {
            JOptionPane.showMessageDialog(contentPane, "L'employé a déjà pris 30 congés cette année.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Créer l'objet Conge
        if (congeId > 0) {
            // Modification du congé existant
            conge = new Conge(congeId, numEmp, motifText, Math.toIntExact(nbJr), dateD, dateR);
            try {
                conge.update(conge);
                dispose();
                JOptionPane.showMessageDialog(contentPane, "Mise a jours du demande de congé effectueé.", "Effectuée", JOptionPane.PLAIN_MESSAGE);
            } catch (SQLException | ParseException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            // Création d'un nouveau congé
            conge = new Conge(numEmp, motifText, Math.toIntExact(nbJr), dateD, dateR);
            try {
                conge.create(conge);
                dispose();
                JOptionPane.showMessageDialog(contentPane, "Demande du congé effectueé.", "Effectuée", JOptionPane.ERROR_MESSAGE);

            } catch (SQLException | ParseException ex) {
                throw new RuntimeException(ex);
            }
        }


        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public void populateComboBox(JComboBox<String> comboBox) throws SQLException {
        List<String> employes = getAllEmployes();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(employes.toArray(new String[0]));
        comboBox.setModel(model);
    }

    public List<String> getAllEmployes() throws SQLException {
        List<String> employes = new ArrayList<>();
        String query = "SELECT numEmp, Nom, Prenom FROM EMPLOYE";
        Statement statement = MySQLConnection.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            String numEmp = resultSet.getString("numEmp");
            String nom = resultSet.getString("Nom");
            String prenom = resultSet.getString("Prenom");
            String employe = numEmp + " - " + nom + " " + prenom;
            employes.add(employe);
        }

        return employes;
    }


    private int getNbCongesPris(int numEmp) throws SQLException {
        String query = "SELECT nbrjr  FROM CONGE WHERE numEmp = " + numEmp + " AND YEAR(dateDemande) = YEAR(CURRENT_DATE)";
        Statement statement = MySQLConnection.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        int nbConges = 0;
        while (resultSet.next()) {
            nbConges = nbConges + resultSet.getInt("nbrjr");
            System.out.println("nombre du conge pris par est de " + resultSet.getInt("nbrjr"));
        }
        resultSet.close();
        statement.close();
        System.out.println("nombre du conge pris par est de " + nbConges);
        return nbConges;
    }


}
