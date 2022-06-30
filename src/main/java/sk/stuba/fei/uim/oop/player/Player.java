package sk.stuba.fei.uim.oop.player;

import lombok.Getter;
import sk.stuba.fei.uim.oop.board.Direction;
import sk.stuba.fei.uim.oop.board.Node;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Player {

    protected Color c;
    protected List<Node> playersRocks;
    protected List<Move> moves;

    public Player(Color c) {
        this.moves= new ArrayList<>();
        this.playersRocks= new ArrayList<>();
        this.c= c;
    }

    public void removeAllRocks(){
        playersRocks.clear();
    }
    public void removeMoves(){
            moves.clear();
    }
    public void removeRock(Node node){
        playersRocks.remove(node);
    }
    public void addRock(Node node){
        playersRocks.add(node);
    }

    public void moves(Player playerD, Direction d){
        Node node;
        for(int i=0; i<this.playersRocks.size(); i++) {
            int amount=0;
            node = this.playersRocks.get(i);
            if(node.getNeighbours().containsKey(d)) {
                if (node.getNeighbours().get(d).getPlayer() != playerD) {
                    continue;
                }
            }
            while (node.getNeighbours().containsKey(d) && node.getNeighbours().get(d).getPlayer() == playerD) {
                    node = node.getNeighbours().get(d);
                    amount = amount+1;
            }

            if (node.getNeighbours().containsKey(d) && node.getNeighbours().get(d).getPlayer()==null) {
                node = node.getNeighbours().get(d);
                for(Move m:moves){
                    if(node==m.getFinishNode()){
                        amount = amount+m.getAmount();
                        m.setAmount(amount);
                    }
                }
                moves.add(new Move(node.getX(),node.getY(),this.playersRocks.get(i),node,amount,d));

            }
        }
    }

    public void drawMoves(Graphics g, int x, int y){
        for(int i=0; i< moves.size(); i++){
            Node n= moves.get(i).getFinishNode();
            if(n.getHighlight()){
                g.setColor(Color.MAGENTA);
            }
            else{g.setColor(Color.CYAN);}
            int xN= n.getX();
            int yN=n.getY();
            g.fillOval(xN*x, yN*y,x-10,y-10);
        }
    }
    public int getBestMove(){
        return -1;
    }
}
