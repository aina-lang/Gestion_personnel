package models;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Frame extends JFrame {
    private JPanel PrincipalLayout;
    private JPanel Header;
    private JPanel layoutCentral;
    private JPanel sideBar;
    private JButton currentButton;

    public Frame() throws IOException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, BadLocationException, SQLException, NoSuchMethodException {
        init();
        setContentPane(PrincipalLayout);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int minWidth = (int) (screenSize.getWidth() * 0.8);
        int minHeight = (int) (screenSize.getHeight() * 0.8);
        setMinimumSize(new Dimension(minWidth, minHeight));
        setLocationRelativeTo(null);
        setBackground(new Color(0x121212));
        setVisible(true);
        pack();
    }

    private void init() throws IOException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, BadLocationException, SQLException, NoSuchMethodException {


        PrincipalLayout = new JPanel(new BorderLayout());

        /************ CODE POUR LE HEADER ****************/

        this.Header = new JPanel();
        this.Header.setPreferredSize(new Dimension(getWidth(), 50));
        this.Header.setBackground(new Color(255, 255, 255));

        JLabel title = new JLabel("GESTION DE PERSONNELS");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16)); // Increase the font size and make it bold
        title.setHorizontalAlignment(JLabel.LEFT); // Align the title to the left

        this.Header.setLayout(new BorderLayout()); // Set the layout manager to BorderLayout

// Create an empty border with a margin of 10 pixels on the left side
        Border emptyBorder = BorderFactory.createEmptyBorder(0, 10, 0, 0);

        title.setBorder(emptyBorder); // Apply the empty border to the title label
        title.setHorizontalAlignment(JLabel.LEFT); // Align the title to the left
        this.Header.add(title);


        // Add the Header to the top of the PrincipalLayout
        PrincipalLayout.add(Header, BorderLayout.NORTH);

        /************ CODE POUR LA BARRE LATERALE ****************/

        int marginTop = 70;

        this.sideBar = new JPanel();
        BoxLayout boxLayout = new BoxLayout(sideBar, BoxLayout.Y_AXIS);
        this.sideBar.setLayout(boxLayout);
        this.sideBar.setBorder(BorderFactory.createEmptyBorder(marginTop, 0, 0, 0));
        this.sideBar.setPreferredSize(new Dimension(200, getHeight()));
        this.sideBar.setBackground(new Color(0x2C3333));

        addButtonToSideBar("Dashboard", "dashboard.png", sideBar, PanelType.DASHBOARD);
        addButtonToSideBar("Employers", "employe.png", sideBar, PanelType.EMPLOYER);
        addButtonToSideBar("Pointages", "calendar.png", sideBar, PanelType.POINTAGE);
        addButtonToSideBar("Conges", "presence.png", sideBar, PanelType.CONGE);
        addButtonToSideBar("About", "about.png", sideBar, PanelType.ABOUT);
        // Select the first button by default
        currentButton = (JButton) sideBar.getComponent(1);
        currentButton.setBackground(new Color(0x0E8388));

        this.PrincipalLayout.add(sideBar, BorderLayout.WEST);

        /************ CODE POUR LE PANNEAU CENTRAL ****************/

        this.layoutCentral = new JPanel(new CardLayout());

        this.layoutCentral.setBackground(new Color(0xEFFBFF));
        this.PrincipalLayout.add(layoutCentral, BorderLayout.CENTER);

        setPanel(PanelType.DASHBOARD);
    }

    private void addButtonToSideBar(String label, String iconName, JPanel sideBar, PanelType type) {
        JButton button = new JButton(label);
        Dimension buttonSize = new Dimension(Integer.MAX_VALUE, 50);
        int verticalGap = 10;
        button.setMaximumSize(buttonSize);

        // Ajouter une bordure à gauche
        Border leftBorder = BorderFactory.createMatteBorder(0, 5, 0, 0, Color.WHITE);

        // Appliquer la bordure à gauche au bouton
        button.setBorder(BorderFactory.createCompoundBorder(leftBorder, button.getBorder()));

        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBackground(new Color(0x2C3333));
        button.setForeground(Color.white);
        ImageIcon icon = loadIcon(iconName, 40);
        if (icon != null) {
            button.setIcon(icon);
        }
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0x0E8388));
                // currentButton.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (currentButton != button) {
                    button.setBackground(null);
                    //button.setForeground(Color.white);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (currentButton != null) {
                    currentButton.setBackground(null);
                    // currentButton.setForeground(null);
                }
                currentButton = button;
                currentButton.setBackground(new Color(0x0E8388));
                // currentButton.setForeground(Color.WHITE);
                try {
                    setPanel(type);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (InstantiationException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (NoSuchMethodException ex) {
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

    private void setPanel(PanelType type) throws IOException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, BadLocationException, SQLException, NoSuchMethodException {
        JPanel panel = null;
        switch (type) {
            case DASHBOARD:
                panel = new Dashboard();

                break;
            case CONGE:
                panel = new CongePanel();
                break;
           /*  case POINTAGE:
                panel = new PointagePanel();

                break;*/
            case EMPLOYER:
                panel = new EmployerPanel();

                break;
            case ABOUT:
                panel = new AboutPanel();
                break;
        }

        panel.setBackground(new Color(0xffffff));
        layoutCentral.removeAll();
        layoutCentral.add(panel, BorderLayout.CENTER);
        layoutCentral.revalidate();
        layoutCentral.repaint();

    }

    private ImageIcon loadIcon(String fileName, int size) {
        String filePath = "resources/" + fileName;
        File imageFile = new File(filePath);
        String imagePath = imageFile.getAbsolutePath();

        //System.out.println(imagePath);
        ImageIcon icon = new ImageIcon(imagePath);
        if (icon != null && icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            Image image = icon.getImage();
            Image scaledImage = image.getScaledInstance(size, size, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } else {
            //System.err.println("Failed to load icon: " + filePath);
            return null;
        }

    }


    public enum PanelType {
        DASHBOARD,
        EMPLOYER,
        POINTAGE,
        CONGE,
        ABOUT,
    }
}

/*setPanel(PanelType.DASHBOARD);*/