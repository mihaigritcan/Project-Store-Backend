package com.sda.app.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.app.entity.Category;
import com.sda.app.entity.Item;
import com.sda.app.service.ItemService;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(ItemController.class)
public class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ItemService itemService;
    @Test
    public void testGetAllProducts() throws Exception {
        Item item1 = new Item();
        item1.setId(1);
        item1.setTitle("resistors");
        item1.setPrice(55.00);
        item1.setCategory(Category.RESISTORS);
        Item item2 = new Item();
        item2.setId(2);
        item2.setTitle("resistors");
        item2.setPrice(80.00);
        item2.setCategory(Category.RESISTORS);
        List<Item> products = Arrays.asList(item1, item2);
        Mockito.when(itemService.findAll()).thenReturn(products);
        mockMvc.perform(get("/items/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", IsCollectionWithSize.hasSize(2)))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].title").value("resistors"))
                .andExpect(jsonPath("$.data[0].price").value(55.00))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].title").value("resistors"))
                .andExpect(jsonPath("$.data[1].price").value(80.00));
    }
    @Test
    public void testCreateProduct() throws Exception {
        Item item = new Item();
        item.setTitle("New Product");
        item.setPrice(35.00);
        item.setCategory(Category.RESISTORS);
        Mockito.when(itemService.createItem(Mockito.any(Item.class))).thenReturn(item);
        mockMvc.perform(post("/items/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(item)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("New Product"))
                .andExpect(jsonPath("$.data.price").value(35.00));
    }
}
