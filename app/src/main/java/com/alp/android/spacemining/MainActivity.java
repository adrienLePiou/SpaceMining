package com.alp.android.spacemining;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void displayClickDmg(int clickDmg){
        TextView totalCDTxt = (TextView) findViewById(R.id.totalCDTxt);
        totalCDTxt.setText(String.valueOf("Click Damage: " + clickDmg));
    }

    public void displayAsteroidsKilled(int astKilled){
        TextView totalMKTxt = (TextView) findViewById(R.id.totalMKTxt);
        totalMKTxt.setText(String.valueOf(astKilled + "/10"));
    }

    public void displayTotalCrystal(int totalCrystal){
        TextView totalGoldTxt = (TextView) findViewById(R.id.totalCrystalTxt);
        totalGoldTxt.setText(String.valueOf(totalCrystal));
    }

    public void displayNextPuCDCost(int heroCDCost){
        TextView powerUpCost = (TextView) findViewById(R.id.pu_cd_cost);
        powerUpCost.setText(String.valueOf(heroCDCost + " Golds"));
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void displayAsteroidStyle(String style){
        ImageView image = (ImageView) findViewById(R.id.asteroidView);
        String myDrawableName = style;
        int resId = getResources().getIdentifier(myDrawableName, "drawable", getPackageName());
        Bitmap bMap = BitmapFactory.decodeResource(getResources(), resId);
        image.setImageBitmap(bMap);
    }


    asteroid Asteroid = new asteroid();
    Cosmonaute cosmonaute = new Cosmonaute();
    int asteroidHP = Asteroid.getAsteroidHP();
    int lvl = 1;
    String AsteroidStyle = Asteroid.getAsteroidStyle();
    int astKilled = 0;
    int totalCrystal = 0;
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
    public boolean setClickDmg(){
        if(totalCrystal - heroCDCost >= 0){
            cosmonaute.setClickDamage(1);
            clickDmg = cosmonaute.getClickDamage();
            totalCrystal = totalCrystal - heroCDCost;
            cosmonaute.setCosmonauteLvl(1);
            heroLvl = cosmonaute.getCosmonauteLvl();
            heroCDCost = (int) Math.floor(heroCDCost * Math.pow(1.07, heroLvl));
            displayTotalCrystal(totalCrystal);
            displayNextPuCDCost(heroCDCost);
            displayClickDmg(clickDmg);

            Toast.makeText(
                    MainActivity.this,
                    "Your Cosmonaute is Lvl" + cosmonaute.getCosmonauteLvl(),
                    Toast.LENGTH_SHORT
            ).show();

            return true;
        } else {
            Toast.makeText(
                    MainActivity.this,
                    "You don't have enough crystals",
                    Toast.LENGTH_SHORT
            ).show();
            return false;
        }

    }



    //Create Cosmonaute Pop Up Menu
    public void CreatePopupMenu(View v) {

        PopupMenu mypopupmenu = new PopupMenu(this, v);
        MenuInflater inflater = mypopupmenu.getMenuInflater();
        inflater.inflate(R.menu.cosmonaute_menu, mypopupmenu.getMenu());
        mypopupmenu.show();

        //registering popup with OnMenuItemClickListener
        mypopupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.option1:
                        setClickDmg();

                        return true;

                    case R.id.option2:

                        Toast.makeText(
                                MainActivity.this,
                                "Option 2 Clicked",
                                Toast.LENGTH_SHORT
                        ).show();

                        return true;

                    default:

                        return true;

                }

                /*Toast.makeText(
                        MainActivity.this,
                        "Click Damage " + item.getTitle() + " " + item.getItemId(),
                        Toast.LENGTH_SHORT
                ).show();*/


            }

            });

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
