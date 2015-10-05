package com.alp.android.spacemining;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.net.Uri;


import org.w3c.dom.Text;

import java.util.Random;
import java.util.Timer;



public class MainActivity extends Activity implements ComonautUpgrade.OnFragmentInteractionListener{

    Fragment f;
    private static final int PROGRESS = 0x1;

    private ProgressBar mProgress;
    private int mProgressStatus = 0;
    private Handler progressHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayAsteroidStyle(AsteroidStyle);
        displayCurrentLvl(lvl);
        displayLvlName(LvlName);

        final LinearLayout fragContainer = (LinearLayout) findViewById(R.id.llFragmentContainer);
        final LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setId(R.id.my_linear_layout_fragment);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        //final Fragment f = new ComonautUpgrade();
        final ComonautUpgrade f = ComonautUpgrade.newInstance("caca","pipi");

        ft.add(ll.getId(), f).commit();
        Log.d("Fragment ID ", String.valueOf(f.getId()));
        //getFragmentManager().beginTransaction().add(ll.getId(), ComonautUpgrade.newInstance("I am a fragment", "tag2")).commit();

        fragContainer.addView(ll);

        ft.hide(f);
        Log.d("I am Hidden ", String.valueOf(f.isHidden()));

        // CREATES COSMONAUT ENHANCEMENT POPUP MENU

        Button button = (Button) findViewById(R.id.open);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(android.R.animator.fade_in,
                        android.R.animator.fade_out);

                if (f.isHidden()) {
                    ft.show(f);
                    Log.d("Show", "Show");
                } else {
                    ft.hide(f);
                    Log.d("Hide", "Hide");
                }

