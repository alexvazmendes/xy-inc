package br.com.xyinc.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.xyinc.model.Codes;
import br.com.xyinc.model.Poi;
import br.com.xyinc.model.ResponseModel;
import br.com.xyinc.service.PoiService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(
		  locations = "classpath:test.properties")
public class PoiControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private PoiService poiService;

	private Poi poi;
	private ResponseModel rmSucess;
	private ResponseModel rmError;
	
	@Before
	public void setup() {
		poi = new Poi(1L, "local1", 0L, 0L);
		rmSucess = new ResponseModel(0, "", 1L);
		rmError = new ResponseModel(99, "");
	}

	@Test
	public void getOneById() {
		BDDMockito.when(poiService.getById(poi.getId())).thenReturn(poi);
		Poi poi = restTemplate.getForObject("/poi/1", Poi.class);
		assertTrue(poi.getId() == 1L);
	}

	@Test
	public void createNewPoi() {
		BDDMockito.when(poiService.create((Matchers.any(Poi.class)))).thenReturn(rmSucess);
		BDDMockito.when(poiService.getById(poi.getId())).thenReturn(poi);
		ResponseModel response = restTemplate.postForObject("/poi", poi, ResponseModel.class);
		assertTrue(response.getId() == 1 && response.getStatus() == Codes.SUCCESS);
		Poi poi = restTemplate.getForObject("/poi/1", Poi.class);
		assertTrue(poi.getId() == 1);
	}

	@Test
	public void deletePoi() {
		BDDMockito.when(poiService.delete(1L)).thenReturn(rmSucess);
		BDDMockito.when(poiService.getById(poi.getId())).thenReturn(rmError);
		ResponseEntity<ResponseModel> exchange = restTemplate.exchange("/poi/{id}", 
				HttpMethod.DELETE, null, ResponseModel.class, 1L);
		ResponseModel response = exchange.getBody();
		assertTrue(response.getId() == 1 && response.getStatus() == Codes.SUCCESS);

		ResponseModel responseGet = restTemplate.getForObject("/poi/1", ResponseModel.class);
		assertTrue(responseGet.getStatus() != Codes.SUCCESS);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void findAllPoisWithAnEmptyResult() {
		List<Poi> pois = new ArrayList<Poi>();
		BDDMockito.when(poiService.getAll()).thenReturn(pois);
		List<Poi> newPois = restTemplate.getForObject("/poi", List.class);

		assertEquals(newPois.size(), 0);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void findAllPois() {
		List<Poi> pois = Arrays.asList(poi = new Poi(1L, "local1", 0L, 0L), 
				poi = new Poi(2L, "local2", 0L, 0L));
		BDDMockito.when(poiService.getAll()).thenReturn(pois);
		List<Poi> newPois = restTemplate.getForObject("/poi", List.class);

		assertEquals(newPois.size(), 2);
	}

	@Test
	public void findAllPoisWithAnError() {
		BDDMockito.when(poiService.getAll()).thenReturn(rmError);
		ResponseModel rs = restTemplate.getForObject("/poi", ResponseModel.class);

		assertEquals(rs.getStatus(), Codes.OTHER_ERROR);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void findByDistance() {
		List<Poi> pois = Arrays.asList(poi = new Poi(1L, "local1", 0L, 0L), 
				poi = new Poi(2L, "local2", 0L, 0L));
		BDDMockito.when(poiService.getAllByDistance(0L, 0L, 1L)).thenReturn(pois);
		List<Poi> newPois = restTemplate.getForObject("/poi/by-distance?distance=1&"
				+ "x=0&y=0", List.class);

		assertEquals(newPois.size(), 2);
	}
}
