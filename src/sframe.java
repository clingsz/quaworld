

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class sframe extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean fullScreen=false;
	private boolean initial=true;
	private boolean fps=true;
	static long GAMESPEED = 10L;
	private String titleName = "Qua World";

	private int mouseX,mouseY;
	
	// 2 for pause;
	// 1 for normal;
	// 0 for lose;
	// 3 for win!;
	public int state = 2;

	public int sizeX = 800;
	public int sizeY = 800;
	
	private int width = 10*sizeX;
	private int height = 10*sizeY+30;

	int dir = 1;
	
	long TheTime = 0L;

	public world w = new world();

	//Double Buffer
	//    Image bufferImage;
	//    Graphics offgx;

	Image bufferImage;
	Graphics offgx;


	//initiate ToriWar
	public sframe(){


		//When mouse moved, check the mouse position 
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				int mx=e.getX();
				int my=e.getY();
				mouseX= mx;
				mouseY=my;
			}
		});

		//When mouse clicked, do sth.
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int mx=e.getX();
				int my=e.getY();
				mouseX= mx;
				mouseY= my;
				//world.handleClick(mousX,mouseY);
			}
		});


		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				titleName = "Typed:"+e.getKeyChar();
				char c = e.getKeyChar();
				System.out.println("Pressed "+c);
				switch (c){
				case 'w': dir = 1; break;
				case 's': dir = 2; break;
				case 'a': dir = 3; break;       		
				case 'd': dir = 4; break;
				case 'p': state = 1; break;
				case 'u': if (GAMESPEED==10L) GAMESPEED=GAMESPEED/10;
				else GAMESPEED=GAMESPEED*10;
					dir = 0;
					break;
				case 'r': init(); break;
				}

			}        	
		});
	}
	//End of tankwar initiation

	// Show the whole screen, make it fullscreen;
	public void init(){
		int scale = 20;
		width = scale*sizeX;
		height = scale*sizeY+30;

		w.init(sizeX,sizeY,scale);
		state = 2;
	}

	public void showFrame() {



		if (fullScreen){
			GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);
			this.setBackground(Color.BLACK);
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			//this.setUndecorated(true);  
			this.setVisible(true);

			//Dimension screenSize = new Dimension(800,600);
			Dimension   screenSize   =   Toolkit.getDefaultToolkit().getScreenSize();
			this.setBounds(0,0,screenSize.width,screenSize.height);

			//new Thread(new RepaintThread()).start();
			repaint();
		}
		else{
			//this.show();
			//Dimension   screenSize   =   Toolkit.getDefaultToolkit().getScreenSize(); 
			this.setBounds(0,0,width,height);
			Dimension screenSize = new Dimension(width,height);

			//this.setExtendedState(JFrame.MAXIMIZED_BOTH);

			this.setBackground(Color.WHITE);
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);


			this.setResizable(false);//					 
			this.setTitle(titleName);
			this.setLocation(100,100);
			this.setVisible(true);

			repaint();

		}

		Thread game = new Thread(new RepaintThread());
		game.start();

	}

	//Show paint thing
	//Double buffer implement, firstly draw the things on offgx, which is the graphics of bufferImage, then draw bufferimage on to screen

	public void drawBuffer(){
		super.paint(offgx);
		if (initial) {
			initial=false;
		}
		w.draw(offgx, this);
	}

	public void draw(Graphics g, JFrame f){

	}

	public void paint(Graphics g) {
		try	{
			if (bufferImage!=null){
				drawBuffer();
				g.drawImage(bufferImage,0,0,this);
			}
			else
			{			
				Dimension d = getSize();
				bufferImage = createImage(d.width,d.height);
				offgx = bufferImage.getGraphics();
				drawBuffer();
			}
		}
		catch(Exception e)	{
			e.printStackTrace();
		} 
	}


	//Repaint thread
	private class RepaintThread implements Runnable	{
		public void run()	{
			long second = 0L;
			long second2 = 0L;
			long second3 = 0L;
			while(true)	{
				long start = System.currentTimeMillis();

				long end = System.currentTimeMillis();
				long time = end - start;
				long sleepTime = TheTime - time;
				if (sleepTime < 0L)
					sleepTime = 0L;
				try   {
					Thread.sleep(sleepTime);
				}
				catch(Exception e)   {
					e.printStackTrace();
				}
				//Time counter, different from refresh paint;
				if (true) {
					second += System.currentTimeMillis() - start;
					//do per second
					if (second >= 1000L) {
//						if (fps) resetTitle(moveCount);
						second = 0L;
					}
					//do per 0.1second
					second2 += System.currentTimeMillis() - start;
					if (second2>=GAMESPEED){
						repaint();

						resetTitle();
						second2 = 0L;
					}
					second3 += System.currentTimeMillis() - start;
					if (second3>=500L){
						//for (int i=1;i<=tankNum;i++) t[i].go();
						//moveFrame(mouseX,mouseY);
						second3 = 0L;
					}
				}
			}
		}

	}

	public void resetTitle(){
				String s = "";
				switch (state){
				case 2: s="Pause, Press P to start"; break;
				case 1: s="U to speed up/down"; break;
				case 0: s="LOSE YOU SHIT!!"; break;
				case 3: s="FUCK you win!!!"; break;
				}
				titleName = "(R to restart)";
				this.setTitle(new StringBuilder(titleName).append(s).toString());
	}


	public static void main(String[]args)	{

		for (int i = 0; i<args.length; i++) System.out.println(args[i]);
		sframe tk=new sframe();
		Container c=tk.getContentPane(); 
		c.setBackground(Color.gray);
		tk.init();
		tk.showFrame();

	}
}





















