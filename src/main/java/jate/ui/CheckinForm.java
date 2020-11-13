package jate.ui;

import static java.awt.GridBagConstraints.HORIZONTAL;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class CheckinForm extends JFrame implements ItemListener {

    private final JPanel forms;

    public CheckinForm(int numberOfPanels) {
        forms = new JPanel(new CardLayout());

        JPanel popupMenuPanel = new JPanel();
        createPopupMenu(numberOfPanels, popupMenuPanel);

        JFrame frame = createFrame();
        frame.add(popupMenuPanel, BorderLayout.PAGE_START);
        frame.add(forms, BorderLayout.CENTER);
    }

    private void createPopupMenu(int numberOfPanels, JPanel popupMenuPanel) {
        JComboBox popupMenu = new JComboBox(createForms(numberOfPanels).toArray());
        popupMenu.setEditable(false);
        popupMenu.addItemListener(this);
        popupMenuPanel.add(popupMenu);
    }

    private List<String> createForms(int numberOfPanels) {
        List<String> comboBoxItems = new ArrayList<>();
        for (int i = 1; i <= numberOfPanels; i++) {
            forms.add(createSingleForm(i), "Person number " + i);
            comboBoxItems.add("Person number " + i);
        }
        return comboBoxItems;
    }

    private JFrame createFrame() {
        var frame = new JFrame("Personal information form");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);
        frame.setVisible(true);
        return frame;
    }

    private JPanel createSingleForm(int number) {
        JPanel form = new JPanel();
        GridBagLayout layoutManager = new GridBagLayout();
        form.setLayout(layoutManager);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.weighty = 1;

        constraints.ipady = 10;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = HORIZONTAL;

        constraints.gridy = 1;
        labelTemplate(form, constraints, "Person number " + number);
        constraints.gridy++;
        addName(form, constraints);
        constraints.gridy++;
        labelAndTextboxTemplate(form, constraints, "Date of birth:");
        labelAndTextboxTemplate(form, constraints, "Place of birth:");
        constraints.gridy++;
        labelAndTextboxTemplate(form, constraints, "Address:");
        labelAndTextboxTemplate(form, constraints, "City:");
        constraints.gridy++;

        labelAndTextboxTemplate(form, constraints, "Country:");
        labelAndTextboxTemplate(form, constraints, "Nationality:");
        constraints.gridy++;
        labelTemplate(form, constraints, "Contact:");
        constraints.gridy++;
        labelAndTextboxTemplate(form, constraints, "Telephone:");
        labelAndTextboxTemplate(form, constraints, "Email: ");
        constraints.gridy++;
        return form;
    }

    private void addName(JPanel panel, GridBagConstraints constraints) {
        labelAndTextboxTemplate(panel, constraints, "Name:");

        JTextField lastName = new JTextField(10);
        lastName.setFont(new Font("Arial", Font.PLAIN, 15));
        lastName.setEditable(true);
        panel.add(lastName, constraints);
    }

    private void labelAndTextboxTemplate(JPanel panel, GridBagConstraints constraints, String s) {
        JLabel date = new JLabel(s);
        date.setFont(new Font("Arial", Font.PLAIN, 15));
        panel.add(date, constraints);

        JTextField dateTextBox = new JTextField(10);
        dateTextBox.setFont(new Font("Arial", Font.PLAIN, 15));
        dateTextBox.setEditable(true);
        panel.add(dateTextBox, constraints);
    }

    private void labelTemplate(JPanel panel, GridBagConstraints constraints, String name) {
        JLabel label = new JLabel(name);
        label.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(label, constraints);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        CardLayout cardLayout = (CardLayout) (forms.getLayout());
        cardLayout.show(forms, (String) e.getItem());
    }
}
