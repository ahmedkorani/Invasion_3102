package com.oop.platformer.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oop.platformer.Constants;
import com.oop.platformer.GameClass;
import com.oop.platformer.util.Assets;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class IntroScreen implements Screen {

    private boolean end;

    private enum FrameState {FirstFrame, SecondFrame, ThirdFrame}

    private FrameState currentFrame;
    private int currentLine;
    private String currentStoryLine;
    private ArrayList<String> storyLines;

    private float currentTime;
    private float previousTime;

    private GameClass gameClass;
    private Viewport viewport;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private BitmapFont font;

//    Label introLbl;

    public IntroScreen(GameClass gameClass) {

        this.gameClass = gameClass;

        OrthographicCamera camera = new OrthographicCamera();
        viewport = new StretchViewport(GameClass.screenWidth, GameClass.screenHeight, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        //Declaring font and some of it's properties
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.RETRO_FONT));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 54;
        fontParameter.borderWidth = 3;
        fontParameter.borderColor = Color.PINK;
        fontParameter.color = Color.CYAN;
        //Assigning the font generator to the bitmap font
        font = fontGenerator.generateFont(fontParameter);

        storyLines = getStory();
        currentLine = 0;
        currentStoryLine = storyLines.get(0);
        currentTime = 0;
        previousTime = 0;
        end = false;
        currentFrame = FrameState.FirstFrame;
    }

    private ArrayList<String> getStory() {
        File storyFile = new File(Constants.STORY_FILE);
        Scanner scanner = null;
        try {
            scanner = new Scanner(storyFile);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(3102);
        }
        ArrayList<String> storyList = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            storyList.add(line);
        }
        scanner.close();
        return storyList;
    }

    private Texture getCurrentBackground(){
        setCurrentFrame();

        if(currentFrame.equals(FrameState.FirstFrame)){
            return Assets.instance.mainMenuAssets.FirstFrame;
        }
        else if(currentFrame.equals(FrameState.SecondFrame))
            return Assets.instance.mainMenuAssets.SecondFrame;
        else if(currentFrame.equals(FrameState.ThirdFrame))
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

    private void updateGameFrames(float deltaTime){
        //Begin drawing
        gameClass.batch.begin();

        gameClass.batch.draw(getCurrentBackground(), 0, 0, GameClass.screenWidth, GameClass.screenHeight);
        font.draw(gameClass.batch, getCurrentLine(deltaTime), 50, 200);

        //End drawing
        gameClass.batch.end();
    }

    private String getCurrentLine(float deltaTime) {
        currentTime += deltaTime;

        if(currentTime - previousTime >= 3){
            previousTime = currentTime;
            currentLine++;
            currentStoryLine = storyLines.get(currentLine);
        }

        return currentStoryLine;
    }

    private void checkIntroEnd(){
        if(currentLine == 7 && currentTime - previousTime >= 2)
            end = true;
    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        checkIntroEnd();

        if (end)
            gameClass.endIntro();

        if(!end)
            updateGameFrames(delta);

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
            end = true;

        if (Gdx.input.isKeyJustPressed(Input.Keys.M))
            GameClass.musicPause = !GameClass.musicPause;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
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
