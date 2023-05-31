package models;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class ButtonEditor extends DefaultCellEditor {
    private final JPanel panel;
    private final JButton deleteButton;
    private final JButton editButton;

    private final JButton readButton;
    private final JButton generatePdfButton;

    private String buttonText;

    public ButtonEditor(JTable table, String tableName) {

        super(new JTextField());
        panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setAlignmentY(Component.CENTER_ALIGNMENT);
        panel.setBorder(new EmptyBorder(10, 0, 0, 0)); // Ajoute un padding de 10 pixels en haut

        deleteButton = new JButton();
        deleteButton.setIcon(loadIcon("delete.png", 15));
        //  deleteButton.setBackground(null);
        // deleteButton.setBorder(null);

        editButton = new JButton();
        editButton.setIcon(loadIcon("edit.png", 15));
        // editButton.setBackground(null);
        // editButton.setBorder(null);

        readButton = new JButton();
        readButton.setIcon(loadIcon("book.png", 15));
        //  readButton.setBackground(null);
        // readButton.setBorder(null);

        generatePdfButton = new JButton();
        generatePdfButton.setIcon(loadIcon("pdf.png", 15));
        //  generatePdfButton.setBackground(null);
        //generatePdfButton.setBorder(null);

        panel.add(deleteButton);
        panel.add(editButton);
        panel.add(readButton);
        panel.add(generatePdfButton);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {


                    //if (option == JOptionPane.YES_OPTION) {
                    try {

                        System.out.println(tableName);
                        if (tableName.equals("Employer")) {
                            int numEmp = (int) table.getValueAt(selectedRow, 0);
                            String nomEmploye = (String) table.getValueAt(selectedRow, 1);
                            String prenomEmploye = (String) table.getValueAt(selectedRow, 2);
                            int option = JOptionPane.showConfirmDialog(null,
                                    "Êtes-vous sûr de vouloir supprimer l'employé : " + prenomEmploye + " " + nomEmploye + " ?",
                                    "Confirmation de suppression",
                                    JOptionPane.YES_NO_OPTION);

                            if (option == JOptionPane.YES_OPTION) {
                                Employer temp = new Employer();
                                temp.deleteEmp(numEmp);
                            }
                        } else if (tableName.equals("Pointage")) {
                            int pointageId = (int) table.getValueAt(selectedRow, 0);
                            String datePointage = (String) table.getValueAt(selectedRow, 1);
                            int numEmp = (int) table.getValueAt(selectedRow, 2);
                            String pointage = (String) table.getValueAt(selectedRow, 3);
                            int option = JOptionPane.showConfirmDialog(null,
                                    "Êtes-vous sûr de vouloir supprimer le pointage du " + datePointage + " pour l'employé " + numEmp + " ?",
                                    "Confirmation de suppression",
                                    JOptionPane.YES_NO_OPTION);

                            if (option == JOptionPane.YES_OPTION) {
                                Pointage temp = new Pointage();
                                temp.deletePointage(pointageId);
                            }
                        } else if (tableName.equals("Conge")) {
                            int congeId = (int) table.getValueAt(selectedRow, 0);
                            Date dateDebut = (Date) table.getValueAt(selectedRow, 4);
                            Date dateFin = (Date) table.getValueAt(selectedRow, 5);
                            int numEmp = (int) table.getValueAt(selectedRow, 1);
                            int option = JOptionPane.showConfirmDialog(null,
                                    "Êtes-vous sûr de vouloir supprimer le congé du " + dateDebut + " au " + dateFin + " pour l'employé " + numEmp + " ?",
                                    "Confirmation de suppression",
                                    JOptionPane.YES_NO_OPTION);

                            if (option == JOptionPane.YES_OPTION) {
                                Conge temp = new Conge();
                                temp.delete(congeId);
                            }
                        }

                        refreshTable(table, tableName);

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            //}
        });


        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();

                if (tableName.equals("Employer")) {
                    // Logique de modification pour la table "Employe"
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Récupérer les informations de l'employé sélectionné
                        int numEmp = (int) table.getValueAt(selectedRow, 0);
                        String nom = (String) table.getValueAt(selectedRow, 1);
                        String prenom = (String) table.getValueAt(selectedRow, 2);
                        String poste = (String) table.getValueAt(selectedRow, 3);
                        Float salaire = (Float) table.getValueAt(selectedRow, 4);

                        // Créer un JPanel pour le formulaire d'édition d'employé (updateEmploy.form)
                        System.out.println(numEmp);

                        AddEmploye dialog = new AddEmploye(numEmp, nom, prenom, poste, salaire);
                        dialog.pack();
                        dialog.setResizable(false);
                        dialog.setLocationRelativeTo(null);
                        dialog.setVisible(true);
                    }
                } else if (tableName.equals("Pointage")) {
                    // Logique de modification pour la table "Pointage"
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Récupérer les informations du pointage sélectionné
                        int pointageId = (int) table.getValueAt(selectedRow, 0);
                        String datePointage = (String) table.getValueAt(selectedRow, 1);
                        int numEmp = (int) table.getValueAt(selectedRow, 2);
                        String pointage = (String) table.getValueAt(selectedRow, 3);

                        // Exécuter la logique de modification pour la table "Pointage"
                        // Par exemple, vous pouvez afficher une boîte de dialogue pour permettre à l'utilisateur de modifier les valeurs du pointage
                        String updatedDatePointage = JOptionPane.showInputDialog(null, "Nouvelle date de pointage:", datePointage);
                        String updatedPointage = JOptionPane.showInputDialog(null, "Nouveau pointage:", pointage);

                        // Vérifier si les valeurs mises à jour sont valides (par exemple, si elles ne sont pas vides)
                        if (updatedDatePointage != null && !updatedDatePointage.isEmpty() &&
                                updatedPointage != null && !updatedPointage.isEmpty()) {
                            // Mettre à jour les valeurs dans la table
                            table.setValueAt(updatedDatePointage, selectedRow, 1);
                            table.setValueAt(updatedPointage, selectedRow, 3);

                            // Mettre à jour les valeurs dans la base de données (vous devez implémenter cette logique)
                            Pointage temp = new Pointage();
                            temp.updatePointage(pointageId, updatedDatePointage, updatedPointage);

                            System.out.println("Pointage updated successfully!");
                        } else {
                            System.out.println("Invalid values for pointage update.");
                        }
                    }


                } else if (tableName.equals("Conge")) {
                    // Logique de modification pour la table "Conge"
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Récupérer les informations du congé sélectionné
                        int congeId = (int) table.getValueAt(selectedRow, 0);
                        String motif = (String) table.getValueAt(selectedRow, 3);
                        int nbrJours = (int) table.getValueAt(selectedRow, 2);
                        Date dateDemande = (Date) table.getValueAt(selectedRow, 4);
                        Date dateRetour = (Date) table.getValueAt(selectedRow, 5);
                        int numEmp = (int) table.getValueAt(selectedRow, 1);

                        // Vérifier si c'est une modification ou un ajout
                        if (congeId > 0) {
                            CreateConge dialog = null;
                            try {
                                dialog = new CreateConge(congeId, motif, nbrJours, (java.sql.Date) dateDemande, (java.sql.Date) dateRetour, numEmp);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            dialog.pack();
                            dialog.setResizable(false);
                            dialog.setLocationRelativeTo(null);
                            dialog.setVisible(true);
                        } else {
                            // Ouvrir le dialogue d'ajout d'un nouveau congé
                            CreateConge dialog = null;
                            try {
                                dialog = new CreateConge();
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            dialog.pack();
                            dialog.setResizable(false);
                            dialog.setLocationRelativeTo(null);
                            dialog.setVisible(true);
                        }
                    }
                }

            }

        });


        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();

                if (tableName.equals("Employer")) {
                    // Logique de modification pour la table "Employe"
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Récupérer les informations de l'employé sélectionné
                        int numEmp = (int) table.getValueAt(selectedRow, 0);
                        String nom = (String) table.getValueAt(selectedRow, 1);
                        String prenom = (String) table.getValueAt(selectedRow, 2);
                        String poste = (String) table.getValueAt(selectedRow, 3);
                        Float salaire = (Float) table.getValueAt(selectedRow, 4);

                        // Créer un JPanel pour le formulaire d'édition d'employé (updateEmploy.form)
                        System.out.println(numEmp);

                        AddEmploye dialog = new AddEmploye(numEmp, nom, prenom, poste, salaire);
                        dialog.pack();
                        dialog.setResizable(false);
                        dialog.setLocationRelativeTo(null);
                        dialog.setVisible(true);
                    }
                } else if (tableName.equals("Pointage")) {
                    // Logique de modification pour la table "Pointage"
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Récupérer les informations du pointage sélectionné
                        int pointageId = (int) table.getValueAt(selectedRow, 0);
                        String datePointage = (String) table.getValueAt(selectedRow, 1);
                        int numEmp = (int) table.getValueAt(selectedRow, 2);
                        String pointage = (String) table.getValueAt(selectedRow, 3);

                        // Exécuter la logique de modification pour la table "Pointage"
                        // Par exemple, vous pouvez afficher une boîte de dialogue pour permettre à l'utilisateur de modifier les valeurs du pointage
                        String updatedDatePointage = JOptionPane.showInputDialog(null, "Nouvelle date de pointage:", datePointage);
                        String updatedPointage = JOptionPane.showInputDialog(null, "Nouveau pointage:", pointage);

                        // Vérifier si les valeurs mises à jour sont valides (par exemple, si elles ne sont pas vides)
                        if (updatedDatePointage != null && !updatedDatePointage.isEmpty() &&
                                updatedPointage != null && !updatedPointage.isEmpty()) {
                            // Mettre à jour les valeurs dans la table
                            table.setValueAt(updatedDatePointage, selectedRow, 1);
                            table.setValueAt(updatedPointage, selectedRow, 3);

                            // Mettre à jour les valeurs dans la base de données (vous devez implémenter cette logique)
                            Pointage temp = new Pointage();
                            temp.updatePointage(pointageId, updatedDatePointage, updatedPointage);

                            System.out.println("Pointage updated successfully!");
                        } else {
                            System.out.println("Invalid values for pointage update.");
                        }
                    }


                } else if (tableName.equals("Conge")) {
                    // Logique de modification pour la table "Conge"
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Récupérer les informations du congé sélectionné
                        int congeId = (int) table.getValueAt(selectedRow, 0);
                        String motif = (String) table.getValueAt(selectedRow, 3);
                        int nbrJours = (int) table.getValueAt(selectedRow, 2);
                        Date dateDemande = (Date) table.getValueAt(selectedRow, 4);
                        Date dateRetour = (Date) table.getValueAt(selectedRow, 5);
                        int numEmp = (int) table.getValueAt(selectedRow, 1);

                        // Vérifier si c'est une modification ou un ajout
                        if (congeId > 0) {
                            CreateConge dialog = null;
                            try {
                                dialog = new CreateConge(congeId, motif, nbrJours, (java.sql.Date) dateDemande, (java.sql.Date) dateRetour, numEmp);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            dialog.pack();
                            dialog.setResizable(false);
                            dialog.setLocationRelativeTo(null);
                            dialog.setVisible(true);
                        } else {
                            // Ouvrir le dialogue d'ajout d'un nouveau congé
                            CreateConge dialog = null;
                            try {
                                dialog = new CreateConge();
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            dialog.pack();
                            dialog.setResizable(false);
                            dialog.setLocationRelativeTo(null);
                            dialog.setVisible(true);
                        }
                    }
                }

            }

        });


        generatePdfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();

                if (tableName.equals("Employer")) {
                    // Logique de modification pour la table "Employe"
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Récupérer les informations de l'employé sélectionné
                        int numEmp = (int) table.getValueAt(selectedRow, 0);
                        String nom = (String) table.getValueAt(selectedRow, 1);
                        String prenom = (String) table.getValueAt(selectedRow, 2);
                        String poste = (String) table.getValueAt(selectedRow, 3);
                        Float salaire = (Float) table.getValueAt(selectedRow, 4);

                        // Créer un JPanel pour le formulaire d'édition d'employé (updateEmploy.form)
                        System.out.println(numEmp);

                        AddEmploye dialog = new AddEmploye(numEmp, nom, prenom, poste, salaire);
                        dialog.pack();
                        dialog.setResizable(false);
                        dialog.setLocationRelativeTo(null);
                        dialog.setVisible(true);
                    }
                } else if (tableName.equals("Pointage")) {
                    // Logique de modification pour la table "Pointage"
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Récupérer les informations du pointage sélectionné
                        int pointageId = (int) table.getValueAt(selectedRow, 0);
                        String datePointage = (String) table.getValueAt(selectedRow, 1);
                        int numEmp = (int) table.getValueAt(selectedRow, 2);
                        String pointage = (String) table.getValueAt(selectedRow, 3);

                        // Exécuter la logique de modification pour la table "Pointage"
                        // Par exemple, vous pouvez afficher une boîte de dialogue pour permettre à l'utilisateur de modifier les valeurs du pointage
                        String updatedDatePointage = JOptionPane.showInputDialog(null, "Nouvelle date de pointage:", datePointage);
                        String updatedPointage = JOptionPane.showInputDialog(null, "Nouveau pointage:", pointage);

                        // Vérifier si les valeurs mises à jour sont valides (par exemple, si elles ne sont pas vides)
                        if (updatedDatePointage != null && !updatedDatePointage.isEmpty() &&
                                updatedPointage != null && !updatedPointage.isEmpty()) {
                            // Mettre à jour les valeurs dans la table
                            table.setValueAt(updatedDatePointage, selectedRow, 1);
                            table.setValueAt(updatedPointage, selectedRow, 3);

                            // Mettre à jour les valeurs dans la base de données (vous devez implémenter cette logique)
                            Pointage temp = new Pointage();
                            temp.updatePointage(pointageId, updatedDatePointage, updatedPointage);

                            System.out.println("Pointage updated successfully!");
                        } else {
                            System.out.println("Invalid values for pointage update.");
                        }
                    }


                } else if (tableName.equals("Conge")) {
                    // Logique de modification pour la table "Conge"
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Récupérer les informations du congé sélectionné
                        int congeId = (int) table.getValueAt(selectedRow, 0);
                        String motif = (String) table.getValueAt(selectedRow, 3);
                        int nbrJours = (int) table.getValueAt(selectedRow, 2);
                        Date dateDemande = (Date) table.getValueAt(selectedRow, 4);
                        Date dateRetour = (Date) table.getValueAt(selectedRow, 5);
                        int numEmp = (int) table.getValueAt(selectedRow, 1);

                        // Vérifier si c'est une modification ou un ajout
                        if (congeId > 0) {
                            CreateConge dialog = null;
                            try {
                                dialog = new CreateConge(congeId, motif, nbrJours, (java.sql.Date) dateDemande, (java.sql.Date) dateRetour, numEmp);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            dialog.pack();
                            dialog.setResizable(false);
                            dialog.setLocationRelativeTo(null);
                            dialog.setVisible(true);
                        } else {
                            // Ouvrir le dialogue d'ajout d'un nouveau congé
                            CreateConge dialog = null;
                            try {
                                dialog = new CreateConge();
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            dialog.pack();
                            dialog.setResizable(false);
                            dialog.setLocationRelativeTo(null);
                            dialog.setVisible(true);
                        }
                    }
                }

            }

        });

        panel.add(deleteButton);
        panel.add(editButton);
        panel.add(readButton);
        panel.add(generatePdfButton);
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return panel;
    }

    public Object getCellEditorValue() {
        return buttonText;
    }

    private void refreshTable(JTable table, String tableName) {
        if (tableName.equals("Employer")) {
            Employer temp = new Employer();
            List<Employer> employers = temp.listerEmployer();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            for (Employer employer : employers) {
                Object[] rowData = {
                        employer.getNumEmp(),
                        employer.getNom(),
                        employer.getPrenom(),
                        employer.getPoste(),
                        employer.getSalaire(),
                };
                model.addRow(rowData);
            }
        } else if (tableName.equals("Pointage")) {
            Pointage temp = new Pointage();
            List<Pointage> pointages = Pointage.listerPointage();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            for (Pointage pointage : pointages) {
                Object[] rowData = {
                        pointage.getId(),
                        pointage.getDatePointage(),
                        pointage.getNumEmp(),
                        pointage.getPointage(),
                        "Action"
                };

                model.addRow(rowData);
            }
        } else if (tableName.equals("Conge")) {
            Conge temp = new Conge();
            List<Conge> conges = temp.lister();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            for (Conge conge : conges) {
                Object[] rowData = {
                        conge.getNumConge(),
                        conge.getNumEmp(),
                        conge.getNbrjr(),
                        conge.getMotif(),
                        conge.getDateDemande(),
                        conge.getDateRetour(),

                };

                model.addRow(rowData);
            }
        }
    }

    private ImageIcon loadIcon(String fileName, int size) {
        String filePath = "resources/" + fileName;
        File imageFile = new File(filePath);
        String imagePath = imageFile.getAbsolutePath();

        System.out.println(imagePath);
        ImageIcon icon = new ImageIcon(imagePath);
        if (icon != null && icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            Image image = icon.getImage();
            Image scaledImage = image.getScaledInstance(size, size, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } else {
            System.err.println("Failed to load icon: " + filePath);
            return null;
        }

    }
}
