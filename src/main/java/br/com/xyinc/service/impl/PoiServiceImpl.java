package br.com.xyinc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.xyinc.exception.PoiValidationException;
import br.com.xyinc.model.Codes;
import br.com.xyinc.model.Poi;
import br.com.xyinc.model.ResponseModel;
import br.com.xyinc.repository.PoiRepository;
import br.com.xyinc.service.PoiService;
import br.com.xyinc.util.PoiValidate;

@Component
public class PoiServiceImpl implements PoiService{

	@Autowired
	private PoiRepository poiRepository;

	@Override
	public ResponseModel create(Object obj) {
		Poi poi = convertObjectToPoi(obj);
		ResponseModel response = null;
		try {
			PoiValidate.validatePoiObject(poi, true);
			poi = poiRepository.save(poi);
			response = new ResponseModel(Codes.SUCCESS, "Success saving POI.", poi.getId());
		} catch (PoiValidationException pve) {
			response = new ResponseModel(pve.getCode(), pve.getMessage());
		} catch (Exception e) {
			response = new ResponseModel(Codes.OTHER_ERROR, "Error while saving POI. Cause: " + e.getMessage());
		}
		return response;
	}

	@Override
	public ResponseModel update(Long id, Object obj) {
		Poi poi = convertObjectToPoi(obj);
		if(!poiRepository.exists(id)) {
			return new ResponseModel(Codes.OBJECT_NOT_FOUND, "POI not found, id: " + id);
		}
		poi.setId(id);
		ResponseModel response = null;

		try {
			PoiValidate.validatePoiObject(poi, false);
			poiRepository.save(poi);
			response = new ResponseModel(Codes.SUCCESS, "Success updating POI.", poi.getId());
		} catch (PoiValidationException pve) {
			response = new ResponseModel(pve.getCode(), pve.getMessage());
		} catch (Exception e) {
			response = new ResponseModel(Codes.OTHER_ERROR, "Error while updating POI. Cause: " + e.getMessage());
		}
		return response;
	}

	@Override
	public Object getById(Long id) {
		Poi poi = null;
		try {
			poi = poiRepository.findOne(id);
			if(poi == null) {
				return new ResponseModel(Codes.OBJECT_NOT_FOUND, "POI not found, id: " + id);
			}
		} catch (Exception e) {
			return new ResponseModel(Codes.OTHER_ERROR, "Error while finding POI. Cause: " + e.getMessage());
		}
		return poi;
	}

	@Override
	public ResponseModel delete(Long id) {
		if(!poiRepository.exists(id)) {
			return new ResponseModel(Codes.OBJECT_NOT_FOUND, "POI not found, id: " + id);
		}
		
		ResponseModel response = null;
		try {
			poiRepository.delete(id);
			response = new ResponseModel(Codes.SUCCESS, "Success removing POI.", id);
		} catch (Exception e) {
			response = new ResponseModel(Codes.OTHER_ERROR, "Error while updating POI. Cause: " + e.getMessage());
		}
		return response;
	}

	@Override
	public Object getAll() {
		List<Poi> allPois;
		try {
			allPois = (List<Poi>) poiRepository.findAll();
		} catch (Exception e) {
			return new ResponseModel(Codes.OTHER_ERROR, "Error while finding all POI's. Cause: " + e.getMessage());
		}
		return allPois;
	}

	private Poi convertObjectToPoi(Object obj) {
		return (Poi) obj;
	}

	@SuppressWarnings("unchecked")
	public Object getAllByDistance(Long x, Long y, Long distance) {
		Object obj = getAll();
		if(obj instanceof ResponseModel) {
			return obj;
		}
		List<Poi> allPois = (List<Poi>) obj;
		return returnPoisByDistance(x, y, distance, allPois);
	}

	private List<Poi> returnPoisByDistance(Long x, Long y, Long distance, List<Poi> allPois) {
		Long squaredDistance = distance * distance;
		List<Poi> poisOnCircle = new ArrayList<Poi>();
		for(Poi poi : allPois) {
			Long deltaX = x - poi.getX();
			Long deltaY = y - poi.getY();
			if(isPoiOnCircle(deltaX, deltaY, squaredDistance)) {
				poisOnCircle.add(poi);
			}
		}
		return poisOnCircle;
	}
	
	private boolean isPoiOnCircle(Long deltaX, Long deltaY, Long squaredDistance) {
		Long poiSquaredDistance = deltaX * deltaX + deltaY * deltaY;
		if(poiSquaredDistance <= squaredDistance) {
			return true;
		}
		return false;
	}
	
}
