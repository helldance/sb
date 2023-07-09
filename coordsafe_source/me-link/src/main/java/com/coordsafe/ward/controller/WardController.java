package com.coordsafe.ward.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.codetable.editor.CodeTableTypeEditor;
import com.coordsafe.core.codetable.entity.CodeTable;
import com.coordsafe.core.codetable.service.CodeTableService;
import com.coordsafe.exception.CsStatusCode;
import com.coordsafe.exception.kinds.CoordSafeResponse;
import com.coordsafe.exception.kinds.ErrorMessage;
import com.coordsafe.geofence.entity.Geofence;
import com.coordsafe.geofence.service.GeofenceService;
import com.coordsafe.guardian.entity.Guardian;
import com.coordsafe.guardian.service.GuardianService;
import com.coordsafe.img.IMGService;
import com.coordsafe.locator.entity.Locator;
import com.coordsafe.locator.service.LocatorService;
import com.coordsafe.notification.service.WardNotificationSettingService;
import com.coordsafe.ward.entity.Ward;
import com.coordsafe.ward.exception.WardException;
import com.coordsafe.ward.form.WardForm;
import com.coordsafe.ward.service.WardService;
@Controller
@RequestMapping("/" + Constants.WARDHOME)
@SessionAttributes("ward")
public class WardController {
	Logger log = LoggerFactory.getLogger(WardController.class);
	String tmp = "resources/wardphotos/";
	WardService wardService;
	CodeTableService codeTableService;
	MessageSource messageSource;
	@Autowired
	GuardianService guardianService;
	@Autowired
	LocatorService locatorService;
	@Autowired
	WardNotificationSettingService wardNotificationService;
	@Autowired
	GeofenceService geofenceService;
	@Autowired
	IMGService imgservice;
	@Autowired
	public void setWardService(WardService wardService) {
		this.wardService = wardService;
	}

	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Autowired
	public void setCodeTableService(CodeTableService codeTableService) {
		this.codeTableService = codeTableService;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(CodeTable.class, new CodeTableTypeEditor());
	}

	@RequestMapping(method = RequestMethod.GET, value="create")
	public String createWard(Model model) {
		WardForm wardForm = new WardForm();
		model.addAttribute("wardForm",wardForm);
		model.addAttribute("create", true);
		return Constants.WARDHOME + Constants.CREATE;
	}
	
