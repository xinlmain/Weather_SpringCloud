package com.xxx.microweather.service;

import com.xxx.microweather.vo.Weather;

/**
 * @author xin
 * @date 2018/9/1 23:26
 */
public interface WeatherReportService {

    /**
     * 根据city Id返回天气
     * @param cityId
     * @return
     */
    Weather getDataByCityId(String cityId);
}
