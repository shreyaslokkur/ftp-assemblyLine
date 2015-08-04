package com.lks.facade;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.lks.core.enums.DocOperations;
import com.lks.core.model.DocumentDO;
import com.lks.core.model.FileOperationDO;
import com.lks.core.model.FileReceivedForUploadDO;
import com.lks.uploader.IDocumentUploadService;
import com.sun.jersey.core.header.ContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import org.apache.commons.io.FilenameUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Controller
public class MainController {

    @Resource(name = "documentUploadService")
	IDocumentUploadService documentUploadService;

	@RequestMapping(value = { "/welcome**" }, method = RequestMethod.GET)
	public ModelAndView defaultPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security + Hibernate Example");
		model.addObject("message", "This is default page!");
		model.setViewName("hello");
		return model;

	}

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String pageRedirect(){
		String redirectUrl = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.isAuthenticated()){
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
				//retrieve the role of the user
				Iterator iterator = authorities.iterator();
				while(iterator.hasNext()){
					SimpleGrantedAuthority authority = (SimpleGrantedAuthority) iterator.next();
					String role = authority.getAuthority();
					switch (role){
						case "ROLE_DO":
							redirectUrl =  "redirect:/resources/templates/dataOp.html";
							break;
						case "ROLE_SCANNER":
							redirectUrl = "redirect:/resources/templates/FileUpload.html";
							break;
						case "ROLE_APPROVER":
							redirectUrl = "redirect:/resources/templates/Approver.html";
							break;
					}

				}

			}
		}
		return redirectUrl;
	}

	@RequestMapping(value = "/staticPage", method = RequestMethod.GET)
	public String redirect() {

		return "redirect:/resources/templates/dataOp.html";
	}

	@RequestMapping(value = "/admin/hello", method = RequestMethod.GET)
	public ModelAndView adminPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security + Hibernate Example");
		model.addObject("message", "This page is for ROLE_ADMIN only!");
		model.setViewName("admin");

		return model;

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {



		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");

			return model;

	}

	// customize the error message
	private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession().getAttribute(key);

		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = "Invalid username and password!";
		}

		return error;
	}

	// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView model = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			System.out.println(userDetail);

			model.addObject("username", userDetail.getUsername());

		}

		model.setViewName("403");
		return model;

	}

	// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.POST)
	public ModelAndView accesssDeniedPost() {

		ModelAndView model = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			System.out.println(userDetail);

			model.addObject("username", userDetail.getUsername());

		}

		model.setViewName("403");
		return model;

	}


	@RequestMapping(value = "/scanner/upload", method = RequestMethod.POST)
	@ResponseBody
	public int continueFileUpload(HttpServletRequest request,
									 HttpServletResponse response){
		MultipartHttpServletRequest mRequest;
		try {
			mRequest = (MultipartHttpServletRequest) request;
			mRequest.getParameterMap();
			String fileName = null;
			Iterator<String> itr = mRequest.getFileNames();
			MultipartFile mFile = null;
			while (itr.hasNext()) {
				mFile = mRequest.getFile(itr.next());
				fileName = mFile.getOriginalFilename();
				System.out.println(fileName);
			}
			File tmpFile = null;
			tmpFile = File.createTempFile(FilenameUtils.getBaseName(fileName), "." + FilenameUtils.getExtension(fileName));
			mFile.transferTo(tmpFile);

			FileReceivedForUploadDO fileReceivedForUploadDO = new FileReceivedForUploadDO();
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			fileReceivedForUploadDO.setCreatedBy(userDetails.getUsername());
			fileReceivedForUploadDO.setFileLocation(tmpFile.getAbsolutePath());
			fileReceivedForUploadDO.setFileName(fileName);
			fileReceivedForUploadDO.setApplicationNo(Integer.parseInt(mRequest.getParameter("applicationNo")));
			fileReceivedForUploadDO.setBookletNo(Integer.parseInt(mRequest.getParameter("bookletNo")));
			fileReceivedForUploadDO.setBranchName(mRequest.getParameter("branchName"));
			fileReceivedForUploadDO.setNumOfCustomers(Integer.parseInt(mRequest.getParameter("numOfCustomers")));
			fileReceivedForUploadDO.setPlaceOfMeeting(mRequest.getParameter("placeOfMeeting"));
			if(mRequest.getParameter("documentId") != null){
				fileReceivedForUploadDO.setDocumentId(Integer.parseInt(mRequest.getParameter("documentId")));
				return documentUploadService.reuploadDocument(fileReceivedForUploadDO);
			}else {
				return documentUploadService.createNewDocument(fileReceivedForUploadDO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}


	@RequestMapping(method = RequestMethod.POST, value = "/scanner/reupload")
	public
	@ResponseBody
	void documentReUpload(HttpServletRequest request,
						  HttpServletResponse response){
		MultipartHttpServletRequest mRequest;
		try {
			mRequest = (MultipartHttpServletRequest) request;
			mRequest.getParameterMap();
			String fileName = null;
			Iterator<String> itr = mRequest.getFileNames();
			MultipartFile mFile = null;
			while (itr.hasNext()) {
				mFile = mRequest.getFile(itr.next());
				fileName = mFile.getOriginalFilename();
				System.out.println(fileName);
			}
			File tmpFile = null;
			tmpFile = File.createTempFile(FilenameUtils.getBaseName(fileName), "." + FilenameUtils.getExtension(fileName));
			mFile.transferTo(tmpFile);
			FileReceivedForUploadDO fileReceivedForUploadDO = new FileReceivedForUploadDO();
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			fileReceivedForUploadDO.setCreatedBy(userDetails.getUsername());
			fileReceivedForUploadDO.setFileLocation(tmpFile.getAbsolutePath());
			fileReceivedForUploadDO.setFileName(fileName);
			fileReceivedForUploadDO.setApplicationNo(Integer.parseInt(mRequest.getParameter("applicationNo")));
			fileReceivedForUploadDO.setBookletNo(Integer.parseInt(mRequest.getParameter("bookletNo")));
			fileReceivedForUploadDO.setBranchName(mRequest.getParameter("branchName"));
			fileReceivedForUploadDO.setNumOfCustomers(Integer.parseInt(mRequest.getParameter("numOfCustomers")));
			fileReceivedForUploadDO.setPlaceOfMeeting(mRequest.getParameter("placeOfMeeting"));
			fileReceivedForUploadDO.setDocumentId(Integer.parseInt(mRequest.getParameter("documentId")));

			documentUploadService.reuploadDocument(fileReceivedForUploadDO);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/do/lock")
	public
	@ResponseBody
	DocumentDO fileLock(@RequestParam("documentId") int documentId){

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		FileOperationDO fileOperationDO = new FileOperationDO();
		fileOperationDO.setDocOperations(DocOperations.LOCK);
		fileOperationDO.setDocumentId(documentId);
		fileOperationDO.setUserId(userDetails.getUsername());
		DocumentDO documentDO = documentUploadService.performOperationOnDocument(fileOperationDO);


		return documentDO;
	}

    @RequestMapping(method = RequestMethod.GET, value = "/scanner/getRescanDocuments")
    public
    @ResponseBody
		List<DocumentDO> getRescanDocuments(@RequestParam("branchName") String branchName){


        return documentUploadService.retrieveAllDocumentsWhichNeedRescan(branchName);

    }

	@RequestMapping(method = RequestMethod.GET, value = "/do/unlock")
	public
	@ResponseBody
	DocumentDO fileUnLock(@RequestParam("documentId") int documentId){

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		FileOperationDO fileOperationDO = new FileOperationDO();
		fileOperationDO.setDocOperations(DocOperations.UNLOCK);
		fileOperationDO.setDocumentId(documentId);
		fileOperationDO.setUserId(userDetails.getUsername());
		DocumentDO documentDO = documentUploadService.performOperationOnDocument(fileOperationDO);


		return documentDO;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/do/complete")
	public
	@ResponseBody
	DocumentDO fileComplete(@RequestParam("documentId") int documentId){

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		FileOperationDO fileOperationDO = new FileOperationDO();
		fileOperationDO.setDocOperations(DocOperations.COMPLETE);
		fileOperationDO.setDocumentId(documentId);
		fileOperationDO.setUserId(userDetails.getUsername());
		DocumentDO documentDO = documentUploadService.performOperationOnDocument(fileOperationDO);


		return documentDO;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/do/hold")
	public
	@ResponseBody
	DocumentDO fileHold(@RequestParam("documentId") int documentId, @RequestParam("comment") String comment){

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		FileOperationDO fileOperationDO = new FileOperationDO();
		fileOperationDO.setDocOperations(DocOperations.HOLD);
		fileOperationDO.setDocumentId(documentId);
		fileOperationDO.setUserId(userDetails.getUsername());
		fileOperationDO.setComment(comment);
		DocumentDO documentDO = documentUploadService.performOperationOnDocument(fileOperationDO);


		return documentDO;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/do/rescan")
	public
	@ResponseBody
	DocumentDO fileRescan(@RequestParam("documentId") int documentId){

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		FileOperationDO fileOperationDO = new FileOperationDO();
		fileOperationDO.setDocOperations(DocOperations.RESCAN);
		fileOperationDO.setDocumentId(documentId);
		fileOperationDO.setUserId(userDetails.getUsername());
		DocumentDO documentDO = documentUploadService.performOperationOnDocument(fileOperationDO);


		return documentDO;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/qa/approve")
	public
	@ResponseBody
	DocumentDO fileApprove(@RequestParam("documentId") int documentId){

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		FileOperationDO fileOperationDO = new FileOperationDO();
		fileOperationDO.setDocOperations(DocOperations.APPROVE);
		fileOperationDO.setDocumentId(documentId);
		fileOperationDO.setUserId(userDetails.getUsername());
		DocumentDO documentDO = documentUploadService.performOperationOnDocument(fileOperationDO);


		return documentDO;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/qa/reject")
	public
	@ResponseBody
	DocumentDO fileReject(@RequestParam("documentId") int documentId, @RequestParam("comment") String comment, @RequestParam("assignTo") String assignedTo){

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		FileOperationDO fileOperationDO = new FileOperationDO();
		fileOperationDO.setDocOperations(DocOperations.REJECT);
		fileOperationDO.setDocumentId(documentId);
		fileOperationDO.setUserId(userDetails.getUsername());
		fileOperationDO.setComment(comment);
		fileOperationDO.setAssignedTo(assignedTo);
		DocumentDO documentDO = documentUploadService.performOperationOnDocument(fileOperationDO);


		return documentDO;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/qa/getRecordsWhichNeedApproval")
	public
	@ResponseBody
	List<DocumentDO> getRecordsWhichNeedApproval(){
		return documentUploadService.retrieveAllDocumentsWhichNeedApproval();

	}

	@RequestMapping(method = RequestMethod.GET, value = "/scanner/rescan")
	public
	@ResponseBody
	DocumentDO fileRescanned(@RequestParam("documentId") int documentId, @RequestParam("assignTo") String assignedTo){

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		FileOperationDO fileOperationDO = new FileOperationDO();
		/*fileOperationDO.setDocOperations(DocOperations.);*/
		fileOperationDO.setDocumentId(documentId);
		fileOperationDO.setUserId(userDetails.getUsername());
		fileOperationDO.setAssignedTo(assignedTo);
		DocumentDO documentDO = documentUploadService.performOperationOnDocument(fileOperationDO);


		return documentDO;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/do/getNewRecords")
	public
	@ResponseBody
	List<DocumentDO> getNewRecords(){
		return documentUploadService.retrieveAllNewAndLockedDocuments();

	}

	@RequestMapping(method = RequestMethod.GET, value = "/do/getAssignedRejectedRecords")
	public
	@ResponseBody
	List<DocumentDO> getRecordsAssignedTo(){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return documentUploadService.retrieveAllRejectedDocumentsAssignedTo(userDetails.getUsername());

	}

	@RequestMapping(method = RequestMethod.GET, value = "/scanner/getRecordsWhichNeedRescan")
	public
	@ResponseBody
	List<DocumentDO> getRecordsWhichNeedRescan(){
		return documentUploadService.retrieveAllRescanDocuments();

	}


	/*@Configuration
	@EnableWebSecurity
	static protected class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
					.httpBasic().and()
					.authorizeRequests()
					.antMatchers("/index.html", "/home.html", "/login.html", "/").permitAll().anyRequest()
					.authenticated().and()
					.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
					.csrf().csrfTokenRepository(csrfTokenRepository());

		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication()
					.withUser( "user" ).password( "password" ).roles( "DO" );
		}

		private CsrfTokenRepository csrfTokenRepository() {
			HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
			repository.setHeaderName("X-XSRF-TOKEN");
			return repository;
		}

		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
	}*/



}