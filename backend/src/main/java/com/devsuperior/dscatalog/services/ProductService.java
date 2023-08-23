package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dtos.ProductDTO;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exception.EntityNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
		// TODO Auto-generated method stub
		Page<Product> page = repository.findAll(pageRequest);
		return page.map(entity -> new ProductDTO(entity));
//		return  list.stream().map(entity -> new ProductDTO(entity)).collect(Collectors.toList()); 
	}


	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new EntityNotFoundException("Produto não encontrado")); //exceção lançada, necessário capturar no controller
		return new ProductDTO(entity);
	}


	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		//entity.setName(dto.getName());
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}


	public ProductDTO update(ProductDTO dto, Long id) {
		Optional<Product> obj = repository.findById(id);
		//Product entity = repository.getOne(id);
		Product entity = obj.orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
		//entity.setName(dto.getName());
		repository.save(entity);
		ProductDTO dtoUpdate = new ProductDTO(entity);
		return dtoUpdate;
	}


	public void deleteById(Long id) {
		
		try {
			repository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			System.out.println(e.getMessage());
		} catch(DataIntegrityViolationException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
