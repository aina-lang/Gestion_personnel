package models;

import com.raven.datechooser.DateChooser;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EmployerPanel extends JPanel {
    public Tableau table;

    public JTabbedPane tabbedPane;
    public DefaultTableModel model;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextField nameField;
    private DateChooser dateChooser1;
    private DateChooser dateChooser2;
    private JButton showDatePicker1;
    private JButton showDatePicker2;
    private int currentTab;

    public EmployerPanel() throws IOException, SQLException, NoSuchMethodException {
        init();
    }

    private void init() throws IOException, SQLException, NoSuchMethodException {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(0xffffff));


        // Styliser le header du JTabbedPane
        UIManager.put("TabbedPane.background", new ColorUIResource(Color.WHITE));
        UIManager.put("TabbedPane.tabAreaBackground", new ColorUIResource(Color.WHITE));
        UIManager.put("TabbedPane.borderHightlightColor", new ColorUIResource(Color.WHITE));
        UIManager.put("TabbedPane.darkShadow", new ColorUIResource(Color.WHITE));
        UIManager.put("TabbedPane.focus", new ColorUIResource(Color.WHITE));
        UIManager.put("TabbedPane.selected", new ColorUIResource(new Color(0x2E4F4F)));
        UIManager.put("TabbedPane.selectHighlight", new ColorUIResource(Color.WHITE));
        UIManager.put("TabbedPane.light", new ColorUIResource(Color.WHITE));
        UIManager.put("TabbedPane.highlight", new ColorUIResource(Color.WHITE));
        UIManager.put("TabbedPane.shadow", new ColorUIResource(Color.WHITE));
        UIManager.put("TabbedPane.tabInsets", new Insets(5, 10, 5, 10));
        UIManager.put("TabbedPane.tabAreaInsets", new Insets(10, 0, 10, 0));
        UIManager.put("TabbedPane.font", new FontUIResource("Arial", Font.BOLD, 13));

        // Styliser le contenu du JTabbedPane
        UIManager.put("TabbedPane.contentAreaColor", new ColorUIResource(Color.WHITE));
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(10, 10, 10, 10));

        JPanel headerPanel = new JPanel(new BorderLayout());
        JPanel headerPanel1 = new JPanel(new BorderLayout());

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.setBorder(new EmptyBorder(20, 0, 10, 0));

        JLabel startDateLabel = new JLabel("Date de début:");
        startDateField = new JTextField();
        startDateField.setPreferredSize(new Dimension(100, 30));
        startDateField.setBackground(Color.white);
        startDateField.setEnabled(false);
        JLabel endDateLabel = new JLabel("Date de fin:");
        endDateField = new JTextField();
        endDateField.setPreferredSize(new Dimension(100, 30));
        endDateField.setBackground(Color.white);
        endDateField.setEnabled(false);
        dateChooser1 = new DateChooser();
        dateChooser1.setTextRefernce(startDateField);
        dateChooser2 = new DateChooser();
        dateChooser2.setTextRefernce(endDateField);


        showDatePicker1 = new JButton("...");
        showDatePicker2 = new JButton("...");

        datePanel.add(startDateLabel);
        datePanel.add(startDateField);
        datePanel.add(showDatePicker1);
        datePanel.add(endDateLabel);
        datePanel.add(endDateField);
        datePanel.add(showDatePicker2);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBorder(new EmptyBorder(20, 0, 10, 0));
        JLabel nameLabel = new JLabel("Nom ou prénom:");
        nameField = new JTextField();

        nameField.setToolTipText("Effectuez votre  recherche ici");
        nameField.setPreferredSize(new Dimension(150, 30));

        searchPanel.add(nameLabel);
        searchPanel.add(nameField);

        headerPanel.add(datePanel, BorderLayout.WEST);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        String filePath = "resources/search.png";
        File imageFile = new File(filePath);
        String imagePath = imageFile.getAbsolutePath();


        ImageIcon icon = new ImageIcon(imagePath);

        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);

        //DatePickerPanel datePicker=new DatePickerPanel();
        // datePicker.setVisible(true);
        headerPanel1.add(new JLabel(""), BorderLayout.NORTH);

        headerPanel1.setPreferredSize(new Dimension(500, 50));
        headerPanel1.setBackground(new Color(0x2C3333));
        add(headerPanel1, BorderLayout.NORTH);

        String[] columnNamesEmploye = {"Numero", "Nom", "Prenom", "Poste", "Salaire", "Action"};
        String[] methodEmploye = {"numEmp", "Nom", "Prenom", "Poste", "Salaire"};
        model = new DefaultTableModel(columnNamesEmploye, 0);
        table = new Tableau(new Employer(), columnNamesEmploye, model, 5);
        table.refreshTable(new Employer(), "listerEmployer", methodEmploye, table);


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(30, 10, 30, 10));
        scrollPane.setBackground(new Color(0xffffff));
        JLabel label1 = new JLabel("label1");
        JLabel label2 = new JLabel("label2");
        JLabel label3 = new JLabel("label3");
        Border border = BorderFactory.createLineBorder(Color.BLUE, 2); // Exemple de bordure avec une couleur bleue et une épaisseur de 2 pixels

        JPanel tab1Panel = new JPanel(new BorderLayout());
        tab1Panel.setBackground(new Color(0xffffff));
        tab1Panel.add(headerPanel, BorderLayout.NORTH);
        tab1Panel.add(scrollPane, BorderLayout.CENTER);

        // Ajouter les nouvelles données à la table
        String[] columnNamesConge = {"Nom", "Prenom", "Poste", "Nombre de conge", "Nombre de conge restant", "Nombre du conge qu'il peut rester", "Action"};
        String[] methodConge = {"Nom", "Prenom", "Poste", "NombreJours", "congeRestants", "joursRestants"};
        // Créer le tableau pour l'onglet "Employés en congé"
        DefaultTableModel congeModel = new DefaultTableModel(columnNamesConge, 0);
        Tableau congeTableau = new Tableau(new Employer(), columnNamesConge, congeModel, 6);
        congeTableau.setModel(congeModel);
        // Créer un JScrollPane pour afficher le tableau
        JScrollPane congeScrollPane = new JScrollPane(congeTableau);
        // Ajouter le JScrollPane au JPanel de l'onglet "Employés en congé"
        JPanel congePanel = new JPanel(new BorderLayout());
        congePanel.add(congeScrollPane, BorderLayout.CENTER);
        // Rafraîchir la table avec les employés en congé

        congeTableau.refreshTable(new Employer(), "getEmployeesOnLeaveNow", methodConge, congeTableau);


        tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(border);
        tabbedPane.setBackground(new Color(0xffffff));
        tabbedPane.addTab("Tous les employés", tab1Panel);
        tabbedPane.addTab("Employés en congé", congePanel);
        tabbedPane.addTab("Employés absents", label3);


        JEditorPane helpPane = new JEditorPane();
        helpPane.setEditable(false);

        tabbedPane.setBorder(null);
        tabbedPane.setToolTipTextAt(0, "Vous verrez ici la liste de tous les employés dans l'entreprise");
        tabbedPane.setBorder(new EmptyBorder(20, 0, 20, 0));
        tabbedPane.setForegroundAt(tabbedPane.getSelectedIndex(), new Color(0xffffff));
        currentTab = tabbedPane.getSelectedIndex();
        add(tabbedPane, BorderLayout.CENTER);

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));

        JButton searchButton = new JButton();
        searchButton.setIcon(new ImageIcon(scaledImage));
        buttonContainer.add(Box.createHorizontalGlue());
        datePanel.add(searchButton);

        nameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTable();
            }

            private void updateTable() {
                try {
                    String keyword = nameField.getText();
                    List<Employer> matchingEmployees = new Employer().searchEmployees(keyword);
                    refreshEmployeeTable(matchingEmployees);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String startDate = startDateField.getText();
                String endDate = endDateField.getText();
                String name = nameField.getText();

                // Appel à la fonction getAbsentEmployeesForDate avec la plage de dates spécifiée
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Date parsedStartDate = dateFormat.parse(startDate);
                    Date parsedEndDate = dateFormat.parse(endDate);

                    List<Employer> absentEmployees = new Employer().getAbsentEmployeesForDate(parsedStartDate, parsedEndDate);
                    // Faire quelque chose avec la liste des employés absents...

                    // Rafraîchir la table avec les nouvelles données
                    refreshEmployeeTable(absentEmployees);

                } catch (ParseException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        tabbedPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (currentTab == tabbedPane.getSelectedIndex()) {
                    return;
                }

                tabbedPane.setForegroundAt(currentTab, new Color(0x2C3333));
                currentTab = tabbedPane.getSelectedIndex();
                tabbedPane.setForegroundAt(tabbedPane.getSelectedIndex(), new Color(0xffffff));

            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                // tabbedPane.setForegroundAt(tabbedPane.getSelectedIndex(),new Color(0xffffff));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }
        });
        showDatePicker1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showDatePicker1ActionPerformed(evt);
            }
        });

        showDatePicker2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showDatePicker2ActionPerformed(evt);
            }
        });

        JButton addButton = new JButton("Ajouter un employé");
        addButton.setBackground(new Color(0x0081CF));
        addButton.setForeground(Color.WHITE);
        addButton.setBorderPainted(false);

        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AddEmploye dialog = new AddEmploye(EmployerPanel.this);
                dialog.pack();
                dialog.setResizable(false);
                dialog.setLocationRelativeTo(EmployerPanel.this);
                dialog.setVisible(true);
                try {
                    table.refreshTable(new Employer(), "listerEmployer", methodEmploye, table);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buttonContainer.add(Box.createVerticalStrut(50));
        buttonContainer.add(Box.createHorizontalGlue());
        buttonContainer.add(addButton);

        add(buttonContainer, BorderLayout.SOUTH);
        buttonContainer.add(Box.createHorizontalStrut(20));
    }


    public void refreshEmployeeTable(List<Employer> employees) {
        // Effacer les données existantes de la table
        DefaultTableModel model = table.getModel();
        model.setRowCount(0);

        // Ajouter les nouvelles données à la table
        for (Employer employee : employees) {
            Object[] rowData = {employee.getNumEmp(), employee.getNom(), employee.getPrenom()};
            model.addRow(rowData);
        }

        // Réappliquer le modèle à la table
        table.setModel(model);
    }


    private void showDatePicker1ActionPerformed(java.awt.event.ActionEvent evt) {
        dateChooser1.showPopup();
    }

    private void showDatePicker2ActionPerformed(java.awt.event.ActionEvent evt) {
        dateChooser2.showPopup();
    }
}


/*

 public void refreshTableByDateRangeAndName(String startDate, String endDate, String name) throws ParseException {
        try {
            // Convertir les chaînes de date en objets de type Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date start = dateFormat.parse(startDate);
            Date end = dateFormat.parse(endDate);

            // Effectuer la recherche dans la base de données en utilisant les paramètres de recherche
            String query = "SELECT * FROM POINTAGE WHERE datePointage BETWEEN ? AND ? AND pointage = ?";
            PreparedStatement statement = cnx.getConnection().prepareStatement(query);
            try {
                statement.setDate(1, new java.sql.Date(start.getTime()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            statement.setDate(2, new java.sql.Date(end.getTime()));
            statement.setString(3, "non");

            ResultSet resultSet = statement.executeQuery();

            // Mettre à jour la table avec les résultats de la recherche
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Effacer les lignes existantes dans la table

            while (resultSet.next()) {
                String numEmp = resultSet.getString("numEmp");
                Date datePointage = resultSet.getDate("datePointage");
                String pointage = resultSet.getString("pointage");

                // Ajouter les données dans la table
                model.addRow(new Object[]{numEmp, datePointage, pointage});
            }
        } catch (ParseException | SQLException e) {
            e.printStackTrace();
        }
    }
 */