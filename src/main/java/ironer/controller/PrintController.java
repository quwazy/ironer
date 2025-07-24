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

            // Define relative widths for columns
            float[] columnWidths = new float[tableView.getColumns().size()];
            for (int i = 0; i < tableView.getColumns().size(); i++) {
                TableColumn<Iron, ?> column = tableView.getColumns().get(i);

                // Assign widths based on column title or type
                if ("Skica".equals(column.getText())) {
                    columnWidths[i] = 4.0f; // Wider for the "Skica" column
                } else if ("fi".equals(column.getText())) {
                    columnWidths[i] = 1.0f; // Narrower for the "fi" column
                } else {
                    columnWidths[i] = 2.0f; // Default for other columns
                }
            }

            // Apply column widths to the table
            pdfTable.setWidths(columnWidths);

            // Add rows with data (drawings, text, etc.)
            for (Iron iron : tableView.getItems()) {
                for (TableColumn<Iron, ?> column : tableView.getColumns()) {
                    if ("Skica".equals(column.getText())) {
                        // Add drawing for "Skica" column
                        PdfPCell drawingCell = new PdfPCell();
                        if (iron.getDraw() != null) {
                            // Create image from JavaFX drawing
                            Group drawing = iron.getDraw();
                            Scene scene = new Scene(new StackPane(drawing), 250, 100);
                            WritableImage image = new WritableImage(250, 100);
                            scene.snapshot(image);

                            // Convert WritableImage to BufferedImage
                            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

                            // Convert BufferedImage to iText Image
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            ImageIO.write(bufferedImage, "png", baos);
                            Image pdfImage = Image.getInstance(baos.toByteArray());
                            pdfImage.scaleToFit(230, 60);
                            pdfImage.setBackgroundColor(BaseColor.WHITE);

                            drawingCell.addElement(pdfImage);
                            drawingCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            drawingCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            drawingCell.setPadding(5);
                        }
                        pdfTable.addCell(drawingCell);
                    } else {
                        // For other columns, add textual or numeric content
                        Object cellValue = column.getCellData(iron);
                        PdfPCell cell = new PdfPCell(new Phrase(cellValue != null ? cellValue.toString() : ""));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfTable.addCell(cell);
                    }
                }
            }

            document.add(pdfTable);

            //Total view
            PdfPTable pdfTotalTable = new PdfPTable(3);
            pdfTotalTable.setWidthPercentage(100);
            pdfTotalTable.setWidths(new float[]{2.0f, 2.0f, 2.0f});

            PdfPCell headerUzengije = new PdfPCell(new Phrase("Total Uzengije"));
            headerUzengije.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerUzengije.setBackgroundColor(BaseColor.LIGHT_GRAY);

            PdfPCell headerSipke = new PdfPCell(new Phrase("Total Sipke"));
            headerSipke.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerSipke.setBackgroundColor(BaseColor.LIGHT_GRAY);

            PdfPCell headerVezano = new PdfPCell(new Phrase("Total Vezano"));
            headerVezano.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerVezano.setBackgroundColor(BaseColor.LIGHT_GRAY);

            pdfTotalTable.addCell(headerUzengije);
            pdfTotalTable.addCell(headerSipke);
            pdfTotalTable.addCell(headerVezano);

            PdfPCell uzengijeCell = new PdfPCell(new Phrase(String.format("%.2f", totalG) + " kg"));
            uzengijeCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            pdfTotalTable.addCell(uzengijeCell);

            PdfPCell sipkeCell = new PdfPCell(new Phrase(String.format("%.2f", totalR) + " kg"));
            sipkeCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            pdfTotalTable.addCell(sipkeCell);

            PdfPCell vezanoCell = new PdfPCell(new Phrase(String.format("%.2f", totalV) + " kg"));
            vezanoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            pdfTotalTable.addCell(vezanoCell);

            document.add(pdfTotalTable);
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
