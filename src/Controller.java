import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements ActionListener, MouseListener, KeyListener
{
	View view;
	Model model;
	
	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;
	boolean keySpace;
	int mouseDownX;
	int mouseDownY;
	
	
	void setView(View v)
	{
			view = v;
	}
	
	Controller(Model m)
	{
		model = m;
	}
	
	
	Controller()
	{
	}

	public void actionPerformed(ActionEvent e)
	{
		//view.removeButton();
	}
	
	public void mousePressed(MouseEvent e)
	{
		//model.setDestination(e.getX(), e.getY());
		mouseDownX = e.getX();
		mouseDownY = e.getY();

	}


	public void mouseReleased(MouseEvent e) {    
        int x1 = mouseDownX;
        int x2 = e.getX();
        int y1 = mouseDownY;
        int y2 = e.getY();
        int left = Math.min(x1, x2);
        int right = Math.max(x1, x2);
        int top = Math.min(y1, y2);
        int bottom = Math.max(y1, y2);

        if(e.getButton() == MouseEvent.BUTTON1) {
			model.addBrick(left + model.scrollPos, top, right - left, bottom - top);
		}else if(e.getButton() == MouseEvent.BUTTON3) {
			model.addCoinBlock(left + model.scrollPos, top, right - left, bottom - top);
		}
        //model.addBrick(mouseDownX, mouseDownY, e.getX() - mouseDownX, e.getY() - mouseDownY);
	}
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	
	public void mouseClicked(MouseEvent e) {  
		if(e.getY() < 100)
		{
			//Breakpoint
			System.out.println("break here");
		}
	}
	
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = true; break;
			case KeyEvent.VK_LEFT: keyLeft = true; break;
			case KeyEvent.VK_UP: keyUp = true; break;
			case KeyEvent.VK_DOWN: keyDown = true; break;
			case KeyEvent.VK_SPACE: keySpace = true; break;
			case KeyEvent.VK_S: model.save("map.json"); break;
			case KeyEvent.VK_L: model.load("map.json"); break;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			case KeyEvent.VK_UP: keyUp = false; break;
			case KeyEvent.VK_DOWN: keyDown = false; break;
			case KeyEvent.VK_SPACE: keySpace = false; break;
		}
	}

	public void keyTyped(KeyEvent e)
	{
	}

//	void update(Model m)
////	{
////		m.previous_location();
////
////		if(keyRight) {
////			m.mario.x += 10;
////		}
////		if(keyLeft) {
////			m.mario.x -= 10;
////		}
////		if(keySpace)
////		{
////			if(m.mario.onBrick == true)
////			{
////				m.mario.y_vel = -30;
////				m.mario.onBrick = false;
////				m.mario.air_time = 0;
////			}
////			if(m.mario.air_time < 5)
////				m.mario.y_vel = -30;
////		}
////	}

	void update(Model m)
	{
		m.previous_location();

		// Evaluate each possible action
		double score_run = m.evaluateAction(Model.Action.run, 0);
		double score_jump = m.evaluateAction(Model.Action.jump, 0);
		double score_back = m.evaluateAction(Model.Action.back, 0);
//		double score_jump_and_run = m.evaluateAction(Model.Action.jump_and_run, 0);

		// Do the best one
//		if(score_jump_and_run > score_jump && score_jump_and_run > score_run)
//		{
//			m.do_action(Model.Action.jump_and_run);
//		}
		if(score_jump > score_run && score_jump > score_back) {
			m.do_action(Model.Action.jump);
		}
		else if(score_back > score_run){
			m.do_action(Model.Action.back);
		}
		else
			m.do_action(Model.Action.run);
	}






}
