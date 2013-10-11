import java.util.Random;
import java.awt.*;
import java.awt.event.MouseListener;

import javax.swing.JApplet;

// reaction diffusion function (reference: http://www.bio.nagoya-u.ac.jp/~z3/research/rdsoft.htm)

public class ReactionDiffusionFunction {
	
	private int _timesteps = 0;
	private final int _width = 300;
	private final int _height = 300;
	private double [][] _u;
	private double [][] _v;
	
	private final LocalFunctionCore _stripe_function = 
		new LocalFunctionCore(new GenerationFunction(0.08,-0.08,0.04,0.2),
		   		  			  new GenerationFunction(0.06,0.0,-0.15,0.5),
		   		  			  new UVPair(-0.03,-0.03), //degeneration
		   		  			  new UVPair(0.01,0.25));//diffusion
	
	private final LocalFunctionCore _BZ_function = 
		new LocalFunctionCore(new GenerationFunction(2.0,-6.0,0.0,1.0),
					   		  new GenerationFunction(0.2,0.0,0.0,100.0),
					   		  new UVPair(-0.1,-0.6), 
					   		  new UVPair(0.02,0.01));
	
	private final LocalFunctionCore _spot_function = 
		new LocalFunctionCore(new GenerationFunction(0.08,-0.08,0.02,0.2),
					   		  new GenerationFunction(0.1,0.0,-0.15,0.5),
					   		  new UVPair(-0.03,-0.06), 
					   		  new UVPair(0.02,0.5));
	
	private final LocalFunctionCore _net_function = 
		new LocalFunctionCore(new GenerationFunction(0.08,-0.08,0.2,0.2),
					   		  new GenerationFunction(0.1,0.0,-0.15,0.5),
					   		  new UVPair(-0.03,-0.06), 
					   		  new UVPair(0.02,0.5));
	
	private LocalFunctionCore _function_core;
	
	public int getTimeStep(){
		return _timesteps;
	}

	public int getWidth(){return _width;}
	public int getHeight(){return _height;}
	public double getValue(int x,int y){
		return _u[x][y];
	}
	
	public double getUMax(){
		double max = 0.0;
		for(int i=0;i<_width;i++){
			for(int j=0;j<_height;j++){
				max = max > _u[i][j] ? max : _u[i][j];
			}
		}
		return max;
	}
	
	public double getVMax(){
		double max = 0.0;
		for(int i=0;i<_width;i++){
			for(int j=0;j<_height;j++){
				max = max > _v[i][j] ? max : _v[i][j];
			}
		}
		return max;
	}
	
	public void setStripeFunction(){
		_function_core = _stripe_function;
	}
	
	public void setBZFunction(){
		_function_core = _BZ_function;
	}
	
	public void setSpotFunction(){
		_function_core = _spot_function;
	}
	
	public void setNetFunction(){
		_function_core = _net_function;
	}
	
	public ReactionDiffusionFunction(){
		_function_core = _stripe_function;
		_u = new double [_width][_height];
		_v = new double [_width][_height];
		setRandomValue();
	}
	
	private Rectangle getRandomRect(){
		Rectangle rect = new Rectangle();

		Random rand = new java.util.Random();
		rect.x = rand.nextInt(getWidth());
		rect.y = rand.nextInt(getHeight());
		rect.width = rand.nextInt(getWidth()- rect.x);
		rect.height = rand.nextInt(getHeight() - rect.y);
		
		return rect;
	}
	
	public void putSquare(){		
		Rectangle rect = getRandomRect();
		double add_value = getUMax() + 0.1;
		
		for(int i=rect.x;i<rect.x+rect.width;i++){
			for(int j=rect.y;j<rect.y+rect.height;j++){
				_u[i][j] = add_value;
				_v[i][j] = 0.0;
			}
		}
	}
	
	public void addInpact(Point p){
		double max = getUMax();
		max = max < 1.0 ? 1.0 : max;

		for(int i=p.x-5;i<p.x+5;i++){
			if(i < 0 || i >= _width)
				continue;		
			
			for(int j=p.y-5;j<p.y+5;j++){
				if(j < 0 || j >= _height)
					continue;

				_u[i][j] = max;
				_v[i][j] = 0.0;
			}
		}
	}
	
	public void setRandomValue(){
		for(int i=0;i<_width;i++){
			for(int j=0;j<_height;j++){
				_u[i][j] = Math.random();
				_v[i][j] = Math.random();
			}
		}
	}

	public void setZero(){
		for(int i=0;i<_width;i++){
			for(int j=0;j<_height;j++){
				_u[i][j] = 0.0;
				_v[i][j] = 0.0;
			}
		}
	}
	
	private UVPair getLaplace(int i,int j){
		UVPair ans = new UVPair(0.0,0.0);
		
		int counter = 0;
		if(i-1 >= 0){
			ans.add(_u[i-1][j] - _u[i][j], _v[i-1][j] - _v[i][j]);
			counter++;
		}
		if(i+1 < _width){
			ans.add(_u[i+1][j] - _u[i][j], _v[i+1][j] - _v[i][j]);
			counter++;
		}
		if(j-1 >= 0){
			ans.add(_u[i][j-1] - _u[i][j], _v[i][j-1] - _v[i][j]);
			counter++;
		}
		if(j+1 < _height){
			ans.add(_u[i][j+1] - _u[i][j], _v[i][j+1] - _v[i][j]);
			counter++;
		}

		ans._u /= counter;
		ans._v /= counter;
		
		return ans;
	}
	
	public void forwardOneTimeStep(){
		
		double[][] du = new double [_width][_height];
		double[][] dv = new double [_width][_height];
		
		for(int i=0;i<_width;i++){
			for(int j=0;j<_height;j++){
				
				UVPair reaction = _function_core.getReaction(new UVPair(_u[i][j],_v[i][j]));
				UVPair diffusion = _function_core.getDiffusion(getLaplace(i, j));
				
				du[i][j] = reaction._u + diffusion._u;
				dv[i][j] = reaction._v + diffusion._v;
			}
		}

		timeEvolution(du,dv);
		CancelMinus();
	}
	
	private void timeEvolution(double[][] du,double[][] dv){
		_timesteps++;
		for(int i=0;i<_width;i++)
			for(int j=0;j<_height;j++){
				_u[i][j] += du[i][j];
				_v[i][j] += dv[i][j];
			}
	}
	
	private void CancelMinus(){
		for(int i=0;i<_width;i++){
			for(int j=0;j<_height;j++){
				if(_u[i][j] < 0.0)	_u[i][j] = 0.0;
				if(_v[i][j] < 0.0)	_v[i][j] = 0.0;
			}
		}
	}
	
	public String getUEquationText(){
		return _function_core.getUEquationText();
	}
	
	public String getVEquationText(){
		return _function_core.getVEquationText();
	}
}
