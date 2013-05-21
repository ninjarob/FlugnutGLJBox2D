package com.pkp.model;

import com.pkp.gameengine.game.ImageDrawable;
import com.pkp.utils.Constants;
import org.jbox2d.common.Vec2;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 2/11/13
 * Time: 6:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class Animation {
    private ImageDrawable image;
    private float totalTime = 0;
    private int step = 0;
    private float spriteWidth;
    private float spriteHeight;
    private List<Vec2> spriteSequence;
    private float width;
    private float height;
    private float delay;

    public Animation(ImageDrawable image, float width, float height, List<Vec2> spriteSequence, float spriteWidth, float spriteHeight) {
        this.image = image;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.spriteSequence = spriteSequence;
        this.width = width;
        this.height = height;
        delay = 1;
    }

    public Animation(ImageDrawable image, float width, float height, List<Vec2> spriteSequence, float spriteWidth, float spriteHeight, float delay) {
        this.image = image;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.spriteSequence = spriteSequence;
        this.width = width;
        this.height = height;
        this.delay = delay;
    }

    public void animationStep (float deltaTime) {
        totalTime += deltaTime;
        if (totalTime*1000 >= Constants.FRAME_CHANGE_RATE*delay) {
            totalTime = 0;
            step ++;
            if (step >= spriteSequence.size())
                step = 0;
            Vec2 seqEle = spriteSequence.get(step);
            int posx = (int)seqEle.x;
            int posy = (int)seqEle.y;
            image.image.resetVertices(-width/2, -height/2, width/2, height/2, posx*spriteWidth, posy*spriteHeight, (posx+1)*spriteWidth, (posy+1)*spriteHeight);
        }
    }

}
