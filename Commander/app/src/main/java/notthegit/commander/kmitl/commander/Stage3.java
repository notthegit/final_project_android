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

public class Stage3 extends AppCompatActivity {

    final static int mapsize = 5;
    private int turn = 0;
    private boolean tank_destroy = false, blue_on_blue = false, artillery_observationer_destroy = false,
    cas_down = false;
    private TextView radio, napalm;
    private Context context;
    private ImageView[][] ivCell = new ImageView[mapsize][mapsize];
    private Drawable[] drawCell = new Drawable[7];
    private int shot = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage3);
        context = this;
        loadResources();
        design_Battlefield();
    }
    private void loadResources() {
        drawCell[0] = context.getResources().getDrawable(R.drawable.forest);
        drawCell[1] = context.getResources().getDrawable(R.drawable.roadew);
        drawCell[2] = context.getResources().getDrawable(R.drawable.plain);
        drawCell[3] = context.getResources().getDrawable(R.drawable.enarmourplain);
        drawCell[4] = context.getResources().getDrawable(R.drawable.hill);
        drawCell[5] = context.getResources().getDrawable(R.drawable.wheeledarmouredreconroadew);
        drawCell[6] = context.getResources().getDrawable(R.drawable.enaaplain);
    }
    @SuppressLint("NewApi")
    private void design_Battlefield(){
        int sizeofCell = Math.round((ScreenWidth() / mapsize));
        LinearLayout.LayoutParams lpRow = new LinearLayout.LayoutParams(sizeofCell * mapsize, sizeofCell);
        LinearLayout.LayoutParams lpCell = new LinearLayout.LayoutParams(sizeofCell, sizeofCell);

        LinearLayout linBoardGame = (LinearLayout) findViewById(R.id.stage3);
        napalm = findViewById(R.id.napalm);

        for (int i = 0; i < mapsize; i++) {
            LinearLayout linRow = new LinearLayout(context);
            for (int j = 0; j < mapsize; j++) {
                ivCell[i][j] = new ImageView(context);
                if (i == 2 && j == 0){
                    ivCell[i][j].setBackground(drawCell[5]);
                }
                else if (i == 2){
                    ivCell[i][j].setBackground(drawCell[1]);
                }
                else if (i == 0){
                    ivCell[i][j].setBackground(drawCell[0]);
                }
                else if ((i == 4 && j == 1) || (i == 3 && j == 1)){
                    ivCell[i][j].setBackground(drawCell[4]);
                }
                else {
                    ivCell[i][j].setBackground(drawCell[2]);
                }

                final int x = i;
                final int y = j;
                ivCell[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(shot == 1){
                            if (x == 4 && y == 2){
                                tank_destroy = true;
                            }
                            else if (x == 3 && y == 1){
                                artillery_observationer_destroy = true;
                            }
                            else if (y == 3 || y == 4){
                                cas_down = true;
                            }
                            else if (x == 2 && y == turn){
                                blue_on_blue = true;
                                ivCell[x][y].setBackground(drawCell[1]);
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
        if(turn <= 4 && !blue_on_blue){
            ivCell[2][turn].setBackground(drawCell[5]);
            ivCell[2][turn-1].setBackground(drawCell[1]);
        }
        if (blue_on_blue){
            radio.setText("Commissar : Traitor BLAM!");
            shot--;
            napalm.setText("CAS : 0");
            endTurn.setVisibility(View.GONE);
        }
        else if (cas_down){
            radio.setText("HQ : Enemy AA shot down our CAS!\n Mission Fail.");
            shot--;
            napalm.setText("CAS : 0");
            ivCell[1][4].setBackground(drawCell[6]);
            endTurn.setVisibility(View.GONE);
        }
        else if (turn == 5){
            radio.setText("HQ : Enemy AA destroy\n Mission accomplished");
            ivCell[1][4].setBackground(drawCell[2]);
            endTurn.setVisibility(View.GONE);
            shot--;
            napalm.setText("CAS : 0");
            Button next = findViewById(R.id.next_level_btn);
            next.setVisibility(View.VISIBLE);
        }
        else if (turn == 1){
            radio.setText("Zulu 9 : I think I heard something sir.");
        }
        else if (turn == 4){
            radio.setText("Zulu 9 : Enemy AA spot sir!");
            ivCell[1][4].setBackground(drawCell[6]);
        }
        else if (!tank_destroy && turn == 2){
            radio.setText("HQ : Tank ambush and destroy our scout\n Mission Fail.");
            ivCell[2][2].setBackground(drawCell[1]);
            ivCell[4][2].setBackground(drawCell[3]);
            shot--;
            napalm.setText("CAS : 0");
            endTurn.setVisibility(View.GONE);
        }
        else if (!artillery_observationer_destroy && turn == 3){
            radio.setText("HQ : Enemy arty score direct hit on recon\n Mission Fail.");
            shot--;
            napalm.setText("CAS : 0");
            ivCell[2][3].setBackground(drawCell[1]);
            endTurn.setVisibility(View.GONE);
        }
    }
    public void clickNext(View view){
        Intent level = new Intent(this,Stage4.class);
        startActivity(level);
    }
}
