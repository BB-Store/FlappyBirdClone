package objectives;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import de.tutorial.flappybird.clone.Mainactivity;

public class Clouds implements Disposable {

    private Array<Texture> regClouds;
    private Array<Cloud> clouds;
    private Texture cloud01;
    private Texture cloud02;
    private Texture cloud03;

    @Override
    public void dispose() {
        clouds.clear();
        cloud01.dispose();
        cloud02.dispose();
        cloud03.dispose();
        regClouds.clear();
    }


    private class Cloud {
        private Texture regCloud;
        private Vector2 position;
        private Vector2 speed;
        private int width = 65;
        private int height = 50;

        public Cloud(){
            position = new Vector2();
            speed = new Vector2();
        }

        public void setRegion (Texture region){
            regCloud = region;
        }

        public void render(SpriteBatch batch) {
            batch.draw(regCloud, position.x, position.y, width, height);
        }

        public void updateCloud(float deltaTime){
            position.x += speed.x * deltaTime;
        }
    }

    public Clouds(float camPosX){
        cloud01 = new Texture(Gdx.files.internal("cloud01.png"));
        cloud02 = new Texture(Gdx.files.internal("cloud02.png"));
        cloud03 = new Texture(Gdx.files.internal("cloud03.png"));

        regClouds = new Array<Texture>();
        regClouds.add(cloud01);
        regClouds.add(cloud02);
        regClouds.add(cloud03);
        int numClouds = 10;

        clouds = new Array<Cloud>(2*numClouds);
        for (int i = 0; i < numClouds; i++){
            Cloud cloud = spawnCloud(camPosX);
            cloud.position.x = i * 100;
            clouds.add(cloud);
        }
    }

    private Cloud spawnCloud(float camPosX){
        Cloud cloud = new Cloud();
        cloud.setRegion(regClouds.random());

        Vector2 pos = new Vector2();
        pos.x = camPosX + Mainactivity.Width * 0.5f +10;
        pos.y = Mainactivity.Height * 0.5f - 85;
        pos.y += MathUtils.random(0.0f, 35.5f) * (MathUtils.randomBoolean() ? 1 : -1);

        cloud.position.set(pos);

        Vector2 speed = new Vector2();
        speed.x = 3.5f;
        speed.x += MathUtils.random(0.5f, 3.5f);
        speed.x *= -1;
        cloud.speed.set(speed);
        return cloud;
    }

    public void update(float deltaTime, float camPosX, float camWidth){
        for(int i = clouds.size - 1; i >= 0; i--){
            Cloud cloud = clouds.get(i);
            cloud.updateCloud(deltaTime);
            if(cloud.position.x < camPosX - (camWidth + 10)){
                clouds.removeIndex(i);
                clouds.add(spawnCloud(camPosX));
            }
        }
    }

    public void render(SpriteBatch batch){
        for(Cloud cloud : clouds) cloud.render(batch);
    }


}
