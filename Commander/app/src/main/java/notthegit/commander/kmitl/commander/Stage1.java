package notthegit.commander.kmitl.commander;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Stage1 extends AppCompatActivity {
    final static int mapWidth = 5, mapHeight = 4;
    private int turn = 0;
    private boolean pass = false, blue_on_blue = false;
    private TextView radio, napalm;
    private Context context;
    private ImageView[][] ivCell = new ImageView[mapHeight][mapWidth];
    private Drawable[] drawCell = new Drawable[4];
    private int shot = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage1);
        context = this;
        loadResources();
        design_Battlefield();
    }
    private void loadResources() {
        drawCell[0] = context.getResources().getDrawable(R.drawable.infantryonplain);
        drawCell[1] = context.getResources().getDrawable(R.drawable.forest);
        drawCell[2] = context.getResources().getDrawable(R.drawable.plain);
        drawCell[3] = context.getResources().getDrawable(R.drawable.ash);
    }
    @SuppressLint("NewApi")
    private void design_Battlefield(){
        int sizeofCell = Math.round((ScreenWidth() / 9));
        LinearLayout.LayoutParams lpRow = new LinearLayout.LayoutParams(sizeofCell * mapWidth, sizeofCell);
        LinearLayout.LayoutParams lpCell = new LinearLayout.LayoutParams(sizeofCell, sizeofCell);

        LinearLayout linBoardGame = (LinearLayout) findViewById(R.id.stage1);
        napalm = findViewById(R.id.napalm);

        for (int i = 0; i < mapHeight; i++) {
            LinearLayout linRow = new LinearLayout(context);
            for (int j = 0; j < mapWidth; j++) {
                ivCell[i][j] = new ImageView(context);
                if ((i == 1 && j == 2) || (i == 3 && j == 4)){
                    ivCell[i][j].setBackground(drawCell[1]);
                }
                else if((i == 0 && j == 0) || (i == 1 && j == 1)|| (i == 2 && j == 4)){
                    ivCell[i][j].setBackground(drawCell[0]);
                }
                else{
                    ivCell[i][j].setBackground(drawCell[2]);}
                final int x = i;
                final int y = j;
                ivCell[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(shot == 1){
                            if(x == 1 && y == 2){
                                pass = true;
                            }
                            else if((turn == 0 && (x == 1 && y == 1)) || (turn < 2 && (x == 2 && y == 4))
                                    || (x == 0 && y == 0)){
                                blue_on_blue = true;
                            }
                            ivCell[x][y].setBackground(drawCell[3]);
                        }
                        shot--;
                        napalm.setText("Napalm : 0");
                    }
                });
                linRow.addView(ivCell[i][j], lpCell);
            }
            linBoardGame.addView(linRow, lpRow);
        }
    }
    private float ScreenWidth(){
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }
    public void clickMenu(View view){
        Intent level = new Intent(this,MainActivity.class);
        startActivity(level);
    }
    public void clickEndTurn(View view){
        Button endTurn = findViewById(R.id.endTurn_btn);
        radio = findViewById(R.id.radio);
        napalm = findViewById(R.id.napalm);
        turn++;
        if (pass){
            radio.setText("Sniper cooked. Continue reconnaissance.");
            endTurn.setVisibility(View.GONE);
            Button next = findViewById(R.id.next_level_btn);
            next.setVisibility(View.VISIBLE);
        }
        else if (blue_on_blue){
            //radio.setText("Commissar : Traitor BLAM!");
            radio.setText("Commissar : You're fired from life BLAM!");
            endTurn.setVisibility(View.GONE);
        }
        else if (turn == 3){
            radio.setText("HQ : We lost contact with all squads\nMission Fail! Also commissar want to see you.");
            ivCell[0][0].setBackground(drawCell[2]);
            endTurn.setVisibility(View.GONE);
        }
        else if (turn == 2){
            radio.setText("Yankee Squad : Alfa Squad wipeout!. We are all left.");
            ivCell[2][4].setBackground(drawCell[2]);
            napalm.setText("Napalm : 1");
            shot++;
        }
        else if (turn == 1){
            radio.setText("Hotel Squad : Foxtrot Squad wipeout!");
            ivCell[1][1].setBackground(drawCell[2]);
            napalm.setText("Napalm : 1");
            shot++;
        }
    }
    public void clickNext(View view){
        Intent level = new Intent(this,MainActivity.class);
        startActivity(level);
    }
}
