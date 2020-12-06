package Code;

import javafx.scene.shape.Arc;

import java.util.ArrayList;

public class CollisionDetector {
    private CollisionDetector() {}
    public static boolean ballAndArcedCircle(ArrayList<Arc> arcs, Ball ball, double thickness,
                                             double cX, double cY, double rotate) {
        int accuracy = 8;
        double checkEvery = 360d/accuracy;
        double ballCheckAng = 0;

        for (int index = 0 ; index < accuracy ; index++) {
            double rCosTheta = (ball.getCenterX() + ball.getRadius() * Math.cos(ballCheckAng)) - cX;
            double rSinTheta = -(ball.getCenterY() - ball.getRadius() * Math.sin(ballCheckAng)) + cY;
            double r = Math.sqrt(Math.pow(rCosTheta, 2) + Math.pow(rSinTheta, 2));
            double cosThetaI = Math.acos(rCosTheta / r);
            double sinThetaI = Math.asin(rSinTheta / r);
            double tanThetaI = Math.atan(rSinTheta / rCosTheta);
            double theta = Math.toDegrees((sinThetaI > 0) ? (cosThetaI) : ((tanThetaI > 0) ? (Math.PI + tanThetaI) : (2 * Math.PI + tanThetaI)));
            double thetaSkewed = (((theta + rotate) % 360) + 360) % 360;
            double ang = 0;
            double incAng = 360d/arcs.size();
            for (Arc arc : arcs) {
                if (r < arc.getRadiusX() && r > arc.getRadiusX() - thickness
                        && thetaSkewed >= ang && thetaSkewed <= ang + incAng && !arc.getFill().equals(ball.getColor())) {
                    return true;
                }
                ang += incAng;
            }
            ballCheckAng += checkEvery;
        }
        return false;
    }
}
