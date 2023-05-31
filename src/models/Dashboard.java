package models;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JPanel {

    public Dashboard() {
        setPreferredSize(new Dimension(800, 600));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode("#333333"));
        JLabel titleLabel = new JLabel("Dashboard");
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        JPanel navPanel = new JPanel();
        navPanel.setBackground(Color.decode("#f4f4f4"));
        JButton homeButton = new JButton("Home");
        JButton usersButton = new JButton("Users");
        JButton ordersButton = new JButton("Orders");
        JButton settingsButton = new JButton("Settings");
        navPanel.add(homeButton);
        navPanel.add(usersButton);
        navPanel.add(ordersButton);
        navPanel.add(settingsButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 2, 20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel card1 = createCard("Welcome to the Dashboard", "This is a simple dashboard layout created using Java Swing.", Color.decode("#f4f4f4"), new Color(0x62B6B7));
        JPanel card2 = createCard("Statistics", "Display various statistics and charts here.", Color.decode("#e9e9e9"), new Color(0x3B9AE1));
        JPanel card3 = createCard("Recent Orders", "Show a list of recent orders.", Color.decode("#d4d4d4"), Color.YELLOW);
        JPanel card4 = createCard("Another Card", "This is an additional card.", Color.decode("#cccccc"), Color.RED);

        mainPanel.add(card1);
        mainPanel.add(card2);
        mainPanel.add(card3);
        mainPanel.add(card4);

        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(navPanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createCard(String title, String content, Color backgroundColor, Color contentBackgroundColor) {
        JPanel cardPanel = new JPanel();
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#cccccc")));
        cardPanel.setLayout(new BorderLayout());
        cardPanel.setBackground(backgroundColor);

        JLabel titleLabel = new JLabel(title);
        JTextArea contentArea = new JTextArea(content);
        contentArea.setBackground(contentBackgroundColor);
        contentArea.setEditable(false);

        cardPanel.add(titleLabel, BorderLayout.NORTH);
        cardPanel.add(contentArea, BorderLayout.CENTER);

        return cardPanel;
    }


}
