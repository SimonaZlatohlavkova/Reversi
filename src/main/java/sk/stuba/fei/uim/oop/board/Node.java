package sk.stuba.fei.uim.oop.board;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.player.Player;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Node{
    private int x;
    private int y;
    private Map<Direction,Node> neighbours;
    private Player player;
    private boolean highlight;

    public void paintComponent(Graphics g, int x, int y){
        if(player!=null){
            g.setColor(player.getC());
            g.fillOval(this.x*x, this.y*y,x-10,y-10);
        }
    }
    public boolean getHighlight(){
        return this.highlight;
    }

    public Node(int x, int y) {
        this.highlight=false;
        this.player=null;
        this.x=x;
        this.y=y;
        this.neighbours=new HashMap<>();
    }

    public void addNeighbour(Direction direction, Node node){
        this.neighbours.put(direction,node);
    }
}
