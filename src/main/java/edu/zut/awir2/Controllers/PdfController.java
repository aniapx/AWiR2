package edu.zut.awir2.Controllers;

import edu.zut.awir2.Models.PdfFile;
import edu.zut.awir2.Services.PdfFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/pdf")
public class PdfController {
    private final PdfFileService pdfFileService;

    @Autowired
    public PdfController(PdfFileService pdfFileService) {
        this.pdfFileService = pdfFileService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> downloadPdfFile(@PathVariable("id") Long id) {
        Optional<PdfFile> pdfFileOptional = pdfFileService.getPdfFileById(id);

        if (pdfFileOptional.isPresent()) {
            PdfFile pdfFile = pdfFileOptional.get();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData(pdfFile.getFileName(), pdfFile.getFileName());
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(pdfFile.getData(), headers, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }
}