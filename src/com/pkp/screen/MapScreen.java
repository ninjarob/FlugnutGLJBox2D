package com.pkp.screen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import org.jbox2d.common.Vec2;
import com.pkp.gameengine.game.Assets;
import com.pkp.gameengine.game.Settings;
import com.pkp.gameengine.game.Swipeable;
import com.pkp.gameengine.i.IGame;
import com.pkp.gameengine.i.io.ITouchInput.TouchEvent;
import com.pkp.model.level.Level;
import com.pkp.model.level.Wave;
import com.pkp.model.level.Wave.WaveType;
import com.pkp.model.sprite.Background;
import com.pkp.model.sprite.GoBackButton;
import com.pkp.utils.Utilities;

public class MapScreen extends IScreen implements Swipeable {
	private GoBackButton goBackButton;
	private Background background;
	private List<Level> levels;
	private Level goToLevel;
	private float levelXSeperation = .18f;
	private float levelYSeperation = .20f;

	public MapScreen(IGame game) {
		super(game);
		if (Settings.soundEnabled) {
			if(Assets.mainTheme.isPlaying()) {
				Assets.mainTheme.stop();
			}
			if (!Assets.titleAndMapTheme.isPlaying()) {
				Assets.titleAndMapTheme.play();
			}
		}
		background = new Background(((GLGame) game), "spacebg1-half-noalpha.gif");
		goBackButton = new GoBackButton(((GLGame) game), "buttons.png");
		levels = new ArrayList<Level>();
		for (int i = 0; i < 16; i++)
		{
			int change = i%4;
			float x = glGraphics.getWidth()*levelXSeperation*(change+1);
			float y = glGraphics.getHeight()-glGraphics.getHeight()*levelYSeperation*(((int)i/4)+1);
			Integer score = Settings.scores.get(i);
			Level nl;
//			if (score == null)
//			{
//				if (i == 0) nl = new Level(((GLGame) game), "level.png", i+1, x, y, "FlugnutLevel/fgh-noalpha.png", false, 1234, Arrays.asList(staticWaves1[0]));
//				else if (levels.get(i-1).score > 0)
//					nl = new Level(((GLGame) game), "level.png", i+1, x, y, "FlugnutLevel/fgh-noalpha.png", false, 0, Arrays.asList(staticWaves1[0]));
//				else nl = new Level(((GLGame) game), "lockedlevel.png", i+1, x, y, "FlugnutLevel/fgh-noalpha.png", true, -1, Arrays.asList(staticWaves1[0]));
//			}
//			else
//			{
                if (score == null) score = 0;
				String levelFile = "level.png";
				if (score > 100) levelFile = "level1star.png";
				if (score > 200) levelFile = "level2star.png";
				if (score > 300) levelFile = "level3star.png";
                boolean weapons = (i+1 <= 2)?false:true;
				nl = new Level(((GLGame) game), levelFile, i+1, x, y, "FlugnutLevel/fgh-noalpha.png", false, score, weapons);
			//}
			levels.add(nl);
		}
		transitionIn = true;
	}

	@Override
	public void update(float deltaTime) {
		if (transitionIn) transitionIn = transitionIn(deltaTime);
		else if (transitionOut)
		{
			transitionOut = transitionOut(deltaTime);
			if (!transitionOut) {
				if (nextScreen.equals(ScreenType.Game))
				{
					GameScreen gameScreen = new GameScreen(game, goToLevel);
					game.setScreen(gameScreen);
				}
				else {
					Utilities.setTheNextScreen((GLGame)game, nextScreen);
				}
			}
		}
		else {
			List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
			game.getInput().getKeyEvents();
			int len = touchEvents.size();
			for (int i = 0; i < len; i++) {
				TouchEvent event = touchEvents.get(i);
				if (event.type == TouchEvent.TOUCH_UP) {
					if (event.x < 32 && glGraphics.getHeight()-event.y < 32) {
						if (Settings.soundEnabled)
							Assets.click.play(1);
						transitionOut = true;
						nextScreen = ScreenType.MainMenu;
						return;
					}
					Vec2 v = new Vec2(event.x, glGraphics.getHeight()-event.y);
					for (int j = 0; j < 16; j++) {
						Level level = levels.get(j);
						if(!level.locked)
						{
							if (v.x > level.mapx  &&
								v.x < level.mapx+level.mapImage.width &&
								v.y > level.mapy &&
								v.y < level.mapy+level.mapImage.height) {
								if (Settings.soundEnabled)
									Assets.click.play(1);
								transitionOut = true;
								nextScreen = ScreenType.Game;
								goToLevel = level;
								return;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		background.draw(gl);
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		for (Level level : levels) {
			level.draw(gl);
		}
		goBackButton.draw(gl);
		
		gl.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {
		goBackButton.image.getTexture().dispose();
		background.image.getTexture().dispose();
		for (int i = 0; i < levels.size(); i++) {
			Level level = levels.get(i);
			if (level != null)
				level.mapImage.image.getTexture().dispose();
			else break;
		}
		if (nextScreen==null) {
			Assets.titleAndMapTheme.stop();
			Assets.titleAndMapTheme.dispose();
		}
	}

	@Override
	public boolean transitionIn(float deltaTime) {
		boolean returnValue = false;
		for (Level level : levels) {
			returnValue |= level.mapImage.transitionIn(deltaTime);
			returnValue |= level.numbers.transitionIn(deltaTime);
		}
		returnValue |= goBackButton.transitionIn(deltaTime);
		return returnValue;
	}

	@Override
	public boolean transitionOut(float deltaTime) {
		boolean returnValue = false;
		for (Level level : levels) {
			returnValue |= level.mapImage.transitionOut(deltaTime);
			returnValue |= level.numbers.transitionOut(deltaTime);
		}
		returnValue |= goBackButton.transitionOut(deltaTime);
		return returnValue;
	}
}
