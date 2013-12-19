package fr.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

import fr.service.BusinessServiceException;

@ControllerAdvice
@EnableWebMvc
public class ExceptionController {
	
	@ExceptionHandler(BusinessServiceException.class)
	public RedirectView  handleBusinessServiceException(BusinessServiceException exception,   HttpServletRequest request) {
		RedirectView rw = new RedirectView("listeDocs");
		FlashMap outputFlashMap = RequestContextUtils
				.getOutputFlashMap(request);
		if (outputFlashMap != null) {
			outputFlashMap.put("messageErreur", exception.getMessage());
		}
		return rw;
	}
	
	
	@ExceptionHandler({AmazonServiceException.class, AmazonClientException.class})
	public RedirectView  handleAmazonException(Exception exception, HttpServletRequest request) {
		RedirectView rw = new RedirectView("listeDocs");
		FlashMap outputFlashMap = RequestContextUtils
				.getOutputFlashMap(request);
		if (outputFlashMap != null) {
			outputFlashMap.put("messageErreur", "Impossible d'accéder au service de stockage");
		}
		return rw;
	}
	@ExceptionHandler({MaxUploadSizeExceededException.class})
	public RedirectView  handleMaxUploadSizeExceededException(MaxUploadSizeExceededException exception, HttpServletRequest request) {
		RedirectView rw = new RedirectView("addDocForm");
		FlashMap outputFlashMap = RequestContextUtils
				.getOutputFlashMap(request);
		if (outputFlashMap != null) {
			outputFlashMap.put("messageErreur", "Veuillez saisir un fichier de moins de : "+ (exception.getMaxUploadSize()/(1000*1000)) + "Mo");
		}
		return rw;
	}
	
}
