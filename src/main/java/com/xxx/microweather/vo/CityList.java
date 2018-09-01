package com.xxx.microweather.vo;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author xin
 * @date 2018/9/1 17:58
 */
@XmlRootElement(name = "c")
@XmlAccessorType(XmlAccessType.FIELD)
public class CityList {

    @XmlElement(name = "d")
    private List<City> cityList;

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }
}
