public class UVPair{
	public double _u;
	public double _v;
		
	public UVPair(double u,double v){
		set(u,v);
	}
		
	public void set(double u,double v){
		_u = u;
		_v = v;
	}
		
	public UVPair DirectProduct(UVPair rhs){
		return new UVPair(_u*rhs._u,_v*rhs._v);
	}
	
	public double InnerProduct(UVPair rhs){
		return _u*rhs._u + _v*rhs._v;
	}
		
	public UVPair getPlus(UVPair rhs){
		return new UVPair(_u + rhs._u, _v + rhs._v);
	}
	
	public void add(double u,double v){
		_u += u;
		_v += v;
	}
}