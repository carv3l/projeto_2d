package moveshape;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Heart implements Shape {

	GeneralPath path;
	
	public Heart(float x, float y, float w, float h) { // w = width, h = height ( x,y ) = starting position
		path = new GeneralPath();
		
		float x0 = x + 0.5f*w;
	    float y0 = y + 0.3f*h;
	    float x1 = x + 0.1f*w;
	    float y1 = y + 0f * h;
	    float x2 = x + 0f * w;
	    float y2 = y + 0.6f * h;
	    float x3 = x + 0.5f * w;
	    float y3 = y + 0.9f * h;
	    float x4 = x + 1f * w;
	    float y4 = y + 0.6f * h;
	    float x5 = x + 0.9f * w;
	    float y5 = y + 0f * h;
		
		path.moveTo(x0, y0);
		path.curveTo(x1, y1, x2, y2, x3, y3);
		path.curveTo(x4, y4, 52, y5, x0, y0);
		
		
		
	}
	
	
	@Override
	public boolean contains(Point2D arg0) {
	
		return path.contains(arg0);
	}

	@Override
	public boolean contains(Rectangle2D arg0) {
		return path.contains(arg0);
	}

	@Override
	public boolean contains(double arg0, double arg1) {
		return path.contains(arg0,arg1);
	}

	@Override
	public boolean contains(double arg0, double arg1, double arg2, double arg3) {
		return path.contains(arg0, arg1, arg2, arg3);
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return path.getBounds();
	}

	@Override
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return path.getBounds2D();
	}

	@Override
	public PathIterator getPathIterator(AffineTransform arg0) {
		// TODO Auto-generated method stub
		return path.getPathIterator(arg0);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform arg0, double arg1) {
		// TODO Auto-generated method stub
		return path.getPathIterator(arg0,arg1);
	}

	@Override
	public boolean intersects(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return path.intersects(arg0);
	}

	@Override
	public boolean intersects(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		return path.intersects(arg0,arg1,arg2,arg3);
	}

}