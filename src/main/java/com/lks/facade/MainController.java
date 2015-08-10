package com.lks.facade;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lks.core.enums.DocOperations;
import com.lks.core.model.DocumentDO;
import com.lks.core.model.FileOperationDO;
import com.lks.core.model.FileReceivedForUploadDO;
import com.lks.uploader.IDocumentUploadService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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
			else {
				redirectUrl = "redirect:/resources/templates/login.html";
			}
		}
		else {
			redirectUrl = "redirect:/resources/templates/login.html";
		}
		return redirectUrl;
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
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {

		if(error != null){
			String errorMessage = getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION");
			System.out.println("Login Error Message is: " + errorMessage);
			return "redirect:/resources/templates/login.html?error=true";
		}
		return "redirect:/resources/templates/login.html";

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

	@RequestMapping(method = RequestMethod.GET, value = "/scanner/lock")
	public
	@ResponseBody
	DocumentDO lockRescanDocument(@RequestParam("documentId") int documentId){

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

	@RequestMapping(method = RequestMethod.GET, value = "/scanner/getRecordsWhichNeedRescan")
	public
	@ResponseBody
	List<DocumentDO> getRecordsWhichNeedRescan(){
		return documentUploadService.retrieveAllRescanDocuments();

	}

	@RequestMapping(method = RequestMethod.GET, value = "/do/getNewRecords")
	public
	@ResponseBody
	List<DocumentDO> getNewRecords(){
		return documentUploadService.retrieveAllNewAndLockedDocuments();

	}

	@RequestMapping(method = RequestMethod.GET, value = "/do/getAssignedRecords")
	public
	@ResponseBody
	List<DocumentDO> getRecordsAssignedTo(){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return documentUploadService.retrieveAllDocumentsAssignedTo(userDetails.getUsername());

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
	DocumentDO fileRescan(@RequestParam("documentId") int documentId, @RequestParam("comment") String comment){

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		FileOperationDO fileOperationDO = new FileOperationDO();
		fileOperationDO.setDocOperations(DocOperations.RESCAN);
		fileOperationDO.setDocumentId(documentId);
		fileOperationDO.setUserId(userDetails.getUsername());
		fileOperationDO.setComment(comment);
		DocumentDO documentDO = documentUploadService.performOperationOnDocument(fileOperationDO);


		return documentDO;
	}



	@RequestMapping(method = RequestMethod.GET, value = "/qa/lock")
	public
	@ResponseBody
	DocumentDO lockNotApprovedDocument(@RequestParam("documentId") int documentId){

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		FileOperationDO fileOperationDO = new FileOperationDO();
		fileOperationDO.setDocOperations(DocOperations.LOCK);
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



	@RequestMapping(method = RequestMethod.GET, value = "/all/view")
	public
	@ResponseBody
	void viewPdfFile(@RequestParam("documentId") int documentId, HttpServletResponse response) throws ServletException, IOException{

		String documentUrl = documentUploadService.retrieveDocumentUrl(documentId);
		File pdfFile = new File(documentUrl);
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "attachment; filename="+documentUrl+";");
		response.setContentLength((int) pdfFile.length());

		FileInputStream fileInputStream = new FileInputStream(pdfFile);
		OutputStream outputStream = response.getOutputStream();
		int bytes;
		while((bytes = fileInputStream.read()) != -1){
			outputStream.write(bytes);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/resolver/lock")
	public
	@ResponseBody
	DocumentDO lockHoldDocument(@RequestParam("documentId") int documentId){

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		FileOperationDO fileOperationDO = new FileOperationDO();
		fileOperationDO.setDocOperations(DocOperations.LOCK);
		fileOperationDO.setDocumentId(documentId);
		fileOperationDO.setUserId(userDetails.getUsername());
		DocumentDO documentDO = documentUploadService.performOperationOnDocument(fileOperationDO);


		return documentDO;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/resolver/resolve")
	public
	@ResponseBody
	DocumentDO fileResolve(@RequestParam("documentId") int documentId, @RequestParam("comment") String comment, @RequestParam("assignTo") String assignedTo){

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		FileOperationDO fileOperationDO = new FileOperationDO();
		fileOperationDO.setDocOperations(DocOperations.RESOLVE);
		fileOperationDO.setDocumentId(documentId);
		fileOperationDO.setUserId(userDetails.getUsername());
		fileOperationDO.setComment(comment);
		fileOperationDO.setAssignedTo(assignedTo);
		DocumentDO documentDO = documentUploadService.performOperationOnDocument(fileOperationDO);


		return documentDO;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/resolver/getRecordsWhichAreInHold")
	public
	@ResponseBody
	List<DocumentDO> getRecordsWhichAreInHold(){
		return documentUploadService.retrieveAllDocumentsWhichNeedApproval();

	}


	@RequestMapping(method = RequestMethod.POST, value = "/admin/createnewuser")
	public
	@ResponseBody
	int createNewUser(){






		return -1;
	}












}