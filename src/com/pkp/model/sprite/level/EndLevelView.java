package com.pkp.model.sprite.level;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import org.jbox2d.common.Vec2;
import com.pkp.gameengine.game.ImageDrawable;
import com.pkp.gameengine.game.Swipeable;
import com.pkp.model.sprite.FlugerianSymbol;
import com.pkp.model.sprite.ScoreChart;
import com.pkp.utils.Utilities;

public class EndLevelView extends ImageDrawable implements Swipeable {
	public final float finalX = (IScreen.glGraphics.getWidth()/2)-(width/2);
	public LevelCompleted levelCompleted;
	public ScoreCounter scorecounter;
	public FlugerianSymbol flugerianSymbol;
	public ScoreChart scoreChart;
	public Continue continueButton;
	public boolean flug1 = false;
	public boolean flug2 = false;
	public boolean flug3 = false;
	public int symbols = 0;
	public float startTime = 0;
	public float currentScoreCount = 0;
	public float currentBuildingsCount = 0;
	public float currentEfficiencyCount = 0;
	
	public EndLevelView(GLGame game, String filename) {
		super(game, filename, new Vec2(-220*IScreen.SSC, IScreen.glGraphics.getHeight()*.4f*IScreen.SSC), 300*IScreen.SSC, 230*IScreen.SSC);
		scoreChart = new ScoreChart(game, "scorechart.png");
		scorecounter = new ScoreCounter(game, "numbers.png", scoreChart.finalX+scoreChart.width, scoreChart.def.position.y+scoreChart.height);
		flugerianSymbol = new FlugerianSymbol(game, "flugsym0.png");
		continueButton = new Continue(game, "continue.png");
	}

	@Override
	public void draw(GL10 gl) {
		super.draw(gl);
		Utilities.drawText((int)currentScoreCount+"", gl, IScreen.glGraphics, scorecounter.enemiesScoreGraphic);
		Utilities.drawText((int)scorecounter.buildingsScore+"", gl, IScreen.glGraphics, scorecounter.buildingsScoreGraphic);
		Utilities.drawText((int)scorecounter.efficiencyScore+"", gl, IScreen.glGraphics, scorecounter.efficiencyScoreGraphic);
		scorecounter.total = scorecounter.enemiesScore + scorecounter.buildingsScore + scorecounter.efficiencyScore;
		Utilities.drawText((int)scorecounter.total+"", gl, IScreen.glGraphics, scorecounter.totalScore);
		switch(symbols) {
			case 0:
			break;
			case 1:
				if (!flug1) {
					flugerianSymbol.setNewImagefile("flugsym1.png");
					flug1 = true;
				}
			break;
			case 2:
				if (!flug2) {
					flugerianSymbol.setNewImagefile("flugsym2.png");
					flug2 = true;
				}
			break;
			case 3:
				if (!flug3) {
					flugerianSymbol.setNewImagefile("flugsym3.png");
					flug3 = true;
				}
			break;
		}
		flugerianSymbol.draw(gl);
		scoreChart.draw(gl);
	}
	
	@Override
	public boolean transitionIn(float deltaTime) {
		boolean transitionFlag = Utilities.standardTransition(finalX, def.position, deltaTime, 1);
		transitionFlag = transitionFlag || flugerianSymbol.transitionIn(deltaTime) || scoreChart.transitionIn(deltaTime)
				|| continueButton.transitionIn(deltaTime);// || scorecounter.transitionIn(deltaTime);// || flugerianSymbol.transitionIn(deltaTime);
		return transitionFlag;
	}

	@Override
	public boolean transitionOut(float deltaTime) {
		boolean transitionFlag = Utilities.standardTransition(-width-20, def.position, deltaTime, -1);
		transitionFlag = transitionFlag || flugerianSymbol.transitionOut(deltaTime) || scoreChart.transitionOut(deltaTime)
				|| continueButton.transitionIn(deltaTime);// || scorecounter.transitionOut(deltaTime);// || flugerianSymbol.transitionOut(deltaTime);
		return transitionFlag;
	}
}
