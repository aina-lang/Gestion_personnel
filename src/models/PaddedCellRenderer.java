package models;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class PaddedCellRenderer extends DefaultTableCellRenderer {
    private static final int PADDING_TOP = 5;
    private static final int PADDING_BOTTOM = 5;
    private static final int PADDING_LEFT = 10;
    private static final int PADDING_RIGHT = 10;

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setBorder(BorderFactory.createEmptyBorder(PADDING_TOP, PADDING_LEFT, PADDING_BOTTOM, PADDING_RIGHT));
        return this;
    }
}
