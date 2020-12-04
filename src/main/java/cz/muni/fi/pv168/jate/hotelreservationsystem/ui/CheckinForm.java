package cz.muni.fi.pv168.jate.hotelreservationsystem.ui;

import com.github.lgooddatepicker.components.DatePicker;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.Person;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.awt.GridBagConstraints.HORIZONTAL;

public class CheckinForm {

    private final JPanel form;
    private final CheckinDialog parent;
    private final int personNumber;

    private final JTextField firstNameTextField = new JTextField(10);
    private final JTextField lastNameTextField = new JTextField(14);
    private final DatePicker dateOfBirthPicker = new DatePicker();
    private final JTextField idCardTextField = new JTextField(10);
    private final JButton confirmButton = new JButton("Confirm");

    private final JButton saveButton = new JButton("Save");

    public CheckinForm(int personNumber, CheckinDialog parent) {
        this.parent = parent;
        this.personNumber = personNumber;
        form = createSingleForm(personNumber);
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

        textFields.add(firstNameTextField);
        firstNameTextField.setEditable(true);
        form.add(firstNameTextField, constraints);

        textFields.add(lastNameTextField);
        lastNameTextField.setEditable(true);
        form.add(lastNameTextField, constraints);
        constraints.gridy++;

        form.add(new JLabel("Date of birth:"), constraints);

        form.add(dateOfBirthPicker, constraints);
        constraints.gridy++;

        form.add(new JLabel("Identity card number:"), constraints);

        textFields.add(idCardTextField);
        idCardTextField.setEditable(true);
        form.add(idCardTextField, constraints);
        constraints.gridy++;

        confirmButton.setEnabled(false);

        saveButton.setEnabled(false);
        saveButton.addActionListener(e ->
                parent.addPerson(new Person(getFirstName(), getLastName(), getDateOfBirth(), getEvidenceID())));
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

    public JPanel getForm() {
        return form;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public String getFirstName() {
        return firstNameTextField.getText();
    }

    public int getPersonNumber() {
        return personNumber;
    }

    public String getLastName() {
        return lastNameTextField.getText();
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirthPicker.getDate();
    }

    public String getEvidenceID() {
        return idCardTextField.getText();
    }

}
