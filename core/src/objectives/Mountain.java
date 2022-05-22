package objectives;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Mountain implements Disposable {

    private Texture mountain;
    private Vector2 position;
    private Vector2 positionMountainFront1;
    private Vector2 positionMountainFront2;
    private Vector2 positionMountainMiddle1;
    private Vector2 positionMountainMiddle2;
    private Vector2 positionMountainBack1;
    private Vector2 positionMountainBack2;

    private int width;
    private int height;


    public Mountain(){
        mountain = new Texture(Gdx.files.internal("mountain.png"));
        position = new Vector2();
        positionMountainFront1 = new Vector2(0, 25);
        positionMountainFront2 = new Vector2(1000, 25);
        positionMountainMiddle1 = new Vector2(-10, 85);
        positionMountainMiddle2 = new Vector2(990, 85);
        positionMountainBack1 = new Vector2(-20, 135);
        positionMountainBack2 = new Vector2(980, 135);

        width = 1000;
        height = 230;
    }

    private void drawMountain(SpriteBatch batch, float offset1, float offset2, float offsetY, float tintColor, float parallaxSpeedX){
        batch.setColor(tintColor, tintColor, tintColor, 1);
        batch.draw(mountain, offset1 + position.x*parallaxSpeedX, offsetY, width, height);
        batch.draw(mountain, offset2 + position.x*parallaxSpeedX, offsetY, width, height);
    }

    public void update(float x){
        position.set(x, 0);
        if(position.x * 0.7f > (positionMountainFront1.x + width)){
            positionMountainFront1.add(2000, 0);
        }
        else if(position.x * 0.7f > (positionMountainFront2.x + width)){
            positionMountainFront2.add(2000, 0);
        }
        else if(position.x * 0.5f > (positionMountainMiddle1.x + width)){
            positionMountainMiddle1.add(2000, 0);
        }
        else if(position.x * 0.5f > (positionMountainMiddle2.x + width)){
            positionMountainMiddle2.add(2000, 0);
        }
        else if(position.x * 0.2f > (positionMountainBack1.x + width)){
            positionMountainBack1.add(2000, 0);
        }
        else if(position.x * 0.2f > (positionMountainBack2.x + width)){
            positionMountainBack2.add(2000, 0);
        }
    }

    public void render (SpriteBatch batch){
        drawMountain(batch, positionMountainBack1.x, positionMountainBack2.x, positionMountainBack1.y, 0.5f, 0.8f);
        drawMountain(batch, positionMountainMiddle1.x, positionMountainMiddle2.x, positionMountainMiddle1.y, 0.7f, 0.5f);
        drawMountain(batch, positionMountainFront1.x, positionMountainFront2.x, positionMountainFront1.y, 0.9f, 0.3f);
    }

    @Override
    public void dispose() {
        mountain.dispose();
    }
}
