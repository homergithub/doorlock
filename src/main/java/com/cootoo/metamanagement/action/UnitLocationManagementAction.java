package com.cootoo.metamanagement.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.cootoo.common.util.SessionUtil;
import com.cootoo.metamanagement.domain.UnitLocation;
import com.cootoo.metamanagement.service.UnitLocationManagementService;
import com.cootoo.systemmanagement.domain.LoginRole;

@RequestMapping(value="/unitLocationManagement/")
@Controller
public class UnitLocationManagementAction{

	@Autowired
	private UnitLocationManagementService unitLocationManagementServiceImpl;
	
	
	@RequestMapping(value="addUnitLocation",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addUnitLocation(HttpServletRequest request){
		
		List<UnitLocation> unitLocationList = new ArrayList<UnitLocation>();
		String locationID = request.getParameter("locationID");
		String unitName = request.getParameter("unitName");
		String parentNode = request.getParameter("parentNode");
		String unitType = request.getParameter("unitType");
		String strUnitSize = request.getParameter("unitSize");
		Integer unitSize = null;
		if(null != strUnitSize &&  !"".equals(strUnitSize)){
			unitSize = Integer.parseInt(strUnitSize);
		}
		String sex = request.getParameter("sex");
		String orgID = request.getParameter("orgID");
		
		UnitLocation unitLocation = new UnitLocation(locationID, unitName, parentNode, null, unitType, orgID, unitSize, sex);
		unitLocationList.add(unitLocation);
		Map<String, Object> result =  unitLocationManagementServiceImpl.addUnitLocation(unitLocationList);
		return result;
	}
	
	
	@RequestMapping(value="importUnitLocation",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> importUnitLocation(HttpServletRequest request){
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file");
		//获取登陆着orgID
		Session session = SessionUtil.getSession();
		LoginRole login = (LoginRole) session.getAttribute("login");
		String orgID = login.getOrgID();
		Map<String, Object> result = unitLocationManagementServiceImpl.addUnitLocation(file, orgID);
		return result;
	}
	
	
	
	@RequestMapping(value="deleteUnitLocation",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteUnitLocation(HttpServletRequest request){
		
		String strLocationIDs = request.getParameter("locationIDs");
		List<String> locationIDList = Arrays.asList(strLocationIDs.split(","));
		Map<String, Object> result = unitLocationManagementServiceImpl.deleteUnitLocation(locationIDList);
		return result;	
	}
	
	
	@RequestMapping(value="modifyUnitLocation",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> modifyUnitLocation(HttpServletRequest request){
		
		String locationID = request.getParameter("locationID");
		String unitName = request.getParameter("unitName");
		String unitType = request.getParameter("unitType");
		String strUnitSize = request.getParameter("unitSize");
		Integer unitSize = null;
		if(null != strUnitSize && !"".equals(strUnitSize)){
			unitSize = Integer.parseInt(strUnitSize);
		}
		String sex = request.getParameter("sex");
		String orgID = request.getParameter("orgID");
		UnitLocation unitLocation = new UnitLocation(locationID, unitName, null, null, unitType, orgID, unitSize, sex);
		
		Map<String, Object> result = unitLocationManagementServiceImpl.modifyUnitLocation(unitLocation);
		return result;
	}
	
	
	@RequestMapping(value="getSchoolAreaByLoginID",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getSchoolAreaByLoginID(HttpServletRequest request){
		
		//从session中获取loginID
		Session session = SessionUtil.getSession();
		LoginRole loginRole = (LoginRole) session.getAttribute("login");
		Map<String, Object> result = unitLocationManagementServiceImpl.getSchoolAreaByLoginID(loginRole.getLoginID());
		return result;
	}
	
	@RequestMapping(value="getBuildingByLoginID",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getBuildingByLoginID(HttpServletRequest request){
		Session session = SessionUtil.getSession();
		LoginRole loginRole = (LoginRole) session.getAttribute("login");
		Map<String, Object> result = unitLocationManagementServiceImpl.getBuildingByLoginID(loginRole.getLoginID());
		return result;
	}
	
	@RequestMapping(value="getFloorOrRoomByLocationID",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getFloorOrRoomByLocationID(HttpServletRequest request){
		String locationID = request.getParameter("locationID");
		Map<String, Object> result = unitLocationManagementServiceImpl.getFloorOrRoomByLocationID(locationID);
		return result;
	}
	
	
	
	
	
	
	
}
