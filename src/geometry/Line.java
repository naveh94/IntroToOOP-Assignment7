package geometry;


/**
 * A line object.
 * @author Naveh Marchoom
 */
public class Line {
    private Point start, end;

    /**
     * A line object consisting of two points.
     * @param start The starting point for the line.
     * @param end The ending point for the line.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * A line object consisting of two points.
     * @param x1 The x value for the starting point of the line.
     * @param y1 The y value for the starting point of the line.
     * @param x2 The x value for the ending point of the line.
     * @param y2 The y value for the ending point of the line.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Calculate the line length, using the distance between the starting point
     * and the ending point.
     * @return The line's length.
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * Calculate the middle point of the line.
     * @return The middle point of the line.
     */
    public Point middle() {
        double midX = (this.start.getX() + this.end.getX()) / 2;
        double midY = (this.start.getY() + this.end.getY()) / 2;
        Point mid = new Point(midX, midY);
        return mid;
    }

    /**
     * Get the starting point of the line.
     * @return The starting point of the line.
     */
    public Point start() {
        return this.start;
    }

    /**
     * Get the ending point of the line.
     * @return The ending point of the line.
     */
    public Point end() {
        return this.end;
    }

    /**
     * Check if the line is intersecting with another line.
     * @param other The line it check intersection with.
     * @return true if the lines intersect. Else, rerturn flase.
     */
    public boolean isIntersecting(Line other) {
        return (this.intersectionWith(other) != null);
    }

    /**
     * Get the slope of the line equation.
     * @return The slope of the line.
     */
    public double getSlope() {
        if (this.start.getX() == this.end.getX()) {
            return Double.POSITIVE_INFINITY;
        }

        if (this.start.getY() == this.end.getY()) {
            return 0;
        }

        return ((this.start.getY() - this.end.getY()) / (this.start.getX() - this.end.getX()));
    }

    /**
     * Using the line's equation, calculate a y value for a point on the line
     * using an x value.
     * @param x The x value of the point checked.
     * @return the y value of the point.
     */
    private double getEquationY(double x) {
        return ((x - this.start.getX()) * this.getSlope() + this.start.getY());
    }

    /**
     * Get the minimum value out of 2 doubles.
     * @param n1 The first value.
     * @param n2 The second value.
     * @return The minimum value of the two.
     */
    private double min(double n1, double n2) {
        if (n1 > n2) {
            return n2;
        }
        return n1;
    }

    /**
     * Get the maximum value out of 2 doubles.
     * @param n1 The first value.
     * @param n2 The second value.
     * @return The maximum value of the two.
     */
    private double max(double n1, double n2) {
        if (n1 > n2) {
            return n1;
        }
        return n2;
    }

    /**
     * Check if a point is on the line area.
     * @param mid the point checked.
     * @return true if the point parameters are between the line parameters.
     *  else return false.
     */
    private boolean isBetween(Point mid) {
        return ((mid.getX() >= min(this.start.getX(), this.end.getX()))
                && (mid.getX() <= max(this.start.getX(), this.end.getX()))
                && (mid.getY() >= min(this.start.getY(), this.end.getY()))
                && (mid.getY() <= max(this.start.getY(), this.end.getY())));
    }

    /**
     * Get the intersection point of this line with another line.
     * @param other The line it's checked with.
     * @return If exist, returns the intersection point. Else returns null.
     */
    public Point intersectionWith(Line other) {
        double x1 = this.start().getX(), x2 = this.end().getX(), y1 = this.start().getY(), y2 = this.end().getY();
        double x3 = other.start().getX(), x4 = other.end().getX(), y3 = other.start().getY(), y4 = other.end().getY();
        double ua, ub, denom;
        int x, y;
        denom = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
        ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / denom;
        ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / denom;
        if (denom != 0.0 && ua >= 0.0 && ua <= 1.0  && ub >= 0.0 && ub <= 1.0) {
            x = (int) (x1 + ua * (x2 - x1));
            y = (int) (y1 + ua * (y2 - y1));
            return new Point(x, y);
        }
        return null;
    }

    /**
     * Check if the line is equal to another line.
     * @param other The line it's checked with.
     * @return true if the lines are equal. Else will return false.
     */
    public boolean equals(Line other) {
        return ((this.start.equals(other.start()) && this.end.equals(other.end()))
                || (this.start.equals(other.end()) && this.end.equals(other.start())));
    }

    /**
     * Get the closest intersection with the rectangle to the start of the line.
     * @param rect the rectangle checked
     * @return If no intersections, return null. Else returns the closest one to the start of the line.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        java.util.ArrayList<Point> inters = rect.intersectionPoints(this);

        // If there is no intersection with the rectangle, it will return null:
        if (inters.isEmpty()) {
            return null;
        }

        // It will check which point is closest to the start of the line.
        Point closest = new Point(inters.get(0));
        for (int i = 1; i < inters.size(); i++) {
            if (inters.get(i).distance(this.start) < closest.distance(this.start)) {
                closest = new Point(inters.get(i));
            }
        }

        return closest;
    }

    /**
     * Check if a specific point is on this line.
     * @param p the point checked.
     * @return true if p is on the line. Else will return false.
     */
    public boolean isOnLine(Point p) {
        if (p.getY() == this.getEquationY(p.getX()) && this.isBetween(p)) {
            return true;
        }
        return false;
    }
}