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

public class Stage2 extends AppCompatActivity {
    final static int mapsize = 5;
    private int turn = 0;
    private int[] naval_inf_left = {1, 1, 1};
    private boolean bridge_destroy = false, bridging_destroy = false;
    private TextView radio, napalm;
    private Context context;
    private ImageView[][] ivCell = new ImageView[mapsize][mapsize];
    private Drawable[] drawCell = new Drawable[9];
    private int shot = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage2);
        context = this;
        loadResources();
        design_Battlefield();
    }
    private void loadResources() {
        drawCell[0] = context.getResources().getDrawable(R.drawable.riverns);
        drawCell[1] = context.getResources().getDrawable(R.drawable.roadew);
        drawCell[2] = context.getResources().getDrawable(R.drawable.plain);
        drawCell[3] = context.getResources().getDrawable(R.drawable.destroybridgeew);
        drawCell[4] = context.getResources().getDrawable(R.drawable.ennavalinfantryplain);
        drawCell[5] = context.getResources().getDrawable(R.drawable.ennavalinfantryriverns);
        drawCell[6] = context.getResources().getDrawable(R.drawable.enarmourroadew);
        drawCell[7] = context.getResources().getDrawable(R.drawable.enarmourplain);
        drawCell[8] = context.getResources().getDrawable(R.drawable.enbridgingroadew);
    }
    @SuppressLint("NewApi")
    private void design_Battlefield(){
        int sizeofCell = Math.round((ScreenWidth() / mapsize));
        LinearLayout.LayoutParams lpRow = new LinearLayout.LayoutParams(sizeofCell * mapsize, sizeofCell);
        LinearLayout.LayoutParams lpCell = new LinearLayout.LayoutParams(sizeofCell, sizeofCell);

        LinearLayout linBoardGame = (LinearLayout) findViewById(R.id.stage2);
        napalm = findViewById(R.id.napalm);

        for (int i = 0; i < mapsize; i++) {
            LinearLayout linRow = new LinearLayout(context);
            for (int j = 0; j < mapsize; j++) {
                ivCell[i][j] = new ImageView(context);
                if(i == 2 && (j == 1 || j == 2 || j == 3)){
                    ivCell[i][j].setBackground(drawCell[6]);
                }
                else if(i == 2){
                    ivCell[i][j].setBackground(drawCell[1]);
                }
                else if (j == 3){
                    ivCell[i][j].setBackground(drawCell[0]);
                }
                else if(j == 0 && i != 3){
                    ivCell[i][j].setBackground(drawCell[4]);
                }
                else{
                    ivCell[i][j].setBackground(drawCell[2]);}
                final int x = i;
                final int y = j;
                ivCell[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(shot == 1){
                            if ((x == 2) && (y == 3)){
                                bridge_destroy = true;
                                ivCell[x][y].setBackground(drawCell[3]);
                            }
                            else if ((x == 0 && y == turn) && turn == 3) {
                                ivCell[x][y].setBackground(drawCell[0]);
                                naval_inf_left[0] = 0;
                            }
                            else if (x == 0 && y == turn){
                                ivCell[x][y].setBackground(drawCell[2]);
                                naval_inf_left[0] = 0;
                            }
                            else if ((x == 1 && y == turn) && turn == 3){
                                ivCell[x][y].setBackground(drawCell[0]);
                                naval_inf_left[1] = 0;
                            }
                            else if (x == 1 && y == turn) {
                                ivCell[x][y].setBackground(drawCell[2]);
                                naval_inf_left[1] = 0;
                            }
                            else if ((x == 4 && y == turn) && turn == 3){
                                ivCell[x][y].setBackground(drawCell[0]);
                                naval_inf_left[2] = 0;
                            }
                            else if (x == 4 && y == turn){
                                ivCell[x][y].setBackground(drawCell[2]);
                                naval_inf_left[2] = 0;
                            }
                            else if (x == 2 && y == turn-2){
                                ivCell[x][y].setBackground(drawCell[1]);
                                bridging_destroy = true;
                            }
                            else if (x == 2){
                                ivCell[x][y].setBackground(drawCell[1]);
                            }
                            else if (y == 3){
                                ivCell[x][y].setBackground(drawCell[0]);
                            }
                            else{
                                ivCell[x][y].setBackground(drawCell[2]);
                            }
                        }
                        shot--;
                        napalm.setText("CAS : 0");
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
        shot++;
        napalm.setText("CAS : 1");
        if (bridging_destroy && naval_inf_left[0]+naval_inf_left[1]+naval_inf_left[2] == 0){
            radio.setText("HQ : All enemy have been stop at bridge");
            endTurn.setVisibility(View.GONE);
            shot--;
            napalm.setText("CAS : 0");
            Button next = findViewById(R.id.next_level_btn);
            next.setVisibility(View.VISIBLE);
        }
        else if (!bridging_destroy && turn == 5){
            radio.setText("HQ : Bridging repair the bridge and tank are crossing the bridge.\n Mission Fail.");
            endTurn.setVisibility(View.GONE);
            shot--;
            napalm.setText("CAS : 0");
            ivCell[2][4].setBackground(drawCell[6]);
            ivCell[2][2].setBackground(drawCell[8]);
            ivCell[3][1].setBackground(drawCell[2]);
            ivCell[3][2].setBackground(drawCell[2]);
        }
        else if (bridge_destroy && turn == 1){
            radio.setText("HQ : Infantry try to cross the river. Bridging try to repair the bridge. Stop them.");
            ivCell[3][1].setBackground(drawCell[7]);
            ivCell[3][2].setBackground(drawCell[7]);
            ivCell[2][1].setBackground(drawCell[1]);
            ivCell[2][2].setBackground(drawCell[1]);
        }
        else if (turn == 4 && naval_inf_left[0]+naval_inf_left[1]+naval_inf_left[2] != 0){
            radio.setText("HQ : Infantry cross the river.\n Mission Fail.");
            ivCell[0][0].setBackground(drawCell[2]);
            shot--;
            napalm.setText("CAS : 0");
            endTurn.setVisibility(View.GONE);
        }
        if (naval_inf_left[0] == 1){
            if (turn == 3) {
                ivCell[0][turn].setBackground(drawCell[5]);
                ivCell[0][turn-1].setBackground(drawCell[2]);
            }
            else if (turn == 4) {
                ivCell[0][turn].setBackground(drawCell[4]);
                ivCell[0][turn-1].setBackground(drawCell[0]);
            }
            else{
                ivCell[0][turn].setBackground(drawCell[4]);
                ivCell[0][turn-1].setBackground(drawCell[2]);
            }
        }
        if (naval_inf_left[1] == 1){
            if (turn == 3) {
                ivCell[1][turn].setBackground(drawCell[5]);
                ivCell[1][turn-1].setBackground(drawCell[2]);
            }
            else if (turn == 4) {
                ivCell[1][turn].setBackground(drawCell[4]);
                ivCell[1][turn-1].setBackground(drawCell[0]);
            }
            else{
                ivCell[1][turn].setBackground(drawCell[4]);
                ivCell[1][turn-1].setBackground(drawCell[2]);
            }
        }
        if (naval_inf_left[2] == 1){
            if (turn == 3) {
                ivCell[4][turn].setBackground(drawCell[5]);
                ivCell[4][turn-1].setBackground(drawCell[2]);
            }
            else if (turn == 4) {
                ivCell[4][turn].setBackground(drawCell[4]);
                ivCell[4][turn-1].setBackground(drawCell[0]);
            }
            else{
                ivCell[4][turn].setBackground(drawCell[4]);
                ivCell[4][turn-1].setBackground(drawCell[2]);
            }
        }
        if (!bridging_destroy && turn >= 2){
            if (turn == 2){
                ivCell[2][turn-2].setBackground(drawCell[8]);
            }
            else{
                ivCell[2][turn-2].setBackground(drawCell[8]);
                ivCell[2][turn-3].setBackground(drawCell[1]);
            }

        }

    }
    public void clickNext(View view){
        Intent level = new Intent(this,Stage3.class);
        startActivity(level);
    }
}