                ft.commit();

            }


        });

        //HP PROGRESS BAR
        mProgress = (ProgressBar) findViewById(R.id.progress_bar);
        mProgress.setMax(asteroidMaxHP);



        // Start lengthy operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (mProgressStatus < 100) {
                    mProgressStatus = doWork();

                    // Update the progress bar
                    progressHandler.post(new Runnable() {
                        public void run() {
                            mProgress.setProgress(mProgressStatus);
                        }
                    });
                }
            }
        }).start();

        // DPS TIMER METHOD
        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            private long time = 0;

            @Override
            public void run() {
                DPS();
                if (asteroidHP > 0) {
                    /* The hp of the mob decreases*/
                    displayMobLife(asteroidHP);
                } else { /* If the monster doesn't have HP anymore*/
                    totalCrystal = totalCrystal + Asteroid.getAsteroidGold(lvl); /* We had the gold of the monster to the total amount of gold*/
                    astKilled = astKilled + 1; /* We increment the number of monster killed*/
                    displayAsteroidsKilled(astKilled); /* We display the number of monster killed*/
                    displayTotalCrystal(totalCrystal); /* We display the total gold we have*/
                    if (astKilled % 10 == 0) { /*  If the amount of monster killed is dividable by 10 */
                        displayAsteroidsKilled(astKilled);
                        lvl = lvl + 1; /* We increment the lvl */
                        astKilled = 0;
                        Asteroid = new asteroid(); /* We create the new monster */
                        Asteroid.setLvl(lvl); /* We set the new lvl */
                        asteroidHP = Asteroid.getAsteroidHP(); /* We create a new monster */
                        AsteroidStyle = Asteroid.getAsteroidStyle(); /* We get its color */
                        displayMobLife(asteroidHP); /* We display the monster's HP */
                        displayAsteroidStyle(AsteroidStyle); /* We display its color */
                        displayAsteroidsKilled(astKilled);
                    } else { /* If we didn't kill 10 monsters */
                        Asteroid = new asteroid(); /* We create the new monster */
                        Asteroid.setLvl(lvl); /* We set the new lvl */
                        asteroidHP = Asteroid.getAsteroidHP(); /* We create a new monster */
                        AsteroidStyle = Asteroid.getAsteroidStyle(); /* We get its color */
                        displayMobLife(asteroidHP); /* We display the monster's HP */
                        displayAsteroidStyle(AsteroidStyle); /* We display its color */
                        displayAsteroidsKilled(astKilled);
                    }
                }
                time += 1000;
                //Log.d("TimerExample", "Going for... " + time);
                h.postDelayed(this, 1000);
            }
        }, 1000); // 1 second delay (takes millis)


    }

    private int doWork() {
       return asteroidHP;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // DISPLAY FUNCTIONS

    public void displayCurrentLvl(int lvl){
        TextView currentLvlText = (TextView) findViewById(R.id.currentLvlText);
        currentLvlText.setText(String.valueOf(lvl));
    }

    public void displayMobLife(int mobLife){
        TextView mobHPTxt = (TextView) findViewById(R.id.asteroidHPTxt);
        mobHPTxt.setText(String.valueOf(mobLife + " HP"));
    }

    public void displayLvlName(String monsterName){
        TextView monsterNameTxt = (TextView) findViewById(R.id.LvlName);
        monsterNameTxt.setText("" + monsterName);
    }

    public void displayAsteroidsKilled(int astKilled){
        TextView totalMKTxt = (TextView) findViewById(R.id.totalMKTxt);
        totalMKTxt.setText(String.valueOf(astKilled + "/10"));
    }

    public void displayTotalCrystal(int totalCrystal) {
        final TextView totalGoldTxt = (TextView) findViewById(R.id.totalCrystalTxt);
        totalGoldTxt.setText(String.valueOf(totalCrystal));
    }

    public void displaySpaceshipClick(){
        ImageView iv = (ImageView) findViewById(R.id.spaceshipView);
        int resId = getResources().getIdentifier("spaceship_clicked", "drawable", getPackageName());
        Bitmap bMap = BitmapFactory.decodeResource(getResources(), resId);
        iv.setImageBitmap(bMap);
    }

    private void displaySpaceship() {
        ImageView iv = (ImageView) findViewById(R.id.spaceshipView);
        int resId = getResources().getIdentifier("spaceship", "drawable", getPackageName());
        Bitmap bMap = BitmapFactory.decodeResource(getResources(), resId);
        iv.setImageBitmap(bMap);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void displayAsteroidStyle(String style){
        ImageView image = (ImageView) findViewById(R.id.asteroidView);
        int resId = getResources().getIdentifier(style, "drawable", getPackageName());
        Bitmap bMap = BitmapFactory.decodeResource(getResources(), resId);
        image.setImageBitmap(bMap);
    }


    public void DPS(){
        asteroidHP = asteroidHP - spaceshipDPS;
    };


    // VARIABLE DECLARATIONS

    asteroid Asteroid = new asteroid();
    Cosmonaute cosmonaute = new Cosmonaute();
    Spaceship spaceship = new Spaceship();
    int spaceshipDPS = spaceship.getDPS();
    int asteroidHP = Asteroid.getAsteroidHP();
    int asteroidMaxHP = Asteroid.getAsteroidHP();
    int lvl = 1;
    String AsteroidStyle = Asteroid.getAsteroidStyle();
    int astKilled = 0;
    int totalCrystal = 10;
    int clickDmg = cosmonaute.getClickDamage();
    int heroCDCost = 10;
    int heroLvl = cosmonaute.getCosmonauteLvl();
    String LvlName = "Orion";
    boolean crit = false;
    int CHRate = cosmonaute.getCritRate();
    int CritDmg = 2;
    boolean clicked;

    // Is it a crit ?
    public boolean isCritical(){
        Random rnd = new Random();
        int rndInt = rnd.nextInt(100);
        if(rndInt <= CHRate){ // If the random number is below the crit Rate then it is a crit
            crit = true;
        } else {crit = false;}

        return crit;
    }



    /* What happens when you click*/
    public void hittingAsteroid(View view){
        if(!clicked){
            displaySpaceshipClick();
            clicked = true;
        } else {
            displaySpaceship();
            clicked = false;
        }
        isCritical();
        if(crit){
            asteroidHP = asteroidHP - (clickDmg * CritDmg);
        } else {
            asteroidHP = asteroidHP - clickDmg;
        }
         /* We display the new life of the mob*/
        displayAsteroidStyle(AsteroidStyle);
        displayCurrentLvl(lvl);
        displayLvlName(LvlName);
        if( asteroidHP > 0 ){
         /* The hp of the mob decreases*/
            displayMobLife(asteroidHP);
        } else { /* If the monster doesn't have HP anymore*/
            totalCrystal = totalCrystal + Asteroid.getAsteroidGold(lvl); /* We had the gold of the monster to the total amount of gold*/
            astKilled = astKilled + 1; /* We increment the number of monster killed*/
            displayAsteroidsKilled(astKilled); /* We display the number of monster killed*/
            displayTotalCrystal(totalCrystal); /* We display the total gold we have*/
            if (astKilled % 10 == 0){ /*  If the amount of monster killed is dividable by 10 */
                displayAsteroidsKilled(astKilled);
                lvl = lvl + 1; /* We increment the lvl */
                astKilled = 0;
                Asteroid = new asteroid(); /* We create the new monster */
                Asteroid.setLvl(lvl); /* We set the new lvl */
                asteroidHP = Asteroid.getAsteroidHP(); /* We get the new asteroid's hp amount */
                asteroidMaxHP = Asteroid.getAsteroidHP(); /* We set the new asteroid's max hp */
                AsteroidStyle = Asteroid.getAsteroidStyle(); /* We get its color */
                displayMobLife(asteroidHP); /* We display the monster's HP */
                displayAsteroidStyle(AsteroidStyle); /* We display its color */
                displayAsteroidsKilled(astKilled);
            } else { /* If we didn't kill 10 monsters */
                Asteroid = new asteroid(); /* We create the new monster */
                Asteroid.setLvl(lvl); /* We set the new lvl */
                asteroidHP = Asteroid.getAsteroidHP(); /* We get the new asteroid's hp amount */
                asteroidMaxHP = Asteroid.getAsteroidHP(); /* We set the new asteroid's max hp */
                AsteroidStyle = Asteroid.getAsteroidStyle(); /* We get its color */
                displayMobLife(asteroidHP); /* We display the monster's HP */
                displayAsteroidStyle(AsteroidStyle); /* We display its color */
                displayAsteroidsKilled(astKilled);
            }
        }
    }




    public void onFragmentInteraction(Uri uri){
        FragmentManager fm = getFragmentManager();
        ComonautUpgrade fragment = (ComonautUpgrade)fm.findFragmentById(R.id.my_linear_layout_fragment);
        if(totalCrystal - heroCDCost >= 0){
            fragment.setClickDmg(totalCrystal, heroCDCost, cosmonaute);
            clickDmg = cosmonaute.getClickDamage();
            totalCrystal = totalCrystal - heroCDCost;
            displayTotalCrystal(totalCrystal);

        } else {
            Toast.makeText(
                    MainActivity.this,
                    "Not Enough Crystals",
                    Toast.LENGTH_SHORT
            ).show();
        }


    }




}
