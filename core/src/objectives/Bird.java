package objectives;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Bird implements Disposable {

    private static final int Gravity = -15;
    private static final int Movement = 100;
    private Vector2 position;
    private Vector2 velocity;
    private int size = 50;
    private Texture texture;
    private Rectangle bounds;
    private Animator animation;

    public Bird(int x, int y){
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        texture = new Texture("bird.png");
        animation = new Animator(new TextureRegion(texture), 5, 0.5f);
        bounds = new Rectangle(x, y, size, size);
    }

    public void update(float dt){
        animationUpdate(dt);
        if(position.y > 0) {
            velocity.add(0, Gravity);
        }
        velocity.scl(dt);
        position.add(Movement*dt, velocity.y);
        if(position.y < 0) {
            position.y = 0;
        }

        velocity.scl(1/dt);
        bounds.setPosition(position.x, position.y);
    }

    private void animationUpdate(float dt) {
        animation.update(dt);
    }

    public Rectangle getBounds() {
        return bounds;
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

    public TextureRegion getTexture() {
        return animation.getFrame();
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
