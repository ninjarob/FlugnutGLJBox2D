package com.pkp.utils;

import com.pkp.screen.GameScreen;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 1/11/13
 * Time: 10:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class Constants {
    //GENERAL CONSTANTS
    public static final String GAME_TITLE = "Flugnut the Flugerian";
    public static final float ANGULAR_FLAT_TOLERANCE = .05f;
    public static final int BIRD_TOP_SPEED = 50;
    public static final int FPS = 60;
    public static final float FRAME_CHANGE_RATE = 1000f/(float)Constants.FPS;
    public static final float V_SCALE = 2;

    //public static final int COLLIDABLE = 1;
    public static final int NON_COLLIDABLE = -1;

    public static final int NON_COL_CATEGORY = 0x0000;
    public static final int DEFAULT_CATEGORY = 0x0001;
    public static final int BAD_GUY_CATEGORY = 0x0002;
    public static final int BUILDING_CATEGORY = 0x0004;
    public static final int PIECE_CATEGORY = 0x0008;
    public static final int GOOD_GUY_CATEGORY = 0x0010;
    public static final int MISC_CATEGORY = 0x0020;
    public static final int GUY_CATEGORY = 0x0040;

    public static final int NON_COL_MASK = 0x0000;
    public static final int BAD_GUY_MASK = 0XFFFF ^ BAD_GUY_CATEGORY;
    public static final int BUILDING_MASK = 0xFFFF;
    public static final int PIECE_MASK = 0xFFFF ^ BAD_GUY_CATEGORY ^ MISC_CATEGORY ^ GOOD_GUY_CATEGORY;
    public static final int PIECE_MASK_GGC_COL = 0xFFFF ^ BAD_GUY_CATEGORY ^ MISC_CATEGORY;
    public static final int GOOD_GUY_MASK = 0xFFFF;
    public static final int GUY_MASK = 0xFFFF ^ BAD_GUY_CATEGORY ^ PIECE_CATEGORY ^ GOOD_GUY_CATEGORY ^ BUILDING_CATEGORY ^ MISC_CATEGORY;
    public static final int MISC_MASK = 0XFFFF ^ PIECE_CATEGORY ^ GUY_CATEGORY ^ MISC_CATEGORY ^ BUILDING_CATEGORY;
    public static final int MISC_MASK_COLLIDE = 0XFFFF ^ PIECE_CATEGORY ^ GUY_CATEGORY ^ MISC_CATEGORY;

    //LEVEL CONSTANTS
    //FLUGNUT'S HOUSE
    public static final String F_HOME_PATH = "FlugnutLevel/House.png";
    public static final float F_HOME_WIDTH = 199;
    public static final float F_HOME_HEIGHT = 100;
    public static final int F_HOME_HEALTH = 10;
    public static final float F_HOME_STARTX = -20;
    public static final float F_HOME_STARTY = -4;

    public static final String F_HOME_CWIN_PATH = "FlugnutLevel/Window.png";
    public static final String F_HOME_CWIN_PATH2 = "FlugnutLevel/BrokenWindow.png";
    public static final float F_HOME_CWIN_RAD = 12;
    public static final float F_HOME_CWIN_Y_OFFSET = 45;

    public static final String F_HOME_DOOR_PATH = "FlugnutLevel/Door.png";
    public static final float F_HOME_DOOR_RAD = 12;
    public static final float F_HOME_DOOR_Y_OFFSET = 35;

    public static final String F_HOME_RP_PATH = "FlugnutLevel/RoofPiece.png";
    public static final float F_HOME_RP_WIDTH = 12;
    public static final float F_HOME_RP_HEIGHT = 8;
    public static final float F_HOME_RP_Y_OFFSET = 54;

    public static final String F_HOME_PYLON_PATH = "FlugnutLevel/Pylon.png";
    public static final String F_HOME_PYLON_CLICKED_PATH = "FlugnutLevel/PylonClicked.png";
    public static final float F_HOME_PYLON_WIDTH = 9;
    public static final float F_HOME_PYLON_HEIGHT = 17;
    public static final float F_HOME_PYLON_Y_OFFSET = 30;

    public static final String F_HOME_FLAG_PATH = "FlugnutLevel/Flag/Flag1.png";
    public static final String F_HOME_FLAG_PATH_ONLY = "FlugnutLevel/Flag/";
    public static final String F_HOME_FLAG_NAME = "Flag";
    public static final float F_HOME_FLAG_WIDTH = 18;
    public static final float F_HOME_FLAG_HEIGHT = 24;
    public static final float F_HOME_FLAG_Y_OFFSET = 85;

    //FLUGNUT'S LAB
    public static final String F_LAB_PATH = "FlugnutLevel/Lab.png";
    public static final float F_LAB_WIDTH = 190;
    public static final float F_LAB_HEIGHT = 63;
    public static final int F_LAB_HEALTH = 10;
    public static final float F_LAB_STARTX = -20;
    public static final float F_LAB_STARTY = 6;
    public static final float F_LAB_FLAG1_Y_OFFSET = 45;
    public static final float F_LAB_FLAG2_Y_OFFSET = 45;
    
    public static final String F_LAB_WELL_PATH = "FlugnutLevel/Well.png";
    public static final float F_LAB_WELL_WIDTH = 9;
    public static final float F_LAB_WELL_HEIGHT = 24;
    
    //FLUGNUT'S HANGAR
    public static final String F_HANGAR_PATH = "FlugnutLevel/Hangar.png";
    public static final float F_HANGAR_WIDTH = 238;
    public static final float F_HANGAR_HEIGHT = 119;
    public static final int F_HANGAR_HEALTH = 10;
    public static final float F_HANGAR_STARTX = -30;
    public static final float F_HANGAR_STARTY = -9;
    
    //MISC
    public static final String BIRD_PATH = "Misc/Bird.png";
    public static final float BIRD_WIDTH = 20;
    public static final float BIRD_HEIGHT = 20;

    //TUTORIAL
    public static final String TUT_BIRD_DOOR_PATH = "Tutorial/BirdCageDoor.png";
    public static final float BIRD_CAGE_STARTX = 80/V_SCALE;
    public static final float BIRD_CAGE_STARTY = GameScreen.BBH/V_SCALE;

    public static final String TOWER_PATH = "Tutorial/Tower.png";
    public static final float TOWER_WIDTH = 40f;
    public static final float TOWER_HEIGHT = 50f;

    public static final String BOX_PATH = "Tutorial/Box.png";
    public static final float BOX_WIDTH = 50f;
    public static final float BOX_HEIGHT = 50f;

    public static final String POD_PATH = "Tutorial/Pod.png";
    public static final float POD_RADIUS = 30;
}
