package animation;
import java.awt.Robot;
import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

//import java.sql.Time;
import java.awt.print.*;
import java.io.File;
import java.io.IOException;

public class Animation extends JFrame implements ActionListener{
	

	
	public static void main(String[] args) {
		
	JFrame frame = new Animation();
	frame.setTitle("Animation");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	JPanel panel = new MyJPanel();
	
	
	
	frame.getContentPane().add(panel);
	frame.setResizable(false);
	frame.pack();
	frame.setVisible(true);

	}
	MyJPanel mypanel = new MyJPanel();//Calling class
	
	
	public Animation() {
		Container cp = this.getContentPane();
	    cp.setLayout(new BorderLayout());
		
		JMenuBar mb = new JMenuBar();
		setJMenuBar(mb);
		
		JMenu menu = new JMenu("Op��es");
		JMenuItem mi = new JMenuItem("");
		mi = new JMenuItem("Exit");
		mi.addActionListener(this);
		menu.add(mi);
		mi = new JMenuItem("Heart Shape");
		mi.addActionListener(this);
		menu.add(mi);
		mb.add(menu);
		
		menu = new JMenu("Aumentar Rapidez");
		mi = new JMenuItem("+20%");
		mi.addActionListener(this);
		menu.add(mi);
		mi = new JMenuItem("-20%");
		mi.addActionListener(this);
		menu.add(mi);
		mi = new JMenuItem("Reset");
		mi.addActionListener(this);
		menu.add(mi);	
		mb.add(menu);
		menu = new JMenu("Imprimir");
		mi = new JMenuItem("Imprimir Gr�fico");
		mi.addActionListener(this);
		menu.add(mi);
		mb.add(menu);
		
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String menuitem = e.getActionCommand();


		
		if (menuitem.equals("+20%")) {
			
			mypanel.updateTime(1);

		}else if(menuitem.equals("-20%")){
			mypanel.updateTime(2);
		}else if(menuitem.equals("Reset")){
			mypanel.updateTime(0);
		}else if(menuitem.equals("Heart Shape")) {
			
			mypanel.changeObjShape();
			
		}else if (menuitem.equals("Exit")) {
			 System.exit(0);
		}else if (menuitem.equals("Imprimir Gr�fico")) {

			//mypanel.PrintGame();
		//mypanel.PrintGame1(this);
			mypanel.PrintProcessing(this);
		}else {
			
			//MyJPanel(menuitem);
		}
		
	
		
	}
	
}
class MyJPanel extends JPanel implements Runnable, KeyListener{

	public static int objtype = 1;
	
	float[] positions = {0};
	int ball_size = 23;
	public static int randomObj_size = 10;
	int state = 0;
	int type = 1;
	int dimension = 800;
	int startx = 500;
	int starty = 500;
	Shape player_object = playerobj(startx,starty,ball_size);
	Shape obj2 = randomObj(dimension,dimension,objtype);
	Shape obj3 = null;
	int deltaX, deltaY;
	int actualX, actualY;
	int score = 1;
	

	JLabel label_score = new JLabel();
	JLabel label_time = new JLabel();
	JLabel label_speed = new JLabel();
	
	int Triangle_Area = 70;
	int Cross_area = 40;
	long start = System.currentTimeMillis();
	public static int time = 1200; //Speed of player global variable to be updated

	Border border_label_score = BorderFactory.createLineBorder(Color.GREEN, 5);
	Border border_label_time = BorderFactory.createLineBorder(Color.RED, 5);
	Border border_label_speed = BorderFactory.createLineBorder(Color.ORANGE, 5);
	
	Object[] options = {"Print","Ok"};
	
	ImagePanel imageSrc = new ImagePanel();
	
	 public static PrinterJob pj;
	 public static MyJPanel painter;
	  
	
	AffineTransform at = new AffineTransform();
	
	JFrame frameProcessing = new JFrame();
	

