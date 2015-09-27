package com.alp.android.spacemining;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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


import java.util.Random;
import java.util.Timer;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayAsteroidStyle(AsteroidStyle);
        displayCurrentLvl(lvl);
        displayLvlName(LvlName);

        // DPS TIMER METHOD
        final Handler h = new Handler();
        h.postDelayed(new Runnable()
        {
            private long time = 0;

            @Override
            public void run() {
                DPS();
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
                Log.d("TimerExample", "Going for... " + time);
                h.postDelayed(this, 1000);
            }
        }, 1000); // 1 second delay (takes millis)

        // CREATE COSMONAUT ENHANCEMENT POPUP MENU

        Button button = (Button) findViewById(R.id.open);

        // add button listener
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // custom dialog
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.cosmonaute_popup);
                dialog.setTitle("Cosmonaut enhancement.");
                //dialog.getWindow().getAttributes().y = 400;

                final TextView heroCDCostTxt = (TextView) dialog.findViewById(R.id.pu_cd_cost);
                heroCDCostTxt.setText(String.valueOf(heroCDCost));
                Button dialogButton = (Button) dialog.findViewById(R.id.close);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                ImageButton CDButton = (ImageButton) dialog.findViewById(R.id.imageButton1);
                // if button is clicked, Activate setClickDmg function.
                CDButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setClickDmg(findViewById(R.id.LinearCosmonautePopup));
                    }
                });

                dialog.show();
            }
        });
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

    public void displayNextPuCDCost(int heroCDCost){
        final Dialog dialog = new Dialog(MainActivity.this);
        final TextView heroCDCostTxt = (TextView) dialog.findViewById(R.id.pu_cd_cost);
        heroCDCostTxt.setText(String.valueOf(heroCDCost));
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




    asteroid Asteroid = new asteroid();
    Cosmonaute cosmonaute = new Cosmonaute();
    Spaceship spaceship = new Spaceship();
    int spaceshipDPS = spaceship.getDPS();
    int asteroidHP = Asteroid.getAsteroidHP();
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

    // Is it a crit ?
    public boolean isCritical(){
        Random rnd = new Random();
        int rndInt = rnd.nextInt(100);
        if(rndInt <= CHRate){ // If the random number is below the crit Rate then it is a crit
            crit = true;
        } else {crit = false;}

        return crit;
    }

    /* Increase Click Damage */
    public void setClickDmg(View view){
        if(totalCrystal - heroCDCost >= 0){
            cosmonaute.setClickDamage(1);
            clickDmg = cosmonaute.getClickDamage();
            totalCrystal = totalCrystal - heroCDCost;
            cosmonaute.setCosmonauteLvl(1);
            heroLvl = cosmonaute.getCosmonauteLvl();
            heroCDCost = (int) Math.floor(heroCDCost * Math.pow(1.07, heroLvl));
            displayTotalCrystal(totalCrystal);
            //displayNextPuCDCost(heroCDCost);
            //displayClickDmg(clickDmg);


            Toast.makeText(
                    MainActivity.this,
                    "Your Cosmonaute is now level " + cosmonaute.getCosmonauteLvl(),
                    Toast.LENGTH_SHORT
            ).show();




        } else {
            Toast.makeText(
                    MainActivity.this,
                    "You don't have enough crystals",
                    Toast.LENGTH_SHORT
            ).show();

        }

    }

    /* What happens when you click*/
    public void hittingAsteroid(View view){
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
    }









}
