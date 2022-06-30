package sk.stuba.fei.uim.oop.game;

import lombok.Setter;
import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.player.Player;

import javax.swing.*;
import java.awt.*;


@Setter
public class Base extends JPanel {
    private Board board;
    private Player[] playersList;
    private int size;

    public Base(Board board, Player [] playersList,int size) {
        this.size=size;
        this.board=board;
        this.playersList=playersList;
        this.setLayout(new GridLayout(size,size));
        this.setBackground(new Color(67,203,62));
        }

    @Override
    protected void paintComponent(Graphics g) {
        int xPoint= this.getWidth()/size;
        int yPoint= this.getHeight()/size;
        super.paintComponent(g);
        this.board.drawGrid(g,xPoint,yPoint);
        this.board.draw(g,xPoint,yPoint);
        this.playersList[0].drawMoves(g,xPoint,yPoint);
    }

}