	public MyJPanel () {
		setPreferredSize(new Dimension(dimension,dimension));
		setFocusable(true);
		Thread thread = new Thread(this);
		thread.start();
		addKeyListener(this);
		
		//label.setText("");

		add(label_score);
		add(label_time);
		add(label_speed);
		label_time.setBounds(100,100,10,10);	
		randomObj(dimension,dimension,objtype); //Generate random point
	}
	
public void paintComponent(Graphics g) {
	label_speed.setBorder(border_label_speed);
	label_time.setBorder(border_label_time);
	label_score.setBorder(border_label_score);
	label_score.setBounds(getWidth()-70,30,35,30);
	label_time.setBounds(getWidth()-110,30,35,30);
	label_speed.setBounds(getWidth()-165,30,50,30);
	
	super.paintComponent(g);
	
	Graphics2D g2 = (Graphics2D) g;
	
	RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	g2.setRenderingHints(rh);
	g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
	
		
	g2.drawLine(30, 20,getWidth()-20,20); //x
	g2.drawLine(30, getHeight()-20,30,20); //y
	g2.drawLine(getWidth()-20, 20,getWidth()-20,getHeight()-20); //y-1
	g2.drawLine(30, getHeight()-20,getWidth()-20,getHeight()-20);  //x-1

	g2.draw(new Cross(312, 432, 50,Cross_area));
	g2.draw(new TriangleArea(200, 200, Triangle_Area,ball_size));

	player_object = at.createTransformedShape(player_object);
	g2.setColor(Color.RED);
	g2.fill(player_object);
	g2.setColor(Color.GREEN);
	g2.fill(obj2);
}


public void updateTime(int timeselector) {
	int increase = 0;
	System.out.println("Time in: "+ time);
	if (timeselector == 1) { //+20%
			increase = (int) (time * 0.2);
			time = time - increase;
				
	}else if (timeselector == 2) {
		increase = (int) (time * 0.2);
		time = time + increase;
	}else if (timeselector ==0) {
		time = 1200;
	}
	


}	

//Fazer um screenshot para posteriormente ser tratado e impresso
public BufferedImage CaptureImage(Component component) {
	 Rectangle rect = component.getBounds();
 	  BufferedImage capture = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
 	  try {
 	  capture = new Robot().createScreenCapture(rect); //Screenshot para tratar como imagem
 	//  g.drawImage(capture, 0, 0, (int)pf.getImageableWidth(), (int)pf.getImageableHeight(), null);
 } catch (AWTException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return capture;
	
}

//Fun��o para Imprimir ScreenShot V2 Solu��o pretendida
public void PrintGame1(Component component) {
	pj = PrinterJob.getPrinterJob();
    pj.setPrintable(new Printable() {
	    	public int print(Graphics g, PageFormat pf, int pageIndex) {
	        switch (pageIndex) {
	        case 0:
	      	  //Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());   //Todo o ecr�
	          Rectangle rect = component.getBounds();
	      	  BufferedImage capture = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
	      	  try {
	      	  capture = new Robot().createScreenCapture(rect); //Screenshot para tratar como imagem
	      	
	      	  imageSrc.setImage(capture);
	      
	      	//  g.drawImage(capture, 0, 0, (int)pf.getImageableWidth(), (int)pf.getImageableHeight(), null);
	      } catch (AWTException e) {
	  		// TODO Auto-generated catch block
	  		e.printStackTrace();
	  	}
	          break;
	        case 1:    	
	        	break;
	        default:
	          return NO_SUCH_PAGE;
	      }
	      return PAGE_EXISTS;
	    }
	});

	 if (pj.printDialog()) {
	      try {
	        pj.print();
	      } catch (PrinterException ex) {
	        ex.printStackTrace();
	      }
	    }
	
	
}

//Imprime depois do screenshot ser tratado
public void PrintGame2(BufferedImage bi) {
	pj = PrinterJob.getPrinterJob();
    pj.setPrintable(new Printable() {
	    	public int print(Graphics g, PageFormat pf, int pageIndex) {
	        switch (pageIndex) {
	        case 0:
	      	g.drawImage(bi, 0, 0, (int)pf.getImageableWidth(), (int)pf.getImageableHeight(), null);
	          break;
	        case 1:    	
	        	break;
	        default:
	          return NO_SUCH_PAGE;
	      }
	      return PAGE_EXISTS;
	    }
	});

	 if (pj.printDialog()) {
	      try {
	        pj.print();
	      } catch (PrinterException ex) {
	        ex.printStackTrace();
	      }
	      frameProcessing.dispose();
	    }
	
	
}


JButton button1 = new JButton("Smooth");
JButton button2 = new JButton("Sharpen");
JButton button3 = new JButton("Edge");
JButton button4 = new JButton("Rescale");
JButton button5 = new JButton("Gray scale");
JButton button6 = new JButton("Reset");
JButton button7 = new JButton("Print");

public void PrintProcessing(Component component) {
	BufferedImage original_screenshot;
	frameProcessing.setPreferredSize(new Dimension(1400, 900));
	frameProcessing.setFocusable(true);
	
	
	Container cp = frameProcessing.getContentPane();
    cp.setLayout(new FlowLayout());
	cp.add(imageSrc);
	cp.add(button1);
	cp.add(button2);
	cp.add(button3);
	cp.add(button4);
	cp.add(button5);
	cp.add(button6);
	cp.add(button7);

    original_screenshot = CaptureImage(component);
    imageSrc.setImage(original_screenshot);
   
	
		button1.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  Operations(1);}});
		button2.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  Operations(2);}});
		button3.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  Operations(3);}});
		button4.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  Operations(4);}});
		button5.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  Operations(5);}});
		button6.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) {
				  imageSrc.setImage(original_screenshot);
				}});
		button7.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) {
				  PrintGame2(imageSrc.getImage());
				}});

    frameProcessing.pack();
    frameProcessing.setVisible(true);

}


