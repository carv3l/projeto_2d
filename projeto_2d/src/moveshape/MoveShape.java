package moveshape;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;
import javax.swing.JPanel;



public class MoveShape extends JFrame {

	public static void main(String[] args)  {

		JFrame frame = new MoveShape();
		frame.setTitle("Dataplot");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new MyJPanel();
		
		frame.getContentPane().add(panel);
		
		frame.pack();
		frame.setVisible(true);

	}

}
class MyJPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener{
	
	Shape s1 = new Heart(200, 10, 50, 50);
	Shape s2 ;
	boolean selected;
	int actualX, actualY;
	int deltaX, deltaY;
	boolean colision ;
	
	AffineTransform at = new AffineTransform();
	
	public MyJPanel () {
		
		setPreferredSize(new Dimension(400,400));
		
		this.setFocusable(true);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		
	}
	
public void paintComponent(Graphics g) {
	
	super.paintComponent(g);
	
	Graphics2D g2 = (Graphics2D) g;
	
	s2 = new Ellipse2D.Float(getWidth()/2 -25, getHeight()/2 - 25, 50,50 );
	
	g2.setColor(Color.GREEN);
	g2.fill(s1);
	g2.setColor(Color.BLUE);
	g2.fill(s2);
	
	
	if(colision)
		g2.setColor(Color.RED);
	else
		g2.setColor(Color.BLUE);
	g2.fill(s2);
	
	if(colision)
		g2.setColor(Color.RED);
	else
		g2.setColor(Color.GREEN);
	g2.fill(s1);
	
}

public void mousePressed(MouseEvent e) {
	if (s1.contains(e.getX(), e.getY())) {
		selected = true;
		actualX = e.getX();
		actualY = e.getY();
		
		
		
	}
	
	
	
}
public void mouseDragged(MouseEvent e) {
	if (selected) {
		deltaX = e.getX() - actualX;
		deltaY = e.getY() - actualY;
		updateLocationAndDetectColision();
		
	}		
}

private void updateLocationAndDetectColision() {
	
	at.setToTranslation(deltaX, deltaY);
	s1 = at.createTransformedShape(s1);
	
	actualX += deltaX;
	actualY += deltaY;
	
	deltaX = 0;
	deltaY = 0;
	
	//if (s1.intersects(s2.getBounds2D())) {
	if(s1.contains(s2.getBounds().getCenterX(),s2.getBounds().getCenterY()))
		colision = true;
	else
		colision = false;
	repaint();
	
}

@Override
public void keyPressed(KeyEvent e) {
	switch (e.getKeyCode()) {
	case KeyEvent.VK_RIGHT:
		deltaX = 5;
		break;
	case KeyEvent.VK_LEFT:
		deltaX = -5;
		break;


	case KeyEvent.VK_UP:
		deltaY = -5;
		break;
	case KeyEvent.VK_DOWN:
		deltaY = 5;
		break;
	}
	updateLocationAndDetectColision();
}

@Override
public void keyReleased(KeyEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void keyTyped(KeyEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseMoved(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseClicked(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseEntered(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseExited(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseReleased(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

}