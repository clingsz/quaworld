public class MapContainer {

	public Octree root;
	public MapContainer(int level){
		root = new Octree(level,0L,0L);
	}

	public static void main(String[]args)	{
		MapContainer m = new MapContainer(5);
		System.println
	}
}

class Octree {

	public int value;
	public int level;
	public boolean spanned = false;
	public Octree[] subTree;
	public Long[] range = new Long[5];
	public Long midx;
	public Long midy;


	public Octree(int nlevel, Long sx, Long sy){
		if (nlevel<0)
			System.err.println("Level is too low: "+nlevel);
		level = nlevel;
		range[1] = sx;
		range[2] = sx+(1<<level);
		range[3] = sy;
		range[4] = sy+(1<<level);
		midx = (range[2]+range[1])/2;
		midy = (range[3]+range[4])/2;

	}

	public int locate(Long x, Long y){
		if (x<range[1] || x>range[2] || y<range[3] || y>range[4]){
			return -1;
		}
		if (level==0)
			return 0;

		int state = 1;
		if (x>midx) state = state + 1;
		if (y>midy) state = state + 2;

		return state;
	}




	public void setValue(Long x, Long y, int newValue){
		if (!spanned){
			if (level==0)
				spanned = true;
			else{
				spanned = true;
				Long[] lx = {0L,range[1],midx+1,range[1],midx+1};
				Long[] ly = {0L,range[3],range[3],midy+1,midy+1};
				for (int i = 1;i<=4;i++)
					subTree[i] = new Octree(level-1,lx[i],ly[i]);
			}	
		}

		int pos = locate(x,y);
		switch(pos){
		case -1: System.err.println("find the wrong place!"); break;
		case 0: value = newValue; break;
		default: subTree[pos].setValue(x, y, newValue);
		}
		return;
	}

	public int getValue(Long x, Long y){
		if (!spanned){
			if (level==0){
				spanned = true;
				value = randGen();
			}
			else{
				spanned = true;
				Long[] lx = {0L,range[1],midx+1,range[1],midx+1};
				Long[] ly = {0L,range[3],range[3],midy+1,midy+1};
				for (int i = 1;i<=4;i++)
					subTree[i] = new Octree(level-1,lx[i],ly[i]);
			}	
		}

		int pos = locate(x,y);
		switch(pos){
		case -1: System.err.println("find the wrong place!"); return -1;
		case 0: return value; 
		default: return subTree[pos].getValue(x, y);
		}

	}

	
	public void traverse(){
		String s = "";
		
		
	}

	public int randGen(){
		return 1;
	}
}