package com.alp.android.spacemining;

/**
 * Created by piouter on 25/09/15.
 */
public class Cosmonaute {
    public int ClickDamage = 1;
    public int CritRate = 5;
    public int CosmonauteLvl = 1;

    public Cosmonaute(){
        super();
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
