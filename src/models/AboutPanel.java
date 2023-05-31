package models;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AboutPanel extends JPanel {

    private JLabel label;
    private JTextPane textPane;

    public AboutPanel() throws IOException, BadLocationException {
        initialize();
    }

    private void initialize() throws IOException, BadLocationException {
        setLayout(new BorderLayout());

        label = new JLabel("ABOUT PAGE");
        add(label, BorderLayout.NORTH);

        textPane = new JTextPane();
        textPane.setContentType("text/html");

        String filePath = "src/models/index.html";
        Path path = Paths.get(filePath);
        byte[] htmlBytes = Files.readAllBytes(path);
        String htmlContent = new String(htmlBytes);

        // Parse the HTML content using Jsoup
        HTMLDocument htmlDocument = new HTMLDocument();
        HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
        htmlEditorKit.read(new StringReader(htmlContent), htmlDocument, 0);

        // Apply styles to the text pane
        StyleSheet styleSheet = htmlDocument.getStyleSheet();
        styleSheet.addRule("body { font-family: Arial, sans-serif; margin: 0; padding: 0; }");
        styleSheet.addRule("header { background-color: #333; color: #fff; padding: 10px; text-align: center; }");
        styleSheet.addRule("nav { background-color: #f4f4f4; padding: 10px; }");
        styleSheet.addRule("nav ul { list-style-type: none; margin: 0; padding: 0; }");
        styleSheet.addRule("nav ul li { display: inline-block; margin-right: 10px; }");
        styleSheet.addRule("section { padding: 20px; }");
        styleSheet.addRule(".card { border: 1px solid #ccc; border-radius: 4px; padding: 20px; margin-bottom: 20px; color: #333; }");
        styleSheet.addRule(".card:nth-child(odd) { background-color: #f4f4f4; }");
        styleSheet.addRule(".card:nth-child(even) { background-color: #e9e9e9; }");
        styleSheet.addRule(".card:nth-child(3n+0) { background-color: #d4d4d4; }");

        textPane.setEditorKit(htmlEditorKit);
        textPane.setDocument(htmlDocument);
        textPane.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textPane);
        add(scrollPane, BorderLayout.CENTER);
    }
}
