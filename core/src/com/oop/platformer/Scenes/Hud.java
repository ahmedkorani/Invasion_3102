package com.oop.platformer.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oop.platformer.Constants;
import com.oop.platformer.GameClass;


public class Hud implements Disposable {
    public Stage stage;

    private Integer score;
    private Integer lives;

    private Label lbl_score;
    private Label lbl_lives;


    public Hud(SpriteBatch spriteBatch) {

        lives = Constants.LIVES;
        score = Constants.SCORE;

        Viewport viewport = new FitViewport(GameClass.V_WIDTH, GameClass.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        lbl_score = new Label("Score: " + score.toString(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lbl_lives = new Label("Lives: " + lives.toString(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));


        table.add(lbl_score).expand().top().left().padLeft(5);
        table.add(lbl_lives).expand().top().right().padRight(5);

        stage.addActor(table);

    }


    @Override
    public void dispose() {
        stage.dispose();
    }

    //Decrease Player lives on hitting enemies
    public void setLives(int playerLives) {
        lives = playerLives;
        lbl_lives.setText("Lives: " + lives.toString());
    }

    //To increase player score upon doing achievements
    public void setScore(int playerScore) {
        score = playerScore;
        lbl_score.setText("Score: " + score.toString());
    }
}
