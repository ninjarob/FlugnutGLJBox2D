package com.pkp.screen;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import com.pkp.model.level.VCPiece;
import com.pkp.model.level.VictoryCondition;
import com.pkp.model.sprite.flugerian.BuildingPiece;
import com.pkp.model.sprite.flugerian.MiscObject;
import com.pkp.model.sprite.snostreblaian.Bomb;
import com.pkp.utils.Constants;
import org.jbox2d.common.Vec2;
import com.pkp.gameengine.game.Assets;
import com.pkp.gameengine.game.Settings;
import com.pkp.gameengine.game.Swipeable;
import com.pkp.gameengine.i.IGame;
import com.pkp.gameengine.i.io.ITouchInput.TouchEvent;
import com.pkp.gameengine.io.impl.GLGraphics;
import com.pkp.model.FlugnutWorld;
import com.pkp.model.Zig;
import com.pkp.model.level.Level;
import com.pkp.model.sprite.BorderLine;
import com.pkp.model.sprite.Numbers;
import com.pkp.model.sprite.flugerian.Building;
import com.pkp.model.sprite.flugerian.weapons.EMPCircle;
import com.pkp.model.sprite.flugerian.weapons.EMPHoriz;
import com.pkp.model.sprite.flugerian.weapons.EMPLauncher;
import com.pkp.model.sprite.flugerian.weapons.EMPSuperCircle;
import com.pkp.model.sprite.flugerian.weapons.EMPZigZag;
import com.pkp.model.sprite.flugerian.weapons.SelectorElement;
import com.pkp.model.sprite.flugerian.weapons.WeaponSelector;
import com.pkp.model.sprite.level.EndGameButton;
import com.pkp.model.sprite.level.EndLevelView;
import com.pkp.model.sprite.level.GameOver;
import com.pkp.model.sprite.level.PauseButton;
import com.pkp.model.sprite.level.PauseMenu;
import com.pkp.model.sprite.level.Ready;
import com.pkp.utils.Utilities;

public class GameScreen extends IScreen implements Swipeable{
	public final static float TOP_BAR_HEIGHT = IScreen.glGraphics.getHeight() - 30*IScreen.SSC;
    public final static float TBH = TOP_BAR_HEIGHT;
	public final static float BOTTOM_BAR_HEIGHT = 46*IScreen.SSC;
	public final static float BBH = BOTTOM_BAR_HEIGHT;
	//private FPSCounter fpsCounter = new FPSCounter();
	private PauseButton pauseButton;
	private EndGameButton endGameButton;
	private Ready ready;
	private PauseMenu pauseMenu;
	private GameOver gameOver;
	private GameOver gameWin;
	private Numbers numbers;
	private BorderLine topBorderLine;
	private BorderLine bottomBorderLine;
	private WeaponSelector weaponSelector;
	private Vec2 touchPos;
	public Level level;
	public EndLevelView endLevelView;
	public boolean sameZigZag = false;
    public boolean dragEvent = false;
    public float dragEventTime = 0;

	enum GameState {
		Ready, Running, Paused, GameOver, GameWin, GameScore
	}

	GameState state = GameState.Ready;
	FlugnutWorld flugnutWorld;
	int oldScore = 0;
	String score = "0";

