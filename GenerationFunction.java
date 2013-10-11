
public class GenerationFunction {
	private double _u_coef;
	private double _v_coef;
	private double _constant;
	private double _upperbound;
	
	public GenerationFunction(double u,double v,double c,double max){
		_u_coef = u;
		_v_coef = v;
		_constant = c;
		_upperbound = max;
	}
	
	private double thresholding(double value){
		if(value < 0.0){
			return 0.0;
		}
		if(value > _upperbound){
			return _upperbound;
		}
		return value;
	}
	
	public double getValue(UVPair x){
		return thresholding(_u_coef*x._u + _v_coef*x._v + _constant);
	}
	
	public String getEquationText(){
		return Double.toString(_u_coef) + "u" 
			+ addSign(_v_coef) + "v" + addSign(_constant);
		
	}
	
	private String addSign(double value){
		String sign = value >= 0.0 ? "+" : "";
		return sign + value;
	}
}
