package com.pkp.utils;

import javax.microedition.khronos.opengles.GL10;

import android.util.FloatMath;
import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import com.pkp.gameengine.game.Swipeable;
import com.pkp.gameengine.io.impl.GLGraphics;
import com.pkp.model.sprite.Numbers;
import com.pkp.screen.HelpScreen;
import com.pkp.screen.MainMenuScreen;
import com.pkp.screen.MapScreen;
import com.pkp.screen.StoryScreen;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;

public class Utilities {

    public static float TO_RADIANS = (1 / 180.0f) * (float) Math.PI;
    public static float TO_DEGREES = (1 / (float) Math.PI) * 180;

	public static void drawText(String line, GL10 gl, GLGraphics glGraphics, Numbers numbers) {
		int len = line.length();
		for (int i = 0; i < len; i++) {
			gl.glLoadIdentity();
			char character = line.charAt(i);
			float transx = 0;
			float transy = 0;
			if (character == '.') {
				numbers.image.resetVertices(0, 0, 10.0f*numbers.scale, 32.0f*numbers.scale,
						.06f * Character.getNumericValue(character), 0,
						.06f * (1 + Character.getNumericValue(character)), 1f);
					transx = -((10*numbers.scale) * (len - i));
					transy= -32*numbers.scale;
			} else {
				numbers.image.resetVertices(0, 0, 20.0f*numbers.scale, 32.0f*numbers.scale,
						.095f * Character.getNumericValue(character), 0,
						.095f * (1 + Character.getNumericValue(character)), 1f);
				transx = -((20*numbers.scale) * (len - i));
				transy= -32*numbers.scale;
			}
			numbers.drawWithTrans(gl, transx, transy);
		}
	}
	
	public static boolean standardTransition(float finalX, Vec2 position, float deltaTime, int direction) {
		if (Math.abs(finalX-position.x) < 10 || direction==-1?position.x<=finalX:position.x>finalX) {
			position.x = finalX;
			return false;
		}
		else {
			position.x += direction*Swipeable.TRANSITION_SPEED*deltaTime;
			return true;
		}
	}
	
	public static void setTheNextScreen(GLGame game, IScreen.ScreenType nextScreen) {
		switch (nextScreen) {
		case Map:
			game.setScreen(new MapScreen(game));
			break;
		case Help:
			game.setScreen(new HelpScreen(game));
			break;
		case MainMenu:
			game.setScreen(new MainMenuScreen(game));
			break;
		case Story:
			game.setScreen(new StoryScreen(game));
			break;
		default:
			break;
		}
	}

    public static float findAngleBetweenVectorsRad(Vec2 v1, Vec2 v2) {
        float xdis = (v2.x-v1.x);
        float ydis = (v2.y-v1.y);
        float angle = (float) Math.atan2(ydis, xdis);
        if (angle < 0)
            angle += (2*Math.PI);
        return angle;
    }

    public static float lenFromOrig(Vec2 v) {
        return FloatMath.sqrt(v.x * v.x + v.y * v.y);
    }

    public Vec2 norFromOrig(Vec2 v) {
        float len = lenFromOrig(v);
        if (len != 0) {
            v.x /= len;
            v.y /= len;
        }
        return v;
    }

    public static float angleFromOrig(Vec2 v) {
        float angle = (float) Math.atan2(v.y, v.x) * TO_DEGREES;
        if (angle < 0)
            angle += 360;
        return angle;
    }

    public float rotationAngle(BodyDef bd) {
        return (float) bd.angle * TO_DEGREES;
    }

    public static BodyDef rotateFromOrig(BodyDef bd, float angle) {
        float rad = angle * TO_RADIANS;
        float cos = FloatMath.cos(rad);
        float sin = FloatMath.sin(rad);
        float newX = bd.position.x * cos - bd.position.y * sin;
        float newY = bd.position.x * sin + bd.position.y * cos;
        bd.position.x = newX;
        bd.position.y = newY;
        return bd;
    }

    public static float dist(Vec2 position, Vec2 other) {
        float distX = position.x - other.x;
        float distY = position.y - other.y;
        return FloatMath.sqrt(distX * distX + distY * distY);
    }

    public static float dist(Vec2 position, float x, float y) {
        float distX = position.x - x;
        float distY = position.y - y;
        return FloatMath.sqrt(distX * distX + distY * distY);
    }

    public static boolean somewhatClose(Vec2 position, float x, float y, float bothWithin) {
        float distX = position.x - x;
        float distY = position.y - y;
        return distX <= bothWithin && distY <= bothWithin;
    }

    //given a delta time, adds to the position the velocity at the delta time
    public static void updateVectorByTime(BodyDef bd, float deltaTime)
    {
        float sinDir = FloatMath.sin(TO_RADIANS*bd.angle);
        float currentYAmount = bd.linearVelocity.x*(sinDir) * deltaTime;
        float cosDir = FloatMath.cos(TO_RADIANS*bd.angle);
        float currentXAmount = bd.linearVelocity.x*cosDir * deltaTime;
        bd.position.x+=currentXAmount;
        bd.position.y+=currentYAmount;
    }

    public static String getClickedFilePath(String filePath) {
        String[] splitString = filePath.split("\\.");
        return splitString[0] + "Clicked." + splitString[1];
    }

    public static String getUnclickedFilePath(String filePath) {
        return filePath.replaceFirst("Clicked", "");
    }

    public static float getVelocityMagnitude(Vec2 velocity) {
        return (float)Math.sqrt(Math.pow(velocity.x, 2) + Math.pow(velocity.y, 2));
    }

    public static Vec2 diminishVelocityToMag(Vec2 velocity, float diminishedMag) {
        float mag = (float)Math.sqrt(Math.pow(velocity.x, 2) + Math.pow(velocity.y, 2));
        float ratio = diminishedMag / mag;
        velocity.x = ratio*velocity.x;
        velocity.y = ratio*velocity.y;
        return velocity;
    }
}
