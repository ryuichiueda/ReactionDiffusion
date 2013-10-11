import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class GraphicPanel extends JPanel{
	private static final long serialVersionUID = 1701284289052638330L;
	private final ReactionDiffusionFunction _refRDF;
	private int _magnitude;
	Point _origin_point;
	
	public GraphicPanel(ReactionDiffusionFunction rdf){
		super();
		_refRDF = rdf;
		_magnitude = 1;
		_origin_point = new Point(0,0);

		this.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
				_refRDF.addInpact(relative_point(arg0));
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {
				repaint();
			}
		});
	}
	
	private Point relative_point(MouseEvent arg0){
		Point p = arg0.getLocationOnScreen();
		Point q = getLocationOnScreen();
		p.x -= q.x;
		p.y -= q.y;
		p.x /= _magnitude;
		p.y /= _magnitude;
		return p;
	}
	
	public void setMagnitude(int value){
		_magnitude = value > 0 ? value : 1;
	}
	
	public void paintComponent(Graphics g){
		drawValues(g);
	}
	
	private void drawValues(Graphics g){

		final double max_value = _refRDF.getUMax();	
		if(max_value < 0.0001){
			g.setColor(new Color(0,0,0));
			g.fillRect(0,0,_refRDF.getWidth(),_refRDF.getHeight());
			return;
		}
		
		for(int i=0;i<_refRDF.getWidth();i++){
			for(int j=0;j<_refRDF.getHeight();j++){
				int v = (int)(_refRDF.getValue(i,j)/max_value*255);
				
				g.setColor(new Color(v,v,v));
				g.fillRect(i*_magnitude,j*_magnitude,_magnitude,_magnitude);
			}
		}	
	}
}
