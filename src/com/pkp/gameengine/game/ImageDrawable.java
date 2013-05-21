package com.pkp.gameengine.game;

import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import com.pkp.gameengine.i.Destroyable;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import javax.microedition.khronos.opengles.GL10;

public class ImageDrawable extends Drawable implements Destroyable {
    protected GLGame game;
    public GL2DRectImage image;
    protected float backgroundRed = 1;
    protected float backgroundGreen = 1;
    protected float backgroundBlue = 1;

    //size
    public float width;
    public float height;
    public Float tcstartx;
    public Float tcstarty;
    public Float tcendx;
    public Float tcendy;

    //name (path) of the image file
    public String filename;
    //the x coordinate positioning of the image  (0 is the left side of the image, 1 is the right, 0.5 is middle)
    protected float imageXCenterScale;
    //the y coordinate positioning of the image  (0 is the bottom of the image, 1 is the top, 0.5 is middle)
    protected float imageYCenterScale;

    public ImageDrawable() {
        super();
    }

    public ImageDrawable(GLGame game, String filename, Vec2 position, float width, float height, float imageXCenterScale, float imageYCenterScale) {
        super(position);
        this.game = game;
        this.filename = filename;
        this.width = width;
        this.height = height;
        this.imageXCenterScale = imageXCenterScale;
        this.imageYCenterScale = imageYCenterScale;
        image = new GL2DRectImage(game, filename, imageXCenterScale, imageYCenterScale, width, height);
    }

    public ImageDrawable(GLGame game, String filename, Vec2 position, float width, float height, float imageXCenterScale, float imageYCenterScale, float tcstartx, float tcstarty, float tcendx, float tcendy) {
        super(position);
        this.game = game;
        this.filename = filename;
        this.width = width;
        this.height = height;
        this.imageXCenterScale = imageXCenterScale;
        this.imageYCenterScale = imageYCenterScale;
        this.tcstartx = tcstartx;
        this.tcstarty = tcstarty;
        this.tcendx = tcendx;
        this.tcendy = tcendy;
        image = new GL2DRectImage(game, filename, imageXCenterScale, imageYCenterScale, width, height, tcstartx, tcstarty, tcendx, tcendy);
    }

    public ImageDrawable(GLGame game, String filename, Vec2 position, float width, float height, float tcstartx, float tcstarty, float tcendx, float tcendy) {
        super(position);
        this.game = game;
        this.filename = filename;
        this.width = width;
        this.height = height;
        this.tcstartx = tcstartx;
        this.tcstarty = tcstarty;
        this.tcendx = tcendx;
        this.tcendy = tcendy;
        image = new GL2DRectImage(game, filename, 0, 0, width, height, tcstartx, tcstarty, tcendx, tcendy);
    }

    public ImageDrawable(GLGame game, String filename, Vec2 position, float width, float height) {
        super(position);
        this.game = game;
        this.filename = filename;
        this.width = width;
        this.height = height;
        image = new GL2DRectImage(game, filename, 0.0f, 0.0f, width, height);
    }

    public ImageDrawable(GLGame game, String filename, float width, float height) {
        super();
        this.game = game;
        this.filename = filename;
        this.width = width;
        this.height = height * IScreen.SSC;
        image = new GL2DRectImage(game, filename, 0.0f, 0.0f, width, height);
    }

    //for backgrounds
    public ImageDrawable(GLGame game, String filename, float width, float height, boolean background) {
        super();
        this.game = game;
        this.filename = filename;
        this.width = width * IScreen.SSC;
        this.height = height * IScreen.SSC;
        image = new GL2DRectImage(game, filename, 0.0f, 0.0f, width, height, background);
    }

    public void setNewImagefile(String newFilename) {
        this.image.getTexture().dispose();
        this.filename = newFilename;
        if (tcstartx == null) {
            image = new GL2DRectImage(game, newFilename, imageXCenterScale, imageYCenterScale, width, height);
        }
        else {
            image = new GL2DRectImage(game, newFilename, imageXCenterScale, imageYCenterScale, width, height, tcstartx, tcstarty, tcendx, tcendy);
        }
    }

    @Override
    public void draw(GL10 gl) {
        gl.glLoadIdentity();
        gl.glTranslatef(def.position.x, def.position.y, 0);
        image.draw(gl);
    }

    public void drawWithTrans(GL10 gl, float transx, float transy) {
        gl.glLoadIdentity();
        gl.glTranslatef(def.position.x + transx, def.position.y + transy, 0);
        image.draw(gl);
    }

    public void destroy() {
        if (image.getTexture() != null)
            image.getTexture().dispose();
    }
}
