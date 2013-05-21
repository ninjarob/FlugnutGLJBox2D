package com.pkp.model.sprite.level.Tutorial.Tut1;

import com.pkp.model.level.VCPiece;
import com.pkp.model.level.VictoryCondition;
import com.pkp.model.sprite.flugerian.Bird;
import com.pkp.model.sprite.flugerian.ShwaiGuy;
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
public class BirdCollectVC implements VictoryCondition {

    public List<Bird> birds;

    public BirdCollectVC(List<Bird> birds) {
        this.birds = birds;
    }

    @Override
    public boolean checkVictoryConditionsMet() {
        boolean retVal = true;
        for (Bird b : birds){
            retVal = retVal && b.caged;
        }
        return retVal;
    }

    @Override
    public VCPiece handleVCClick(float x, float y) {
        return null;
    }

    @Override
    public void executeVictoryEvent() {

    }
}