private void Operations(int opt) {
	BufferedImageOp op = null;
	 if (opt==1) {
	      float[] data = new float[9];
	      for (int i = 0; i < 9; i++) data[i] = 1.0f/9.0f;
	      Kernel ker = new Kernel(3,3,data);
	      op = new ConvolveOp(ker);
	    } else if (opt==2) {
	      float[] data = {0f, -1f, 0f, -1f, 5f, -1f, 0f, -1f, 0f};
	      Kernel ker = new Kernel(3,3,data);
	      op = new ConvolveOp(ker);
	    } else if (opt==3) {
	      float[] data = {0f, -1f, 0f, -1f, 4f, -1f, 0f, -1f, 0f};
	      Kernel ker = new Kernel(3,3,data);
	      op = new ConvolveOp(ker);
	    } else if (opt==4) {
	      op = new RescaleOp(1.5f, 0.0f, null);
	    } else if (opt==5) {
	      op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
	    }
	    BufferedImage bi = op.filter(imageSrc.getImage(), null);
	    imageSrc.setImage(bi);
	    frameProcessing.pack();
	
}

Boolean loopstate = true;


public void run() {
		

	while (loopstate) {	
			
		long end = System.currentTimeMillis();
		
		int elapsed_time = Math.round((end - start)/1000f);
		label_speed.setText(""+time);	
		label_time.setText(""+elapsed_time);	
		if (elapsed_time >= 60.0f) {	
			JPanel panel = new JPanel();
	        panel.add(new JLabel("Enter number between 0 and 1000"));
			int result = JOptionPane.showOptionDialog(panel,"Pontua��o:"+  score,"GAME OVER",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null, options, options[1]);
			if (result == JOptionPane.YES_OPTION){
			loopstate = false;
			PrintProcessing(this);
			//JOptionPane.getRootFrame().setVisible(false);
			
			//JOptionPane.getRootFrame().setVisible(true);
	        }else {
	        	System.exit(0);
	        }
			
		}
		
		collision();
		CrossCollision(312, 432, 50, Cross_area);
		TriangleCollision(200, 200,Triangle_Area,ball_size,type);		
		repaint();
		
		if(time/60 <0) {
			JOptionPane.showMessageDialog(this, "YOU WON");
			System.exit(0);
		}
		
	//Speed
		try {
			Thread.sleep(time/60);
			
			
		} catch (InterruptedException e) {
		
			e.printStackTrace();
		}
	}
	
}


//Update something
public void update() {
	
	at.setToTranslation(deltaX, deltaY);
	player_object= at.createTransformedShape(player_object);
	
	int posx = (int)player_object.getBounds().getX();
	int posy = (int)player_object.getBounds().getY();
	
	playerobj(posx, posy, ball_size);
	deltaX = 0;
	deltaY = 0;
	repaint();
}


