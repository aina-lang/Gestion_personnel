package models;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.File;

public class ButtonRenderer extends JPanel implements TableCellRenderer {
    private final JButton deleteButton;
    private final JButton editButton;
    private final JButton readButton;
    private final JButton generatePdfButton;

    public ButtonRenderer() {
        setOpaque(true);
        setBorder(new EmptyBorder(10, 0, 0, 0));

        setBackground(Color.WHITE);
        deleteButton = new JButton();
        deleteButton.setIcon(loadIcon("delete.png", 15));
        // deleteButton.setBackground(null);
        // deleteButton.setBorder(null);

        editButton = new JButton();
        editButton.setIcon(loadIcon("edit.png", 15));
        //editButton.setBackground(null);
        // editButton.setBorder(null);

        readButton = new JButton();
        readButton.setIcon(loadIcon("book.png", 15));
        // readButton.setBackground(null);
        //readButton.setBorder(null);

        generatePdfButton = new JButton();
        generatePdfButton.setIcon(loadIcon("pdf.png", 15));
        // generatePdfButton.setBackground(null);
        // generatePdfButton.setBorder(null);

        add(deleteButton);
        add(editButton);
        add(readButton);
        add(generatePdfButton);

    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
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
