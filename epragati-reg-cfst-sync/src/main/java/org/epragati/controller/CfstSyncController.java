package org.epragati.controller;



import java.util.List;
import java.util.Map;

import org.epragati.constants.MessageKeys;
import org.epragati.master.vo.CfstSyncRegstrationVO;
import org.epragati.service.CfstSyncService;
import org.epragati.service.RegCfstService;
import org.epragati.util.AppMessages;
import org.epragati.util.GateWayResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
/**
 * 
 * @author krishnarjun.pampana
 *
 */
@CrossOrigin
@RestController
public class CfstSyncController {
	
	private static final Logger logger = LoggerFactory.getLogger(CfstSyncController.class);
	
	@Autowired
	private  CfstSyncService cfstSyncService;
	
	@Autowired
	private AppMessages appMessages;
	

	@Autowired
	private RegCfstService regCfstService;
	
	
	@PostMapping(value = "saveSyncData", produces = { MediaType.APPLICATION_JSON_VALUE })
	public GateWayResponse<?> saveCfstSyncData(@RequestBody List<CfstSyncRegstrationVO> registrationDetailsVOList ) {
			Map<String,String> result = null;
		if(registrationDetailsVOList!=null){
			try {
				result = cfstSyncService.saveRegDetails(registrationDetailsVOList);
				if(result!=null){
					return new GateWayResponse<>(HttpStatus.OK,result,appMessages.getResponseMessage(MessageKeys.MESSAGE_SUCCESS));	
				}
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
		}
		return new GateWayResponse<>(HttpStatus.NOT_FOUND,result,appMessages.getResponseMessage(MessageKeys.MESSAGE_REG_SAVE_FAILED));	
	}
	
	/*
	 * @PostMapping(value = "saveDataToCfst", produces = {
	 * MediaType.APPLICATION_JSON_VALUE }) public GateWayResponse<?>
	 * postDatatoCfst(@RequestBody List<String>
	 * prNumbers,@RequestHeader("Authorization") String authString ) {
	 * 
	 * if(prNumbers!=null){ try {
	 * if(!authString.equals("cfstDataPostAttryttRjujjjJwqeurwqfrjUjdsfjdsfjN")) {
	 * return new
	 * GateWayResponse<>(HttpStatus.BAD_REQUEST,"Authorization Required"); }
	 * regCfstService.docfstSyncProcess(prNumbers,Boolean.FALSE);
	 * 
	 * return new
	 * GateWayResponse<>(HttpStatus.OK,appMessages.getResponseMessage(MessageKeys.
	 * MESSAGE_SUCCESS));
	 * 
	 * } catch (Exception e) { logger.info(e.getMessage()); } } return new
	 * GateWayResponse<>(HttpStatus.NOT_FOUND,appMessages.getResponseMessage(
	 * MessageKeys.MESSAGE_REG_SAVE_FAILED)); }
	 */
	
	
	
}