public void collision() {
	
	int posx = (int)player_object.getBounds().getX();
	int posy = (int)player_object.getBounds().getY();
	
	if (player_object.intersects(30, 20, getWidth()-20, 10)) { //X
		deltaY = 6;
		type= 1;
		update();
	
		
	} if (player_object.intersects(30,20,10, getHeight()-40)) { //Y - LEFT
		deltaX = 6;
		update();
		type=4;
	
		
	} if (player_object.intersects(getWidth()-20,20, 10, getHeight()-40)) { // 1-Y - RIGHT
		deltaX = -6;
		update();
	
		type=3;
		
	}if (player_object.intersects(30, getHeight()-20, getWidth()-50, 10)) { //1-X
		deltaY = -6;
		update();

		type=2;
		
	}
		
	if (player_object.intersects(obj2.getBounds().getMinX(),obj2.getBounds().getMinY(),10,10)) {
		
		
		obj2 = randomObj(getWidth()-30,getHeight()-50,objtype);
		
		//ball_size -=1;
		//time -=20;
		score +=30;			
		label_score.setText(""+score);
	}
	player_object = playerobj(posx,posy,ball_size);
	repaint();
	
}
public void changeObjShape() {
		objtype = 0;
}


public Shape randomObj(int x,int y,int objtype){
	
	int randpos = (int)(Math.random() * (3 - 0) + 0);
	int randobj = (int)(Math.random() * (3 - 1) + 1);
	
	if (objtype==1) {
		if (randpos == 0) {
			int randposX = (int)(Math.random() * (((x-30) - 50) + 10)) + 50;
			int randposY = (int)(Math.random() * (((y-30) - 40) + 10)) + 40;
			System.out.println("x: "+randposX+"y: "+randposY);
			obj2 = new Ellipse2D.Float(randposX, randposY, randomObj_size,randomObj_size );
			
		}if (randpos == 1) {
			if (randobj == 1) 
				obj2 = new Ellipse2D.Float(196, 248, randomObj_size,randomObj_size );
			else
				obj2 = new Ellipse2D.Float(196, 215, randomObj_size,randomObj_size );
			
		}if (randpos ==2) {
			if(randobj == 1) {
				float crossX = (int)(Math.random() * (((y-375) - 240) + 10)) + 240;
		    obj2 = new Ellipse2D.Float(crossX,490,randomObj_size,randomObj_size);
			}else {
				float crossY = (int)(Math.random() * (((560) - 420) + 10)) + 420;
				obj2 = new Ellipse2D.Float(322,crossY,randomObj_size,randomObj_size);
				
			}	
			
		}
		
		
	}else {
		obj2 = new Heart(1, 1, 5,5 );
		if (randpos == 0) {
			int randposX = (int)(Math.random() * (((x-30) - 50) + 10)) + 50;
			int randposY = (int)(Math.random() * (((y-30) - 40) + 10)) + 40;
			at.setToTranslation(randposX, randposY);
		}if (randpos == 1) {
			if (randobj == 1) 
				at.setToTranslation(196, 248);
			else
				at.setToTranslation(196, 215);
		}if (randpos ==2) {
			if(randobj == 1) {
				float crossX = (int)(Math.random() * (((y-375) - 240) + 10)) + 240;
		    //obj2 = new Heart(crossX,490,20,20);
		    at.setToTranslation(crossX,490);
			}else {
				float crossY = (int)(Math.random() * (((560) - 420) + 10)) + 420;
				at.setToTranslation(322,crossY);
				
			}	
			
		}
		obj2 = at.createTransformedShape(obj2);
	}
	return obj2;
	
}




public Shape playerobj(int positionx,int positiony,int size){
	//JOptionPane.showMessageDialog(this, ""+type);
	Shape object = null;
	switch (type) {
	case 1:
		object = new Ellipse2D.Float(positionx,positiony,size+6,size+6);
		break;
	case 2:
		object = new Rectangle2D.Float(positionx,positiony,size-6,size-6);
		break;
	case 3:
		object = new Triangle(positionx, positiony, size,1); //normal triangle
		break;
	case 4:
		object = new Triangle(positionx, positiony, size,2); //inverted triangle
		break;
	default:
		break;
	}	
	return object;
	
}



@Override
public void keyPressed(KeyEvent e) {
	//JOptionPane.showMessageDialog(this, "intro");
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
	case KeyEvent.VK_Q:
		//JOptionPane.showMessageDialog(this, "ol");
		type =2;
		
		break;
	}
	update();
	
	
}


