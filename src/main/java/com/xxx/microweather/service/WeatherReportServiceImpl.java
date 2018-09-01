package com.xxx.microweather.service;

import com.xxx.microweather.vo.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xin
 * @date 2018/9/1 23:27
 */
@Service
public class WeatherReportServiceImpl implements WeatherReportService {

    @Autowired
    private WeatherDataService weatherDataService;

    @Override
    public Weather getDataByCityId(String cityId) {
        return weatherDataService.getDataByCityId(cityId).getData();
    }
}
