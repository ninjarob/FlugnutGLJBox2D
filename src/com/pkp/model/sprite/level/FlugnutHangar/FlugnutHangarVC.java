package com.pkp.model.sprite.level.FlugnutHangar;

import com.pkp.model.level.VCPiece;
import com.pkp.model.level.VictoryCondition;
import com.pkp.model.sprite.flugerian.ShwaiGuy;
import com.pkp.model.sprite.level.FlugnutHome.Flag;
import com.pkp.model.sprite.level.FlugnutHome.Pylon;
import com.pkp.utils.Utilities;
import org.jbox2d.common.Vec2;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 1/15/13
 * Time: 8:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class FlugnutHangarVC implements VictoryCondition {

    public List<Pylon> pylons;

    public FlugnutHangarVC(List<Pylon> pylons) {
        this.pylons = pylons;
    }

    @Override
    public boolean checkVictoryConditionsMet() {
        boolean retVal = true;
        for (Pylon p : pylons){
            retVal = retVal && p.engaged;
        }
        return retVal;
    }

    @Override
    public VCPiece handleVCClick(float x, float y) {
        for (Pylon p : pylons){
            p.clicked = false;
        }
        for (Pylon p : pylons) {
            if (p.body.getFixtureList().testPoint(new Vec2(x, y))) {
                p.clicked = true;
                return p;
            }
        }
        return null;
    }

    @Override
    public void executeVictoryEvent() {

    }
}