public void TriangleCollision(float posx,float posy, int asize,int shapesize,int type) {

	int plax = (int)player_object.getBounds().getX();
	int play = (int)player_object.getBounds().getY();
	
		Point2D[] polyPoints = getPolypoints(plax, play,20,type); 

if (type ==1 || type == 2) {
	if (player_object.intersects(posx-shapesize,posy,2*shapesize,asize)) {
		deltaX =0 ;
		deltaY =0 ;

		update();
		
	}
}
			for (int i = 0; i < polyPoints.length - 1; i++) {
				
					Line2D Line1 = getLines(posx,posy,asize,shapesize,1);
					Line2D Line2 = getLines(posx,posy,asize,shapesize,2);
					Line2D Line3 = getLines(posx,posy,asize,shapesize,3);
					Line2D Line4 = getLines(posx,posy,asize,shapesize,4);
					Line2D Line5 = getLines(posx,posy,asize,shapesize,5);
					Line2D Line6 = getLines(posx,posy,asize,shapesize,6);
					Line2D Line7 = getLines(posx,posy,asize,shapesize,7);
					Line2D Line8 = getLines(posx,posy,asize,shapesize,8);
					
					boolean linha1, linha2, linha3, linha4, linha5, linha6, linha7, linha8;
					
					linha1 = Line1.intersectsLine(polyPoints[i].getX(), polyPoints[i].getY(), polyPoints[i+1].getX(), polyPoints[i+1].getY());	
					linha2 =Line2.intersectsLine(polyPoints[i].getX(), polyPoints[i].getY(), polyPoints[i+1].getX(), polyPoints[i+1].getY());
					linha3 =Line3.intersectsLine(polyPoints[i].getX(), polyPoints[i].getY(), polyPoints[i+1].getX(), polyPoints[i+1].getY());
					linha4 =Line4.intersectsLine(polyPoints[i].getX(), polyPoints[i].getY(), polyPoints[i+1].getX(), polyPoints[i+1].getY());
					linha5 =Line5.intersectsLine(polyPoints[i].getX(), polyPoints[i].getY(), polyPoints[i+1].getX(), polyPoints[i+1].getY());
					linha6 =Line6.intersectsLine(polyPoints[i].getX(), polyPoints[i].getY(), polyPoints[i+1].getX(), polyPoints[i+1].getY());
					linha7 =Line7.intersectsLine(polyPoints[i].getX(), polyPoints[i].getY(), polyPoints[i+1].getX(), polyPoints[i+1].getY());
					linha8 =Line8.intersectsLine(polyPoints[i].getX(), polyPoints[i].getY(), polyPoints[i+1].getX(), polyPoints[i+1].getY());
				
					//System.out.println(linha1+"-"+linha2+"-"+linha3+"-"+linha4+"-"+linha5+"-"+linha6+"-"+linha7+"-"+linha8);
					if (linha1 || linha3) {
						deltaX=-1;
						deltaY=-1;
						update();	
					}if (linha2 || linha4) {
						deltaX=1;
						deltaY=-1;
						update();
					}if (linha5 ||linha7) {
						deltaX=1;
						deltaY=1;
						update();	
					}
					if (linha8 || linha6) {
						deltaX=-1;
						deltaY=1;
						update();
					}

				}
			
			}
	

public Line2D getLines(float x, float y, int size,int shapesize,int line) {
										
	
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
	
	switch (line) {
	case 1:
		return new Line2D.Float(ax, ay, bx, by);
	case 2:
		return new Line2D.Float(bx, by, cx, cy);
	case 3:
		return new Line2D.Float(cx, cy, dx, dy);
	case 4:
		return new Line2D.Float(dx, dy, ex, ey);
	case 5:
		return new Line2D.Float(ex, ey, fx, fy);
	case 6:	
		return new Line2D.Float(fx, fy, cx, cy);
	case 7:
		return new Line2D.Float(cx, cy, gx, gy);
	case 8:
		return new Line2D.Float(gx, gy, ax, ay);
	default:
		return null;
	}
	
	
}

public Point2D[] getPolypoints(float x, float y, float size,int type) {
	
	
	float ax=0,ay=0,bx=0,by=0,cx=0,cy=0;

	switch (type) {
	case 1:

	case 3:
		ax = x + size/2; 
		ay = y;
		bx = x;
		by = y+size;
		cx = x+size;
		cy = y+size;
		break;
	case  4:
		ax = x;
		ay = y;
		bx = x+size;
		by = y;
		cx = x+(size/2);
		cy = y+size;
		break;
	}

	Point2D a = new Point2D.Float(ax,ay);
	Point2D b = new Point2D.Float(bx,by);
	Point2D c = new Point2D.Float(cx,cy);
	Point2D[] polypoints = {a,b,c,a};
	
	//System.out.println(ax+","+ay+","+bx+","+by);
	return polypoints;
	
}

