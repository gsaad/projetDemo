package fr.web;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.amazonaws.services.s3.model.S3Object;

import fr.persistence.domain.Document;
import fr.service.BusinessServiceException;
import fr.service.DocumentService;
import fr.service.UserService;
import fr.web.form.DocumentForm;
 
@Controller
@RequestMapping("/document")
public class DocController {
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/files", method = RequestMethod.GET)
	@ResponseBody
	public void getFile(@RequestParam Integer idFile,
			HttpServletResponse resp) throws BusinessServiceException, IOException {
		S3Object objectS3 = documentService.loadDocument(idFile, userService
				.getCurrentUser().getLogin());
		InputStream inputStream = objectS3.getObjectContent();
		OutputStream outputStream = null;
		String mimetype = objectS3.getObjectMetadata().getContentType();
		resp.setContentType(mimetype);
		outputStream = resp.getOutputStream();
		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment; filename=\""
				+ objectS3.getKey() + "\"");
		byte[] buffer = new byte[1024];
		int read = 0;
		while ((read = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, read);
		}
		outputStream.flush();
		Long fileSize = objectS3.getObjectMetadata().getContentLength();
		resp.setContentLength(fileSize.intValue());
	}

		
	@RequestMapping(value="/addDocForm", method = RequestMethod.GET)
	public String addDocument(ModelMap model) {
		model.addAttribute("typeDocuments", documentService.findAllTypeDocument());
		model.addAttribute(new DocumentForm());
		return "addDoc";
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.GET)
	public String deleteDocument(@RequestParam int idDocument, RedirectAttributes redirectAttributes) throws BusinessServiceException {
		Document doc = documentService.getDocument(idDocument, userService.getCurrentUser().getLogin());
		if(doc!=null){
			redirectAttributes.addFlashAttribute("messageConfirmation", this.messageSource.getMessage(
					"document.confirmation.suppression",
					new Object[] { doc
							.getIntitule() }, null));
			documentService.removeDocument(doc);
			return "redirect:/document/listeDocs";
		}
		throw new BusinessServiceException("Erreur technique, impossible de supprimer le document");
	}
	
	@RequestMapping(value = "/addDocForm", method = RequestMethod.POST)
	public ModelAndView nouveauDocument(DocumentForm documentForm, RedirectAttributes redirectAttributes) throws BusinessServiceException {
		if(StringUtils.isEmpty(documentForm.getIntituleDocument()) ||
				documentForm.getIdTypeDocument() == null ||
				documentForm.getFileData() == null ||
				StringUtils.isEmpty(documentForm.getPeriode()) ||
				documentForm.getFileData().getSize() ==0 ){
			ModelAndView mav = new ModelAndView("redirect:/document/addDocForm");
			redirectAttributes.addFlashAttribute("messageErreur", this.messageSource.getMessage("user.info.champs.obligatoire", null, null));
			return mav;
		}else{
			documentForm.setUser(userService.getCurrentUser());
			documentService.addDocument(documentForm);
			ModelAndView mav = new ModelAndView("redirect:/document/listeDocs");
			redirectAttributes.addFlashAttribute("messageConfirmation",
					this.messageSource
							.getMessage("document.confirmation.ajout",
									new Object[] { documentForm
											.getIntituleDocument() }, null));
			return mav;
		}
	}
}
