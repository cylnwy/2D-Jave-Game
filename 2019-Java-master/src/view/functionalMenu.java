package view;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import java.lang.String;

import controller.*;
import model.*;

public class functionalMenu {
    private MouseListen ML;
    private JDialog pauseDialog;
    private JDialog escDialog;
    private myPanel mp;
    private gameTimer timer;
    private soundEffect sound;
    private soundEffect walkingSound;
    private boolean isStoped = false;
    private LoF window;
    public String userName;
    public JTextField textField = new JTextField(8);
    private boolean Walking = false;

    public functionalMenu(LoF window) {
        ML = new MouseListen(window);
        this.window = window;
    }

    public void setSound(soundEffect sound) {
        this.sound = sound;
    }

    public void setWalkingSound(soundEffect walkingSound) {
        this.walkingSound = walkingSound;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Set button
    private JButton setButton(JButton jb, float alignment, Boolean alignX) {
        if (alignX) {
            jb.setAlignmentX(alignment);
        } else {
            jb.setAlignmentY(alignment);
        }
        jb.setMargin(new Insets(0, 0, 0, 0));
        jb.setFont(new Font("Default", Font.BOLD, 30));
        return jb;
    }

    // Welcom panel
    public JPanel welcome() {
        JPanel panel = new drawPanel(window.imageRes.welcome);
        panel.setLayout(null);

        JButton jb1 = new JButton("--- CLICK HERE TO CONTINUE ---");

        jb1.setContentAreaFilled(false); 
        jb1.setBorderPainted(false); 
        jb1.setForeground(Color.white);
        jb1.setBorder(BorderFactory.createRaisedBevelBorder()); 

        jb1.setBounds(380, 600, 350, 30);
        jb1.setMargin(new Insets(0, 0, 0, 0));
        jb1.setFont(new Font("Default", Font.ROMAN_BASELINE, 20));

        jb1.addActionListener(ML);

        panel.add(jb1);
        return panel;
    }

    // Menu panel
    public JPanel menu() {
        JPanel panel = new drawPanel(window.imageRes.menu);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton jb1 = new JButton("START");
        JButton jb2 = new JButton("SCORES");
        JButton jb3 = new JButton("EXIT");
        jb1 = setButton(jb1, Component.CENTER_ALIGNMENT, true);
        jb2 = setButton(jb2, Component.CENTER_ALIGNMENT, true);
        jb3 = setButton(jb3, Component.CENTER_ALIGNMENT, true);

        jb1.setContentAreaFilled(false); 
        jb1.setBorderPainted(false); 
        jb1.setForeground(Color.white);
        jb1.setBorder(BorderFactory.createRaisedBevelBorder()); 

        jb2.setContentAreaFilled(false); 
        jb2.setBorderPainted(false); 
        jb2.setForeground(Color.white);
        jb2.setBorder(BorderFactory.createRaisedBevelBorder()); 

        jb3.setContentAreaFilled(false); 
        jb3.setBorderPainted(false); 
        jb3.setForeground(Color.white);
        jb3.setBorder(BorderFactory.createRaisedBevelBorder()); 

        jb1.addActionListener(ML);
        jb2.addActionListener(ML);
        jb3.addActionListener(ML);

        panel.add(jb1);
        panel.add(Box.createRigidArea(new Dimension(20, 40)));
        panel.add(jb2);
        panel.add(Box.createRigidArea(new Dimension(20, 40)));
        panel.add(jb3);
        panel.setBorder(BorderFactory.createEmptyBorder(250, 5, 15, 5));

        return panel;
    }

    // high score panel
    public JPanel scores(String data) {
        int i;
        JPanel panel = new drawPanel(window.imageRes.score);
        panel.setLayout(null);

        Object[] columnNames = new Object[] { "#", "MEGICIAN", "TIME", "SCORE" };
        Object[][] rowData = new Object[10][4];
        String[] byPlayer = data.split("\n");
        for (i = 1; i < byPlayer.length; i++) {
            String[] details = byPlayer[i].split(",");
            rowData[i - 1][0] = i;
            for (int j = 1; j < details.length + 1; j++) {
                rowData[i - 1][j] = details[j - 1];
            }
        }

        for (; i < 11; i++) {
            rowData[i - 1][0] = i;
        }

       // Set table transparent and colour
        JTable table = new JTable(rowData, columnNames) {
            private static final long serialVersionUID = 7385924210025209738L;

            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (c instanceof JComponent) {
                    ((JComponent) c).setOpaque(false);
                }
                return c;
            }
        };
        table.setRowHeight(40);
        table.setShowGrid(false);
        table.setOpaque(false);
        table.getTableHeader().setFont(new Font("Default", Font.BOLD, 30));
        table.setFont(new Font("Default", Font.ROMAN_BASELINE, 20));
        table.setForeground(new Color(255, 255, 255));

        // import data
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
        cr.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, cr);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(185);
        table.getColumnModel().getColumn(2).setPreferredWidth(110);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setEnabled(false);

