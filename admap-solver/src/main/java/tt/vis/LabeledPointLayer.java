package tt.vis;import java.awt.BasicStroke;import java.awt.Color;import java.awt.Graphics2D;import java.util.Collection;import javax.vecmath.Point2d;import cz.agents.alite.vis.Vis;import cz.agents.alite.vis.layer.AbstractLayer;import cz.agents.alite.vis.layer.VisLayer;public class LabeledPointLayer<P> extends AbstractLayer {    static public class LabeledPoint<PP> {        PP point;        String label;        public LabeledPoint(PP point, String label) {            super();            this.point = point;            this.label = label;        }    }    static public interface LabeledPointsProvider<PP> {        Collection<LabeledPoint<PP>> getLabeledPoints();    }    LabeledPointsProvider<P> provider;    private Color color;    private ProjectionTo2d<P> projection;    LabeledPointLayer(LabeledPointsProvider<P> pointProvider, ProjectionTo2d<P> projection, Color color) {        this.provider = pointProvider;        this.projection = projection;        this.color = color;    }    @Override    public void paint(Graphics2D canvas) {        canvas.setStroke(new BasicStroke(1));        canvas.setColor(color);        for (LabeledPoint<P> p: provider.getLabeledPoints()) {            Point2d point2d = projection.project(p.point);            canvas.drawOval(Vis.transX(point2d.x)-3, Vis.transY(point2d.y)-3, 6, 6);            int x = Vis.transX(point2d.x);            int y = Vis.transY(point2d.y);            for (String line : p.label.split("\n")) {               canvas.drawString(line, x, y += canvas.getFontMetrics().getHeight());            }        }    }    @Override    public String getLayerDescription() {        String description = "Layer shows points and labels associated to them.";        return buildLayersDescription(description);    }    public static <P> VisLayer create(LabeledPointsProvider<P> pointProvider, ProjectionTo2d<P> projection, Color color) {        return new LabeledPointLayer<P>(pointProvider, projection, color);    }}