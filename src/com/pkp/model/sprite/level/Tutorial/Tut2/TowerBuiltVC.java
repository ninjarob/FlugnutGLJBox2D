package com.pkp.model.sprite.level.Tutorial.Tut2;

import com.pkp.model.level.VCPiece;
import com.pkp.model.level.VictoryCondition;
import com.pkp.model.sprite.flugerian.Bird;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 1/15/13
 * Time: 8:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class TowerBuiltVC implements VictoryCondition {

    public Tower tower;

    public TowerBuiltVC(Tower tower) {
        this.tower = tower;
    }

    @Override
    public boolean checkVictoryConditionsMet() {
        return tower.body.getPosition().x > 50 && tower.timeSpent > 3;
    }

    @Override
    public VCPiece handleVCClick(float x, float y) {
        return null;
    }

    @Override
    public void executeVictoryEvent() {

    }
}
