package models;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonRenderer extends JPanel implements TableCellRenderer {
    private final JButton deleteButton;
    private final JButton editButton;


    public ButtonRenderer() {
        setOpaque(true);

        deleteButton = new JButton("Supprimer");
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);

        editButton = new JButton("Modifier");

        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        add(deleteButton);
        add(editButton);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }

    public void addActionListenerToDeleteButton(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    public void addActionListenerToEditButton(ActionListener listener) {
        editButton.addActionListener(listener);
    }
}


