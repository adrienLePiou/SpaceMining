package com.alp.android.spacemining;

import java.util.Random;

/**
 * Created by piouter on 24/09/15.
 */
public class CriticalHit {

    public CriticalHit(){
        super();
    }

    double CHRate = 5;
    int CriticalDamage = 2;
    boolean crit = false;

    public double setCHRate(double bonus) {
        CHRate = CHRate + bonus;
        return CHRate;
    }

    public boolean isCritical(){
        Random rnd = new Random();
        int rndInt = rnd.nextInt(100);
        if(rndInt <= CHRate){
            crit = true;
        } else {crit = false;}

        return crit;
    }

    public int getCriticalDamage(){
        CriticalDamage = 2;
        return CriticalDamage;
    }

}