	public GameScreen(IGame game, Level level) {
		super(game);
		if (Settings.soundEnabled) {
			if (Assets.titleAndMapTheme.isPlaying()) {
				Assets.titleAndMapTheme.stop();
			}
			Assets.mainTheme.setLooping(true);
			if (!Assets.mainTheme.isPlaying())
				Assets.mainTheme.play();
		}
        this.level = level;
        this.level.initLevel();
		touchPos = new Vec2();
		GLGame g = (GLGame) game;
		ready = new Ready(g, "ready.png");
		pauseButton = new PauseButton(g, "buttons.png");
		endGameButton = new EndGameButton(g, "buttons.png");
		pauseMenu = new PauseMenu(g, "pausemenu.png");
		gameOver = new GameOver(g, "gameover.png");
		gameWin = new GameOver(g, "gamewon.png");
		numbers = new Numbers(g, "numbers.png", .8f);
		numbers.def.position.x = glGraphics.getWidth();
		numbers.def.position.y = glGraphics.getHeight();
		flugnutWorld = new FlugnutWorld((GLGame) game, this);
		topBorderLine = new BorderLine(0, TOP_BAR_HEIGHT, glGraphics.getWidth(), TOP_BAR_HEIGHT);
		bottomBorderLine = new BorderLine(0, BOTTOM_BAR_HEIGHT, glGraphics.getWidth(), BOTTOM_BAR_HEIGHT);
		endLevelView = new EndLevelView(g, "testgrey.png");
		transitionIn = true;
		weaponSelector = new WeaponSelector(g);
        weaponSelector.weaponsDisabled = !level.weapons;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if (!transitionIn && !transitionOut)
		{
			List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
			game.getInput().getKeyEvents();
			if (state == GameState.Ready)
				updateReady(touchEvents);
			if (state == GameState.Running)
				updateRunning(touchEvents, deltaTime);
			if (state == GameState.Paused)
				updatePaused(touchEvents);
			if (state == GameState.GameOver || state == GameState.GameWin)
				updateGameOver(touchEvents);
			if (state == GameState.GameScore)
				updateGameScore(touchEvents, deltaTime);
		}
	}

	private void updateReady(List<TouchEvent> touchEvents) {
		if (touchEvents.size() > 0)
		{
			state = GameState.Running;
		}
	}

	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		timePassed += deltaTime;
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			touchPos.x = event.x; 
			touchPos.y = glGraphics.getHeight() - event.y;

