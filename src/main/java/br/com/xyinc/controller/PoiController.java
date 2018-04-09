package br.com.xyinc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.xyinc.model.Poi;
import br.com.xyinc.model.ResponseModel;
import br.com.xyinc.service.PoiService;

@RestController
@RequestMapping("/poi")
public class PoiController {

	@Autowired
	private PoiService poiService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseModel create(@RequestBody Poi poi) {
		return poiService.create(poi);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseModel update(@PathVariable Long id, @RequestBody Poi poi) {
		return poiService.update(id, poi);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody Object getById(@PathVariable Long id) {
		return poiService.getById(id);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody Object getAll() {
		return poiService.getAll();
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseModel delete(@PathVariable Long id) {
		return poiService.delete(id);
	}

	@GetMapping(value = "/by-distance", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody Object getAllByDistance(@RequestParam(name = "x") Long x, @RequestParam(name = "y") Long y,
			@RequestParam(name = "distance") Long distance) {
		return poiService.getAllByDistance(x, y, distance);
	}
}
