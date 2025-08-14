package ironer.controller;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
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
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

public class SaveController {

    public void saveOrder(String identifier, TableView<Iron> tableView, double totalG, double totalR, double totalV, String orderData, String orderer){
        String userHome = System.getProperty("user.home"); // Get the user's home directory
        String ironerPath;

        // Determine the directory based on the operating system
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            ironerPath = "C://Users/janko/Documents/Ironer";
        } else {
            ironerPath = userHome + "/Documents/Ironer";
        }
        File directory = new File(ironerPath);
        if (!directory.exists()) {
            boolean isCreated = directory.mkdirs();
            if (!isCreated) {
                new WarningController("Failed to create the directory: " + ironerPath);
                return;
            }
        }

        // Construct the file path for the PDF
        String name = identifier;
        if (name.equalsIgnoreCase("RN")){
            name = "RN-" + (LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyMMddHHmms")));
        }
        File file = new File(directory, name+".pdf");
        try {
            if (file.createNewFile()) {
                exportTableViewToPdf(tableView, file.getAbsolutePath(), totalG, totalR, totalV, name, orderData, orderer);
            } else {
                new WarningController("File already exists.");
            }
        } catch (IOException e) {
            new WarningController("Failed to create or access the file.");
        }
    }

    private void exportTableViewToPdf(TableView<Iron> tableView, String filePath, double totalG, double totalR, double totalV, String orderIdentifier, String orderData, String orderer) {
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Add title
            PdfPTable titleTable = new PdfPTable(3);
            titleTable.setWidthPercentage(100);
            titleTable.setWidths(new float[]{2.0f, 2.0f, 2.0f});
            titleTable.setSpacingBefore(10);

            Font subTitleFont = new Font(StandardFonts.HELVETICA_BOLD.create(9));
            subTitleFont.setColor(Color.GRAY);
            Paragraph subTitle = new Paragraph("Nikolaja Saltikova 8, Zemun\ntelefon: 011/314-1092\nemail: ctpristic.bgd@gmail.com", subTitleFont);
            subTitle.setAlignment(Element.ALIGN_LEFT);
            PdfPCell leftCell = new PdfPCell();
            leftCell.addElement(subTitle);
            leftCell.setBorder(Rectangle.NO_BORDER);

            Font titleFont = new Font(StandardFonts.HELVETICA_BOLD.create(18));
            Paragraph title = new Paragraph("CTP Ristic D.O.O.", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            PdfPCell titleCell = new PdfPCell();
            titleCell.addElement(title);
            titleCell.setBorder(Rectangle.NO_BORDER);

            PdfPCell rightCell = new PdfPCell(new Phrase(""));
            rightCell.setBorder(Rectangle.NO_BORDER);

            titleTable.addCell(leftCell);
            titleTable.addCell(titleCell);
            titleTable.addCell(rightCell);
            document.add(titleTable);
            document.add(new Paragraph(" "));

            // Info table
            PdfPTable infoTable = new PdfPTable(3);
            infoTable.setWidthPercentage(100);
            infoTable.setWidths(new float[]{2.0f, 2.0f, 2.0f});
            infoTable.setSpacingBefore(10);

            PdfPCell orderIdentifierCell = new PdfPCell(new Phrase("Oznaka: " + orderIdentifier));
            orderIdentifierCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            orderIdentifierCell.setBorder(Rectangle.NO_BORDER);
            orderIdentifierCell.setBorderWidthBottom(1f);

            PdfPCell emptyCell = new PdfPCell(new Phrase(" "));
            emptyCell.setBorder(Rectangle.NO_BORDER);

            PdfPCell ordererCell = new PdfPCell(new Phrase("Narucilac: " + orderer));
            ordererCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            ordererCell.setBorder(Rectangle.NO_BORDER);
            ordererCell.setBorderWidthBottom(1f);

            PdfPCell dateCell = new PdfPCell(new Phrase("Datum: " + orderData));
            dateCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            dateCell.setBorder(Rectangle.NO_BORDER);
            dateCell.setBorderWidthBottom(1f);

            PdfPCell noteCell = new PdfPCell(new Phrase("Napomena: "));
            noteCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            noteCell.setBorder(Rectangle.NO_BORDER);
            noteCell.setBorderWidthBottom(1f);

            infoTable.addCell(orderIdentifierCell);
            infoTable.addCell(emptyCell);
            infoTable.addCell(ordererCell);
            infoTable.addCell(dateCell);
            infoTable.addCell(emptyCell);
            infoTable.addCell(noteCell);
            document.add(infoTable);
            document.add(new Paragraph(" "));

            // Create PDF table with all columns (including drawing)
            PdfPTable pdfTable = new PdfPTable(tableView.getColumns().size());
            pdfTable.setWidthPercentage(100);

            for (TableColumn<Iron, ?> column : tableView.getColumns()) {
                PdfPCell header = new PdfPCell(new Phrase(column.getText()));
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setBackgroundColor(Color.LIGHT_GRAY);
                pdfTable.addCell(header);
            }

            // Define relative widths for columns
            float[] columnWidths = new float[tableView.getColumns().size()];
            for (int i = 0; i < tableView.getColumns().size(); i++) {
                TableColumn<Iron, ?> column = tableView.getColumns().get(i);
                if ("".equals(column.getText())){
                    continue;
                }
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
                            pdfImage.setBackgroundColor(Color.WHITE);

                            drawingCell.addElement(pdfImage);
                            drawingCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            drawingCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            drawingCell.setPadding(5);
                        }
                        pdfTable.addCell(drawingCell);
                    } else {
                        Object cellValue = column.getCellData(iron);
                        PdfPCell cell = new PdfPCell(new Phrase(cellValue != null ? cellValue.toString() : ""));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        pdfTable.addCell(cell);
                    }
                }
            }

            document.add(pdfTable);
            Paragraph blank = new Paragraph(" ");
            document.add(blank);

            //Total view
            PdfPTable pdfTotalTable = new PdfPTable(3);
            pdfTotalTable.setWidthPercentage(100);
            pdfTotalTable.setWidths(new float[]{2.0f, 2.0f, 2.0f});

            PdfPCell headerUzengije = new PdfPCell(new Phrase("Total Uzengije:"));
            headerUzengije.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerUzengije.setBackgroundColor(Color.LIGHT_GRAY);

            PdfPCell headerSipke = new PdfPCell(new Phrase("Total Sipke:"));
            headerSipke.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerSipke.setBackgroundColor(Color.LIGHT_GRAY);

            PdfPCell headerVezano = new PdfPCell(new Phrase("Total Vezano:"));
            headerVezano.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerVezano.setBackgroundColor(Color.LIGHT_GRAY);

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
            new WarningController("Failed to export to PDF. An error occurred while exporting to PDF: " + e.getMessage());
        }
    }
}
