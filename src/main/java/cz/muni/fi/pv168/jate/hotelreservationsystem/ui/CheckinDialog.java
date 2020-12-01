package cz.muni.fi.pv168.jate.hotelreservationsystem.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        dialog.setVisible(true);
        dialog.pack();
    }

    private JComboBox createPopupMenu(int numberOfPanels, JPanel popupMenuPanel) {
        JComboBox popupMenu = new JComboBox(createForms(numberOfPanels).toArray());
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

        List<ComponentListener> a;
        if (isAlreadyFilled())
            for (Component form : forms.getComponents()) {
                Component confirmButton = form.getComponentAt(451, 293);
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
        dialog.setSize(1000, 500);
        dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        return dialog;
    }

    private JPanel createSingleForm(int number) {
 
        JPanel form = new JPanel();
        GridBagLayout layoutManager = new GridBagLayout();
        form.setLayout(layoutManager);

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.ipady = 10;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = HORIZONTAL;

        constraints.gridy = 1;
        JLabel personLabel = new JLabel("Person " + number);
        form.add(personLabel, constraints);
        constraints.gridy++;

        JLabel nameLabel = new JLabel("Name:");
        form.add(nameLabel, constraints);

        JTextField firstNameTextField = new JTextField(10);
        firstNameTextField.setEditable(true);
        form.add(firstNameTextField, constraints);

        JTextField lastNameTextField = new JTextField(10);
        lastNameTextField.setEditable(true);
        form.add(lastNameTextField, constraints);
        constraints.gridy++;

        JLabel dateOfBirthLabel = new JLabel("Date of birth:");
        form.add(dateOfBirthLabel, constraints);

        JTextField dateOfBirthTextField = new JTextField(10);
        dateOfBirthTextField.setEditable(true);
        form.add(dateOfBirthTextField, constraints);
        constraints.gridy++;

        JLabel idCardLabel = new JLabel("Identity card number:");
        form.add(idCardLabel, constraints);

        JTextField idCardTextField = new JTextField(10);
        idCardTextField.setEditable(true);
        form.add(idCardTextField, constraints);
        constraints.gridy++;

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setEnabled(false);
        confirmButton.addActionListener(e -> dialog.dispose());

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> updateComboBoxItems(firstNameTextField.getText(), number - 1));
        form.add(saveButton, constraints);
        form.add(confirmButton, constraints);

        return form;
    }
}
