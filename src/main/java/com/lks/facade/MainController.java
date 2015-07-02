package com.lks.facade;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.lks.core.enums.DocOperations;
import com.lks.core.model.FileOperationDO;
import com.lks.core.model.FileReceivedForUploadDO;
import com.lks.uploader.IDocumentUploadService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;

@Controller
public class MainController {

    @Resource(name = "documentUploadService")
	IDocumentUploadService documentUploadService;

	@RequestMapping(value = { "/", "/welcome**" }, method = RequestMethod.GET)
	public ModelAndView defaultPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security + Hibernate Example");
		model.addObject("message", "This is default page!");
		model.setViewName("hello");
		return model;

	}

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
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

    @RequestMapping(method = RequestMethod.POST, value = "/scanner/upload")
    public
    @ResponseBody
    int fileUpload(@RequestParam("file") MultipartFile file,
                      @RequestParam("uploadName") String fileName,
                      @RequestParam("branchName") String branchName,
                      @RequestParam("placeOfMeeting") String placeOfMeeting,
                      @RequestParam("bookletNo") int bookletNo,
                      @RequestParam("applicationNo") int applicationNo,
                      @RequestParam("numOfCustomers") int numOfCustomers){

        File tmpFile = null;
        try {
            tmpFile = File.createTempFile(FilenameUtils.getBaseName(file.getOriginalFilename()), "." + FilenameUtils.getExtension(file.getOriginalFilename()));
            file.transferTo(tmpFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        FileReceivedForUploadDO fileReceivedForUploadDO = new FileReceivedForUploadDO();
        /*UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();*/
        fileReceivedForUploadDO.setCreatedBy("lokkur");
        fileReceivedForUploadDO.setFileLocation(tmpFile.getAbsolutePath());
        fileReceivedForUploadDO.setFileName(fileName);
        fileReceivedForUploadDO.setApplicationNo(applicationNo);
        fileReceivedForUploadDO.setBookletNo(bookletNo);
        fileReceivedForUploadDO.setBranchName(branchName);
        fileReceivedForUploadDO.setNumOfCustomers(numOfCustomers);
        fileReceivedForUploadDO.setPlaceOfMeeting(placeOfMeeting);

        return documentUploadService.createNewDocument(fileReceivedForUploadDO);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/do/lock")
    public
    @ResponseBody
    String fileLock(@RequestParam("fileId") int documentId){

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FileOperationDO fileOperationDO = new FileOperationDO();
        fileOperationDO.setDocOperations(DocOperations.LOCK);
        fileOperationDO.setDocumentId(documentId);
        fileOperationDO.setUserId(userDetails.getUsername());
        documentUploadService.performOperationOnDocument(fileOperationDO);


        return null;
    }

	@RequestMapping(method = RequestMethod.GET, value = "/do/complete")
	public
	@ResponseBody
	String fileComplete(@RequestParam("fileId") int documentId){

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		FileOperationDO fileOperationDO = new FileOperationDO();
		fileOperationDO.setDocOperations(DocOperations.COMPLETE);
		fileOperationDO.setDocumentId(documentId);
		fileOperationDO.setUserId(userDetails.getUsername());
		documentUploadService.performOperationOnDocument(fileOperationDO);


		return null;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/do/hold")
	public
	@ResponseBody
	String fileHold(@RequestParam("fileId") int documentId, @RequestParam("comment") String comment){

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		FileOperationDO fileOperationDO = new FileOperationDO();
		fileOperationDO.setDocOperations(DocOperations.HOLD);
		fileOperationDO.setDocumentId(documentId);
		fileOperationDO.setUserId(userDetails.getUsername());
		fileOperationDO.setComment(comment);
		documentUploadService.performOperationOnDocument(fileOperationDO);


		return null;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/do/rescan")
	public
	@ResponseBody
	String fileRescan(@RequestParam("fileId") int documentId){

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		FileOperationDO fileOperationDO = new FileOperationDO();
		fileOperationDO.setDocOperations(DocOperations.RESCAN);
		fileOperationDO.setDocumentId(documentId);
		fileOperationDO.setUserId(userDetails.getUsername());
		documentUploadService.performOperationOnDocument(fileOperationDO);


		return null;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/qa/approve")
	public
	@ResponseBody
	String fileApprove(@RequestParam("fileId") int documentId){

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		FileOperationDO fileOperationDO = new FileOperationDO();
		fileOperationDO.setDocOperations(DocOperations.APPROVE);
		fileOperationDO.setDocumentId(documentId);
		fileOperationDO.setUserId(userDetails.getUsername());
		documentUploadService.performOperationOnDocument(fileOperationDO);


		return null;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/qa/reject")
	public
	@ResponseBody
	String fileReject(@RequestParam("fileId") int documentId, @RequestParam("comment") String comment, @RequestParam("assignTo") String assignedTo){

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		FileOperationDO fileOperationDO = new FileOperationDO();
		fileOperationDO.setDocOperations(DocOperations.REJECT);
		fileOperationDO.setDocumentId(documentId);
		fileOperationDO.setUserId(userDetails.getUsername());
		fileOperationDO.setComment(comment);
		fileOperationDO.setAssignedTo(assignedTo);
		documentUploadService.performOperationOnDocument(fileOperationDO);


		return null;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/scanner/rescan")
	public
	@ResponseBody
	String fileRescanned(@RequestParam("fileId") int documentId, @RequestParam("assignTo") String assignedTo){

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		FileOperationDO fileOperationDO = new FileOperationDO();
		/*fileOperationDO.setDocOperations(DocOperations.);*/
		fileOperationDO.setDocumentId(documentId);
		fileOperationDO.setUserId(userDetails.getUsername());
		fileOperationDO.setAssignedTo(assignedTo);
		documentUploadService.performOperationOnDocument(fileOperationDO);


		return null;
	}

}