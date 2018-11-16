import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Coin extends Sprite
{

    static Image coinpic = null;
    int bounce;

    Coin(int _x, int _y, int _w, int _h, double x_vel_, double y_vel_, Model m)
    {

        x = _x;
        y = _y;
        w = _w;
        h = _h;
        x_vel = x_vel_;
        y_vel = y_vel_;

        model = m;

        if(coinpic == null) {
            try {
                coinpic = ImageIO.read(new File("coin.png"));
            } catch (Exception e) {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }
    }

    Coin(Coin copyme, Model newmodel) //Copy constructor
    {

        super(copyme, newmodel);

        if(coinpic == null) {
            try {
                coinpic = ImageIO.read(new File("coin.png"));
            } catch (Exception e) {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }
    }

    Coin cloneme(Model newmodel)
    {
        return new Coin(this, newmodel);
    }

    boolean isCoin(){return true;}

    boolean isCoinBlock() {
        return false;
    }

    boolean isBrick() {
        return false;
    }

    boolean isMario() {
        return false;
    }

    void draw(Graphics g, Model m)
    {
        g.drawImage(coinpic, x - m.scrollPos, y, w, h, null);
    }

    boolean update(Model m)
    {

        if(y > 645)
            return false;

        y_vel += 2.8675309;
        y += y_vel;

        x_vel += 0;
        x += x_vel;

        return true;
    }


    //Unmarshal
    Coin(Json ob)
    {

        if(coinpic == null) {
            try {
                coinpic = ImageIO.read(new File("coin.png"));
            } catch (Exception e) {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }

        ob.getString("type");
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        w = (int)ob.getLong("w");
        h = (int)ob.getLong("h");
        x_vel = (int)ob.getDouble("x_vel");
        y_vel = (int)ob.getDouble("y_vel");
    }

    //Marshal
    Json marshall()
    {
        Json ob = Json.newObject();
        ob.add("type", "Coin");
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        ob.add("x_vel", x_vel);
        ob.add("y_vel", y_vel);
        return ob;
    }
}
