package fr.web;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	@Value("${file.path}")
	private String PATH_FILE;
	
	@RequestMapping(value = "/files", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getFile(@RequestParam Integer idFile, HttpServletResponse resp, HttpServletRequest request, RedirectAttributes redirectAttributes){
		Document document = documentService.getDocument(idFile, userService
				.getCurrentUser().getLogin());
		ModelAndView mv = new ModelAndView("redirect:/listeDocs");
		if(document == null){
			redirectAttributes.addFlashAttribute("messageErreur", "fichier.download.erreur");
			return mv;
		}
		File file = new File(PATH_FILE + document.getNomFichier());
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			
			inputStream = new FileInputStream(file);
			String mimetype =  request.getSession().getServletContext().getMimeType(file.getName());
		    resp.setContentType(mimetype);
			outputStream = resp.getOutputStream();
			resp.setContentType("application/octet-stream"); // .exe file
			resp.setHeader("Content-Disposition", "attachment; filename=\""+ document.getNomFichier() + "\"");
			byte[] buffer = new byte[1024];
			int read = 0;
			while ((read = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, read);
			}
			outputStream.flush();
			
			Long fileSize = file.length();
			resp.setContentLength(fileSize.intValue());
		} catch (IOException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("messageErreur", "fichier.download.erreur");
			return mv;
		}finally{
			//TODO
			//close input/output
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
	
	@RequestMapping("/updateForm")
	public String updateForm(ModelMap model) {
		model.addAttribute("messageConfirmation", this.messageSource.getMessage(
				"document.confirmation.modification",
				new Object[] { }, null));
		return "redirect:/listeDocs";
	}
	
	@RequestMapping(value = "/addDocForm", method = RequestMethod.POST)
	public ModelAndView nouveauDocument(DocumentForm documentForm, RedirectAttributes redirectAttributes) {
		if(StringUtils.isEmpty(documentForm.getIntituleDocument()) ||
				documentForm.getIdTypeDocument() == null ||
				documentForm.getFileData() == null ||
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
								.getIntituleDocument() }, null));
				return mav;
			}
		}
	}
}