public void CrossCollision(int x, int y , int size, int shapesize) {
	
	float ax = x;
	float ay = y;
	float a2y= ay+size;
	float a3x = ax-size;
	float a3y = a2y;

	float bx = x+shapesize;
	float by = y;
	float b2x= bx;
	float b2y= a2y;
	float b3x= bx+size;
	float b3y= b2y;
	

	float cy = b3y+shapesize;
	float c2x= b3x-size;
	float c2y= cy;
	float c3x= c2x;
	float c3y= c2y+size;
	
	float dx = c3x-shapesize;
	float dy = c3y;
	float d2x= dx;
	float d2y= dy-size;
	float d3x= d2x-size;
	float d3y= d2y;
	
	
	
	int value = checkBoundaries(a3x, a3y, ax, ay, size, shapesize);
	
	
		
	if (player_object.getBounds().intersects(ax,ay,1,size)) { //h 
		
		if (value == 2) {
			//JOptionPane.showMessageDialog(this, "h"+a2y);
			deltaX = 5;
		
		}else
		deltaX = -5;

		update();
		
		
	}if (player_object.intersects(bx,by,1,size)) {//a
		
		if (value == 2) {
			//JOptionPane.showMessageDialog(this, "h"+a2y);
			deltaX = -5;
		
		}else		
		deltaX = 5;

		update();
	}if (player_object.intersects(c2x,c2y,1,size)) { //d
		if (value == 2) {
			//JOptionPane.showMessageDialog(this, "h"+a2y);
			deltaX = -5;
		
		}else				
		deltaX = 5;
		update();
		
	}if (player_object.intersects(d2x,d2y,1,size)) { //e
		if (value == 2) {
			//JOptionPane.showMessageDialog(this, "h"+a2y);
			deltaX = 5;
		
		}else		
		deltaX = -5;
		update();
		
	}if (player_object.intersects(b2x,b2y,size,1)) { //b
		
		
		if (value == 1) {
			//JOptionPane.showMessageDialog(this, "h"+a2y);
			deltaY = 5;
		
		}else		
			deltaY = -5;

		update();	
	}if (player_object.intersects(c2x,c2y,size,1)) { //c
		if (value == 1) {
			//JOptionPane.showMessageDialog(this, "h"+a2y);
			deltaY = -5;
		
		}else
		deltaY = 5;
		
		update();
	}if (player_object.intersects(d3x,d3y,size,1)) { //f
		if (value == 1) {
			//JOptionPane.showMessageDialog(this, "h"+a2y);
			deltaY = -5;
		
		}else
		deltaY = 5;
		
		update();
	}if (player_object.intersects(a3x,a3y,size,1)) { //g
		if (value == 1) {
			//JOptionPane.showMessageDialog(this, "h"+a2y);
			deltaY = 5;
		}else
		deltaY = -5;
		
		update();
	}
	value = 0;
	
	repaint();
	
	
	
	
}

//Area dentro da cruz para testar 

public int checkBoundaries(float a3x,float a3y, float ax, float ay, int size, int shapesize) {
	int value = 0;
		
	
	if (player_object.intersects(a3x,a3y+3,(2*size)+shapesize,shapesize-6)) {
		value=1;
		//HORIZONTAL
		
	}else
		if (player_object.getBounds().intersects(ax+3,ay,shapesize-6,(2*size)+shapesize)) {
			value=2;
			//VERTICAL
		}
	return value;
}

@Override
public void keyTyped(KeyEvent e) {
	
}

@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}

}

class ImagePanel extends JPanel {
	  BufferedImage image = null;

	  public ImagePanel() {
	    image = null;
	    setPreferredSize(new Dimension(256, 256));
	  }

	  public ImagePanel(BufferedImage bi) {
	    image = bi;
	  }

	  public void paintComponent(Graphics g) {
	    Graphics2D g2 = (Graphics2D)g;
	    if (image != null)
	      g2.drawImage(image, 0, 0, this);
	    else
	      g2.drawRect(0, 0, getWidth()-1, getHeight()-1);
	  }

	  public BufferedImage getImage() {
	    return image;
	  }

	  public void setImage(BufferedImage bi) {
	    image = bi;
	    setPreferredSize(new Dimension(bi.getWidth(), bi.getHeight()));
	    invalidate();
	    repaint();
	  }
	}
