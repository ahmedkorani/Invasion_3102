package com.oop.platformer.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oop.platformer.Constants;
import com.oop.platformer.GameClass;
import com.oop.platformer.util.Assets;

import java.util.ArrayList;
import java.util.Collections;

public class IntroScreen implements Screen {

    private boolean end;
    private boolean mainThemeMusicPlayState;

    private enum FrameState {FirstFrame, SecondFrame, ThirdFrame}

    private FrameState currentFrame;
    private int currentLine;
    private String currentStoryLine;
    private ArrayList<String> storyLines;
    private boolean introEndingSoundEffect;

    private float currentTime;
    private float previousTime;

    private GameClass gameClass;
    private Viewport viewport;

    public IntroScreen(GameClass gameClass) {
        this.gameClass = gameClass;
        mainThemeMusicPlayState = GameClass.isMusicPaused;
        GameClass.isMusicPaused = true;
        Assets.instance.audio.introMusic.play();

        OrthographicCamera camera = new OrthographicCamera();
        viewport = new StretchViewport(GameClass.screenWidth, GameClass.screenHeight, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        storyLines = getStory();
        currentLine = 0;
        currentStoryLine = storyLines.get(0);
        currentTime = 0;
        previousTime = 0;
        end = false;
        currentFrame = FrameState.FirstFrame;
        introEndingSoundEffect = false;
    }

    private ArrayList<String> getStory() {
        //Read the file
        FileHandle fileHandle = Gdx.files.internal(Constants.STORY_FILE);
        //Reading the file text and passing it to a string
        String story = fileHandle.readString();
        //splitting the lines to string array
        String[] splittedLines = story.split("\n");
        ArrayList<String> storyLines = new ArrayList<String>();
        //copying array of strings to the ArrayList to return it at the end
        Collections.addAll(storyLines, splittedLines);
        return storyLines;
    }

    private Texture getCurrentBackground() {
        setCurrentFrame();

        if (currentFrame.equals(FrameState.FirstFrame)) {
            return Assets.instance.mainMenuAssets.FirstFrame;
        } else if (currentFrame.equals(FrameState.SecondFrame))
            return Assets.instance.mainMenuAssets.SecondFrame;
        else if (currentFrame.equals(FrameState.ThirdFrame))
            return Assets.instance.mainMenuAssets.ThirdFrame;
        else
            return Assets.instance.mainMenuAssets.FirstFrame;
    }

    private void setCurrentFrame() {

        switch (currentLine) {
            case 0:
            case 1:
            case 2:
            case 3:
                currentFrame = FrameState.FirstFrame;
                break;
            case 4:
            case 5:
                currentFrame = FrameState.SecondFrame;
                break;
            case 6:
            case 7:
                currentFrame = FrameState.ThirdFrame;
                break;
        }
    }

    private void updateGameFrames(float deltaTime) {
        //Begin drawing
        gameClass.batch.begin();

        gameClass.batch.draw(getCurrentBackground(), 0, 0, GameClass.screenWidth, GameClass.screenHeight);
        Assets.instance.customFont.font.draw(gameClass.batch, getCurrentLine(deltaTime), 50, 200);
        Assets.instance.customFont.font.draw(gameClass.batch, "Space to Skip...", 1400, 1000);

        //End drawing
        gameClass.batch.end();
    }

    private String getCurrentLine(float deltaTime) {
        currentTime += deltaTime;

        if (currentTime - previousTime >= 5) {
            previousTime = currentTime;
            currentLine++;
            currentStoryLine = storyLines.get(currentLine);
        }

        return currentStoryLine;
    }

    private void checkIntroEnd() {
        if (currentLine == 7 && !introEndingSoundEffect) {
            Assets.instance.audio.introMusic.stop();
            Assets.instance.audio.introLastSound.play();
            introEndingSoundEffect = true;
        }

        if (currentLine == 7 && currentTime - previousTime >= 3) {
            GameClass.isMusicPaused = mainThemeMusicPlayState;
            end = true;
        }

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        checkIntroEnd();

        if (end) {
<<<<<<< HEAD
            GameClass.pauseMusic = mainThemeMusicPlayState;
            gameClass.beginLevel();
=======
            GameClass.isMusicPaused = mainThemeMusicPlayState;
            gameClass.endIntro();
>>>>>>> b5c20268985221159680f0c5a5b975820384ecb3
        }


        if (!end)
            updateGameFrames(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            end = true;

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
