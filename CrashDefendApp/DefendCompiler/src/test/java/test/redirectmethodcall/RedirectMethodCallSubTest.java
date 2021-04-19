package test.redirectmethodcall;

import java.math.BigDecimal;
import java.math.BigInteger;

public class RedirectMethodCallSubTest extends  RedirectMethodCallTest {



    public void callRedirectMethod(){
        int intType = this.testIntMethod(20);
        this.testVoidMethod(null);
        BigDecimal bigDecimal = this.testObjectMethod();

        BigInteger bigInteger = bigDecimal.toBigInteger();

        System.out.println(intType);
        System.out.println(bigDecimal.toEngineeringString() + " " + bigInteger.abs());
    }



    @Override
    public void testVoidMethod(Object args){

    }


    public int testIntMethod(int num){
        return  30 + num;
    }

    public BigDecimal testObjectMethod(){
        return  new BigDecimal(30);
    }




}
