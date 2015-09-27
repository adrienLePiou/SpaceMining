package com.alp.android.spacemining;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button popUpButton = (Button) findViewById(R.id.open);
        popUpButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (p != null)
                    showPopup(MainActivity.this, p);
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

    private Point p;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        int[] location = new int[2];
        Button button = (Button) findViewById(R.id.open);

        button.getLocationOnScreen(location);
        // Initialize the Point with x, and y positions
        p = new Point();
        p.x = location[0];
        p.y = location[1];



    }



    public void showPopup(final Activity context, Point p) {

        Rect rectgle= new Rect();
        Window window= getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
        int StatusBarHeight= rectgle.top;
        int contentViewTop=
                window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int TitleBarHeight= contentViewTop - StatusBarHeight;
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int popupWidth = display.getWidth();
        int popupHeight = (display.getHeight()-TitleBarHeight);

        // Inflate the popup_layout.xml
        RelativeLayout viewGroup = (RelativeLayout) context
                .findViewById(R.id.LinearCosmonautePopup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.cosmonaute_popup, viewGroup);


        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);
        popup.setAnimationStyle(R.style.PopupWindowAnimation);
        ((TextView)popup.getContentView().findViewById(R.id.pu_cd_cost)).setText(String.valueOf(heroCDCost));
        ((TextView)popup.getContentView().findViewById(R.id.totalCDTxt)).setText(String.valueOf(clickDmg));


        // Some offset to align the popup a bit to the right, and a bit down,
        // relative to button's position.

        int OFFSET_X = 0;
        int OFFSET_Y = 0;
        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());
        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y
                + OFFSET_Y);

        // Getting a reference to Close button, and close the popup when
        // clicked.
        Button close = (Button) layout.findViewById(R.id.close);
        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });


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

    public void displayTotalCrystal(int totalCrystal){
        TextView totalGoldTxt = (TextView) findViewById(R.id.totalCrystalTxt);
        totalGoldTxt.setText(String.valueOf(totalCrystal));
    }

    public void displayNextPuCDCost(int heroCDCost){

        LayoutInflater layoutInflater =
                (LayoutInflater)MainActivity.this
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.cosmonaute_popup, null);
        PopupWindow popupWindow = new PopupWindow(getBaseContext());


        ((TextView)popupWindow.getContentView().findViewById(R.id.pu_cd_cost)).setText(String.valueOf(heroCDCost));
       // TextView heroCDCostTxt = (TextView) popupView.getContentView().findViewById(R.id.pu_cd_cost);
        //heroCDCostTxt.setText(String.valueOf(heroCDCost));


    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void displayAsteroidStyle(String style){
        ImageView image = (ImageView) findViewById(R.id.asteroidView);
        int resId = getResources().getIdentifier(style, "drawable", getPackageName());
        Bitmap bMap = BitmapFactory.decodeResource(getResources(), resId);
        image.setImageBitmap(bMap);
    }


    asteroid Asteroid = new asteroid();
    Cosmonaute cosmonaute = new Cosmonaute();
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
    public void setClickDmg(View popup){
        if(totalCrystal - heroCDCost >= 0){
            cosmonaute.setClickDamage(1);
            clickDmg = cosmonaute.getClickDamage();
            totalCrystal = totalCrystal - heroCDCost;
            cosmonaute.setCosmonauteLvl(1);
            heroLvl = cosmonaute.getCosmonauteLvl();
            heroCDCost = (int) Math.floor(heroCDCost * Math.pow(1.07, heroLvl));
            displayTotalCrystal(totalCrystal);
            displayNextPuCDCost(heroCDCost);
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
