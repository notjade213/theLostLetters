package de.jade.player.abilities;

import com.badlogic.gdx.Gdx;
import de.jade.player.Obanana;


public class Attack {
    private float punchAttackDamage = 100f;// in hp, normal monster = 200
    private float punchAttackRange = 1.5f; // in Tiles
    private float punchAttackCoolDown = 3f; // in Seconds
    private float punchAttackCoolDownTimer = 0f;
    private boolean punchAttackReady = true;

    private float kickAttackDamage = 200f;
    private float kickAttackRange = 2f;
    private float kickAttackCoolDown = 8f;
    private float kickAttackCoolDownRemaining = 0f;


    public void updatePunch(float delta) {
        if(!punchAttackReady) {
            punchAttackCoolDownTimer += delta;
            if (punchAttackCoolDownTimer >= punchAttackCoolDown) {
                punchAttackReady = true;
                punchAttackCoolDownTimer = 0f;

            }
        }
    }

    public void updateKick(float delta) {
        if (kickAttackCoolDownRemaining > 0f) {
            kickAttackCoolDownRemaining -= delta;
        }
    }


    public void PunchAttack(Obanana player) {
        if(punchAttackReady) {
            System.out.println("Ability used!!!");
            // Ability logic
            punchAttackReady = false;

        } else {
            System.out.println("Punch Attack is on Cooldown for: " + punchAttackCoolDownTimer);
        }

    }
}



