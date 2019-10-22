package com.boot.soumen;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.soumen.boot.controller.ShipwreckController;
import com.soumen.boot.model.Shipwreck;
import com.soumen.boot.repository.ShipwreckRepository;

public class ShipwreckControllerTest {
	@InjectMocks
	private ShipwreckController sc;

	@Mock
	private ShipwreckRepository shipwreckRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testShipwreckGet() {
    	Shipwreck sw = new Shipwreck();
    	sw.setId(1l);
		when(shipwreckRepository.findById(1L)).thenReturn(Optional.of(sw));

		Shipwreck wreck = sc.get(1L);

		verify(shipwreckRepository).findById(1L);		

//		assertEquals(1l, wreck.getId().longValue());	
		assertThat(wreck.getId(), is(1l));
	}

}
