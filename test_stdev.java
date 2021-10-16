
import java.lang.reflect.*;

public class test_stdev {
    public static void main(String[] args)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        System.out.println("Test stdev");

        // note the way EcgCalc implements it, the first element is
        // ignored
        double[] x = {0, 10, 12, 23, 23, 16, 23, 21, 16};
        int n = x.length-1;
        double expected = 5.237229365663817;

        Class myClass = Class.forName("EcgCalc");
        EcgCalc ecgCalc = new EcgCalc(new EcgParam());

        String methodName = "stdev";
        Class[] parameterType = {double[].class, int.class};
        Method method = myClass.getDeclaredMethod(methodName, parameterType);
        if (method == null) {
            System.out.println("cant find stdev");
        } else {
            method.setAccessible(true);
            double r = (double) method.invoke(ecgCalc, x, n);
            System.out.println("stdev " + r);
            if (r != expected) { 
                System.out.println("test failed");
            }
        }

    }
}
