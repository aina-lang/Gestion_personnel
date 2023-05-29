package models;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class CongePanel extends JPanel {
    public Tableau table;
    public DefaultTableModel model;
    private JTextField searchField;
    private JLabel titleLabel;
    private JButton addButton;

    public CongePanel() {
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10)); // Ajoute un padding de 10 pixels autour du panneau

        // Conteneur pour le titre et le champ de recherche
        JPanel headerContainer = new JPanel();
        headerContainer.setLayout(new BoxLayout(headerContainer, BoxLayout.X_AXIS));

        // Titre à droite du champ de recherche
        titleLabel = new JLabel("Titre");
        titleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        headerContainer.add(titleLabel);

        // Espace flexible
        headerContainer.add(Box.createHorizontalGlue());

        // Champ de recherche
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setMaximumSize(new Dimension(200, 30)); // Fixe la taille souhaitée (200 de largeur, 30 de hauteur)
        headerContainer.add(searchField);

        add(headerContainer, BorderLayout.NORTH);

        // models.Tableau
        String[] columnNames = {"Numero", "Numero employe", "Nombres de Jours", "Motif", "Date du demande", "Date du retour", "Action"};
        model = new DefaultTableModel(columnNames, 0);
        int numConge;
        table = new Tableau(new Conge(), columnNames, model, 6);
        table.refreshTable(table);


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(30, 10, 30, 10));
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));

        // Bouton d'ajout
        addButton = new JButton("Ajouter un Conge");
        buttonContainer.add(Box.createHorizontalGlue());
        buttonContainer.add(addButton);

        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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
                table.refreshTable(table);
            }
        });

        add(buttonContainer, BorderLayout.SOUTH);
    }

    // Getters and setters
    public JTextField getSearchField() {
        return searchField;
    }

    public void setSearchField(JTextField searchField) {
        this.searchField = searchField;
    }

    public JLabel getTitleLabel() {
        return titleLabel;
    }

    public void setTitleLabel(JLabel titleLabel) {
        this.titleLabel = titleLabel;
    }

    public Tableau getTable() {
        return table;
    }

    public void setTable(Tableau table) {
        this.table = table;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public void setAddButton(JButton addButton) {
        this.addButton = addButton;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public void setModel(DefaultTableModel model) {
        this.model = model;
    }

    // CRUD methods
    public void deleteConge(int numConge) {
        // Delete the Conge with the specified numConge
    }

    public void updateConge(int numConge) {
        // Update the Conge with the specified numConge
    }

    public void createConge() {
        // Create a new Conge
    }

    public Conge[] listConge() {
        // Retrieve a Conge from the list
        return null;
    }
}
