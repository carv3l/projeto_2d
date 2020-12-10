package animation ;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;



public class TriangleArea implements Shape {

	GeneralPath triangle_path;
		public TriangleArea(float x, float y, float size,float shapesize) { // w = width, h = height ( x,y ) = starting position
		triangle_path = new GeneralPath();
	
		
		float ax = x-shapesize;
		float ay = y+(size/2);
		float bx = ax+(size/2)-shapesize;
		float by = y;
		float cx =ax-shapesize+(size-shapesize);
		float cy = ay;
		float dx= cx+(size/2)-shapesize;
		float dy= by;
		float ex= dx +(size/2)-shapesize;
		float ey =cy;
		float fx= dx;
		float fy= ay+(size/2);
		float gx = bx;
		float gy= fy;
		
		triangle_path.moveTo(ax, ay);
		triangle_path.lineTo(bx, by);
		triangle_path.moveTo(bx, by);
		triangle_path.lineTo(cx, cy);
		triangle_path.moveTo(cx, cy);
		triangle_path.lineTo(dx, dy);
		triangle_path.moveTo(dx, dy);
		triangle_path.lineTo(ex, ey);
		triangle_path.moveTo(ex, ey);
	    triangle_path.lineTo(fx, fy);
	    triangle_path.moveTo(fx, fy);
	    triangle_path.lineTo(cx, cy);
		triangle_path.moveTo(cx, cy);
		triangle_path.lineTo(gx, gy);
		triangle_path.moveTo(gx, gy);
		triangle_path.lineTo(ax, ay);


	}
	
	

	
	@Override
	public boolean contains(Point2D arg0) {
	
		return triangle_path.contains(arg0);
	}

	@Override
	public boolean contains(Rectangle2D arg0) {
		return triangle_path.contains(arg0);
	}

	@Override
	public boolean contains(double arg0, double arg1) {
		return triangle_path.contains(arg0,arg1);
	}

	@Override
	public boolean contains(double arg0, double arg1, double arg2, double arg3) {
		return triangle_path.contains(arg0, arg1, arg2, arg3);
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return triangle_path.getBounds();
	}

	@Override
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return triangle_path.getBounds2D();
	}

	@Override
	public PathIterator getPathIterator(AffineTransform arg0) {
		// TODO Auto-generated method stub
		return triangle_path.getPathIterator(arg0);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform arg0, double arg1) {
		// TODO Auto-generated method stub
		return triangle_path.getPathIterator(arg0,arg1);
	}

	@Override
	public boolean intersects(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return triangle_path.intersects(arg0);
	}

	@Override
	public boolean intersects(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		return triangle_path.intersects(arg0,arg1,arg2,arg3);
	}

}
