package fr.integration;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import fr.persistence.AbstractUnitTest;
import fr.persistence.domain.Document;
import fr.persistence.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class ControllersIT extends AbstractUnitTest {
    private static String SEC_CONTEXT_ATTR = HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;
    
    @Autowired
	private WebApplicationContext wac ;
    
    @Autowired
    private FilterChainProxy springSecurityFilterChain;
 
	private MockMvc mockMvc;
 
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilters(this.springSecurityFilterChain).build();
	}
	
	@Test
	public void page_accueil() throws Exception {
		this.mockMvc.perform(get("/login")).andExpect(status().isOk())
				.andExpect(view().name("accueilLogin"))
				.andExpect(model().attribute("user", new User()));
	}
	
	@Test
	public void erreur_d_authentification() throws Exception{
		final String username = "test";
		HttpSession session = this.mockMvc.perform(
						post("/j_spring_security_check").param("j_username", username)
									 .param("j_password", "password"))
		//		.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isFound())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/loginfailed"))
				.andExpect(new ResultMatcher() {
                                public void match(MvcResult mvcResult) throws Exception {
                                        HttpSession session = mvcResult.getRequest().getSession();
                                        SecurityContext securityContext = (SecurityContext) session.getAttribute(SEC_CONTEXT_ATTR);
                                        Assert.assertNull(securityContext);
                                }
                        })
                 .andReturn().getRequest().getSession();
        assertNotNull(session);

		this.mockMvc
				.perform(get("/loginfailed").session((MockHttpSession) session))
				.andExpect(status().isOk())
				.andExpect(view().name("accueilLogin"))
				.andExpect(model().attribute("error", "true"));

	}
	
	@Test
	public void liste_documents_after_authentification_OK() throws Exception{
		final String username = "test_login1";
		HttpSession session = this.mockMvc.perform(post("/j_spring_security_check").param("j_username", username)
									 .param("j_password", "password"))
									 .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isFound())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/login"))
				.andExpect(new ResultMatcher() {
                     public void match(MvcResult mvcResult) throws Exception {
                             HttpSession session = mvcResult.getRequest().getSession();
                             SecurityContext securityContext = (SecurityContext) session.getAttribute(SEC_CONTEXT_ATTR);
                             assertEquals("le nom d'utilisateur connecté ", securityContext.getAuthentication().getName(), username);
                     }
             })
             .andReturn().getRequest().getSession();
		assertNotNull(session);
		
		this.mockMvc.perform(get("/login").session((MockHttpSession) session))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/document/listeDocs"));
		
		MvcResult result = this.mockMvc.perform(get("/document/listeDocs").session((MockHttpSession) session))
		.andExpect(status().isOk())
		.andExpect(view().name("welcomeListeDocs"))
		.andExpect(model().attributeExists("listDocs")).andReturn();
		
		 @SuppressWarnings("unchecked")
		 List<Document> documents = (List<Document>) result.getModelAndView().getModel().get("listDocs");
		 assertEquals("nbre de documents rattachés à l'utilisateur", documents.size(), 5);
	}
	
	@Test
	public void register_user_add_delete_load_document() throws Exception{
		this.mockMvc.perform(post("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("accueilRegister"))
                .andExpect(model().attribute("user", new User()))
                .andReturn().getRequest().getSession();
		this.mockMvc
				.perform(
						post("/register")
								.param("firstName", "firstNameIT")
								.param("lastName", "lastNameIt")
								.param("login", "loginIt")
								.param("password", "passwordIt")
								.param("verifyPassword", "passwordIt")
								.param("email", "emailIt@it.fr"))
				.andExpect(status().isOk())
				.andExpect(view().name("register_ok"));
				
		final String username = "loginit";
		HttpSession session = this.mockMvc.perform(post("/j_spring_security_check").param("j_username", username)
									 .param("j_password", "passwordIt"))
									 .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isFound())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/login"))
				.andExpect(new ResultMatcher() {
                     public void match(MvcResult mvcResult) throws Exception {
                             HttpSession session = mvcResult.getRequest().getSession();
                             SecurityContext securityContext = (SecurityContext) session.getAttribute(SEC_CONTEXT_ATTR);
                             assertEquals("le nom d'utilisateur connecté ", securityContext.getAuthentication().getName(), username);
                     }
             })
             .andReturn().getRequest().getSession();
		assertNotNull(session);
		
		this.mockMvc.perform(get("/login").session((MockHttpSession) session))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/document/listeDocs"));
		
		MvcResult result = this.mockMvc.perform(get("/document/listeDocs").session((MockHttpSession) session))
		.andExpect(status().isOk())
		.andExpect(view().name("welcomeListeDocs"))
		.andExpect(model().attributeExists("listDocs")).andReturn();
		
		 @SuppressWarnings("unchecked")
		 List<Document> documents = (List<Document>) result.getModelAndView().getModel().get("listDocs");
		 assertEquals("aucun document n'est rattaché au nouveau utilisateur", documents.size(), 0);
		//TODO : add document + load + remove doucument + modify User
	}	
}
