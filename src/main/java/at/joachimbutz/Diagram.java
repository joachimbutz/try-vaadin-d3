package at.joachimbutz;

import java.util.List;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

@JavaScript({"https://d3js.org/d3.v3.min.js", "diagram_connector.js"})
public class Diagram extends AbstractJavaScriptComponent {

	public void setCoords(final List<Integer> coords) {
		getState().setCoords(coords);
	}
	
	public DiagramState getState() {
		return (DiagramState) super.getState();
	}
}
