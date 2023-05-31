package models;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;

public class PayrollPDFGenerator {
    public void generatePayrollPDF(String employeeName, String month, int salary) {
        // Demander à l'utilisateur de spécifier l'emplacement du fichier de sortie
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Sélectionner l'emplacement de sortie");
        fileChooser.setSelectedFile(new File(employeeName + "Pdf"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Fichiers PDF (*.pdf)", "pdf"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String outputFile = fileChooser.getSelectedFile().getAbsolutePath();

            try {
                // Créer un nouvel objet PdfWriter avec le chemin du fichier de sortie
                PdfWriter writer = new PdfWriter(outputFile);

                // Créer un nouvel objet PdfDocument avec le PdfWriter
                PdfDocument pdfDocument = new PdfDocument(writer);

                // Créer un nouvel objet Document avec le PdfDocument
                Document document = new Document(pdfDocument);

                // Ajouter le contenu de la fiche de paie
                Paragraph heading = new Paragraph("Fiche de paie")
                        .setFontSize(20)
                        .setBold();
                document.add(heading);

                document.add(new Paragraph(new Text("Nom de l'employé: " + employeeName)));
                document.add(new Paragraph(new Text("Mois: " + month)));
                document.add(new Paragraph(new Text("Salaire: " + salary + " AR")));

                // Fermer le document
                document.close();

                JOptionPane.showMessageDialog(null, "Fiche de paie générée avec succès!");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
