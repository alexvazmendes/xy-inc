package br.com.xyinc.service;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.xyinc.model.Codes;
import br.com.xyinc.model.Poi;
import br.com.xyinc.model.ResponseModel;
import br.com.xyinc.repository.PoiRepository;
import br.com.xyinc.service.PoiService;
import br.com.xyinc.service.impl.PoiServiceImpl;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class PoiServiceTest {

	@Configuration
	static class PoiServiceTestConfiguration {
		@Bean
		public PoiService poiService() {
			return new PoiServiceImpl();
		}
		@Bean
		public PoiRepository poiRepository() {
			return Mockito.mock(PoiRepository.class);
		}
	}
	
	@Autowired
	private PoiService poiService;
	@Autowired
	private PoiRepository poiRepository;
	
	private Poi newPoi;
	private Poi successPoi;
	
	@Before
	public void setup() {
		newPoi = new Poi("local1", 1L, 1L);
		successPoi = new Poi(1L, "local1", 1L, 1L);
	}
	
	@Test
	public void createPoi() {
		Mockito.when(poiRepository.save(newPoi)).thenReturn(successPoi);
		ResponseModel rm = poiService.create(newPoi);
		assertEquals(Codes.SUCCESS, rm.getStatus());
	}
	
	@Test
	public void createPoiWithSavingError() {
		ResponseModel rm = poiService.create(newPoi);
		assertEquals(Codes.OTHER_ERROR, rm.getStatus());
	}
	
	@Test
	public void createPoiWithValidationError() {
		ResponseModel rm;
		rm = poiService.create(new Poi("", 0L, 0L));
		assertEquals(Codes.NULL_OR_EMPTY, rm.getStatus());
		rm = poiService.create(new Poi("local", -1L, 0L));
		assertEquals(Codes.NEGATIVE_NUMBER, rm.getStatus());
		rm = poiService.create(new Poi("local", 0L, -1L));
		assertEquals(Codes.NEGATIVE_NUMBER, rm.getStatus());
	}
	
	@Test
	public void updatePoi() {
		Mockito.when(poiRepository.save(successPoi)).thenReturn(successPoi);
		Mockito.when(poiRepository.exists(successPoi.getId())).thenReturn(true);
		ResponseModel rm = poiService.update(1L, successPoi);
		assertEquals(Codes.SUCCESS, rm.getStatus());
	}
	
	@Test
	public void updatePoiNotFound() {
		Mockito.when(poiRepository.exists(Matchers.anyLong())).thenReturn(false);
		
		ResponseModel rm;
		rm = poiService.update(1L, new Poi("local", 0L, 0L));
		assertEquals(Codes.OBJECT_NOT_FOUND, rm.getStatus());
	}
	
	@Test
	public void getById() {
		Mockito.when(poiRepository.findOne(Matchers.anyLong())).thenReturn(successPoi);
		Poi poi = (Poi) poiService.getById(1L);
		assertTrue(poi.getId() == 1);
	}
	
	@Test
	public void getByIdNotFound() {
		Mockito.when(poiRepository.findOne(Matchers.anyLong())).thenReturn(null);
		ResponseModel rm = (ResponseModel) poiService.getById(1L);
		assertEquals(Codes.OBJECT_NOT_FOUND, rm.getStatus());
	}
	
	@Test
	public void deletePoi() {
		Mockito.when(poiRepository.save(successPoi)).thenReturn(null);
		Mockito.when(poiRepository.exists(successPoi.getId())).thenReturn(true);
		ResponseModel rm = poiService.delete(1L);
		assertEquals(Codes.SUCCESS, rm.getStatus());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getAll() {
		Mockito.when(poiRepository.findAll()).thenReturn(returnPoiList());
		List<Poi> list = (List<Poi>) poiService.getAll();
		assertEquals(4, list.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getAllByDistance() {
		Mockito.when(poiRepository.findAll()).thenReturn(returnPoiList());
		List<Poi> list = (List<Poi>) poiService.getAllByDistance(20L, 10L, 10L);
		assertEquals(2, list.size());
		assertEquals(1L, list.get(0).getId().longValue());
		assertEquals(3L, list.get(1).getId().longValue());
	}
	
	private List<Poi> returnPoiList() {
		Poi poi1 = new Poi(1L, "local1", 27L, 12L);
		Poi poi2 = new Poi(2L, "local2", 31L, 18L);
		Poi poi3 = new Poi(3L, "local3", 15L, 12L);
		Poi poi4 = new Poi(4L, "local4", 19L, 21L);
		return Arrays.asList(poi1, poi2, poi3, poi4);
	}
}
