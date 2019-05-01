//package com.oop.platformer.Screens;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.Skin;
//import com.badlogic.gdx.scenes.scene2d.ui.Table;
//import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.utils.viewport.FitViewport;
//import com.badlogic.gdx.utils.viewport.StretchViewport;
//import com.badlogic.gdx.utils.viewport.Viewport;
//
//import com.oop.platformer.Constants;
//import com.oop.platformer.GameClass;
//import com.oop.platformer.util.Assets;
//
//public class MainMenuScreen implements Screen {
//
//    private GameClass gameClass;
//
//    private Stage stage;
//    private Viewport viewport;
//    private OrthographicCamera camera;
//    private Skin skin;
//
//    public MainMenuScreen(GameClass gameClass) {
//
//        this.gameClass = gameClass;
//
//        skin = new Skin(Gdx.files.internal(Constants.SKIN_JSON), Assets.instance.mainMenuAssets.skinAtlas);
//
//        camera = new OrthographicCamera();
//        viewport = new StretchViewport(940, 710, camera);
//        viewport.apply();
//
//        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
//        camera.update();
//
//        stage = new Stage(viewport, gameClass.batch);
//    }
//
//
//    @Override
//    public void show() {
//        //Stage should controll input:
//        Gdx.input.setInputProcessor(stage);
//
//        //Create Table
//        Table mainTable = new Table();
//        //Set table to fill stage
//        mainTable.setFillParent(true);
//        //Set alignment of contents in the table.
////        mainTable.top();
//        mainTable.center();
//
//        //Create buttons
//        TextButton playButton = new TextButton("Play", skin);
//        TextButton exitButton = new TextButton("Exit", skin);
//
//        //Add listeners to buttons
//        playButton.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                gameClass.setScreen(new Level1(gameClass));
//            }
//        });
//        exitButton.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                Gdx.app.exit();
//            }
//        });
//
//        //Add buttons to table
//        mainTable.add(playButton).pad(30);
//        mainTable.row();
//        mainTable.add(exitButton).pad(30);
//
//        //Add table to stage
//        stage.addActor(mainTable);
//    }
//
//    @Override
//    public void render(float delta) {
//        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        gameClass.batch.begin();
//        gameClass.batch.draw(Assets.instance.mainMenuAssets.background,0,0,1000,800);
//        gameClass.batch.end();
//
//        stage.act();
//        stage.draw();
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        viewport.update(width, height);
//        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
//        camera.update();
//    }
//
//    @Override
//    public void pause() {
//
//    }
//
//    @Override
//    public void resume() {
//
//    }
//
//    @Override
//    public void hide() {
//
//    }
//
//    @Override
//    public void dispose() {
//        skin.dispose();
//    }
//}