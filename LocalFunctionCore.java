public class LocalFunctionCore{
	private UVPair _degeneration_param;
	private UVPair _diffusion_param;

	private GenerationFunction _u_generator;
	private GenerationFunction _v_generator;
	
	public LocalFunctionCore(GenerationFunction gu, GenerationFunction gv,
					  UVPair degeneration_param,UVPair diffusion_param){
		_u_generator = gu;
		_v_generator = gv;
		_degeneration_param = degeneration_param;
		_diffusion_param = diffusion_param;
	}
	
	public UVPair getReaction(UVPair x){
		return getGeneration(x).getPlus(getDegeneration(x));
	}	
		
	public UVPair getDiffusion(UVPair x){
		return _diffusion_param.DirectProduct(x);
	}
		
	private UVPair getGeneration(UVPair x){
		return new UVPair(_u_generator.getValue(x),
						  _v_generator.getValue(x));
	}

	private UVPair getDegeneration(UVPair x){
		return _degeneration_param.DirectProduct(x);
	}
	
	public String getUEquationText(){
		return "u = TH(" +_u_generator.getEquationText() + ")"
		+ addSign(_degeneration_param._u) + "u"
		+ addSign(_diffusion_param._u) + "Lap u";
	}
	
	public String getVEquationText(){
		return "v = TH(" +_v_generator.getEquationText() + ")"
				+ addSign(_degeneration_param._v) + "v"
				+ addSign(_diffusion_param._v) + "Lap v";
	}
	
	private String addSign(double value){
		String sign = value >= 0.0 ? "+" : "";
		return sign + value;
	}
}