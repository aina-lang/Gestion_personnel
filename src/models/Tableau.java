package models;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;

public class Tableau extends JTable {

    private Object objectModel;
    private DefaultTableModel model;
    private String[] columnNames;

    public Tableau() {
    }

    public Tableau(Object model, String[] columnNames, DefaultTableModel dm, int nbColumns) throws IOException {
        this.model = dm;
        this.columnNames = columnNames;
        this.objectModel = model;
        this.setRowHeight(50);
        this.setModel(this.model);
        setBackground(new Color(0xffffff));
        TableColumn buttonColumn = this.getColumnModel().getColumn(nbColumns);
        buttonColumn.setMinWidth(150);
        buttonColumn.setCellEditor(new ButtonEditor(this, model.getClass().getSimpleName()));
        buttonColumn.setCellRenderer(new ButtonRenderer());
        CustomHeaderRenderer headerRenderer = new CustomHeaderRenderer();
        getTableHeader().setDefaultRenderer(headerRenderer);
        DefaultTableCellRenderer cellRenderer = new CustomCellRenderer();
        setDefaultRenderer(Object.class, cellRenderer);
        TableColumn firstColumn = getColumnModel().getColumn(0);
        firstColumn.setPreferredWidth(25);

        // Définir l'éditeur de cellules en premier
        DefaultCellEditor cellEditor = (DefaultCellEditor) buttonColumn.getCellEditor();
        cellEditor.setClickCountToStart(1);
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public Object getObjectModel() {
        return objectModel;
    }

    public void setObjectModel(Object objectModel) {
        this.objectModel = objectModel;
    }

    public void refreshTable(Object objectModel, String listMethod, String[] method, Tableau tableau) throws SQLException {
        // Effacer les données existantes du modèle de tableau
        this.model.setRowCount(0);

        try {
            // Récupérer la classe de l'objet modèle
            Class<?> modelClass = objectModel.getClass();

            // Obtenir la méthode spécifiée à partir du nom de la méthode
            Method methodObj = modelClass.getMethod(listMethod);

            // Appeler la méthode spécifiée pour obtenir la liste d'objets
            List<?> objects = (List<?>) methodObj.invoke(objectModel);

            // Parcourir la liste d'objets et ajouter les données au modèle de tableau
            for (Object obj : objects) {
                Object[] rowData = new Object[method.length];

                for (int i = 0; i < method.length; i++) {
                    Method getterMethod = modelClass.getMethod("get" + capitalize(method[i]));
                    rowData[i] = getterMethod.invoke(obj);
                    // System.out.println(rowData[i]);
                }

                model.addRow(rowData);
            }

            // Rafraîchir l'affichage du tableau
            tableau.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Méthode utilitaire pour mettre en majuscule la première lettre d'une chaîne de caractères
    private String capitalize(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    private class CustomHeaderRenderer extends DefaultTableCellRenderer {
        private final int PADDING = 15;

        public CustomHeaderRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
            setFont(new Font("Arial", Font.BOLD, 14));
            setForeground(Color.WHITE);
            setBackground(new Color(0x0E8388));

        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            // Personnalisez l'apparence des en-têtes de colonnes ici
            // Par exemple, vous pouvez définir une couleur de fond, une couleur de texte, etc.
            setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
            return this;
        }
    }

    private class CustomCellRenderer extends DefaultTableCellRenderer {
        private final int PADDING = 10;

        public CustomCellRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
            setFont(new Font("Arial", Font.PLAIN, 12));
            setForeground(Color.BLACK);
            setOpaque(true);
            setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Check if the column index is the last column
            if (column == table.getColumnCount() - 1) {
                // Set a background color for the last column
                setBackground(Color.YELLOW);
            } else {
                // Set different background colors for odd and even rows
                if (row % 2 == 0) {
                    setBackground(Color.WHITE);
                } else {
                    setBackground(Color.LIGHT_GRAY);
                }
            }

            return this;
        }
    }

}
