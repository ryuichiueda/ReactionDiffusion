import javax.swing.*;
import java.awt.event.*;

public class ReactionDiffusion extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	static ReactionDiffusionFunction _rdf;
	static GraphicPanel _graphic_window;
	static StatePanel _state_panel;
	static ControlPanel _control_panel;
	static JFrame _main_frame;

	public ReactionDiffusion(String str){
		super(str);
		_rdf = new ReactionDiffusionFunction();
		_graphic_window = new GraphicPanel(_rdf);
		_state_panel = new StatePanel(_rdf);
		_control_panel = new ControlPanel(_rdf,_graphic_window,this);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	  public void actionPerformed(ActionEvent e){
		_rdf.forwardOneTimeStep();
		repaint();		  
	  }

	public static void main(String[] args) {		
		_main_frame = new ReactionDiffusion("Reaction Diffusion Function Simulator");
		_main_frame.setSize(_rdf.getWidth()+300 , _rdf.getHeight()+100);
		_main_frame.setVisible(true);
		
		_graphic_window.setBounds(0, 0, _rdf.getWidth(), _rdf.getHeight());
		_control_panel.setBounds(_rdf.getWidth()+10, 0,150,160);
		_state_panel.setBounds(_rdf.getWidth()+10, 200,300,200);
		
		_main_frame.getContentPane().setLayout(null);
		_main_frame.getContentPane().add(_graphic_window,null);
		_main_frame.getContentPane().add(_control_panel,null);
		_main_frame.getContentPane().add(_state_panel,null);
	}

}
