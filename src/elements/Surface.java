package elements;

import java.awt.Color;
import processing.core.*;

public class Surface {
	PVector[] verts; // atleast three
	PVector[] origVerts;
	PVector norm;
	PVector centroid;
	int pushed = 0;
	public Color col = Color.GRAY;
	public boolean isActive = true;
	public boolean isHit = false;
	public boolean reversed = false;
	int timer;
	
	public Surface(PVector[] v) {
		verts = v;
		origVerts = verts;
		norm = calculateNormal();
		centroid = calculateCentroid();
	}

	public void hit()
	{
		isHit = true;
		timer = 255;
	}
	
	public void update()
	{
		if(timer < 1) isHit = false;
		else
			timer--;
	}
	
//	public void pushSurf(float push) {
//		if (isActive) {
//			pushed++;
//			for (PVector v : verts) {
//				v.sub(PVector.mult(norm, push));
//			}
//		}
//	}
	
	

	public void draw(PApplet p){
		draw(p,col,120);
	}
	
	public void draw(PApplet p, Color c, int alpha) {
		p.pushStyle();

		p.noStroke();
		if (isHit)
			p.fill(timer/1.0f, 0, 0, timer*timer/255*timer/255);
		else if ((pushed > 0) && !reversed)
			p.fill(pushed * 10, 0, 0, 100);
		else
			p.fill(c.getRGB(),alpha);
		// draw edges
		p.beginShape();
		p.stroke(255); //if commented then no visible shape drawn
		p.strokeWeight(0.5f);
		for (PVector v : verts) {
			p.vertex(v.x, v.y, v.z);
		}
		p.endShape();

		// draw normal
		p.pushMatrix();
		p.translate(centroid.x, centroid.y, centroid.z);
		p.beginShape(PConstants.LINES);
		p.vertex(0, 0, 0);
		// PVector normHead = PVector.add(centroid, norm);
		p.vertex(norm.x, norm.y, norm.z);
		p.endShape();
		p.popMatrix();
		p.popStyle();
	}

	private PVector calculateNormal() {
		PVector n = new PVector();
		PVector edge0 = PVector.sub(verts[1], verts[0]);
		PVector edge1 = PVector.sub(verts[verts.length - 1], verts[0]);
		PVector.cross(edge0, edge1, n);
		n.normalize();
		return n;
	}

	private PVector calculateCentroid() {
		PVector sumOfAllVerts = new PVector(0f, 0f, 0f);
		for (PVector v : verts) {
			sumOfAllVerts.add(v);
		}
		sumOfAllVerts.mult(1.0f / verts.length);
		// System.out.println(sumOfAllVerts.toString());
		return sumOfAllVerts;
	}

	public boolean isFacing(PVector v) {
		if (norm.dot(v) < 0)
			return true;
		else
			return false;
	}

	public void reverseNormal() {
		int n = verts.length;
		PVector[] reversedList = new PVector[n];
		for (int i = 0; i < n; i++) {
			reversedList[i] = verts[n - i - 1];
		}

		verts = reversedList;
		origVerts = verts;
		norm = calculateNormal();
		centroid = calculateCentroid();
		reversed = true;
	}
}
