/**
* A class represent planet
*/
public class Planet {
    //Its current x position
    public double xxPos;
    //Its current y position
    public double yyPos;
    //Its current velocity in the x direction
    public double xxVel;
    //Its current velocity in the y direction(在y方向速度)
    public double yyVel;
    //Its mass (质量)
    public double mass;
    //The name of an image in the images directory that depicts the planet
    public String imgFileName;

    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        double dx = this.xxPos - p.xxPos;
        double dy = this.yyPos - p.yyPos;
        double dist = Math.sqrt((dx * dx) + (dy * dy));
        return dist;
    }

    /** p对this施加的力(总共的力) */
    public double calcForceExertedBy(Planet p) {
        double G = 6.67 * Math.pow(10, -11);
        double dist = this.calcDistance(p);
        double F = G * this.mass * p.mass / (dist * dist);
        return F;
    }

    /** 计算p对this施加的力在x方向上的分量 */
    public double calcForceExertedByX(Planet p) {
        double F = this.calcForceExertedBy(p);
        double dist = this.calcDistance(p);
        double Fx = F * (p.xxPos - this.xxPos) / dist;
        return Fx;
    }

    /** 计算p对this施加的力在y方向上的分量 */
    public double calcForceExertedByY(Planet p) {
        double F = this.calcForceExertedBy(p);
        double dist = this.calcDistance(p);
        double Fy = F * (p.yyPos - this.yyPos) / dist;
        return Fy;
    }

    /** 计算X方向上的合力,需要去除自身 */
    public double calcNetForceExertedByX(Planet[] planets) {
        if (planets == null || planets.length == 0) {
            return 0.0;
        }

        double fNetX = 0.0;
        for (Planet p: planets) {
            if (!this.equals(p)) {
                fNetX += this.calcForceExertedByX(p);
            }
        }
        return fNetX;
    }

    /** 计算Y方向上的合力,需要去除自身 */
    public double calcNetForceExertedByY(Planet[] planets) {
        if (planets == null || planets.length == 0) {
            return 0.0;
        }

        double fNetY = 0.0;
        for (Planet p: planets) {
            if (!this.equals(p)) {
                fNetY += this.calcForceExertedByY(p);
            }
        }
        return fNetY;
    }

    /** 计算经过X和Y方向上力的作用dt时间后行星最终的位置 */
    public void update(double dt, double fX, double fY) {
        //x方向上的加速度
        double ax = fX / this.mass;
        double ay = fY / this.mass;
        this.xxVel = this.xxVel + dt * ax;
        this.yyVel = this.yyVel + dt * ay;
        this.xxPos = this.xxPos + dt * this.xxVel;
        this.yyPos = this.yyPos + dt * this.yyVel;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }

}
