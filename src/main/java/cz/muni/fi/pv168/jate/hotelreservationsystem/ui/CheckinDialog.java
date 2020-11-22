package cz.muni.fi.pv168.jate.hotelreservationsystem.ui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.awt.GridBagConstraints.HORIZONTAL;

final class CheckinDialog {

    private final JPanel forms;
    private final JDialog dialog;

    CheckinDialog(Window owner, int numberOfPanels) {
        forms = new JPanel(new CardLayout());

        JPanel popupMenuPanel = new JPanel();
        createPopupMenu(numberOfPanels, popupMenuPanel);

        dialog = createDialog(owner);
        dialog.add(popupMenuPanel, BorderLayout.PAGE_START);
        dialog.add(forms, BorderLayout.CENTER);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void createPopupMenu(int numberOfPanels, JPanel popupMenuPanel) {
        JComboBox popupMenu = new JComboBox(createForms(numberOfPanels).toArray());
        popupMenu.setEditable(false);
        popupMenu.addItemListener(e -> {
            CardLayout cardLayout = (CardLayout) (forms.getLayout());
            cardLayout.show(forms, (String) e.getItem());
        });
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

    private JDialog createDialog(Window owner) {
        var dialog = new JDialog(owner);
        dialog.setTitle("Personal information form");
        dialog.setSize(1000, 500);
        dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        return dialog;
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
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> dialog.dispose());
        form.add(confirmButton, constraints);
        return form;
    }

    private void addName(JPanel panel, GridBagConstraints constraints) {
        labelAndTextboxTemplate(panel, constraints, "Name:");

        JTextField lastName = new JTextField(10);
        lastName.setEditable(true);
        panel.add(lastName, constraints);
    }

    private void labelAndTextboxTemplate(JPanel panel, GridBagConstraints constraints, String s) {
        JLabel date = new JLabel(s);
        panel.add(date, constraints);

        JTextField dateTextBox = new JTextField(10);
        dateTextBox.setEditable(true);
        panel.add(dateTextBox, constraints);
    }

    private void labelTemplate(JPanel panel, GridBagConstraints constraints, String name) {
        JLabel label = new JLabel(name);
        panel.add(label, constraints);
    }
}
