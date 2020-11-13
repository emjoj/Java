package jate.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ToolbarCreator {
    private JToolBar toolBar;

    public ToolbarCreator() {
        toolBar = new JToolBar(0);
        toolBar.setRollover(true);
        createButtons();
        EnableAllButtons();
        DisableButton((JButton) toolBar.getComponent(0));
    }

    public JToolBar getToolBar() {
        return toolBar;
    }

    private void createButtons() {
        toolBar.add(new ItemChangedAction("Overview"));
        toolBar.add(new ItemChangedAction("Calendar"));
        toolBar.add(new ItemChangedAction("New reservation"));
        toolBar.add(new ItemChangedAction("Reservations"));
        toolBar.add(new ItemChangedAction("Check-in"));
        toolBar.add(new ItemChangedAction("Check-out"));
    }

    private void EnableAllButtons() {
        for (int i = 0; i < toolBar.getComponentCount(); i++) {
            Component c = toolBar.getComponent(i);
            if (c instanceof JButton) {
                c.setEnabled(true);
                c.setBackground(Color.GRAY);
            }
        }
    }

    private void DisableButton(JButton button){
        button.setEnabled(false);
        button.setBackground(Color.CYAN);
    }

    private class ItemChangedAction extends AbstractAction {

        public ItemChangedAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            EnableAllButtons();
            var button = actionEvent.getSource();
            if (button instanceof JButton) {
                DisableButton((JButton) button);
            }
        }
    }
}

