package com.oop.platformer.GameObjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.oop.platformer.GameClass;
import com.oop.platformer.Screens.Level1;

public class GombaEnemy extends GameObjects {

    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;


    public GombaEnemy(World world, Vector2 position, Level1 level1Screen) {
        super(world, position, level1Screen,"");
        this.define();
    }

    @Override
    public void define() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;

        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();

        CircleShape shape = new CircleShape();
        shape.setRadius(5 / GameClass.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
