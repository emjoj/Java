package cz.muni.fi.pv168.jate.hotelreservationsystem.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class CheckinDialog {

    private final JPanel forms;

    public JDialog getDialog() {
        return dialog;
    }

    public JComboBox getPopUpMenu() {
        return popUpMenu;
    }

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
            CheckinForm newForm = new CheckinForm(i, this);
            newForm.getSaveButton().addActionListener(e -> updateComboBoxItems(newForm.getFirstName(), newForm.getPersonNumber() - 1));
            forms.add(newForm.getForm(), "Person " + i);
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
                var confirmButton = (JButton) Arrays.stream(((JPanel) form).getComponents()).filter(a -> a instanceof JButton).collect(Collectors.toList()).get(1);
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

}
