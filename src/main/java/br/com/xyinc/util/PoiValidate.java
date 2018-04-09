package br.com.xyinc.util;

import br.com.xyinc.exception.PoiValidationException;
import br.com.xyinc.model.Codes;
import br.com.xyinc.model.Poi;

public class PoiValidate {

	public static void validatePoiObject(Poi poi, boolean isNew) throws PoiValidationException {
		validateName(poi.getName());
		validateCoordinate("x", poi.getX());
		validateCoordinate("y", poi.getY());
		validateId(poi.getId(), isNew);
	}

	private static void validateId(Long id, boolean isNew) throws PoiValidationException {
		if(isNew) {
			if(id != null) {
				throw new PoiValidationException(Codes.NOT_NULL, "ID is not null");
			}
		}
	}

	private static void validateCoordinate(String name, Long value) throws PoiValidationException {
		if(value == null) {
			throw new PoiValidationException(Codes.NULL_OR_EMPTY, name + " coordinate is null or empty.");
		} 
		if(value < 0) {
			throw new PoiValidationException(Codes.NEGATIVE_NUMBER, name + " coordinate is negative: " + value);
		}
	}

	private static void validateName(String name) throws PoiValidationException {
		if(name == null || name.trim().isEmpty()) {
			throw new PoiValidationException(Codes.NULL_OR_EMPTY, "Name is null or empty.");
		}
	}
}
