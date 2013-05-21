package com.pkp.model.sprite.level.FlugnutHome;

import com.pkp.model.level.VCPiece;
import com.pkp.model.level.VictoryCondition;
import com.pkp.model.sprite.flugerian.ShwaiGuy;
import com.pkp.utils.Utilities;
import org.jbox2d.common.Vec2;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 1/15/13
 * Time: 8:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class FlugnutHomeVC implements VictoryCondition {

    public Flag flag;
    public Pylon pylon1;
    public Pylon pylon2;

    public FlugnutHomeVC(Flag flag, Pylon pylon1, Pylon pylon2) {
        this.flag = flag;
        this.pylon1 = pylon1;
        this.pylon2 = pylon2;
    }

    @Override
    public boolean checkVictoryConditionsMet() {
        return flag.engaged && pylon1.engaged && pylon2.engaged;
    }

    @Override
    public VCPiece handleVCClick(float x, float y) {
        flag.clicked = false;
        pylon1.clicked = false;
        pylon2.clicked = false;
        VCPiece returnPiece = null;
        if (flag.body.getFixtureList().testPoint(new Vec2(x, y))) {
            returnPiece = flag;
            flag.clicked = true;
        }
        else if (pylon1.body.getFixtureList().testPoint(new Vec2(x, y))) {
            returnPiece = pylon1;
            pylon1.clicked = true;
        }
        else if (pylon2.body.getFixtureList().testPoint(new Vec2(x, y))) {
            returnPiece = pylon2;
            pylon2.clicked = true;
        }
        return returnPiece;
    }

    @Override
    public void executeVictoryEvent() {

    }
}
