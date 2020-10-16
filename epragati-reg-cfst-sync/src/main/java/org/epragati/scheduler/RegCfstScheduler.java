package org.epragati.scheduler;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.epragati.auditService.AuditLogsService;
import org.epragati.constants.Schedulers;
import org.epragati.reports.service.RtaReportService;
import org.epragati.service.RegCfstService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RegCfstScheduler {
	
	private static final Logger logger = LoggerFactory.getLogger(RegCfstScheduler.class);

	@Value("${scheduler.cfst.sync.process.isEnable}")
	private Boolean isCfstSyncProcessSchedulerEnable;

	@Autowired
	private AuditLogsService auditLogsService;
	
	@Autowired
	private RegCfstService cfstService;
	
	@Value("${reg.services.reports.data.isEnable}")
	private Boolean isServicesReportsDataEnabled;
	
	@Autowired
	private RtaReportService rtaReportService;


	/**
	 * Do cfst sync process
	 */
	@Scheduled(cron = "${scheduler.cfst.sync.process.interval}")
	public void docfstSyncProcessScheduler() {
		
		LocalDateTime startTime=LocalDateTime.now();
		LocalDateTime endTime=null;
		Boolean isExecuteSucess=true;
		String error=null;
		if (isCfstSyncProcessSchedulerEnable) {
			logger.info("Cfst sync process scheduler starting at time is now {}", LocalDateTime.now());

			try {
				 List<String> prnumbers = Collections.EMPTY_LIST;
					cfstService.docfstSyncProcess(prnumbers,Boolean.TRUE);
			

			} catch (Exception ex) {
				error= ex.getMessage();
				isExecuteSucess=false;
				logger.error("Exception occured while cfst sync process scheduler running", ex);
			}
			endTime=LocalDateTime.now();
			logger.info("Cfst sync process scheduler end at time is now {}", endTime);

		} else {
			isExecuteSucess=false;
			endTime=LocalDateTime.now();
			error= "Cfst sync process scheduler is skiped at time is now {}"+ endTime;
			logger.info("Cfst sync process scheduler is skiped at time is now {}", endTime);
		}
		auditLogsService.saveScedhuleLogs(Schedulers.CFSTSYNC,startTime,endTime,isExecuteSucess,error,null);
	}
	@Scheduled(cron = "${reg.services.reports.data.sync}")
	public void regSerDataSync() {
		
		LocalDateTime startTime=LocalDateTime.now();
		LocalDateTime endTime=null;
		Boolean isExecuteSucess=true;
		String error=null;
		if (isServicesReportsDataEnabled) {
			logger.info("Registration Serevices reports data scheduler starting at time is now {}", LocalDateTime.now());

			try {
				rtaReportService.regServiceReportDataSync();
			} catch (Exception ex) {
				error= ex.getMessage();
				isExecuteSucess=false;
				logger.error("Exception occured while releasing lock for users roles ", ex);
			}
			endTime=LocalDateTime.now();
			logger.info("Registration role unlock scheduler end at time is now {}", endTime);
		} else {
			isExecuteSucess=false;
			endTime=LocalDateTime.now();
			logger.info("Registration role unlock scheduler is skiped at time is now {}", LocalDateTime.now());
		}
		auditLogsService.saveScedhuleLogs(Schedulers.APPLICATIONLOCKINGMECHANISMS,startTime,endTime,isExecuteSucess,error,null);
	}

}
