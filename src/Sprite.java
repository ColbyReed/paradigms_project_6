import java.awt.Graphics;

abstract class Sprite
{
    Model model;

    int x;
    int y;
    int w;
    int h;
    double x_vel;
    double y_vel;

    Sprite()
    {

    }

    Sprite(Sprite copyme, Model newmodel) //Copy constructor
    {
        x = copyme.x;
        y = copyme.y;
        w = copyme.w;
        h = copyme.h;
        x_vel = copyme.x_vel;
        y_vel = copyme.y_vel;

        model = newmodel;
    }

    abstract Sprite cloneme(Model newmodel);

    abstract boolean isMario();
    abstract boolean isBrick();
    abstract boolean isCoinBlock();
    abstract boolean isCoin();

    //abstract boolean doesCollide();

    abstract void draw(Graphics g, Model m);
    abstract boolean update(Model m);
    abstract Json marshall();
}