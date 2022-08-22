package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.*;

public class MouseListen implements ActionListener {
    private LoF window = null;
    public MouseListen(LoF window){
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
        case "--- CLICK HERE TO CONTINUE ---":
            window.dispMemu();
            break;
        case "START":
            window.runGame();
            break;
        case "SCORES":
            window.dispScores();
            break;
        case "BACK":
            window.dispMemu();
            break;
        case "EXIT":
            System.exit(0);
            break;
        case "Click to Back to Menu":
            window.closeDialog();
            window.dispMemu();
            break;
        case "MENU":
            window.dispMemu();
            break;
        case "BACK TO MENU":
            window.dispMemu();
            window.getSound().StopGameBGM();
            break;
        case "OK":
            String string = window.getFM().textField.getText();
            window.getFM().setUserName(string);
        }
        
        // record name typed
		if ( e.getSource() == window.getFM().textField) {
            window.getFM().setUserName(String.format( "textField1: %s", command));
            window.getFM().userName = command;
            window.compareScore();
        }
    }
}