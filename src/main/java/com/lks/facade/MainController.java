package com.lks.facade;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lks.core.DateUtils;
import com.lks.core.DocumentUtils;
import com.lks.core.FALException;
import com.lks.core.enums.DocOperations;
import com.lks.core.enums.ExceptionCode;
import com.lks.core.model.*;
import com.lks.security.IBranchService;
import com.lks.security.IUserService;
import com.lks.uploader.IDocumentUploadService;
import com.lks.uploader.IFTPService;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MainController {

    @Resource(name = "documentUploadService")
	IDocumentUploadService documentUploadService;

	@Resource(name = "branchService")
	IBranchService branchService;

	@Resource(name = "userDetailService")
	IUserService userService;

	@Resource(name = "documentUtils")
	DocumentUtils documentUtils;

	@Resource(name = "ftpService")
	IFTPService ftpService;

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
						case "ROLE_RESOLVER":
							redirectUrl = "redirect:/resources/templates/QueryResolver.html";
							break;
						case "ROLE_ADMIN":
							redirectUrl = "redirect:/resources/templates/admin.html";
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
	public synchronized int continueFileUpload(HttpServletRequest request,
									 HttpServletResponse response){
		MultipartHttpServletRequest mRequest;

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
		try{
			tmpFile = File.createTempFile(FilenameUtils.getBaseName(fileName), "." + FilenameUtils.getExtension(fileName));
			mFile.transferTo(tmpFile);
		}catch (Exception e){
			throw new FALException(ExceptionCode.SYSTEM_ERROR,"Unable to create a temp file for upload");
		}

		String ftpFileLocation = uploadToFTPServer(fileName,tmpFile.getAbsolutePath(), mRequest.getParameter("branchCode"));
		if(ftpFileLocation != null){
			FileReceivedForUploadDO fileReceivedForUploadDO = new FileReceivedForUploadDO();
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			fileReceivedForUploadDO.setCreatedBy(userDetails.getUsername());
			fileReceivedForUploadDO.setFileLocation(ftpFileLocation);
			fileReceivedForUploadDO.setFileName(fileName);
			fileReceivedForUploadDO.setApplicationNo(Integer.parseInt(mRequest.getParameter("applicationNo")));
			fileReceivedForUploadDO.setBookletNo(mRequest.getParameter("bookletNo"));
			fileReceivedForUploadDO.setBranchCode(Integer.parseInt(mRequest.getParameter("branchCode")));
			fileReceivedForUploadDO.setNumOfCustomers(Integer.parseInt(mRequest.getParameter("numOfCustomers")));
			fileReceivedForUploadDO.setPlaceOfMeeting(mRequest.getParameter("placeOfMeeting"));
			//delete file from tomcat server
			tmpFile.delete();
			if(mRequest.getParameter("documentId") != null){
				fileReceivedForUploadDO.setDocumentId(Integer.parseInt(mRequest.getParameter("documentId")));
				return documentUploadService.reuploadDocument(fileReceivedForUploadDO);
			}else {
				return documentUploadService.createNewDocument(fileReceivedForUploadDO);
			}
		}
		else {
			//delete file from tomcat server
			tmpFile.delete();
			throw new FALException(ExceptionCode.SYSTEM_ERROR, "Unable to create a file in the ftp folder");
		}





	}


	@RequestMapping(method = RequestMethod.POST, value = "/scanner/reupload")
	public
	@ResponseBody
	synchronized void documentReUpload(HttpServletRequest request,
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
			fileReceivedForUploadDO.setBookletNo(mRequest.getParameter("bookletNo"));
			fileReceivedForUploadDO.setBranchCode(Integer.parseInt(mRequest.getParameter("branchCode")));
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

	@RequestMapping(method = RequestMethod.GET, value = "/scanner/getRescanDocumentsForBranch")
	public
	@ResponseBody
	List<DocumentDO> getRescanDocuments(@RequestParam("branchCode") int branchCode){


		return documentUploadService.retrieveBranchDocumentsWhichNeedRescan(branchCode);

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
	DocumentListDO getNewRecords(@RequestParam("pageNumber") int pageNumber){
		int offset = getOffsetFromPageNumber(pageNumber);
		List<DocumentDO> documentList = documentUploadService.retrieveAllNewDocuments(offset);
		int tatTimeInMinutes = documentUtils.getTatTimeInMinutes();
		Long totalCount = documentUploadService.retrieveCountOfNewDocuments();
		Date currentDate = new Date();
		DocumentListDO documentListDO = new DocumentListDO();

		Date recCreatedOn = null;
		for(DocumentDO documentDO : documentList){
			recCreatedOn = DateUtils.convertStringToDate(documentDO.getRecCreatedOn());
			if(hasCrossedTat(recCreatedOn,currentDate,tatTimeInMinutes))
			{
				documentDO.setHasCrossedTat(true);
			}else {
				documentDO.setHasCrossedTat(false);
			}



		}
		documentListDO.setDocumentList(documentList);
		documentListDO.setTotalCount(totalCount);
		return documentListDO;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/do/getAssignedRecords")
	public
	@ResponseBody
	List<DocumentDO> getRecordsAssignedTo(){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<DocumentDO> documentDOList = documentUploadService.retrieveAllDocumentsAssignedTo(userDetails.getUsername());
		int tatTimeInMinutes = documentUtils.getTatTimeInMinutes();
		Date currentDate = new Date();

		Date recCreatedOn = null;
		for(DocumentDO documentDO : documentDOList){
			recCreatedOn = DateUtils.convertStringToDate(documentDO.getRecCreatedOn());
			if(hasCrossedTat(recCreatedOn,currentDate,tatTimeInMinutes))
			{
				documentDO.setHasCrossedTat(true);
			}else {
				documentDO.setHasCrossedTat(false);
			}



		}
		return documentDOList;

	}

	private boolean hasCrossedTat(Date recCreatedOn, Date currentDate, int tatInMinutes){
		long t=recCreatedOn.getTime();
		Date afterAddingTat=new Date(t + (tatInMinutes * 60000));
		if(currentDate.after(afterAddingTat)){
			return true;
		}
		return false;

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
	DocumentListDO getRecordsWhichNeedApproval(@RequestParam("pageNumber") int pageNumber){
		DocumentListDO documentListDO = new DocumentListDO();
		int offset = getOffsetFromPageNumber(pageNumber);
		List<DocumentDO> documentList = documentUploadService.retrieveAllDocumentsWhichNeedApproval(offset);
		Long totalCount = documentUploadService.retrieveCountOfApprovalDocuments();
		documentListDO.setDocumentList(documentList);
		documentListDO.setTotalCount(totalCount);
		return documentListDO;

	}



	@RequestMapping(method = RequestMethod.GET, value = "/all/view")
	public
	@ResponseBody
	synchronized void viewPdfFile(@RequestParam("documentId") int documentId, HttpServletResponse response) throws ServletException, IOException{

		File pdfFile = null;
		FileInputStream fileInputStream = null;
		OutputStream outputStream = null;
		try{
			String documentUrl = documentUploadService.retrieveDocumentUrl(documentId);
			pdfFile = ftpService.downloadFile(documentUrl);
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename="+documentUrl+";");
			response.setContentLength((int) pdfFile.length());

			fileInputStream = new FileInputStream(pdfFile);
			outputStream = response.getOutputStream();
			int bytes;
			while((bytes = fileInputStream.read()) != -1){
				outputStream.write(bytes);
			}
		}finally {
			fileInputStream.close();
			fileInputStream = null;
			outputStream.flush();
			outputStream.flush();
			outputStream = null;
			System.gc();
			pdfFile.delete();
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
	DocumentListDO getRecordsWhichAreInHold(@RequestParam("pageNumber") int pageNumber){
		DocumentListDO documentListDO = new DocumentListDO();
		int offset = getOffsetFromPageNumber(pageNumber);
		List<DocumentDO> documentList = documentUploadService.retrieveAllDocumentsWhichAreInHold(offset);
		Long totalCount = documentUploadService.retrieveCountOfApprovalDocuments();
		documentListDO.setDocumentList(documentList);
		documentListDO.setTotalCount(totalCount);
		return documentListDO;


	}


	@RequestMapping(method = RequestMethod.POST, value = "/admin/createnewuser")
	public
	@ResponseBody
	int createNewUser(@RequestBody UserModelDO userModelDO){
		return userService.createNewUser(userModelDO);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/admin/editPassword")
	public
	@ResponseBody
	boolean editPasswordOfUser(@RequestBody UserModelDO userModelDO){
		return userService.resetPassword(userModelDO);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/admin/deleteUser")
	public
	@ResponseBody
	boolean deleteUser(@RequestParam("username") String username){
		UserModelDO userModelDO = new UserModelDO();
		userModelDO.setUsername( username);
		return userService.deleteUser(userModelDO);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/all/getCurrentUser")
	public
	@ResponseBody
	UserModelDO getUser(){
		UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.findUser(principal.getUsername());

	}

	@RequestMapping(method = RequestMethod.GET, value = "/all/getAllUsersForRole")
	public
	@ResponseBody
	List<UserModelDO> getUsersForRole(@RequestParam("role") String role){
		return userService.findUsersByRole(role);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/admin/getAllUsers")
	public
	@ResponseBody
	List<UserModelDO> getAllUsers(){
		return userService.findAllUsers();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/admin/createnewbranch")
	public
	@ResponseBody
	int createNewBranch(@RequestBody BranchDO branchDO){
		return branchService.createNewBranch(branchDO);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/admin/editBranch")
	public
	@ResponseBody
	boolean editBranchAddress(@RequestBody BranchDO branchDO){
		return branchService.editBranch(branchDO);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/admin/deleteBranch")
	public
	@ResponseBody
	boolean deleteBranch(@RequestParam("branchCode") int branchCode){
		BranchDO branchDO = new BranchDO();
		branchDO.setBranchCode(branchCode);
		return branchService.deleteBranch(branchDO);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/all/getAllBranches")
	public
	@ResponseBody
	List<BranchDO> getAllBranches(){
		return branchService.getAllBranches();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/admin/getAllRoles")
	public
	@ResponseBody
	List<RoleDO> getAllRoles(){
		return userService.getAllRoles();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/admin/getAllDocuments")
	public
	@ResponseBody
	List<DocumentDO> getAllDocuments(){
		return documentUploadService.retrieveAllDocuments();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/admin/unlock")
	public
	@ResponseBody
	DocumentDO unlockDocument(@RequestParam("documentId") int documentId){

		FileOperationDO fileOperationDO = new FileOperationDO();
		fileOperationDO.setDocOperations(DocOperations.UNLOCK);
		fileOperationDO.setDocumentId(documentId);
		DocumentDO documentDO = documentUploadService.performOperationOnDocument(fileOperationDO);
		return documentDO;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/all/getTat")
	public
	@ResponseBody
	String getTatTime(){
		return documentUtils.getTatTime();
	}

	@ExceptionHandler(FALException.class)
	public @ResponseBody
	ErrorDO handleCustomException(FALException ex, HttpServletResponse response) {

		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		ErrorDO errorDO = new ErrorDO();
		if(ex.getExceptionCode() != null)
			errorDO.setExceptionCode(ex.getExceptionCode().name());
		errorDO.setStatusText(ex.getErrorMessage());
		return errorDO;

	}

	@ExceptionHandler(Exception.class)
	public @ResponseBody
	ErrorDO handleException(Exception ex, HttpServletResponse response) {

		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		ErrorDO errorDO = new ErrorDO();
		errorDO.setStatusText(ex.getMessage());
		return errorDO;

	}


	private synchronized String uploadToFTPServer(String fileName, String fileLocation, String branchCode){
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		String date = dateFormat.format(cal.getTime());
		date = date.replaceAll("/","");
		if(!ftpService.checkIfDirectoryWithBranchCodeExists(branchCode)){
			ftpService.createBranchCodeDirectory(branchCode);
		}
		if(!ftpService.checkIfDirectoryWithDateExists(branchCode,date)){
			ftpService.createDateDirectory(branchCode,date);
		}
		String dirPath = ftpService.getDirectoryForBranchAndDate(branchCode,date);
		return ftpService.uploadFile(fileLocation, fileName, dirPath);
	}


	private int getOffsetFromPageNumber(int pageNumber){
		int offset;
		if(pageNumber > 0){
			offset = (documentUtils.getOffset()*pageNumber)+1;
		}else{
			offset = 0;
		}
		return offset;
	}







}