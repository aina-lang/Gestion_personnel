package models;

import com.raven.datechooser.DateChooser;
import com.raven.datechooser.SelectedDate;

import javax.swing.*;
import java.awt.*;

public class DatePickerPanel extends JPanel {
    private com.raven.datechooser.DateChooser dateChooser;
    private javax.swing.JButton getNow;
    private javax.swing.JButton getDate;
    private javax.swing.JButton getSelected;
    private javax.swing.JButton showDatePicker;
    private javax.swing.JTextField txtDate1;

    public DatePickerPanel() {
        initComponents();
    }

    private void initComponents() {

        dateChooser = new DateChooser();
        getNow = new JButton("Date aujourd'hui");
        getDate = new JButton("Date");
        getSelected = new JButton("Date selectionéé");
        showDatePicker = new JButton("Selectioner une date");
        txtDate1 = new JTextField();

        //add(dateChooser);
        // add(getDate);
        // add(getNow);
        // add(getSelected);
        add(showDatePicker);
        add(txtDate1);
        setPreferredSize(new Dimension(500, 50));
        setBackground(Color.BLUE);
        dateChooser.setTextRefernce(txtDate1);
        //  jButton2.setText("Now");
        getNow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        // jButton3.setText("Get Date");
        getDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        //getSelected.setText("Set Select");
        getSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        //jButton5.setText("...");
        showDatePicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        dateChooser.toDay();
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        SelectedDate d = dateChooser.getSelectedDate();
        System.out.println(d.getDay() + "-" + d.getMonth() + "-" + d.getYear());
        System.out.println("Text : " + txtDate1.getText());
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        dateChooser.setSelectedDate(new SelectedDate(5, 10, 2022));
        //  dateChooser.setSelectedDate(new Date());
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
        dateChooser.showPopup();
    }


}
