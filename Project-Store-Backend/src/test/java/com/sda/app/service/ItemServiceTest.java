package com.sda.app.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.sda.app.entity.Category;
import com.sda.app.entity.Item;
import com.sda.app.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
public class ItemServiceTest {
    @Mock
    private ItemRepository itemRepository;
    @InjectMocks
    private ItemService itemService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testGetAllProducts() {
        Item item1 = new Item();
        item1.setId(1);
        item1.setTitle("capacitor");
        item1.setPrice(20.00);
        item1.setCategory(Category.CAPACITORS);
        Item item2 = new Item();
        item2.setId(2);
        item2.setTitle("inductor");
        item2.setPrice(30.00);
        item2.setCategory(Category.INDUCTORS);
        // Mock data
        List<Item> productList = Arrays.asList(
                item1, item2
        );
        // Define the behavior of the repository mock
        when(itemRepository.findAll()).thenReturn(productList);
        // Call the service method
        List<Item> result = itemService.findAll();
        // Verify the result
        assertEquals(2, result.size());
        assertEquals("capacitor", result.get(0).getTitle());
        assertEquals("inductor", result.get(1).getTitle());
    }
    @Test
    public void testGetProductById() {
        // Mock data
        Item item1 = new Item();
        item1.setId(1);
        item1.setTitle("capacitor");
        item1.setPrice(20.00);
        item1.setCategory(Category.CAPACITORS);
        // Define the behavior of the repository mock
        when(itemRepository.findById(1)).thenReturn(Optional.of(item1));
        // Call the service method
        Optional<Item> result = itemService.getById(1);
        // Verify the result
        assertEquals(true, result.isPresent());
        assertEquals("capacitor", result.get().getTitle());
    }
    @Test
    public void testSaveProduct() {
        // Mock data
        Item item1 = new Item();
        item1.setTitle("capacitor");
        item1.setPrice(20.00);
        item1.setCategory(Category.CAPACITORS);
        // Call the service method
        itemService.createItem(item1);
        // Verify that the repository’s save method was called
        verify(itemRepository, times(1)).save(item1);
    }
    @Test
    public void testDeleteProduct() {
        itemService.deleteItem(1);
        verify(itemRepository, times(1)).deleteById(1);
    }
}