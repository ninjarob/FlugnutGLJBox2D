package com.pkp.model.sprite.level.FlugnutLab;

import com.pkp.model.level.VCPiece;
import com.pkp.model.level.VictoryCondition;
import com.pkp.model.sprite.flugerian.ShwaiGuy;
import com.pkp.model.sprite.level.FlugnutHome.Flag;
import com.pkp.model.sprite.level.FlugnutHome.Pylon;
import com.pkp.model.sprite.level.FlugnutHome.Well;
import com.pkp.utils.Utilities;
import org.jbox2d.common.Vec2;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 1/15/13
 * Time: 8:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class FlugnutLabVC implements VictoryCondition {

    public Flag flag1;
    public Flag flag2;
    public Pylon pylon1;
    public Pylon pylon2;
    public Well well;

    public FlugnutLabVC(Flag flag1, Flag flag2, Pylon pylon1, Pylon pylon2, Well well) {
        this.flag1 = flag1;
        this.flag2 = flag2;
        this.pylon1 = pylon1;
        this.pylon2 = pylon2;
        this.well = well;
    }

    @Override
    public boolean checkVictoryConditionsMet() {
        return flag1.engaged && flag2.engaged && pylon1.engaged && pylon2.engaged;
    }

    @Override
    public VCPiece handleVCClick(float x, float y) {
        flag1.clicked = false;
        flag2.clicked = false;
        pylon1.clicked = false;
        pylon2.clicked = false;
        VCPiece returnPiece = null;
        if (flag1.body.getFixtureList().testPoint(new Vec2(x, y))) {
            returnPiece = flag1;
            flag1.clicked = true;
        }
        else if (flag2.body.getFixtureList().testPoint(new Vec2(x, y)))
        {
            returnPiece = flag2;
            flag2.clicked = true;
        }
        else if (pylon1.body.getFixtureList().testPoint(new Vec2(x, y))) {
            returnPiece = pylon1;
            pylon1.clicked = true;
        }
        else if (pylon2.body.getFixtureList().testPoint(new Vec2(x, y))) {
            returnPiece = pylon2;
            pylon2.clicked = true;
        }
        else if (well.body.getFixtureList().testPoint(new Vec2(x, y))) {
            returnPiece = well;
            well.clicked = true;
        }
        return returnPiece;
    }

    @Override
    public void executeVictoryEvent() {

    }
}
