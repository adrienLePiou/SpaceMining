package com.alp.android.spacemining;

/**
 * Created by piouter on 27/09/15.
 */
public class Spaceship {

    public Spaceship(){
        super();
    }

    int DPS = 1;

    public int setDPS(int bonus){
        DPS = DPS + bonus; //For now this is the way to improve DPS
        return DPS;
    }

    public int getDPS() {
        return DPS;
    }
}
