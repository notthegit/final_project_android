package notthegit.commander.kmitl.commander;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Stage4 extends AppCompatActivity {
    final static int mapsize = 5;
    private int turn = 0, aa_destroy = 0;
    private boolean blue_on_blue = false, mortar_kill = false;
    private int[] aa = {1, 1, 1}, important_target = {1, 1, 1};
    private TextView radio, napalm;
    private Context context;
    private ImageView[][] ivCell = new ImageView[mapsize][mapsize];
    private Drawable[] drawCell = new Drawable[10];
    private int shot = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage4);
        context = this;
        loadResources();
        design_Battlefield();
    }
    private void loadResources() {
        drawCell[0] = context.getResources().getDrawable(R.drawable.forest);
        drawCell[1] = context.getResources().getDrawable(R.drawable.artilleryobservationplain);
        drawCell[2] = context.getResources().getDrawable(R.drawable.plain);
        drawCell[3] = context.getResources().getDrawable(R.drawable.enaaplain);
        drawCell[4] = context.getResources().getDrawable(R.drawable.hill);
        drawCell[5] = context.getResources().getDrawable(R.drawable.ash);
        drawCell[6] = context.getResources().getDrawable(R.drawable.enmortarplain);
        drawCell[7] = context.getResources().getDrawable(R.drawable.eninfantryonplain);
        drawCell[8] = context.getResources().getDrawable(R.drawable.enmechanizedantiarmourplain);
        drawCell[8] = context.getResources().getDrawable(R.drawable.enmechanizedantiarmourplain);
        drawCell[9] = context.getResources().getDrawable(R.drawable.ensupplyplain);
    }
    @SuppressLint("NewApi")
    private void design_Battlefield(){
        int sizeofCell = Math.round((ScreenWidth() / mapsize));
        LinearLayout.LayoutParams lpRow = new LinearLayout.LayoutParams(sizeofCell * mapsize, sizeofCell);
        LinearLayout.LayoutParams lpCell = new LinearLayout.LayoutParams(sizeofCell, sizeofCell);

        LinearLayout linBoardGame = (LinearLayout) findViewById(R.id.stage4);
        napalm = findViewById(R.id.napalm);
        final MediaPlayer arty_sound = MediaPlayer.create(this, R.raw.artysound);

        for (int i = 0; i < mapsize; i++) {
            LinearLayout linRow = new LinearLayout(context);
            for (int j = 0; j < mapsize; j++) {
                ivCell[i][j] = new ImageView(context);
                if(j == 0 && i == 2){
                    ivCell[i][j].setBackground(drawCell[1]);
                }
                else if((j == 3 && i == 2) || (j == 4 && i == 1) || (j == 4 && i == 4)){
                    ivCell[i][j].setBackground(drawCell[3]);
                }
                else if((j == 1 && i == 3) || (j == 4 && i == 0)){
                    ivCell[i][j].setBackground(drawCell[0]);
                }
                else if(j == 4 && i == 2){
                    ivCell[i][j].setBackground(drawCell[6]);
                }
                else if((j == 4 && i == 3) || (j == 3 && i == 0)){
                    ivCell[i][j].setBackground(drawCell[8]);
                }
                else if((j == 4 && i == 3) || (j == 3 && i == 0)){
                    ivCell[i][j].setBackground(drawCell[8]);
                }
                else if(j == 3 && i == 3){
                    ivCell[i][j].setBackground(drawCell[9]);
                }
                else if(j == 2){
                    ivCell[i][j].setBackground(drawCell[7]);
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
                            if(x == 2 && y == 0){
                                blue_on_blue = true;
                            }
                            else if (x == 2 && y == 3){
                                aa[0] = 0;
                            }
                            else if (x == 1 && y == 4){
                                aa[1] = 0;
                            }
                            else if (x == 4 && y == 4){
                                aa[2] = 0;
                            }
                            else if (x == 3 && y == 4){
                                important_target[0] = 0;
                            }
                            else if (x == 0 && y == 3){
                                important_target[1] = 0;
                            }
                            else if (x == 3 && y == 3){
                                important_target[2] = 0;
                            }
                            else if (x == 2 && y == 4){
                                mortar_kill = true;
                            }
                            if ((y == 1 && x == 3) || (y == 4 && x == 0)){
                                //
                            }
                            else{
                                ivCell[x][y].setBackground(drawCell[2]);
                            }
                            arty_sound.start();
                            shot--;
                            napalm.setText("Arty : 0");
                        }
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
        napalm.setText("Arty : 1");
        if (turn == 5 && (aa[0]+aa[1]+aa[2] != 0)){
            radio.setText("HQ : Sniper kill our artillery observationer\n Mission Fail");
            ivCell[2][0].setBackground(drawCell[2]);
            endTurn.setVisibility(View.GONE);
            shot--;
            napalm.setText("Arty : 0");
        }
        else if (turn == 10 && (important_target[0]+important_target[1]+important_target[2] == 0)){
            radio.setText("HQ : All important target have been destroy. Well done");
            endTurn.setVisibility(View.GONE);
            shot--;
            napalm.setText("Arty : 0");
            Button next = findViewById(R.id.next_level_btn);
            next.setVisibility(View.VISIBLE);
        }
        else if (turn == 10){
            radio.setText("HQ : Enemy still have some important target left.\n Mission Fail");
            endTurn.setVisibility(View.GONE);
            shot--;
            napalm.setText("Arty : 0");
        }
        else if (blue_on_blue){
            radio.setText("Commissar : Arty cannon await you after this!");
            shot--;
            napalm.setText("Arty : 0");
            endTurn.setVisibility(View.GONE);
        }
        else if (turn == 1){
            radio.setText("Tango 6 : I think there are snipers in the wood sir.");
        }
        else if (turn == 6 && !mortar_kill){
            final MediaPlayer arty_sound = MediaPlayer.create(this, R.raw.artysound);
            arty_sound.start();
            radio.setText("HQ : Random mortar round kill our artillery_observationer\n Mission Fail");
            ivCell[2][0].setBackground(drawCell[2]);
            endTurn.setVisibility(View.GONE);
            shot--;
            napalm.setText("Arty : 0");
        }
        else if (turn == 7){
            radio.setText("Hotel 9 : We only have 3 round left. Make it count.");
        }
        else if (aa_destroy == 1){
            final MediaPlayer napalm_sound = MediaPlayer.create(this, R.raw.napalmsound);
            napalm_sound.start();
            radio.setText("Tango 6 : Damm I heard sniper scream while being burn alive.");
            aa_destroy++;
            ivCell[3][1].setBackground(drawCell[5]);
            ivCell[0][4].setBackground(drawCell[5]);
        }
        else if (aa[0]+aa[1]+aa[2] == 0 && aa_destroy == 0){
            radio.setText("Tango 6 : All AA have been destroy.Request naplam on forest sir.");
            aa_destroy++;
        }
    }
    public void clickNext(View view){
        Intent level = new Intent(this,MainActivity.class);
        startActivity(level);
    }
}
