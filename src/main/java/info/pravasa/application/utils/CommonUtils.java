package info.pravasa.application.utils;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.VaadinSession;

import java.util.Objects;

public class CommonUtils {

    public static boolean verifyUserLogin() {
        if (Objects.isNull(VaadinSession.getCurrent().getAttribute("token"))) {
            Notification.show("Please Login", 3000, Notification.Position.MIDDLE);
            UI.getCurrent().navigate("/");
        }
        return Objects.nonNull(VaadinSession.getCurrent().getAttribute("token"));
    }
}
