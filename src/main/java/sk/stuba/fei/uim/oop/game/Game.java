package sk.stuba.fei.uim.oop.game;
import sk.stuba.fei.uim.oop.control.GameLogic;
import javax.swing.*;
import java.awt.*;

public class Game {
   private JFrame frame;
   private GameLogic logic;
   private JPanel menu;
    public Game() {
       frame = new JFrame("Reversi");

       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(1000,670);
       frame.setResizable(false);
       frame.setLayout(new BorderLayout());

       this.logic= new GameLogic(6);
       this. menu=new JPanel();
       this.intializeMenu();
       this.intializeBottomMenu();
       frame.addKeyListener(logic);
       frame.setFocusable(true);
       frame.add(BorderLayout.CENTER,logic.getBase());
       frame.setVisible(true);
    }

    public void intializeMenu(){
       menu.setBackground(Color.white);
       menu.setLayout(new GridLayout(6,1));
       restartButton();
       JPanel played= new JPanel();
       played.setLayout(new GridLayout(2,1));
       played.setBackground(Color.lightGray);
       JLabel lable = new JLabel("    AI played");
       played.add(lable);
       JPanel position= new JPanel();
       position.setBackground(Color.lightGray);
       position.add(logic.getPlayedMove());
       played.add(position);
       menu.add(played);

       stonesLabel(Color.WHITE,"  AI Stones:",logic.getRocksAI());
       stonesLabel(Color.BLACK,"  Your Stones:",logic.getRocksPlayer());

       menu.add(logic.getBoardSize());
       frame.add(BorderLayout.LINE_END,menu);
    }
    public void restartButton(){
       JButton restartButton= new JButton("RESTART");
       restartButton.addActionListener(logic);
       restartButton.setFocusable(false);
       menu.add(restartButton);
    }

    public void stonesLabel(Color colorBackground, String text, JLabel label){
       JPanel panel= new JPanel(new GridLayout(2,1));
       panel.setBackground(colorBackground);
       JLabel stone= new JLabel(text);
       panel.add(stone);
       panel.add(label);
       menu.add(panel);
    }

    public void intializeBottomMenu(){
       String[] values ={"6","8","10","12"};
       JComboBox box = new JComboBox(values);
       box.addItemListener(logic);
       box.setFocusable(false);
       JLabel boxLabel= new JLabel("Select size of board");
       boxLabel.setForeground(Color.BLACK);
       JPanel boxPanel= new JPanel(new GridLayout(1,2));
       boxPanel.setBackground(Color.gray);
       boxPanel.add(boxLabel);
       boxPanel.add(box);
       JPanel messagePanel= new JPanel();
       messagePanel.setBackground(Color.lightGray);
       messagePanel.add(logic.getMessage());
       boxPanel.add(messagePanel);
       frame.add(BorderLayout.PAGE_END, boxPanel );
    }

}
