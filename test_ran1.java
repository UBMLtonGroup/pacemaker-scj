import java.lang.reflect.*;

public class test_ran1 {
    public static void main(String[] args)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        System.out.println("Test ran1");

        Class myClass = Class.forName("EcgCalc");
        EcgCalc ecgCalc = new EcgCalc(new EcgParam());

        String methodName = "ran1";
        Class[] parameterType = null;
        Method method = myClass.getDeclaredMethod(methodName, parameterType);
        if (method == null) {
            System.out.println("cant find ran1");
        } else {
            method.setAccessible(true);
            double r = (double) method.invoke(ecgCalc);
            System.out.println("ran1 " + r);
            r = (double) method.invoke(ecgCalc);
            System.out.println("ran1 " + r);
        }
    }
}
