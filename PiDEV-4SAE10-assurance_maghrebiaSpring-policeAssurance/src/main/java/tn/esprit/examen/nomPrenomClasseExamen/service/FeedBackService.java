package tn.esprit.examen.nomPrenomClasseExamen.service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FeedBack;
import tn.esprit.examen.nomPrenomClasseExamen.repository.FeedBackRepository;

import java.awt.*;
import java.awt.Color;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



@Service
@RequiredArgsConstructor
public class FeedBackService implements IFeedbackService {

    @Autowired
    private final FeedBackRepository feedbackRepo;
    @Autowired
    private FeedBackRepository feedBackRepository;

    @Override
    public FeedBack addFeedBack(FeedBack feedback) {
        if (feedback.getSubmissionDate() == null) {
            feedback.setSubmissionDate(LocalDateTime.now());
        }

        // Toujours analyser le sentiment, même si déjà fourni
        feedback.setSentimentAnalysis(analyzeSentiment(feedback.getDescription()));

        return feedbackRepo.save(feedback);
    }

    @Override
    public Optional<FeedBack> getFeedBackById(Long id) {
        return feedbackRepo.findById(id);
    }

    @Override
    public List<FeedBack> getAllFeedBacks() {
        return feedbackRepo.findAll();
    }

    @Override
    public FeedBack updateFeedBack(Long id, FeedBack feedback) {
        return feedbackRepo.findById(id).map(existingFeedBack -> {
            existingFeedBack.setDescription(feedback.getDescription());
            existingFeedBack.setSatisfactionScore(feedback.getSatisfactionScore());
            existingFeedBack.setProductService(feedback.getProductService());

            // Re-analyse du sentiment à partir de la nouvelle description
            existingFeedBack.setSentimentAnalysis(analyzeSentiment(feedback.getDescription()));

            return feedbackRepo.save(existingFeedBack);
        }).orElseThrow(() -> new IllegalArgumentException("Feedback non trouvé."));
    }

    @Override
    public void deleteFeedBack(Long id) {
        feedbackRepo.deleteById(id);
    }

    @Override
    public String analyzeSentiment(String text) {
        try {
            File scriptFile = new File("PiDEV-4SAE10-assurance_maghrebiaSpring-policeAssurance/src/main/resources/python/sentiment_analysis.py");
            String scriptPath = scriptFile.getAbsolutePath();

            String safeText = text.replace("\"", "\\\"").replace("\n", " ");
            ProcessBuilder processBuilder = new ProcessBuilder("python", scriptPath, safeText);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8)
            );

            StringBuilder jsonOutput = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonOutput.append(line.trim());
            }

            process.waitFor();

            String resultJson = jsonOutput.toString();
            if (!resultJson.startsWith("{")) {
                throw new RuntimeException("Invalid JSON returned: " + resultJson);
            }

            JSONObject result = new JSONObject(resultJson);
            System.out.println("result json**********"+resultJson.toString());
            return result.getString("sentiment");

        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    @Override
    public ByteArrayInputStream exportFeedbacksToPDF(List<FeedBack> feedbacks) throws IOException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        // 🔹 Insertion du logo
        try {
            ClassPathResource logoResource = new ClassPathResource("static/logo.png");
            Image logo = Image.getInstance(logoResource.getInputStream().readAllBytes());
            logo.scaleAbsolute(120, 80); // Ajuste la taille du logo si nécessaire
            logo.setAlignment(Image.ALIGN_CENTER);
            document.add(logo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 🔹 Titre
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Color.BLACK);
        Paragraph title = new Paragraph("Rapport des Feedbacks Clients", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // 🔹 Tableau
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{2, 3, 2, 2, 2});
        table.setSpacingBefore(10);

        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        Stream.of("User ID", "Description", "Satisfaction", "Sentiment", "Date")
                .forEach(headerTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(Color.LIGHT_GRAY);
                    header.setPhrase(new Phrase(headerTitle, headFont));
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });

        for (FeedBack feedback : feedbacks) {
            table.addCell(String.valueOf(feedback.getUser() != null ? feedback.getUser().getId() : "N/A"));
            table.addCell(feedback.getDescription());
            table.addCell(String.valueOf(feedback.getSatisfactionScore()));
            table.addCell(feedback.getSentimentAnalysis());
            table.addCell(feedback.getSubmissionDate() != null ? feedback.getSubmissionDate().toString() : "N/A");
        }

        document.add(table);
        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public ByteArrayInputStream exportFeedbacksToExcel(List<FeedBack> feedbacks) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Feedbacks");

            // Style de l'en-tête
            CellStyle headerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            // En-tête
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Feedback ID", "User ID", "Description", "Satisfaction Score", "Sentiment Analysis", "Submission Date"};
            for (int col = 0; col < headers.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(headers[col]);
                cell.setCellStyle(headerStyle);
            }

            // Données
            int rowIdx = 1;
            for (FeedBack feedback : feedbacks) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(feedback.getFeedbackId());
                row.createCell(1).setCellValue(feedback.getUser() != null ? feedback.getUser().getId() : 0);
                row.createCell(2).setCellValue(feedback.getDescription());
                row.createCell(3).setCellValue(feedback.getSatisfactionScore());
                row.createCell(4).setCellValue(feedback.getSentimentAnalysis());
                row.createCell(5).setCellValue(feedback.getSubmissionDate() != null ? feedback.getSubmissionDate().toString() : "N/A");
            }

            // Auto-ajustement des colonnes
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'export Excel : " + e.getMessage());
        }
    }


    public List<FeedBack> rechercheGlobale(String keyword) {
        return feedBackRepository.rechercheGlobale(keyword);
    }


}
