package br.com.xyinc.repository;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.xyinc.model.Poi;
import br.com.xyinc.repository.PoiRepository;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PoiRepositoryTest {

	@Autowired
	PoiRepository repository;
	
	@Test
	public void savePoi() {
		Poi poi = createAndSavePoi();
		assertTrue(poi.getId() != null);
	}
	
	@Test
	public void updatePoi() {
		Poi poi = createAndSavePoi();
		poi.setName("newname");
		Poi updatedPoi = repository.save(poi);
		assertEquals(poi.getId(), updatedPoi.getId());
	}
	
	@Test
	public void deleteAndFindPoi() {
		Poi poi = createAndSavePoi();
		assertEquals(repository.findOne(poi.getId()).getName(), "name");
		repository.delete(poi.getId());
		assertEquals(repository.findOne(poi.getId()), null);
	}
	
	@Test
	public void findPoisWithEmptyTable() {
		List<Poi> pois = (List<Poi>) repository.findAll();
		assertTrue(pois.isEmpty());
	}
	
	@Test
	public void findPoisWithElements() {
		createAndSavePoi();
		createAndSavePoi();
		
		List<Poi> pois = (List<Poi>) repository.findAll();
		assertEquals(pois.size(), 2);
	}

	private Poi createAndSavePoi() {
		return repository.save(new Poi("name", 0L, 0L));
	}
	
}
