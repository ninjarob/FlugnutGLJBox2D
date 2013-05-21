package com.pkp.screen;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import com.pkp.gameengine.game.Assets;
import com.pkp.gameengine.game.Settings;
import com.pkp.gameengine.game.Swipeable;
import com.pkp.gameengine.i.IGame;
import com.pkp.gameengine.i.io.ITouchInput.TouchEvent;
import com.pkp.model.sprite.Background;
import com.pkp.model.sprite.GoBackButton;
import com.pkp.model.sprite.StoryText;

public class StoryScreen extends IScreen implements Swipeable {

	private GoBackButton goBackButton;
	private Background background;
	private StoryText storyText;
	
	public StoryScreen(IGame game) {
		super(game);
		background = new Background(((GLGame) game), "spacebg1-half-noalpha.gif");
		goBackButton = new GoBackButton(((GLGame) game), "buttons.png");
		storyText = new StoryText(((GLGame) game), "storyText1.png");
		transitionIn = true;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if (!transitionIn && !transitionOut)
		{
			List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
			game.getInput().getKeyEvents();
	
			int len = touchEvents.size();
			for (int i = 0; i < len; i++) {
				TouchEvent event = touchEvents.get(i);
				if (event.type == TouchEvent.TOUCH_UP) {
					if (event.x < 32 && (glGraphics.getHeight()-event.y) < 32) {
						transitionOut = true;
						if (Settings.soundEnabled)
							Assets.click.play(1);
						nextScreen = ScreenType.MainMenu;
						return;
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
		storyText.draw(gl);
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
		storyText.image.getTexture().dispose();
		if (nextScreen==null) {
			Assets.titleAndMapTheme.stop();
			Assets.titleAndMapTheme.dispose();
		}
	}

	@Override
	public boolean transitionIn(float deltaTime) {
		return goBackButton.transitionIn(deltaTime) | storyText.transitionIn(deltaTime);
	}

	@Override
	public boolean transitionOut(float deltaTime) {
		return goBackButton.transitionOut(deltaTime) | storyText.transitionOut(deltaTime);
	}

}
