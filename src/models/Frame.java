package models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Frame extends JFrame {
    private JPanel PrincipalLayout;
    private JPanel Header;
    private JPanel layoutCentral;
    private JPanel sideBar;
    private JButton currentButton;

    public Frame() throws IOException {
        init();
        setContentPane(PrincipalLayout);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int minWidth = (int) (screenSize.getWidth() * 0.8);
        int minHeight = (int) (screenSize.getHeight() * 0.8);
        setMinimumSize(new Dimension(minWidth, minHeight));
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }

    private void init() throws IOException {


        PrincipalLayout = new JPanel(new BorderLayout());

        /************ CODE POUR LE HEADER ****************/

        this.Header = new JPanel();
        this.Header.setPreferredSize(new Dimension(getWidth(), 50));
        this.Header.setBackground(new Color(255, 255, 255));

        // Add the Header to the top of the PrincipalLayout
        PrincipalLayout.add(Header, BorderLayout.NORTH);

        /************ CODE POUR LA BARRE LATERALE ****************/

        int marginTop = 70;

        this.sideBar = new JPanel();
        BoxLayout boxLayout = new BoxLayout(sideBar, BoxLayout.Y_AXIS);
        this.sideBar.setLayout(boxLayout);
        this.sideBar.setBorder(BorderFactory.createEmptyBorder(marginTop, 0, 0, 0));
        this.sideBar.setPreferredSize(new Dimension(200, getHeight()));
        this.sideBar.setBackground(new Color(0x89d5c2));

        addButtonToSideBar("models.Dashboard", sideBar, PanelType.DASHBOARD);
        addButtonToSideBar("Employers", sideBar, PanelType.EMPLOYER);
        addButtonToSideBar("Pointages", sideBar, PanelType.POINTAGE);
        addButtonToSideBar("Conges", sideBar, PanelType.CONGE);

        // Select the first button by default
        currentButton = (JButton) sideBar.getComponent(1);
        currentButton.setBackground(new Color(0x3c7164));

        this.PrincipalLayout.add(sideBar, BorderLayout.WEST);

        /************ CODE POUR LE PANNEAU CENTRAL ****************/

        this.layoutCentral = new JPanel(new CardLayout());

        this.layoutCentral.setBackground(new Color(0xEFFBFF));
        this.PrincipalLayout.add(layoutCentral, BorderLayout.CENTER);

        setPanel(PanelType.DASHBOARD);
    }

    private void addButtonToSideBar(String label, JPanel sideBar, PanelType type) {
        JButton button = new JButton(label);
        Dimension buttonSize = new Dimension(Integer.MAX_VALUE, 50);
        int verticalGap = 10;
        button.setMaximumSize(buttonSize);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBackground(new Color(0x89d5c2));
        //button.setContentAreaFilled(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0x3c7164));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (currentButton != button) {
                    button.setBackground(null);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (currentButton != null) {
                    currentButton.setBackground(null);
                }
                currentButton = button;
                currentButton.setBackground(new Color(0x3c7164));
                try {
                    setPanel(type);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                JPanel panel = null;


                PrincipalLayout.add(Header, BorderLayout.NORTH);
                layoutCentral.revalidate();
                layoutCentral.repaint();

            }

        });

        sideBar.add(Box.createRigidArea(new Dimension(0, 10)));
        sideBar.add(button);
        sideBar.add(Box.createVerticalStrut(verticalGap));
    }

    private void setPanel(PanelType type) throws IOException {
        JPanel panel = null;
        switch (type) {
            case DASHBOARD:
                panel = new Dashboard();

                break;
            case CONGE:
                panel = new CongePanel();
                break;
            case POINTAGE:
                panel = new PointagePanel();

                break;
            case EMPLOYER:
                panel = new EmployerPanel();

                break;
        }

        panel.setBackground(new Color(0xFCF8FF));
        layoutCentral.removeAll();
        layoutCentral.add(panel, BorderLayout.CENTER);
        layoutCentral.revalidate();
        layoutCentral.repaint();

    }

    public enum PanelType {
        DASHBOARD,
        EMPLOYER,
        POINTAGE,
        CONGE
    }


}

/*setPanel(PanelType.DASHBOARD);*/