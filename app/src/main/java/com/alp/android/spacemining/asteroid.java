package com.alp.android.spacemining;

import java.util.Random;

/**
 * Created by piouter on 23/09/15.
 */
public class asteroid {
    public int lvl = 1;
    public int HP = 10;
    private int color;
    private int asteroidGold;
    public String asteroidName = "";
    public String designName;


    public asteroid(){
        super();
    }

    public String getAsteroidStyle(){
        Random rnd = new Random();
        int rndInt = rnd.nextInt(5) + 1;
        String designName = "asteroid";
        designName = designName + rndInt;
        return designName;
    }

    
    /*public int getAsteroidColor(){
        Random rnd = new Random();
        color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        return color;
    }*/

    public void setLvl(int lvl){
        this.lvl = lvl;
        this.HP = HP * lvl;
    }

    public int getAsteroidHP(){
        return HP;
    }

    public String getAsteroidName(){
        asteroidName = getAsteroidStyle();
        return asteroidName;
    }

    public int getAsteroidGold(int lvl){
        int asteroidGold = (2 * lvl);
        this.asteroidGold = asteroidGold;
        return asteroidGold;
    }

}
