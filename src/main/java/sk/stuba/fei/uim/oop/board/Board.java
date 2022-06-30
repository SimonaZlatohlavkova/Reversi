package sk.stuba.fei.uim.oop.board;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.control.GameLogic;
import java.awt.*;

@Getter
@Setter
public class Board {
    private Node[][] board;
    private int size;
    private GameLogic logic;

    public Board(int size, GameLogic logic){
        this.size=size;
        this.generate(size);
        this.logic=logic;
    }

    public void generate(int size) {
        this.board = new Node[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = new Node(j, i);
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != 0) {
                    this.board[i][j].addNeighbour(Direction.UP, this.board[i - 1][j]);
                }
                if (i != size - 1) {
                    this.board[i][j].addNeighbour(Direction.DOWN, this.board[i + 1][j]);
                }
                if (j != 0) {
                    this.board[i][j].addNeighbour(Direction.LEFT, this.board[i][j - 1]);
                }
                if (j != size - 1) {
                    this.board[i][j].addNeighbour(Direction.RIGHT, this.board[i][j + 1]);
                }

                if(i != 0 && j != size - 1){
                    this.board[i][j].addNeighbour(Direction.UP_RIGHT, this.board[i-1][j + 1]);
                }
                if(i != 0 && j != 0){
                    this.board[i][j].addNeighbour(Direction.UP_LEFT, this.board[i-1][j -1]);
                }
                if(i != size-1 && j != size-1){
                    this.board[i][j].addNeighbour(Direction.DOWN_RIGHT, this.board[i+1][j+1]);
                }
                if(i != size-1 && j != 0){
                    this.board[i][j].addNeighbour(Direction.DOWN_LEFT, this.board[i+1][j-1]);
                }
            }
        }
    }

    public void drawGrid(Graphics g,int x, int y){
        g.setColor(new Color(117,203,114));
        for(int i=1; i<size; i++){
            g.drawLine(x*i-5,0,x*i-5,y*size);
        }
        for(int j=1; j<size; j++) {
            g.drawLine(0, y * j-5, x * size, y * j-5);
        }
    }

    public void draw(Graphics g, int x, int y){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)  {
                this.board[i][j].paintComponent(g,x,y);
            }
        }
    }
}
