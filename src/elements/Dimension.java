package elements;

public class Dimension {
	public float w;
	public float l;
	public float h;
	/**
	 * creates a dimension (0,0,0);
	 *
	 */
	public Dimension(){
		this.w=0;
		this.l=0;
		this.h=0;
	}
	
	public Dimension(float w, float l, float h){
		this.w = w;
		this.l = l;
		this.h = h;
	}
	
}