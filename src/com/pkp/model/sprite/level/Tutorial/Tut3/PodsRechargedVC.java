package com.pkp.model.sprite.level.Tutorial.Tut3;

import com.pkp.model.level.VCPiece;
import com.pkp.model.level.VictoryCondition;
import com.pkp.model.sprite.level.Tutorial.Tut2.Tower;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 1/15/13
 * Time: 8:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class PodsRechargedVC implements VictoryCondition {

    public List<SolarPod> pods;

    public PodsRechargedVC(List<SolarPod> pods) {
        this.pods = pods;
    }

    @Override
    public boolean checkVictoryConditionsMet() {
        for(SolarPod pod : pods) {
            if (!pod.isFullyCharged()) return false;
        }
        return true;
    }

    @Override
    public VCPiece handleVCClick(float x, float y) {return null;}

    @Override
    public void executeVictoryEvent() {

    }
}
