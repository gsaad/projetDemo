package fr.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

import fr.service.BusinessServiceException;

@ControllerAdvice
@EnableWebMvc
public class ExceptionController {
	private final Log log = LogFactory.getLog(this.getClass());
	
	@ExceptionHandler(BusinessServiceException.class)
	public RedirectView  handleBusinessServiceException(BusinessServiceException exception,   HttpServletRequest request) {
		return construireRedirectView(exception, request,"listeDocs" , exception.getMessage());
	}
	
	
	@ExceptionHandler({AmazonServiceException.class, AmazonClientException.class})
	public RedirectView  handleAmazonException(Exception exception, HttpServletRequest request) {
		String message = "Impossible d'accéder au service de stockage";
		return construireRedirectView(exception, request,"listeDocs" , message);
	}
	@ExceptionHandler({MaxUploadSizeExceededException.class})
	public RedirectView  handleMaxUploadSizeExceededException(MaxUploadSizeExceededException exception, HttpServletRequest request) {
		String message = "Veuillez saisir un fichier de moins de : "+ (exception.getMaxUploadSize()/(1000*1000)) + "Mo";
		return construireRedirectView(exception, request,"addDocForm" , message);
	}

	private RedirectView construireRedirectView(
			Exception exception, HttpServletRequest request,String view, String message) {
		log.error(exception.getMessage());
		RedirectView rw = new RedirectView(view);
		FlashMap outputFlashMap = RequestContextUtils
				.getOutputFlashMap(request);
		if (outputFlashMap != null) {
			outputFlashMap.put("messageErreur", message);
		}
		return rw;
	}
	
}
