package models;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.io.IOException;
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
        TableColumn buttonColumn = this.getColumnModel().getColumn(nbColumns);
        buttonColumn.setMinWidth(150);
        buttonColumn.setCellEditor(new ButtonEditor(this, model.getClass().getSimpleName()));
        buttonColumn.setCellRenderer(new ButtonRenderer());

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

    public void refreshTable(Tableau tableau) {
        // Effacer les données existantes du modèle de tableau
        this.model.setRowCount(0);

        // Vérifier le type de modèle d'objet
        if (this.objectModel instanceof Employer employerModel) {
            List<Employer> employers = employerModel.listerEmployer();

            for (Employer employer : employers) {
                Object[] rowData = {
                        employer.getNumEmp(),
                        employer.getNom(),
                        employer.getPrenom(),
                        employer.getPoste(),
                        employer.getSalaire()
                };
                this.model.addRow(rowData);
            }
        } else if (this.objectModel instanceof Conge congeModel) {
            List<Conge> conges = congeModel.lister();

            for (Conge conge : conges) {
                Object[] rowData = {
                        conge.getNumConge(),
                        conge.getNumEmp(),
                        conge.getNbrjr(),
                        conge.getMotif(),
                        conge.getDateDemande(),
                        conge.getDateRetour(),

                };

                this.model.addRow(rowData);
            }
        } else if (this.objectModel instanceof Pointage pointageModel) {
            List<Pointage> pointages = Pointage.listerPointage();

            for (Pointage pointage : pointages) {
                Object[] rowData = {
                        pointage.getId(),
                        pointage.getDatePointage(),
                        pointage.getNumEmp(),
                        pointage.getPointage(),
                };

                this.model.addRow(rowData);
            }
        }

        // Définir le modèle de tableau mis à jour
        tableau.setModel(this.model);
    }

}