			if (event.type == TouchEvent.TOUCH_UP) {
				if (event.x < pauseButton.width && event.y < pauseButton.height) {
					if (Settings.soundEnabled)
						Assets.click.play(1);
					state = GameState.Paused;
					transitionIn = true;
					return;
				}
				else if (touchPos.y<TBH && touchPos.y > BBH) {
                    //guy can't go lower than the bottom bar
                    if (touchPos.y < BBH + flugnutWorld.guy.height/2)
                        touchPos.y = BBH+flugnutWorld.guy.height/2;
                    //when they click on the play screen a second time, the current action needs to end.
					endActions();
                    if (!checkGuyClick()) {
                        BuildingPiece objectClicked = checkObjClick();
                        flugnutWorld.guy.setObjectClicked(objectClicked);
                        if (objectClicked != null || !level.weapons) {
                            moveEvent();
                        }
                        else {
                            SelectorElement se = weaponSelector.elements.get(weaponSelector.selectedIndex);
                            if (weaponSelector.selectedIndex == 0) {  //circle  emp
                                moveEvent();
                                activateMoveToWeapon(null);
                                flugnutWorld.empCircleFired = true;
                            }
                            else if (weaponSelector.selectedIndex == 1) {  //emp launcher
                                if (se.ammo != 0) {
                                    flugnutWorld.empLaunchers.add(new EMPLauncher((GLGame)game,
                                                                    "missile.png",
                                                                    flugnutWorld.guy.body.getPosition().x,
                                                                    flugnutWorld.guy.body.getPosition().y,
                                                                    touchPos.x/Constants.V_SCALE,
                                                                    touchPos.y/Constants.V_SCALE));
                                    flugnutWorld.weaponsUsed += 1;
                                    if(se.ammo > 0) se.ammo -= 1;
                                }
                            }
                            else if (weaponSelector.selectedIndex == 2) { //horiz emp
                                if (se.ammo != 0) {
                                    moveEvent();
                                    activateMoveToWeapon(se);
                                    flugnutWorld.horizEmpFired = true;
                                }
                            }
                            else if (weaponSelector.selectedIndex == 3) { //super circle
                                if (se.ammo != 0) {
                                    moveEvent();
                                    activateMoveToWeapon(se);
                                    flugnutWorld.empSuperCircleFired = true;
                                }
                            }
                            else if (weaponSelector.selectedIndex == 4) { //zig zag
                                if (se.ammo != 0) {
                                    if (sameZigZag)
                                    {
                                        EMPZigZag currentZigZag = flugnutWorld.empZigZags.get(flugnutWorld.empZigZags.size()-1);
                                        Zig lastZig = currentZigZag.zigs.get(currentZigZag.zigs.size()-1);
                                        currentZigZag.addZig(lastZig.ev.x, lastZig.ev.y);
                                    }
                                    else
                                        flugnutWorld.empZigZags.add(new EMPZigZag(flugnutWorld.guy.body.getPosition().x, flugnutWorld.guy.body.getPosition().y));
                                    sameZigZag = true;
                                    moveEvent();
                                    activateMoveToWeapon(se);
                                    flugnutWorld.zigZagEmpFired = true;
                                }
                            }
                        }
                    }
				}
				else if (level.weapons && glGraphics.getHeight()-event.y < BBH) {
					//when they select a new weapon, the current action needs to end.
					sameZigZag = false;
					endActions();
					weaponSelector.selectWeapon(event.x);
				}
			}
			else if (event.type == TouchEvent.TOUCH_DOWN) {

			}
            else if (event.type == TouchEvent.TOUCH_DRAGGED) {

                if (/*dragEvent && */touchPos.y<TBH && touchPos.y > BBH && weaponSelector.selectedIndex != 1) {
                    //guy can't go lower than the bottom bar
                    if (touchPos.y < BBH + flugnutWorld.guy.height/2)
                        touchPos.y = BBH+flugnutWorld.guy.height/2;
                    moveEvent();
                }
            }
		}
		flugnutWorld.update(deltaTime);
		if (level.victory())
		{
			state = GameState.GameWin;
			transitionIn = true;
		}
		else if (flugnutWorld.gameOver) {
			state = GameState.GameOver;
			transitionIn = true;
		}
		if (oldScore != flugnutWorld.score) {
			oldScore = flugnutWorld.score;
			score = "" + oldScore;
		}
	}

    private BuildingPiece checkObjClick() {
        BuildingPiece bp = null;
        for (Building b : flugnutWorld.buildings) {
            bp = b.handleObjClick(touchPos.x/Constants.V_SCALE, touchPos.y/Constants.V_SCALE);
            if (bp != null)
                break;
        }
        return bp;
    }

    private boolean checkGuyClick() {
        boolean clicked = false;
        if (flugnutWorld.guy.objectCarried != null) {
            clicked = flugnutWorld.guy.body.getFixtureList().testPoint(new Vec2(touchPos.x/Constants.V_SCALE, touchPos.y/Constants.V_SCALE));
            if (clicked)
            {
                flugnutWorld.guy.objectCarried.setPickedUp(false);
                flugnutWorld.guy.objectCarried = null;
            }
        }
        return clicked;
    }

    private void moveEvent() {
        flugnutWorld.guy.newDestination.set(touchPos.x/Constants.V_SCALE, touchPos.y/Constants.V_SCALE);
        flugnutWorld.guy.arrived = false;
        flugnutWorld.guy.missileArrived = false;
    }

	private void activateMoveToWeapon(SelectorElement se) {
			FlugnutWorld.empWave.x=touchPos.x/Constants.V_SCALE;
			FlugnutWorld.empWave.y=touchPos.y/Constants.V_SCALE;
            //TODO Move these to the right spot(s).  You should not have used the weapon or the ammo until arrival.
			flugnutWorld.weaponsUsed += 1;
			if(se != null && se.ammo > 0) se.ammo -= 1;
	}
	
	private void endActions() {
		flugnutWorld.empCircleFired = false;
		flugnutWorld.horizEmpFired = false;
		flugnutWorld.empSuperCircleFired = false;
		flugnutWorld.zigZagEmpFired = false;
	}
	
