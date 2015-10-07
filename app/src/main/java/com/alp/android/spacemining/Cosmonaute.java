package com.alp.android.spacemining;

import java.math.BigInteger;

/**
 * Created by piouter on 25/09/15.
 */
public class Cosmonaute {
    public int ClickDamage = 1;
    public int CritRate = 5;
    public int CosmonauteLvl = 1;
    int heroCDCost = 5;

    public Cosmonaute(){
        super();
    }

    // Get next add Click Damage amount
    public BigInteger getNextAddClickDamageAmount(){
        BigInteger heroLvl = BigInteger.valueOf(CosmonauteLvl);
        BigInteger clickDmg = BigInteger.valueOf(ClickDamage);
        BigInteger nextClickDmg = heroLvl.gcd(clickDmg);
        return nextClickDmg;
    }

    public void setHeroCDCost(){
        heroCDCost = (int) Math.floor(heroCDCost * Math.pow(1.07, CosmonauteLvl));
    }

    public int getHeroCDCost(){
        return heroCDCost;
    }

    //When we increase the ClickDamage we will set it through this.
    public int setClickDamage(int bonus){
        ClickDamage = ClickDamage + bonus;
        return ClickDamage;
    }

    //For the moment the crit rate increase functions works like that. It needs more work.
    public int setCritRate(int bonus){
        CritRate = CritRate + bonus;
        return CritRate;
    }

    public int setCosmonauteLvl(int bonus){
        CosmonauteLvl = CosmonauteLvl + bonus;
        return CosmonauteLvl;
    }

    public int getCritRate(){
        return CritRate;
    }

    public int getClickDamage(){
        return ClickDamage;
    }

    public int getCosmonauteLvl(){
        return CosmonauteLvl;
    }
}
