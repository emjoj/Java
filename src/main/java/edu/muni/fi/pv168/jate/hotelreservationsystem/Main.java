package edu.muni.fi.pv168.jate.hotelreservationsystem;

import edu.muni.fi.pv168.jate.hotelreservationsystem.ui.Dashboard;

import java.awt.EventQueue;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new Dashboard().show());
    }
}
