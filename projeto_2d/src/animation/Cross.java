package animation ;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;



public class Cross implements Shape {

	GeneralPath cross_path;
		public Cross(float x, float y, float size,float sqsize) { // w = width, h = height ( x,y ) = starting position
		cross_path = new GeneralPath();
	
		float ax = x;
		float ay = y;
		float a2x = ax;
		float a2y= ay+size;
		float a3x = ax-size;
		float a3y = a2y;
		
		float bx = x+sqsize;
		float by = y;
		float b2x= bx;
		float b2y= a2y;
		float b3x= bx+size;
		float b3y= b2y;
		
		float cx = b3x;
		float cy = b3y+sqsize;
		float c2x= b3x-size;
		float c2y= cy;
		float c3x= c2x;
		float c3y= c2y+size;
		
		float dx = c3x-sqsize;
		float dy = c3y;
		float d2x= dx;
		float d2y= dy-size;
		float d3x= d2x-size;
		float d3y= d2y;
				
		cross_path.moveTo(ax, ay);
		cross_path.lineTo(a2x, a2y);
		cross_path.moveTo(a2x, a2y);
		cross_path.lineTo(a3x, a3y);
		
		cross_path.moveTo(bx, by);
		cross_path.lineTo(b2x, b2y);
		cross_path.moveTo(b2x, b2y);
		cross_path.lineTo(b3x, b3y);
		
		
		cross_path.moveTo(cx, cy);
		cross_path.lineTo(c2x, c2y);
		cross_path.moveTo(c2x, c2y);
		cross_path.lineTo(c3x, c3y);
		
		cross_path.moveTo(dx, dy);
		cross_path.lineTo(d2x, d2y);
		cross_path.moveTo(d2x, d2y);
		cross_path.lineTo(d3x, d3y);

	}
	
	

	
	@Override
	public boolean contains(Point2D arg0) {
	
		return cross_path.contains(arg0);
	}

	@Override
	public boolean contains(Rectangle2D arg0) {
		return cross_path.contains(arg0);
	}

	@Override
	public boolean contains(double arg0, double arg1) {
		return cross_path.contains(arg0,arg1);
	}

	@Override
	public boolean contains(double arg0, double arg1, double arg2, double arg3) {
		return cross_path.contains(arg0, arg1, arg2, arg3);
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return cross_path.getBounds();
	}

	@Override
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return cross_path.getBounds2D();
	}

	@Override
	public PathIterator getPathIterator(AffineTransform arg0) {
		// TODO Auto-generated method stub
		return cross_path.getPathIterator(arg0);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform arg0, double arg1) {
		// TODO Auto-generated method stub
		return cross_path.getPathIterator(arg0,arg1);
	}

	@Override
	public boolean intersects(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return cross_path.intersects(arg0);
	}

	@Override
	public boolean intersects(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		return cross_path.intersects(arg0,arg1,arg2,arg3);
	}

}
