import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.Random;

public class CoinBlock extends Sprite
{
    //int scrollPos;
    Model model;
    Mario mario;
    Sprite sprites;
    int coin_score;

    //int CoinBlockState;
    int CoinBlockStates;
    int coins_remaining;

    static Image[] coinblockpic = null;

   Random rand = new Random();

    CoinBlock(int _x, int _y, int _w, int _h)
    {

        x = _x;
        y = _y;
        w = _w;
        h = _h;
        coins_remaining = 5;

        //Draw CoinBlock
        if(coinblockpic == null) {
            coinblockpic = new Image[2];
            try {
                coinblockpic[0] = ImageIO.read(new File("block1.png"));
                coinblockpic[1] = ImageIO.read(new File("block2.png"));
            } catch (Exception e) {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }
    }

    CoinBlock(CoinBlock copyme, Model newmodel) //Copy constructor
    {

        super(copyme, newmodel);

        coins_remaining = copyme.coins_remaining;


        //Draw CoinBlock
        if(coinblockpic == null) {
            coinblockpic = new Image[2];
            try {
                coinblockpic[0] = ImageIO.read(new File("block1.png"));
                coinblockpic[1] = ImageIO.read(new File("block2.png"));
            } catch (Exception e) {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }
    }

    CoinBlock cloneme(Model newmodel)
    {
        return new CoinBlock(this, newmodel);
    }

    boolean isBrick() {
        return false;
    }

    boolean isMario() {
        return false;
    }

    boolean isCoinBlock() {
        return true;
    }

    boolean isCoin(){return false;}

    void pop_coin(Model m)
    {
        if(coins_remaining >= 1) {
            double x_vel = rand.nextDouble() * 32 - 16;
            double y_vel = -25.00;

            Coin c = new Coin(x, y - h, w, h, x_vel, y_vel, m);
            m.sprites.add(c);

            coins_remaining--;
            m.coin_score++;
        }
    }

    void draw(Graphics g, Model m)
    {

        if(coins_remaining >= 1)
        {
            CoinBlockStates = 0;
        }else{
            CoinBlockStates = 1;
        }
        //g.drawRect(x - m.scrollPos, y, w, h);
        int CoinBlockState = CoinBlockStates;
        g.drawImage(coinblockpic[CoinBlockState], x - m.scrollPos, y, w, h, null);
    }

    boolean update(Model m)
    {
        return true;
    }


    //Unmarshal
    CoinBlock(Json ob)
    {

        //Draw CoinBlock
        if(coinblockpic == null) {
            coinblockpic = new Image[2];
            try {
                coinblockpic[0] = ImageIO.read(new File("block1.png"));
                coinblockpic[1] = ImageIO.read(new File("block2.png"));
            } catch (Exception e) {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }


        ob.getString("type");
        coins_remaining = (int)ob.getLong("coins_remaining");
        CoinBlockStates = (int)ob.getLong("CoinBlockStates");
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        w = (int)ob.getLong("w");
        h = (int)ob.getLong("h");
    }

    //Marshal
    Json marshall()
    {
        Json ob = Json.newObject();
        ob.add("type", "CoinBlock");
        ob.add("coins_remaining", coins_remaining);
        ob.add("CoinBlockStates", CoinBlockStates);
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        return ob;
    }
}
