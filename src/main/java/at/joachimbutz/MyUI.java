package at.joachimbutz;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Widgetset("at.joachimbutz.MyAppWidgetset")
public class MyUI extends UI {
	
	final VerticalLayout layout = new VerticalLayout();  
    final TextField xCoordField = new TextField("X");
    final TextField yCoordField = new TextField("Y");
    final Button button = new Button("move circle");
    final Diagram diagram = new Diagram();
    final List<Integer> coords = new ArrayList<>();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        configureIntegerField(xCoordField);     //not interesting, just adding converter/validator to the textFields
        configureIntegerField(yCoordField);
 
        button.addClickListener(new Button.ClickListener() {   //ATTENTION! Here we get the coordinates from the textfields and apply them to our Diagram via calling diagram.setCoords()
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if(xCoordField.isValid() && yCoordField.isValid()){
                    coords.clear();
                    coords.add(Integer.parseInt(xCoordField.getValue()));
                    coords.add(Integer.parseInt(yCoordField.getValue()));
                    diagram.setCoords(coords);
                }
            }
        });
        //now we build the layout.
        layout.setSpacing(true);
        layout.addComponent(xCoordField);
        layout.addComponent(yCoordField);
        layout.addComponent(button);
        layout.addComponent(diagram);     //add the diagram like any other vaadin component, cool!
        setContent(layout);
    }
    
    private void configureIntegerField(final TextField integerField) {
        integerField.setConverter(Integer.class);
        integerField.addValidator(new IntegerRangeValidator("only integer, 0-500", 0, 500));
        integerField.setRequired(true);
        integerField.setImmediate(true);
    }    

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
