package NGPlayerTools;

/**
 * Created by Nils on 13.05.2017.
 */
public class VirtualPotential {
    ScanDirection scanDirection;
    VirtualPosition virtualPosition;
    int gapDepthUnderneathPosition;
    int potentialScore;
    int distanceFromPattern;
    VirtualPattern virtualPattern;

    public VirtualPotential(VirtualPattern virtualPattern, ScanDirection scanDirection, VirtualPosition virtualPosition, int gapDepthUnderneathPosition, int distanceFromPattern, int potentialScore) {
        this.scanDirection              = scanDirection;
        this.virtualPosition            = virtualPosition;
        this.gapDepthUnderneathPosition = gapDepthUnderneathPosition;
        this.distanceFromPattern        = distanceFromPattern;
        this.virtualPattern             = virtualPattern;
        this.potentialScore             = potentialScore;
        //TODO: set potential score

        ////System.out.println("-> new Potential:" + this);
    }

    public ScanDirection getScanDirection() {
        return scanDirection;
    }

    public VirtualPosition getVirtualPosition() {
        return virtualPosition;
    }

    public int getGapDepthUnderneathPosition() {
        return gapDepthUnderneathPosition;
    }

    public int getPotentialScore() {
        return potentialScore;
    }

    public int getDistanceFromPattern() {
        return distanceFromPattern;
    }

    public VirtualPattern getVirtualPattern() {
        return virtualPattern;
    }

    @Override
    public String toString(){
        return  "(" + virtualPosition.getHorizontalPosition() + "|" + virtualPosition.getVerticalPosition() + ")" +
                virtualPattern.getPatternType() + " " + virtualPattern.getPlayerColor() + " " + scanDirection + " " +
                distanceFromPattern + " " + gapDepthUnderneathPosition + " " + potentialScore;
    }
}


