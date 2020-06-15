package uw.lmanker.tictactoe;

public class Logic {
    Logic(){

    }
    public int getSq(int x, int y, float h, float w){
        float hThird = h/3;
        float wThird = w/3;
        int yCoord;
        int xCoord;
        if(y <= hThird){yCoord=0;}
        else if (y <= hThird*2){yCoord=1;}
        else{yCoord=2;}
        if(x <= wThird){xCoord=0;}
        else if (x <= wThird*2){xCoord=1;}
        else{xCoord=2;}
        return xCoord + 3*yCoord;
    }
    public boolean checkFull(int[] coords){
        int length = coords.length;
        for(int i = 0; i < length; i++){
            if(coords[i] == 0){return false;}
        }
        return true;
    }
    public boolean checkWin(int[] coords){
        int length = coords.length/3 + 1;
        for(int i = 0; i < 3; i++){
            if(coords[i*3]!=0&&coords[i*3]==coords[(i*3)+1]&&coords[i*3]==coords[(i*3)+2]){
                return true;
            }
            else if(coords[i]!=0&&coords[i]==coords[i+3]&&coords[i]==coords[i+6]){
                return true;
            }
            else if(coords[0]!=0&&coords[0]==coords[4]&&coords[0]==coords[8]){
                return true;
            }
            else if(coords[2]!=0&&coords[2]==coords[4]&&coords[2]==coords[6]){
                return true;
            }
        }
        return false;
    }
}
