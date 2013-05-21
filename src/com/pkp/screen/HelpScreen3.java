package com.pkp.screen;

import java.util.List;

import com.pkp.gameengine.IScreen;
import com.pkp.gameengine.game.Assets;
import com.pkp.gameengine.game.Settings;
import com.pkp.gameengine.game.Swipeable;
import com.pkp.gameengine.i.IGame;
import com.pkp.gameengine.i.io.ITouchInput.TouchEvent;

public class HelpScreen3 extends IScreen implements Swipeable {

	public HelpScreen3(IGame game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (event.x > 256 && event.y > 416) {
					game.setScreen(new MainMenuScreen(game));
					if (Settings.soundEnabled)
						Assets.click.play(1);
					return;
				}
			}
		}
	}

	@Override
	public void present(float deltaTime) {
//		IGraphics g = game.getGraphics();
//		g.drawPixmap(Assets.background, 0, 0);
//		g.drawPixmap(Assets.help3, 64, 100);
//		g.drawPixmap(Assets.buttons, 256, 416, 0, 64, 64, 64);
		if (transitionIn) transitionIn = transitionIn(deltaTime);
		if (transitionOut) transitionOut = transitionOut(deltaTime);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean transitionIn(float deltaTime) {
		return false;
	}

	@Override
	public boolean transitionOut(float deltaTime) {
		return false;
	}
}
