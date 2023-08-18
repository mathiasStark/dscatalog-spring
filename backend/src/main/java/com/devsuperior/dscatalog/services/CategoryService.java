package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dtos.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exception.EntityNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	

	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		// TODO Auto-generated method stub
		List<Category> list =  repository.findAll();
		return  list.stream().map(category -> new CategoryDTO(category)).collect(Collectors.toList()); 
	}


	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Produto não encontrado")); //exceção lançada, necessário capturar no controller
		return new CategoryDTO(entity);
	}


	public CategoryDTO insert(CategoryDTO dto) {
		Category category = new Category();
		category.setName(dto.getName());
		category = repository.save(category);
		return new CategoryDTO(category);
	}


	public CategoryDTO update(CategoryDTO dto, Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
		repository.save(entity);
		CategoryDTO dtoUpdate = new CategoryDTO(entity);
		return dtoUpdate;
	}

}