	public CoordSafeResponse validateObject(BindingResult bindingResult, long csCode){
		CoordSafeResponse result = new CoordSafeResponse();

		result.csCode = csCode;
		List<FieldError> allErrors = bindingResult.getFieldErrors();
		List<ErrorMessage> errorMesages = new ArrayList<ErrorMessage>();
		for (FieldError objectError : allErrors) {
			errorMesages.add(new ErrorMessage(objectError.getField(), objectError.getDefaultMessage()));
		}
		result.setErrorMessageList(errorMesages);
		result.machineMsg = "";
		result.userMsg = "";
		
		return result;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="create")
	public @ResponseBody CoordSafeResponse addWard(@Valid WardForm wardForm, BindingResult bindingResult, HttpServletRequest request, 
			Model model) {
		// model.addAttribute("resourceType",
		// codeTableService.findByType("RESOURCE_TYPE"));
		log.info("In the ward create POST method...");
		if (bindingResult.hasErrors()) {
			log.error("The errors is " + bindingResult.getErrorCount());
			//return Constants.WARDHOME + Constants.CREATE;

		}
		
		
		Locator locator = null;
		try {
			
			String name = wardForm.getName();
			if(wardService.findByName(name) != null){
				bindingResult
				.addError(new FieldError("wardForm", "name", messageSource
						.getMessage("wardForm.name.exit", null, null)));
				return validateObject(bindingResult, 700);
				
			}
			
			
			locator = locatorService.findLocatorByImei(wardForm.getDeviceid().trim());
			if(locator == null){
				bindingResult
				.addError(new FieldError("wardForm", "deviceid", messageSource
						.getMessage("wardForm.deviceid", null, null)));
				return validateObject(bindingResult,700);
			}
			if(!wardForm.getDevicepassword().equals(locator.getDevicePassword())){
				
				bindingResult
				.addError(new FieldError("wardForm", "devicepassword", messageSource
						.getMessage("wardForm.devicepassword", null, null)));
				return validateObject(bindingResult,700);
				
			}
			

			
			if (request instanceof MultipartHttpServletRequest){
		           MultipartHttpServletRequest multipartRequest =
		                        (MultipartHttpServletRequest) request;
		           MultipartFile file = multipartRequest.getFiles("photo").get(0);
		           log.info("The Img for request is " + file.getOriginalFilename());

		    }
			MultipartFile imgFile = wardForm.getPhoto();

			if( imgFile == null || imgFile.getSize() < 0){
				log.error("The IMG is null");
				/*
				bindingResult
				.addError(new FieldError("wardForm", "photo", messageSource
						.getMessage("wardForm.photo", null, null)));
				return validateObject(bindingResult,700);
				*/
				
			}
			log.info("The IMG is " + imgFile.getOriginalFilename()+ " The size is " + imgFile.getSize());

			String photoPath  = request.getSession().getServletContext().getRealPath("/");
			
			log.info("path = " + photoPath);
			File dest = new File(photoPath +tmp + imgFile.getOriginalFilename());
			log.info("The file dest is " + dest.getAbsolutePath()+dest.getPath());
			imgFile.transferTo(dest);
			String photourl =  tmp + imgFile.getOriginalFilename();
			String urlIMG64 =  tmp + imgservice.cutImage(dest, 100, 100);
			String urlIMG32 =  tmp + imgservice.cutImage(dest, 32, 32);
			
			//Update Locator's spoil status to FALSE for the WARD
			locator.setSpoiled(false);
			locatorService.updateLocator(locator);
			
			Ward wardInstance = new Ward();
			wardInstance.setName(wardForm.getName());
			//The img can be taken from generated img file instead of url;
			//wardInstance.setPhotourl(wardForm.getPhotourl());
			wardInstance.setPhotourl(photourl);
			wardInstance.setPhoto64(urlIMG64);
			wardInstance.setPhoto32(urlIMG32);
			
			wardInstance.setLocator(locator);
			
			wardService.create(wardInstance);
			
			

	        Guardian guardian = guardianService.get(request.getUserPrincipal().getName());
	        
	        guardian.getWards().add(wardService.findByName(wardForm.getName()));
	        
	        guardianService.update(guardian);
	        
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult
					.addError(new FieldError("resource", "name", messageSource
							.getMessage("resource.nameExists", null, null)));
			//return Constants.WARDHOME + Constants.CREATE;
			//return validateObject(bindingResult,700);
		}
		return validateObject(bindingResult,600);
	}
	
