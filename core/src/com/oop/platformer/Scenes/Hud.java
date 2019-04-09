package com.oop.platformer.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oop.platformer.GameClass;


import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import com.badlogic.gdx.utils.Disposable;



import java.awt.*;

public class Hud implements  Disposable{
    public Stage stage;
    private Viewport viewport;

    private Integer score;
    private Integer lives;
    private Integer health;
    private Float timer;

    Label lbl_health;
    Label lbl_score;
    Label lbl_lives;


    public  Hud(SpriteBatch spriteBatch){

        lives = 4;
        health = 100;
        score = 0;

        viewport = new FitViewport(GameClass.V_WIDTH, GameClass.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        lbl_health = new Label("Health: " + health.toString(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lbl_score = new Label("Score: " + score.toString(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lbl_lives = new Label("Lives: " + lives.toString(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));


//        table.setDebug(true);

        table.add(lbl_score).expand().top().left().padLeft(5);
        table.add(lbl_health).expand().top().right().padRight(5);
        table.row();
        table.add(new Actor());
        table.add(lbl_lives).expand().bottom().right().padRight(5);

        stage.addActor(table);



    }


    @Override
    public void dispose() {
        stage.dispose();
    }
}
