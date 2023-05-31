package models;

import javax.swing.*;
import java.awt.*;

public class MessageBox extends JOptionPane {
    private String message;
    private String objectName;
    private String boxTitle;

    public MessageBox(String message, String objectName, String boxTitle) {
        this.message = message;
        this.objectName = objectName;
        this.boxTitle = boxTitle;
        setBackground(Color.WHITE);
        setVisible(true);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getBoxTitle() {
        return boxTitle;
    }

    public void setBoxTitle(String boxTitle) {
        this.boxTitle = boxTitle;
    }

    public void showMessageDialog() {
        showMessageDialog(null, this.message);
    }


}
