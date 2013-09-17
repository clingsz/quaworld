import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

import javax.swing.JFrame;
public class world {

	public int m,n;
	public int scale = 20;
	public int TOP = 30;
	public long curx = 0;
	public long cury = 0;
	
	MapContainer mc = new MapContainer(10);

	public world(){

	}

	// type 0 void, 1 snake, 2 point
	public int[] sx;
	public int[] sy;
	public int ls;

	public int npx,npy;

	int map[][];

	public void init(int nm, int nn,int ns){
		scale = ns;
		m = nm;
		n = nn;
		sx = new int[m*n+1];
		sy = new int[m*n+1];
		map = new int[m][n];
		
		Long range[] = new Long[5];
		range[1] = curx;
		range[2] = curx + m;
		range[3] = cury;
		range[4] = cury + n;
		
		map = mc.retrieve(range);
		
		
	}

	int dx[] = {0,0,-1,1};
	int dy[] = {-1,1,0,0};


	public void genPoint(){
		int nx,ny;

		nx = (int)(Math.random()*m);
		ny = (int)(Math.random()*n);
		while(!(isValid(nx,ny)&&map[nx][ny]==0)){
			nx = (int)(Math.random()*m);
			ny = (int)(Math.random()*n);
		}			
		npx = nx;
		npy = ny;
		map[npx][npy]=2;
	}


//	public int move(int dir){
//		boolean useAI = true;
//		int nx,ny;
//		if (useAI){
//			dir =sol[sx[0]][sy[0]]; 
//		}
//		nx = sx[0]+dx[dir];
//		ny = sy[0]+dy[dir];		
//
//		if (isValid(nx,ny)){
//			int ans = refreshMap(nx,ny);
//			return ans;
//		}
//
//
//		return 0;

//	}

	Color color[] = {Color.black,Color.white,Color.yellow};
	public void draw(Graphics g, JFrame f){
		for (int i = 0; i<m;i++) for(int j=0;j<n;j++)
		{			
			g.setColor(color[map[i][j]]);
			g.fillRect(i*scale, j*scale+TOP, scale, scale);
			g.setColor(Color.black);
			//			g.drawRoundRect(x, y, width, height, arcWidth, arcHeight)
			g.drawRoundRect(i*scale, j*scale+TOP, scale, scale,scale,scale);
		}
	}

	public boolean isValid(int x, int y){
		if (x>=0&&x<m&&y>=0&&y<n){				
			return true;
		}
		return false;
	}
	public static void main(String args[])
	{
		world w = new world();
	}

}
