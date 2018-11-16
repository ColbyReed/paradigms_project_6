import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Brick extends Sprite
{

static Image brickpic = null;

    Brick(int _x, int _y, int _w, int _h)
    {

        x = _x;
        y = _y;
        w = _w;
        h = _h;


        if(brickpic == null) {
            try {
                brickpic = ImageIO.read(new File("Brick_Block.png"));
            } catch (Exception e) {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }
    }

    Brick(Brick copyme, Model newmodel) //Copy constructor
    {
        super(copyme, newmodel);

        if(brickpic == null) {
            try {
                brickpic = ImageIO.read(new File("Brick_Block.png"));
            } catch (Exception e) {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }
    }

    Brick cloneme(Model newmodel)
    {
        return new Brick(this, newmodel);
    }

    boolean isBrick() {
        return true;
    }

    boolean isCoinBlock() {
        return false;
    }

    boolean isMario() {
        return false;
    }

    boolean isCoin(){return false;}

    void draw(Graphics g, Model m)
    {

        //Sprite s = model.sprites.get(i);
        g.drawRect(x - m.scrollPos, y, w, h);
        g.drawImage(brickpic, x - m.scrollPos, y, w, h, null);
        //scrollPos++;
    }

    boolean update(Model m)
    {
        return true;
    }


    //Unmarshal
    Brick(Json ob)
    {

        if(brickpic == null) {
            try {
                brickpic = ImageIO.read(new File("Brick_Block.png"));
            } catch (Exception e) {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }

        ob.getString("type");
        //ob.get
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        w = (int)ob.getLong("w");
        h = (int)ob.getLong("h");
    }
    
    //Marshal
    Json marshall()
    {
        Json ob = Json.newObject();
        ob.add("type", "Brick");
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        return ob;
    }
}