        // Set JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(325, 150, 498, 443);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Set JButton
        JButton jb1 = new JButton("BACK");
        jb1.setBounds(750, 650, 120, 40);
        jb1.setMargin(new Insets(0, 0, 0, 0));
        jb1.setFont(new Font("Default", Font.BOLD, 30));
        jb1.setContentAreaFilled(false); 
        jb1.setBorderPainted(false); 
        jb1.setForeground(Color.white);
        jb1.setBorder(BorderFactory.createRaisedBevelBorder()); 

        jb1.addActionListener(ML);

        panel.add(scrollPane);
        panel.add(jb1);

        return panel;
    }

    // Congrats panel
    public JPanel congrats(int[] time, int score) {
        JPanel panel = new drawPanel(window.imageRes.congrats, window, time, score);
        panel.setLayout(null);

        textField.setBounds(450, 330, 200, 25);
        textField.setFont(new Font(null, Font.PLAIN, 20));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.addActionListener(ML);

        panel.add(textField);

        return panel;
    }

    // rank panel + save result
    public JPanel rank(String data, int[] time, int score) {
        JPanel panel = new drawPanel(window.imageRes.score);
        panel.setLayout(null);
        String dispStr1;
        String dispStr2;
        String dispStr3;
        int i;

        String timeStr = "";
        timeStr += String.valueOf(time[0]);
        timeStr += String.valueOf(time[1]);
        timeStr += ":";
        timeStr += String.valueOf(time[2]);
        timeStr += String.valueOf(time[3]);

        String[] byPlayer = data.split("\n");
        String[] newByPlayer;
        if (byPlayer.length == 11) {
            newByPlayer = new String[10];
        } else {
            newByPlayer = new String[byPlayer.length];
        }

        for (i = 1; i < byPlayer.length; i++) {
            String[] details = byPlayer[i].split(",");
            if (Integer.parseInt(details[2]) <= score) {
                if (Integer.parseInt(details[2]) == score) {
                    String[] timetop = details[1].split(":");
                    int min =time[0] * 10 +time[1];
                    if (Integer.parseInt(timetop[0]) < min) {
                        continue;
                    } else if (Integer.parseInt(timetop[0]) == min) {
                        int sec =time[2] * 10 +time[3];
                        if (Integer.parseInt(timetop[0]) <= sec) {
                            continue;
                        }
                    }
                }
                
                System.arraycopy(byPlayer, 1, newByPlayer, 0, i - 1);
                newByPlayer[i - 1] = info2String(timeStr, score);
                if (byPlayer.length == 11) {
                    System.arraycopy(byPlayer, i + 1, newByPlayer, i, 10 - i);
                } else {
                    System.arraycopy(byPlayer, i, newByPlayer, i, byPlayer.length - i);
                }
                break;
            }
        }
        if (i == byPlayer.length && i != 11){
            System.arraycopy(byPlayer, 1, newByPlayer, 0, i - 1);
            newByPlayer[i - 1] = info2String(timeStr, score);
        }
        if (i != 11) {
            int m;
            String stringWt = "";
            for (m = 0; m < newByPlayer.length - 1; m++) {
                stringWt += newByPlayer[m];
                stringWt += "\n";
            }
            stringWt += newByPlayer[m];
            File f = new File("./res/scores.txt");
            try {
                txtRW.string2Txt(stringWt, f);
            } catch (Exception e) {
                e.printStackTrace();
            }

            dispStr1 = "CONGRATULATIONS, " + userName;
            dispStr2 = "YOU ARE NO. " + String.valueOf(i);
            dispStr3 = "WITH SCORE " + String.valueOf(mp.player.score) + " AND FINISHED IN " + timeStr;
        } else {
            dispStr1 = "SORRY, " + userName;
            dispStr2 = "YOU ARE NOT IN TOP 10";
            dispStr3 =  "WITH SCORE " + String.valueOf(mp.player.score) + " AND FINISHED IN " + timeStr;
        }

        JLabel lab1 = new JLabel(dispStr1, JLabel.CENTER);
        JLabel lab2 = new JLabel(dispStr2, JLabel.CENTER);
        JLabel lab3 = new JLabel(dispStr3, JLabel.CENTER);
        
        lab1.setBounds(0, 200, 1100, 100);
        lab1.setFont(new Font("Default", Font.PLAIN, 22));
        lab1.setForeground(Color.white);

        lab2.setBounds(0, 300, 1100, 100);
        lab2.setFont(new Font("Default", Font.PLAIN, 22));
        lab2.setForeground(Color.white);

        lab3.setBounds(0, 400, 1100, 100);
        lab3.setFont(new Font("Default", Font.PLAIN, 22));
        lab3.setForeground(Color.white);

        JButton jb1 = new JButton("BACK TO MENU");
        jb1.setBounds(600, 650, 500, 40);
        jb1.setMargin(new Insets(0, 0, 0, 0));
        jb1.setFont(new Font("Default", Font.BOLD, 30));
        jb1.addActionListener(ML);
        jb1.setForeground(Color.white);
        jb1.setContentAreaFilled(false); 
        jb1.setBorderPainted(false);
        jb1.setBorder(BorderFactory.createRaisedBevelBorder());


        panel.add(lab1);
        panel.add(lab2);
        panel.add(lab3);
        panel.add(jb1);

        return panel;
    }

    // pause dialog
    public JDialog creatPause(myPanel mp, gameTimer timer){
        if (pauseDialog == null) {
            this.mp = mp;
            this.timer = timer;
            pauseDialog = new JDialog();
            JPanel screen = new JPanel();
            JLabel jl1 = new JLabel("Press 'p' to resume");
            JButton jb1 = new JButton("Click to Back to Menu");

            jl1.setFont(new Font("Default", Font.PLAIN, 22));
            jl1.setForeground(Color.white);

            int frameX = window.getFrame().getBounds().x;
            int frameY = window.getFrame().getBounds().y;

            pauseDialog.setBounds(frameX,frameY,1100,800);
            pauseDialog.setUndecorated(true);
            pauseDialog.setOpacity(0.9f);
            pauseDialog.setVisible(false);
            pauseDialog.setFocusable(true);
            
            screen.setBounds(frameX,frameY,1100,800);
            screen.setBackground(Color.gray);
            screen.setOpaque(true);
            screen.setLayout(new BoxLayout(screen, BoxLayout.Y_AXIS ));
            
            jb1.setContentAreaFilled(false); 
            jb1.setBorderPainted(false); 
            jb1.setForeground(Color.BLACK);
            jb1.setBorder(BorderFactory.createRaisedBevelBorder()); 
            jb1.setFont(new java.awt.Font("Dialog",1,30));
            jb1.addActionListener(ML);
            jb1.setForeground(Color.white);

            screen.add(jl1,Component.CENTER_ALIGNMENT);
            screen.add(Box.createRigidArea(new Dimension(375,40)));
            screen.add(jb1,Component.CENTER_ALIGNMENT);
            screen.setBorder(BorderFactory.createEmptyBorder(255, 5, 15, 5));


            pauseDialog.add(screen);
            pauseDialog.addKeyListener(new KeyAdapter() {
                // key listener
                public void keyPressed(KeyEvent e){
                    if(e.getKeyCode() == KeyEvent.VK_P){
                        closeDialog();
                    }
                }
            });
        }
        return pauseDialog;
    }

    // escape dialog
    public JDialog creatEsc(myPanel mp){
        if (escDialog == null) {
            this.mp = mp;
            escDialog = new JDialog();
            JPanel screen = new JPanel();
            JLabel jl1 = new JLabel("Wanna esape?");
            JLabel jl2 = new JLabel("Press 'esc' again to comfirm");
            JLabel jl3 = new JLabel("Any other keys to resume");

            int frameX = window.getFrame().getBounds().x;
            int frameY = window.getFrame().getBounds().y;

            escDialog.setBounds(frameX,frameY,1100,800);
            escDialog.setUndecorated(true);
            escDialog.setOpacity(0.9f);
            escDialog.setVisible(false);
            escDialog.setFocusable(true);
            
            jl1.setFont(new java.awt.Font("Dialog",1,40));
            jl2.setFont(new java.awt.Font("Dialog",1,40));
            jl3.setFont(new java.awt.Font("Dialog",1,40));

            screen.setBounds(frameX,frameY,1100,800);
            screen.setBackground(Color.gray);
            screen.setOpaque(true);
            screen.setLayout(new BoxLayout(screen, BoxLayout.Y_AXIS ));
            screen.add(jl1,Component.CENTER_ALIGNMENT);
            screen.add(Box.createRigidArea(new Dimension(375,40)));
            screen.add(jl2,Component.CENTER_ALIGNMENT);
            screen.add(Box.createRigidArea(new Dimension(375, 40)));
            screen.add(jl3,Component.CENTER_ALIGNMENT);
            screen.setBorder(BorderFactory.createEmptyBorder(375, 5, 15, 5));

            escDialog.add(screen);
            escDialog.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e){
                    if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                        System.exit(0);                
                    }
                    else{
                        closeEsc();
                    }
                }
            });
        }
        return pauseDialog;
    }

    // over panel
    public JPanel gameOverScreen(){
        sound.StopGameBGM();
        JPanel panel = new drawPanel(window.imageRes.gameOverScreen);
        panel.setLayout(null);
        JButton jb1 = new JButton("EXIT");
        JButton jb2 = new JButton("MENU");

        jb1.setContentAreaFilled(false); 
        jb1.setBorderPainted(false); 
        jb1.setForeground(Color.white);
        jb1.setBorder(BorderFactory.createRaisedBevelBorder()); 

        jb2.setContentAreaFilled(false); 
        jb2.setBorderPainted(false); 
        jb2.setForeground(Color.white);
        jb2.setBorder(BorderFactory.createRaisedBevelBorder()); 

        setButton(jb1, Component.CENTER_ALIGNMENT, true);
        jb1.setBounds(480,500,100,50);
        setButton(jb2, Component.CENTER_ALIGNMENT, true);
        jb2.setBounds(480,570,110,50);

        jb1.addActionListener(ML);
        jb2.addActionListener(ML);

        panel.add(jb1);
        panel.add(jb2);
        return panel;
    }

    // tutorial dialog
    public JDialog TutorialPage(){
        openDialog();

        JDialog dialog = new JDialog();
        JPanel panel = new drawPanel(window.imageRes.Tutor);

        int frameX = window.getFrame().getBounds().x;
        int frameY = window.getFrame().getBounds().y;

        dialog.setTitle("Laura's Magic Notes");
        dialog.setVisible(true);
        dialog.add(panel);
        dialog.setBounds(frameX + 300, frameY + 150,500,500);
        dialog.requestFocus();
        dialog.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                
            }
        
            @Override
            public void keyReleased(KeyEvent e) {
                
            }
        
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_M){
                    dialog.dispose();
                }                
            }
        });
        return dialog;
    }

    // close dialog
    public void closeDialog(){
        pauseDialog.setVisible(false);
        timer.timerResume();
        sound.playGameBGM();
        mp.gs.resumeEnemies();
        isStoped = false;
    }

    // close dialog and back to menu
    public void closeDialog_pu(){
        pauseDialog.dispose();
        sound.StopGameBGM();
        isStoped = false;
    }

    // close esc dialog
    public void closeEsc(){
        escDialog.setVisible(false);
        timer.timerResume();
        sound.playGameBGM();
        isStoped = false;
        mp.gs.resumeEnemies();

    }

    // open pause dialog
    public void openDialog(){
        sound.StopGameBGM();

        int frameX = window.getFrame().getBounds().x;
        int frameY = window.getFrame().getBounds().y;

        pauseDialog.setBounds(frameX,frameY,1100,800);
        pauseDialog.setVisible(true);
        timer.timerPause();
        sound.StopGameBGM();
        if(Walking){
            walkingSound.Stopwalking();
        }
        isStoped = true;
        mp.gs.suspendEnemies();
    }

    // open esc
    public void openEsc(){
        int frameX = window.getFrame().getBounds().x;
        int frameY = window.getFrame().getBounds().y;

        escDialog.setBounds(frameX,frameY,1100,800);
        escDialog.setVisible(true);
        timer.timerPause();
        sound.StopGameBGM();
        if(Walking){
            walkingSound.Stopwalking();
        }
        mp.gs.suspendEnemies();
        isStoped = true;
    }

    public boolean getIsStoped(){
        return isStoped;
    }

    public void setWalkSoundFlag(boolean flag){
        this.Walking = flag;
    }
    
    public String info2String(String timeStr, int score) {
        String info = "";

        info += userName;
        info += ",";
        info += timeStr;
        info += ",";
        info += String.valueOf(score);

        return info;
    }
}