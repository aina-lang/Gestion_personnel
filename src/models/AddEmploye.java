package models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddEmploye extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nom;
    private JTextField prenom;
    private JTextField poste;
    private JLabel nomMsg;
    private JLabel prenomMsg;
    private JLabel posteMsg;
    private JLabel salaireMsg;
    private JTextField salaire;

    private Employer employe;
    private int numEmp = -1;

    public AddEmploye(EmployerPanel employerPanel) {
        init();
    }

    public AddEmploye(int numEmp, String nom, String prenom, String poste, Float salaire) {
        init();
        this.numEmp = numEmp;
        this.nom.setText(nom);
        this.prenom.setText(prenom);
        this.poste.setText(poste);
        this.salaire.setText(String.valueOf(salaire));

    }

    private void onOK() {
        String updatedNom = nom.getText();
        String updatedPrenom = prenom.getText();
        String updatedPoste = poste.getText();
        Float updatedSalaire;

        if (!isFieldValid(updatedNom, "nom") ||
                !isFieldValid(updatedPrenom, "prénom") ||
                !isFieldValid(updatedPoste, "poste") ||
                !isSalaireValid()) {
            return;
        }

        updatedSalaire = Float.parseFloat(salaire.getText());

        Employer tmp;
        if (numEmp > 0) {
            // Mise à jour de l'employé existant
            tmp = new Employer(numEmp, updatedNom, updatedPrenom, updatedPoste, updatedSalaire);
            try {
                tmp.updateEmployer(numEmp, updatedNom, updatedPrenom, updatedPoste, updatedSalaire);
                System.out.println("Employee updated successfully!");
            } catch (SQLException ex) {
                System.out.println("Error updating employee: " + ex.getMessage());
            }
        } else {
            // Ajout d'un nouvel employé
            tmp = new Employer(updatedNom, updatedPrenom, updatedPoste, updatedSalaire);
            try {
                tmp.createEmployer(tmp);
                System.out.println("New employee added successfully!");
            } catch (SQLException ex) {
                System.out.println("Error adding new employee: " + ex.getMessage());
            }
        }

        dispose();
    }

    private boolean isFieldValid(String fieldValue, String fieldName) {
        if (fieldValue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le champ " + fieldName + " est vide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean isSalaireValid() {
        String salaireValue = salaire.getText();
        if (salaireValue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le champ salaire est vide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            Float.parseFloat(salaireValue);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Le champ salaire doit être un nombre valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }


    private void init() {
        initListeners();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        contentPane.setBackground(Color.WHITE); // Set the background color to white
        pack();
        buttonOK.setBackground(new Color(0x3c7164));
        buttonOK.setBorderPainted(false);
        buttonOK.setForeground(Color.WHITE);
        buttonCancel.setBackground(new Color(0xD13A28));
        buttonCancel.setBorderPainted(false);
        buttonCancel.setForeground(Color.WHITE);
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

       /* nom.addKeyListener(new KeyAdapter() {
            final Pattern pattern = Pattern.compile("[^a-zA-Z\\s]");

            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char typedChar = e.getKeyChar();

                // Vérifier si la touche "Effacer" est pressée


                String updatedText = nom.getText() + typedChar;
                Matcher textMatcher = pattern.matcher(updatedText);

                if (textMatcher.find()) {
                    if (typedChar == KeyEvent.VK_DELETE) {
                        nomMsg.setText("");
                    } else {
                        nomMsg.setText("Ce champ ne doit pas avoir de caractères spéciaux");
                    }
                } else {
                    nomMsg.setText("");
                }
            }
        });

*/
    }


    private void initListeners() {
        // Ajout des KeyListeners pour les champs "nom", "prénom" et "poste"
        nom.addKeyListener(new KeyAdapter() {
            final Pattern pattern = Pattern.compile("[^a-zA-Z\\s]");

            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char typedChar = e.getKeyChar();

                String updatedText = nom.getText() + typedChar;
                Matcher textMatcher = pattern.matcher(updatedText);

                if (textMatcher.find()) {
                    if (typedChar == KeyEvent.VK_DELETE) {
                        nomMsg.setText("");
                    } else {
                        nomMsg.setText("Ce champ ne doit pas avoir de caractères spéciaux");
                    }
                } else {
                    nomMsg.setText("");
                }
            }
        });

        prenom.addKeyListener(new KeyAdapter() {
            final Pattern pattern = Pattern.compile("[^a-zA-Z\\s]");

            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char typedChar = e.getKeyChar();

                String updatedText = prenom.getText() + typedChar;
                Matcher textMatcher = pattern.matcher(updatedText);

                if (textMatcher.find()) {
                    if (typedChar == KeyEvent.VK_DELETE) {
                        prenomMsg.setText("");
                    } else {
                        prenomMsg.setText("Ce champ ne doit pas avoir de caractères spéciaux");
                    }
                } else {
                    prenomMsg.setText("");
                }
            }
        });

        poste.addKeyListener(new KeyAdapter() {
            final Pattern pattern = Pattern.compile("[^a-zA-Z]");

            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char typedChar = e.getKeyChar();

                String updatedText = poste.getText() + typedChar;
                Matcher textMatcher = pattern.matcher(updatedText);

                if (textMatcher.find()) {
                    if (typedChar == KeyEvent.VK_DELETE) {
                        posteMsg.setText("");
                    } else {
                        posteMsg.setText("Ce champ ne doit pas avoir de caractères spéciaux");
                    }
                } else {
                    posteMsg.setText("");
                }
            }
        });
    }

    private void onCancel() {
        dispose();
    }
}
