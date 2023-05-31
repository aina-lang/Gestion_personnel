package models;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class EmployerPanel extends JPanel {
    public Tableau table;
    public DefaultTableModel model;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextField nameField;

    public EmployerPanel() throws IOException {
        init();
    }

    private void init() throws IOException {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));


        JPanel headerPanel = new JPanel(new BorderLayout());
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.setBorder(new EmptyBorder(80, 0, 0, 0));

        JLabel startDateLabel = new JLabel("Date de début:");
        startDateField = new JTextField();
        startDateField.setPreferredSize(new Dimension(100, 30));

        JLabel endDateLabel = new JLabel("Date de fin:");
        endDateField = new JTextField();
        endDateField.setPreferredSize(new Dimension(100, 30));

        datePanel.add(startDateLabel);
        datePanel.add(startDateField);
        datePanel.add(endDateLabel);
        datePanel.add(endDateField);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBorder(new EmptyBorder(80, 0, 0, 0));
        JLabel nameLabel = new JLabel("Nom ou prénom:");
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(150, 30));

        searchPanel.add(nameLabel);
        searchPanel.add(nameField);

        headerPanel.add(datePanel, BorderLayout.WEST);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        String filePath = "resources/search.png";
        File imageFile = new File(filePath);
        String imagePath = imageFile.getAbsolutePath();

        System.out.println(imagePath);
        ImageIcon icon = new ImageIcon(imagePath);

        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);


        add(headerPanel, BorderLayout.NORTH);

        String[] columnNames = {"Numéro", "Nom", "Prénom", "Poste", "Salaire", "Action"};
        model = new DefaultTableModel(columnNames, 0);
        table = new Tableau(new Employer(), columnNames, model, 5);
        table.refreshTable(table);


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(30, 10, 30, 10));

        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));

        JButton searchButton = new JButton();
        searchButton.setIcon(new ImageIcon(scaledImage));
        buttonContainer.add(Box.createHorizontalGlue());
        datePanel.add(searchButton);

        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String startDate = startDateField.getText();
                String endDate = endDateField.getText();
                String name = nameField.getText();
                refreshTableByDateRangeAndName(startDate, endDate, name);
            }
        });

        JButton addButton = new JButton("Ajouter un employé");
        buttonContainer.add(addButton);

        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AddEmploye dialog = new AddEmploye(EmployerPanel.this);
                dialog.pack();
                dialog.setResizable(false);
                dialog.setLocationRelativeTo(EmployerPanel.this);
                dialog.setVisible(true);
                table.refreshTable(table);
            }
        });

        add(buttonContainer, BorderLayout.SOUTH);
    }

    private List<Employer> searchAbsentEmployeesByDateRangeAndName(String startDate, String endDate, String name) {
        /* List<Employer> employees = table.getEmployeeList();
        List<Employer> absentEmployees = new ArrayList<>();

        for (Employer employer : employees) {
            if (employer.getAbsenceDate().compareTo(startDate) >= 0 && employer.getAbsenceDate().compareTo(endDate) <= 0) {
                if (employer.getNom().equalsIgnoreCase(name) || employer.getPrenom().equalsIgnoreCase(name)) {
                    absentEmployees.add(employer);
                }
            }
        }*/

        return null;
    }

    private void refreshTableByDateRangeAndName(String startDate, String endDate, String name) {
        model.setRowCount(0);

        List<Employer> absentEmployees = searchAbsentEmployeesByDateRangeAndName(startDate, endDate, name);

        for (Employer employer : absentEmployees) {
            Object[] rowData = {employer.getNumEmp(), employer.getNom(), employer.getPrenom(), employer.getPoste(), employer.getSalaire(), ""};
            model.addRow(rowData);
        }
    }
}
