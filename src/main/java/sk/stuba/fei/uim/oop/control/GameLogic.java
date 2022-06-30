package sk.stuba.fei.uim.oop.control;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.board.Direction;
import sk.stuba.fei.uim.oop.board.Node;
import sk.stuba.fei.uim.oop.game.Base;
import sk.stuba.fei.uim.oop.player.AI;
import sk.stuba.fei.uim.oop.player.Move;
import sk.stuba.fei.uim.oop.player.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GameLogic extends Adapter {
    @Getter
    private int currentPlayer;
    private int nextPlayer;
    @Getter
    private Base base;
    private Board board;
    private Player[]playerList;
    @Setter
    private int size;
    @Getter
    private JLabel rocksAI;
    @Getter
    private JLabel rocksPlayer;
    @Getter
    private JLabel playedMove;
    @Getter
    private JLabel message;
    @Getter
    private JLabel boardSize;

    public GameLogic(int size) {
        this.currentPlayer=0;
        this.nextPlayer=1;
        this.size=size;
        this.rocksAI=new JLabel();
        this.rocksPlayer=new JLabel();
        this.playedMove=new JLabel();
        this.message= new JLabel();
        this.boardSize=new JLabel();
        this.rocksPlayer.setForeground(Color.WHITE);
        this.board=new Board(size, this);
        this.playerList=new Player[2];
        this.playerList[0]=new Player(Color.BLACK);
        this.playerList[1]=new AI(Color.WHITE);
        firstRocks();
        this.base=new Base(this.board,this.playerList,this.size);
        this.base.addMouseListener(this);
        this.base.addMouseMotionListener(this);
        this.base.repaint();
        gamePlay();
    }

    public void firstRocks(){
        initializeRocks(playerList[0], size/2, size/2);
        initializeRocks(playerList[0], size/2+1, size/2+1);
        initializeRocks(playerList[1], size/2, size/2+1);
        initializeRocks(playerList[1], size/2+1, size/2);
    }

    public void initializeRocks(Player player, int x, int y){
        this.board.getBoard()[y-1][x-1].setPlayer(player);
        player.addRock(this.board.getBoard()[y-1][x-1]);
    }

    public void gamePlay(){
        updateLabel(boardSize,"Size "+this.size+"x"+ this.size,20);
        updateLabel(rocksAI, " "+playerList [1].getPlayersRocks().size(),30);
        updateLabel(rocksPlayer, " "+playerList [0].getPlayersRocks().size(),30 );

        if (playerList [currentPlayer].getPlayersRocks().size()+playerList[nextPlayer].getPlayersRocks().size()==size*size){
            getWinner();
           return;
        }
        if(currentPlayer == 1){
            getMoves(playerList[1],playerList[0]);
            int index= playerList[1].getBestMove();
            if(index==-1){
                updateLabel(playedMove,"no moves",15);
                reversCurrentPlayer();
                gamePlay();
            }
            else{
                int x=playerList[1].getMoves().get(index).getX();
                int y=playerList[1].getMoves().get(index).getY();
                updateLabel(playedMove,(x+1)+" "+(y+1),30);
                playMove(x,y);
            }
        }
        else{
            getMoves(playerList[0],playerList[1]);
            updateLabel(message, "Your turn",13);
            if(playerList [0].getMoves().isEmpty()){
                getMoves(playerList[1],playerList[0]);
               if(playerList [1].getMoves().isEmpty()){
                    getWinner();
                    return;
                }
                else{
                    playerList[1].removeMoves();
                    reversCurrentPlayer();
                    gamePlay();
                }

            }
        }
    }

    public void updateLabel(JLabel label, String s,int size){
        label.setText(s);
        label.setFont(new Font("Calibri", Font.BOLD, size));
    }

    public void getWinner(){
        int playerStones= playerList [0].getPlayersRocks().size();
        int AIstones= playerList [1].getPlayersRocks().size();
        if(playerStones>AIstones){
            message.setForeground(new Color(0,153,0));
            updateLabel(message,"! You won !",13);
        }
        else if (playerStones==AIstones){
            message.setForeground(Color.red);
            updateLabel(message,"It is a draw",13);
        }
        else{
            message.setForeground(Color.red);
            updateLabel(message,"! You lose !",13);
        }

    }

    public void getMoves(Player player, Player nextPlayer){
        player.moves(nextPlayer, Direction.RIGHT);
        player.moves(nextPlayer, Direction.UP);
        player.moves(nextPlayer, Direction.LEFT);
        player.moves(nextPlayer, Direction.DOWN_LEFT);
        player.moves(nextPlayer, Direction.DOWN_RIGHT);
        player.moves(nextPlayer, Direction.UP_LEFT);
        player.moves(nextPlayer, Direction.UP_RIGHT);
        player.moves(nextPlayer, Direction.DOWN);
    }

    public void playMove(int x, int y){
        this.activate(this.board.getBoard()[y][x],playerList [currentPlayer],playerList [nextPlayer]);
        playerList[currentPlayer].removeMoves();
        reversCurrentPlayer();
        gamePlay();
    }

    public void reversCurrentPlayer(){
        int temp= this.currentPlayer;
        this.currentPlayer=this.nextPlayer;
        this.nextPlayer=temp;
    }

    public void activate(Node node,Player player,Player playerD) {
        System.out.println();
        Direction d;
        Move currentMove;
        for (int i = 0; i < player.getMoves().size(); i++) {
            currentMove = player.getMoves().get(i);
            Node nodeStart = node;
            if (currentMove.getFinishNode().getX()==node.getX()&&currentMove.getFinishNode().getY()==node.getY()) {
                d = currentMove.getD();
                d = revertDirection(d);
                while (nodeStart.getNeighbours().get(d).getPlayer() == playerD) {
                    nodeStart.getNeighbours().get(d).setPlayer(player);
                    playerD.removeRock(nodeStart.getNeighbours().get(d));
                    player.addRock(nodeStart.getNeighbours().get(d));
                    nodeStart=nodeStart.getNeighbours().get(d);
                }
            }
        }
        node.setPlayer(player);
        player.addRock(node);
    }

    public Direction revertDirection(Direction d){
        switch(d){
            case UP: d= Direction.DOWN; break;
            case DOWN:d= Direction.UP;break;
            case LEFT:d= Direction.RIGHT;break;
            case RIGHT:d= Direction.LEFT;break;
            case UP_LEFT:d= Direction.DOWN_RIGHT;break;
            case UP_RIGHT:d= Direction.DOWN_LEFT;break;
            case DOWN_LEFT:d= Direction.UP_RIGHT;break;
            case DOWN_RIGHT:d= Direction.UP_LEFT;break;
            default:break;
        }
        return d;
    }

    public void gameRestart(int size){
        updateLabel(playedMove, " ",30);
        updateLabel(message," ",15);
        updateLabel(rocksPlayer," ",30);
        updateLabel(rocksAI," ",30);
        this.board.generate(size);
        currentPlayer=0;
        nextPlayer=1;
        playerList[0].removeAllRocks();
        playerList[1].removeAllRocks();
        playerList[0].removeMoves();
        playerList[1].removeMoves();
        firstRocks();
        this.base.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e){
        int eX= e.getX();
        int eY= e.getY();
        int x=convertPosition(eX,base.getWidth());
        int y=convertPosition(eY,base.getHeight());

        for(Move m:playerList[0].getMoves()){
            Node n= m.getFinishNode();
            if(n.getX()==x&&n.getY()==y){
                n.setHighlight(true);
            }
            else{
                n.setHighlight(false);
            }
        }
        this.base.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int eX= e.getX();
        int eY= e.getY();
        int xPosition=convertPosition(eX,base.getWidth());
        int yPosition=convertPosition(eY,base.getHeight());
        if( xPosition==-1&&yPosition==-1){
            return;
        }
        if(currentPlayer==0){
            for(int i=0; i< playerList[this.currentPlayer].getMoves().size(); i++){
                Node n= playerList[this.currentPlayer].getMoves().get(i).getFinishNode();
                if (n.getX() ==  xPosition && n.getY() == yPosition) {
                    playMove( xPosition,yPosition);
                    break;
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        gameRestart(this.size);
        gamePlay();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange()== ItemEvent.SELECTED) {
            String s=((JComboBox)e.getSource()).getSelectedItem().toString();
            int i=Integer.parseInt(s);
            this.size=i;
            this.board.setSize(i);
            gameRestart(i);
            this.base.setSize(i);
            this.base.repaint();
            gamePlay();
        }
    }

    private int convertPosition(int screenPosition, int window) {
        int i= window/this.size;
        if(i<1){return -1;}
        return screenPosition/i;
    }

    @Override
    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                gameRestart(this.size);
                gamePlay();
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
        }
    }
}