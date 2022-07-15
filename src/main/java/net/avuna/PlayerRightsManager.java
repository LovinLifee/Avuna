package net.avuna;

import lombok.SneakyThrows;
import net.avuna.tasks.security.PlayerPermissions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

public class PlayerRightsManager implements ActionListener {

    private static long permissions = PlayerPermissions.PLAYER;
    private static JLabel outputLabel = new JLabel("<html>Player permissions: <font color='green'>1</font></html>");

    public static void main(String[] args) {
        JFrame frame = new JFrame("Player Permissions Generator");
        frame.setSize(new Dimension(380, 250));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3));
        ActionListener listener = new PlayerRightsManager();
        generateButtons(buttonPanel, listener);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(buttonPanel);
        JPanel outputPanel = new JPanel();
        outputPanel.add(outputLabel);
        splitPane.setBottomComponent(outputPanel);
        splitPane.setDividerLocation(150);
        splitPane.setEnabled(false);
        frame.add(splitPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void generateButtons(JPanel panel, ActionListener listener) {
        String[] permissions = {"Player", "Donator", "Super Donator", "Extreme Donator",
                "Helper", "Moderator", "Administrator", "Owner", "Developer"};
        for(String p : permissions) {
            JCheckBox checkbox = new JCheckBox(p, false);
            if(p.equalsIgnoreCase("Player")) {
                checkbox.setSelected(true);
                checkbox.setEnabled(false);
            }
            checkbox.addActionListener(listener);
            panel.add(checkbox);
        }
    }

    @Override
    @SneakyThrows
    public void actionPerformed(ActionEvent e) {
        if(!(e.getSource() instanceof JCheckBox)) {
            return;
        }
        JCheckBox checkBox = (JCheckBox) e.getSource();
        String fieldName = checkBox.getActionCommand().toUpperCase().replaceAll("\\s", "_");
        Field field = PlayerPermissions.class.getDeclaredField(fieldName);
        long value = field.getLong(null);
        permissions += checkBox.isSelected() ? value : -value;
        outputLabel.setText(String.format("<html>Player permissions: <font color='green'>%d</font></html>", permissions));
    }
}
