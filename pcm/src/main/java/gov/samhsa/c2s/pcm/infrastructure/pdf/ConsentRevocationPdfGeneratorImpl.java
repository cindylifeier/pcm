package gov.samhsa.c2s.pcm.infrastructure.pdf;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import gov.samhsa.c2s.pcm.domain.Consent;
import gov.samhsa.c2s.pcm.infrastructure.dto.PatientDto;
import gov.samhsa.c2s.pcm.infrastructure.exception.ConsentPdfGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.ByteArrayOutputStream;
import java.util.Date;

@Service
public class ConsentRevocationPdfGeneratorImpl implements ConsentRevocationPdfGenerator {


    @Autowired
    private ITextPdfService iTextPdfService;

    @Override
    public byte[] generateConsentRevocationPdf(Consent consent, PatientDto patient, Date attestedOnDateTime, String consentRevocationTerm) {
        Assert.notNull(consent, "Consent is required.");

        Document document = new Document();

        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, pdfOutputStream);

            document.open();

            // Title
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
            document.add(iTextPdfService.createParagraphWithContent("Revocation of Consent to Participate in Health Information Exchange", titleFont));

            // Blank line
            document.add(Chunk.NEWLINE);

            //consent Reference Number
            document.add(iTextPdfService.createConsentReferenceNumberTable(consent));

            document.add(new Paragraph(" "));

            //Patient Name and date of birth
            document.add(iTextPdfService.createPatientNameAndDOBTable(patient.getFirstName(), patient.getLastName(), patient.getBirthDate()));

            document.add(new Paragraph(" "));

            document.add(new Paragraph(consentRevocationTerm));

            document.add(new Paragraph(" "));

            //Signing details
            document.add(iTextPdfService.createSigningDetailsTable(patient.getFirstName(), patient.getLastName(), patient.getEmail(), true, attestedOnDateTime));

            document.close();

        } catch (Throwable e) {
            throw new ConsentPdfGenerationException("Exception when trying to generate pdf", e);
        }

        byte[] pdfBytes = pdfOutputStream.toByteArray();

        return pdfBytes;
    }

}