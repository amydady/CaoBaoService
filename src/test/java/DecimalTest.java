import java.math.BigDecimal;

public class DecimalTest
{

	public static void main(String[] args)
	{
		BigDecimal a=new BigDecimal("1.2");
		BigDecimal b=new BigDecimal("1.13");
		System.out.println(a.multiply(b));
		System.out.println(a.multiply(b).setScale(2,java.math.BigDecimal.ROUND_DOWN));

	}

}
