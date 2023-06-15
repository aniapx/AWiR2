package edu.zut.awir2.Services;

import edu.zut.awir2.Models.PdfFile;
import edu.zut.awir2.Repository.PdfFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PdfFileService {
    private final PdfFileRepository pdfFileRepository;

    @Autowired
    public PdfFileService(PdfFileRepository pdfFileRepository) {
        this.pdfFileRepository = pdfFileRepository;
    }

    public Optional<PdfFile> getPdfFileById(Long id) {
        return pdfFileRepository.findById(id);
    }

    public PdfFile savePdfFile(String fileName, byte[] fileData) {
        PdfFile pdfFile = new PdfFile();
        pdfFile.setFileName(fileName);
        pdfFile.setData(fileData);
        return pdfFileRepository.save(pdfFile);
    }

    public void deletePdfFileById(Long id) {
        pdfFileRepository.deleteById(id);
    }
}