	@RequestMapping(value = Constants.SEARCH, method = RequestMethod.GET)
	public Model searchWard(HttpServletRequest request, Model model) {
		Set<Ward> wards = guardianService.get(request.getUserPrincipal().getName()).getWards();
		model.addAttribute("wardList",wards);
		return model;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/edit")
	public Model editWard(@RequestParam(value = "name", required = true) String wardname, Model model) {
		Ward ward = wardService.findByName(wardname);
		WardForm wardForm = new WardForm();
		wardForm.setId(ward.getId());
		wardForm.setDeviceid(ward.getLocator().getImeiCode());
		wardForm.setName(ward.getName());
		wardForm.setPhotourl(ward.getPhotourl());
		model.addAttribute("ward",ward);
		model.addAttribute("create", false);
		model.addAttribute("wardForm", wardForm);
		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST,value="/edit")
	public @ResponseBody CoordSafeResponse updateWard(@Valid @ModelAttribute WardForm ward2, BindingResult bindingResult,HttpServletRequest request,HttpSession session) {
		/*if (bindingResult.hasErrors()) {
			return "vehicle/edit";
		}*/
		
		Ward ward = (Ward)session.getAttribute("ward");
		CoordSafeResponse result = null;
		
		try {
			
			String name = ward2.getName();
			if(wardService.findByName(name) != null && !name.equalsIgnoreCase(ward.getName())){
				bindingResult
				.addError(new FieldError("wardForm", "name", messageSource
						.getMessage("wardForm.name.exit", null, null)));
				return validateObject(bindingResult, 700);
				
			}
			
			//delete the old imgs of ward
			
			String photoPath  = request.getSession().getServletContext().getRealPath("/");
			
			File photourlDest = new File(photoPath + ward.getPhotourl());
			deleteFile(photourlDest);
			
			File photo32Dest = new File(photoPath + ward.getPhoto32());
			deleteFile(photo32Dest);
			File photo64Dest = new File(photoPath + ward.getPhoto64());
			deleteFile(photo64Dest);
			/////////
			
			if (request instanceof MultipartHttpServletRequest){
		           MultipartHttpServletRequest multipartRequest =
		                        (MultipartHttpServletRequest) request;
		           MultipartFile file = multipartRequest.getFiles("photo").get(0);
		           log.info("The Img for request is " + file.getOriginalFilename());

		    }
			MultipartFile imgFile = ward2.getPhoto();

			if( imgFile == null || imgFile.getSize() == 0){
				log.info("No Img is provided, only ward's name is changed!");
				ward.setName(ward2.getName());
				
			}
			else{
				
				log.info("The IMG is " + imgFile.getOriginalFilename()+ " The size is " + imgFile.getSize());

				//String photoPath  = request.getSession().getServletContext().getRealPath("/");
				
				log.info("path = " + photoPath);
				File dest = new File(photoPath +tmp + imgFile.getOriginalFilename());
				log.info("The file dest is " + dest.getAbsolutePath()+dest.getPath());
				imgFile.transferTo(dest);
				String photourl =  tmp + imgFile.getOriginalFilename();
				String urlIMG64 =  tmp + imgservice.cutImage(dest, 100, 100);
				String urlIMG32 =  tmp + imgservice.cutImage(dest, 32, 32);

				ward.setName(ward2.getName());
				//The img can be taken from generated img file instead of url;
				//wardInstance.setPhotourl(wardForm.getPhotourl());
				ward.setPhotourl(photourl);
				ward.setPhoto64(urlIMG64);
				ward.setPhoto32(urlIMG32);
				
			}

			/////////////////////
			
			wardService.update(ward);
			
			result = new CoordSafeResponse(CsStatusCode.SUCCESS, String.format("Ward %s is updated", ward2.getName()));
		} catch (Exception e) {
			log.error("error update ward: ");
			e.printStackTrace();
			
			//String [] args = {vehicle2.getName()};
			
			bindingResult.addError(new FieldError("vehicle", "name",
					messageSource.getMessage("vehicle.nameExists", null, null)));
			
			result = new CoordSafeResponse(CsStatusCode.FAIL, String.format("Failed to update Ward %s", ward2.getName()));
			
		}

		return result;
	}	
	
	@RequestMapping(method = RequestMethod.GET,value="/delete")
	public Model deleteVehicle(@RequestParam(value = "name", required = true) String name, Model model) {
		
		log.info("Ward Deleting...");
		model.addAttribute("ward", wardService.findByName(name));
		model.addAttribute("name", name);
		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST,value="/delete")
	public String deleteVehicle1(@RequestParam(value = "name", required = true) String name, HttpSession session,HttpServletRequest req) {
		Ward ward = (Ward)session.getAttribute("ward");
		Guardian guardian = guardianService.get(req.getUserPrincipal().getName());
		
		guardian.getWards().remove(ward);
		guardianService.update(guardian);
		try {
			wardNotificationService.delete(String.valueOf(ward.getId()));
			//delete the geofence and ward table
			Set<Geofence> gfs = ward.getGeofences();
			for(Geofence gf : gfs){
				gf.getWards().remove(ward);
				geofenceService.update(gf);
			}
			
			//should delete the imgs of ward;
			
			String photoPath  = req.getSession().getServletContext().getRealPath("/");
			
			File photourlDest = new File(photoPath + ward.getPhotourl());
			deleteFile(photourlDest);
			
			File photo32Dest = new File(photoPath + ward.getPhoto32());
			deleteFile(photo32Dest);
			File photo64Dest = new File(photoPath + ward.getPhoto64());
			deleteFile(photo64Dest);
			

			
			
			////
			wardService.delete(ward);
		} catch (WardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		return "redirect:/" + "wards/search?guardian=";
	}
	
	private boolean deleteFile(File dest){
		if(!dest.exists()){
			log.error("The file is not existed, delete failed!");
			return false;
		}else{
			if(dest.isFile()){
				dest.delete();
				log.info("The file" + dest.getName() + " has been deleted!");
				return true;
			}
		}
		
		return false;
	}
	
	//Jcrop
	@RequestMapping("uploadHeaderPicTmp")
	@ResponseBody
	public String uploadHeaderPic(String inputId, MultipartHttpServletRequest request) {
/*		try {
			MultipartFile realPicFile = request.getFile(inputId);
			InputStream in = realPicFile.getInputStream();
			String path = request.getSession().getServletContext().getRealPath("/");
			path += TMP;
			User loginUser = SystemUtil.getLoginUser(request.getSession());
			String fileName = loginUser.getName() + "." + FilenameUtils.getExtension(realPicFile.getOriginalFilename());
			File f = new File(path + "/" + fileName);
			FileUtils.copyInputStreamToFile(in, f);
			return "{\"path\" : \"" + TMP + "/" + fileName + "\"}";
		} catch (Exception e) {
			LOG.error("upload header picture error : ", e);
		}*/
		return null;
	}

/*	@RequestMapping("uploadHeaderPic")
	@ResponseBody
	public GeneralMessage cutImage(String srcImageFile, int x, int y, int destWidth, int destHeight, int srcShowWidth, int srcShowHeight,
			HttpServletRequest request) {
		try {
			String path = request.getSession().getServletContext().getRealPath("/");
			Image img;
			ImageFilter cropFilter;
			String srcFileName = FilenameUtils.getName(srcImageFile);
			// 读取源图像  
			File srcFile = new File(path + TMP + "/" + srcFileName);

			BufferedImage bi = ImageIO.read(srcFile);
			//前端页面显示的并非原图大小，而是经过了一定的压缩，所以不能使用原图的宽高来进行裁剪，需要使用前端显示的图片宽高
			int srcWidth = bi.getWidth(); // 源图宽度  
			int srcHeight = bi.getHeight(); // 源图高度
			if (srcShowWidth == 0)
				srcShowWidth = srcWidth;
			if (srcShowHeight == 0)
				srcShowHeight = srcHeight;

			if (srcShowWidth >= destWidth && srcShowHeight >= destHeight) {
				Image image = bi.getScaledInstance(srcShowWidth, srcShowHeight, Image.SCALE_DEFAULT);//获取缩放后的图片大小  
				cropFilter = new CropImageFilter(x, y, destWidth, destHeight);
				img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, null); // 绘制截取后的图  
				g.dispose();

				String ext = FilenameUtils.getExtension(srcImageFile);

				path += HEADER_PIC;
				User loginUser = SystemUtil.getLoginUser(request.getSession());
				String fileName = loginUser.getName() + "." + ext;
				File destImageFile = new File(path + "/" + fileName);
				// 输出为文件  
				ImageIO.write(tag, ext, destImageFile);

				loginUser.setPicPath(SystemConst.SYSTEM_CONTEXT_PATH_VALUE + HEADER_PIC + "/" + fileName);
				userService.update(loginUser);
				// 删除原临时文件
				srcFile.delete();

				GeneralMessage msg = new GeneralMessage();
				msg.setCode(GeneralMessage.SUCCESS);
				msg.setMsg("上传成功！");
				return msg;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/
}
