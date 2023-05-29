package models;

import javax.swing.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CreatePointage extends JDialog {
    private final MySQLConnection cnx = new MySQLConnection();
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox<String> comboBoxEmploye;
    private JLabel dateComplet;

    public CreatePointage() throws SQLException {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        populateComboBox(comboBoxEmploye);
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

    public static void main(String[] args) throws SQLException {
        CreatePointage dialog = new CreatePointage();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void onOK() {
        // add your code here
        String employeText = (String) comboBoxEmploye.getSelectedItem();
        String selectedEmploye = (String) comboBoxEmploye.getSelectedItem();
        String[] parts = selectedEmploye.split(" - ");
        int numEmp = Integer.parseInt(parts[0]);
        System.out.println(selectedEmploye);
        Pointage pointage = new Pointage();
        pointage.createPointage(numEmp);

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary

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
