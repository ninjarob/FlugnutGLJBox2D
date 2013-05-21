package com.pkp.gameengine.game.PathAndAnimationHandlers;

import com.pkp.gameengine.IScreen;
import com.pkp.model.sprite.flugerian.Bird;
import com.pkp.model.sprite.flugerian.BuildingPiece;
import com.pkp.model.sprite.level.FlugnutHome.RoofPiece;
import com.pkp.utils.Constants;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 1/23/13
 * Time: 6:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class BirdPath {

    Bird bp;
    Random r;
    boolean actionInProgress = false;
    float startActionTime;
    int actionType;
    float actionForceMagnitude;
    float actionAngle;  //radians here

    public BirdPath(Bird bp) {
        this.bp = bp;
        r = new Random();
        actionType = r.nextInt(2);
        startActionTime = r.nextFloat()*10;
        actionAngle = 0;
    }

    public BirdPath(Bird bp, float actionAngle) {
        this.bp = bp;
        r = new Random();
        actionType = r.nextInt(2);
        startActionTime = r.nextFloat()*10;
        this.actionAngle = actionAngle;
    }

    public void haveFun(float minusX, float plusx) {
        Body b = bp.body;
        if (!actionInProgress) {
            float xFactor = (float)Math.cos(actionAngle);
            float yFactor = (float)Math.sin(actionAngle);
            float mag = 100;
            b.applyForceToCenter(new Vec2(mag*xFactor, mag*yFactor));
//            if (b.getPosition().x < minusX) {
//                actionInProgress = true;
//                actionAngle = 0;
//                actionForceMagnitude = 1000;
//                actionType = 1;
//                return;
//            }
//            if (b.getPosition().x > plusx) {
//                actionInProgress = true;
//                actionType = 2;
//                actionAngle = (float)Math.PI;
//                actionForceMagnitude = 1000;
//                return;
//            }
//            if (b.getPosition().y < 100) {
//                diveUp();
//                return;
//            }
        }
//        else {
//            float xFactor = (float)Math.cos(actionAngle);
//            float yFactor = (float)Math.sin(actionAngle);
//            float mag = actionForceMagnitude;
//            b.applyForceToCenter(new Vec2(mag*xFactor, mag*yFactor));
//            actionInProgress = !endActionCheck();
//        }
    }

    private boolean endActionCheck() {
        boolean endAction = false;
        switch (actionType) {
            case 1:
                if (bp.body.getLinearVelocity().x >= Constants.BIRD_TOP_SPEED) endAction = true;
                break;
            case 2:
                if (bp.body.getLinearVelocity().x <= -1*Constants.BIRD_TOP_SPEED) endAction = true;
                break;
        }
        return endAction;
    }

    private void switchDirection(int direction) {

    }

    private void dive() {

    }

    private void diveUp() {

    }

    private void loop() {

    }

    private boolean goingStraight() {
        float tol = Constants.ANGULAR_FLAT_TOLERANCE;
        float angle = bp.body.getAngle();
        if ((angle > 0+tol || angle < 0-tol) && (angle > 2*Math.PI+tol || angle < 2*Math.PI-tol)) {
            return false;
        }
        return true;
    }


}
