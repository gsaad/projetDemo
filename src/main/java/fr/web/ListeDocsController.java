package fr.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.persistence.domain.Document;
import fr.service.DocumentService;
import fr.service.UserService;

@Controller
@RequestMapping("/document/listeDocs")
public class ListeDocsController {

	@Autowired
	private UserService userService;

	@Autowired
	private DocumentService documentService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView mv = new ModelAndView("welcomeListeDocs");
		List<Document> listDocs = documentService
				.findListDocumentByLogin(userService.getCurrentUser()
						.getLogin());
		if (listDocs == null) {
			mv.addObject("listDocs", new ArrayList<Document>());
		} else {
			mv.addObject("listDocs", listDocs);
		}
		return mv;
	}
}
