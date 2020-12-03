package cz.muni.fi.pv168.jate.hotelreservationsystem.ui;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.awt.GridBagConstraints.HORIZONTAL;

final class CheckinDialog {

    private final JPanel forms;
    private final JDialog dialog;
    private final JComboBox popUpMenu;
    private final boolean[] areFormsFilled;

    CheckinDialog(Window owner, int numberOfPanels) {
        areFormsFilled = new boolean[numberOfPanels];
        Arrays.fill(areFormsFilled, false);

        forms = new JPanel(new CardLayout());

        JPanel popupMenuPanel = new JPanel();
        popUpMenu = createPopupMenu(numberOfPanels, popupMenuPanel);
        dialog = createDialog(owner);
        dialog.add(popupMenuPanel, BorderLayout.PAGE_START);
        dialog.add(forms, BorderLayout.CENTER);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }

    private JComboBox createPopupMenu(int numberOfPanels, JPanel popupMenuPanel) {
        JComboBox popupMenu = new JComboBox(createForms(numberOfPanels).toArray());
        popupMenu.setPreferredSize(new Dimension(100, 20));
        popupMenu.setEditable(false);
        popupMenu.addItemListener(e -> {
            CardLayout cardLayout = (CardLayout) (forms.getLayout());
            cardLayout.show(forms, (String) e.getItem());
        });
        popupMenuPanel.add(popupMenu);
        return popupMenu;
    }

    private List<String> createForms(int numberOfPanels) {
        List<String> comboBoxItems = new ArrayList<>();
        for (int i = 1; i <= numberOfPanels; i++) {
            forms.add(createSingleForm(i), "Person " + i);
            comboBoxItems.add("Person " + i);
        }
        return comboBoxItems;
    }

    private void updateComboBoxItems(String personName, int index) {
        String[] updatedItems = new String[popUpMenu.getItemCount()];
        for (int i = 0; i < popUpMenu.getItemCount(); i++) {
            if (i == index) {
                forms.add(forms.getComponent(i), personName, i);
                updatedItems[i] = personName;
                areFormsFilled[i] = true;
            } else {
                updatedItems[i] = popUpMenu.getItemAt(i).toString();
            }
        }

        DefaultComboBoxModel model = new DefaultComboBoxModel(updatedItems);
        popUpMenu.setModel(model);

        if (isAlreadyFilled())
            for (Component form : forms.getComponents()) {
                Component confirmButton = form.getComponentAt(203, 295);
                confirmButton.setEnabled(true);
            }
    }

    private boolean isAlreadyFilled() {
        for (boolean formFilled : areFormsFilled) {
            if (!formFilled) {
                return false;
            }
        }
        return true;
    }

    private JDialog createDialog(Window owner) {
        var dialog = new JDialog(owner);
        dialog.setTitle("Personal information form");
        dialog.setSize(600, 500);
        dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        return dialog;
    }

    private JPanel createSingleForm(int number) {
        List<JTextField> textFields = new ArrayList<>();

        JPanel form = new JPanel();
        GridBagLayout layoutManager = new GridBagLayout();
        form.setLayout(layoutManager);

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.ipady = 10;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = HORIZONTAL;

        constraints.gridy = 1;
        form.add(new JLabel("Person " + number), constraints);
        constraints.gridy++;

        form.add(new JLabel("Name:"), constraints);

        JTextField firstNameTextField = new JTextField(10);
        textFields.add(firstNameTextField);
        firstNameTextField.setEditable(true);
        form.add(firstNameTextField, constraints);

        JTextField lastNameTextField = new JTextField(14);
        textFields.add(lastNameTextField);
        lastNameTextField.setEditable(true);
        form.add(lastNameTextField, constraints);
        constraints.gridy++;

        form.add(new JLabel("Date of birth:"), constraints);

        DatePicker dateOfBirthPicker = new DatePicker();
        form.add(dateOfBirthPicker, constraints);
        constraints.gridy++;

        form.add(new JLabel("Identity card number:"), constraints);

        JTextField idCardTextField = new JTextField(10);
        textFields.add(idCardTextField);
        idCardTextField.setEditable(true);
        form.add(idCardTextField, constraints);
        constraints.gridy++;

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setEnabled(false);
        confirmButton.addActionListener(e -> dialog.dispose());

        JButton saveButton = new JButton("Save");
        saveButton.setEnabled(false);
        saveButton.addActionListener(e -> updateComboBoxItems(firstNameTextField.getText(), number - 1));
        form.add(saveButton, constraints);
        form.add(confirmButton, constraints);

        firstNameTextField.addCaretListener(e -> checkThatTextBoxesAreFilled(textFields, dateOfBirthPicker, saveButton));
        lastNameTextField.addCaretListener(e -> checkThatTextBoxesAreFilled(textFields, dateOfBirthPicker, saveButton));
        dateOfBirthPicker.addDateChangeListener(e -> checkThatTextBoxesAreFilled(textFields, dateOfBirthPicker, saveButton));
        idCardTextField.addCaretListener(e -> checkThatTextBoxesAreFilled(textFields, dateOfBirthPicker, saveButton));

        return form;
    }

    private void checkThatTextBoxesAreFilled(List<JTextField> textFields, DatePicker dateOfBirth, JButton saveButton) {
        List<String> contentOfTextFields = textFields.stream().map(JTextComponent::getText).collect(Collectors.toList());
        contentOfTextFields.add(dateOfBirth.getText());
        boolean areFilled = true;
        for (String textField : contentOfTextFields) {
            if (textField.isEmpty()) {
                areFilled = false;
                break;
            }
        }
        saveButton.setEnabled(areFilled);
    }

}
