package info.pravasa.application.utils;

import com.vaadin.flow.component.html.Span;

public class Icons {

    private static final String CHARGING_STATION_ICON = "fa-solid fa-charging-station";
    private static final String GAS_PUMP = "fa-solid fa-gas-pump";


    public static Span getIcon(String iconName){
        Span span = new Span();
        switch (iconName) {
            case "CHARGING_STATION" -> {
                span.getElement().setAttribute("class", CHARGING_STATION_ICON);
                span.getElement().getStyle().set("color", "green");
            }
            case "CNG_REFUELLING" -> {
                span.getElement().setAttribute("class", GAS_PUMP);
                span.getElement().getStyle().set("color", "blue");
            }
            case "DIESEL_REFUELLING" -> {
                span.getElement().setAttribute("class", GAS_PUMP);
                span.getElement().getStyle().set("color", "black");
            }
        }
        return span;
    }

}
