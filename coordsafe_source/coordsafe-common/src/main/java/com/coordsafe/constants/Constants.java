package com.coordsafe.constants;

public class Constants {

	/*
	 * Path name for static resources
	 */
	public static final String RESOURCEPATH = "/resources";
	public static final String RESOURCEIMAGEPATH = "/resources/images";
	
	public static final String RESTPATH = "rest";
	
	/*
	 * 
	 */
	public static final String CREATEPARAM = "new";
	
	public static final String LOST = "lost";
		
	/*
	 * Login Controller
	 */
	public static final String LOGINHOME = "login";
	
	/*
	 * Mappings for User Controller.
	 * 
	 * If changes are to be made, remember to change the relevant tiles 
	 * configuration located at '../WEB-INF/views/tiles/user/tiles.xml'
	 */
	public static final String USERHOME = "user";
	public static final String USERCREATE = "/create";
	public static final String USEREDIT = "/edit";
	public static final String USERRESETPASSWORD = "/resetPassword";
	public static final String USERSEARCH = "/search";
	public static final String USERDISABLE = "/disable";
	public static final String USERENABLE = "/enable";
	public static final String USERDELETE = "/delete";
	public static final String USERASSIGNROLES = "/assignRoles";
	public static final String USERASSIGNGROUPS = "/assignGroups";
	public static final String USERJOINORGANIZATION = "/joinOrganization";
	public static final String USERPARAM = "username";
	
	/*
	 * Mappings for Organization Controller.
	 * 
	 * If changes are to be made, remember to change the relevant tiles 
	 * configuration located at '../WEB-INF/views/tiles/organization/tiles.xml'
	 */
	public static final String ORGANIZATIONHOME = "organization";
	public static final String ORGANIZATIONCREATE = "/create";
	public static final String ORGANIZATIONSEARCH = "/search";
	public static final String ORGANIZATIONEDIT = "/edit";
	public static final String ORGANIZATIONDELETE = "/delete";
	public static final String ORGANIZATIONPARAM = "name";
	
	/*
	 * Mappings for Role Controller.
	 * 
	 * If changes are to be made, remember to change the relevant tiles 
	 * configuration located at '../WEB-INF/views/tiles/role/tiles.xml'
	 */
	public static final String ROLEHOME = "role";
	public static final String ROLECREATE = "/create";
	public static final String ROLESEARCH = "/search";
	public static final String ROLEEDIT = "/edit";
	public static final String ROLEDELETE = "/delete";
	public static final String ROLEUSERS = "/users";
	public static final String ROLEASSIGNPERMISSIONS = "/assignPermissions";
	public static final String ROLEPARAM = "name";
	public static final String ROLETYPE = "ROLE_TYPE";
	
	/*
	 * Mappings for Resource Controller.
	 * 
	 * If changes are to be made, remember to change the relevant tiles 
	 * configuration located at '../WEB-INF/views/tiles/resource/tiles.xml'
	 */
	public static final String RESOURCEHOME = "resource";
	public static final String RESOURCECREATE = "/create";
	public static final String RESOURCESEARCH = "/search";
	public static final String RESOURCEEDIT = "/edit";
	public static final String RESOURCEDELETE = "/delete";
	public static final String RESOURCEPARAM = "name";
	
	/*
	 * Mappings for Group Controller.
	 * 
	 * If changes are to be made, remember to change the relevant tiles 
	 * configuration located at '../WEB-INF/views/tiles/group/tiles.xml'
	 */
	public static final String GROUPHOME = "group";
	public static final String GROUPCREATE = "/create";
	public static final String GROUPSEARCH = "/search";
	public static final String GROUPEDIT = "/edit";
	public static final String GROUPDELETE = "/delete";
	public static final String GROUPPARAM = "name";
	public static final String GROUPASSIGNROLES = "/assignRoles";
	public static final String GROUPASSIGNUSERS = "/assignUsers";
	public static final String GROUPJOINORGANIZATION = "/joinOrganization";
	
	/*
	 * Mappings for Code Table Controller.
	 * 
	 * If changes are to be made, remember to change the relevant tiles 
	 * configuration located at '../WEB-INF/views/tiles/codetable/tiles.xml'
	 */
	public static final String CODETABLEHOME = "codetable";
	public static final String CODETABLECREATE = "/create";
	public static final String CODETABLESEARCH = "/search";
	public static final String CODETABLEEDIT = "/edit";
	public static final String CODETABLEDELETE = "/delete";
	public static final String CODETABLETYPEPARAM = "type";
	public static final String CODETABLECODEPARAM = "code";

	public static final String WARDHOME = "ward";
	public static final String CREATE = "/create";
	public static final String SEARCH = "/search";
	public static final String DELETE = "/delete";
	public static final String EDIT = "/edit";

	public static final String LOCATORHOME = "locator";
	public static final String MANUFACTURER = "MANUFACTURER_TYPE";
	public static final String MODEL = "MODEL_TYPE";
	public static final String LOCATE = "/locateOnMap";
	
	/*
	 * Mappings for Vehicle Table Controller.
	 * 
	 * If changes are to be made, remember to change the relevant tiles 
	 * configuration located at '../WEB-INF/views/tiles/vehicle/tiles.xml'
	 */
	public static final String VEHICLEHOME = "vehicle";
	public static final String VEHICLECREATE = "/create";
	public static final String VEHICLESEARCH = "/search";
	public static final String VEHICLEEDIT = "/edit";
	public static final String VEHICLEDELETE = "/delete";
	public static final String VEHICLETYPEPARAM = "type";
	public static final String VEHICLE_TYPE = "VEHICLE_TYPE";

	public static final String GEOREADY_PATH = "geoready";
	public static final String SAFELINK_PATH = "safe-link";
	
	
	/*
	 * For the Panic_Alarm and Circle_Event
	 * 
	 */
	public static final String ALARMBATTERY = "AlarmBattery";
	public static final String ALARMBATTERYNOGPS = "AlarmBatteryNOGPS";
	public static final String ALARM = "Alarm";
	public static final String ALARMNOGPS = "AlarmNOGPS";
	
	public static final String CIRCLE_IN = "CircleIn";
	public static final String CIRCLE_OUT = "CircleOut";
	
	public static final String GEOFENCE_TYPE_CIRCLE = "Circle";
	public static final String GEOFENCE_TYPE_DANGEROUS_ZONE = "DangerousZone";
	public static final String GEOFENCE_TYPE_SAFE_ZONE = "SafeZone";
	
	public static final String WARD_STATUS_ONLINE = "online";
	public static final String WARD_STATUS_OFFLINE = "offline";
	public static final String WARD_STATUS_BATTERY_LOW = "lowBattery";
	public static final String WARD_STATUS_BATTERY_FULL = "fullBattery";
	
	public static final String TRIP_TYPE_FINISHED = "Finished";
	public static final String TRIP_TYPE_UNFINISHED = "UnFinished";
}
