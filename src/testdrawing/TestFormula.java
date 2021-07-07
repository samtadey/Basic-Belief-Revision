package testdrawing;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

public class TestFormula {

	public TestFormula() {
		Function test = new Function("f(v) = (1.5 * v) + 2");
		Argument v = new Argument("v = 2.0");
		Expression e = new Expression("f(v)", test, v);
		
		System.out.println(e.calculate());

	}
	
	
    public static void main(String[] args) {
    	new TestFormula();
    }

}
