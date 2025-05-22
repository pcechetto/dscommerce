package com.example.dscommerce.services;

import com.example.dscommerce.dto.CategoryDTO;
import com.example.dscommerce.entities.Category;
import com.example.dscommerce.repositories.CategoryRepository;
import com.example.dscommerce.tests.CategoryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    private Category category;
    private List<Category> listCategory;

    @BeforeEach
    void setUp() throws Exception {
        category = CategoryFactory.createCategory();

        listCategory = new ArrayList<>();
        listCategory.add(category);

        Mockito.when(categoryRepository.findAll()).thenReturn(listCategory);
    }

    @Test
    public void findAllShouldReturnListCategoryDTO() {
        List<CategoryDTO> result = categoryService.findAll();
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(category.getId(), result.getFirst().getId());
        Assertions.assertEquals(category.getName(), result.getFirst().getName());
    }
}
