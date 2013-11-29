package ch.itraum.recruiter.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;
import org.jpedal.fonts.FontMappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.itraum.recruiter.repository.DocumentRepository;

@Controller
public class ThumbnailController {
	
	@Autowired
	private DocumentRepository documentRepository;

	@ResponseBody
	@RequestMapping(value = "/png", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] png() throws IOException {
		
		ByteArrayInputStream inputStream = new ByteArrayInputStream(documentRepository.findOne(1).getContent());
		
		PDDocument pdf = PDDocument.load(inputStream);
		PDPage page = (PDPage) pdf.getDocumentCatalog().getAllPages().get(0);
		
		BufferedImage image = page.convertToImage();
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "png", outputStream);
		
		return outputStream.toByteArray();
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/png2", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] png2() throws IOException, PdfException {
		
		PdfDecoder pdfDecoder = new PdfDecoder(true);
		
		FontMappings.setFontReplacements();
		
		pdfDecoder.openPdfArray(documentRepository.findOne(1).getContent());
		
		BufferedImage image = pdfDecoder.getPageAsImage(1);
		
		pdfDecoder.closePdfFile();
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "png", outputStream);
		
		return outputStream.toByteArray();
		
	}
}
