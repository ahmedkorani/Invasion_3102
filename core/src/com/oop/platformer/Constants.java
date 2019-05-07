package com.oop.platformer;

public class Constants {

    //Intro PATH
    public static final String FIRST_FRAME = "IntroScreen/FirstFrame.png";
    public static final String SECOND_FRAME = "IntroScreen/SecondFrame.png";
    public static final String THIRD_FRAME = "IntroScreen/ThirdFrame.png";
    public static final String STORY_FILE = "IntroScreen/IntroStory.txt";
    public static final String RETRO_FONT = "IntroScreen/SFAlienEncounters.ttf";

    //Rambo Texture Atlas path
    public static final String PLAYER_TEXTURE_ATLAS = "Player/player.atlas";

    public static final int LIVES = 100;
    public static final int SCORE = 0;

    //Enemy Textures paths
    public static final String ENEMY_TEXTURE_ATLAS = "Enemy/Enemy.atlas";
    public static final String BOSS_TEXTURE_ATLAS = "Enemy/Boss.atlas";

    //Level 1 Map Path
    public static final String MAP = "Map/level1.tmx";

    //Main Menu Buttons and Background
    public static final String BACKGROUND = "StartScreen.png";

    //Bullets Constants
    public static final String BULLET = "Bullets/bullet1.png";
    public static final float FIRE_RATE = 0.4f;

    //Audio Constants

    public static final String START_SCREEN_MUSIC = "Audio/Music/Perturbator - Night Slasher II (Lueur Verte).ogg";
    public static final String MUSIC = "Audio/Music/Perturbator - I Am The Program.ogg";
    public static final String INTRO_MUSIC = "IntroScreen/IntroMusic.ogg";
    public static final String IntroFinalSoundEffect = "IntroScreen/introLastFrame.wav";
    public static final String WIN_MUSIC = "Audio/Music/Perturbator - Positive Attitude Jingle.ogg";
    public static final String LOSE_MUSIC = "Audio/Music/Perturbator - The Price of Failure.ogg";
    public static final String GUN_SHOT = "Audio/Sounds/bullet_normal.wav";
    public static final String ENEMY_DESTROYED = "Audio/Sounds/enemyDestroyed.wav";
    public static final String ENEMY_HIT = "Audio/Sounds/enemy_hit.wav";
    public static final String PLAYER_HIT = "Audio/Sounds/player_hurt.wav";
    public static final String PLAYER_DIED = "Audio/Sounds/player_died.wav";
    public static final String PLAYER_WON = "Audio/Sounds/player_won.wav";
}