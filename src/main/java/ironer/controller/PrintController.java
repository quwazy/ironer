package ironer.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import ironer.model.irons.Iron;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class PrintController {

    public void exportTableViewToPdf(TableView<Iron> tableView, String filePath, double totalG, double totalR, double totalV) {
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Add title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Iron Order Details", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ")); // Add some space

            // Create PDF table with all columns (including drawing)
            PdfPTable pdfTable = new PdfPTable(tableView.getColumns().size());
            pdfTable.setWidthPercentage(100);

            // Add headers for all columns
            for (TableColumn<Iron, ?> column : tableView.getColumns()) {
                PdfPCell header = new PdfPCell(new Phrase(column.getText()));
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                pdfTable.addCell(header);
            }

            // Add rows with drawings
            for (Iron iron : tableView.getItems()) {
                // Add drawing
                PdfPCell drawingCell = new PdfPCell();
                if (iron.getDraw() != null) {
                    // Create image from JavaFX drawing
                    Group drawing = iron.getDraw();
                    // Need to place in scene to render correctly
                    Scene scene = new Scene(new StackPane(drawing), 100, 120);
                    WritableImage image = new WritableImage(100, 120);
                    scene.snapshot(image);

                    // Convert WritableImage to BufferedImage
                    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

                    // Convert BufferedImage to iText Image
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(bufferedImage, "png", baos);
                    Image pdfImage = Image.getInstance(baos.toByteArray());
                    pdfImage.scaleToFit(80, 60);

                    drawingCell.addElement(pdfImage);
                    drawingCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    drawingCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    drawingCell.setPadding(5);
                }
                pdfTable.addCell(drawingCell);

                // Add other fields
                pdfTable.addCell(new Phrase(iron.getIronType().toString()));
                pdfTable.addCell(new Phrase(String.format("%.2f", iron.getLength())));
                pdfTable.addCell(new Phrase(String.valueOf(iron.getAmount())));
                pdfTable.addCell(new Phrase(String.format("%.2f", iron.getWeight())));
            }

            document.add(pdfTable);

            // Add footer with totals
            document.add(new Paragraph(" ")); // Add some space
            document.add(new Paragraph("Total Uzengije: " + totalG + " kg"));
            document.add(new Paragraph("Total Sipke: " + totalR + " kg"));
            document.add(new Paragraph("Total Vezano: " + totalV + " kg"));

            document.close();

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Export Successful");
            alert.setHeaderText(null);
            alert.setContentText("Table has been exported to PDF successfully!");
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();

            // Show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Export Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while exporting to PDF: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
