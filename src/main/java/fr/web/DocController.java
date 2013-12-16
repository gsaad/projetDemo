package fr.web;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
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
	public ModelAndView getFile(@RequestParam Integer idFile,
			HttpServletResponse resp, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("redirect:/listeDocs");
		try {
			S3Object objectS3 = documentService.loadDocument(idFile, userService.getCurrentUser().getLogin());
			if (objectS3 == null) {
				redirectAttributes.addFlashAttribute("messageErreur", "fichier.download.erreur");
				return mv;
			}
			InputStream inputStream = objectS3.getObjectContent();
			OutputStream outputStream = null;
			String mimetype = objectS3.getObjectMetadata().getContentType();
			resp.setContentType(mimetype);
			outputStream = resp.getOutputStream();
			resp.setContentType("application/octet-stream");
			resp.setHeader("Content-Disposition", "attachment; filename=\"" + objectS3.getKey() + "\"");
			byte[] buffer = new byte[1024];
			int read = 0;
			while ((read = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, read);
			}
			outputStream.flush();
			Long fileSize = objectS3.getObjectMetadata().getContentLength();
			resp.setContentLength(fileSize.intValue());
		}catch (IOException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute(
			"messageErreur", "fichier.download.erreur");
			return mv;
		}
		return null;
	}

		
	@RequestMapping(value="/addDocForm", method = RequestMethod.GET)
	public String addDocument(ModelMap model) {
		model.addAttribute("typeDocuments", documentService.findAllTypeDocument());
		model.addAttribute(new DocumentForm());
		return "addDoc";
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.GET)
	public String deleteDocument(@RequestParam int idDocument, RedirectAttributes redirectAttributes) {
		Document doc = documentService.getDocument(idDocument, userService.getCurrentUser().getLogin());
		if(doc!=null){
			redirectAttributes.addFlashAttribute("messageConfirmation", this.messageSource.getMessage(
					"document.confirmation.suppression",
					new Object[] { doc
							.getIntitule() }, null));
			documentService.removeDocument(doc);
			return "redirect:/listeDocs";
		}else{
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/addDocForm", method = RequestMethod.POST)
	public ModelAndView nouveauDocument(DocumentForm documentForm, RedirectAttributes redirectAttributes) {
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
			try {
				documentService.addDocument(documentForm);
				ModelAndView mav = new ModelAndView("redirect:/listeDocs");
				redirectAttributes.addFlashAttribute("messageConfirmation",
						this.messageSource.getMessage(
								"document.confirmation.ajout",
								new Object[] { documentForm
										.getIntituleDocument() }, null));
				return mav;
			} catch (IOException e) {
				e.printStackTrace();
				ModelAndView mav = new ModelAndView("redirect:/document/addDocForm");
				redirectAttributes.addFlashAttribute("messageErreur", this.messageSource.getMessage(
						"fichier.download.erreur",
						new Object[] { documentForm
								.getIntituleDocument()}, null));
				return mav;
			}
		}
	}
}
