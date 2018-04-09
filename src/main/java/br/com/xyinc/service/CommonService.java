package br.com.xyinc.service;

import br.com.xyinc.model.ResponseModel;

public interface CommonService {

	ResponseModel create(Object obj);
	
	ResponseModel update(Long id, Object obj);
	
	Object getById(Long id);
	
	ResponseModel delete(Long id);
	
	Object getAll();
}
