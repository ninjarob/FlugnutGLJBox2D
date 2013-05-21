package com.pkp.model.sprite.level;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import org.jbox2d.common.Vec2;
import com.pkp.gameengine.game.Swipeable;
import com.pkp.model.sprite.Numbers;


public class ScoreCounter implements Swipeable {
	//public final float finalX = (IScreen.glGraphics.getWidth()/2)-(20/2);
	public float enemiesScore;
	public float buildingsScore;
	public float efficiencyScore;
	public float total;
	public Numbers enemiesScoreGraphic;
	public Numbers buildingsScoreGraphic;
	public Numbers efficiencyScoreGraphic;
	public Numbers totalScore;
	
	public ScoreCounter(GLGame game, String filename, float chartXPos, float chartYPos) {
		enemiesScoreGraphic = new Numbers(game, filename, .5f);
		buildingsScoreGraphic = new Numbers(game, filename, .5f);
		efficiencyScoreGraphic = new Numbers(game, filename, .5f);
		totalScore = new Numbers(game, filename, .5f);
		enemiesScoreGraphic.def.position = new Vec2(chartXPos+70, chartYPos+5);
		buildingsScoreGraphic.def.position = new Vec2(chartXPos+70, chartYPos-25);
		efficiencyScoreGraphic.def.position = new Vec2(chartXPos+70, chartYPos-55);
		totalScore.def.position = new Vec2(chartXPos+70, chartYPos-85);
	}

	public void draw(GL10 gl) {
		enemiesScoreGraphic.draw(gl);
		buildingsScoreGraphic.draw(gl);
		efficiencyScoreGraphic.draw(gl);
		totalScore.draw(gl);
	}
	
	@Override
	public boolean transitionIn(float deltaTime) {
		return enemiesScoreGraphic.transitionIn(deltaTime) || buildingsScoreGraphic.transitionIn(deltaTime) || efficiencyScoreGraphic.transitionIn(deltaTime) || totalScore.transitionIn(deltaTime);
	}

	@Override
	public boolean transitionOut(float deltaTime) {
		return enemiesScoreGraphic.transitionOut(deltaTime) || buildingsScoreGraphic.transitionOut(deltaTime) || efficiencyScoreGraphic.transitionOut(deltaTime) || totalScore.transitionIn(deltaTime);
	}
}