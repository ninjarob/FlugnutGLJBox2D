package com.pkp.screen;

import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import com.pkp.gameengine.game.Assets;
import com.pkp.gameengine.game.Settings;

public class LoadingScreen extends IScreen {

	public LoadingScreen(GLGame game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		Assets.click = game.getAudio().newSound("click.ogg");
        Assets.explode = game.getAudio().newSound("Sound/explosion-01.mp3");
        Assets.ras = game.getAudio().newSound("Sound/ras.mp3");
		Assets.mainTheme = game.getAudio().newMusic("flugnutmaintheme.mp3");
		Assets.titleAndMapTheme = game.getAudio().newMusic("funkypunk.mp3");
		Settings.load(game.getFileIO());
		game.setScreen(new MainMenuScreen(game));
	}

	@Override
	public void present(float deltaTime) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {}

	@Override
	public boolean transitionIn(float deltaTime) {
		return false;
	}

	@Override
	public boolean transitionOut(float deltaTime) {
		return false;
	}
}
