package com.oop.platformer.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oop.platformer.GameClass;
import com.oop.platformer.util.Assets;


public class StartScreen implements Screen {

    enum ScreenState {MainMenu, Help, Credits}

    private GameClass gameClass;

    private Viewport viewport;

    private boolean showMainMenu;
    private boolean showCredits;
    private boolean showHelp;

    public StartScreen(GameClass gameClass) {

        this.gameClass = gameClass;

        showMainMenu = true;
        showHelp = false;
        showCredits = false;

        OrthographicCamera camera = new OrthographicCamera();
        viewport = new StretchViewport(GameClass.V_WIDTH / GameClass.PPM, GameClass.V_HEIGHT / GameClass.PPM, camera);
        viewport.apply();

        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        camera.update();


    }

    private void update(float deltaTime) {
        handleInput();
        //Begin drawing
        gameClass.batch.begin();

        gameClass.batch.draw(Assets.instance.mainMenuAssets.mainBackground, 0, 0, GameClass.screenWidth, GameClass.screenHeight);
        drawScreenContent();

        //End drawing
        gameClass.batch.end();
    }

    private void handleInput() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
            gameClass.beginIntro();

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            GameClass.isMusicPaused = !GameClass.isMusicPaused;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            showMainMenu = !showMainMenu;
            showHelp = !showHelp;
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            showMainMenu = !showMainMenu;
            showCredits = !showCredits;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            System.exit(0);
    }

    private void drawScreenContent() {

        ScreenState currentState = getScreenState();

        switch (currentState) {
            case MainMenu:
                Assets.instance.customFont.font.draw(gameClass.batch, "PRESS ENTER", 750, 380);
                Assets.instance.customFont.font.draw(gameClass.batch, "F1 Help", 80, 120);
                Assets.instance.customFont.font.draw(gameClass.batch, "C Credits", 1550, 120);
                break;
            case Help:
                Assets.instance.customFont.font.draw(gameClass.batch, "Arrow Keys: Move", 680, 400);
                Assets.instance.customFont.font.draw(gameClass.batch, "F: Shoot", 840, 300);
                Assets.instance.customFont.font.draw(gameClass.batch, "M: Pause Music", 770, 200);
                Assets.instance.customFont.font.draw(gameClass.batch, "Escape: Exit", 800, 100);
                break;
            case Credits:
                Assets.instance.customFont.font.draw(gameClass.batch, "OOP 2019 project", 710, 900);
                Assets.instance.customFont.font.draw(gameClass.batch, "Ahmed Mahmoud \"Ahmed-MK\"", 500, 700);
                Assets.instance.customFont.font.draw(gameClass.batch, "Andrew Awni \"andrewawni\"", 500, 600);
                Assets.instance.customFont.font.draw(gameClass.batch, "Ahmed Abuamra \"ahmedabuamra\"", 450, 500);
                Assets.instance.customFont.font.draw(gameClass.batch, "AbdulRahman Yousry \"slashdevo\"", 400, 400);
                Assets.instance.customFont.font.draw(gameClass.batch, "Ahmed Elmayyah \"Satharus\"", 480, 300);

                Assets.instance.customFont.font.draw(gameClass.batch, "All assets are credited in project's readme file on GitHub", 30, 150);
                break;
        }
    }

    private ScreenState getScreenState() {

        if (showMainMenu)
            return ScreenState.MainMenu;
        else if (showHelp)
            return ScreenState.Help;
        else if (showCredits)
            return ScreenState.Credits;

        return ScreenState.MainMenu;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        update(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
