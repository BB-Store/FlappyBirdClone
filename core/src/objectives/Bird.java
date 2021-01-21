package objectives;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Bird implements Disposable {

    private static final int Gravity = -15;
    private static final int Movement = 100;
    private Vector2 position;
    private Vector2 velocity;
    private int size = 50;
    private Texture texture;

    public Bird(int x, int y){
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        texture = new Texture("bird.png");
    }

    public void update(float dt){
        if(position.y > 0) {
            velocity.add(0, Gravity);
        }
        velocity.scl(dt);
        position.add(Movement*dt, velocity.y);
        if(position.y < 0) {
            position.y = 0;
        }

        velocity.scl(1/dt);
    }

    public void jump () {
        velocity.y = 250;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getSize() {
        return size;
    }

    public Texture getTexture() {
        return texture;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
