package com.devsuperior.dscatalog.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.dscatalog.dtos.ProductDTO;
import com.devsuperior.dscatalog.services.ProductService;

/**
 * Também chamado de Controller
 */

@RestController
@RequestMapping(value = "/products")
@CrossOrigin
public class ProductResource {
	
	@Autowired
	private ProductService categoryService;

	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "DESC") String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Page<ProductDTO> list = categoryService.findAllPaged(pageRequest);
		return ResponseEntity.ok().body(list);
	}
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
		ProductDTO entity = categoryService.findById(id);
		return ResponseEntity.ok(entity);
	}
	
	@PostMapping
	public ResponseEntity<ProductDTO> insertProduct(@RequestBody ProductDTO dto) {		
		ProductDTO categoryDTO = categoryService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.ok().body(categoryDTO);
	}
	
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<ProductDTO> updateById(@RequestBody ProductDTO dto, @PathVariable Long id) {
		ProductDTO entity = categoryService.update(dto, id);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/update/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.ok().body(entity);
	}
	
	@DeleteMapping(value = "delete/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		categoryService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
