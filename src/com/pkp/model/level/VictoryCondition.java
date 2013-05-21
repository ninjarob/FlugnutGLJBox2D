package com.pkp.model.level;

import com.pkp.model.sprite.flugerian.ShwaiGuy;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 1/15/13
 * Time: 7:24 AM
 * To change this template use File | Settings | File Templates.
 */
public interface VictoryCondition {

    public boolean checkVictoryConditionsMet();
    public VCPiece handleVCClick(float x, float y);

    public void executeVictoryEvent();
}
