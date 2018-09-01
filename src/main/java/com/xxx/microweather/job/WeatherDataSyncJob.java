package com.xxx.microweather.job;

import com.xxx.microweather.service.CityDataService;
import com.xxx.microweather.service.WeatherDataService;
import com.xxx.microweather.vo.City;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

/**
 * @author xin
 * @date 2018/9/1 17:29
 */
public class WeatherDataSyncJob extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(WeatherDataSyncJob.class);

    @Autowired
    private CityDataService cityDataService;

    @Autowired
    private WeatherDataService weatherDataService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("Weather Data Sync Job. Start!");
        // get city list
        List<City> cityList = null;

        try {
            cityList = cityDataService.listCity();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // enumerate city list, update weather
        for (City city : cityList) {
            String cityId = city.getCityId();
            logger.info("Weather Data Sync Job, city id:" + cityId);
            weatherDataService.syncDataByCityId(cityId);
        }

        logger.info("Weather Data Sync Job. End!");
    }
}
