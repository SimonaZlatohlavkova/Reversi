package sk.stuba.fei.uim.oop.player;

import java.awt.*;

public class AI extends Player{

    public AI(Color c) {
        super(c);
    }

    @Override
    public int getBestMove(){
        int max=0;
        int index=-1;
        int i=0;
        for (Move m:moves){
            if(m.getAmount()>max){
                index=i;
                max=m.getAmount();
            }
            i++;
        }
        return index;
    }

    @Override
    public void drawMoves(Graphics g, int x, int y){
    }

}
