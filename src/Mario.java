import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.Iterator;
import java.awt.Graphics;
import java.util.ArrayList;

class Mario extends Sprite
{
	boolean onBrick;

	static Image[] mario_images = null;

	//Position of Mario
	int prev_x;
	int prev_y;
	
	//int x;
	//int y;
	int w = 60;
	int h = 95;
	//double y_vel; //Vertical Velocity
	
	Model model;
	CoinBlock coinblock;
	
	int air_time; //Time not touching ground
//	int landed_count;
	

	Mario(Model m)
	{
		super();
		onBrick = false;
		model = m;
		air_time = 0;


		//Draw Mario
		if(mario_images == null) {
			mario_images = new Image[5];
			try {
				mario_images[0] = ImageIO.read(new File("mario1.png"));
				mario_images[1] = ImageIO.read(new File("mario2.png"));
				mario_images[2] = ImageIO.read(new File("mario3.png"));
				mario_images[3] = ImageIO.read(new File("mario4.png"));
				mario_images[4] = ImageIO.read(new File("mario5.png"));
			} catch (Exception e) {
				e.printStackTrace(System.err);
				System.exit(1);
			}
		}
	}

	Mario(Mario copyme, Model newmodel) //Copy constructor
	{
		super(copyme, newmodel);
		onBrick = copyme.onBrick;
		air_time = copyme.air_time;

		prev_y = copyme.prev_y;
		prev_x = copyme.prev_x;

//		landed_count = copyme.landed_count;

		//Draw Mario
		if(mario_images == null) {
			mario_images = new Image[5];
			try {
				mario_images[0] = ImageIO.read(new File("mario1.png"));
				mario_images[1] = ImageIO.read(new File("mario2.png"));
				mario_images[2] = ImageIO.read(new File("mario3.png"));
				mario_images[3] = ImageIO.read(new File("mario4.png"));
				mario_images[4] = ImageIO.read(new File("mario5.png"));
			} catch (Exception e) {
				e.printStackTrace(System.err);
				System.exit(1);
			}
		}
	}

	Mario cloneme(Model newmodel)
	{
		return new Mario(this, newmodel);
	}

	boolean isMario() {
		return true;
	}


	boolean isBrick() {
		return false;
	}


	boolean isCoinBlock() {
		return false;
	}

	boolean isCoin(){return false;}

	void previous_location()
	{
		prev_x = x;
		prev_y = y;
	}
	
	boolean doesCollide(int ox, int oy, int ow, int oh)
	{
		if(x + w <= ox)
			return false;
		else if(x >= ox + ow)
			return false;
		else if(y + h <= oy)
			return false;
		else if(y >= oy + oh)
			return false;
		else
			return true;
	}
	
	void get_out(Sprite s)
	{
		if((x + w) >= s.x && (prev_x + w < s.x)) // Collision from left
		{
			x = s.x - w - 1;
		}
		else if(x <= (s.x + s.w) && prev_x > (s.x + s.w)) // Collision from right
		{
			x = s.x + s.w + 1;
		}
		else if(y + h >= s.y && prev_y + h < s.y) //Collision from top
		{
			y = s.y - h - 1;
			y_vel = 0;
			onBrick = true;
		}
		else if(y <= s.y + s.h && prev_y > s.y + s.h) //Collision from bottom
		{
			y_vel = 0;
			y = s.y + s.h + 1;
			y_vel += 5;
			//landed_count++;
		}
		//Else statement to fix when Mario sprite gets stuck
//		else{
//			y = s.y - h - 1;
//			y_vel = 0;
//			onBrick = true;
//		}

	}

//	boolean hit_ground(int landed_count){
//		if(landed_count % 2 == 1)
//			return true;
//		else return false;
//	}

	void draw(Graphics g, Model m)
	{
		int marioFrame = (Math.abs(x) / 20 % 5); //Determines drawing of frames to x position
		g.drawImage(mario_images[marioFrame], x - m.scrollPos, y, null);
	}

	boolean update(Model m)
	{
		m.scrollPos = x - 250;

		y_vel += 2.8675309;
		y += y_vel;
		
		//Stop velocity when Mario hits ground.
		if(y >= 550)
		{
			y = 550;
			y_vel = 0;
			air_time = 0;
		}
		
		//Collision detection (Iteration)
//		Iterator<Sprite> it = model.sprites.iterator();
//		while(it.hasNext())
//		{
//			Sprite s = it.next();
//			if(s.isBrick()) {
//				if (doesCollide(s.x, s.y, s.w, s.h)) {
//					//System.out.println("Previous Location was: + Integer.toString(prev_x) ");
//					//System.out.println("Current Location is: + Integer.toString(x)");
//					get_out(s);
//					if (doesCollide(s.x, s.y, s.w, s.h))
//						System.out.println("Collision detected!");
//				} else
//					System.out.println("");
//			}
//
//			if(s.isCoinBlock())
//			{
//				CoinBlock coinblock = (CoinBlock)s;
//				coinblock.pop_coin(model);
//			}
//		}
		
		for(int i = 0; i < m.sprites.size(); i++) {
			Sprite s = m.sprites.get(i);

			if(s.isCoinBlock() && doesCollide(s.x, s.y, s.w, s.h) && y <= s.y + s.h && prev_y > s.y + s.h)
			{
				CoinBlock coinblock = (CoinBlock)s;
				coinblock.pop_coin(m);
			}

			if ((s.isBrick() || s.isCoinBlock()) && doesCollide(s.x, s.y, s.w, s.h)) {
				get_out(s);
				if (doesCollide(s.x, s.y, s.w, s.h))
					System.out.println("Collision detected!");
			}else {
				System.out.println("");
			}
		}
		
		air_time++;
		return true;
	}

	//Unmarshal
	Mario(Json ob) {

		ob.getString("type");
		x = (int) ob.getLong("x");
		y = (int) ob.getLong("y");
		w = (int) ob.getLong("w");
		h = (int) ob.getLong("h");
		y_vel = (int) ob.getDouble("y_vel");
	}

	//Marshal
	Json marshall() {
		Json ob = Json.newObject();
		ob.add("type", "Mario");
		ob.add("x", x);
		ob.add("y", y);
		ob.add("w", w);
		ob.add("h", h);
		ob.add("y_vel", y_vel);
		return ob;
	}
}
