import java.awt.*;

import javax.swing.*;

public class StatePanel extends JPanel {
	private static final long serialVersionUID = 1524690574810532920L;
	
	private final ReactionDiffusionFunction _refRDF;
	private JLabel _u_max;
	private JLabel _v_max;
	private JLabel _time;
	private JLabel _u_equation;
	private JLabel _v_equation;
	
	public StatePanel(ReactionDiffusionFunction rdf){
		super();
		
		_refRDF = rdf;
		_u_max = new JLabel();
		_v_max = new JLabel();
		_time = new JLabel();	
		_u_equation = new JLabel();
		_v_equation = new JLabel();	
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		add(_u_max);
		add(_v_max);
		add(_time);
		add(_u_equation);
		add(_v_equation);
	}
	
	public void paintComponent(Graphics g){
		_u_max.setText("u max: " + _refRDF.getUMax());
		_v_max.setText("v max: " + _refRDF.getVMax());
		_time.setText("time: " + _refRDF.getTimeStep());
		_u_equation.setText(_refRDF.getUEquationText());
		_v_equation.setText(_refRDF.getVEquationText());
	}
}
