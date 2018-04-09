package br.com.xyinc.service;

public interface PoiService extends CommonService{
	Object getAllByDistance(Long x, Long y, Long distance);
	
}
