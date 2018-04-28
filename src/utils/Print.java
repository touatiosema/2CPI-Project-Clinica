package utils;

import core.App;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.util.HashMap;

public class Print {

    // print and show printing parametres(job.showPageSetupDialog()).
    public static void print(Stage window, Node node){



        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A5, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
        PrinterJob job = PrinterJob.createPrinterJob();
        JobSettings settings = job.getJobSettings();
        settings.setJobName("Document");
        double scaleX = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / (node.getBoundsInParent().getHeight() + 30);
        Scale scale = new Scale(scaleX, scaleY);
        node.getTransforms().add(scale);

        window.setIconified(true);

        if (job != null && job.showPrintDialog(null)) {
            boolean success = job.printPage(pageLayout, node);
            if (success) {
                job.endJob();
            }
        }
        node.getTransforms().remove(scale);
        window.setIconified(false);
    }

    // print directly your node without showing printing parameteres
    public static void print(Node node, double up, double down, double left, double right, Paper paper,
                             PageOrientation pageOrientation){

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job == null) {
            return;
        }
        JobSettings jobSettings = job.getJobSettings();
        javafx.print.Printer printer=job.getPrinter();
        PageLayout pageLayout = printer.createPageLayout(paper, pageOrientation, left, right,up, down);

        JobSettings settings = job.getJobSettings();
        settings.setJobName("Document");
        double scaleX = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / (node.getBoundsInParent().getHeight() + 30);

        Scale scale = new Scale(scaleX, scaleY);
        node.getTransforms().add(scale);


        jobSettings.setPageLayout(pageLayout);
            boolean printed = job.printPage(node);
            if (printed)
                job.endJob();

    }

    // mpreview a node and give the possibility to print.
    public static void preview(Node node) {
        App.newWindow("Preview", new HashMap() {{
            put("node", node);
        }});
    }

}

