import java.util.ArrayList;



class Model
{
    int scrollPos;
    Mario mario;
    ArrayList<Sprite> sprites;

    int d = 14;
    int k = 3;

    int jumpCount = 0;
    int coin_score = 0;

	Model()
	{

		mario = new Mario(this);
        sprites = new ArrayList<Sprite>();
        sprites.add(mario);
        load("map.json");
	}

    Model(Model other) //Copy Constructor
    {
        sprites = new ArrayList<Sprite>();
        for(int i = 0; i < other.sprites.size(); i++)
        {
            Sprite othersprite = other.sprites.get(i);
            Sprite clone = othersprite.cloneme(this);
            sprites.add(clone);
            if(clone.isMario()) {
                mario = (Mario)clone;
            }
        }
        scrollPos = other.scrollPos;
        jumpCount = other.jumpCount;
        coin_score = other.coin_score;
    }

    enum Action
    {
        run,
        jump,
        jump_and_run,
        back,
    }

    void do_action(Action a) //Set of actions possible for AI
    {
        if(a == Action.run) {
            mario.x += 10;
        }
        else if(a == Action.jump) {
            if(mario.onBrick == true)
			{
				mario.y_vel = -30;
                jumpCount++;
				mario.onBrick = false;
				mario.air_time = 0;
			}
			if(mario.air_time < 5){
				mario.y_vel = -30;
                jumpCount++;
			}
        }
        else if(a == Action.back){
            mario.x -=  5;
        }
        else if(a == Action.jump_and_run)
        {
            if(mario.onBrick == true)
            {
                mario.x += 10;
                mario.y_vel = -30;
                jumpCount++;
                mario.onBrick = false;
                mario.air_time = 0;
            }
            if(mario.air_time < 5){
                mario.x += 10;
                mario.y_vel = -30;
                jumpCount++;
            }
        }
    }

    double evaluateAction(Action action, int depth)
    {
        // Evaluate the state
        if(depth >= d)
            return scrollPos + 5000 * coin_score - 2 * jumpCount;

        // Simulate the action
        Model copy = new Model(this); // uses the copy constructor
        copy.do_action(action); // like what Controller.update did before
        copy.update(this); // advance simulated time

        // Recurse
        if(depth % k != 0)
            return copy.evaluateAction(action, depth + 1);
        else
        {
            double best = copy.evaluateAction(Action.run, depth + 1);
            best = Math.max(best, copy.evaluateAction(Action.jump, depth + 1));
            best = Math.max(best, copy.evaluateAction(Action.back, depth +1));
            //best = Math.max(best, copy.evaluateAction(Action.jump_and_run, depth + 1));
            return best;
        }
    }

	void previous_location()
	{
		mario.previous_location();
	}


	public void update(Model m)
	{

		for(int i = 0; i < sprites.size(); i++)
		{
           Sprite s = sprites.get(i);
            boolean alive = s.update(m);
            if(!alive) {
                sprites.remove(i);
                i--;
            }
		}
	}

    void addBrick(int x, int y, int w, int h)
    {
        Sprite s = new Brick(x, y, w, h);
        sprites.add(s);
    }

    void addCoinBlock(int x, int y, int w, int h)
    {
        Sprite s = new CoinBlock(x, y, w, h);
        sprites.add(s);
    }
    
    
    void unmarshall(Json ob)
    {
        sprites.clear();
        Json json_sprites = ob.get("sprites");
        for(int i = 0; i < json_sprites.size(); i++)
        {
            Json j = json_sprites.get(i);
            String str = j.getString("type");
            Sprite s = null;
            if(str.equals("Mario")){
                s = new Mario(j);
                mario = (Mario)s;
            }else if(str.equals("Brick")){
                s = new Brick(j);
            }else if(str.equals("CoinBlock")){
                s = new CoinBlock(j);
            }else if(str.equals("Coin")){
                s = new Coin(j);
            }
            sprites.add(s);
        }
    }
    
    Json marshall()
    {
        Json ob = Json.newObject();
        Json json_sprites = Json.newList();
        ob.add("sprites", json_sprites);
        for(int i = 0; i < sprites.size(); i++)
        {
            Sprite s = sprites.get(i);
            Json j = s.marshall();
            json_sprites.add(j);
        }
        return ob;
    }
    
    void save(String filename)
    {
        Json ob = marshall();
        ob.save(filename);
    }
    
	void load(String filename)
	{
        Json ob = Json.load(filename);
        unmarshall(ob);
	}
    
    
}
