package com.pkp.utils;

import com.pkp.gameengine.game.Assets;
import com.pkp.model.FlugnutWorld;
import com.pkp.model.sprite.flugerian.Building;
import com.pkp.model.sprite.flugerian.weapons.EMP;
import com.pkp.model.sprite.flugerian.weapons.EMPCircle;
import com.pkp.model.sprite.flugerian.weapons.EMPSuperCircle;
import com.pkp.model.sprite.level.Tutorial.Tut3.SolarPod;
import com.pkp.model.sprite.snostreblaian.Bomb;
import com.pkp.model.sprite.snostreblaian.HeavyBomb;
import com.pkp.model.sprite.snostreblaian.SimpleBomb;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 1/11/13
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class FContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Object bofc = contact.getFixtureA().getBody().getUserData();
        Object bosc = contact.getFixtureB().getBody().getUserData();
        if (bofc != null && bofc instanceof Bomb) {
            bombContact((Bomb)bofc, bosc);
        }
        else if (bosc != null && bosc instanceof Bomb) {
            bombContact((Bomb)bosc, bofc);
        }
        else if (bofc != null && bofc instanceof EMP) {
            empContact((EMP)bofc, bosc);
        }
        else if (bosc != null && bosc instanceof EMP) {
            empContact((EMP)bosc, bofc);
        }
    }

    private void bombContact(Bomb bomb, Object other) {
        if (other instanceof Building) {
            Building b = (Building)other;
            b.health -= bomb.health;
            bomb.health = 0;
            Assets.explode.play(.25f);
            FlugnutWorld.bodiesToDestroy.add(bomb.body);
        }
        else if (other instanceof EMPCircle) {
            if (!other.equals(bomb.previousEMP)) {
                Assets.ras.play(1);
                EMP emp = (EMP)other;
                bomb.previousEMP = emp;
                bomb.health -= 1;
                updateScore(bomb);
            }
        }
        else if (other instanceof EMPSuperCircle) {
            if (!(other).equals(bomb.previousEMP)) {
                Assets.ras.play(1);
                EMP emp = (EMP)other;
                bomb.previousEMP = emp;
                bomb.health -= 2;
                updateScore(bomb);
            }
        }
        else //hit the ground
        {
            bomb.health = 0;
            Assets.explode.play(.25f);
            FlugnutWorld.bodiesToDestroy.add(bomb.body);
        }
    }

    private void empContact(EMP emp, Object other) {
        if (other instanceof SolarPod) {
            SolarPod s = (SolarPod)other;
            if (!emp.equals(s.previousEmp)) {
                s.previousEmp = emp;
                s.charge += 1;
                //possibly play a keeeerrrrZAP here.  Might be better to put it with an animation.
                //Assets.explode.play(.25f);
            }
        }
    }

    private void updateScore(Bomb bomb) {
        if (bomb.health <= 0) {
            float scoreMultiplier = 1;
            if (bomb.time < 6) {
                scoreMultiplier = 7 - bomb.time;
            }
            if (bomb instanceof SimpleBomb) {
                bomb.flugnutWorld.score += SimpleBomb.score * scoreMultiplier;
            } else if (bomb instanceof HeavyBomb) {
                bomb.flugnutWorld.score += HeavyBomb.score * scoreMultiplier;
            } else {
                bomb.flugnutWorld.score += HeavyBomb.score * scoreMultiplier;
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
