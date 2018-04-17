package utils;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class ActionButton extends Button {
    public ActionButton(TableCell cell, String action, FontAwesomeIcon icon, String color) {
        FontAwesomeIconView icon_view = new FontAwesomeIconView(icon);
        icon_view.setScaleX(1.5);
        icon_view.setScaleY(1.5);
        icon_view.setStyle("-fx-fill :" + color + ";" + icon_view.getStyle());
        icon_view.setEffect(new DropShadow(10, new Color(1, 1, 1, 1)));
        setGraphic(icon_view);

        setStyle("-fx-cursor: hand;-fx-background-color: transparent");
        setOnAction((ActionEvent event) -> {
            Object object = cell.getTableView().getItems().get(cell.getIndex());
            onAction(object);
        });

        Tooltip tt = new Tooltip();
        tt.setText(action);
        setTooltip(tt);
    }

    public void onAction(Object object) {

    }
}