//	public void updateWaveBar(float deltaTime) {
//		float percentTotalTime = (float)timePassed/level.totalTime;
//		if (percentTotalTime <= 1)
//		level.waveBar.frontWidth = (1-percentTotalTime)*level.waveBar.backWidth; 
//	}
	
	private void updatePaused(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (event.x > 80 * IScreen.SSC
						&& event.x <= 240 * IScreen.SSC) {
					if (event.y > 100 * IScreen.SSC
							&& event.y <= 148 * IScreen.SSC) {
						if (Settings.soundEnabled)
							Assets.click.play(1);
						state = GameState.Running;
						return;
					}
					if (event.y > 148 * IScreen.SSC
							&& event.y < 196 * IScreen.SSC) {
						if (Settings.soundEnabled)
							Assets.click.play(1);
						transitionOut = true;
						nextScreen = ScreenType.Map;
						return;
					}
				}
			}
		}
	}

	private void updateGameOver(List<TouchEvent> touchEvents) {
		if (!transitionIn && !transitionOut) {
			int len = touchEvents.size();
			for (int i = 0; i < len; i++) {
				TouchEvent event = touchEvents.get(i);
				if (event.type == TouchEvent.TOUCH_UP) {
					if (glGraphics.getHeight()-event.y >= glGraphics.getHeight()*.7f*IScreen.SSC) {
						if (Settings.soundEnabled)
							Assets.click.play(1);
						transitionOut = true;
						if (state == GameState.GameOver) {
							nextScreen = ScreenType.Map;
						}
						else if (state == GameState.GameWin) {
							Settings.addScore(level.levelNum-1, flugnutWorld.score);
							Settings.save(game.getFileIO());
							endLevelView.scorecounter.enemiesScore = flugnutWorld.score;
							for (Building building : flugnutWorld.buildings) {
									endLevelView.scorecounter.buildingsScore +=building.health*100;
							}
							endLevelView.scorecounter.efficiencyScore = 1000 - flugnutWorld.weaponsUsed;
						}
						return;
					}
				}
			}
		}
	}
	
	private void updateGameScore(List<TouchEvent> touchEvents, float deltaTime) {
		if (!transitionIn && !transitionOut) {
			int len = touchEvents.size();
			for (int i = 0; i < len; i++) {
				TouchEvent event = touchEvents.get(i);
				if (event.type == TouchEvent.TOUCH_UP) {
					if (glGraphics.getHeight()-event.y >= glGraphics.getHeight()*.4f*IScreen.SSC) {
						if (Settings.soundEnabled)
							Assets.click.play(1);
						transitionOut = true;
						nextScreen = ScreenType.Map;
						return;
					}
				}
			}
			if (endLevelView.currentScoreCount < endLevelView.scorecounter.enemiesScore)
			{
				endLevelView.currentScoreCount += deltaTime*1000;
			}
			else if (endLevelView.currentBuildingsCount < endLevelView.scorecounter.buildingsScore){
				for (Building building : flugnutWorld.buildings) {
                    if (building.health > 0) {
                        endLevelView.scorecounter.buildingsScore += building.health*100;
                        building.health = 0;
                    }
				}
			}
			else if (endLevelView.currentEfficiencyCount < endLevelView.scorecounter.efficiencyScore){
				endLevelView.currentEfficiencyCount += deltaTime*1000;
			}
			flugnutWorld.score = (int)endLevelView.scorecounter.total;
			if (endLevelView.currentScoreCount > flugnutWorld.score) endLevelView.currentScoreCount = flugnutWorld.score;
			if (endLevelView.currentScoreCount > level.oneSymbolScore) endLevelView.symbols = 1;
			if (endLevelView.currentScoreCount > level.twoSymbolScore) endLevelView.symbols = 2;
			if (endLevelView.currentScoreCount > level.threeSymbolScore) endLevelView.symbols = 3;
		}
	}

	@Override
	public void present(float deltaTime) {
		//fpsCounter.logFrame();
		// background
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		level.background.draw(gl);
//		level.waveBar.draw(gl);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		drawWorld(flugnutWorld, glGraphics, gl);
		if (state == GameState.Ready)
			drawReadyUI();
		if (state == GameState.Running)
			drawRunningUI();
		if (state == GameState.Paused)
			drawPausedUI();
		if (state == GameState.GameOver || state == GameState.GameWin)
			drawGameOverUI();
		if (state == GameState.GameScore)
			drawGameScoreUI();
		Utilities.drawText(score, gl, glGraphics, numbers);
		topBorderLine.draw(gl);
		bottomBorderLine.draw(gl);
		gl.glDisable(GL10.GL_BLEND);
	}

	private void drawWorld(FlugnutWorld world, GLGraphics glGraphics, GL10 gl) {
		for (int i = 0; i < world.buildings.size(); i++) {
			Building b = world.buildings.get(i);
			b.draw(gl);
		}

        for (int i = 0; i < world.miscObjects.size(); i++) {
            MiscObject mo = world.miscObjects.get(i);
            mo.draw(gl);
        }

		// draw bombs
		for (int i = 0; i < world.bombs.size(); i++) {
			Bomb bomb = world.bombs.get(i);
			bomb.draw(gl);
		}

		// draw emp circles
		for (int i = 0; i < world.empCircles.size(); i++) {
			EMPCircle am = world.empCircles.get(i);
			am.draw(gl);
		}
		
		// draw anti-missile launchers
		for (int i = 0; i < world.empLaunchers.size(); i++) {
			EMPLauncher am = world.empLaunchers.get(i);
			am.draw(gl);
		}
		
		// draw horizontal emps
		for (int i = 0; i < world.horizEmps.size(); i++) {
			EMPHoriz he = world.horizEmps.get(i);
			he.draw(gl);
		}
		
		// draw super emps circles
		for (int i = 0; i < world.empSuperCircles.size(); i++) {
			EMPSuperCircle am = world.empSuperCircles.get(i);
			am.draw(gl);
		}
		
		// draw zig zags
		for (int i = 0; i < world.empZigZags.size(); i++) {
			EMPZigZag am = world.empZigZags.get(i);
			am.draw(gl);
		}
		
		world.guy.draw(gl);
		weaponSelector.draw(gl);
	}

	private void drawReadyUI() {
		ready.draw(gl);
	}

	private void drawRunningUI() {
		pauseButton.draw(gl);
	}

	private void drawPausedUI() {
		pauseMenu.draw(gl);
	}

	private void drawGameOverUI() {
		endGameButton.draw(gl);
		if (state == GameState.GameOver)
			gameOver.draw(gl);
		else
			gameWin.draw(gl);
	}
	
	private void drawGameScoreUI() {
		endLevelView.draw(gl);
	}

	@Override
	public void pause() {
		if (state == GameState.Running)
			state = GameState.Paused;
		if (flugnutWorld.gameWin) {
			Settings.addScore(level.levelNum-1, flugnutWorld.score);
			Settings.save(game.getFileIO());
		}
	}

	@Override
	public void resume() {
		gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
		gl.glClearDepthf(0);
		gl.glClearColor(1, 0, 0, 1);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0, IScreen.glGraphics.getWidth(), 0,
				IScreen.glGraphics.getHeight(), 1, -1);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
	}

	@Override
	public void dispose() {
		ready.image.getTexture().dispose();
		pauseButton.image.getTexture().dispose();
		endGameButton.image.getTexture().dispose();
		gameOver.image.getTexture().dispose();
        gameWin.image.getTexture().dispose();
		numbers.image.getTexture().dispose();
		pauseMenu.image.getTexture().dispose();
        level.background.image.getTexture().dispose();
		level.mapImage.image.getTexture().dispose();
        level.numbers.image.getTexture().dispose();
        weaponSelector.destroy();
        endLevelView.destroy();
        flugnutWorld.destroy();
		if (nextScreen==null) {
			Assets.mainTheme.stop();
			Assets.mainTheme.dispose();
		}
	}
	
	@Override
	public boolean transitionIn(float deltaTime) {
		if (state == GameState.Ready)
			return ready.transitionIn(deltaTime);
		if (state == GameState.Paused)
			return pauseButton.transitionIn(deltaTime) || pauseMenu.transitionIn(deltaTime);
		if (state == GameState.GameOver)
			return endGameButton.transitionIn(deltaTime) || gameOver.transitionIn(deltaTime);
		if (state == GameState.GameWin)
			return endGameButton.transitionIn(deltaTime) || gameWin.transitionIn(deltaTime);
		if (state == GameState.GameScore)
			return endLevelView.transitionIn(deltaTime);
		return false;
	}

	@Override
	public boolean transitionOut(float deltaTime) {
		if (state == GameState.Paused)
			return pauseButton.transitionOut(deltaTime) || pauseMenu.transitionOut(deltaTime);
		if (state == GameState.GameOver)
			return endGameButton.transitionOut(deltaTime) || gameOver.transitionOut(deltaTime);
		if (state == GameState.GameWin) {
			boolean transitionDone = endGameButton.transitionOut(deltaTime) || gameWin.transitionOut(deltaTime);
			if (!transitionDone) {
				state = GameState.GameScore;
				transitionIn = true;
			}
			return transitionDone; 
		}
		if (state == GameState.GameScore)
			return endLevelView.transitionOut(deltaTime);
		return false;
	}
}
