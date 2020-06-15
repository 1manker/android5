package uw.lmanker.tictactoe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Paint;
import android.graphics.Color;
import androidx.annotation.Nullable;



public class Board extends View {
    private MainActivity activity;
    private Paint gridC, xCol, oCol;
    int width;
    int height;
    int navBar;
    int count = 0;
    float midL, midT, midR, midB, x1, x2, x3, x4;
    int dx;
    int dy;
    int currPlay = 1;
    Logic logic = new Logic();
    int[]coords = new int[9];
    boolean won = false;

    public Board(Context context){
        super(context);
        Paint gridC = new Paint(Paint.ANTI_ALIAS_FLAG);
    }
    public Board(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        gridC = new Paint();
        xCol = new Paint(Paint.ANTI_ALIAS_FLAG);
        oCol = new Paint(Paint.ANTI_ALIAS_FLAG);
        width = context.getResources().getDisplayMetrics().widthPixels;
        height = context.getResources().getDisplayMetrics().heightPixels;
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android");
        navBar = resources.getDimensionPixelSize(resourceId);
        float adjHeight = height - navBar - 84;
        midL = width/3 + 50;
        midT = adjHeight/3 + 50;
        midR = (2*width)/3 - 50;
        midB = (2*adjHeight)/3 - 50;
        x1 = width/3 + 50;
        x2 = (adjHeight/3) + 50;
        x3 = (2*width/3) - 50;
        x4 = (2*adjHeight)/3 - 50;
        fillCoords();

    }

    public void setMainActivity(MainActivity a) {
        activity = a;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawGrid(canvas);
        if(count > 0) {
            drawX(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) (event.getX());
        int y = (int) (event.getY());
        Log.e("x:",Integer.toString(x));
        Log.e("y:", Integer.toString(y));
        count ++;
        float adjHeight = height - navBar - 84;
        int touched = logic.getSq(x,y,adjHeight,width);
        if(won){fillCoords(); invalidate(); won = false;}
        if(coords[touched] == 0) {
            coords[touched] = 1 + currPlay;
            currPlay = (currPlay + 1) % 2;
            invalidate();}
        if(logic.checkFull(coords)){
            invalidate();
            currPlay=1;
            new AlertDialog.Builder(getContext())
                    .setTitle("Tie!!")
                    .setMessage("Tie!\nNew Game?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Sure",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    fillCoords();
                                    invalidate();
                                }})
                    .setNegativeButton("Nope",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    System.exit(1);
                                }}).show();
        }
            if(logic.checkWin(coords)){
                won = true;
                char winner;
                if(currPlay==0){winner = 'X';}
                else{winner = 'O';}
                new AlertDialog.Builder(getContext())
                        .setTitle("Winner!")
                        .setMessage(winner + " wins!\nNew Game?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Sure",
                                new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                fillCoords();
                                invalidate();
                            }})
                        .setNegativeButton("Nope",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        android.os.Process.killProcess(android.os.Process.myPid());
                                        System.exit(1);
                                    }}).show();
                currPlay=1;}
        return super.onTouchEvent(event);
    }

    protected void drawGrid(Canvas canvas){
        gridC.setStrokeWidth(8);
        float adjHeight = height - navBar - 84;
        canvas.drawLine(0,adjHeight/3,width,adjHeight/3,gridC);
        canvas.drawLine(0,(2*adjHeight)/3,width,(2*adjHeight)/3,gridC);
        canvas.drawLine(width/3,0,width/3,adjHeight,gridC);
        canvas.drawLine((2*width)/3,0,(2*width)/3,adjHeight,gridC);
    }

    protected void drawX(Canvas canvas){
        xCol.setStrokeWidth(12);
        xCol.setColor(Color.RED);
        oCol.setColor(Color.BLUE);
        oCol.setStrokeWidth(12);
        oCol.setStyle(Paint.Style.STROKE);
        float adjHeight = height - navBar - 84;
        for(int i = 0; i < 9; i++){
            dx = i % 3 - 1;
            dy = i / 3 - 1;
            float offsetX = (width/3) * dx;
            float offsetY = (adjHeight/3) * dy;
            if(coords[i] == 1){
                canvas.drawOval(midL + offsetX, midT + offsetY, midR + offsetX,
                        midB + offsetY, oCol);
            }
            if(coords[i] == 2){
                canvas.drawLine(x1 + offsetX, x2 + offsetY, x3 + offsetX,
                        x4 + offsetY, xCol);
                canvas.drawLine(x1 + offsetX, x4 + offsetY, x3 + offsetX,
                        x2 + offsetY, xCol);
            }
        }
    }

    private void fillCoords(){
        for(int i = 0; i < 9; i++){
            coords[i] = 0;
        }
    }


}
