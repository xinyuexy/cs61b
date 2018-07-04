import java.math.*;

/**
* Test Planet Class.
*/
public class TestPlanet {
    public static void main(String[] args) {
        Planet sun = new Planet(1.0 * Math.pow(10,12), 2.0 * Math.pow(10,11), 0.0,
                                0.0, 2.0 * Math.pow(10,30), "./img/sun.gif");

        Planet saturn = new Planet(2.3 * Math.pow(10,12), 9.5 * Math.pow(10,11), 0.0,
                                   0.0, 6.0 * Math.pow(10,26), "./img/saturn.gif");

        double F1 = sun.calcForceExertedBy(saturn);
        double F2 = saturn.calcForceExertedBy(sun);
        double F1X = sun.calcForceExertedByX(saturn);
        double F2X = saturn.calcForceExertedByX(sun);

        System.out.println("sun exertedBy saturn F Fx: " + F1 + " " + F1X);
        System.out.println("saturn exertedBy sun F Fx: " + F2 + " " + F2X);
    }
}

