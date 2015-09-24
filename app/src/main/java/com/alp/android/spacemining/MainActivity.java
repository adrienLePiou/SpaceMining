package com.alp.android.spacemining;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    int asteroidHP = Asteroid.getAsteroidHP();
    int lvl = 1;
    String AsteroidStyle = Asteroid.getAsteroidStyle();
    int astKilled = 0;
    int totalCrystal = 0;
    int clickDmg = 1;
    int heroCDCost = 10;
    int heroLvl = 1;
    String LvlName = "Orion";

    /* Increase Click Damage */
    public void setClickDmg(View view){
        if(totalCrystal - heroCDCost >= 0){
            clickDmg = clickDmg + 1;
            totalCrystal = totalCrystal - heroCDCost;
            heroLvl = heroLvl + 1;
            heroCDCost = (int) Math.floor(heroCDCost * Math.pow(1.07, heroLvl));
            displayTotalCrystal(totalCrystal);
            displayNextPuCDCost(heroCDCost);
            displayClickDmg(clickDmg);
        }
    }

    //somes changes

    /* What happens when you click*/
    public void hittingAsteroid(View view){
        asteroidHP = asteroidHP - clickDmg;
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
