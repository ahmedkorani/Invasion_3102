package com.oop.platformer.GameObjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.oop.platformer.GameClass;
import com.badlogic.gdx.utils.Array;
import com.oop.platformer.util.Assets;

public abstract class Enemy extends GameObject {

    public Path path;
    public boolean destroyed;
    public boolean isSetToDestroy;
    public int healthPoints;
    public float stateTime;

    public Enemy(World world, Vector2 spritePosition, Array<Vector2> path) {
        super(world, spritePosition);

        destroyed = false;
        isSetToDestroy = false;
        stateTime = 0;
        this.path = new Path(path.size);
        for (Vector2 p : path)
            this.path.AddPoint(p, 15f);
        this.path.Reset();
        initSprite();
        setHealthPoints();
    }

    public abstract void initSprite();

    public abstract void updateSprite();

    public abstract void setHealthPoints();


    @Override
    public void update(float delta) {

        if (isSetToDestroy && !destroyed) {
            world.destroyBody(body);
            destroyed = true;
            Assets.instance.audio.enemyDestroyed.play();
        } else if (!destroyed) {
            stateTime += delta;
            updateSprite();
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            if (path.UpdatePath(body.getPosition())) {
                body.setLinearVelocity(path.GetVelocity().x, path.GetVelocity().y);
            }
        }
    }

    @Override
    public void draw(Batch batch) {
        if (!destroyed)
            super.draw(batch);
    }

    public boolean setToDestroy() {
        //if the enemy has health points left it's not destroyed and hp is decreased
        if (healthPoints == 0) {
            isSetToDestroy = true;
            return true;
        } else {
            healthPoints--;
            Assets.instance.audio.enemyHit.play();
            return false;
        }
    }

    public class Path {

        Array<Vector2> points;
        Array<Float> durations;
        Vector2 velocity;

        int countPoints;
        int currentPointIndex;
        int nextPointIndex;

        static final float CHECK_RADIUS = 1f / GameClass.PPM;

        Path(int countPoints) {

            points = new Array<Vector2>();
            durations = new Array<Float>();
            velocity = new Vector2();
            this.countPoints = countPoints;

        }

        void AddPoint(Vector2 pos, float duration) {
            points.add(pos);
            durations.add(duration);
        }

        void Reset() {
            currentPointIndex = 0;
            nextPointIndex = 0;
            SetNextPointVelocity();
        }

        Vector2 GetCurrentPoint() {
            return points.get(currentPointIndex);
        }

        boolean UpdatePath(Vector2 bodyPosition) {
            return ReachedNextPoint(bodyPosition);
        }

        boolean ReachedNextPoint(Vector2 bodyPosition) {
            Vector2 nextPointPosition = points.get(nextPointIndex);
            float d = nextPointPosition.dst2(bodyPosition);
            if (d < CHECK_RADIUS) {

                currentPointIndex = nextPointIndex;
                nextPointIndex = GetNextPoint();
                SetNextPointVelocity();
                return true;
            }
            return false;
        }

        int GetNextPoint() {
            return (currentPointIndex + 1) % countPoints;

        }

        void SetNextPointVelocity() {
            Vector2 nextPosition = points.get(nextPointIndex);
            Vector2 currentPosition = points.get(currentPointIndex);
            float dx = nextPosition.x - currentPosition.x;
            float dy = nextPosition.y - currentPosition.y;
            float time = durations.get(nextPointIndex);

            velocity.set(dx / time, dy / time);
        }

        Vector2 GetVelocity() {
            return velocity;
        }
    }

}



