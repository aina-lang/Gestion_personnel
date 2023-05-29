package models;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
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
        this.jourDemande.setText(Integer.toString(dateDemande.getDay()));
        this.moisDemande.setText(Integer.toString(dateDemande.getMonth()));
        this.anneeDemande.setText(Integer.toString(dateDemande.getYear() + 1900));
        this.motif.setText(motif);
        this.jourRetour.setText(Integer.toString(dateRetour.getDay()));
        this.moisRetour.setText(Integer.toString(dateRetour.getMonth()));
        this.anneeRetour.setText(Integer.toString(dateRetour.getYear() + 1900));
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
        int nbJr = (int) this.nbrJour.getValue();
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

        // Créer les objets Date pour dateDemande et dateRetour
        Date dateDemande = new Date(anneeDemande - 1900, moisDemande - 1, jourDemande);
        Date dateRetour = new Date(anneeRetour - 1900, moisRetour - 1, jourRetour);

        // Créer l'objet Conge
        if (congeId > 0) {
            // Modification du congé existant
            conge = new Conge(congeId, numEmp, motifText, nbJr, dateDemande, dateRetour);
            try {
                conge.update(conge);
                System.out.println("Congé mis à jour avec succès !");
            } catch (SQLException | ParseException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            // Création d'un nouveau congé
            conge = new Conge(numEmp, motifText, nbJr, dateDemande, dateRetour);
            try {
                conge.create(conge);
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
}
