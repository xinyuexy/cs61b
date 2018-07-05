/**
* NBody is a class that will actually run your simulation
*/
public class NBody {
    /** 读取宇宙半径 */
    public static double readRadius(String planetsTxtPath) {
        In in = new In(planetsTxtPath);
        int pnum = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    /** 读取行星参数 */
    public static Planet[] readPlanets(String planetsTxtPath) {
        In in = new In(planetsTxtPath);
        int pnum = in.readInt();
        Planet[] planets = new Planet[pnum];
        in.readDouble();
        for (int i=0; i<pnum; i++) {
            planets[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(),
                                    in.readDouble(), in.readDouble(), in.readString());
        }
        return planets;
    }

    private static void drawBackgroud(double radius) {
        String backgroud = "images/starfield.jpg";
        //设置屏幕坐标范围
        StdDraw.setScale(-radius, radius);
        StdDraw.picture(0, 0, backgroud);
    }

    private static void drawPlanets(Planet[] planets) {
        for (Planet p: planets) {
            p.draw();
        }
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Please input valid parameters");
            System.exit(0);
        }
        //动画持续时间
        double T = Double.parseDouble(args[0]);
        //时间增量
        double dt = Double.parseDouble(args[1]);
        //行星参数文件
        String filename = args[2];

        double radius = NBody.readRadius(filename);
        Planet[] planets = NBody.readPlanets(filename);

        //StdDraw.setCanvasSize(800,600);
        drawBackgroud(radius);
        drawPlanets(planets);
        StdDraw.show(10);
        StdAudio.loop("audio/2001.mid");

        for (int t = 0; t < T ; t += dt) {
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            //计算每个行星受到的合力
            for (int i=0; i<planets.length; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            //作用dt后更新行星位置,速度
            for (int i=0; i<planets.length; i++) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }

            drawBackgroud(radius);
            drawPlanets(planets);
            StdDraw.show(10);
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i=0; i<planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                planets[i].xxPos, planets[i].yyPos, planets[i].xxVel, planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
