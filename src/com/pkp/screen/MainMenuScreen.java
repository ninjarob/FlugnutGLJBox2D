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
import com.pkp.model.sprite.MainMenu;
import com.pkp.model.sprite.SoundButton;
import com.pkp.model.sprite.Title;

public class MainMenuScreen extends IScreen implements Swipeable {
	private Title title;
	private Background background;
	private MainMenu mainMenu;
	private SoundButton soundButton;
	
	public MainMenuScreen(IGame game) {
		super(game);
		background = new Background(((GLGame) game), "spacebg1-half-noalpha.gif");
		mainMenu = new MainMenu(((GLGame) game), "mainmenu-new.png");
		title = new Title(((GLGame) game), "flugnut_title.png");
		soundButton = new SoundButton(((GLGame) game), "buttons.png");
		transitionIn = true;
		Assets.titleAndMapTheme.setLooping(true);
		if (Settings.soundEnabled) {
			if (Assets.titleAndMapTheme.isStopped())
				Assets.titleAndMapTheme.play();
		}
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
					int height = glGraphics.getHeight();
					if (inBounds(event, 0, height-32, 32, height)) {
						Settings.soundEnabled = !Settings.soundEnabled;
						soundButton.toggleSound(Settings.soundEnabled);
						if(Settings.soundEnabled) {
							Assets.titleAndMapTheme.play();
							Assets.click.play(1);
						}
						else {
							Assets.titleAndMapTheme.stop();
						}
					}
					else if (inBounds(event, 60*IScreen.SSC, height-(328*IScreen.SSC), 192*IScreen.SSC, 42*IScreen.SSC)) {
						transitionOut = true;
						nextScreen = ScreenType.Map;
						if(Settings.soundEnabled)
							Assets.click.play(1);
						return;
					}
					else if (inBounds(event, 64*IScreen.SSC, height-(286*IScreen.SSC), 192*IScreen.SSC, 42*IScreen.SSC)) {
						return;
					}
					else if (inBounds(event, 64*IScreen.SSC, height-(244*IScreen.SSC), 192*IScreen.SSC, 42*IScreen.SSC)) {
						transitionOut = true;
						nextScreen = ScreenType.Help;
						if(Settings.soundEnabled)
							Assets.click.play(1);
						return;
					}
					else if (inBounds(event, 64*IScreen.SSC, height-(202*IScreen.SSC), 192*IScreen.SSC, 42*IScreen.SSC)) {
						transitionOut = true;
						nextScreen = ScreenType.Story;
						if(Settings.soundEnabled)
							Assets.click.play(1);
						return;
					}
				}
			}
		}
	}
	
	private boolean inBounds(TouchEvent event, float x, float y, float width, float height) {
		if (event.x > x && event.x < x+width-1 && event.y > y && event.y<y+height+1)
			return true;
		return false;
	}

	@Override
	public void present(float deltaTime) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		background.draw(gl);
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		title.draw(gl);
		mainMenu.draw(gl);
		soundButton.draw(gl);
		
		gl.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void pause() {
		Settings.save(game.getFileIO());
	}

	@Override
	public void resume() {
		if (Settings.soundEnabled) {
			if (Assets.titleAndMapTheme.isStopped())
				Assets.titleAndMapTheme.play();
		}
		gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
		gl.glClearDepthf(0);
		gl.glClearColor(1, 1, 0, 1);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0, IScreen.glGraphics.getWidth(), 0, IScreen.glGraphics.getHeight(), 1, -1);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
	}

	@Override
	public void dispose() {
		background.image.getTexture().dispose();
		mainMenu.image.getTexture().dispose();
		soundButton.image.getTexture().dispose();
		title.image.getTexture().dispose();
		if (nextScreen==null) {
			Assets.titleAndMapTheme.stop();
			Assets.titleAndMapTheme.dispose();
		}
	}
	
	@Override
	public boolean transitionIn(float deltaTime) {
		return title.transitionIn(deltaTime) | mainMenu.transitionIn(deltaTime) | soundButton.transitionIn(deltaTime);
	}

	@Override
	public boolean transitionOut(float deltaTime) {
		return title.transitionOut(deltaTime) | mainMenu.transitionOut(deltaTime) | soundButton.transitionOut(deltaTime);
	}
}
