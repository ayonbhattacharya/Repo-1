package spittr.test;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.servlet.view.InternalResourceView;

import spittr.Spittle;
import spittr.data.SpittleRepository;
import spittr.web.SpittleController;;

public class SplittleControllerTest {

	 @Test
	  public void shouldShowRecentSpittles() throws Exception {
	    List<Spittle> expectedSpittles = createSpittleList(20);
	    SpittleRepository mockRepository = mock(SpittleRepository.class);
	    when(mockRepository.findSpittles(Long.MAX_VALUE, 20))
	        .thenReturn(expectedSpittles);

	    SpittleController controller = new SpittleController(mockRepository);
	    MockMvc mockMvc = standaloneSetup(controller)
	        .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
	        .build();

	    mockMvc.perform(get("/spittles"))
	       .andExpect(view().name("spittles"))
	       .andExpect(model().attributeExists("spittleList"))
	       .andExpect(model().attribute("spittleList", 
	                  hasItems(expectedSpittles.toArray())));
	  }
	 
	  private List<Spittle> createSpittleList(int count) {
		    List<Spittle> spittles = new ArrayList<Spittle>();
		    for (int i=0; i < count; i++) {
		      spittles.add(new Spittle("Spittle " + i, new Date()));
		    }
		    return spittles;
		  }
		 @Test
		  public void shouldShowPagedSpittles() throws Exception {
		    List<Spittle> expectedSpittles = createSpittleList(20);
		    SpittleRepository mockRepository = mock(SpittleRepository.class);
		    when(mockRepository.findSpittles(Long.MAX_VALUE, 20))
		        .thenReturn(expectedSpittles);

		    SpittleController controller = new SpittleController(mockRepository);
		    MockMvc mockMvc = standaloneSetup(controller)
		        .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
		        .build();

		    mockMvc.perform(get("/spittles"))
		       .andExpect(view().name("spittles"))
		       .andExpect(model().attributeExists("spittleList"))
		       .andExpect(model().attribute("spittleList", 
		                  hasItems(expectedSpittles.toArray())));
		  }
}
