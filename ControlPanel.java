import java.awt.Dimension;
import javax.swing.*;
import java.awt.event.*;

public class ControlPanel extends JPanel {
	private static final long serialVersionUID = 2030406662988062643L;
	private final ReactionDiffusionFunction _refRDF;
	private final GraphicPanel _graphic;
	static Timer _timer;
	JButton _start_stop_btn;
	JButton _random_btn;
	JButton _zero_btn;
	JButton _square_btn;
	JComboBox _parameter_cbox;
	JComboBox _magnitude_cbox;
	
	private void giveLabelName(){
		_start_stop_btn = new JButton("start");
		_random_btn = new JButton("random");
		_zero_btn = new JButton("set zero");	
		_square_btn = new JButton("add square");
		
		String [] params = {"stripe","BZ","spot","net"};
		_parameter_cbox = new JComboBox(params);
		_parameter_cbox.setPreferredSize(new Dimension(100, 30));
		
		String [] magnitude_number = {"1","2","3","4"};
		_magnitude_cbox = new JComboBox(magnitude_number);
		_magnitude_cbox.setPreferredSize(new Dimension(100, 30));
	}
	
	private void addButtons(){
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		add(_parameter_cbox);
		add(_magnitude_cbox);
		add(_start_stop_btn);
		add(_random_btn);
		add(_zero_btn);		
		add(_square_btn);
	}
	
	private void stringToParamMode(String s){
		if(s.equals("stripe")){
			_refRDF.setStripeFunction();
		}
		else if(s.equals("BZ")){
			_refRDF.setBZFunction();
		}
		else if(s.equals("spot")){
			_refRDF.setSpotFunction();
		}
		else if(s.equals("net")){
			_refRDF.setNetFunction();
		}		
	}
	
	public ControlPanel(ReactionDiffusionFunction rdf,
						GraphicPanel graphic_panel,
						ActionListener action_listener){
		_refRDF = rdf;
		_graphic = graphic_panel;
		_timer = new Timer(50,action_listener);
		
		giveLabelName();
		addButtons();
		
		_parameter_cbox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String param_name = (String)_parameter_cbox.getSelectedItem();
				stringToParamMode(param_name);
			}
		});
		
		_magnitude_cbox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String param_name = (String)_magnitude_cbox.getSelectedItem();
				_graphic.setMagnitude(Integer.parseInt(param_name));
			}
		});
	
		_start_stop_btn.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
				if(_start_stop_btn.getText().equals("start")){
					_timer.start();
					_start_stop_btn.setText("stop");
				}
				else if(_start_stop_btn.getText().equals("stop")){
					_timer.stop();
					_start_stop_btn.setText("start");
				}
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}		
		});
		
		_random_btn.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
				_refRDF.setRandomValue();
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}		
		});
		
		_zero_btn.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
				_refRDF.setZero();
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}		
		});
		
		_square_btn.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
				_refRDF.putSquare();
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}		
		});
	}
}
