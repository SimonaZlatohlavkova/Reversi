package sk.stuba.fei.uim.oop.player;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.board.Direction;
import sk.stuba.fei.uim.oop.board.Node;

@Getter
@Setter
public class Move {
    int x;
    int y;
    Node startNode;
    Node finishNode;
    int amount;
    Direction d;

    public Move(int x, int y, Node startNode, Node finishNode, int amount, Direction d) {
        this.d=d;
        this.x = x;
        this.y = y;
        this.startNode = startNode;
        this.finishNode = finishNode;
        this.amount = amount;
    }
}
