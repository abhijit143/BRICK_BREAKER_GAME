package org.example;

import javax.swing.JFrame;

public class BrickBreaker {
    public static void main(String[] args) {
        JFrame obj = new JFrame();
        Gameplay gameplay = new Gameplay(); // whole game will be implemented here...in this class
        // set the bounds of the frame
        obj.setBounds(10,10,700,600);
        // set the Title of the Frame
        obj.setTitle("BrickBreaker");
        // for Resize the frame
        obj.setResizable(false);
        // Set Visibility
        obj.setVisible(true);
        // for the Exit
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gameplay);
    }
}