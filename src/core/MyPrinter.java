package core;

import controllers.PreviewController;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyPrinter {
    //this methode prints and shows printing parametres(job.showPageSetupDialog()).
    public static void print(Stage window, Node node){
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job == null)
            return;
        boolean proceed = job.showPageSetupDialog(window);
        if (proceed) {
            boolean printed = job.printPage(node);
            if (printed)
                job.endJob();
        }
    }
//this methode prints directly your node without showing printing parameteres
    public static void print(Node node, double up, double down, double left, double right, Paper paper,
                             PageOrientation pageOrientation){

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job == null) {
            return;
        }
        JobSettings jobSettings = job.getJobSettings();
        Printer printer=job.getPrinter();
        PageLayout pageLayout = printer.createPageLayout(paper, pageOrientation,left, right,up, down);
        jobSettings.setPageLayout(pageLayout);
            boolean printed = job.printPage(node);
            if (printed)
                job.endJob();

    }
//this methode previews a node and give the possibility to print.
    public static void preview(Node node) throws IOException{
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(MyPrinter.class.getResource("../views/Preview.fxml"));
        Parent root = loader.load();
        PreviewController previewController = loader.getController();
        System.out.println(previewController);
        previewController.putNode(node);
        myStage.setTitle("Apercue Avant Impression : ");
        myStage.initModality(Modality.APPLICATION_MODAL);
        myStage.setMaximized(true);
        myStage.setScene(new Scene(root));
        myStage.show();


    }
//this methode exports a node to pdf.
    public static void nodeToPdf(Node node , String name){
        WritableImage nodeshot = node.snapshot(new SnapshotParameters(), null);
        File file = new File("node.png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(nodeshot, null), "png", file);
        } catch (IOException e) {

        }

        PDDocument doc    = new PDDocument();
        PDPage page = new PDPage();
        PDImageXObject pdimage;
        PDPageContentStream content;
        try {
            pdimage = PDImageXObject.createFromFile("node.png",doc);
            content = new PDPageContentStream(doc, page);
            content.drawImage(pdimage, 100, 100);
            content.close();
            doc.addPage(page);
            doc.save(name+".pdf");
            doc.close();
            file.delete();
        } catch (IOException ex) {
            Logger.getLogger(MyPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

