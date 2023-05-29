package models;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EmployerPanel extends JPanel {
    public Tableau table;
    public DefaultTableModel model;
    private JTextField searchField;
    private JLabel titleLabel;
    private JButton addButton;

    public EmployerPanel() {
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
        String[] columnNames = {"numero", "Nom", "Prenom", "Poste", "Salaire", "Action"};
        model = new DefaultTableModel(columnNames, 0);
        table = new Tableau(new Employer(), columnNames, model, 5);
        table.refreshTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(30, 10, 30, 10));
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));

        // Bouton d'ajout
        addButton = new JButton("Ajouter un employé");
        buttonContainer.add(Box.createHorizontalGlue());
        buttonContainer.add(addButton);

        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AddEmploye dialog = new AddEmploye();
                dialog.pack();
                dialog.setResizable(false);
                dialog.setLocationRelativeTo(EmployerPanel.this);
                dialog.setVisible(true);
                table.refreshTable(table);
            }
        });

        add(buttonContainer, BorderLayout.SOUTH);
    }

}
